package loo2.plp.orientadaObjetos1.util;

import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;

/**
 * Classe que representa os possiveis tipos de uma expressao.
 */
public class TipoClasse implements Tipo {

    /**
     * Indica que a expressao associada &eacute; uma classe.
     */
    private Id tipoClasse;

    /**
     * Indica que a expressao associada &eacute; nula.
     */
    public static final Id NULL = new Id("NULL");

    /**
     * Constante de tipo nulo.
     */
    public static final Tipo TIPO_NULL = new TipoClasse(NULL);

     /**
     * Construtor da classe.
     *
     * @param tipo o tipo da expressao associada.
     */

     public TipoClasse(Id tipoClasse) {
        this.tipoClasse = tipoClasse;
     }


    /**
     * Retorna o tipo da expressao associada.
     *
     * @return o tipo da expressao associada.
     */
    public Id getTipo() {
        return tipoClasse;
    }


    /**
     * Indica se esta classe &eacute; um tipo v�lido, ou seja, se j� tiver sido declarada.
     *
     * @return <code>true</code> se esta classe for um tipo v�lido (j� declarada);
     *          <code>false</code> caso contrario.
     */

    public boolean eValido(AmbienteCompilacaoOO1 ambiente)
         throws ClasseNaoDeclaradaException {
        boolean resposta = false;
        try{
             resposta =  (tipoClasse == NULL) || (ambiente.getDefClasse(tipoClasse) != null);
         }
         catch(ClasseNaoDeclaradaException c){
              resposta = false;
         }
         return resposta;
    }

    /**
     * Compara este tipo com o tipo dado.
     *
     * @return <code>true</code> se se tratarem do mesmo tipo;
     *          <code>false</code> caso contrario.
     */
    public boolean equals(Object obj) {
        return (obj instanceof TipoClasse) &&
               ((TipoClasse)obj).tipoClasse.equals(this.tipoClasse);
    }
    /**
     * Retorna  a descri��o textual do tipo.
     * @return  a descri��o textual do tipo.
     */
    public String toString() {
        return tipoClasse.toString();
    }
}
