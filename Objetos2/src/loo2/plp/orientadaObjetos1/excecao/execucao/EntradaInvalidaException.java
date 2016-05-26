package loo2.plp.orientadaObjetos1.excecao.execucao;
/**
 * Exce��o lan�ada quando uma entrada fornecida durante a execu��o � inv�lida.
 */
public class EntradaInvalidaException extends Exception {
    /**
     * Construtor Default.
     */
    public EntradaInvalidaException() {
        super("A entrada fornecida n�o pode ser atribu�da a um identificador desse tipo!");
    }

    /**
     * Construtor com uma mensagem como par�metro.
     * @param mensagem Mensagem com o erro.
     */
    public EntradaInvalidaException(String mensagem) {
        super(mensagem);
    }
}