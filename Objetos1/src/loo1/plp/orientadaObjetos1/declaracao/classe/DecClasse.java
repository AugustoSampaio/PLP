package loo1.plp.orientadaObjetos1.declaracao.classe;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.declaracao.Declaracao;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;

/**
 * Interface representando a declara��o de uma classe.
 */
public interface DecClasse extends Declaracao{

    /**
     * Cria um mapeamento do identificador para o valor da express�o
     * desta declara��o no AmbienteExecucao
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela inicializa��o da vari�vel.
     */
    public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente)
       throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
             ClasseJaDeclaradaException,ClasseNaoDeclaradaException,
             ProcedimentoNaoDeclaradoException,ProcedimentoJaDeclaradoException;

    /**
     * Verifica se a declara��o est� bem tipada, ou seja, se a
     * express�o de inicializa��o est� bem tipada.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos da declara��o s�o v�lidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
       throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
           ClasseJaDeclaradaException,ClasseNaoDeclaradaException,
           ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException ;
}
