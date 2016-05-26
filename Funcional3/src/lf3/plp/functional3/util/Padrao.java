package lf3.plp.functional3.util;

import static lf3.plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.List;

import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.functional3.util.padrao.ExpPadrao;

public class Padrao {
	
	@SuppressWarnings("unchecked")
	private List<ExpPadrao> listaExpPadrao;
	
	@SuppressWarnings("unchecked")
	public Padrao(List<ExpPadrao> listaExpPadrao) {
		super();
		this.listaExpPadrao = listaExpPadrao;
	}
	
	@SuppressWarnings("unchecked")
	public static Padrao createPadraoFrom(List<Expressao> listaExpressoes) {
		List<ExpPadrao> listaExpPadrao = new ArrayList<ExpPadrao>();
		
		for ( Expressao expressao : listaExpressoes ) {
			ExpPadrao expPadrao = ExpPadrao.createExpPadraoFrom(expressao);
			listaExpPadrao.add(expPadrao);
		}
		return new Padrao(listaExpPadrao);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExpPadrao> getListaExpPadrao() {
		return this.listaExpPadrao;
	}
	
	@SuppressWarnings("unchecked")
	public List<Id> getListaIds() {
		List<ExpPadrao> list = this.getListaExpPadrao();
		List<Id> result = new ArrayList<Id>();
		
		for ( ExpPadrao expressao : list ) {
			if ( expressao.getAridade() > 0 ) {
				Id id = (Id) expressao.getExpressao();
				result.add(id);
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public boolean match(AmbienteExecucao ambiente, List<Expressao> argumentos) {
		ExpPadrao expPadrao;
		Expressao argumento;
		
		for ( int i = 0; i < this.listaExpPadrao.size(); i++ ) {
			expPadrao = this.listaExpPadrao.get(i);
			argumento = argumentos.get(i);
			
			if ( !expPadrao.match(ambiente, argumento) ) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return listToString(this.listaExpPadrao, " ");
	}
	
	public Padrao clone() {
		List<ExpPadrao> novaLista = new ArrayList<ExpPadrao>(this.listaExpPadrao.size());
		
		for (ExpPadrao exp : this.listaExpPadrao) {
			novaLista.add(exp.clone());
		}
		
		return new Padrao(novaLista);
	}
}
