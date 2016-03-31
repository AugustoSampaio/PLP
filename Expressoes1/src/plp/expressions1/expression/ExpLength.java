package plp.expressions1.expression;

import plp.expressions1.util.Tipo;
import plp.expressions1.util.TipoPrimitivo;

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
	 * Retorna o valor da Expressao de tamanho.
	 */
	public Valor avaliar(){
		return new ValorInteiro (((ValorString)getExp().avaliar()).valor().length());
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 *
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 */
	protected boolean checaTipoElementoTerminal() {
		return (getExp().getTipo().eString());
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 *
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo() {
		return TipoPrimitivo.INTEIRO;
	}

}
