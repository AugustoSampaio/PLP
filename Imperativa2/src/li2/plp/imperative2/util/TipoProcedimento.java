package li2.plp.imperative2.util;

import static li2.plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.List;

import li2.plp.expressions1.util.Tipo;

public class TipoProcedimento implements Tipo {

	private List<Tipo> tiposParametrosFormais = new ArrayList<Tipo>();

	public TipoProcedimento(List<Tipo> tiposParametrosFormais2) {
		this.tiposParametrosFormais.addAll(tiposParametrosFormais2);
	}

	public boolean eBooleano() {
		return false;
	}

	public boolean eIgual(Tipo tipo) {
		if (tipo instanceof TipoProcedimento) {
			TipoProcedimento tipoProc = (TipoProcedimento) tipo;
			return tipoProc.tiposParametrosFormais
					.equals(this.tiposParametrosFormais);
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
		boolean retorno = true;
		for (Tipo tipo : tiposParametrosFormais) {
			retorno &= tipo.eValido();
		}

		return retorno;
	}

	public String getNome() {
		return listToString(this.tiposParametrosFormais, "{", "}", ",");
	}

	public Tipo intersecao(Tipo outroTipo) {
		if (outroTipo.eIgual(this))
			return this;
		else
			return null;
	}

}
