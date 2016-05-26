package loo2.plp.orientadaObjetos1.expressao.binaria;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.Expressao;
import loo2.plp.orientadaObjetos1.expressao.valor.Valor;
import loo2.plp.orientadaObjetos1.expressao.valor.ValorInteiro;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;
import loo2.plp.orientadaObjetos1.util.TipoPrimitivo;

/**
* Um objeto desta classe representa uma expressao de Subtracao.
*/
public class ExpSub extends ExpBinaria {

    /**
     * Controi uma expressao de Subtracao com as sub-expressoes especificadas.
     * Assume-se que estas expressoes resultam em <code>ValorInteiro</code>
     * quando avaliadas.
     *
     * @param esq expressao da esquerda
     * @param dir expressao da direita
     */
    public ExpSub(Expressao esq, Expressao dir) {
        super(esq, dir, "-");
    }

    /**
     * Retorna o valor da expressao de Subtracao.
     */
    public Valor avaliar(AmbienteExecucaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
            ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException {
        return obterResultadoDaSubtracao(ambiente);
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
     * Retorna o valor inteiro que representa o resultado da subtracao das duas express�es
     * @param ambiente � o Ambiente de Execu��o
     * @return o valor inteiro que representa o resultado da subtracao das duas express�es
     * @throws ClasseNaoDeclaradaException 
     */
    private ValorInteiro obterResultadoDaSubtracao(AmbienteExecucaoOO1 ambiente)
            throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
                   ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException{
        return new ValorInteiro(
                ((ValorInteiro)getEsq().avaliar(ambiente)).valor() -
                ((ValorInteiro)getDir().avaliar(ambiente)).valor());
    }
}
