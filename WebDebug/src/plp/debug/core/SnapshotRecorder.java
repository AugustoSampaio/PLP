package plp.debug.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Acumula os escopos observados durante a verificação de tipos.
 *
 * Cada {@code incrementa()} do ambiente empilha um nível. Um nível só se
 * torna um frame visível no debugger quando um nó da AST publica seu escopo
 * via {@link #registraEscopo(InfoEscopo)} — mesma estratégia dos marcadores
 * usada originalmente em f288186. Assim os níveis internos do verificador de
 * tipos (unificação de aplicação de função, chamada de procedimento, etc.)
 * não poluem a saída: eles nunca registram escopo.
 *
 * A faixa de código-fonte vem do parser do WebDebug, capturada no ponto
 * sintático exato do escopo, então escopo e faixa são precisos. Revisitas do
 * mesmo escopo (comuns na inferência de tipos, que retipa funções recursivas
 * a cada chamada) colapsam no mesmo frame por identidade exata
 * ({@link InfoEscopo#chave()}), e os bindings mais recentes — tipicamente
 * mais resolvidos — sobrescrevem os anteriores.
 */
public final class SnapshotRecorder implements ScopeAware {

	/** Frames publicados, na ordem em que os escopos foram registrados. */
	private final List<Map<String, Object>> published = new ArrayList<Map<String, Object>>();

	/** Identidade do escopo -> frame já publicado, para colapsar revisitas. */
	private final Map<String, Map<String, Object>> byKey = new LinkedHashMap<String, Map<String, Object>>();

	/** Um item por incrementa(); null enquanto o nível não registrar escopo. */
	private final List<Map<String, Object>> stack = new ArrayList<Map<String, Object>>();

	public void pushFrame() {
		stack.add(null);
	}

	public void popFrame() {
		if (!stack.isEmpty()) {
			stack.remove(stack.size() - 1);
		}
	}

	@Override
	public void registraEscopo(InfoEscopo info) {
		if (info == null || stack.isEmpty()) {
			return;
		}
		int top = stack.size() - 1;
		if (stack.get(top) != null) {
			// Nível já registrado: mantém o primeiro escopo publicado.
			return;
		}
		String key = info.chave();
		Map<String, Object> frame = byKey.get(key);
		if (frame == null) {
			frame = novoFrame(info);
			byKey.put(key, frame);
			published.add(frame);
		}
		stack.set(top, frame);
	}

	private Map<String, Object> novoFrame(InfoEscopo info) {
		Map<String, Object> frame = new LinkedHashMap<String, Object>();
		if (info.getNome() != null) {
			frame.put("name", info.getNome());
		}
		if (info.getEscopo() != null) {
			frame.put("scope", info.getEscopo());
		}
		frame.put("bindings", new LinkedHashMap<String, Object>());
		TrechoCodigoFonte trecho = info.getTrechoCodigoFonte();
		if (trecho != null) {
			Map<String, Object> range = new LinkedHashMap<String, Object>();
			range.put("startLine", Integer.valueOf(trecho.getLinhaInicio()));
			range.put("startColumn", Integer.valueOf(trecho.getColunaInicio()));
			range.put("endLine", Integer.valueOf(trecho.getLinhaFim()));
			range.put("endColumn", Integer.valueOf(trecho.getColunaFim()));
			frame.put("sourceRange", range);
		}
		return frame;
	}

	/**
	 * Registra um binding no escopo corrente.
	 *
	 * @param idArg identificador vinculado.
	 * @param valorArg valor (Tipo) associado.
	 * @param display texto preferido para exibição, fornecido pelo wrapper da
	 *        linguagem (normalmente {@code Tipo.getNome()}); pode ser null.
	 */
	@SuppressWarnings("unchecked")
	public void recordBinding(Object idArg, Object valorArg, String display) {
		if (stack.isEmpty()) {
			return;
		}
		Map<String, Object> frame = stack.get(stack.size() - 1);
		if (frame == null) {
			// Nível interno do verificador de tipos, sem escopo registrado.
			return;
		}
		Map<String, Object> bindings = (Map<String, Object>) frame.get("bindings");
		bindings.put(idArg == null ? "null" : idArg.toString(), TipoRenderer.describe(valorArg, display));
	}

	public List<Map<String, Object>> getSnapshot() {
		return published;
	}
}
