package plp.orientadaObjetos1.expressao.valor;

import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.util.Tipo;
import plp.orientadaObjetos1.util.TipoPrimitivo;
/**
 * Classe que representa a referência a um objeto.
 */
public class ValorRef implements Valor{
    /**
     * Primeira referencia valida na memoria
     */
    public static final int VALOR_INICIAL = 0;
    /**
     * Valor que representa a referência.
     */
    private int valor;
    /**
     * Construtor.
     * @param valor O valor que representa a referência.
     */
    public ValorRef(int valor){
        if ( valor >= ValorRef.VALOR_INICIAL){
             this.valor = valor;
    }
        else this.valor = VALOR_INICIAL;
    }

    /**
     * Obtém o valor.
     * @return o valor referência.
     */
    public int valor() {
        return valor;
    }
    /**
     * Avalia um certo valor referência.
     * @param ambiente o ambiente de execuçao
     * @return o valor associado a uma dada referência.
     */
    public Valor avaliar(AmbienteExecucaoOO1 ambiente) throws ClasseNaoDeclaradaException {
        return this;
    }

    //Os métodos getTipo e checaTipo de ValorRef
    //não foram utilizados nessa linguagem.
    public int hashCode() {
        return valor;
    }
    /**
     * Obtém o tipo associado a este valor referência no ambiente de compilaçao.
     * @param amb o ambiente de compilação.
     * @return o tipo deste valor referência.
     */
    public Tipo getTipo(AmbienteCompilacaoOO1 amb) {
        return TipoPrimitivo.TIPO_INTEIRO;
    }
    /**
     * Checa o tipo deste valor referencia no ambiente de compilação.
     * @param amb o ambiente de compilação
     * @return true em todos os casos.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 amb) {
        return true;
    }
    /**
     * Compara dois valores
     * @param val o valor que vai ser comparado com este.
     * @return true se os valores forem iguais e false, caso contrário.
     */
    public boolean equals(Valor val){
        if ( val instanceof ValorRef)
            return valor == ((ValorRef)val).valor();
        else return false;
    }

    /**
     * Incrementa este valor referência.
     * @return um novo valor referência.
     */
    public ValorRef incrementa(){
        valor = valor + 1;
        return this;
    }
}
