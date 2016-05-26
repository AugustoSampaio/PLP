package loo2.plp.orientadaObjetos1.expressao;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.imperative1.util.Lista;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor;
import loo2.plp.orientadaObjetos1.util.ListaTipo;

/**
 * Classe que representa uma tail de express�es.
 */
public class ListaExpressao extends Lista<Expressao> {
	/**
	 * Construtor.
	 */
	public ListaExpressao() {

	}

	/**
	 * Construtor.
	 * 
	 * @param expressao
	 *            Express�o que compoe a tail.
	 */
	public ListaExpressao(Expressao expressao) {
		super(expressao, new ListaExpressao());
	}

	/**
	 * Construtor.
	 * 
	 * @param expressao
	 *            Primeira express�o da tail.
	 * @param listaExpressao
	 *            Restante da tail de expressoes.
	 */
	public ListaExpressao(Expressao expressao, ListaExpressao listaExpressao) {
		super(expressao, listaExpressao);
	}

	/**
	 * Avalia a tail de express�es.
	 * 
	 * @param ambiente
	 *            O ambiente de execu�ao, contendo o mapeamento entre
	 *            identificadores e valores.
	 * @return a tail de valores resultantes da avalia�ao dessa tail de
	 *         expressoes.
	 * @throws VariavelNaoDeclaradaException
	 *             Quando alguma das vari�veis presentes na tail nao foi
	 *             declarada.
	 * @throws VariavelJaDeclaradaException
	 *             Quando alguma das vari�veis referenciada na tail est� sendo
	 *             declarada novamente num mesmo escopo.
	 * @throws ObjetoNaoDeclaradoException
	 *             Quando um objeto que est� sendo referenciado nao foi
	 *             declarado.
	 * @throws ClasseNaoDeclaradaException 
	 */
	public ListaValor avaliar(AmbienteExecucaoOO1 ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
			ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException {
		if (length() >= 2) {
			return new ListaValor(getHead().avaliar(ambiente),
					((ListaExpressao) getTail()).avaliar(ambiente));
		} else if (length() == 1) {
			return new ListaValor(getHead().avaliar(ambiente));
		} else {
			return new ListaValor();
		}
	}

	/**
	 * Obt�m a tail dos tipos dos elementos da tail de expressoes.
	 * 
	 * @param ambiente
	 *            o ambiente de compila�ao, contendo o mapeamento entre
	 *            identificadores e tipos.
	 * @return a tail dos tipos dos elementos da tail de expressoes.
	 * @throws VariavelNaoDeclaradaException
	 *             Quando uma vari�vel sendo referenciada nao foi declarada.
	 * @throws VariavelJaDeclaradaException
	 *             Quando uma vari�vel est� sendo declarada mais de uma vez num
	 *             mesmo escopo.
	 * @throws ClasseNaoDeclaradaException
	 *             Quando uma determinada classe que est� sendo usada nao foi
	 *             declarada.
	 */
	public ListaTipo getTipos(AmbienteCompilacaoOO1 ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
			ClasseNaoDeclaradaException {
		ListaTipo resposta;
		if (length() >= 2) {
			resposta = new ListaTipo(getHead().getTipo(ambiente),
					((ListaExpressao) getTail()).getTipos(ambiente));
		} else if (length() == 1) {
			resposta = new ListaTipo(getHead().getTipo(ambiente));
		} else {
			resposta = new ListaTipo();
		}
		return resposta;
	}
}
