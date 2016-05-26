package loo1.plp.orientadaObjetos1.expressao.unaria;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.expressao.Expressao;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;

/**
 * Uma expressao unaria contem uma expressao e um operador sobre a mesma.
 */
public abstract class ExpUnaria implements Expressao {

    /**
     * expressao contida pela expressao unaria
     */
    private Expressao exp;

    /**
     * Representacao do operador desta expressao unaria.
     */
    private String operador;

    /**
     * Construtor da classe.
     *
     * @param exp expressao contida pela expressao unaria.
     */
    public ExpUnaria(Expressao exp, String operador) {
        this.exp = exp;
        this.operador = operador;
    }

    /**
     * Retorna a expressao contida pela expressao unaria
     *
     * @return a expressao contida pela expressao unaria
     */
    public Expressao getExp() {
        return exp;
    }

    /**
     * Retorna a representacao do operador desta expressao unaria.
     *
     * @return a representacao do operador desta expressao unaria.
     */
    public String getOperador() {
        return operador;
    }

    /**
     * Realiza a verificacao de tipos desta expressao.
     *
     * @param ambiente o ambiente de compila��o.
     * @return <code>true</code> se os tipos da expressao s�o v�lidos;
     *          <code>false</code> caso contrario.
     * @exception VariavelJaDeclaradaException se a vari�vel j� est�
     *            declarada no ambiente
     * @exception VariavelNaoDeclaradaException se a vari�vel ainda n�o
     *            foi declarada no ambiente.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException,
            VariavelNaoDeclaradaException, ClasseNaoDeclaradaException {
        boolean result;
        if (getExp().checaTipo(ambiente)) {
            result = true;
        }
        else {
            result = false;
        }
        return result;
    }
}