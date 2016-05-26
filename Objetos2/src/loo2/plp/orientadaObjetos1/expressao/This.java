package loo2.plp.orientadaObjetos1.expressao;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.expressao.valor.Valor;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;

/**
 * Representa uma expressao utilizando o token "this"
 */
public class This implements Expressao{
    /**
     * Obt�m o valor do objeto this no ambiente e o avalia.
     * @param ambiente o ambiente de execu�ao, que apresenta o mapeamento
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
     * @param ambiente o ambiente de compila��o, contendo o mapeamento entre
     * identificadores e tipos.
     * @return true, se o tipo do objeto this pode ser checado sem problemas,
     * false, caso contr�rio.
     * @throws VariavelNaoDeclaradaException Se nao houver nenhum objeto this
     * no contexto corrente.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException{
        //Como sempre h� uma classe instanciada em orientacao a objeto
        //o checaTipo() de this retorna true
        return true;
    }
    /**
     * Obt�m o tipo do objeto this
     * @param ambiente o ambiente de compila��o, contendo o mapeamento
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
     * @param ambiente � o Ambiente de Execu��o
     * @return o valor do Objeto this no ambiente
     */
    private Valor obterValorDoObjetoThisNoAmbiente(AmbienteExecucaoOO1 ambiente)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return ambiente.get(new Id("this"));
    }
}