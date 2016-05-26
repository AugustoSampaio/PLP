package loo1.plp.orientadaObjetos1.expressao.binaria;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.expressao.Expressao;
import loo1.plp.orientadaObjetos1.expressao.valor.Valor;
import loo1.plp.orientadaObjetos1.expressao.valor.ValorInteiro;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo1.plp.orientadaObjetos1.util.Tipo;
import loo1.plp.orientadaObjetos1.util.TipoPrimitivo;

/**
 * Um objeto desta classe representa uma expressao de Soma.
 */
public class ExpSoma extends ExpBinaria {

    /**
     * Controi uma expressao de Soma com as sub-expressoes especificadas.
     * Assume-se que estas sub-expressoes resultam em <code>ValorInteiro</code>
     * quando avaliadas.
     * @param esq expressao da esquerda
     * @param dir expressao da direita
     */
    public ExpSoma(Expressao esq, Expressao dir) {
        super(esq, dir, "+");
    }

    /**
     * Retorna o valor da expressao de Soma
     */
    public Valor avaliar(AmbienteExecucaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException {
        return obterResultadoDaSoma(ambiente);
    }

    /**
     * Realiza a verificacao de tipos desta expressao.
     *
     * @param ambiente o ambiente de compila��o.
     * @return <code>true</code> se os tipos da expressao s�o v�lidos;
     *          <code>false</code> caso contrario.
     * @exception VariavelNaoDeclaradaException se existir um identificador
     *          nao declarado no ambiente.
     * @exception VariavelNaoDeclaradaException se existir um identificador
     *          declarado mais de uma vez no mesmo bloco do ambiente.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, ClasseNaoDeclaradaException {
        boolean result;
        if (super.checaTipo(ambiente) &&
           ((TipoPrimitivo)getEsq().getTipo(ambiente)).eInteiro() &&
            ((TipoPrimitivo)getDir().getTipo(ambiente)).eInteiro()) {
            result = true;
        }
        else {
            result = false;
        }
        return result;
    }

    /**
     * Retorna os tipos possiveis desta expressao.
     *
     * @param ambiente o ambiente de compila��o.
     * @return os tipos possiveis desta expressao.
     */
    public Tipo getTipo(AmbienteCompilacaoOO1 ambiente) {
        return TipoPrimitivo.TIPO_INTEIRO;
    }

    /**
     * Retorna o valor inteiro que representa o resultado da soma das duas express�es
     * @param ambiente � o Ambiente de Execu��o
     * @return o valor inteiro que representa o resultado da soma das duas express�es
     * @throws ClasseNaoDeclaradaException 
     */
    private ValorInteiro obterResultadoDaSoma(AmbienteExecucaoOO1 ambiente)
            throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
                   ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException{
        return new ValorInteiro(
            ((ValorInteiro) getEsq().avaliar(ambiente)).valor() +
            ((ValorInteiro) getDir().avaliar(ambiente)).valor() );
    }
}
