package plp.orientadaObjetos2.declaracao;

import plp.orientadaObjetos1.expressao.leftExpression.Id;

public class ConstrutorNaoDeclaradoException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 * @param id Identificador representando uma variável.
	 */
	public ConstrutorNaoDeclaradoException(Id id){
		super("Construtor da classe " + id + " não declarado.");
	}
}
