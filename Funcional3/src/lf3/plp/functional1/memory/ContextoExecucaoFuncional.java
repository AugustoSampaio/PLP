package lf3.plp.functional1.memory;

import java.util.HashMap;
import java.util.Stack;

import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.memory.ContextoExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional1.util.DefFuncao;

public class ContextoExecucaoFuncional extends ContextoExecucao
		implements AmbienteExecucaoFuncional {

	/**
	 * A pilha de blocos de funcao deste contexto.
	 */
	private Stack<HashMap<Id, DefFuncao>> pilhaFuncao;

	/**
	 * Construtor da classe.
	 */
	public ContextoExecucaoFuncional() {
		pilhaFuncao = new Stack<HashMap<Id, DefFuncao>>();
	}

	public void incrementa() {
		super.incrementa();
		pilhaFuncao.push(new HashMap<Id, DefFuncao>());
	}

	public void restaura() {
		super.restaura();
		pilhaFuncao.pop();
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
		HashMap<Id, DefFuncao> aux = pilhaFuncao.peek();
		if (aux.put(idArg, funcao) != null) {
			throw new VariavelJaDeclaradaException(idArg);
		}
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
		DefFuncao result = null;
		Stack<HashMap<Id, DefFuncao>> auxStack =
			new Stack<HashMap<Id, DefFuncao>>();
		while (result == null && !pilhaFuncao.empty()) {
			HashMap<Id, DefFuncao> aux = pilhaFuncao.pop();
			auxStack.push(aux);
			result = aux.get(idArg);
		}
		while (!auxStack.empty()) {
			pilhaFuncao.push(auxStack.pop());
		}
		if (result == null) {
			throw new VariavelNaoDeclaradaException(idArg);
		}

		return result;
	}
	
	public ContextoExecucaoFuncional clone() {
		return this;
	}
}
