package plp.functional1.expression;

import static plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Id;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.IdentificadorJaDeclaradoException;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.functional1.declaration.DecFuncao;
import plp.functional1.declaration.DecVariavel;
import plp.functional1.declaration.DeclaracaoFuncional;
import plp.functional1.memory.AmbienteExecucaoFuncional;
import plp.functional1.util.DefFuncao;

public class ExpDeclaracao implements Expressao {

	List<? extends DeclaracaoFuncional> seqdecFuncional;
	Expressao expressao;

	public ExpDeclaracao(
			List<? extends DeclaracaoFuncional> declaracoesFuncionais,
			Expressao expressaoArg) {
		seqdecFuncional = declaracoesFuncionais;
		expressao = expressaoArg;
	}

	/**
	 * Returns the seqdecVariavel.
	 * 
	 * @return List
	 */
	public List<? extends DeclaracaoFuncional> getSeqdecFuncional() {
		return seqdecFuncional;
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
	@Override
	public String toString() {
		return String.format("let %s in %s",
				listToString(seqdecFuncional, ","), expressao);
	}

	public Valor avaliar(AmbienteExecucao ambienteFuncional)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		AmbienteExecucaoFuncional ambiente = (AmbienteExecucaoFuncional) ambienteFuncional;
		ambiente.incrementa();

		// Como declaracoes feitas neste nivel nao devem ter influencia
		// mutua, armazenamos os valores em uma tabela auxiliar, para depois
		// fazer o mapeamento.
		Map<Id, Valor> auxIdValor = new HashMap<Id, Valor>();
		Map<Id, DefFuncao> auxIdValorFuncao = new HashMap<Id, DefFuncao>();

		resolveBindings(ambiente, auxIdValor, auxIdValorFuncao);

		includeBindings(ambiente, auxIdValor, auxIdValorFuncao);

		Valor vresult = expressao.avaliar(ambiente);
		ambiente.restaura();
		return vresult;
	}

	private void includeBindings(AmbienteExecucaoFuncional ambiente,
			Map<Id, Valor> auxIdValor, Map<Id, DefFuncao> auxIdValorFuncao)
			throws VariavelJaDeclaradaException {

		for (Map.Entry<Id, Valor> idValor : auxIdValor.entrySet()) {
			ambiente.map(idValor.getKey(), idValor.getValue());
		}

		for (Map.Entry<Id, DefFuncao> idFuncao : auxIdValorFuncao.entrySet()) {
			try {
				ambiente.mapFuncao(idFuncao.getKey(), idFuncao.getValue());
			} catch (IdentificadorJaDeclaradoException e) {
				throw new VariavelJaDeclaradaException(idFuncao.getKey());
			}
		}
	}

	private void resolveBindings(AmbienteExecucaoFuncional ambiente,
			Map<Id, Valor> auxIdValor, Map<Id, DefFuncao> auxIdValorFuncao)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		for (DeclaracaoFuncional decFuncional : this.seqdecFuncional) {
			if (decFuncional.getAridade() == 0) {
				auxIdValor.put(decFuncional.getId(), decFuncional
						.getExpressao().avaliar(ambiente));
			} else {
				DecFuncao decFuncao = (DecFuncao) decFuncional;
				DefFuncao valorFuncao = decFuncao.getFuncao();
				auxIdValorFuncao.put(decFuncional.getId(), valorFuncao);
			}
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

		boolean result = false;
		try {
			result = checkTypeBindings(ambiente);
			if (result) {
				Map<Id, Tipo> resolvedTypes = resolveTypeBidings(ambiente);
				includeTypeBindings(ambiente, resolvedTypes);
				result = expressao.checaTipo(ambiente);
			}
		} finally {
			ambiente.restaura();
		}
		return result;
	}

	private Map<Id, Tipo> resolveTypeBidings(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		Map<Id, Tipo> resolvedTypes = new HashMap<Id, Tipo>();

		for (DeclaracaoFuncional decFuncional : this.seqdecFuncional) {
			if (resolvedTypes.put(decFuncional.getId(), decFuncional
					.getTipo(ambiente)) != null)
				throw new VariavelJaDeclaradaException(decFuncional.getId());
		}
		return resolvedTypes;
	}

	private boolean checkTypeBindings(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		boolean result = true;
		for (DeclaracaoFuncional decFuncional : this.seqdecFuncional) {
			if (!decFuncional.checaTipo(ambiente)) {
				result = false;
				break;
			}
		}
		return result;
	}

	private void includeTypeBindings(AmbienteCompilacao ambiente,
			Map<Id, Tipo> resolvedTypes) throws VariavelJaDeclaradaException {
		for (Id id : resolvedTypes.keySet()) {
			ambiente.map(id, resolvedTypes.get(id));
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
	 * @precondition this.checaTipo();
	 */
	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		for (DeclaracaoFuncional decFuncional : this.seqdecFuncional) {
			if (decFuncional.getAridade() == 0) {
				ambiente.map(decFuncional.getId(), decFuncional.getExpressao()
						.getTipo(ambiente));
			} else {
				DecFuncao decFuncao = (DecFuncao) decFuncional;
				ambiente.map(decFuncional.getId(), decFuncao.getTipo(ambiente));
			}
		}
		Tipo vresult = expressao.getTipo(ambiente);
		ambiente.restaura();
		return vresult;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ExpDeclaracao clone(){
		ExpDeclaracao retorno;		
		List<DeclaracaoFuncional> novaLista = new ArrayList<DeclaracaoFuncional>(this.seqdecFuncional.size());
		
		for (DeclaracaoFuncional dec : this.seqdecFuncional){
			if (dec.getAridade() == 0) {
				DecVariavel decVar = (DecVariavel) dec;
				novaLista.add(new DecVariavel(decVar.getId().clone(), decVar.getExpressao().clone()));
			} else {
				DecFuncao decFuncao = (DecFuncao) dec;
				
				List<Id> listaArgs = new ArrayList<Id>();
				for(Id id : decFuncao.getListaId()){
					listaArgs.add(id.clone());
				}
				
				novaLista.add(new DecFuncao(decFuncao.getId().clone(),
						listaArgs, decFuncao.getExpressao().clone()));
			}
		}
		
		retorno = new ExpDeclaracao(novaLista, this.expressao.clone());
		
		return retorno;
	}
}
