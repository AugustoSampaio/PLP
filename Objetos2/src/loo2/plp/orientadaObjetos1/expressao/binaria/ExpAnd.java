package loo2.plp.orientadaObjetos1.expressao.binaria;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.Expressao;
import loo2.plp.orientadaObjetos1.expressao.valor.Valor;
import loo2.plp.orientadaObjetos1.expressao.valor.ValorBooleano;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;
import loo2.plp.orientadaObjetos1.util.TipoPrimitivo;

/**
 * Um objeto desta classe representa uma expressao de Conjuncao logica.
 */
public class ExpAnd extends ExpBinaria{

    /**
     * Controi uma expressao de Conjuncao logica  com as sub-expressoes
     * especificadas. Estas devem ser tais que sua avaliacao retorna
     * <code>ValorBooleano</code>
     *
     * @param esq expressao da esquerda
     * @param dir expressao da direita
     */
    public ExpAnd(Expressao esq, Expressao dir) {
        super(esq, dir, "and");
    }


    /**
     * Retorna o valor da expressao de Conjuncao Logica
     * @ambiente O ambiente de execu�ao, contendo o mapeamento
     * de identificadores a valores.
     */
     public Valor avaliar(AmbienteExecucaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException {
        return obterComparacaoAND(ambiente);
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
            ((TipoPrimitivo)getEsq().getTipo(ambiente)).eBooleano() &&
            ((TipoPrimitivo)getDir().getTipo(ambiente)).eBooleano()) {
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
        return TipoPrimitivo.TIPO_BOOLEANO;
    }

/**
 * Retorna o resultado de uma comparacao booleana AND
 * @param ambiente � o Ambiente de Execu��o
 * @return o resultado de uma comparacao booleana AND
 * @throws ClasseNaoDeclaradaException 
 */
    private ValorBooleano obterComparacaoAND(AmbienteExecucaoOO1 ambiente)
            throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
                   ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException{
        return new ValorBooleano(
                    ((ValorBooleano)getEsq().avaliar(ambiente)).valor() &&
                    ((ValorBooleano)getDir().avaliar(ambiente)).valor()
        );
    }
}