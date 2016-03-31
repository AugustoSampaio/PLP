package plp.orientadaObjetos1.excecao.declaracao;

import plp.orientadaObjetos1.expressao.leftExpression.Id;
/**
 * Exceção lançada quando uma variável que está sendo referenciada
 * não foi declarada anteriormente.
 */
public class VariavelNaoDeclaradaException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
    /**
     * Construtor.
     * @param id Identificador representando a variável.
     */
      public VariavelNaoDeclaradaException(Id id){
        super("Variavel " + id + " nao declarada.");
      }

}
