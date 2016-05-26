package loo2.plp.orientadaObjetos1.expressao.valor;

import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;
import loo2.plp.orientadaObjetos1.util.TipoPrimitivo;

/**
 * Este valor primitivo encapsula um valor booleano.
 */
public class ValorBooleano implements ValorConcreto{

    private boolean valor;

    /**
     * Cria um objeto encapsulando o valor booleano fornecido.
     */
    public ValorBooleano(boolean valor) {
        this.valor = valor;
    }

    /**
     * Retorna o valor deste valor primitivo, i.e, ele mesmo.
     */
    public Valor avaliar(AmbienteExecucaoOO1 amb) throws ClasseNaoDeclaradaException {
        return this;
    }

    /**
     * Retorna o valor booleano encapsulado pelo objeto do tipo <code>ValorBooleano</code>
     */
    public boolean valor() {
        return valor;
    }

    /**
     * Determina igualdade entre valores do tipo <code>ValorBooleano</code>
     */
    public boolean equals(ValorConcreto  obj){
    	if (obj instanceof ValorBooleano){
	        return valor == ((ValorBooleano) obj).valor();
    	} else {
    		return false;
    	}
    }

    /**
     * Retorna texto representando o valor booleano
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
        return TipoPrimitivo.TIPO_BOOLEANO;
    }


}