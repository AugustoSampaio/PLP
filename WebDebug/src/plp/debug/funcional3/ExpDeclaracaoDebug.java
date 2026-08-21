package plp.debug.funcional3;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.ContextoCompilacao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional1.declaration.DeclaracaoFuncional;
import lf3.plp.functional2.expression.ExpDeclaracao;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link ExpDeclaracao} publicando o escopo do "let ... in" com a
 * faixa exata de código-fonte capturada pelo parser do WebDebug.
 *
 * Construída apenas pela gramática do WebDebug; Funcional3 permanece
 * inalterada e não conhece esta classe.
 */
public class ExpDeclaracaoDebug extends ExpDeclaracao {

	private final InfoEscopo infoEscopo;

	public ExpDeclaracaoDebug(DeclaracaoFuncional declaracao, Expressao expressaoArg, InfoEscopo infoEscopo) {
		super(declaracao, expressaoArg);
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

	@Override
	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		registra(ambiente, infoEscopo);

		Tipo vresult;
		AmbienteCompilacao aux = new ContextoCompilacao();
		aux.incrementa();
		declaracao.elabora(ambiente, aux);
		declaracao.incluir(ambiente, aux, false);
		aux.restaura();
		vresult = expressao.getTipo(ambiente);
		ambiente.restaura();
		return vresult;
	}

	@Override
	public ExpDeclaracaoDebug clone() {
		return new ExpDeclaracaoDebug(declaracao.clone(), expressao.clone(), infoEscopo);
	}
}
