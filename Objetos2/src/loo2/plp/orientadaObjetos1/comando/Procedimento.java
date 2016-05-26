package loo2.plp.orientadaObjetos1.comando;

import loo2.plp.orientadaObjetos1.comando.Comando;
import loo2.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
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
     * @param parametrosFormais Par�metros
     * @param comando Comando do procedimento.
     */
    public Procedimento(ListaDeclaracaoParametro parametrosFormais, Comando comando) {
        this.parametrosFormais = parametrosFormais;
        this.comando = comando;
    }
    /**
     * Obt�m os par�metros do procedimento.
     * @return os par�metros do procedimento.
     */
    public ListaDeclaracaoParametro getParametrosFormais() {
        return parametrosFormais;
    }
    /**
     * Obt�m o comando do procedimento.
     * @return o comando do procedimento.
     */
    public Comando getComando() {
        return comando;
    }

}