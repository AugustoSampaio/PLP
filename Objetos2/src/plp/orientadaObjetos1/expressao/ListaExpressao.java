package plp.orientadaObjetos1.expressao;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.imperative1.util.Lista;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.memoria.colecao.ListaValor;
import plp.orientadaObjetos1.util.ListaTipo;

/**
 * Classe que representa uma tail de expressões.
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
	 *            Expressão que compoe a tail.
	 */
	public ListaExpressao(Expressao expressao) {
		super(expressao, new ListaExpressao());
	}

	/**
	 * Construtor.
	 * 
	 * @param expressao
	 *            Primeira expressão da tail.
	 * @param listaExpressao
	 *            Restante da tail de expressoes.
	 */
	public ListaExpressao(Expressao expressao, ListaExpressao listaExpressao) {
		super(expressao, listaExpressao);
	}

	/**
	 * Avalia a tail de expressões.
	 * 
	 * @param ambiente
	 *            O ambiente de execuçao, contendo o mapeamento entre
	 *            identificadores e valores.
	 * @return a tail de valores resultantes da avaliaçao dessa tail de
	 *         expressoes.
	 * @throws VariavelNaoDeclaradaException
	 *             Quando alguma das variáveis presentes na tail nao foi
	 *             declarada.
	 * @throws VariavelJaDeclaradaException
	 *             Quando alguma das variáveis referenciada na tail está sendo
	 *             declarada novamente num mesmo escopo.
	 * @throws ObjetoNaoDeclaradoException
	 *             Quando um objeto que está sendo referenciado nao foi
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
	 * Obtém a tail dos tipos dos elementos da tail de expressoes.
	 * 
	 * @param ambiente
	 *            o ambiente de compilaçao, contendo o mapeamento entre
	 *            identificadores e tipos.
	 * @return a tail dos tipos dos elementos da tail de expressoes.
	 * @throws VariavelNaoDeclaradaException
	 *             Quando uma variável sendo referenciada nao foi declarada.
	 * @throws VariavelJaDeclaradaException
	 *             Quando uma variável está sendo declarada mais de uma vez num
	 *             mesmo escopo.
	 * @throws ClasseNaoDeclaradaException
	 *             Quando uma determinada classe que está sendo usada nao foi
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
