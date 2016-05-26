package li2.plp.imperative2.command;

import java.util.LinkedList;
import java.util.List;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.expression.Expressao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.ListaValor;
import li2.plp.imperative1.util.Lista;

public class ListaExpressao extends Lista<Expressao> {

	public ListaExpressao() {

	}

	public ListaExpressao(Expressao expressao) {
		super(expressao, new ListaExpressao());
	}

	public ListaExpressao(Expressao expressao, ListaExpressao listaExpressao) {
		super(expressao, listaExpressao);
	}

	public ListaValor avaliar(AmbienteExecucaoImperativa ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		if (length() >= 2)
			return new ListaValor(getHead().avaliar(ambiente),
					((ListaExpressao) getTail()).avaliar(ambiente));
		else if (length() == 1)
			return new ListaValor(getHead().avaliar(ambiente));
		else
			return new ListaValor();
	}

	public List<Tipo> getTipos(AmbienteCompilacaoImperativa ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		List<Tipo> result = new LinkedList<Tipo>();

		if (this.length() >= 2) {
			result.add(getHead().getTipo(ambiente));
			result.addAll(((ListaExpressao) getTail()).getTipos(ambiente));
		} else if (length() == 1) {
			result.add(getHead().getTipo(ambiente));
		}
		return result;
	}

}
