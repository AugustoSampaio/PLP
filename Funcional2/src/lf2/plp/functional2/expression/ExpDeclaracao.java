package lf2.plp.functional2.expression;

import lf2.plp.expressions1.util.Tipo;
import lf2.plp.expressions2.expression.Expressao;
import lf2.plp.expressions2.expression.Valor;
import lf2.plp.expressions2.memory.AmbienteCompilacao;
import lf2.plp.expressions2.memory.AmbienteExecucao;
import lf2.plp.expressions2.memory.ContextoCompilacao;
import lf2.plp.expressions2.memory.ContextoExecucao;
import lf2.plp.expressions2.memory.InfoEscopo;
import lf2.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf2.plp.functional1.declaration.DeclaracaoFuncional;

public class ExpDeclaracao implements Expressao {

	protected DeclaracaoFuncional declaracao;
	protected Expressao expressao;
	protected InfoEscopo infoEscopo;

	public ExpDeclaracao(DeclaracaoFuncional declaracao, Expressao expressaoArg) {
		this(declaracao, expressaoArg, null);
	}

	public ExpDeclaracao(DeclaracaoFuncional declaracao, Expressao expressaoArg, InfoEscopo infoEscopo) {
		this.declaracao = declaracao;
		this.expressao = expressaoArg;
		this.infoEscopo = infoEscopo;
	}

	public Valor avaliar(AmbienteExecucao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		AmbienteExecucao aux = new ContextoExecucao();
		aux.incrementa();
		declaracao.elabora(ambiente, aux);
		declaracao.incluir(ambiente, aux);
		aux.restaura();
		Valor vresult = expressao.avaliar(ambiente);
		
		if(vresult instanceof ValorFuncao)
			vresult.reduzir(ambiente);
		
		ambiente.restaura();
		return vresult;
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 */
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		ambiente.registraEscopo(infoEscopo);

		boolean result = false;
		try {
			result = declaracao.checaTipo(ambiente);
			if (result) {
				AmbienteCompilacao aux = new ContextoCompilacao();
				aux.incrementa();
				declaracao.elabora(ambiente, aux);
				declaracao.incluir(ambiente, aux, true);
				aux.restaura();
				result = expressao.checaTipo(ambiente);
			}
		} finally {
			ambiente.restaura();
		}
		return result;
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		ambiente.registraEscopo(infoEscopo);

		Tipo vresult = null;
		AmbienteCompilacao aux = new ContextoCompilacao();
		aux.incrementa();
		declaracao.elabora(ambiente, aux);
		declaracao.incluir(ambiente, aux, false);
		aux.restaura();
		vresult = expressao.getTipo(ambiente);
		ambiente.restaura();
		return vresult;
	}

	public Expressao getExpressao() {
		return expressao;
	}
	
	public Expressao reduzir(AmbienteExecucao ambiente) {
		ambiente.incrementa();
		declaracao.reduzir(ambiente);
		this.expressao = expressao.reduzir(ambiente);
		ambiente.restaura();
		return this;
	}
	
	public ExpDeclaracao clone(){
		return new ExpDeclaracao(declaracao.clone(), this.expressao.clone());
	}
}
