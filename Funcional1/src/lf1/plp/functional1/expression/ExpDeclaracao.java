package lf1.plp.functional1.expression;



import java.util.HashMap;
import java.util.Map;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.declaration.DeclaracaoFuncional;
import lf1.plp.functional1.memory.AmbienteExecucaoFuncional;
import lf1.plp.functional1.util.DefFuncao;

public class ExpDeclaracao implements Expressao {

	DeclaracaoFuncional declaracao;
	Expressao expressao;

	public ExpDeclaracao(
			DeclaracaoFuncional declaracao,
			Expressao expressao) {
		this.declaracao = declaracao;
		this.expressao = expressao;
	}

	/**
	 * Returns the expressao.
	 * 
	 * @return Expressao
	 */
	public Expressao getExpressao() {
		return expressao;
	}

	/**
	 * Retorna uma representacao String desta expressao. Util para depuracao.
	 * 
	 * @return uma representacao String desta expressao.
	 */
	/*@Override
	public String toString() {
		return String.format("let %s in %s",
				listToString(seqdecFuncional, ","), expressao);
	}*/
	
	public Valor avaliar(AmbienteExecucao ambienteFuncional)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		AmbienteExecucaoFuncional amb = (AmbienteExecucaoFuncional)ambienteFuncional;
		amb.incrementa();
		Map<Id, Valor> declaracoes = new HashMap<Id, Valor>();
		Map<Id, DefFuncao> declaracoesFuncoes = new HashMap<Id, DefFuncao>();
		declaracao.elabora(amb, declaracoes, declaracoesFuncoes);
		declaracao.incluir(amb, declaracoes, declaracoesFuncoes);
		Valor vresult = expressao.avaliar(amb);
		amb.restaura();
		return vresult;
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
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		boolean result = false;
		try {
			result = declaracao.checaTipo(ambiente);
			if (result) {
				Map<Id, Tipo> tipos = new HashMap<Id, Tipo>();
				declaracao.elabora(ambiente, tipos);
				declaracao.incluir(ambiente, tipos);
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
	 *            o ambiente de compila��o.
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
		Map<Id, Tipo> tipos = new HashMap<Id, Tipo>();
		declaracao.elabora(ambiente, tipos);
		declaracao.incluir(ambiente, tipos);
		Tipo vresult = expressao.getTipo(ambiente);
		ambiente.restaura();
		return vresult;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ExpDeclaracao clone(){
		return new ExpDeclaracao(declaracao.clone(), this.expressao.clone());
	}

}
