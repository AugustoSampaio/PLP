package plp.orientadaObjetos1.util;

import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
/**
 * Interface  representando um tipo.
 */
public interface Tipo {
    /**
     * Obtém o tipo, representado por um identficador.
     * @return o tipo
     */
    public Id getTipo();
    /**
     * Compara dois tipos.
     * @param obj O objeto a ser comparado com este tipo.
     * @return true, se o obj é igual a este tipo, false, caso contrário.
     */
    public boolean equals(Object obj);
    /**
     * Verifica se o tipo é válido no ambiente.
     * @param ambiente o ambiente de compilaçao, que apresenta o mapeamento
     * entre identificadores e tipos.
     * @return true, se o tipo for válido no ambiente, false, caso contrário.
     * @throws ClasseNaoDeclaradaException
     */
    public boolean eValido(AmbienteCompilacaoOO1 ambiente) throws ClasseNaoDeclaradaException;
}
