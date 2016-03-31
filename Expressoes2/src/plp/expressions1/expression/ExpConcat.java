package plp.expressions1.expression;

import plp.expressions1.util.Tipo;
import plp.expressions1.util.TipoPrimitivo;

/**
* Um objeto desta classe representa uma Expressao de Concatenacao entre
* objetos <code>ValorString</code>
*/
public class ExpConcat extends ExpBinaria{
  

	/**
	 * Controi uma Expressao de Concatenacao com as sub-expressoes especificadas.
	 * Estas sub-expressoes devem ser tais que a avaliacao das mesmas resulta
	 * em <code>ValorString</code>
	 *
	 * @param esq Expressao da esquerda
	 * @param dir Expressao da direita
	 */
	public ExpConcat(Expressao esq, Expressao dir){
		super(esq, dir, "++");
	} 

	/**
	 * Retorna o valor da Expressao de Concatenacao
	 */
	public Valor avaliar(){
		return new ValorString(
					( (ValorString) getEsq().avaliar()).valor() +
					( (ValorString) getDir().avaliar()).valor()
		);
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 *
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 */
	protected boolean checaTipoElementoTerminal() {
		return (getEsq().getTipo().eString() && getDir().getTipo().eString());
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 *
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo() {
		return TipoPrimitivo.STRING;
	}

}
