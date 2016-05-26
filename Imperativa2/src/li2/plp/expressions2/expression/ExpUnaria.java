package li2.plp.expressions2.expression;

import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Uma expressao unaria contem uma expressao e um operador sobre a mesma.
 */
public abstract class ExpUnaria implements Expressao {

	/**
	 * Expressao contida pela expressao unaria
	 */
	protected Expressao exp;

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
	 * Realiza a verificacao de tipos desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se o tipo da subexpressao &eacute; valido;
	 *         <code>false</code> caso contrario.
	 * @exception VariavelJaDeclaradaException
	 *                se a vari�vel j� est� declarada no ambiente
	 * @exception VariavelNaoDeclaradaException
	 *                se a vari�vel ainda n�o foi declarada no ambiente.
	 */
	public boolean checaTipo(AmbienteCompilacao amb)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		return getExp().checaTipo(amb) && this.checaTipoElementoTerminal(amb);
	}

	@Override
	public String toString() {
		return String.format("%s %s", operador, exp);
	}

	/**
	 * M�todo 'template' que ser� implementado nas subclasses para checar o tipo
	 * do head terminal
	 */
	protected abstract boolean checaTipoElementoTerminal(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException;
	
	public Expressao reduzir(AmbienteExecucao ambiente) {
		this.exp = this.exp.reduzir(ambiente);
		
		return this;
	}
	
	public abstract ExpUnaria clone();
}