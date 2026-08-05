package lf2.plp.expressions2.memory;

/**
 * Informações de um escopo individual, criadas pelo parser e armazenadas
 * no nó da AST. Passadas ao MetadadosDepuracao durante a verificação de tipos.
 */
public class InfoEscopo {
	private final TrechoCodigoFonte trechoCodigoFonte;
	private final String escopo;

	public InfoEscopo(TrechoCodigoFonte trechoCodigoFonte, String escopo) {
		this.trechoCodigoFonte = trechoCodigoFonte;
		this.escopo = escopo;
	}

	public TrechoCodigoFonte getTrechoCodigoFonte() { return trechoCodigoFonte; }
	public String getEscopo() { return escopo; }
}
