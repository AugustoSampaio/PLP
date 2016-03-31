package plp.orientadaObjetos1.expressao;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.expressao.valor.Valor;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.util.Tipo;

/**
 * Uma expressão é a unidade basica na Linguagem de Expressoes.
 */
public interface Expressao {

    /**
     * Avalia a expressao retornando seu Valor.
     * @throws ClasseNaoDeclaradaException TODO
     */
    Valor avaliar(AmbienteExecucaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException;

    /**
     * Realiza a verificacao de tipos desta expressao.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *          e tipos.
     * @return <code>true</code> se os tipos da expressao são válidos;
     *          <code>false</code> caso contrario.
     */
    boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, ClasseNaoDeclaradaException;

    /**
     * Retorna os tipos possiveis desta expressao.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *          e tipos.
     * @return os tipos possiveis desta expressao.
     */
    public Tipo getTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, ClasseNaoDeclaradaException;

}
