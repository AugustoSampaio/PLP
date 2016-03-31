package plp.orientadaObjetos1.excecao.declaracao;

import plp.expressions2.expression.Id;


/**
 * Exceção lançada quando um procedimento está sendo declarado
 * novamente.
 */
public class ProcedimentoJaDeclaradoException extends Exception {
    /**
     * Construtor
     * @param id Identificador representando um procedimento.
     */
      public ProcedimentoJaDeclaradoException(Id id) {
        super("Procedimento " + id + " já declarado.");
      }
}
