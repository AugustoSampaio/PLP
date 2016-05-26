package loo2.plp.orientadaObjetos1.excecao.declaracao;

import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
/**
 * Exce��o lan�ada quando uma vari�vel est� sendo declarada mais de uma
 * vez num mesmo escopo.
 */
public class VariavelJaDeclaradaException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
    /**
     * Construtor.
     * @param id Identificador representando uma vari�vel.
     */
    public VariavelJaDeclaradaException(Id id){
        super("Variavel " + id + " ja declarada.");
    }
}
