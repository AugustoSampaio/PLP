package loo2.plp.orientadaObjetos1.expressao.binaria;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.Expressao;
import loo2.plp.orientadaObjetos1.expressao.valor.Valor;
import loo2.plp.orientadaObjetos1.expressao.valor.ValorBooleano;
import loo2.plp.orientadaObjetos1.expressao.valor.ValorConcreto;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;
import loo2.plp.orientadaObjetos1.util.TipoClasse;
import loo2.plp.orientadaObjetos1.util.TipoPrimitivo;


/**
 * Um objeto desta classe representa uma expressao de Igualdade entre
 * Expressoes cuja avaliacao resulta num mesmo valor primitivo.
 */
public class ExpEquals extends ExpBinaria{

    /**
     * Controi uma expressao de Igualdade com as sub-expressoes especificadas.
     * Assume-se que estas sub-expressoes resultam num mesmo valor primitivo
     * quando avaliadas.
     * @param esq expressao da esquerda
     * @param dir expressao da direita
     */
    public ExpEquals(Expressao esq, Expressao dir){
        super(esq, dir, "==");
    }

    /**
     * Retorna o valor da expressao de Igualdade
     */
    public Valor avaliar(AmbienteExecucaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
        ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException {
        return verificarIgualdade(ambiente);
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
        boolean result = false;
        if (super.checaTipo(ambiente)) {
           if(getEsq().getTipo(ambiente) instanceof TipoClasse) {
            result = getDir().getTipo(ambiente).equals(TipoClasse.TIPO_NULL) ||
                     getEsq().getTipo(ambiente).equals(getDir().getTipo(ambiente));
           }
           else {
            result = getEsq().getTipo(ambiente).equals(getDir().getTipo(ambiente));
           }
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
     * Retorna o valor booleano que representa o resultado da comparacao de igualdade
     * de duas expressoes
     * @param ambiente � o Ambiente de Execu��o
     * @return o valor inteiro que representa o resultado da concatenacao de dois Strings
     * @throws ClasseNaoDeclaradaException 
     */
    private ValorBooleano verificarIgualdade(AmbienteExecucaoOO1 ambiente)
            throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
                   ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException{
        Valor v1 = getEsq().avaliar(ambiente);
        Valor v2 = getDir().avaliar(ambiente);
        boolean compara;
        if (v1 instanceof ValorConcreto && v2 instanceof ValorConcreto){
            compara = ((ValorConcreto)v1).equals((ValorConcreto)(v2));
        } else {
            compara =  v1.equals(v2);
        }
        return new ValorBooleano(compara);
    }
}