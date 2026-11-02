package li1.plp.imperative1.util;

/**
 * Falha no acesso ao banco SQLite (arquivo inacessível, erro de SQL).
 * Interrompe a execução do programa.
 */
public class ErroBancoException extends RuntimeException {

    public ErroBancoException(String msg, Throwable causa) {
        super(msg, causa);
    }
}