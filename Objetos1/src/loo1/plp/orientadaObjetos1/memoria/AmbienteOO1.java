package loo1.plp.orientadaObjetos1.memoria;

import loo1.plp.expressions2.expression.Id;
import loo1.plp.expressions2.memory.Ambiente;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;

/**
 * Interface que representa um ambiente.
 */
public interface AmbienteOO1<T> extends Ambiente<T>{
	
	/**
	 * Mapeia um identificador a um defini��o de classe.
	 * 
	 * @param idArg
	 *            o nome da classe
	 * @param defClasse
	 *            Defini��o da Classe.
	 * @throws ClasseJaDeclaradaException
	 *             quando a classe j� foi declarada.
	 */
	public void mapDefClasse(Id idArg, DefClasse defClasse)
			throws ClasseJaDeclaradaException;

	/**
	 * Obt�m a defini��o da classe cujo nome � idArg
	 * 
	 * @param idArg
	 *            Nome da classe.
	 * @return a defini��o da classe.
	 * @throws ClasseNaoDeclaradaException
	 *             quando nao foi declarada nenhuma classe com esse nome.
	 */
	public DefClasse getDefClasse(Id idArg) throws ClasseNaoDeclaradaException;
	
	
}