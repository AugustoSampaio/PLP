package plp.debug.funcional3;

import java.util.ArrayList;
import java.util.List;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional1.util.TipoFuncao;
import lf3.plp.functional1.util.TipoPolimorfico;
import lf3.plp.functional2.expression.ValorFuncao;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link ValorFuncao} publicando o escopo da função (onde os
 * parâmetros formais são vinculados) com a faixa exata de código-fonte
 * capturada pelo parser do WebDebug.
 *
 * {@code checaTipo}/{@code getTipo} são herdados de {@code DefFuncao} e
 * reproduzidos aqui apenas para inserir o registro do escopo logo após o
 * {@code incrementa()}. Funcional3 permanece inalterada.
 */
public class ValorFuncaoDebug extends ValorFuncao {

	private final InfoEscopo infoEscopo;

	public ValorFuncaoDebug(List<Id> argsId, Expressao exp, InfoEscopo infoEscopo) {
		super(argsId, exp);
		this.infoEscopo = infoEscopo;
	}

	private void registra(AmbienteCompilacao ambiente) {
		if (ambiente instanceof ScopeAware) {
			((ScopeAware) ambiente).registraEscopo(infoEscopo);
		}
	}

	/**
	 * Reregistra os parâmetros formais após a verificação do corpo.
	 *
	 * No momento do {@code map()} o parâmetro ainda é um
	 * {@code TipoPolimorfico} não inferido, cujo nome é "?". Depois de checar
	 * o corpo, o mesmo objeto já conhece o tipo inferido, então basta reler
	 * {@code getNome()} para o debugger exibir o tipo concreto.
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
	public ValorFuncaoDebug clone() {
		List<Id> novaLista = new ArrayList<Id>(this.argsId.size());
		for (Id id : this.argsId) {
			novaLista.add(id.clone());
		}
		return new ValorFuncaoDebug(novaLista, this.exp.clone(), infoEscopo);
	}
}
