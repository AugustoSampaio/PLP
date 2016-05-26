package loo2.plp.orientadaObjetos1.comando;

/*
 * A execucao de um comando ocorre em um determinado ambiente. O
 * resultado de tal execucao � a modifica��o deste ambiente, i.e., comandos
 *tem efeito colateral.
 */

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;

/**
 * Interface representando um comando na linguagem.
 */
public interface Comando {

    /**
     * Executa este comando.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela execu��o do comando.
     */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
               ObjetoJaDeclaradoException, ObjetoNaoDeclaradoException,
               ProcedimentoNaoDeclaradoException,ProcedimentoJaDeclaradoException,
               ClasseJaDeclaradaException, ClasseNaoDeclaradaException, EntradaInvalidaException;
    /**
     * Realiza a verificacao de tipos deste comando.
     *
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e tipos.
     * @return <code>true</code> se os comando s�o bem tipados;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
        ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
        ClasseJaDeclaradaException, ClasseNaoDeclaradaException;

}

