package plp.orientadaObjetos1.declaracao.variavel;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.expressao.Expressao;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.util.Tipo;
import plp.orientadaObjetos1.util.TipoClasse;

/**
 * Classe que representa uma declaraçao de variável simples.
 */
public class SimplesDecVariavel implements DecVariavel{
    /**
     * Tipo da variável declada.
     */
    private Tipo tipo;
    /**
     * Variável declarada.
     */
    private Id id;
    /**
     * Expressão cujo valor será atribuído à variável.
     */
    private Expressao expressao;
    /**
     * Construtor.
     * @param tipo Tipo da variável declarada.
     * @param id Variável declarada.
     * @param expressao Expressão cujo valor será atribuído à variável.
     */
    public SimplesDecVariavel(Tipo tipo, Id id, Expressao expressao){
        this.tipo = tipo;
        this.id = id;
        this.expressao = expressao;
    }

    /**
    * Retorna o tipo do identificador a ser declarado no AmbienteCompilacao
    * @param id o identificador  da declaracao
    * @return o tipo do identificador
    */
    public Tipo getTipo(Id id) throws VariavelNaoDeclaradaException{
           if(this.id.equals(id)){
               return tipo;
           }
           else {
               throw new VariavelNaoDeclaradaException(id);
           }
    }

    /**
     * Cria um mapeamento do identificador para o valor da expressão
     * desta declaração no AmbienteExecucao
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela inicialização da variável.
     * @throws ClasseNaoDeclaradaException 
     *
     */
    public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
               ObjetoNaoDeclaradoException,ObjetoJaDeclaradoException, ClasseNaoDeclaradaException  {
        ambiente.map(id , expressao.avaliar(ambiente));
        return ambiente;
    }

    /**
     * Verifica se a declaração está bem tipada, ou seja, se a
     * expressão de inicialização está bem tipada.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se a expressão é bem tipada;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ClasseNaoDeclaradaException {
        boolean resposta = false;
        if ( expressao.checaTipo(ambiente) ) {
            if( tipo instanceof TipoClasse) {
                resposta = expressao.getTipo(ambiente).equals(TipoClasse.TIPO_NULL) ||
                           expressao.getTipo(ambiente).equals(tipo);
            }
            else {
                resposta = expressao.getTipo(ambiente).equals(tipo);
            }
        }
        if(resposta) {
            ambiente.map(id, tipo);
        }
        return resposta;
    }

}
