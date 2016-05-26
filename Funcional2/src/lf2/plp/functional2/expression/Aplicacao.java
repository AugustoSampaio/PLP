package lf2.plp.functional2.expression;

import static java.util.Arrays.asList;
import static lf2.plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lf2.plp.expressions1.util.Tipo;
import lf2.plp.expressions2.expression.Expressao;
import lf2.plp.expressions2.expression.Id;
import lf2.plp.expressions2.expression.Valor;
import lf2.plp.expressions2.memory.AmbienteCompilacao;
import lf2.plp.expressions2.memory.AmbienteExecucao;
import lf2.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf2.plp.functional1.util.DefFuncao;
import lf2.plp.functional1.util.TipoFuncao;
import lf2.plp.functional1.util.TipoPolimorfico;

public class Aplicacao implements Expressao {

	private Expressao func;
	private List<? extends Expressao> argsExpressao;

	public Aplicacao(Expressao f, Expressao... expressoes) {
		this(f, asList(expressoes));
	}

	public Aplicacao(Expressao f, List<? extends Expressao> expressoes) {
		func = f;
		argsExpressao = expressoes;
	}

	public Valor avaliar(AmbienteExecucao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		ValorFuncao funcao = (ValorFuncao) func.avaliar(ambiente);

		Map<Id, Valor> mapIdValor = resolveParametersBindings(ambiente, funcao);
		ambiente.incrementa();		
		includeValueBindings(ambiente, mapIdValor);

		if(funcao.getId() != null){
			ambiente.map(funcao.getId(), funcao.clone());
		}
		Expressao exp = funcao.getExp().clone();
		exp.reduzir(ambiente);
		
		Valor vresult = exp.avaliar(ambiente);
		
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
		Tipo tipo = getFuncType(ambiente);

		boolean result;

		TipoFuncao tipoFuncao = (TipoFuncao) tipo;
		result = tipoFuncao.checaTipo(ambiente, argsExpressao);

		return result;
	}

	private Tipo getFuncType(AmbienteCompilacao ambiente) {
		Tipo tipoFuncao = null;
		if (func instanceof Id) {
			tipoFuncao = ambiente.get((Id) func);
		} else if (func instanceof ValorFuncao) {
			tipoFuncao = ((ValorFuncao) func).getTipo(ambiente);
		}

		if (tipoFuncao == null || tipoFuncao instanceof TipoPolimorfico) {
			ArrayList<Tipo> params = new ArrayList<Tipo>();
			for (Expressao valorReal : argsExpressao) {
				params.add(valorReal.getTipo(ambiente));
			}
			tipoFuncao = new TipoFuncao(params, new TipoPolimorfico());
		}
		return tipoFuncao;
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
	public Expressao getFunc() {
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

		Tipo tipo = getFuncType(ambiente);

		TipoFuncao tipoFuncao = (TipoFuncao) tipo;

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
		this.func = this.func.reduzir(ambiente);
		
		ArrayList<Expressao> novosArgs =
			new ArrayList<Expressao>(this.argsExpressao.size());
		
		for(Expressao arg : this.argsExpressao) {
			novosArgs.add(arg.reduzir(ambiente));
		}
		this.argsExpressao = novosArgs;
		
		return this;
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
