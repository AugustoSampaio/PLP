package plp.debug.core;

/**
 * Trecho de código-fonte delimitado por linha/coluna inicial e final.
 * Construído pelo parser do WebDebug a partir dos tokens reais, no exato
 * ponto sintático em que o escopo é criado — por isso a faixa é precisa,
 * e não inferida.
 */
public final class TrechoCodigoFonte {

	private final int linhaInicio;
	private final int colunaInicio;
	private final int linhaFim;
	private final int colunaFim;

	public TrechoCodigoFonte(int linhaInicio, int colunaInicio, int linhaFim, int colunaFim) {
		this.linhaInicio = linhaInicio;
		this.colunaInicio = colunaInicio;
		this.linhaFim = linhaFim;
		this.colunaFim = colunaFim;
	}

	public int getLinhaInicio() {
		return linhaInicio;
	}

	public int getColunaInicio() {
		return colunaInicio;
	}

	public int getLinhaFim() {
		return linhaFim;
	}

	public int getColunaFim() {
		return colunaFim;
	}

	/** Chave de identidade do trecho, usada para deduplicar escopos. */
	public String chave() {
		return linhaInicio + ":" + colunaInicio + "-" + linhaFim + ":" + colunaFim;
	}
}
