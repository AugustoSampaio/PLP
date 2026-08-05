package lf1.plp.expressions2.memory;

/**
 * Posição de um trecho no código-fonte.
 * Criado pelo parser (.jj) no momento da análise sintática.
 */
public class TrechoCodigoFonte {
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

	public int getLinhaInicio() { return linhaInicio; }
	public int getColunaInicio() { return colunaInicio; }
	public int getLinhaFim() { return linhaFim; }
	public int getColunaFim() { return colunaFim; }
}
