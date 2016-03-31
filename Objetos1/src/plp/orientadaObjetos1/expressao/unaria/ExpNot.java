package plp.orientadaObjetos1.expressao.unaria;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.expressao.Expressao;
import plp.orientadaObjetos1.expressao.valor.Valor;
import plp.orientadaObjetos1.expressao.valor.ValorBooleano;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.util.Tipo;
import plp.orientadaObjetos1.util.TipoPrimitivo;


/**
 * Um objeto desta classe representa uma expressao de Negacao logica.
 */
public class ExpNot extends ExpUnaria{

    /**
     * Controi uma expressao de negacao logica com expressao
     * especificada.
     *
     * @param exp expressao a ser negada. Assume-se que sua avaliacao resulta
     *        em <code>ValorBooleano</code>.
     */
    public ExpNot(Expressao expressao) {
        super(expressao, "~");
    }

    /**
     * Retorna o valor da expressao de negacao logica.
     *
     * @param ambiente o ambiente de execução.
     * @return o valor da expressao avaliada.
     * @excepion VariavelJaDeclaradaException se a variável já está
     *           declarada no ambiente.
     * @exception VariavelNaoDeclaradaException se a variável não está
     *            declarada no ambiente.
     */
    public Valor avaliar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
        ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException{
        return obterValorInverso(ambiente);
    }

    /**
     * Realiza a verificacao de tipos desta expressao.
     *
     * @param ambiente o ambiente de compilação.
     * @return <code>true</code> se os tipos da expressao são válidos;
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
            ((TipoPrimitivo)getExp().getTipo(ambiente)).eBooleano()) {
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
     * @param ambiente o ambiente de compilação.
     * @return os tipos possiveis desta expressao.
     */
    public Tipo getTipo(AmbienteCompilacaoOO1 ambiente) {
        return TipoPrimitivo.TIPO_BOOLEANO;
    }

    /**
     * Retorna o valor inverso de uma expressão booleana
     * @param ambiente é o Ambiente de Execução
     * @return o valor inverso da expressão booleana
     * @throws ClasseNaoDeclaradaException 
     */
    private ValorBooleano obterValorInverso(AmbienteExecucaoOO1 ambiente)
            throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
                   ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException{
        return new ValorBooleano(!((ValorBooleano) getExp().avaliar(ambiente)).valor());
    }
}
