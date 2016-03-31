package plp.orientadaObjetos1;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.comando.Comando;
import plp.orientadaObjetos1.declaracao.classe.DecClasse;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import plp.orientadaObjetos1.excecao.execucao.EntradaNaoFornecidaException;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.memoria.colecao.ListaValor;
/**
 * Classe que representa um programa na linguagem OO.
 */
public class Programa {
    /**
     * DeclaraÁao de classe
     */
    private DecClasse decClasse;
    /**
     * Comando executado apÛs a declaraÁao de classes
     */
    private Comando comando;

    /**
     * Construtor.
     * @param decClasse A declaraÁao de classe(s)
     * @param comando O comando executado apÛs a declaraÁao.
     */
    public Programa(DecClasse decClasse, Comando comando){
        this.decClasse = decClasse;
        this.comando = comando;
    }

     /**
     * Executa o programa.
     *
     * @param ambiente o ambiente de execuÁ„o.
     *
     * @return o ambiente depois de modificado pela execuÁ„o
     * do programa.
     *
     * @exception EntradaNaoFornecidaException se n„o for fornecida
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
        
        //nao precisa incrementar no inicio, já que não existe a possibilidade 
        //de declarar variáveis antes de uma declaração de classes
        //ambiente.incrementa();
        ambiente = comando.executar(decClasse.elabora(ambiente));
        //ambiente.restaura();
        return ambiente.getSaida();
    }

    /**
     * Realiza a verificacao de tipos do programa
     *
     * @param ambiente o ambiente de compilaÁ„o.
     * @return <code>true</code> se o programa est· bem tipado;
     *          <code>false</code> caso contrario.
     *
     * @exception EntradaNaoFornecidaException se n„o for fornecida
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
        //nao precisa incrementar no inicio, já que não existe a possibilidade 
        //de declarar variáveis antes de uma declaração de classes
        //ambiente.incrementa();
        resposta = decClasse.checaTipo(ambiente) && comando.checaTipo(ambiente);
        //ambiente.restaura();
        return resposta;
    }
}