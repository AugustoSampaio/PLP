package lf1.plp.functional1.memory;

import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.memory.ContextoExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.util.DefFuncao;

public class ContextoExecucaoFuncional implements AmbienteExecucaoFuncional {

	private ContextoExecucao contextoExecucao;
	private ContextoFuncional contextoFuncional;
	
	/**
	 * Construtor da classe.
	 */
	public ContextoExecucaoFuncional() {
		 contextoExecucao = new ContextoExecucao();
		 contextoFuncional = new ContextoFuncional();
	}

	public void incrementa() {
		contextoExecucao.incrementa();
		contextoFuncional.incrementa();
	}

	public void restaura() {
		contextoExecucao.restaura();
		contextoFuncional.restaura();
	}
	
	/**
	 * Mapeia o id no valor dado.
	 * 
	 * @exception VariavelJaDeclaradaException
	 *                se j� existir um mapeamento do identificador nesta tabela.
	 */
	public void map(Id idArg, Valor tipoId) throws VariavelJaDeclaradaException {
		contextoExecucao.map(idArg, tipoId);
	}
	
	/**
	 * Retorna o valor mapeado ao id dado.
	 * 
	 * @exception VariavelNaoDeclaradaException
	 *                se n�o existir nenhum valor mapeado ao id dado nesta
	 *                tabela.
	 */
	public Valor get(Id idArg) throws VariavelNaoDeclaradaException {
		return contextoExecucao.get(idArg);
	}
	
	// Remover esse m�todo. J� tem em ContextoExecucao
	/**
	 * Mapeia um identificador em uma funcao.
	 * 
	 * @param idArg
	 *            o identificador
	 * @param funcao
	 *            a funcao.
	 * @exception VariavelJaDeclaradaException
	 *                se o id ja' estiver declarado.
	 */
	public void mapFuncao(Id idArg, DefFuncao funcao)
			throws VariavelJaDeclaradaException {
		contextoFuncional.map(idArg, funcao);
	}

	// Remover esse m�todo. J� tem em ContextoExecucao
	/**
	 * Retorna uma funcao.
	 * 
	 * @param idArg
	 *            o identificador que mapeia a funcao
	 * @param funcao
	 *            a funcao.
	 * @exception VariavelNaoDeclaradaException
	 *                se o id nao estiver declarado.
	 */
	public DefFuncao getFuncao(Id idArg) throws VariavelNaoDeclaradaException {
		return contextoFuncional.get(idArg);
	}
	
	public ContextoExecucaoFuncional clone() {
		return this;
	}
}
