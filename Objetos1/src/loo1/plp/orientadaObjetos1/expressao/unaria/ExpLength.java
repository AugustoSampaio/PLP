package loo1.plp.orientadaObjetos1.expressao.unaria;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.expressao.Expressao;
import loo1.plp.orientadaObjetos1.expressao.valor.Valor;
import loo1.plp.orientadaObjetos1.expressao.valor.ValorInteiro;
import loo1.plp.orientadaObjetos1.expressao.valor.ValorString;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo1.plp.orientadaObjetos1.util.Tipo;
import loo1.plp.orientadaObjetos1.util.TipoPrimitivo;


/**
 * Um objeto desta classe representa uma expressao de tamanho de String.
 */
public class ExpLength extends ExpUnaria {

    /**
     * Controi uma expressao de tamanho  com a expressao especificada
     * assume-se que <code>exp</code> &eacute; uma expressao cuja avaliacao
     * resulta num <code>ValorString</code>
     *
     * @param exp a expressao em quest�o.
     */
    public ExpLength(Expressao expressao) {
        super(expressao, "length");
    }

    /**
     * Retorna o valor da expressao de tamanho.
     *
     * @param ambiente o ambiente de execu��o.
     * @return o valor da expressao avaliada.
     * @exception VariavelNaoDeclaradaException se existir um identificador
     *          nao declarado no ambiente.
     * @exception VariavelNaoDeclaradaException se existir um identificador
     *          declarado mais de uma vez no mesmo bloco do ambiente.
     */
    public Valor avaliar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException,
            VariavelNaoDeclaradaException, ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException {
        return obterTamanhoDoString(ambiente);
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
            ((TipoPrimitivo)getExp().getTipo(ambiente)).eString()) {
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
     * Retorna o tamanho de um String
     * @param ambiente � o Ambiente de Execu��o
     * @return o tamanho de um String
     * @throws ClasseNaoDeclaradaException 
     */
    private ValorInteiro obterTamanhoDoString(AmbienteExecucaoOO1 ambiente)
            throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
                   ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException{
        return new ValorInteiro (((ValorString)getExp().avaliar(ambiente)).valor().length());
    }
}