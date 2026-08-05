package lf2.plp.expressions2.memory;

/**
 * Informações de um binding (variável/função) dentro de um escopo.
 */
public class InfoBinding {
	private final String tipo;
	private final String valor;

	public InfoBinding(String tipo, String valor) {
		this.tipo = tipo;
		this.valor = valor;
	}

	public String getTipo() { return tipo; }
	public String getValor() { return valor; }
}
