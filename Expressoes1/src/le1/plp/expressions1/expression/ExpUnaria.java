package le1.plp.expressions1.expression;

import le1.plp.expressions2.memory.AmbienteCompilacao;

/**
 * Uma expressao unaria contem uma expressao e um operador sobre a mesma.
 */
public abstract class ExpUnaria implements Expressao {

	/**
	 * Expressao contida pela expressao unaria
	 */
	private Expressao exp;

	/**
	 * Representacao do operador desta expressao unaria.
	 */
	private String operador;

	/**
	 * Construtor da classe.
	 * 
	 * @param exp
	 *            expressao contida pela expressao unaria.
	 */
	public ExpUnaria(Expressao exp, String operador) {
		this.exp = exp;
		this.operador = operador;
	}

	/**
	 * Retorna a expressao contida pela expressao unaria
	 * 
	 * @return a expressao contida pela expressao unaria
	 */
	public Expressao getExp() {
		return exp;
	}

	/**
	 * Retorna a representacao do operador desta expressao unaria.
	 * 
	 * @return a representacao do operador desta expressao unaria.
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
		return String.format(" %s %s", operador, exp);
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
		return getExp().checaTipo(amb) && this.checaTipoElementoTerminal(amb);
	}

	/**
	 * M�todo 'template' que ser� implementado nas subclasses para checar o tipo
	 * do head terminal
	 */
	protected abstract boolean checaTipoElementoTerminal(AmbienteCompilacao amb);
}
