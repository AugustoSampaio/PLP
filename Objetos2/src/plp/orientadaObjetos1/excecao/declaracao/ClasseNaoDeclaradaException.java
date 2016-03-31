package plp.orientadaObjetos1.excecao.declaracao;

import plp.expressions2.expression.Id;


/**
 * Exceção lançada quando uma classe que está sendo referenciada
 * não foi declarada anteriormente.
 */
public class ClasseNaoDeclaradaException extends Exception {
    /**
     * Construtor
     * @param id Identificador representando a classe.
     */
    public ClasseNaoDeclaradaException(Id id) {
        super("Classe " + id + " não declarada.");
    }
}