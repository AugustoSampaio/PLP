package loo2.plp.orientadaObjetos1.memoria;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Um frame individual dentro do snapshot de compilação.
 * Representa um escopo com sua posição no fonte, tipo e bindings declarados.
 */
public class QuadroEscopo {
	private final String nome;
	private final String escopo;
	private final TrechoCodigoFonte trechoCodigoFonte;
	private final Map<String, InfoBinding> bindings;

	public QuadroEscopo(String escopo, TrechoCodigoFonte trechoCodigoFonte) {
		this(null, escopo, trechoCodigoFonte);
	}

	public QuadroEscopo(String nome, String escopo, TrechoCodigoFonte trechoCodigoFonte) {
		this.nome = nome;
		this.escopo = escopo;
		this.trechoCodigoFonte = trechoCodigoFonte;
		this.bindings = new LinkedHashMap<String, InfoBinding>();
	}

	public void adicionaBinding(String nome, InfoBinding binding) {
		bindings.put(nome, binding);
	}

	public String getNome() { return nome; }
	public String getEscopo() { return escopo; }
	public TrechoCodigoFonte getTrechoCodigoFonte() { return trechoCodigoFonte; }
	public Map<String, InfoBinding> getBindings() { return bindings; }
}
