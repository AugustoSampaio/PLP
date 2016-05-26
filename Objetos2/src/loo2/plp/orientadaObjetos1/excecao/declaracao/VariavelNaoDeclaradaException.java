package loo2.plp.orientadaObjetos1.excecao.declaracao;

import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
/**
 * Exce��o lan�ada quando uma vari�vel que est� sendo referenciada
 * n�o foi declarada anteriormente.
 */
public class VariavelNaoDeclaradaException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
    /**
     * Construtor.
     * @param id Identificador representando a vari�vel.
     */
      public VariavelNaoDeclaradaException(Id id){
        super("Variavel " + id + " nao declarada.");
      }

}
