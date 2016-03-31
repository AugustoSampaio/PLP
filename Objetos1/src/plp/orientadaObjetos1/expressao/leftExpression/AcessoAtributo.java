package plp.orientadaObjetos1.expressao.leftExpression;

import plp.orientadaObjetos1.expressao.Expressao;
/**
 * Classe que representa um acesso de atributo.
 */
public abstract class AcessoAtributo implements LeftExpression{

    /**
     * Identificador.
     */
    private Id id;
    /**
     * Construtor
     * @param id Identificador
     */
    public AcessoAtributo(Id id) {
        this.id = id;
    }
    /**
     * Obtém o identificador.
     * @return o identificador.
     */
    public Id getId(){
        return id;
    }
    /**
     * Obtém uma expressao
     * @return uma expressão.
     */
    public abstract Expressao getExpressaoObjeto();
}