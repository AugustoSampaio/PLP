package loo2.plp.orientadaObjetos2.util;

import loo2.plp.expressions2.expression.Id;


public class SuperClasseMap {
	
	Id classe, superClasse;
	
	public SuperClasseMap(Id classe, Id superClasse){
		this.classe = classe;
		this.superClasse = superClasse; 
	}

	public Id getClasse() {
		return classe;
	}

	public void setClasse(Id classe) {
		this.classe = classe;
	}

	public Id getSuperClasse() {
		return superClasse;
	}

	public void setSuperClasse(Id superClasse) {
		this.superClasse = superClasse;
	}

}
