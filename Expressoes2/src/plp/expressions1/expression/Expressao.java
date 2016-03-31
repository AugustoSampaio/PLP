package plp.expressions1.expression;

import plp.expressions1.util.Tipo;

/** 
 * Uma expressao &eacute; a unidade basica na Linguagem de Expressoes.
 */
public interface Expressao {

	/**
	 * Avalia a expressao  retornando seu Valor.
	 */
	Valor avaliar();

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 *
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 */
	boolean checaTipo();

	/**
	 * Retorna os tipos possiveis desta expressao.
	 *
	 * @return os tipos possiveis desta expressao.
	 */
	Tipo getTipo();

}
