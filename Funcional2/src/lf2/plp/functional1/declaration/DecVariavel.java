package lf2.plp.functional1.declaration;

import java.util.Map;

import lf2.plp.expressions1.util.Tipo;
import lf2.plp.expressions2.expression.Expressao;
import lf2.plp.expressions2.expression.Id;
import lf2.plp.expressions2.expression.Valor;
import lf2.plp.expressions2.memory.AmbienteCompilacao;
import lf2.plp.expressions2.memory.AmbienteExecucao;
import lf2.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf2.plp.functional2.expression.ValorFuncao;

public class DecVariavel implements DeclaracaoFuncional {
	private Id id;
	private Expressao expressao;

	public DecVariavel(Id idArg, Expressao expressaoArg) {
		id = idArg;
		expressao = expressaoArg;
	}


	/**
	 * Retorna uma representacao String desta expressao. Util para depuracao.
	 * 
	 * @return uma representacao String desta expressao.
	 */
	@Override
	public String toString() {
		return String.format("var %s = %s", id, expressao);
	}

	public Expressao getExpressao() {
		return expressao;
	}

	public Id getId() {
		return id;
	}

	/**
	 * Retorna os tipos possiveis desta declara��o.
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
	 * @precondition this.checaTipo(amb);
	 */
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return expressao.getTipo(amb);
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
		return expressao.checaTipo(ambiente);
	}

	public DecVariavel clone() {
		return new DecVariavel(this.id.clone(), this.expressao.clone());
	}


	public void elabora(AmbienteExecucao amb, AmbienteExecucao aux) throws VariavelJaDeclaradaException {
		aux.map(getId(), getExpressao().avaliar(amb));
	}


	public void elabora(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException {
		aux.map(getId(), getTipo(amb));
	}


	public void incluir(AmbienteExecucao amb, AmbienteExecucao aux) throws VariavelJaDeclaradaException {
		amb.map(getId(), aux.get(getId()));
	}


	public void incluir(AmbienteCompilacao amb, AmbienteCompilacao aux, boolean incluirCuringa) throws VariavelJaDeclaradaException {
		amb.map(getId(), aux.get(getId()));
	}


	public void reduzir(AmbienteExecucao amb) {
		amb.map(getId(), null);
	}


	
}
