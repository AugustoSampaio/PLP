package plp.orientadaObjetos1.memoria;

import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.comando.Procedimento;
import plp.orientadaObjetos1.declaracao.procedimento.DecProcedimento;
import plp.orientadaObjetos1.declaracao.variavel.DecVariavel;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos1.util.Tipo;

/**
 * Uma definiçao de classe é uma declaraçao de variável e uma declaração de
 * procedimento. Ambos podem ser simples ou compostos.
 */
public class DefClasse {

	/**
	 * Declaração de variável
	 */
	protected DecVariavel decVariavel;
	/**
	 * Declaraçao do Procedimento
	 */
	protected DecProcedimento decProcedimento;

	/**
	 * Declaracao de id
	 */
	protected Id idClasse;

	public DefClasse(Id idClasse, DecVariavel decVariavel, DecProcedimento decProcedimento) {
		this.idClasse = idClasse;
		this.decVariavel = decVariavel;
		this.decProcedimento = decProcedimento;
	}

	/**
	 * Obtém as declaraçoes das variáveis.
	 * 
	 * @return as declaraçoes das variáveis.
	 */
	public DecVariavel getDecVariavel() {
		return decVariavel;
	}

	/**
	 * Retorna um método da classe a partir de seu identificador.
	 * 
	 * @param idMetodo
	 *            Identificador do método
	 * @return o método desejado
	 * @throws ProcedimentoNaoDeclaradoException
	 */
	public Procedimento getMetodo(Id idMetodo)
			throws ProcedimentoNaoDeclaradoException {
		return decProcedimento.getProcedimento(idMetodo);
	}

	/**
	 * O método abaixo deve verificar se existe algum atributo, identificado por
	 * idAtributo na definicao da classe
	 * 
	 * @param idAtributo
	 *            Um identificador de atributo.
	 * @param Tipo
	 *            O tipo do atributo do identificador.
	 */
	public Tipo getTipoAtributo(Id idAtributo)
			throws VariavelNaoDeclaradaException {
		return decVariavel.getTipo(idAtributo);
	}

	public Id getIdClasse() {
		return idClasse;
	}
}