//Mudar o for do iterator para o novo for.
package lf1.plp.functional1.expression;

import static java.util.Arrays.asList;
import static lf1.plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.memory.AmbienteExecucaoFuncional;
import lf1.plp.functional1.util.DefFuncao;
import lf1.plp.functional1.util.TipoFuncao;

public class Aplicacao implements Expressao {

	private Id func;
	private List<? extends Expressao> argsExpressao;

	public Aplicacao(Id f, Expressao... expressoes) {
		this(f, asList(expressoes));
	}

	public Aplicacao(Id f, List<? extends Expressao> expressoes) {
		func = f;
		argsExpressao = expressoes;
	}

	public Valor avaliar(AmbienteExecucao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		AmbienteExecucaoFuncional ambienteFuncional = (AmbienteExecucaoFuncional) ambiente;

		DefFuncao funcao;
		try {
			funcao = ambienteFuncional.getFuncao(func);
		} catch (IdentificadorNaoDeclaradoException e) {
			throw new VariavelJaDeclaradaException(func);
		}

		Map<Id, Valor> mapIdValor = resolveParametersBindings(ambiente, funcao);

		ambiente.incrementa();

		includeValueBindings(ambiente, mapIdValor);

		Valor vresult = funcao.getExp().avaliar(ambiente);
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
		boolean result;
		Tipo aux = ambiente.get(func);

		if (aux instanceof TipoFuncao) {
			TipoFuncao tipoFuncao = (TipoFuncao) aux;

			result = tipoFuncao.checaTipo(ambiente, argsExpressao);
		} else {
			// A fun��o func n�o foi declarada.

			// TODO: lan�ar uma exce��o ou separar vari�veis de fun��es no
			// contexto de compila��o.

			result = false;
		}
		return result;
	}

	/**
	 * Returns the args.
	 * 
	 * @return ListaExpressao
	 */
	public List<? extends Expressao> getArgsExpressao() {
		return argsExpressao;
	}

	/**
	 * Returns the func.
	 * 
	 * @return Id
	 */
	public Id getFunc() {
		return func;
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
		TipoFuncao tipoFuncao = (TipoFuncao) ambiente.get(func);
		return tipoFuncao.getTipo(ambiente, argsExpressao);
	}

	private void includeValueBindings(AmbienteExecucao ambiente,
			Map<Id, Valor> mapIdValor) throws VariavelJaDeclaradaException {
		for (Map.Entry<Id, Valor> mapeamento : mapIdValor.entrySet()) {
			Id id = mapeamento.getKey();
			Valor valor = mapeamento.getValue();
			ambiente.map(id, valor);
		}
	}

	private Map<Id, Valor> resolveParametersBindings(AmbienteExecucao ambiente,
			DefFuncao funcao) throws VariavelNaoDeclaradaException,
			VariavelJaDeclaradaException {
		List<Id> parametrosId = funcao.getListaId();
		List<? extends Expressao> expressoesValorReal = argsExpressao;

		Map<Id, Valor> mapIdValor = new HashMap<Id, Valor>();

		Iterator<? extends Expressao> iterExpressoesValor = expressoesValorReal
				.iterator();
		for (Id id : parametrosId) {
			Expressao exp = iterExpressoesValor.next();
			Valor valorReal = exp.avaliar(ambiente);
			mapIdValor.put(id, valorReal);
		}
		return mapIdValor;
	}

	/**
	 * Retorna uma representacao String desta expressao. Util para depuracao.
	 * 
	 * @return uma representacao String desta expressao.
	 */
	@Override
	public String toString() {
		return String.format("%s(%s)", func, listToString(argsExpressao, ","));
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Aplicacao clone() {
		Aplicacao retorno;
		ArrayList<Expressao> novaLista = new ArrayList<Expressao>(this.argsExpressao.size());

		Iterator<? extends Expressao> iterator = argsExpressao.iterator();
		while (iterator.hasNext()){
			Expressao exp = iterator.next();
			novaLista.add(exp.clone());			
		}
		
		retorno = new Aplicacao(this.func.clone(), novaLista);
		
		return retorno;
	}
}
