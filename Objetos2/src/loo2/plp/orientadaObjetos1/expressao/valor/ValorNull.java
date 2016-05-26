package loo2.plp.orientadaObjetos1.expressao.valor;

import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;
import loo2.plp.orientadaObjetos1.util.TipoClasse;

/**
 * Este valor primitivo encapsula um valor do tipo Null.
 */
public class ValorNull implements ValorConcreto {


    /**
     * Retorna texto representando o valor string do objeto desta classe.
     *
     * @return texto representando o valor string do objeto desta classe.
     */
    public String toString() {
      return "null";
    }

    /**
     * Determina igualdade entre  objetos desta classe
     */
    public boolean equals(ValorConcreto v){
        if ( v instanceof ValorNull)
              return true;
        else return false;
  }

    /**
     * Retorna ele mesmo.
     */
    public Valor avaliar(AmbienteExecucaoOO1 amb) throws ClasseNaoDeclaradaException {
        return this;
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
        return TipoClasse.TIPO_NULL;
    }
}