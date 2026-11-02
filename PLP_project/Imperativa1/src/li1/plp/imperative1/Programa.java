package li1.plp.imperative1;

import li1.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li1.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li1.plp.imperative1.command.Comando;
import li1.plp.imperative1.command.ListaCreateUse;
import li1.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li1.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li1.plp.imperative1.memory.EntradaVaziaException;
import li1.plp.imperative1.memory.ErroTipoEntradaException;
import li1.plp.imperative1.memory.ListaValor;

public class Programa {
    /**
     * lista de create use
     */
    private ListaCreateUse listaCreateUse;
    /**
     * Comando executado apos a lista de create use
     */
    private Comando comando;
    
    /**
     * Construtor.
     * @param listaCreateUse A criacao das tabelas
     * @param comando O comando executado apos a criacao das tabelas.
     */

    public Programa(ListaCreateUse listaCreateUse, Comando comando){
        this.listaCreateUse = listaCreateUse;
        this.comando = comando;
    }

    /**
     * Executa o programa.
     *
     * @param ambienteExecucao o ambiente de execucao.
     *
     * @return a saída produzida pelo programa.
     * 
     * @throws ErroTipoEntradaException 
     *
     * @exception EntradaNaoFornecidaException se nao for fornecida
     *  a tail de valores de entrada do programa.
     *
     */ 
    public ListaValor executar(AmbienteExecucaoImperativa ambienteExecucao) 
        throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException, EntradaVaziaException, ErroTipoEntradaException {
            ambienteExecucao = comando.executar(listaCreateUse.executar(ambienteExecucao));
            return ambienteExecucao.getSaida();       
    }

    /**
     * Realiza a verificação de tipos do programa: primeiro os create-use,
     * que registram as tabelas no mapa, e depois o comando, que pode
     * usa-las.
     *
     * @param ambienteCompilacao o ambiente de compilacao.
     * @return <code>true</code> se o programa esta bem tipado;
     *          <code>false</code> caso contrario.
     *
     * @exception EntradaNaoFornecidaException se nao for fornecida
     *  a tail de valores de entrada do programa.
     *
     */
    public boolean checaTipo(AmbienteCompilacaoImperativa ambienteCompilacao)
        throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException, EntradaVaziaException  {

            return listaCreateUse.checaTipo(ambienteCompilacao) && comando.checaTipo(ambienteCompilacao);
        
    }

}
