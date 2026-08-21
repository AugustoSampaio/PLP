package plp.debug.funcional1;

import java.util.ArrayList;
import java.util.List;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.declaration.DecFuncao;
import lf1.plp.functional1.util.TipoFuncao;
import lf1.plp.functional1.util.TipoPolimorfico;
import plp.debug.core.InfoEscopo;

/**
 * Estende {@link DecFuncao} apenas para injetar um {@link DefFuncaoDebug},
 * que é quem publica o escopo dos parâmetros da função.
 *
 * {@code DecFuncao} constrói seu {@code DefFuncao} internamente em um campo
 * privado, então é necessário reproduzir {@code checaTipo}/{@code getTipo}
 * aqui para que usem a versão com registro de escopo. O nível criado por
 * estes métodos guarda apenas o auto-tipo usado para permitir recursão, que
 * não é um escopo do programa — por isso ele não registra escopo próprio e
 * permanece invisível no debugger.
 */
public class DecFuncaoDebug extends DecFuncao {

	private final Id id;
	private final DefFuncaoDebug funcaoDebug;

	public DecFuncaoDebug(Id idFun, List<Id> argsId, Expressao exp, InfoEscopo infoEscopo) {
		super(idFun, argsId, exp);
		this.id = idFun;
		this.funcaoDebug = new DefFuncaoDebug(argsId, exp, infoEscopo);
	}

	@Override
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		ambiente.map(id, tipo);

		boolean result = funcaoDebug.checaTipo(ambiente);
		ambiente.restaura();
		return result;
	}

	@Override
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		amb.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		amb.map(id, tipo);

		Tipo result = funcaoDebug.getTipo(amb);
		amb.restaura();
		return result;
	}
}
