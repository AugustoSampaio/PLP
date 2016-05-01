package plp.expressions2.declaration;

import java.util.Map;

import plp.expressions2.expression.Id;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;

public class DecComposta implements Declaracao {
	private Declaracao d1;
	private Declaracao d2;
	
	public DecComposta(Declaracao d1, Declaracao d2){
		this.d1=d1;
		this.d2=d2;
	}
	
	public void elabora(AmbienteExecucao amb, Map<Id, Valor> novasDeclaracoes) {
		d1.elabora(amb, novasDeclaracoes);
		d2.elabora(amb, novasDeclaracoes);
	}
	
	public boolean checaTipo(AmbienteCompilacao amb){
		return (d1.checaTipo(amb) && d2.checaTipo(amb));
	}
	
	public void reduzir(AmbienteExecucao amb){
		d1.reduzir(amb);
		d2.reduzir(amb);
	}
}
