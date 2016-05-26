package loo1.plp.orientadaObjetos1.util;

import loo1.plp.orientadaObjetos1.util.Tipo;

/**
 * Lista encadeada contendo os tipos
 */
public class ListaTipo {
    /**
     * O tipo que representa a cabe�a dessa tail.
     */
    private Tipo tipo;

    /**
     * Lista de tipos que representa a cauda dessa tail.
     */
    private ListaTipo listaTipo;

    /**
     * Construtor default.
     */
    public ListaTipo(){
        tipo = null;
        listaTipo = null;
    }

    /**
     * Construtor
     * @param tipo Cabe�a da tail
     */
    public ListaTipo(Tipo tipo){
        this.tipo = tipo;
        this.listaTipo = new ListaTipo();
    }
    /**
     * Construtor
     * @param tipo Cabe�a da tail
     * @param listaTipo Cauda da tail
     */
    public ListaTipo(Tipo tipo, ListaTipo listaTipo){
        this.tipo = tipo;
        this.listaTipo = listaTipo;
    }

    /**
     * Tamanho da tail
     * @return tamanho da tail.
     */
    public int length() {
        int resposta;
        if ( listaTipo  == null) {
            resposta  = 0;
        }
        else {
            resposta = 1 + listaTipo.length();
        }
        return resposta;
    }

    /**
     * Cabe�a da tail.
     * @return a cabe�a dessa tail de tipos, representada por um Tipo.
     */
    public Tipo head() {
        return tipo;
    }

    /**
     * Cauda da tail, contendo uma outra tail de tipos.
     * @return Cauda da tail, contendo uma outra tail de tipos.
     */
    public ListaTipo tail() {
        return listaTipo;
    }

    /**
     * Representa��o textual dessa tail de tipos.
     * @return representa��o textual dessa tail de tipos.
     */
    public String toString(){
        StringBuffer resposta = new StringBuffer();
        resposta = this.getString(resposta);
        return resposta.toString();
    }

    /**
     * M�todo auxiliar que captura todo o conte�do da tail.
     * @param resposta StringBuffer onde vai sendo impresso o resultado.
     * @return todo o conte�do da tail.
     */
    private StringBuffer getString(StringBuffer resposta){
        if(tipo != null) {
            if ( listaTipo != null) {
                resposta = listaTipo.getString(resposta);
            }
            resposta.append(tipo.toString()+" ");
        }
        return resposta;
    }

}
