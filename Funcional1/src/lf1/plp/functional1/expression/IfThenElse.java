package lf1.plp.functional1.expression;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.expression.ValorBooleano;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class IfThenElse implements Expressao {

	private Expressao condicao;
	private Expressao then;
	private Expressao elseExpressao;

	public IfThenElse(Expressao teste, Expressao thenExpressao,
			Expressao elseExpressao) {
		this.condicao = teste;
		this.then = thenExpressao;
		this.elseExpressao = elseExpressao;
	}

	/**
	 * Returns the condicao.
	 * 
	 * @return Expressao
	 */
	public Expressao getCondicao() {
		return condicao;
	}

	/**
	 * Returns the then.
	 * 
	 * @return Expressao
	 */
	public Expressao getThen() {
		return then;
	}

	/**
	 * Returns the elseExpressao.
	 * 
	 * @return Expressao
	 */
	public Expressao getElseExpressao() {
		return elseExpressao;
	}

	public Valor avaliar(AmbienteExecucao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		if (((ValorBooleano) condicao.avaliar(ambiente)).valor())
			return then.avaliar(ambiente);
		else
			return elseExpressao.avaliar(ambiente);
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
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
	public boolean checaTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		boolean ret = condicao.checaTipo(amb);
		ret &= then.checaTipo(amb);
		ret &= elseExpressao.checaTipo(amb);

		Tipo condicaoTipo = condicao.getTipo(amb);
		Tipo thenTipo = then.getTipo(amb);
		Tipo elseTipo = elseExpressao.getTipo(amb);

		return ret && condicaoTipo.eBooleano() && thenTipo.eIgual(elseTipo);
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 * @exception VariavelNaoDeclaradaException
	 *                se existir um identificador nao declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException
	 *                se existir um identificador declarado mais de uma vez no
	 *                mesmo bloco do ambiente.
	 */
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return then.getTipo(amb).intersecao(elseExpressao.getTipo(amb));
	}

	@Override
	public String toString() {
		return String.format("if (%s) then (%s) else (%s)", condicao, then,
				elseExpressao);
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		this.condicao = this.condicao.reduzir(ambiente);
		this.then = this.then.reduzir(ambiente);
		this.elseExpressao = this.elseExpressao.reduzir(ambiente);
		
		return this;
	}

	public IfThenElse clone() {
		return new IfThenElse(this.condicao.clone(), this.then.clone(), this.elseExpressao.clone());
	}
}
