package lf3.plp.functional3.util;

public class ListaVaziaException extends RuntimeException {

	private static final long serialVersionUID = -2863294396766306479L;

	/**
	 * Cria um exception informando que a operacao est� sendo realizada com a
	 * lista vazia e eh invalido.
	 */
	public ListaVaziaException() {
		super("N�o � poss�vel realizar a opera��o com lista vazia!");
	}
}
