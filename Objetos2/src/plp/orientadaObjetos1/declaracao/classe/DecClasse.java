package plp.orientadaObjetos1.declaracao.classe;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.declaracao.Declaracao;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;

/**
 * Interface representando a declaração de uma classe.
 */
public interface DecClasse extends Declaracao{

    /**
     * Cria um mapeamento do identificador para o valor da expressão
     * desta declaração no AmbienteExecucao
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela inicialização da variável.
     */
    public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente)
       throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
             ClasseJaDeclaradaException,ClasseNaoDeclaradaException,
             ProcedimentoNaoDeclaradoException,ProcedimentoJaDeclaradoException;

    /**
     * Verifica se a declaração está bem tipada, ou seja, se a
     * expressão de inicialização está bem tipada.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos da declaração são válidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
       throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
           ClasseJaDeclaradaException,ClasseNaoDeclaradaException,
           ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException ;
}
