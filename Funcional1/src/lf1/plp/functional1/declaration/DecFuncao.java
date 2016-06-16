package lf1.plp.functional1.declaration;

import static lf1.plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.memory.AmbienteExecucaoFuncional;
import lf1.plp.functional1.util.DefFuncao;
import lf1.plp.functional1.util.TipoFuncao;
import lf1.plp.functional1.util.TipoPolimorfico;

public class DecFuncao implements DeclaracaoFuncional {

	private Id id;

	private DefFuncao funcao;

	public DecFuncao(Id idFun, List<Id> argsId, Expressao exp) {
		this.id = idFun;
		this.funcao = new DefFuncao(argsId, exp);
	}

	public Id getId() {
		return id;
	}

	public List<Id> getListaId() {
		return funcao.getListaId();
	}

	public Expressao getExpressao() {
		return funcao.getExp();
	}

	/**
	 * Retorna a aridade da funcao declarada. Variaveis tem aridade 0.
	 * 
	 * @return a aridade da funcao declarada.
	 */
	public int getAridade() {
		return funcao.getAridade();
	}

	public DefFuncao getFuncao() {
		return funcao;
	}

	/**
	 * Retorna uma representacao String desta expressao. Util para depuracao.
	 * 
	 * @return uma representacao String desta expressao.
	 */
	@Override
	public String toString() {
		return String.format("fun %s (%s) = %s", id, listToString(funcao
				.getListaId(), ","), funcao.getExp());
	}

	/**
	 * Realiza a verificacao de tipos desta declara��o.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *         <code>false</code> caso contrario.
	 * @exception VariavelNaoDeclaradaException
	 *                se existir um identificador nao declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException
	 *                se existir um identificador declarado mais de uma vez no
	 *                mesmo bloco do ambiente.
	 */
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		// Mapeia a pr�pria fun��o no ambiente para permitir recurs�o.
		ambiente.map(id, tipo);

		boolean result = funcao.checaTipo(ambiente);
		ambiente.restaura();
		return result;
	}

	/**
	 * Retorna os tipos possiveis da fun��o declarada.
	 * 
	 * @param amb
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            tipos.
	 * @return os tipos possiveis desta declara��o.
	 * @exception VariavelNaoDeclaradaException
	 *                se houver uma vari&aacute;vel n&atilde;o declarada no
	 *                ambiente.
	 * @exception VariavelJaDeclaradaException
	 *                se houver uma mesma vari&aacute;vel declarada duas vezes
	 *                no mesmo bloco do ambiente.
	 * @precondition this.checaTipo();
	 */
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		amb.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		amb.map(id, tipo);

		Tipo result = funcao.getTipo(amb);
		amb.restaura();
		return result;
	}

	public DecFuncao clone() {
		DefFuncao aux = this.funcao.clone();
		return new DecFuncao(this.id.clone(), aux.getListaId(), aux.getExp());
	}

	public void elabora(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException {
		aux.map(getId(), getTipo(amb));
	}

	public void incluir(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException {
		amb.map(getId(), aux.get(getId()));
	}

	public void elabora(AmbienteExecucaoFuncional amb, AmbienteExecucaoFuncional aux) throws VariavelJaDeclaradaException {
		aux.mapFuncao(getId(), getFuncao());
	}

	public void incluir(AmbienteExecucaoFuncional amb, AmbienteExecucaoFuncional aux) throws VariavelJaDeclaradaException {
		amb.mapFuncao(getId(), aux.getFuncao(getId()));
	}

}
