package plp.expressions2.declaration;

import java.util.Map;

import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Id;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.VariavelJaDeclaradaException;

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
	
	public boolean checaTipo(AmbienteCompilacao amb){
		boolean resultado = getExpressao().checaTipo(amb);
		if(resultado)
			amb.map(getId(), getExpressao().getTipo(amb));
		return resultado;
	}
	
	public void reduzir(AmbienteExecucao amb){
		amb.map(getId(), null);
	}
}
