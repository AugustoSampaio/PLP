package plp.imperative1.command;

import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Id;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.imperative1.memory.AmbienteCompilacaoImperativa;
import plp.imperative1.memory.AmbienteExecucaoImperativa;

public class Atribuicao implements Comando {

	private Id id;

	private Expressao expressao;

	public Atribuicao(Id id, Expressao expressao) {
		this.id = id;
		this.expressao = expressao;
	}

	/**
	 * Executa a atribuição.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            valores.
	 * 
	 * @return o ambiente modificado pela execução da atribuição.
	 * 
	 */
	public AmbienteExecucaoImperativa executar(
			AmbienteExecucaoImperativa ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		ambiente.changeValor(id, expressao.avaliar(ambiente));
		return ambiente;
	}

	/**
	 * Um comando de atribuição está bem tipado, se o tipo do identificador é o
	 * mesmo da expressão. O tipo de um identificador é determinado pelo tipo da
	 * expressão que o inicializou (na declaração).
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            valores.
	 * 
	 * @return <code>true</code> se os tipos da atribuição são válidos;
	 *         <code>false</code> caso contrario.
	 * 
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return expressao.checaTipo(ambiente)
				&& id.getTipo(ambiente).eIgual(expressao.getTipo(ambiente));
	}

}
