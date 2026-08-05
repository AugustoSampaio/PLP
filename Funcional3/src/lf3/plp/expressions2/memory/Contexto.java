package lf3.plp.expressions2.memory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import lf3.plp.expressions2.expression.Id;

/**
 * Classe que representa um contexto de compilação/execução.
 */
public class Contexto<T> {
	protected Stack<HashMap<Id, T>> pilha;
	protected final MetadadosDepuracao<T> metadadosDepuracao;

	public Contexto() {
		pilha = new Stack<HashMap<Id, T>>();
		metadadosDepuracao = new MetadadosDepuracao<T>();
	}

	public void incrementa() {
		pilha.push(new HashMap<Id, T>());
		metadadosDepuracao.getPilhaSnapshot().incrementa();
	}

	public void registraEscopo(InfoEscopo info) {
		metadadosDepuracao.getPilhaSnapshot().registraEscopo(info);
	}

	public void restaura() {
		pilha.pop();
		metadadosDepuracao.getPilhaSnapshot().restaura();
	}

	public void map(Id idArg, T valorId) throws VariavelJaDeclaradaException {
		try {
			HashMap<Id, T> aux = pilha.peek();
			if (aux.put(idArg, valorId) != null)
				throw new IdentificadorJaDeclaradoException();
			metadadosDepuracao.getPilhaSnapshot().map(idArg == null ? "null" : idArg.toString(), valorId);
		} catch (IdentificadorJaDeclaradoException e) {
			throw new VariavelJaDeclaradaException(idArg);
		}
	}

	public T get(Id idArg) throws VariavelNaoDeclaradaException {
		try {
			T result = null;
			Stack<HashMap<Id, T>> auxStack = new Stack<HashMap<Id, T>>();
			while (result == null && !pilha.empty()) {
				HashMap<Id, T> aux = pilha.pop();
				auxStack.push(aux);
				result = aux.get(idArg);
			}
			while (!auxStack.empty()) {
				pilha.push(auxStack.pop());
			}
			if (result == null)
				throw new IdentificadorNaoDeclaradoException();
			return result;
		} catch (IdentificadorNaoDeclaradoException e) {
			throw new VariavelNaoDeclaradaException(idArg);
		}
	}

	protected Stack<HashMap<Id, T>> getPilha() {
		return pilha;
	}

	protected void setPilha(Stack<HashMap<Id, T>> pilha) {
		this.pilha = pilha;
	}

	public List<Map<String, Object>> getPilhaSnapshot() {
		return metadadosDepuracao.toSnapshot();
	}
}
