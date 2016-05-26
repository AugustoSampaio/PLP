package loo2.plp.orientadaObjetos1.memoria.colecao;


import loo2.plp.imperative1.util.Lista;
import loo2.plp.orientadaObjetos1.expressao.valor.Valor;
/**
 * Lista encadeada com os valores.
 */
public class ListaValor extends Lista<Valor>{
    /**
     * Construtor default.
     */
    public ListaValor(){
    
    }
    /**
     * Construtor
     * @param valor Cabe�a da tail.
     */
    public ListaValor(Valor valor){
        super(valor, new ListaValor());
    }

    /**
     * Construtor
     * @param valor Cabe�a da tail.
     * @param listaValor Cauda da tail.
     */
    public ListaValor(Valor valor, ListaValor listaValor){
        super(valor, listaValor);
    }

    /**
     * M�todo utilizado para ir enfileirando os valores.
     * @param valor O valor a ser adicionado a tail de valores.
     */
    public void write(Valor valor) {
        if(getHead() == null) {
            this.head = valor;
            this.tail = new ListaValor();
        }
        else {
            ((ListaValor)getTail()).write(valor);
        }
    }
	
}
