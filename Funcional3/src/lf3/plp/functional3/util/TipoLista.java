package lf3.plp.functional3.util;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.functional1.util.TipoPolimorfico;

public class TipoLista implements Tipo {

	/**
	 * Subtipo - tipo dos elementos da lista
	 */
	private Tipo subTipo;

	/**
	 * cria uma inst�ncia de tipoLista sem subTipo
	 * 
	 */
	public TipoLista() {
		this.subTipo = new TipoPolimorfico();
	}

	/**
	 * cria uma inst�ncia de tipoLista com o seu subtipo
	 * 
	 * @param subTipo
	 */
	public TipoLista(Tipo subTipo) {
		this.subTipo = subTipo;
	}

	/**
	 * Retorna o tipo dos elementos de uma lista
	 * 
	 * @return tipo dos elementos da lista
	 */
	public Tipo getSubTipo() {
		return subTipo;
	}

	public boolean eBooleano() {
		return false;
	}

	public boolean eIgual(Tipo tipo) {
		if (tipo instanceof TipoLista) {
			TipoLista lista = (TipoLista) tipo;
			return lista.subTipo.eIgual(this.subTipo);
		}

		return tipo.eIgual(this);
	}

	public boolean eInteiro() {
		return false;
	}

	public boolean eString() {
		return false;
	}

	public boolean eValido() {
		return subTipo.eValido();
	}

	public String getNome() {
		return "[" + subTipo.getNome() + "]";
	}

	public Tipo intersecao(Tipo outroTipo) {
		if (outroTipo instanceof TipoLista) {
			TipoLista outraLista = (TipoLista) outroTipo;
			return this.subTipo.intersecao(outraLista.subTipo);
		}

		return outroTipo.intersecao(this);
	}

	@Override
	public String toString() {
		return "[" + subTipo.getNome() + "]";
	}

}
