package loo1.plp.orientadaObjetos1.excecao.declaracao;

import loo1.plp.expressions2.expression.Id;


/**
 * Exce��o lan�ada quando uma classe que est� sendo referenciada
 * n�o foi declarada anteriormente.
 */
public class ClasseNaoDeclaradaException extends Exception {
    /**
     * Construtor
     * @param id Identificador representando a classe.
     */
    public ClasseNaoDeclaradaException(Id id) {
        super("Classe " + id + " n�o declarada.");
    }
}