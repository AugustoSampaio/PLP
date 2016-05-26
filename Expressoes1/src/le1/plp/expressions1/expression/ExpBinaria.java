package le1.plp.expressions1.expression;

import le1.plp.expressions2.memory.AmbienteCompilacao;

/**
 * Uma expressao binaria contem duas expressoes e um operador. Ha uma ordem
 * definida entre estas sub-expressoes
 */
public abstract class ExpBinaria implements Expressao {

	/**
	 * Expressao da esquerda
	 */
	private Expressao esq;

	/**
	 * Expressao da direita
	 */
	private Expressao dir;

	/**
	 * Operador desta expressao binaria
	 */
	private String operador;

	/**
	 * Construtor da classe.
	 * 
	 * @param esq
	 *            a expressao da esquerda.
	 * @param esq
	 *            a expressao da esquerda.
	 * @param esq
	 *            a expressao da esquerda.
	 */
	public ExpBinaria(Expressao esq, Expressao dir, String operador) {
		this.esq = esq;
		this.dir = dir;
		this.operador = operador;
	}

	/**
	 * Retorna a expressao da esquerda
	 * 
	 * @return a expressao da esquerda
	 */
	public Expressao getEsq() {
		return esq;
	}

	/**
	 * Retorna a expressao da direita
	 * 
	 * @return a expressao da direita
	 */
	public Expressao getDir() {
		return dir;
	}

	/**
	 * Retorna o operador desta expressao binaria
	 * 
	 * @return o operador desta expressao binaria
	 */
	public String getOperador() {
		return operador;
	}

	/**
	 * Retorna uma representacao String desta expressao. Util para depuracao.
	 * 
	 * @return uma representacao String desta expressao.
	 */
	@Override
	public String toString() {
		return String.format("%s %s %s", esq, operador, dir);
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se os tipos das subexpressoes sao validos;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacao amb) {
		boolean result;
		if (!getEsq().checaTipo(amb) || !getDir().checaTipo(amb)) {
			result = false;
		} else {
			result = this.checaTipoElementoTerminal(amb);
		}
		return result;
	}

	/**
	 * M�todo 'template' que ser� implementado nas subclasses para checar o tipo
	 * do head terminal
	 * @param amb
	 *            o ambiente de compila��o.
	 */
	protected abstract boolean checaTipoElementoTerminal(AmbienteCompilacao amb);

}
