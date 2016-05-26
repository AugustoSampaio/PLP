package le2.plp.expressions2.declaration;

import java.util.Map;

import le2.plp.expressions1.util.Tipo;
import le2.plp.expressions2.expression.Expressao;
import le2.plp.expressions2.expression.Id;
import le2.plp.expressions2.expression.Valor;
import le2.plp.expressions2.memory.Ambiente;
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

	public void elabora(AmbienteExecucao amb, Map<Id, Valor> declaracoes) {
		if(declaracoes.put(getId(), getExpressao().avaliar(amb))!=null)
			throw new VariavelJaDeclaradaException(getId());
	}
	
	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException {
		if(tipos.put(getId(), getExpressao().getTipo(amb))!=null)
			throw new VariavelJaDeclaradaException(getId());
	}
	
	public boolean checaTipo(AmbienteCompilacao amb){
		return getExpressao().checaTipo(amb);
	}
	
	public void reduzir(AmbienteExecucao amb){
		amb.map(getId(), null);
	}

	public void incluir(AmbienteExecucao amb, Map<Id, Valor> declaracoes) throws VariavelJaDeclaradaException {
		amb.map(getId(), declaracoes.get(getId()));
	}

	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException {
		amb.map(getId(), tipos.get(getId()));
	}

}
