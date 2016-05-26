package loo2.plp.orientadaObjetos2;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.comando.Comando;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import loo2.plp.orientadaObjetos1.excecao.execucao.EntradaNaoFornecidaException;
import loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor;
import loo2.plp.orientadaObjetos2.declaracao.ConstrutorNaoDeclaradoException;
import loo2.plp.orientadaObjetos2.declaracao.ListaDeclaracaoOO;
import loo2.plp.orientadaObjetos2.memoria.AmbienteCompilacaoOO2;
import loo2.plp.orientadaObjetos2.memoria.AmbienteExecucaoOO2;

/**
 * Classe que representa um programa na linguagem OO.
 */
public class Programa {
    /**
     * lista de declaracoes OO
     */
    private ListaDeclaracaoOO declaracoesOO;
    /**
     * Comando executado pos a declaracao de classes
     */
    private Comando comando;
    
    /**
     * Construtor.
     * @param decClasse A declaraçao de classe(s)
     * @param comando O comando executado após a declaraçao.
     */
    public Programa(ListaDeclaracaoOO dec, Comando comando){
        this.declaracoesOO = dec;
        this.comando = comando;
    }

     /**
     * Executa o programa.
     *
     * @param ambiente o ambiente de execução.
     *
     * @return o ambiente depois de modificado pela execução
     * do programa.
     *
     * @exception EntradaNaoFornecidaException se não for fornecida
     *  a tail de valores de entrada do programa.
     * @throws ConstrutorNaoDeclaradoException 
     *
     */
    public ListaValor executar(AmbienteExecucaoOO2 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ObjetoNaoDeclaradoException, ObjetoJaDeclaradoException,
               ProcedimentoJaDeclaradoException,ProcedimentoNaoDeclaradoException,
               ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
               EntradaNaoFornecidaException, EntradaInvalidaException, ConstrutorNaoDeclaradoException {
        if(ambiente == null)
            throw new EntradaNaoFornecidaException();
        ambiente.incrementa();
        ambiente = (AmbienteExecucaoOO2) comando.executar(declaracoesOO.elabora(ambiente));
        ambiente.restaura();
        return ambiente.getSaida();
    }

    /**
     * Realiza a verificacao de tipos do programa
     *
     * @param ambiente o ambiente de compilação.
     * @return <code>true</code> se o programa está bem tipado;
     *          <code>false</code> caso contrario.
     *
     * @exception EntradaNaoFornecidaException se não for fornecida
     *  a tail de valores de entrada do programa.
     * @throws ConstrutorNaoDeclaradoException 
     *
     */
    public boolean checaTipo(AmbienteCompilacaoOO2 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
               ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
               EntradaNaoFornecidaException, ConstrutorNaoDeclaradoException{
        boolean resposta;
        if(ambiente == null) {
            throw new EntradaNaoFornecidaException();
        }
        ambiente.incrementa();
        resposta = declaracoesOO.checaTipo((AmbienteCompilacaoOO2) ambiente) && comando.checaTipo(ambiente);
        ambiente.restaura();
        return resposta;
    }
}