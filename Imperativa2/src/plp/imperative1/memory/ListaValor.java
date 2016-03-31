package plp.imperative1.memory;


import plp.expressions2.expression.Valor;
import plp.imperative1.util.Lista;

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
     * @param valor Cabeça da tail.
     */
    public ListaValor(Valor valor){
        super(valor, new ListaValor());
    }

    /**
     * Construtor
     * @param valor Cabeça da tail.
     * @param listaValor Cauda da tail.
     */
    public ListaValor(Valor valor, ListaValor listaValor){
        super(valor, listaValor);
    }

    /**
     * Método utilizado para ir enfileirando os valores.
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
