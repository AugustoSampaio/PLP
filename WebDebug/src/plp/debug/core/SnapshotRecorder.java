package plp.debug.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Acumula um snapshot de bindings por escopo a partir de chamadas
 * incrementa()/restaura()/map(id, valor) observadas em um ambiente de
 * compilação real. Não depende de nenhuma classe específica de linguagem —
 * cada linguagem é observada por um wrapper que implementa a interface do
 * seu próprio ambiente e delega para uma instância desta classe.
 *
 * Como o parser (arquivo .jj) de cada linguagem não constrói nenhuma classe
 * deste módulo, não há como capturar rótulos de escopo ou posições no
 * código-fonte aqui — apenas os bindings visíveis em cada nível de
 * incrementa()/restaura() são registrados.
 *
 * Linguagens com inferência de tipos (Funcional1/2/3) reavaliam a mesma
 * declaração várias vezes (ex.: uma função recursiva é retipada a cada
 * chamada durante a unificação), o que produziria um frame duplicado por
 * reavaliação. Como não há identidade de nó de AST disponível aqui (isso só
 * existiria se o parser publicasse posições de código-fonte), os frames são
 * agrupados heuristicamente por (profundidade de aninhamento, conjunto de
 * nomes vinculados): reavaliações da mesma declaração normalmente ocorrem na
 * mesma profundidade e vinculam os mesmos nomes, então caem na mesma chave e
 * o valor mais recente (tipicamente mais resolvido) substitui o anterior no
 * lugar do primeiro frame visto com aquela chave. Isso é uma heurística, não
 * uma correspondência exata: dois escopos genuinamente diferentes que
 * calhem de ter a mesma profundidade e os mesmos nomes de binding
 * colapsariam incorretamente em um só.
 */
public final class SnapshotRecorder {

	private final List<Map<String, Object>> frames = new ArrayList<Map<String, Object>>();
	private final List<Integer> frameDepths = new ArrayList<Integer>();
	private final List<Map<String, Object>> activeFrames = new ArrayList<Map<String, Object>>();

	public void pushFrame() {
		Map<String, Object> frame = new LinkedHashMap<String, Object>();
		frame.put("bindings", new LinkedHashMap<String, Object>());
		frames.add(frame);
		frameDepths.add(activeFrames.size() + 1);
		activeFrames.add(frame);
	}

	public void popFrame() {
		if (!activeFrames.isEmpty()) {
			activeFrames.remove(activeFrames.size() - 1);
		}
	}

	@SuppressWarnings("unchecked")
	public void recordBinding(Object idArg, Object valorArg) {
		if (activeFrames.isEmpty()) {
			return;
		}
		Map<String, Object> frame = activeFrames.get(activeFrames.size() - 1);
		Map<String, Object> bindings = (Map<String, Object>) frame.get("bindings");
		bindings.put(idArg == null ? "null" : idArg.toString(), describeValor(valorArg));
	}

	private Map<String, Object> describeValor(Object valorArg) {
		Map<String, Object> binding = new LinkedHashMap<String, Object>();
		String display = valorArg == null ? null : valorArg.toString();
		binding.put("type", valorArg == null ? null : valorArg.getClass().getSimpleName());
		binding.put("value", display);
		binding.put("display", display);
		return binding;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSnapshot() {
		List<Map<String, Object>> deduped = new ArrayList<Map<String, Object>>();
		Map<String, Integer> keyToIndex = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < frames.size(); i++) {
			Map<String, Object> frame = frames.get(i);
			String key = dedupeKey(frameDepths.get(i), (Map<String, Object>) frame.get("bindings"));
			Integer existingIndex = keyToIndex.get(key);
			if (existingIndex == null) {
				keyToIndex.put(key, deduped.size());
				deduped.add(frame);
			} else {
				Map<String, Object> existingBindings =
						(Map<String, Object>) deduped.get(existingIndex).get("bindings");
				existingBindings.putAll((Map<String, Object>) frame.get("bindings"));
			}
		}
		return deduped;
	}

	/**
	 * Como {@link #getSnapshot()}, mas anota cada frame com a faixa de
	 * código-fonte, o rótulo do escopo e (quando aplicável) o nome do
	 * construtor, resolvidos pelo {@code resolver} a partir dos nomes
	 * vinculados no frame. Frames que o resolver não consegue localizar ficam
	 * sem faixa, em vez de receber uma faixa possivelmente errada.
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSnapshot(SourceRangeResolver resolver) {
		List<Map<String, Object>> deduped = getSnapshot();
		if (resolver == null) {
			return deduped;
		}
		for (Map<String, Object> frame : deduped) {
			Map<String, Object> bindings = (Map<String, Object>) frame.get("bindings");
			SourceRangeResolver.ScopeInfo info = resolver.resolve(bindings.keySet());
			if (info == null) {
				continue;
			}
			if (info.getLabel() != null) {
				frame.put("scope", info.getLabel());
			}
			if (info.getName() != null) {
				frame.put("name", info.getName());
			}
			Map<String, Object> range = new LinkedHashMap<String, Object>();
			range.put("startLine", Integer.valueOf(info.getStartLine()));
			range.put("startColumn", Integer.valueOf(info.getStartColumn()));
			range.put("endLine", Integer.valueOf(info.getEndLine()));
			range.put("endColumn", Integer.valueOf(info.getEndColumn()));
			frame.put("sourceRange", range);
		}
		return deduped;
	}

	private String dedupeKey(int depth, Map<String, Object> bindings) {
		List<String> names = new ArrayList<String>(bindings.keySet());
		Collections.sort(names);
		return depth + "|" + names;
	}
}

