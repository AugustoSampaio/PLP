package le2.plp.expressions2.expression;

import le2.plp.expressions1.util.Tipo;
import le2.plp.expressions2.declaration.Declaracao;
import le2.plp.expressions2.memory.AmbienteCompilacao;
import le2.plp.expressions2.memory.AmbienteExecucao;
import le2.plp.expressions2.memory.ContextoCompilacao;
import le2.plp.expressions2.memory.ContextoExecucao;
import le2.plp.expressions2.memory.VariavelJaDeclaradaException;
import le2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpDeclaracao implements Expressao {

	private Declaracao declaracao;
	private Expressao expressao;

	public ExpDeclaracao(Declaracao declaracao, Expressao expressaoArg) {
		this.declaracao = declaracao;
		this.expressao = expressaoArg;
	}

	public Valor avaliar(AmbienteExecucao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		ambiente.incrementa();
		AmbienteExecucao aux = new ContextoExecucao();
		aux.incrementa();
		declaracao.elabora(ambiente, aux);
		declaracao.incluir(ambiente, aux);
		aux.restaura();
		Valor result = expressao.avaliar(ambiente);
		ambiente.restaura();

		return result;
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
		try{
			if(declaracao.checaTipo(ambiente)){
				AmbienteCompilacao aux = new ContextoCompilacao();
				aux.incrementa();
				declaracao.elabora(ambiente, aux);
				declaracao.incluir(ambiente, aux);
				aux.restaura();
				result = expressao.checaTipo(ambiente);
			} else
				result = false;
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
	 */
	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		Tipo result = expressao.getTipo(ambiente);
		return result;
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
		retorno = new ExpDeclaracao(declaracao, this.expressao.clone());
		return retorno;
	}
}