package le2.plp.expressions2.declaration;

import java.util.Map;

import le2.plp.expressions1.util.Tipo;
import le2.plp.expressions2.expression.Id;
import le2.plp.expressions2.expression.Valor;
import le2.plp.expressions2.memory.AmbienteCompilacao;
import le2.plp.expressions2.memory.AmbienteExecucao;
import le2.plp.expressions2.memory.VariavelJaDeclaradaException;

public class DecComposta implements Declaracao {
	private Declaracao d1;
	private Declaracao d2;
	
	public DecComposta(Declaracao d1, Declaracao d2){
		this.d1=d1;
		this.d2=d2;
	}
	
	public void elabora(AmbienteExecucao amb, Map<Id, Valor> declaracoes) {
		d1.elabora(amb, declaracoes);
		d2.elabora(amb, declaracoes);
	}
	
	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) {
		d1.elabora(amb, tipos);
		d2.elabora(amb, tipos);
	}
	
	public boolean checaTipo(AmbienteCompilacao amb){
		return (d1.checaTipo(amb) && d2.checaTipo(amb));
	}
	
	public void reduzir(AmbienteExecucao amb){
		d1.reduzir(amb);
		d2.reduzir(amb);
	}

	public void incluir(AmbienteExecucao amb, Map<Id, Valor> declaracoes) throws VariavelJaDeclaradaException {
		d1.incluir(amb,declaracoes);
		d2.incluir(amb,declaracoes);
	}

	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException {
		d1.incluir(amb,tipos);
		d2.incluir(amb,tipos);
	}
}
