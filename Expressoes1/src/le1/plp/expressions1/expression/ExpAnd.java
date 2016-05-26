package le1.plp.expressions1.expression;

import le1.plp.expressions1.util.Tipo;
import le1.plp.expressions1.util.TipoPrimitivo;
import le1.plp.expressions2.memory.AmbienteCompilacao;
import le1.plp.expressions2.memory.AmbienteExecucao;

/**
 * Um objeto desta classe representa uma Expressao de Conjuncao logica.
 */
public class ExpAnd extends ExpBinaria{

	/**
	 * Controi uma Expressao de Conjuncao logica  com as sub-expressoes
	 * especificadas. Estas devem ser tais que sua avaliacao retorna
	 * <code>ValorBooleano</code>
	 *
	 * @param esq Expressao da esquerda
	 * @param dir Expressao da direita
	 */
	public ExpAnd(Expressao esq, Expressao dir) {
		super(esq, dir, "and");
	}


	/**
	 * Retorna o valor da Expressao de Conjuncao Logica
	 * 
	 * @param amb
	 *            o ambiente de execu��o.
	 */
	 public Valor avaliar(AmbienteExecucao amb){
		return new ValorBooleano(
					((ValorBooleano)getEsq().avaliar(amb)).valor() &&
					((ValorBooleano)getDir().avaliar(amb)).valor()
		);
	}
 
	 
	 
	/**
	 * Realiza a verificacao de tipos desta expressao.
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 */
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) {
		return (getEsq().getTipo(amb).eBooleano() && getDir().getTipo(amb).eBooleano());
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 *
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.BOOLEANO;
	}

}
