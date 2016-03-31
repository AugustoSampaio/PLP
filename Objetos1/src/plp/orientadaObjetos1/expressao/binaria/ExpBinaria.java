package plp.orientadaObjetos1.expressao.binaria;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.expressao.Expressao;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;

/**
 * Uma expressao binaria contem duas expressoes e um operador. Ha uma ordem
 * definida  entre estas sub-expressoes
 */
public abstract class ExpBinaria implements Expressao {

    /**
     * expressao da esquerda
     */
    private Expressao esq;

    /**
     * expressao da direita
     */
    private Expressao dir;

    /**
     * Operador desta expressao binaria
     */
    private String operador;

    /**
     * Construtor da classe.
     *
     * @param esq a expressao da esquerda.
     * @param esq a expressao da direita.
     * @param operador o operador desta expressao binaria.
     */
    public ExpBinaria(Expressao esq, Expressao dir, String operador) {
        this.esq = esq;
        this.dir = dir;
        this.operador = operador;
    }

    /**
     * Retorna a expressao da esquerda
     *
     * @return a expressao da esquerda
     */
    public Expressao getEsq() {
        return esq;
    }

    /**
     * Retorna a expressao da direita
     *
     * @return a expressao da direita
     */
    public Expressao getDir() {
        return dir;
    }

    /**
     * Retorna o operador desta expressao binaria
     *
     * @return o operador desta expressao binaria
     */
    public String getOperador() {
        return operador;
    }

    /**
     * Realiza a verificacao de tipos desta expressao.
     *
     * @param ambiente o ambiente de compilação.
     * @return <code>true</code> se os tipos da expressao são válidos;
     *          <code>false</code> caso contrario.
     * @exception VariavelJaDeclaradaException se a variável já está
     *            declarada no ambiente
     * @exception VariavelNaoDeclaradaException se a variável ainda não
     *            foi declarada no ambiente.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, ClasseNaoDeclaradaException {
        boolean result;
        if (getEsq().checaTipo(ambiente) &&
            getDir().checaTipo(ambiente)) {
            result = true;
        }
        else {
            result = false;
        }
        return result;
    }

}
