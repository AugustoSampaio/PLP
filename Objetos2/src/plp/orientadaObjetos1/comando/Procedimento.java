package plp.orientadaObjetos1.comando;

import plp.orientadaObjetos1.comando.Comando;
import plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
/**
 * Representa um procedimento.
 */
public class Procedimento {
    /**
     * Parametros do procedimento.
     */
    private ListaDeclaracaoParametro parametrosFormais;
    /**
     * Comando do procedimento.
     */
    private Comando comando;
    /**
     * Construtor
     * @param parametrosFormais Parâmetros
     * @param comando Comando do procedimento.
     */
    public Procedimento(ListaDeclaracaoParametro parametrosFormais, Comando comando) {
        this.parametrosFormais = parametrosFormais;
        this.comando = comando;
    }
    /**
     * Obtém os parâmetros do procedimento.
     * @return os parâmetros do procedimento.
     */
    public ListaDeclaracaoParametro getParametrosFormais() {
        return parametrosFormais;
    }
    /**
     * Obtém o comando do procedimento.
     * @return o comando do procedimento.
     */
    public Comando getComando() {
        return comando;
    }

}