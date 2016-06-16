package le2.plp.expressions2.declaration;

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
	
	public void elabora(AmbienteExecucao amb, AmbienteExecucao aux) {
		d1.elabora(amb, aux);
		d2.elabora(amb, aux);
	}
	
	public void elabora(AmbienteCompilacao amb, AmbienteCompilacao aux) {
		d1.elabora(amb, aux);
		d2.elabora(amb, aux);
	}
	
	public boolean checaTipo(AmbienteCompilacao amb){
		return (d1.checaTipo(amb) && d2.checaTipo(amb));
	}
	
	public void reduzir(AmbienteExecucao amb){
		d1.reduzir(amb);
		d2.reduzir(amb);
	}

	public void incluir(AmbienteExecucao amb, AmbienteExecucao aux) throws VariavelJaDeclaradaException {
		d1.incluir(amb,aux);
		d2.incluir(amb,aux);
	}

	public void incluir(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException {
		d1.incluir(amb,aux);
		d2.incluir(amb,aux);
	}
}
