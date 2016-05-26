package loo2.plp.orientadaObjetos2.memoria;

import loo2.plp.orientadaObjetos1.declaracao.procedimento.DecProcedimento;
import loo2.plp.orientadaObjetos1.declaracao.variavel.DecVariavel;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.memoria.DefClasse;
import loo2.plp.orientadaObjetos2.declaracao.DecConstrutor;

public class DefClasseOO2 extends DefClasse {

	/**
	 * Nome super classe
	 */
	private Id nomeSuperClasse;
	
	/**
	 * Consturtor
	 */
	private DecConstrutor construtor;

	public DefClasseOO2(Id idClasse, Id nomeSuperClasse,DecVariavel decVariavel,
			DecConstrutor construtor, DecProcedimento decProcedimento) {
		super(idClasse, decVariavel, decProcedimento);
		this.nomeSuperClasse = nomeSuperClasse;
		this.construtor = construtor;
	}

	public DecConstrutor getConstrutor() {
		return construtor;
	}

	public void setConstrutor(DecConstrutor construtor) {
		this.construtor = construtor;
	}

	/**
	 * @return the nomeSuperClasse
	 */
	public Id getNomeSuperClasse() {
		return nomeSuperClasse;
	}

	/**
	 * @param nomeSuperClasse the nomeSuperClasse to set
	 */
	public void setNomeSuperClasse(Id nomeSuperClasse) {
		this.nomeSuperClasse = nomeSuperClasse;
	}
}
