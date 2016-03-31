package plp.expressions1.expression;

import plp.expressions1.util.Tipo;
import plp.expressions1.util.TipoPrimitivo;

/**
 * Um objeto desta classe representa uma Expressao de Igualdade entre
 * Expressoes cuja avaliacao resulta num mesmo valor primitivo.
 */
public class ExpEquals extends ExpBinaria{

	/**
	 * Controi uma Expressao de Igualdade com as sub-expressoes especificadas.
	 * Assume-se que estas sub-expressoes resultam num mesmo valor primitivo
	 * quando avaliadas.
	 * @param esq Expressao da esquerda
	 * @param dir Expressao da direita
	 */
	public ExpEquals(Expressao esq, Expressao dir){
		super(esq, dir, "==");
	}

	/**
	 * Retorna o valor da Expressao de Igualdade
	 */
	public Valor avaliar(){
		return(
			new ValorBooleano(((ValorConcreto)getEsq().avaliar()).isEquals((ValorConcreto)getDir().avaliar()))
		);
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 *
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 */
	protected boolean checaTipoElementoTerminal() {
		return (getEsq().getTipo().eIgual(getDir().getTipo()));
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 *
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo() {
		return TipoPrimitivo.BOOLEANO;
	}

}
