package le2.plp.expressions2.memory;

import le2.plp.expressions2.expression.Id;

public class VariavelNaoDeclaradaException extends IdentificadorNaoDeclaradoException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VariavelNaoDeclaradaException(Id id){
		super("Vari�vel " + id + " n�o declarada.");
	}
}
