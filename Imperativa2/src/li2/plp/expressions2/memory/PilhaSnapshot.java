package li2.plp.expressions2.memory;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Pilha de escopos capturados durante a verificação de tipos.
 * Variante "eager": o quadro é criado imediatamente em incrementa().
 */
public class PilhaSnapshot<T> {
	private final List<QuadroEscopo> quadros;
	private final Stack<QuadroEscopo> quadrosAtivos;
	private final Stack<Boolean> marcadores;
	private final Set<String> sourceRangesPublicados;

	public PilhaSnapshot() {
		this.quadros = new ArrayList<QuadroEscopo>();
		this.quadrosAtivos = new Stack<QuadroEscopo>();
		this.marcadores = new Stack<Boolean>();
		this.sourceRangesPublicados = new LinkedHashSet<String>();
	}

	/** Cria um novo quadro imediatamente ao entrar no escopo. */
	public void incrementa() {
		marcadores.push(Boolean.TRUE);
		quadrosAtivos.push(new QuadroEscopo(null, null));
	}

	/** Registra as informações do escopo no quadro ativo. */
	public void registraEscopo(InfoEscopo info) {
		registraEscopo(info, null);
	}

	/** Registra as informações do escopo e um nome opcional para o frame. */
	public void registraEscopo(InfoEscopo info, String nome) {
		if (!quadrosAtivos.empty() && info != null) {
			QuadroEscopo antigo = quadrosAtivos.pop();
			QuadroEscopo novo = new QuadroEscopo(nome, info.getEscopo(), info.getTrechoCodigoFonte());
			for (java.util.Map.Entry<String, InfoBinding> e : antigo.getBindings().entrySet()) {
				novo.adicionaBinding(e.getKey(), e.getValue());
			}
			String sourceRangeKey = sourceRangeKey(info.getTrechoCodigoFonte());
			if (sourceRangeKey == null || sourceRangesPublicados.add(sourceRangeKey)) {
				quadros.add(novo);
			}
			quadrosAtivos.push(novo);
		}
	}

	/** Registra um binding no escopo ativo. */
	public void map(String nome, T valorId) {
		if (!quadrosAtivos.empty()) {
			String tipo = valorId == null ? null : valorId.getClass().getSimpleName();
			String valor = valorId == null ? null : valorId.toString();
			quadrosAtivos.peek().adicionaBinding(nome, new InfoBinding(tipo, valor));
		}
	}

	/** Restaura (sai do escopo atual). */
	public void restaura() {
		if (!marcadores.empty() && marcadores.pop()) {
			if (!quadrosAtivos.empty()) {
				quadrosAtivos.pop();
			}
		}
	}

	/** Retorna todos os quadros capturados. */
	public List<QuadroEscopo> getQuadros() {
		return quadros;
	}

	private String sourceRangeKey(TrechoCodigoFonte trechoCodigoFonte) {
		if (trechoCodigoFonte == null) {
			return null;
		}
		return trechoCodigoFonte.getLinhaInicio() + ":" + trechoCodigoFonte.getColunaInicio() + "-"
			+ trechoCodigoFonte.getLinhaFim() + ":" + trechoCodigoFonte.getColunaFim();
	}
}
