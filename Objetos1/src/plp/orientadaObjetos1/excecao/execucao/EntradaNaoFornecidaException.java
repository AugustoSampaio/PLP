package plp.orientadaObjetos1.excecao.execucao;

/**
 * Exceção lançada quando uma entrada esperada não é fornecida.
 */
public class EntradaNaoFornecidaException extends Exception {
    /**
     * Construtor.
     */
    public EntradaNaoFornecidaException() {
        super("Forneca os valores de entrada do programa!");
    }

}
