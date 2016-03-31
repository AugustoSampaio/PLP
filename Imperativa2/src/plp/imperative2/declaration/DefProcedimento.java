package plp.imperative2.declaration;

import plp.expressions1.util.Tipo;
import plp.imperative1.command.Comando;
import plp.imperative2.util.TipoProcedimento;

/**
 * Uma definiçao de procedimento é uma declaraçao de comando e parametrosFormais
 * uma declaração de procedimento.
 */
public class DefProcedimento {

	/**
	 * Declaração dos parametrosFormais
	 */
	private ListaDeclaracaoParametro parametrosFormais;

	/**
	 * Declaraçao de Comando
	 */
	private Comando comando;

	/**
	 * Construtor
	 * 
	 * @param parametrosFormais
	 *            Declaração de ListaDeclaracaoParametro
	 * @param comando
	 *            Declaraçao de Comando.
	 */
	public DefProcedimento(ListaDeclaracaoParametro parametrosFormais,
			Comando comando) {
		this.parametrosFormais = parametrosFormais;
		this.comando = comando;
	}

	/**
	 * Obtém o comando do Procedimento.
	 * 
	 * @return o comando
	 */
	public Comando getComando() {
		return comando;
	}

	/**
	 * Obtém os parametrosFormais do Procedimento.
	 * 
	 * @return a ListaDeclaracaoParametro
	 */
	public ListaDeclaracaoParametro getParametrosFormais() {
		return parametrosFormais;
	}

	public Tipo getTipo() {
		return new TipoProcedimento(parametrosFormais.getTipos());
	}
}
