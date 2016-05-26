package li2.plp.expressions2.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.declaration.DecVariavel;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpDeclaracao implements Expressao {

	private List<DecVariavel> seqdecVariavel;
	private Expressao expressao;

	public ExpDeclaracao(List<DecVariavel> declarations, Expressao expressaoArg) {
		seqdecVariavel = declarations;
		expressao = expressaoArg;
	}

	public Valor avaliar(AmbienteExecucao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		ambiente.incrementa();
		Map<Id, Valor> resolvedValues = resolveValueBindings(ambiente);
		includeValueBindings(ambiente, resolvedValues);
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

	private Map<Id, Valor> resolveValueBindings(AmbienteExecucao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		Map<Id, Valor> resolvedValues = new HashMap<Id, Valor>();

		for (DecVariavel declaration : this.seqdecVariavel) {
			resolvedValues.put(declaration.getId(), declaration.getExpressao()
					.avaliar(ambiente));
		}
		return resolvedValues;
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
		Map<Id, Tipo> resolvedTypes;
		boolean result = false;
		try {
			if (this.checkTypeBindings(ambiente)) {
				resolvedTypes = this.resolveTypeBindings(ambiente);
				this.includeTypeBindings(ambiente, resolvedTypes);
				result = expressao.checaTipo(ambiente);
			} else {
				result = false;
			}
		} finally {
			ambiente.restaura();
		}
		return result;
	}

	private void includeTypeBindings(AmbienteCompilacao ambiente,
			Map<Id, Tipo> resolvedTypes) throws VariavelJaDeclaradaException {

		for (Id id : resolvedTypes.keySet()) {
			Tipo type = resolvedTypes.get(id);
			ambiente.map(id, type);
		}
	}

	private Map<Id, Tipo> resolveTypeBindings(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		Map<Id, Tipo> resolvedTypes = new HashMap<Id, Tipo>();

		for (DecVariavel declaration : this.seqdecVariavel) {
			if (resolvedTypes.put(declaration.getId(), declaration
					.getExpressao().getTipo(ambiente)) != null)
				throw new VariavelJaDeclaradaException(declaration.getId());

		}

		return resolvedTypes;
	}

	private boolean checkTypeBindings(AmbienteCompilacao ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {

		boolean result = true;

		for (DecVariavel declaration : this.seqdecVariavel) {
			if (!declaration.getExpressao().checaTipo(ambiente)) {
				result = false;
				break;
			}
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
		ambiente.incrementa();

		Map<Id, Tipo> resolvedTypes = this.resolveTypeBindings(ambiente);
		this.includeTypeBindings(ambiente, resolvedTypes);
		Tipo result = expressao.getTipo(ambiente);

		ambiente.restaura();
		return result;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		ambiente.incrementa();
		
		for(DecVariavel dec : this.seqdecVariavel){
			ambiente.map(dec.getId(), null);
		}
		this.expressao = expressao.reduzir(ambiente);
		
		ambiente.restaura();
		
		return this;
	}

	public ExpDeclaracao clone(){
		ExpDeclaracao retorno;		
		List<DecVariavel> novaLista = new ArrayList<DecVariavel>(this.seqdecVariavel.size());
		
		for (DecVariavel dec : this.seqdecVariavel){
			novaLista.add(new DecVariavel(dec.getId().clone(), dec.getExpressao().clone()));
		}
		
		retorno = new ExpDeclaracao(novaLista, this.expressao.clone());
		
		return retorno;
	}
}