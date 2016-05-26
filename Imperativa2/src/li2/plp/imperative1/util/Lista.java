package li2.plp.imperative1.util;

public class Lista<T> {
	protected T head;
	protected Lista<T> tail;

	public Lista() {
		head = null;
		tail = null;
	}

	public Lista(T valor, Lista<T> lista) {
		this.head = valor;
		this.tail = lista;
	}

	public int length() {

		if (head == null)
			return 0;
		else if (tail == null)
			return 1;
		else
			return 1 + tail.length();

	}

	public T getHead() {
		return head;
	}

	public Lista<T> getTail() {
		return tail;
	}

	@Override
	public String toString() {
		StringBuffer resposta = new StringBuffer();
		this.formatar(resposta);
		return resposta.toString();
	}

	private void formatar(StringBuffer resposta) {
		if (head != null) {
			resposta.append(head.toString());
			if (tail != null) {
				resposta.append(" ");
				tail.formatar(resposta);
			}
		}
	}

}
