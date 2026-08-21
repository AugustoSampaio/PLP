package plp.debug.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Deriva a faixa de código-fonte de um escopo a partir dos nomes vinculados
 * nele ("name-anchored"), usando apenas o fluxo de tokens produzido pelo lexer
 * real da linguagem.
 *
 * Motivação: o ambiente observado por {@link SnapshotRecorder} só expõe
 * incrementa()/restaura()/map(), sem qualquer referência ao nó da AST que
 * abriu o escopo. Como o parser (.jj) não é alterado, não há InfoEscopo com a
 * posição exata. Então, em vez de alinhar frames a regiões por ordem (frágil,
 * porque escopos de parâmetro não têm delimitador textual próprio), ancoramos
 * pelo nome: localizamos a declaração de um dos nomes vinculados no frame e
 * subimos até o construtor que o envolve (let/in, bloco, classe, proc).
 *
 * Isto é aproximado por construção:
 * <ul>
 *   <li>nomes repetidos ou sombreados podem casar com a ocorrência errada;</li>
 *   <li>frames sem nenhum binding textual (ex.: só "this", ou procedimento sem
 *       parâmetros) ficam sem faixa — preferimos nenhuma faixa a uma errada;</li>
 *   <li>frames distintos da mesma declaração (placeholder de recursão x tipo
 *       final) compartilham a mesma faixa.</li>
 * </ul>
 */
public final class SourceRangeResolver {

	/** Palavras que antecedem o identificador em uma declaração. */
	private static final Set<String> DECL_PRECEDERS = new HashSet<String>(Arrays.asList(
			"var", "fun", "proc", "classe", "int", "boolean", "string"));

	/** Construtores que abrem um escopo. */
	private static final Set<String> OPENERS = new HashSet<String>(Arrays.asList(
			"let", "{", "classe", "proc"));

	/**
	 * Declarações cujo próprio nome pertence ao escopo de fora: em
	 * "proc foo() {...}" o binding de foo vive no bloco que contém o proc, e
	 * não no escopo aberto pelo proc.
	 */
	private static final Set<String> NAMES_BELONG_OUTSIDE = new HashSet<String>(Arrays.asList(
			"proc", "classe"));

	/** Bindings sintéticos, sem ocorrência no código-fonte. */
	private static final Set<String> SYNTHETIC = new HashSet<String>(Arrays.asList("this"));

	private final List<SourceToken> tokens;

	public SourceRangeResolver(List<SourceToken> tokens) {
		this.tokens = tokens;
	}

	/**
	 * Resolve o escopo que contém os nomes informados.
	 *
	 * @return informações do escopo, ou {@code null} se não for possível
	 *         determiná-lo com segurança.
	 */
	public ScopeInfo resolve(Collection<String> bindingNames) {
		if (tokens.isEmpty() || bindingNames == null) {
			return null;
		}
		for (String name : bindingNames) {
			if (name == null || SYNTHETIC.contains(name)) {
				continue;
			}
			int anchor = findDeclaration(name);
			if (anchor < 0) {
				continue;
			}
			ScopeInfo info = enclosingScope(anchor);
			if (info != null) {
				return info;
			}
		}
		return null;
	}

	/**
	 * Procura a ocorrência de declaração do nome (identificador precedido por
	 * uma palavra de declaração). Se não houver, usa a primeira ocorrência.
	 */
	private int findDeclaration(String name) {
		int firstOccurrence = -1;
		for (int i = 0; i < tokens.size(); i++) {
			if (!name.equals(tokens.get(i).getImage())) {
				continue;
			}
			if (firstOccurrence < 0) {
				firstOccurrence = i;
			}
			if (i > 0 && DECL_PRECEDERS.contains(tokens.get(i - 1).getImage())) {
				return i;
			}
		}
		return firstOccurrence;
	}

	/** Sobe do token âncora até o construtor que abre o escopo. */
	private ScopeInfo enclosingScope(int anchor) {
		int start = anchor - 1;
		// O nome de um proc/classe pertence ao escopo de fora: pula o opener
		// imediatamente anterior para não atribuir o escopo do próprio proc.
		if (start >= 0 && NAMES_BELONG_OUTSIDE.contains(tokens.get(start).getImage())) {
			start = start - 1;
		}

		int braceDepth = 0;
		int inDepth = 0;
		for (int j = start; j >= 0; j--) {
			String image = tokens.get(j).getImage();
			if ("}".equals(image)) {
				braceDepth++;
			} else if ("{".equals(image)) {
				if (braceDepth == 0) {
					return fromBrace(j);
				}
				braceDepth--;
			} else if ("in".equals(image)) {
				inDepth++;
			} else if ("let".equals(image)) {
				if (inDepth == 0) {
					return fromLet(j);
				}
				inDepth--;
			} else if (OPENERS.contains(image) && braceDepth == 0) {
				// proc/classe alcançado antes de qualquer chave: o cabeçalho
				// abre o escopo (ex.: parâmetros de um procedimento).
				return fromHeader(j, image);
			}
		}
		return null;
	}

	/** let ... in: a faixa vai do let até o in correspondente. */
	private ScopeInfo fromLet(int letIndex) {
		int depth = 0;
		for (int j = letIndex + 1; j < tokens.size(); j++) {
			String image = tokens.get(j).getImage();
			if ("let".equals(image)) {
				depth++;
			} else if ("in".equals(image)) {
				if (depth == 0) {
					return range(letIndex, j, "let-in", null);
				}
				depth--;
			}
		}
		return null;
	}

	/**
	 * Bloco por chaves. Se as chaves forem o corpo de uma classe ou
	 * procedimento, a faixa começa no cabeçalho e o rótulo reflete isso.
	 */
	private ScopeInfo fromBrace(int braceIndex) {
		int close = matchBrace(braceIndex);
		if (close < 0) {
			return null;
		}
		int header = headerBefore(braceIndex);
		if (header >= 0) {
			String keyword = tokens.get(header).getImage();
			return range(header, close, labelFor(keyword), nameAfter(header));
		}
		return range(braceIndex, close, "block", null);
	}

	/** proc/classe alcançado diretamente (antes das chaves do corpo). */
	private ScopeInfo fromHeader(int headerIndex, String keyword) {
		for (int j = headerIndex + 1; j < tokens.size(); j++) {
			if ("{".equals(tokens.get(j).getImage())) {
				int close = matchBrace(j);
				if (close < 0) {
					return null;
				}
				return range(headerIndex, close, labelFor(keyword), nameAfter(headerIndex));
			}
		}
		return null;
	}

	/**
	 * Verifica se a chave em braceIndex é o corpo de um classe/proc,
	 * ignorando o nome e a lista de parâmetros entre o cabeçalho e a chave.
	 */
	private int headerBefore(int braceIndex) {
		for (int j = braceIndex - 1; j >= 0; j--) {
			String image = tokens.get(j).getImage();
			if (NAMES_BELONG_OUTSIDE.contains(image)) {
				return j;
			}
			boolean skippable = "(".equals(image) || ")".equals(image) || ",".equals(image)
					|| DECL_PRECEDERS.contains(image) || isIdentifier(image);
			if (!skippable) {
				return -1;
			}
		}
		return -1;
	}

	private int matchBrace(int openIndex) {
		int depth = 0;
		for (int j = openIndex + 1; j < tokens.size(); j++) {
			String image = tokens.get(j).getImage();
			if ("{".equals(image)) {
				depth++;
			} else if ("}".equals(image)) {
				if (depth == 0) {
					return j;
				}
				depth--;
			}
		}
		return -1;
	}

	private String nameAfter(int index) {
		if (index + 1 < tokens.size() && isIdentifier(tokens.get(index + 1).getImage())) {
			return tokens.get(index + 1).getImage();
		}
		return null;
	}

	private static String labelFor(String keyword) {
		if ("classe".equals(keyword)) {
			return "class";
		}
		if ("proc".equals(keyword)) {
			return "procedure";
		}
		return "block";
	}

	private static boolean isIdentifier(String image) {
		if (image == null || image.isEmpty()) {
			return false;
		}
		char first = image.charAt(0);
		return Character.isLetter(first) || first == '_' || first == '$';
	}

	private ScopeInfo range(int fromIndex, int toIndex, String label, String name) {
		SourceToken from = tokens.get(fromIndex);
		SourceToken to = tokens.get(toIndex);
		return new ScopeInfo(from.getBeginLine(), from.getBeginColumn(),
				to.getEndLine(), to.getEndColumn(), label, name);
	}

	/** Faixa e rótulo de um escopo localizado no código-fonte. */
	public static final class ScopeInfo {
		private final int startLine;
		private final int startColumn;
		private final int endLine;
		private final int endColumn;
		private final String label;
		private final String name;

		ScopeInfo(int startLine, int startColumn, int endLine, int endColumn, String label, String name) {
			this.startLine = startLine;
			this.startColumn = startColumn;
			this.endLine = endLine;
			this.endColumn = endColumn;
			this.label = label;
			this.name = name;
		}

		public int getStartLine() { return startLine; }
		public int getStartColumn() { return startColumn; }
		public int getEndLine() { return endLine; }
		public int getEndColumn() { return endColumn; }
		public String getLabel() { return label; }
		public String getName() { return name; }
	}
}
