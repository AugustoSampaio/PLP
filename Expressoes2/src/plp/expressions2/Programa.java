package plp.expressions2;

import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.ContextoCompilacao;
import plp.expressions2.memory.ContextoExecucao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;

public class Programa {

	private Expressao exp;

	public Programa(Expressao exp){
		this.exp = exp;
	}

	public Valor executar() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		AmbienteExecucao ambExec = new ContextoExecucao();
		return exp.avaliar(ambExec);
	}

	public boolean checaTipo() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		AmbienteCompilacao ambComp = new ContextoCompilacao();
		return exp.checaTipo(ambComp);
	}

	public Expressao getExpressao() {
		return exp;
	}

}
