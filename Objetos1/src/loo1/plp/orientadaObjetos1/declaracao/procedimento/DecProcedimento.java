package loo1.plp.orientadaObjetos1.declaracao.procedimento;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.comando.Procedimento;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;

/**
 * Interface que representa uma declara��o de Procedimento.
 */
public interface DecProcedimento {

     /**
     * Retorna o procedimento a ser declarado na Declara��o da Classe
     * @param id o identificador da declaracao de procedimento
     * @return o procedimento da declara��o
     */
    public Procedimento getProcedimento(Id nomeProcedimento)
        throws ProcedimentoNaoDeclaradoException;
    /**
     * Verifica se a declara��o est� bem tipada, ou seja, se a
     * express�o de inicializa��o est� bem tipada.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos da declara��o s�o v�lidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
        ProcedimentoJaDeclaradoException, ProcedimentoNaoDeclaradoException,
        ClasseNaoDeclaradaException,ClasseJaDeclaradaException;
}
