package le1.plp.expressions2.memory;

import java.util.HashMap;
import java.util.Stack;

/**
 * Classe abstrata que representa um contexto
 * 
 * @author eagt
 * 
 */
public class Contexto<T> {
	protected Stack<HashMap<String, T>> pilha;
	protected final SnapshotPilha<T> snapshot;

	public Contexto() {
		pilha = new Stack<HashMap<String, T>>();
		snapshot = new SnapshotPilha<T>();
	}


	public void incrementa() {
		pilha.push(new HashMap<String, T>());
		snapshot.incrementa();
	}

	public void restaura() {
		pilha.pop();
		snapshot.restaura();
	}

	public void map(String idArg, T valorId) {
		HashMap<String, T> aux = pilha.peek();
		aux.put(idArg, valorId);
		snapshot.map(idArg, valorId);
	}

	protected Stack<HashMap<String, T>> getPilha() {
		return pilha;
	}

	protected void setPilha(Stack<HashMap<String, T>> pilha) {
		this.pilha = pilha;
	}

	public java.util.List<java.util.Map<String, Object>> getPilhaSnapshot() {
		return snapshot.getPilhaSnapshot();
	}
}
