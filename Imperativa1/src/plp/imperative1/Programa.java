package plp.imperative1;

import plp.expressions2.memory.IdentificadorJaDeclaradoException;
import plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import plp.imperative1.command.Comando;
import plp.imperative1.memory.AmbienteCompilacaoImperativa;
import plp.imperative1.memory.AmbienteExecucaoImperativa;
import plp.imperative1.memory.EntradaVaziaException;
import plp.imperative1.memory.ErroTipoEntradaException;
import plp.imperative1.memory.ListaValor;

public class Programa {

    private Comando comando;

    public Programa(Comando comando){
        this.comando = comando;
    }

    /**
     * Executa o programa.
     *
     * @param ambiente o ambiente de execu��o.
     *
     * @return o ambiente depois de modificado pela execu��o
     * do programa.
     * @throws ErroTipoEntradaException 
     *
     * @exception EntradaNaoFornecidaException se n�o for fornecida
     *  a tail de valores de entrada do programa.
     *
     */ 
    public ListaValor executar(AmbienteExecucaoImperativa ambienteExecucao) 
        throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException, EntradaVaziaException, ErroTipoEntradaException {
        ambienteExecucao = comando.executar(ambienteExecucao);
        return ambienteExecucao.getSaida();
    }

    /**
     * Realiza a verificacao de tipos do programa
     *
     * @param ambiente o ambiente de compila��o.
     * @return <code>true</code> se o programa est� bem tipado;
     *          <code>false</code> caso contrario.
     *
     * @exception EntradaNaoFornecidaException se n�o for fornecida
     *  a tail de valores de entrada do programa.
     *
     */
    public boolean checaTipo(AmbienteCompilacaoImperativa ambienteCompilacao)
        throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException, EntradaVaziaException  {
        return comando.checaTipo(ambienteCompilacao);
    }

}
