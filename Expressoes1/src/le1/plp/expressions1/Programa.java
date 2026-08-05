package le1.plp.expressions1;

import java.util.List;
import java.util.Map;

import le1.plp.expressions1.expression.Expressao;
import le1.plp.expressions1.expression.Valor;
import le1.plp.expressions2.memory.AmbienteCompilacao;
import le1.plp.expressions2.memory.AmbienteExecucao;
import le1.plp.expressions2.memory.ContextoCompilacao;
import le1.plp.expressions2.memory.ContextoExecucao;

public class Programa{

	private Expressao exp;
	private AmbienteCompilacao ambComp;

	public Valor executar() {
		AmbienteExecucao ambExec = new ContextoExecucao();
		Valor result = exp.avaliar(ambExec);
		System.out.println(result);
		return  result;		
 	}

	public boolean checaTipo() {
		ambComp = new ContextoCompilacao();
		return exp.checaTipo(ambComp);
 	}

	public Programa(Expressao exp){
		this.exp = exp;
	}

	public Expressao getExpressao() {
		return exp;
	}

	public List<Map<String, Object>> getAmbCompSnapshot() {
		return ambComp == null ? null : ambComp.getPilhaSnapshot();
	}

}
