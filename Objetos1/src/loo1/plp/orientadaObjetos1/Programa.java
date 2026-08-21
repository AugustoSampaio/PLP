package loo1.plp.orientadaObjetos1;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.comando.Comando;
import loo1.plp.orientadaObjetos1.declaracao.classe.DecClasse;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import loo1.plp.orientadaObjetos1.excecao.execucao.EntradaNaoFornecidaException;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo1.plp.orientadaObjetos1.memoria.colecao.ListaValor;
/**
 * Classe que representa um programa na linguagem OO.
 */
public class Programa {
    /**
     * Declara�ao de classe
     */
    private DecClasse decClasse;
    /**
     * Comando executado ap�s a declara�ao de classes
     */
    private Comando comando;

    /**
     * Construtor.
     * @param decClasse A declara�ao de classe(s)
     * @param comando O comando executado ap�s a declara�ao.
     */
    public Programa(DecClasse decClasse, Comando comando){
        this.decClasse = decClasse;
        this.comando = comando;
    }

     /**
     * Executa o programa.
     *
     * @param ambiente o ambiente de execu��o.
     *
     * @return o ambiente depois de modificado pela execu��o
     * do programa.
     *
     * @exception EntradaNaoFornecidaException se n�o for fornecida
     *  a tail de valores de entrada do programa.
     *
     */
    public ListaValor executar(AmbienteExecucaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ObjetoNaoDeclaradoException, ObjetoJaDeclaradoException,
               ProcedimentoJaDeclaradoException,ProcedimentoNaoDeclaradoException,
               ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
               EntradaNaoFornecidaException, EntradaInvalidaException {
        if(ambiente == null)
            throw new EntradaNaoFornecidaException();
        
        //nao precisa incrementar no inicio, j� que n�o existe a possibilidade 
        //de declarar vari�veis antes de uma declara��o de classes
        //ambiente.incrementa();
        ambiente = comando.executar(decClasse.elabora(ambiente));
        //ambiente.restaura();
        return ambiente.getSaida();
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
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
               ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
               EntradaNaoFornecidaException{
        boolean resposta;
        if(ambiente == null) {
            throw new EntradaNaoFornecidaException();
        }
        //nao precisa incrementar no inicio, j� que n�o existe a possibilidade 
        //de declarar vari�veis antes de uma declara��o de classes
        //ambiente.incrementa();
        resposta = decClasse.checaTipo(ambiente) && comando.checaTipo(ambiente);
        //ambiente.restaura();
        return resposta;
    }
}