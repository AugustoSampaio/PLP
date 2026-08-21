package plp.debug.funcional1;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.ContextoCompilacao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.declaration.DeclaracaoFuncional;
import lf1.plp.functional1.expression.ExpDeclaracao;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link ExpDeclaracao} publicando o escopo do "let ... in" com a
 * faixa exata de código-fonte capturada pelo parser do WebDebug.
 * Funcional1 permanece inalterada e não conhece esta classe.
 */
public class ExpDeclaracaoDebug extends ExpDeclaracao {

	private final DeclaracaoFuncional declaracaoDebug;
	private final Expressao expressaoDebug;
	private final InfoEscopo infoEscopo;

	public ExpDeclaracaoDebug(DeclaracaoFuncional declaracao, Expressao expressao, InfoEscopo infoEscopo) {
		super(declaracao, expressao);
		this.declaracaoDebug = declaracao;
		this.expressaoDebug = expressao;
		this.infoEscopo = infoEscopo;
	}

	private static void registra(AmbienteCompilacao ambiente, InfoEscopo info) {
		if (ambiente instanceof ScopeAware) {
			((ScopeAware) ambiente).registraEscopo(info);
		}
	}

	@Override
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		registra(ambiente, infoEscopo);

		boolean result = false;
		try {
			result = declaracaoDebug.checaTipo(ambiente);
			if (result) {
				AmbienteCompilacao aux = new ContextoCompilacao();
				aux.incrementa();
				declaracaoDebug.elabora(ambiente, aux);
				declaracaoDebug.incluir(ambiente, aux);
				aux.restaura();
				result = expressaoDebug.checaTipo(ambiente);
			}
		} finally {
			ambiente.restaura();
		}
		return result;
	}

	@Override
	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		registra(ambiente, infoEscopo);

		AmbienteCompilacao aux = new ContextoCompilacao();
		aux.incrementa();
		declaracaoDebug.elabora(ambiente, aux);
		declaracaoDebug.incluir(ambiente, aux);
		aux.restaura();
		Tipo vresult = expressaoDebug.getTipo(ambiente);
		ambiente.restaura();
		return vresult;
	}

	@Override
	public ExpDeclaracaoDebug clone() {
		return new ExpDeclaracaoDebug(declaracaoDebug.clone(), expressaoDebug.clone(), infoEscopo);
	}
}
