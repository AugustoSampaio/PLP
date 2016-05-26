package loo2.plp.orientadaObjetos1.expressao.unaria;

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
 * Um objeto desta classe representa uma expressao de menos unario.
 */
public class ExpMenos extends ExpUnaria {

    /**
     * Controi uma expressao de menos unario com expressao especificada
     *
     * @param exp expressao cuja avaliacao resulta <code>ValorInteiro</code>.
     */
    public ExpMenos(Expressao expressao){
        super(expressao, "-");
    }

    /**
     * Retorna o valor da expressao de menos unario.
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
        return obterValorNegativo(ambiente);
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
            ((TipoPrimitivo)getExp().getTipo(ambiente)).eInteiro()) {
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
     * Retorna o valor negativo de uma express�o
     * @param ambiente � o Ambiente de Execu��o
     * @return o valor negativo da express�o inteira
     * @throws ClasseNaoDeclaradaException 
     */
    private ValorInteiro obterValorNegativo(AmbienteExecucaoOO1 ambiente)
            throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
                   ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException{
        return new ValorInteiro(- ((ValorInteiro)getExp().avaliar(ambiente)).valor());
    }
}
