package plp.expressions1;

import plp.expressions1.expression.Expressao;
import plp.expressions1.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.ContextoCompilacao;
import plp.expressions2.memory.ContextoExecucao;

public class Programa{

	private Expressao exp;

	public Valor executar() {
		AmbienteExecucao ambExec = new ContextoExecucao();
		Valor result = exp.avaliar(ambExec);
		System.out.println(result);
		return  result;		
 	}

	public boolean checaTipo() {
		AmbienteCompilacao ambComp = new ContextoCompilacao();
		return exp.checaTipo(ambComp);
 	}

	public Programa(Expressao exp){
		this.exp = exp;
	}

	public Expressao getExpressao() {
		return exp;
	}

}
