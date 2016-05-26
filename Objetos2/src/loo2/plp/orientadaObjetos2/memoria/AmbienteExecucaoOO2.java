package loo2.plp.orientadaObjetos2.memoria;

import java.util.ArrayList;

import loo2.plp.expressions2.expression.Id;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos2.util.SuperClasseMap;

public interface AmbienteExecucaoOO2 extends AmbienteExecucaoOO1{

	/**
	 * Mapei uma classe com a sua super classe
	 * @param classe
	 * @param superClasse
	 * @throws ClasseNaoDeclaradaException Caso a classe mae n�o tiver sido declarada.
	 */
	public void mapSuperClasse(Id classe, Id superClasse) throws ClasseNaoDeclaradaException;
	
	/**
	 * Dado um identificador da classe, recupera a super-classe
	 * @param classe identificador da classe base
	 * @return Definicao da super classe
	 * @throws ClasseNaoDeclaradaException Quando a classe ainda nao foi definida
	 */
	public SuperClasseMap getSuperClasse(Id classe) throws ClasseNaoDeclaradaException;

	/**
	 * Retorna todos os mapeamentos de heran�a do ambiente de execucao
	 * @return
	 */
	public ArrayList<SuperClasseMap> getMapSuperClasse();	
}
