/*
 * Universidade Federal de Pernambuco - UFPE
 * Centro de Informática - CIn
 * 
 * Paradigmas de Linguagem de Programação - PLP
 * 
 * Tipo: AmbienteFuncional
 */
package plp.functional1.memory;

import plp.expressions2.expression.Id;
import plp.expressions2.memory.IdentificadorJaDeclaradoException;
import plp.expressions2.memory.IdentificadorNaoDeclaradoException;


/**
 * Esta interface define um ambiente funcional.
 * 
 * @author Joabe Jesus
 *
 * @param <T> A classe que representa uma função na linguagem.
 */
public interface AmbienteFuncional<T> {

	/**
	 * Este método mapeia um identificador em uma função.
	 *
	 * @param id O identificador
	 * @param funcao A função mapeada.
	 * 
	 * @throws IdentificadorJaDeclaradoException Se já houver uma função declarada
	 *                                       com o id fornecido.
	 */
	public void mapFuncao(Id id, T funcao)
			throws IdentificadorJaDeclaradoException;

	/**
	 * Este método retorna a função cujo id foi dado.
	 *
	 * @param id O identificador que mapeia a função.
	 * 
	 * @throws IdentificadorNaoDeclaradoException Se a função não estiver declarada.
	 */
	public T getFuncao(Id id) throws IdentificadorNaoDeclaradoException;

}
