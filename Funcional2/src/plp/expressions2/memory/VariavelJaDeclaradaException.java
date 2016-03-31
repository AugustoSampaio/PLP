package plp.expressions2.memory;

import plp.expressions2.expression.Id;

public class VariavelJaDeclaradaException extends IdentificadorJaDeclaradoException{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VariavelJaDeclaradaException(Id id){
		super("Variável " + id + " já declarada.");
	}
}
