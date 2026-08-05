package lf1.plp.functional1.expression;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.expressions2.memory.ContextoCompilacao;
import lf1.plp.expressions2.memory.InfoEscopo;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.declaration.DeclaracaoFuncional;
import lf1.plp.functional1.memory.AmbienteExecucaoFuncional;
import lf1.plp.functional1.memory.ContextoExecucaoFuncional;

public class ExpDeclaracao implements Expressao {

	DeclaracaoFuncional declaracao;
	Expressao expressao;
	InfoEscopo infoEscopo;

	public ExpDeclaracao(
			DeclaracaoFuncional declaracao,
			Expressao expressao) {
		this(declaracao, expressao, null);
	}

	public ExpDeclaracao(
			DeclaracaoFuncional declaracao,
			Expressao expressao,
			InfoEscopo infoEscopo) {
		this.declaracao = declaracao;
		this.expressao = expressao;
		this.infoEscopo = infoEscopo;
	}

	/**
	 * Returns the expressao.
	 * 
	 * @return Expressao
	 */
	public Expressao getExpressao() {
		return expressao;
	}

	public Valor avaliar(AmbienteExecucao ambienteFuncional)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		AmbienteExecucaoFuncional amb = (AmbienteExecucaoFuncional)ambienteFuncional;
		amb.incrementa();
		AmbienteExecucaoFuncional aux = new ContextoExecucaoFuncional();
		aux.incrementa();
		declaracao.elabora(amb, aux);
		declaracao.incluir(amb, aux);
		aux.restaura();
		Valor vresult = expressao.avaliar(amb);
		amb.restaura();
		return vresult;
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compilação.
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
		ambiente.registraEscopo(infoEscopo);

		boolean result = false;
		try {
			result = declaracao.checaTipo(ambiente);
			if (result) {
				AmbienteCompilacao aux = new ContextoCompilacao();
				aux.incrementa();
				declaracao.elabora(ambiente, aux);
				declaracao.incluir(ambiente, aux);
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
	 * 
	 * @param amb
	 *            o ambiente de compilação.
	 * @return os tipos possiveis desta expressao.
	 * @exception VariavelNaoDeclaradaException
	 *                se existir um identificador nao declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException
	 *                se existir um identificador declarado mais de uma vez no
	 *                mesmo bloco do ambiente.
	 * @precondition this.checaTipo();
	 */
	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		ambiente.registraEscopo(infoEscopo);
		AmbienteCompilacao aux = new ContextoCompilacao();
		aux.incrementa();
		declaracao.elabora(ambiente, aux);
		declaracao.incluir(ambiente, aux);
		aux.restaura();
		Tipo vresult = expressao.getTipo(ambiente);
		ambiente.restaura();
		return vresult;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ExpDeclaracao clone(){
		return new ExpDeclaracao(declaracao.clone(), this.expressao.clone(), infoEscopo);
	}

}
