/*
 * Universidade Federal de Pernambuco - UFPE
 * Centro de Inform�tica - CIn
 * 
 * Paradigmas de Linguagem de Programa��o - PLP
 * 
 * Tipo: AmbienteFuncional
 */
package lf1.plp.functional1.memory;

import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import lf1.plp.expressions2.memory.IdentificadorNaoDeclaradoException;


/**
 * Esta interface define um ambiente funcional.
 * 
 * @author Joabe Jesus
 *
 * @param <T> A classe que representa uma fun��o na linguagem.
 */
public interface AmbienteFuncional<T> {

	/**
	 * Este m�todo mapeia um identificador em uma fun��o.
	 *
	 * @param id O identificador
	 * @param funcao A fun��o mapeada.
	 * 
	 * @throws IdentificadorJaDeclaradoException Se j� houver uma fun��o declarada
	 *                                       com o id fornecido.
	 */
	public void mapFuncao(Id id, T funcao)
			throws IdentificadorJaDeclaradoException;

	/**
	 * Este m�todo retorna a fun��o cujo id foi dado.
	 *
	 * @param id O identificador que mapeia a fun��o.
	 * 
	 * @throws IdentificadorNaoDeclaradoException Se a fun��o n�o estiver declarada.
	 */
	public T getFuncao(Id id) throws IdentificadorNaoDeclaradoException;

}
