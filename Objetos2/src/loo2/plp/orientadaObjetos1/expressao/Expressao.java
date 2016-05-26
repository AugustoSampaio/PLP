package loo2.plp.orientadaObjetos1.expressao;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.valor.Valor;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;

/**
 * Uma express�o � a unidade basica na Linguagem de Expressoes.
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
     * @return <code>true</code> se os tipos da expressao s�o v�lidos;
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
