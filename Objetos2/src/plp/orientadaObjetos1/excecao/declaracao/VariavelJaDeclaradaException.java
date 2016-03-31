package plp.orientadaObjetos1.excecao.declaracao;

import plp.orientadaObjetos1.expressao.leftExpression.Id;
/**
 * Exceção lançada quando uma variável está sendo declarada mais de uma
 * vez num mesmo escopo.
 */
public class VariavelJaDeclaradaException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
    /**
     * Construtor.
     * @param id Identificador representando uma variável.
     */
    public VariavelJaDeclaradaException(Id id){
        super("Variavel " + id + " ja declarada.");
    }
}
