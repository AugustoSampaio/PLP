package lf3.plp.expressions2.memory;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Pilha de escopos capturados durante a verificação de tipos.
 * Variante "lazy": o quadro só é criado quando registraEscopo é chamado.
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

	/** Marca a entrada em um novo nível de escopo (ainda sem metadata). */
	public void incrementa() {
		marcadores.push(Boolean.FALSE);
	}

	/** Registra as informações do escopo atual, criando o quadro. */
	public void registraEscopo(InfoEscopo info) {
		if (marcadores.empty()) return;

		boolean visivel = marcadores.pop();
		if (!visivel) {
			marcadores.push(Boolean.TRUE);
			QuadroEscopo quadro = new QuadroEscopo(
				info != null ? info.getEscopo() : null,
				info != null ? info.getTrechoCodigoFonte() : null
			);
			String sourceRangeKey = sourceRangeKey(info != null ? info.getTrechoCodigoFonte() : null);
			if (sourceRangeKey == null || sourceRangesPublicados.add(sourceRangeKey)) {
				quadros.add(quadro);
			}
			quadrosAtivos.push(quadro);
		} else {
			marcadores.push(Boolean.TRUE);
		}
	}

	/** Registra um binding no escopo ativo. */
	public void map(String nome, T valorId) {
		if (!marcadores.empty() && marcadores.peek() && !quadrosAtivos.empty()) {
			String tipo = valorId == null ? null : valorId.getClass().getSimpleName();
			String valor = valorId == null ? null : valorId.toString();
			quadrosAtivos.peek().adicionaBinding(nome, new InfoBinding(tipo, valor));
		}
	}

	/** Restaura (sai do escopo atual). */
	public void restaura() {
		if (!marcadores.empty()) {
			boolean visivel = marcadores.pop();
			if (visivel && !quadrosAtivos.empty()) {
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
