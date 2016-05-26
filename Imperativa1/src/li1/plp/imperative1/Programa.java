package li1.plp.imperative1;

import li1.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li1.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li1.plp.imperative1.command.Comando;
import li1.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li1.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li1.plp.imperative1.memory.EntradaVaziaException;
import li1.plp.imperative1.memory.ErroTipoEntradaException;
import li1.plp.imperative1.memory.ListaValor;

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
