package plp.orientadaObjetos1.excecao.declaracao;

import plp.expressions2.expression.Id;


/**
 * Exceçao lançada quando um procedimento que está sendo referenciado
 * não foi declarado.
 */
public class ProcedimentoNaoDeclaradoException extends Exception {
    /**
     * Construtor
     * @param id Identificador representando o procedimento.
     */
    public ProcedimentoNaoDeclaradoException(Id id) {
        super("Procedimento " + id + " nao declarado.");
    }
}
