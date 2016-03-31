package plp.functional3.exception;

public class PadraoNaoEncontradoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public PadraoNaoEncontradoException() {
		super("Nenhum padrão casou.");
	}
	
}
