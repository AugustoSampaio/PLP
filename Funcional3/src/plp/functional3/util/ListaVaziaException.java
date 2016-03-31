package plp.functional3.util;

public class ListaVaziaException extends RuntimeException {

	private static final long serialVersionUID = -2863294396766306479L;

	/**
	 * Cria um exception informando que a operacao está sendo realizada com a
	 * lista vazia e eh invalido.
	 */
	public ListaVaziaException() {
		super("Não é possível realizar a operação com lista vazia!");
	}
}
