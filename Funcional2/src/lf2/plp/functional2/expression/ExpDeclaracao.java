package lf2.plp.functional2.expression;

import java.util.HashMap;
import java.util.Map;

import lf2.plp.expressions1.util.Tipo;
import lf2.plp.expressions2.expression.Expressao;
import lf2.plp.expressions2.expression.Id;
import lf2.plp.expressions2.expression.Valor;
import lf2.plp.expressions2.memory.AmbienteCompilacao;
import lf2.plp.expressions2.memory.AmbienteExecucao;
import lf2.plp.expressions2.memory.ContextoCompilacao;
import lf2.plp.expressions2.memory.ContextoExecucao;
import lf2.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf2.plp.functional1.declaration.DeclaracaoFuncional;

public class ExpDeclaracao implements Expressao {

	protected DeclaracaoFuncional declaracao;
	protected Expressao expressao;

	public ExpDeclaracao(DeclaracaoFuncional declaracao,
			Expressao expressaoArg) {
		this.declaracao = declaracao;
		expressao = expressaoArg;
	}

	/**
	 * Retorna uma representacao String desta expressao. Util para depuracao.
	 * 
	 * @return uma representacao String desta expressao.
	 */
//	@Override
//	public String toString() {
//		return String.format("let %s in %s",
//				listToString(seqdecFuncional, ","), expressao);
//	}

	public Valor avaliar(AmbienteExecucao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		// Como declaracoes feitas neste nivel nao devem ter influencia
		// mutua, armazenamos os valores em uma ambiente auxiliar, para depois
		// fazer o mapeamento.
		AmbienteExecucao aux = new ContextoExecucao();
		aux.incrementa();
		declaracao.elabora(ambiente,aux);
		declaracao.incluir(ambiente,aux);
		aux.restaura();
		Valor vresult = expressao.avaliar(ambiente);
		
		if(vresult instanceof ValorFuncao)
			vresult.reduzir(ambiente);
		
		ambiente.restaura();
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
				AmbienteCompilacao aux = new ContextoCompilacao();
				aux.incrementa();
				declaracao.elabora(ambiente, aux);
				declaracao.incluir(ambiente, aux,true);
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

		Tipo vresult = null;
		
		AmbienteCompilacao aux = new ContextoCompilacao();
		aux.incrementa();
		declaracao.elabora(ambiente, aux);
		declaracao.incluir(ambiente, aux,false);
		aux.restaura();
		vresult = expressao.getTipo(ambiente);
		ambiente.restaura();
		return vresult;
	}

	/**
	 * Returns the expressao.
	 * 
	 * @return Expressao
	 */
	public Expressao getExpressao() {
		return expressao;
	}
	
	public Expressao reduzir(AmbienteExecucao ambiente) {
		ambiente.incrementa();
		
		declaracao.reduzir(ambiente);
		this.expressao = expressao.reduzir(ambiente);
		
		ambiente.restaura();
		
		return this;
	}
	
	public ExpDeclaracao clone(){
		ExpDeclaracao retorno;		
//		List<DeclaracaoFuncional> novaLista = new ArrayList<DeclaracaoFuncional>(this.seqdecFuncional.size());
//		
//		for (DeclaracaoFuncional dec : this.seqdecFuncional){
//			novaLista.add(dec.clone());
//
////			if (dec.getAridade() == 0) {
////				DecVariavel decVar = (DecVariavel) dec;
////				novaLista.add(new DecVariavel(decVar.getId().clone(), decVar.getExpressao().clone()));
////			} else {
////				DecFuncao decFuncao = (DecFuncao) dec;
////				novaLista.add(new DecFuncao(decFuncao.getId().clone(), decFuncao.getFuncao().clone()));
////			}
//		}
		
		retorno = new ExpDeclaracao(declaracao.clone(), this.expressao.clone());
		
		return retorno;
	}
	
}
