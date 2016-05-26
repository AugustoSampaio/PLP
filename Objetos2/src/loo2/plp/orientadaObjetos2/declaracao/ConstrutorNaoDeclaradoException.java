package loo2.plp.orientadaObjetos2.declaracao;

import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;

public class ConstrutorNaoDeclaradoException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 * @param id Identificador representando uma vari�vel.
	 */
	public ConstrutorNaoDeclaradoException(Id id){
		super("Construtor da classe " + id + " n�o declarado.");
	}
}
