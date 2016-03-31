package plp.orientadaObjetos1.memoria;

import plp.expressions2.expression.Id;
import plp.expressions2.memory.Ambiente;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;

/**
 * Interface que representa um ambiente.
 */
public interface AmbienteOO1<T> extends Ambiente<T>{
	
	/**
	 * Mapeia um identificador a um definição de classe.
	 * 
	 * @param idArg
	 *            o nome da classe
	 * @param defClasse
	 *            Definição da Classe.
	 * @throws ClasseJaDeclaradaException
	 *             quando a classe já foi declarada.
	 */
	public void mapDefClasse(Id idArg, DefClasse defClasse)
			throws ClasseJaDeclaradaException;

	/**
	 * Obtém a definição da classe cujo nome é idArg
	 * 
	 * @param idArg
	 *            Nome da classe.
	 * @return a definição da classe.
	 * @throws ClasseNaoDeclaradaException
	 *             quando nao foi declarada nenhuma classe com esse nome.
	 */
	public DefClasse getDefClasse(Id idArg) throws ClasseNaoDeclaradaException;
	
	
}