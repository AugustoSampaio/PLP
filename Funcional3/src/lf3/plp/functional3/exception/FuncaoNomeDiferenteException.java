package lf3.plp.functional3.exception;

public class FuncaoNomeDiferenteException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public FuncaoNomeDiferenteException() {
		super("Nome da fun��o n�o casou.");
	}
	
}
