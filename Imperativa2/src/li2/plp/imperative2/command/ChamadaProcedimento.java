package li2.plp.imperative2.command;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li2.plp.imperative1.command.Comando;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative1.memory.ErroTipoEntradaException;
import li2.plp.imperative1.memory.ListaValor;
import li2.plp.imperative2.declaration.DefProcedimento;
import li2.plp.imperative2.declaration.ListaDeclaracaoParametro;
import li2.plp.imperative2.memory.AmbienteExecucaoImperativa2;
import li2.plp.imperative2.util.TipoProcedimento;

public class ChamadaProcedimento implements Comando {

	private Id nomeProcedimento;

	private ListaExpressao parametrosReais;

	public ChamadaProcedimento(Id nomeProcedimento,
			ListaExpressao parametrosReais) {
		this.nomeProcedimento = nomeProcedimento;
		this.parametrosReais = parametrosReais;
	}

	public AmbienteExecucaoImperativa executar(AmbienteExecucaoImperativa amb)
			throws IdentificadorNaoDeclaradoException,
			IdentificadorJaDeclaradoException, EntradaVaziaException, ErroTipoEntradaException {
		AmbienteExecucaoImperativa2 ambiente = (AmbienteExecucaoImperativa2) amb;
		DefProcedimento procedimento = ambiente
				.getProcedimento(nomeProcedimento);

		/*
		 * o incrementa e o restaura neste ponto servem para criar as variveis
		 * que serao utilizadas pela execucao do procedimento
		 */
		ambiente.incrementa();
		ListaDeclaracaoParametro parametrosFormais = procedimento
				.getParametrosFormais();
		AmbienteExecucaoImperativa2 aux = bindParameters(ambiente,
				parametrosFormais);
		aux = (AmbienteExecucaoImperativa2) procedimento.getComando().executar(
				aux);
		aux.restaura();
		return aux;

	}

	/**
	 * insere no contexto o resultado da associacao entre cada parametro formal
	 * e seu correspondente parametro atual
	 */
	private AmbienteExecucaoImperativa2 bindParameters(
			AmbienteExecucaoImperativa2 ambiente,
			ListaDeclaracaoParametro parametrosFormais)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		ListaValor listaValor = parametrosReais.avaliar(ambiente);
		while (listaValor.length() > 0) {
			ambiente.map(parametrosFormais.getHead().getId(), listaValor
					.getHead());
			parametrosFormais = (ListaDeclaracaoParametro) parametrosFormais
					.getTail();
			listaValor = (ListaValor) listaValor.getTail();
		}
		return ambiente;
	}

	/**
	 * Realiza a verificacao de tipos desta chamada de procedimento, onde os
	 * tipos dos parametros formais devem ser iguais aos tipos dos parametros
	 * reais na ordem em que se apresentam.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            tipos.
	 * @return <code>true</code> se a chamada de procedimeno est� bem tipada;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa amb)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException {

		Tipo tipoProcedimento = amb.get(this.nomeProcedimento);

		TipoProcedimento tipoParametrosReais = new TipoProcedimento(
				parametrosReais.getTipos(amb));
		return tipoProcedimento.eIgual(tipoParametrosReais);

	}

}
