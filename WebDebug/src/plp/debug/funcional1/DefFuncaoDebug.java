package plp.debug.funcional1;

import java.util.ArrayList;
import java.util.List;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.util.DefFuncao;
import lf1.plp.functional1.util.TipoFuncao;
import lf1.plp.functional1.util.TipoPolimorfico;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link DefFuncao} publicando o escopo da função (onde os parâmetros
 * formais são vinculados) com a faixa exata de código-fonte capturada pelo
 * parser do WebDebug. Funcional1 permanece inalterada.
 */
public class DefFuncaoDebug extends DefFuncao {

	private final InfoEscopo infoEscopo;

	public DefFuncaoDebug(List<Id> argsId, Expressao exp, InfoEscopo infoEscopo) {
		super(argsId, exp);
		this.infoEscopo = infoEscopo;
	}

	private void registra(AmbienteCompilacao ambiente) {
		if (ambiente instanceof ScopeAware) {
			((ScopeAware) ambiente).registraEscopo(infoEscopo);
		}
	}

	/**
	 * Reregistra os parâmetros após a verificação do corpo: no {@code map()}
	 * eles ainda são {@code TipoPolimorfico} não inferidos (nome "?"), e só
	 * depois da unificação o mesmo objeto conhece o tipo concreto.
	 */
	private void refinaParametros(AmbienteCompilacao ambiente) {
		if (!(ambiente instanceof ScopeAware)) {
			return;
		}
		for (Id id : argsId) {
			try {
				Tipo tipo = ambiente.get(id);
				if (tipo != null) {
					((ScopeAware) ambiente).registraBinding(id, tipo, tipo.getNome());
				}
			} catch (VariavelNaoDeclaradaException ignored) {
				// Parâmetro fora do ambiente: mantém o valor já registrado.
			}
		}
	}

	@Override
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		registra(ambiente);

		for (Id id : argsId) {
			ambiente.map(id, new TipoPolimorfico());
		}

		boolean result = exp.checaTipo(ambiente);

		refinaParametros(ambiente);
		ambiente.restaura();

		return result;
	}

	@Override
	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		registra(ambiente);

		for (Id id : argsId) {
			ambiente.map(id, new TipoPolimorfico());
		}

		exp.checaTipo(ambiente);

		Tipo result = exp.getTipo(ambiente);

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		Tipo argTipo;
		for (int i = 0; i < getAridade(); i++) {
			argTipo = ((TipoPolimorfico) ambiente.get(argsId.get(i))).inferir();
			params.add(argTipo);
		}
		result = new TipoFuncao(params, result);

		refinaParametros(ambiente);
		ambiente.restaura();

		return result;
	}

	@Override
	public DefFuncaoDebug clone() {
		List<Id> novaLista = new ArrayList<Id>(this.argsId.size());
		for (Id id : this.argsId) {
			novaLista.add(id.clone());
		}
		return new DefFuncaoDebug(novaLista, this.exp.clone(), infoEscopo);
	}
}
