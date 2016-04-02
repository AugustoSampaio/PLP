package plp.expressions1.expression;

import plp.expressions1.util.Tipo;
import plp.expressions1.util.TipoPrimitivo;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;

/**
 * Um objeto desta classe representa uma Expressao de tamanho de String.
 */
public class ExpLength extends ExpUnaria {

	/**
	 * Controi uma Expressao de tamanho  com a expressao especificada
	 * assume-se que <code>exp</code> &eacute; uma expressao cuja avaliacao
	 * resulta num <code>ValorString</code>
	 */
	public ExpLength(Expressao exp) {
		super(exp, "length");
	}

	/**
	 * Retorna o valor da Expressao de tamanho de uma String.
	 * 
	 * @param amb
	 *            o ambiente de execução.
	 */
	public Valor avaliar(AmbienteExecucao amb) {
		return new ValorInteiro (((ValorString)getExp().avaliar(amb)).valor().length());
	}
	
	/**
	 * Realiza a verificacao de tipos desta expressao.
	 *
	 * @param amb
	 *            o ambiente de compilação.
	 *
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *         <code>false</code> caso contrario.
	 */
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) {
		return (getExp().getTipo(amb).eString());
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compilação.
	 * 
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.INTEIRO;
	}

}
