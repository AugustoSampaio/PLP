package plp.expressions2.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import plp.expressions1.util.Tipo;
import plp.expressions2.declaration.DecVariavel;
import plp.expressions2.declaration.Declaracao;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;

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
		Map<Id, Valor> declaracoes = new HashMap<Id,Valor>();
		declaracao.elabora(ambiente, declaracoes);
		includeValueBindings(ambiente, declaracoes);
		Valor result = expressao.avaliar(ambiente);
		ambiente.restaura();

		return result;
	}

	private void includeValueBindings(AmbienteExecucao ambiente,
			Map<Id, Valor> resolvedValues) throws VariavelJaDeclaradaException {
		for (Id id : resolvedValues.keySet()) {
			Valor valor = resolvedValues.get(id);
			ambiente.map(id, valor);
		}
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
		boolean resposta = declaracao.checaTipo(ambiente) && expressao.checaTipo(ambiente);
		ambiente.restaura();
		return resposta;
	}

	private void includeTypeBindings(AmbienteCompilacao ambiente,
			Map<Id, Tipo> resolvedTypes) throws VariavelJaDeclaradaException {

		for (Id id : resolvedTypes.keySet()) {
			Tipo type = resolvedTypes.get(id);
			ambiente.map(id, type);
		}
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