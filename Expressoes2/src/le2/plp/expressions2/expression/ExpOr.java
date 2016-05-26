package le2.plp.expressions2.expression;

import le2.plp.expressions1.util.Tipo;
import le2.plp.expressions1.util.TipoPrimitivo;
import le2.plp.expressions2.memory.AmbienteCompilacao;
import le2.plp.expressions2.memory.AmbienteExecucao;
import le2.plp.expressions2.memory.VariavelJaDeclaradaException;
import le2.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Um objeto desta classe representa uma Disjuncao Logica.
 */
public class ExpOr extends ExpBinaria {

	/**
	 * Controi uma Expressao de disjuncao logica  com as sub-expressoes
	 * especificadas.Estas devem ser tais que sua avaliacao resulta em
	 * <code>ValorBooleano</code>
	 *
	 * @param esq Expressao da esquerda
	 * @param dir Expressao da direita
	 */
	public ExpOr(Expressao esq, Expressao dir){
		super(esq, dir, "or");
	}


	/**
	 * Retorna o valor da Expressao de disjuncao logica
	 */
	public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
	return new ValorBooleano(
				((ValorBooleano)getEsq().avaliar(amb)).valor() ||
				((ValorBooleano)getDir().avaliar(amb)).valor() );
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
		return (getEsq().getTipo(ambiente).eBooleano() && getDir().getTipo(ambiente).eBooleano());
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 *
	 * @param ambiente o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao ambiente) {
		return TipoPrimitivo.BOOLEANO;
	}
	
	@Override
	public ExpBinaria clone() {
		return new ExpOr(esq.clone(), dir.clone());
	}
}
