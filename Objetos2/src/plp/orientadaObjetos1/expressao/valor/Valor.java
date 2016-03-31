package plp.orientadaObjetos1.expressao.valor;

import plp.orientadaObjetos1.expressao.Expressao;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.util.Tipo;

/**
 * <code>Valor</code> agrupa objetos dos diferentes valores primitivos
 */
public interface Valor extends Expressao {


    /**
     * Retorna o tipo do valor.
     * @param ambiente o ambiente de compilação.
     * @return o tipo do valor.
     */
    public abstract Tipo getTipo(AmbienteCompilacaoOO1 ambiente);

}


