package le1.plp.expressions1.expression;

import le1.plp.expressions1.util.Tipo;
import le1.plp.expressions1.util.TipoPrimitivo;
import le1.plp.expressions2.memory.AmbienteCompilacao;
import le1.plp.expressions2.memory.AmbienteExecucao;

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
	 * 
	 * @param amb
	 *            o ambiente de execu��o.
	 */
	public Valor avaliar(AmbienteExecucao amb) {
	return new ValorBooleano(
				((ValorBooleano)getEsq().avaliar(amb)).valor() ||
				((ValorBooleano)getDir().avaliar(amb)).valor() );
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 *
	 * @param amb
	 *            o ambiente de compila��o.
	 *
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *         <code>false</code> caso contrario.
	 */
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) {
		return (getEsq().getTipo(amb).eBooleano() && getDir().getTipo(amb).eBooleano());
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * 
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.BOOLEANO;
	}

}
