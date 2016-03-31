package plp.functional3.exception;

public class FuncaoNomeDiferenteException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public FuncaoNomeDiferenteException() {
		super("Nome da função não casou.");
	}
	
}
