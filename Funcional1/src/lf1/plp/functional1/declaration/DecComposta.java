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

	public void elabora(AmbienteExecucaoFuncional amb, AmbienteExecucaoFuncional aux) throws VariavelJaDeclaradaException {
		d1.elabora(amb, aux);
		d2.elabora(amb, aux);
	}

	@Override
	public void elabora(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException {
		d1.elabora(amb, aux);
		d2.elabora(amb, aux);
	}

	@Override
	public void incluir(AmbienteExecucaoFuncional amb, AmbienteExecucaoFuncional aux) throws VariavelJaDeclaradaException {
		d1.incluir(amb, aux);
		d2.incluir(amb, aux);
	}

	@Override
	public void incluir(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException {
		d1.incluir(amb, aux);
		d2.incluir(amb, aux);
	}

	@Override
	public DeclaracaoFuncional clone() {
		return new DecComposta(d1.clone(),d2.clone());
	}

}
