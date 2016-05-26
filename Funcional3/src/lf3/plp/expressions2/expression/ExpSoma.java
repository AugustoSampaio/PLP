package lf3.plp.expressions2.expression;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions1.util.TipoPrimitivo;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Um objeto desta classe representa uma Expressao de Soma.
 */
public class ExpSoma extends ExpBinaria {

	/**
	 * Controi uma Expressao de Soma com as sub-expressoes especificadas.
	 * Assume-se que estas sub-expressoes resultam em <code>ValorInteiro</code> 
	 * quando avaliadas.
	 * @param esq Expressao da esquerda
	 * @param dir Expressao da direita
	 */
	public ExpSoma(Expressao esq, Expressao dir) {
		super(esq, dir, "+");
	}

	/**
	 * Retorna o valor da Expressao de Soma
	 */
	public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return new ValorInteiro(
			((ValorInteiro) getEsq().avaliar(amb)).valor() +
			((ValorInteiro) getDir().avaliar(amb)).valor() );
	}
	
	/**
	 * Realiza a verificacao de tipos desta expressao.
	 *
	 * @param ambiente o ambiente de compila��o.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          nao declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          declarado mais de uma vez no mesmo bloco do ambiente.
	 */
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return (getEsq().getTipo(ambiente).eInteiro() && getDir().getTipo(ambiente).eInteiro());
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 *
	 * @param ambiente o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao ambiente) {
		return TipoPrimitivo.INTEIRO;
	}
	
	@Override
	public ExpBinaria clone() {
		return new ExpSoma(esq.clone(), dir.clone());
	}
}
