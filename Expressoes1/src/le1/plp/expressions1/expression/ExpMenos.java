package le1.plp.expressions1.expression;

import le1.plp.expressions1.util.Tipo;
import le1.plp.expressions1.util.TipoPrimitivo;
import le1.plp.expressions2.memory.AmbienteCompilacao;
import le1.plp.expressions2.memory.AmbienteExecucao;

/**
* Um objeto desta classe representa uma Expressao de menos unario.
*/

public class ExpMenos extends ExpUnaria{

	/**
	 * Controi uma Expressao de menos unario com expressao especificada
	 *
	 * @param exp Expressao cuja avaliacao resulta <code>ValorInteiro</code>.
	 */
	public ExpMenos(Expressao exp){
		super(exp, "-");
	}

	/**
	 * Retorna o valor da Expressao de menos unario.
	 * 
	 * @param amb
	 *            o ambiente de execu��o.
	 */
	public Valor avaliar(AmbienteExecucao amb) {
		return new ValorInteiro(- ((ValorInteiro)getExp().avaliar(amb)).valor());
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
		return (getExp().getTipo(amb).eInteiro());
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
		return TipoPrimitivo.INTEIRO;
	}
}
