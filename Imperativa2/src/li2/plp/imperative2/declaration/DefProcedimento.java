package li2.plp.imperative2.declaration;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.memory.InfoEscopo;
import li2.plp.imperative1.command.Comando;
import li2.plp.imperative2.util.TipoProcedimento;

/**
 * Uma definição de procedimento é uma declaração de comando e parâmetros formais.
 */
public class DefProcedimento {

	private ListaDeclaracaoParametro parametrosFormais;
	private Comando comando;
	private InfoEscopo infoEscopo;

	public DefProcedimento(ListaDeclaracaoParametro parametrosFormais, Comando comando) {
		this.parametrosFormais = parametrosFormais;
		this.comando = comando;
	}

	public DefProcedimento(ListaDeclaracaoParametro parametrosFormais, Comando comando, InfoEscopo infoEscopo) {
		this(parametrosFormais, comando);
		this.infoEscopo = infoEscopo;
	}

	public Comando getComando() {
		return comando;
	}

	public InfoEscopo getInfoEscopo() {
		return infoEscopo;
	}

	public ListaDeclaracaoParametro getParametrosFormais() {
		return parametrosFormais;
	}

	public Tipo getTipo() {
		return new TipoProcedimento(parametrosFormais.getTipos());
	}
}
