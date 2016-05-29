package lf1.plp.functional1.declaration;

import java.util.Map;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.memory.AmbienteExecucaoFuncional;
import lf1.plp.functional1.util.DefFuncao;

public class DecComposta implements DeclaracaoFuncional {
	private DeclaracaoFuncional d1;
	private DeclaracaoFuncional d2;
	
	public DecComposta(DeclaracaoFuncional d1, DeclaracaoFuncional d2) {
		this.d1=d1;
		this.d2=d2;
	}

	@Override
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return (d1.checaTipo(ambiente) && d2.checaTipo(ambiente));
	}

	public void elabora(AmbienteExecucaoFuncional amb, Map<Id,Valor> declaracoes, Map<Id,DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		d1.elabora(amb, declaracoes,declaracoesFuncoes);
		d2.elabora(amb, declaracoes,declaracoesFuncoes);
	}

	@Override
	public void elabora(AmbienteCompilacao amb, Map<Id,Tipo> tipos) throws VariavelJaDeclaradaException {
		d1.elabora(amb, tipos);
		d2.elabora(amb, tipos);
	}

	@Override
	public void incluir(AmbienteExecucaoFuncional amb, Map<Id,Valor> declaracoes, Map<Id,DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		d1.incluir(amb, declaracoes,declaracoesFuncoes);
		d2.incluir(amb, declaracoes,declaracoesFuncoes);
	}

	@Override
	public void incluir(AmbienteCompilacao amb, Map<Id,Tipo> tipos) throws VariavelJaDeclaradaException {
		d1.incluir(amb, tipos);
		d2.incluir(amb, tipos);
	}

	@Override
	public DeclaracaoFuncional clone() {
		return new DecComposta(d1.clone(),d2.clone());
	}

}
