package plp.functional3.expression;

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
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.functional1.declaration.DeclaracaoFuncional;
import plp.functional1.util.TipoPolimorfico;
import plp.functional3.declaration.DecFuncao;

public class ExpDeclaracao implements Expressao {
	
	List<? extends DeclaracaoFuncional> seqdecFuncional;
	Expressao expressao;
	
	public ExpDeclaracao(List<? extends DeclaracaoFuncional> declaracoes,
			Expressao expressaoArg) {
		this.seqdecFuncional = declaracoes;
		this.expressao = expressaoArg;
	}
	
	/**
	 * Retorna uma representacao String desta expressao. Util para depuracao.
	 * 
	 * @return uma representacao String desta expressao.
	 */
	@Override
	public String toString() {
		return String.format("let %s in %s", listToString(this.seqdecFuncional, ","),
				this.expressao);
	}
	
	public Valor avaliar(AmbienteExecucao ambiente) throws VariavelNaoDeclaradaException,
			VariavelJaDeclaradaException {
		ambiente.incrementa();
		
		// Como declaracoes feitas neste nivel nao devem ter influencia
		// mutua, armazenamos os valores em uma tabela auxiliar, para depois
		// fazer o mapeamento.
		Map<Id, Valor> auxIdValor = new HashMap<Id, Valor>();
		Map<Id, ValorFuncao> auxIdValorFuncao = new HashMap<Id, ValorFuncao>();
		
		this.resolveBindings(ambiente, auxIdValor, auxIdValorFuncao);
		
		this.includeBindings(ambiente, auxIdValor, auxIdValorFuncao);
		
		Valor vresult = this.expressao.avaliar(ambiente);
		ambiente.restaura();
		return vresult;
	}
	
	private void includeBindings(AmbienteExecucao ambiente, Map<Id, Valor> auxIdValor,
			Map<Id, ValorFuncao> auxIdValorFuncao) throws VariavelJaDeclaradaException {
		for ( Map.Entry<Id, Valor> idValor : auxIdValor.entrySet() ) {
			ambiente.map(idValor.getKey(), idValor.getValue());
		}
		
		for ( Map.Entry<Id, ValorFuncao> idValorFuncao : auxIdValorFuncao.entrySet() ) {
			ambiente.map(idValorFuncao.getKey(), idValorFuncao.getValue());
		}
		
	}
	
	private void resolveBindings(AmbienteExecucao ambiente, Map<Id, Valor> auxIdValor,
			Map<Id, ValorFuncao> auxIdValorFuncao) throws VariavelNaoDeclaradaException,
			VariavelJaDeclaradaException {
		
		for ( DeclaracaoFuncional decFuncional : this.seqdecFuncional ) {
			if ( decFuncional.getAridade() == 0 ) {
				Expressao expressao = decFuncional.getExpressao();
				auxIdValor.put(decFuncional.getId(), expressao.avaliar(ambiente));
			}
			else {
				DecFuncao decFuncao = (DecFuncao) decFuncional;
				ValorFuncao valor = (ValorFuncao) decFuncao.getExpressao();
				auxIdValorFuncao.put(decFuncional.getId(), valor);
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
			// Checa o tipo de todas as declarações funcionais da ExpDeclaracao
			result = this.checkTypeBindings(ambiente);
			if ( result ) {
				// Verifica os tipos das DeclaracaoFuncional já resolvidos
				Map<Id, Tipo> resolvedTypes = this.resolveTypeBidings(ambiente);
				// Faz o binding dos tipos já resolvidos
				this.includeTypeBindings(ambiente, resolvedTypes);
				// Checa o tipo da expressão
				result = this.expressao.checaTipo(ambiente);
			}
		}
		finally {
			ambiente.restaura();
		}
		return result;
	}
	
	private Map<Id, Tipo> resolveTypeBidings(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		Map<Id, Tipo> resolvedTypes = new HashMap<Id, Tipo>();
		
		for ( DeclaracaoFuncional decFuncional : this.seqdecFuncional ) {
			Id id = decFuncional.getId();
			Tipo tipo = decFuncional.getTipo(ambiente);
			if ( resolvedTypes.put(id, tipo) != null ) {
				throw new VariavelJaDeclaradaException(decFuncional.getId());
			}
		}
		return resolvedTypes;
	}
	
	private boolean checkTypeBindings(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		boolean result = true;
		
		for ( DeclaracaoFuncional decFuncional : this.seqdecFuncional ) {
			if ( !decFuncional.checaTipo(ambiente) ) {
				ambiente.restaura();
				result = false;
			}
		}
		return result;
	}
	
	private void includeTypeBindings(AmbienteCompilacao ambiente,
			Map<Id, Tipo> resolvedTypes) throws VariavelJaDeclaradaException {
		if ( resolvedTypes.keySet() != null ) {
			for ( Id id : resolvedTypes.keySet() ) {
				Tipo t = resolvedTypes.get(id);
				ambiente.map(id, t);
			}
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
		
		Tipo vresult = null;
		
		for ( DeclaracaoFuncional decFuncional : this.seqdecFuncional ) {
			if ( decFuncional.getAridade() == 0 ) {
				ambiente.map(decFuncional.getId(), decFuncional.getExpressao().getTipo(
						ambiente));
			}
			else {
				DecFuncao decFuncao = (DecFuncao) decFuncional;
				Tipo tipo = decFuncao.getTipo(ambiente);
				if ( tipo != TipoPolimorfico.CURINGA ) {
					ambiente.map(decFuncional.getId(), tipo);
				}
			}
		}
		vresult = this.expressao.getTipo(ambiente);
		ambiente.restaura();
		return vresult;
	}
	
	/**
	 * Returns the seqdecVariavel.
	 * 
	 * @return List
	 */
	public List<? extends DeclaracaoFuncional> getSeqdecFuncional() {
		return this.seqdecFuncional;
	}
	
	/**
	 * Returns the expressao.
	 * 
	 * @return Expressao
	 */
	public Expressao getExpressao() {
		return this.expressao;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		ambiente.incrementa();
		
		for(DeclaracaoFuncional decFuncional : this.seqdecFuncional){
			ambiente.map(decFuncional.getId(), null);
			
			if(decFuncional.getAridade() > 0){
				DecFuncao decFuncao = (DecFuncao) decFuncional;

				ValorFuncao valorFuncao = (ValorFuncao) decFuncao.getExpressao();
				ValorFuncao novoValorFuncao = (ValorFuncao) valorFuncao.reduzir(ambiente);
				
				decFuncao.setValorFuncao(novoValorFuncao);
			}
		}
		this.expressao = expressao.reduzir(ambiente);
		
		ambiente.restaura();
		
		return this;
	}
	
	public ExpDeclaracao clone(){
		ExpDeclaracao retorno;		
		List<DeclaracaoFuncional> novaLista = new ArrayList<DeclaracaoFuncional>(this.seqdecFuncional.size());
		
		for (DeclaracaoFuncional dec : this.seqdecFuncional){
			novaLista.add(dec.clone());
		}
		
		retorno = new ExpDeclaracao(novaLista, this.expressao.clone());
		
		return retorno;
	}
}
