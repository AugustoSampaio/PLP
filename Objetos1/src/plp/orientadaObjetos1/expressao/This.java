package plp.orientadaObjetos1.expressao;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos1.expressao.valor.Valor;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.util.Tipo;

/**
 * Representa uma expressao utilizando o token "this"
 */
public class This implements Expressao{
    /**
     * Obtém o valor do objeto this no ambiente e o avalia.
     * @param ambiente o ambiente de execuçao, que apresenta o mapeamento
     * entre identificadores e valores.
     * @return o valor do objeto this no escopo do ambiente atual.
     * @throws VariavelNaoDeclaradaException Quando no escopo atual
     * nao pode ser acessado o this.
     * @throws VariavelJaDeclaradaException Se por acaso se tentasse inserir
     * no ambiente corrente mais de um "this" com o mesmo escopo.
     */
    public Valor avaliar(AmbienteExecucaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException, ClasseNaoDeclaradaException{
        return obterValorDoObjetoThisNoAmbiente(ambiente);
    }
    /**
     * Checa o tipo do objeto this.
     * @param ambiente o ambiente de compilação, contendo o mapeamento entre
     * identificadores e tipos.
     * @return true, se o tipo do objeto this pode ser checado sem problemas,
     * false, caso contrário.
     * @throws VariavelNaoDeclaradaException Se nao houver nenhum objeto this
     * no contexto corrente.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException{
        //Como sempre há uma classe instanciada em orientacao a objeto
        //o checaTipo() de this retorna true
        return true;
    }
    /**
     * Obtém o tipo do objeto this
     * @param ambiente o ambiente de compilação, contendo o mapeamento
     * entre identificadoes e tipos.
     * @return o tipo do objeto this no ambiente.
     * @throws VariavelNaoDeclaradaException Se nao houver nenhum objeto this
     * no contexto atual.
     */
    public Tipo getTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException{
          return ambiente.get(new Id("this"));
    }

    /**
     * Retorna o valor do Objeto this no ambiente
     * @param ambiente é o Ambiente de Execução
     * @return o valor do Objeto this no ambiente
     */
    private Valor obterValorDoObjetoThisNoAmbiente(AmbienteExecucaoOO1 ambiente)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return ambiente.get(new Id("this"));
    }
}