package loo1.plp.expressions2.memory;

public class IdentificadorNaoDeclaradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdentificadorNaoDeclaradoException(String msg){
		super(msg);
	}
	
	public IdentificadorNaoDeclaradoException(){
		super();
	}
}

