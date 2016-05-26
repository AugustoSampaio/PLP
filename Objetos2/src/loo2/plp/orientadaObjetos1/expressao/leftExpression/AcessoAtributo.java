package loo2.plp.orientadaObjetos1.expressao.leftExpression;

import loo2.plp.orientadaObjetos1.expressao.Expressao;
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
     * Obt�m o identificador.
     * @return o identificador.
     */
    public Id getId(){
        return id;
    }
    /**
     * Obt�m uma expressao
     * @return uma express�o.
     */
    public abstract Expressao getExpressaoObjeto();
}