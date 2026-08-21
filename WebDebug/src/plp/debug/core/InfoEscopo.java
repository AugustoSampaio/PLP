package plp.debug.core;

/**
 * Informações de um escopo, criadas pelo parser do WebDebug no ponto
 * sintático exato em que o escopo começa e armazenadas no nó da AST
 * (subclasse Debug). Repassadas ao {@link SnapshotRecorder} durante a
 * verificação de tipos.
 */
public final class InfoEscopo {

	private final TrechoCodigoFonte trechoCodigoFonte;
	private final String escopo;
	private final String nome;

	public InfoEscopo(TrechoCodigoFonte trechoCodigoFonte, String escopo) {
		this(trechoCodigoFonte, escopo, null);
	}

	public InfoEscopo(TrechoCodigoFonte trechoCodigoFonte, String escopo, String nome) {
		this.trechoCodigoFonte = trechoCodigoFonte;
		this.escopo = escopo;
		this.nome = nome;
	}

	public TrechoCodigoFonte getTrechoCodigoFonte() {
		return trechoCodigoFonte;
	}

	/** Rótulo do escopo: "let-in", "block", "class", "procedure", ... */
	public String getEscopo() {
		return escopo;
	}

	/** Nome do construtor, quando houver (classe, procedimento, função). */
	public String getNome() {
		return nome;
	}

	/**
	 * Devolve uma cópia com o nome informado. Usada quando o nome só é
	 * conhecido na verificação de tipos (ex.: nome do procedimento).
	 */
	public InfoEscopo comNome(String novoNome) {
		return new InfoEscopo(trechoCodigoFonte, escopo, novoNome);
	}

	/** Chave de identidade do escopo, usada para deduplicar revisitas. */
	public String chave() {
		return (escopo == null ? "" : escopo) + "|" + (nome == null ? "" : nome) + "|"
				+ (trechoCodigoFonte == null ? "" : trechoCodigoFonte.chave());
	}
}
