package plp.orientadaObjetos1.excecao.declaracao;

import plp.expressions2.expression.Id;


/**
 * Exceção lançada quando o objeto que está sendo referenciado não
 * foi declarado.
 */
public class ObjetoNaoDeclaradoException extends Exception {
    /**
     * Construtor.
     * @param id Identificador representando o objeto.
     */
    public ObjetoNaoDeclaradoException(Id id) {
        super("Objeto" + id + " não declarado.");
    }
}