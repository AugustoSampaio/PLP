package le2.plp.expressions2.declaration;

import le2.plp.expressions2.expression.Expressao;
import le2.plp.expressions2.expression.Id;
import le2.plp.expressions2.memory.AmbienteCompilacao;
import le2.plp.expressions2.memory.AmbienteExecucao;
import le2.plp.expressions2.memory.VariavelJaDeclaradaException;

public class DecVariavel implements Declaracao {
	private Id id;
	private Expressao expressao;
	
	public DecVariavel(Id idArg, Expressao expressaoArg) {
		id = idArg;
		expressao = expressaoArg;
	}
	
	public Id getId() {
		return id;
	}
	public Expressao getExpressao() {
		return expressao;
	}

	public void elabora(AmbienteExecucao amb, AmbienteExecucao aux) {
		aux.map(getId(), getExpressao().avaliar(amb));
	}
	
	public void elabora(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException {
		aux.map(getId(), getExpressao().getTipo(amb));
	}
	
	public boolean checaTipo(AmbienteCompilacao amb){
		return getExpressao().checaTipo(amb);
	}
	
	public void reduzir(AmbienteExecucao amb){
		amb.map(getId(), null);
	}

	public void incluir(AmbienteExecucao amb, AmbienteExecucao aux) throws VariavelJaDeclaradaException {
		amb.map(getId(), aux.get(getId()));
	}

	public void incluir(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException {
		amb.map(getId(), aux.get(getId()));
	}

}
