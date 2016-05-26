package lf3.plp.expressions2.expression;

import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Uma expressao binaria contem duas expressoes e um operador. Ha uma ordem
 * definida entre estas sub-expressoes
 */
public abstract class ExpBinaria implements Expressao {

	/**
	 * Expressao da esquerda
	 */
	protected Expressao esq;

	/**
	 * Expressao da direita
	 */
	protected Expressao dir;

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
	 *            a expressao da direita.
	 * @param operador
	 *            o operador desta expressao binaria.
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
	 * @exception VariavelJaDeclaradaException
	 *                se a vari�vel j� est� declarada no ambiente
	 * @exception VariavelNaoDeclaradaException
	 *                se a vari�vel ainda n�o foi declarada no ambiente.
	 */
	public boolean checaTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		boolean result = true;
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
	 */
	protected abstract boolean checaTipoElementoTerminal(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException;
	
	
	public Expressao reduzir(AmbienteExecucao ambiente) {
		this.esq = this.esq.reduzir(ambiente);
		this.dir = this.dir.reduzir(ambiente);
		
		return this;
	}
	
	public abstract ExpBinaria clone();
}
