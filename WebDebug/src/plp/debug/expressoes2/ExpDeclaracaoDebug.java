package plp.debug.expressoes2;

import le2.plp.expressions1.util.Tipo;
import le2.plp.expressions2.declaration.Declaracao;
import le2.plp.expressions2.expression.ExpDeclaracao;
import le2.plp.expressions2.expression.Expressao;
import le2.plp.expressions2.memory.AmbienteCompilacao;
import le2.plp.expressions2.memory.ContextoCompilacao;
import le2.plp.expressions2.memory.VariavelJaDeclaradaException;
import le2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link ExpDeclaracao} publicando o escopo do "let ... in" com a
 * faixa exata de código-fonte capturada pelo parser do WebDebug.
 *
 * Construída apenas pela gramática do WebDebug; Expressoes2 permanece
 * inalterada e não conhece esta classe.
 */
public class ExpDeclaracaoDebug extends ExpDeclaracao {

	private final Declaracao declaracao;
	private final Expressao expressao;
	private final InfoEscopo infoEscopo;

	public ExpDeclaracaoDebug(Declaracao declaracao, Expressao expressaoArg, InfoEscopo infoEscopo) {
		super(declaracao, expressaoArg);
		this.declaracao = declaracao;
		this.expressao = expressaoArg;
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
		boolean result;
		try {
			if (declaracao.checaTipo(ambiente)) {
				AmbienteCompilacao aux = new ContextoCompilacao();
				aux.incrementa();
				declaracao.elabora(ambiente, aux);
				declaracao.incluir(ambiente, aux);
				aux.restaura();
				result = expressao.checaTipo(ambiente);
			} else {
				result = false;
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
		Tipo tipo;
		AmbienteCompilacao aux = new ContextoCompilacao();
		aux.incrementa();
		declaracao.elabora(ambiente, aux);
		declaracao.incluir(ambiente, aux);
		aux.restaura();
		tipo = expressao.getTipo(ambiente);
		ambiente.restaura();
		return tipo;
	}

	@Override
	public ExpDeclaracaoDebug clone() {
		return new ExpDeclaracaoDebug(declaracao, expressao.clone(), infoEscopo);
	}
}
