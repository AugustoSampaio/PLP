package lf1.plp.functional1;

import java.util.List;
import java.util.Map;

import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.ContextoCompilacao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.memory.AmbienteExecucaoFuncional;
import lf1.plp.functional1.memory.ContextoExecucaoFuncional;

public class Programa {

	private Expressao exp;
	private AmbienteCompilacao ambComp;

	public Programa(Expressao exp) {
		this.exp = exp;
	}

	public Valor executar()
		throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		AmbienteExecucaoFuncional ambExec = new ContextoExecucaoFuncional();
		return exp.avaliar(ambExec);
	} 

	public boolean checaTipo()
		throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		ambComp = new ContextoCompilacao();
		return exp.checaTipo(ambComp);
	}

	public Expressao getExpressao() {
		return exp;
	}

	public List<Map<String, Object>> getAmbCompSnapshot() {
		return ambComp == null ? null : ambComp.getPilhaSnapshot();
	}

}
