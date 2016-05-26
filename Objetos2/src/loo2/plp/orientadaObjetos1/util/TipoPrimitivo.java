package loo2.plp.orientadaObjetos1.util;

import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;

/**
 * Classe que representa os possiveis tipos de uma expressao.
 */
public class TipoPrimitivo implements Tipo {

    /**
     * Indica que a expressao associada &eacute; inteira.
     */
    public static final int INTEIRO = 1;

    /**
     * Indica que a expressao associada &eacute; booleana.
     */
    public static final int BOOLEANO = 2;

    /**
     * Indica que a expressao associada &eacute; string.
     */
    public static final int STRING = 4;

    /**
     * Constante de tipo inteira.
     */
    public static final Tipo TIPO_INTEIRO = new TipoPrimitivo(INTEIRO);


    /**
     * Constante de tipo booleana.
     */
    public static final Tipo TIPO_BOOLEANO = new TipoPrimitivo(BOOLEANO);

    /**
     * Constante de tipo string.
     */
    public static final Tipo TIPO_STRING = new TipoPrimitivo(STRING);

    /**
     * Constante de tipo identificador inteiro.
     */
    public static final Id TIPO_ID_int = new Id("int");

    /**
     * Constante de tipo identificador string.
     */
    public static final Id TIPO_ID_string = new Id("string");

    /**
     * Constante de tipo identificador booleno.
     */
    public static final Id TIPO_ID_boolean = new Id("boolean");

    /**
     * O tipo da expressao associada.
     */
    private int tipo;

    /**
     * Construtor da classe.
     *
     * @param tipo o tipo da expressao associada.
     * @see #INTEIRO
     * @see #BOOLEANO
     * @see #STRING
     */
    public TipoPrimitivo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna o tipo da expressao associada.
     *
     * @return o tipo da expressao associada.
     * @see #INTEIRO
     * @see #BOOLEANO
     * @see #STRING
     */
     public Id getTipo(){

        Id resposta = new Id("undefined");
        switch(tipo) {
            case(INTEIRO):
                resposta = TIPO_ID_int;
                break;
            case(BOOLEANO):
                resposta = TIPO_ID_boolean;
                break;
            case(STRING):
                resposta = TIPO_ID_string;
                break;
        }
        return resposta;
    }

    /**
     * Indica se esta expressao &eacute; inteira.
     *
     * @return <code>true</code> se esta expressao for inteira;
     *          <code>false</code> caso contrario.
     */
    public boolean eInteiro() {
        return tipo == INTEIRO;
    }

    /**
     * Indica se esta expressao &eacute; booleana.
     *
     * @return <code>true</code> se esta expressao for booleana;
     *          <code>false</code> caso contrario.
     */
    public boolean eBooleano() {
        return tipo == BOOLEANO;
    }

    /**
     * Indica se esta expressao &eacute; string.
     *
     * @return <code>true</code> se esta expressao for string;
     *          <code>false</code> caso contrario.
     */
    public boolean eString() {
        return tipo == STRING;
    }

    /**
    *
    * Por questao de simplificacao, este m�todo foi implementado para unificar TipoPrimitivo e
    * TipoClasse em uma �nica interface: Tipo
    *
    */
    public boolean eValido(AmbienteCompilacaoOO1 ambiente){
        return tipo == STRING || tipo == BOOLEANO || tipo == INTEIRO;
    }

    /**
     * Indica se esta expressao &eacute; um tipo v�lido.
     *
     * @return <code>true</code> se esta expressao for um tipo v�lido;
     *          <code>false</code> caso contrario.
     */
    public boolean eValido() {
        return tipo == STRING || tipo == BOOLEANO || tipo == INTEIRO;
    }

    /**
     * Compara este tipo com o tipo dado.
     *
     * @return <code>true</code> se se tratarem do mesmo tipo;
     *          <code>false</code> caso contrario.
     */
    public boolean equals(Object obj) {
        if (obj instanceof TipoPrimitivo) {
            return ((TipoPrimitivo) obj).tipo == this.tipo;
        }
        return false;
    }
    /**
     * Retorna a descri��o textual do tipo.
     * @return  a descri��o textual do tipo.
     */
    public String toString() {
        String resposta = "undefined";
        switch(tipo) {
            case(INTEIRO):
                resposta = "int";
                break;
            case(BOOLEANO):
                resposta = "boolean";
                break;
            case(STRING):
                resposta = "string";
                break;
        }
        return resposta;
    }

}
