package lf3.plp.expressions2.memory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Metadados de depuração no nível do programa.
 * Contém a pilha de compilação (PilhaSnapshot) e é responsável por
 * produzir a representação serializável para a API/GUI.
 */
public class MetadadosDepuracao<T> {
	private final PilhaSnapshot<T> pilhaSnapshot;

	public MetadadosDepuracao() {
		this.pilhaSnapshot = new PilhaSnapshot<T>();
	}

	public PilhaSnapshot<T> getPilhaSnapshot() {
		return pilhaSnapshot;
	}

	/** Retorna o snapshot serializado para a API. */
	public List<Map<String, Object>> toSnapshot() {
		List<Map<String, Object>> snapshot = new ArrayList<Map<String, Object>>();
		for (QuadroEscopo quadro : pilhaSnapshot.getQuadros()) {
			Map<String, Object> quadroMap = new LinkedHashMap<String, Object>();

			Map<String, Object> bindingsMap = new LinkedHashMap<String, Object>();
			for (Map.Entry<String, InfoBinding> entry : quadro.getBindings().entrySet()) {
				Map<String, Object> bindingMap = new LinkedHashMap<String, Object>();
				bindingMap.put("type", entry.getValue().getTipo());
				bindingMap.put("value", entry.getValue().getValor());
				bindingMap.put("display", entry.getValue().getValor());
				bindingsMap.put(entry.getKey(), bindingMap);
			}
			quadroMap.put("bindings", bindingsMap);

			if (quadro.getEscopo() != null) {
				quadroMap.put("scope", quadro.getEscopo());
			}

			TrechoCodigoFonte tcf = quadro.getTrechoCodigoFonte();
			if (tcf != null) {
				Map<String, Integer> range = new LinkedHashMap<String, Integer>();
				range.put("startLine", tcf.getLinhaInicio());
				range.put("startColumn", tcf.getColunaInicio());
				range.put("endLine", tcf.getLinhaFim());
				range.put("endColumn", tcf.getColunaFim());
				quadroMap.put("sourceRange", range);
			}

			snapshot.add(quadroMap);
		}
		return snapshot;
	}
}
