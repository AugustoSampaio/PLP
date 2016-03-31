package plp.orientadaObjetos1.excecao.execucao;
/**
 * Exceção lançada quando uma entrada fornecida durante a execução é inválida.
 */
public class EntradaInvalidaException extends Exception {
    /**
     * Construtor Default.
     */
    public EntradaInvalidaException() {
        super("A entrada fornecida não pode ser atribuída a um identificador desse tipo!");
    }

    /**
     * Construtor com uma mensagem como parâmetro.
     * @param mensagem Mensagem com o erro.
     */
    public EntradaInvalidaException(String mensagem) {
        super(mensagem);
    }
}