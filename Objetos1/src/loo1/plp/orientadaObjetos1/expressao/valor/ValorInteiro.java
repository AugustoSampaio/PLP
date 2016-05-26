package loo1.plp.orientadaObjetos1.expressao.valor;

import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo1.plp.orientadaObjetos1.util.Tipo;
import loo1.plp.orientadaObjetos1.util.TipoPrimitivo;

/**
 * Objetos desta classe encapsulam valor inteiro.
 */
public class ValorInteiro implements ValorConcreto {

    private int valor;

    /**
     * Cria <code>ValorInteiro</code> contendo o valor fornecido.
     */
    public ValorInteiro(int valor) {
        this.valor = valor;
    }

    /**
     * Retorna o valor inteiro encapsulado pelo objeto deste tipo.
     */
    public int valor() {
        return valor;
    }

    /**
     * Retorna o valor deste valor primitivo, i.e., ele mesmo.
     */
    public Valor avaliar (AmbienteExecucaoOO1 amb) throws ClasseNaoDeclaradaException {
        return this;
    }

    /**
     * Determina igualdade entre objetos deste tipo
     */
    public boolean equals(ValorConcreto obj) {
    	if (obj instanceof ValorInteiro){
    		return valor == ((ValorInteiro) obj).valor();
    	} else {
    		return false;
    	}
    }

    /**
     * Retorna texto representando o valor inteiro do objeto desta classe.
     */
    public String toString() {
        return String.valueOf(valor);
    }

    /**
     * Realiza a verificacao de tipos desta expressao.
     *
     * @param ambiente o ambiente de compila��o.
     * @return <code>true</code> se os tipos da expressao s�o v�lidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 amb) {
        return true;
    }

    /**
     * Retorna os tipos possiveis desta expressao.
     *
     * @param ambiente o ambiente de compila��o.
     * @return os tipos possiveis desta expressao.
     */
    public Tipo getTipo(AmbienteCompilacaoOO1 amb) {
        return TipoPrimitivo.TIPO_INTEIRO;
    }

}
