package li1.plp.expressions2.memory;

public class IdentificadorJaDeclaradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IdentificadorJaDeclaradoException(String msg){
		super(msg);
	}
	public IdentificadorJaDeclaradoException(){
		super();
	}
}