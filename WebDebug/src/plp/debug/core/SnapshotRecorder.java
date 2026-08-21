package plp.debug.core;

import java.util.ArrayList;
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
 */
public final class SnapshotRecorder {

	private final List<Map<String, Object>> frames = new ArrayList<Map<String, Object>>();
	private final List<Map<String, Object>> activeFrames = new ArrayList<Map<String, Object>>();

	public void pushFrame() {
		Map<String, Object> frame = new LinkedHashMap<String, Object>();
		frame.put("bindings", new LinkedHashMap<String, Object>());
		frames.add(frame);
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

	public List<Map<String, Object>> getSnapshot() {
		return frames;
	}
}
