package plp.orientadaObjetos1.excecao.declaracao;

import plp.expressions2.expression.Id;


/**
 * Exceção lançada quando a classe que está sendo declarada, já o foi
 * anteriormente.
 */
public class ClasseJaDeclaradaException extends Exception {
    /**
     * Construtor
     * @param id Identificador representando a classe.
     */
    public ClasseJaDeclaradaException(Id id) {
        super("Classe " + id + " já declarada.");
    }
}
