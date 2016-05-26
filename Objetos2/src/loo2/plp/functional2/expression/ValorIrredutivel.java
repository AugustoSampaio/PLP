package loo2.plp.functional2.expression;

import loo2.plp.expressions1.util.Tipo;
import loo2.plp.expressions2.expression.Expressao;
import loo2.plp.expressions2.expression.Valor;
import loo2.plp.expressions2.memory.AmbienteCompilacao;
import loo2.plp.expressions2.memory.AmbienteExecucao;
import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ValorIrredutivel implements Valor {

	public Valor avaliar(AmbienteExecucao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return null;
	}

	public boolean checaTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return true;
	}

	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return null;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		return this;
	}
	
	public ValorIrredutivel clone() {
		return this;
	}
}
