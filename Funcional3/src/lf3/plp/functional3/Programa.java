package lf3.plp.functional3;

import java.util.Map;
import java.util.List;

import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Valor;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.ContextoCompilacao;
import lf3.plp.expressions2.memory.ContextoExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class Programa {

	private Expressao exp;
	
	private lf3.plp.expressions2.memory.AmbienteCompilacao ambComp;	

	public Programa(Expressao exp) {
		this.exp = exp;
	}

	public Valor executar()
		throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		AmbienteExecucao ambExec = new ContextoExecucao();
		return exp.avaliar(ambExec);
	}

	public boolean checaTipo()
		throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		AmbienteCompilacao amb = new ContextoCompilacao();
		this.ambComp = amb;		
		return exp.checaTipo(amb);
	}

	public Expressao getExpressao() {
		return exp;
	}

	public List<Map<String,Object>> getAmbCompSnapshot() {
		return ambComp.getPilhaSnapshot();
	}

}
