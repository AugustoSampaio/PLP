package plp.functional1.util;

import java.util.ArrayList;
import java.util.List;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Id;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;

public class DefFuncao {

	protected List<Id> argsId;

	protected Expressao exp;

	public DefFuncao(List<Id> argsId, Expressao exp) {
		this.argsId = argsId;
		this.exp = exp;
	}

	public List<Id> getListaId() {
		return argsId;
	}

	public Expressao getExp() {
		return exp;
	}

	/**
	 * Retorna a aridade desta funcao.
	 * 
	 * @return a aridade desta funcao.
	 */
	public int getAridade() {
		return argsId.size();
	}

	/**
	 * Realiza a verificacao de tipos desta declaração.
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

		// Usa uma instância de TipoQualquer para cada parâmetro formal.
		// Essa instância será inferida durante o getTipo de exp.
		for (Id id : argsId) {
			ambiente.map(id, new TipoPolimorfico());
		}

		// Chama o checa tipo da expressão para veririficar se o corpo da
		// função está correto. Isto irá inferir o tipo dos parâmetros.
		boolean result = exp.checaTipo(ambiente);

		ambiente.restaura();

		return result;
	}

	/**
	 * Retorna os tipos possiveis desta função.
	 * 
	 * @param amb
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            tipos.
	 * @return os tipos possiveis desta declaração.
	 * @exception VariavelNaoDeclaradaException
	 *                se houver uma vari&aacute;vel n&atilde;o declarada no
	 *                ambiente.
	 * @exception VariavelJaDeclaradaException
	 *                se houver uma mesma vari&aacute;vel declarada duas vezes
	 *                no mesmo bloco do ambiente.
	 * @precondition exp.checaTipo();
	 */
	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		for (Id id : argsId) {
			ambiente.map(id, new TipoPolimorfico());
		}

		// Usa o checaTipo apenas para inferir o tipo dos parâmetros.
		// Pois o getTipo da expressão pode simplismente retornar o
		// tipo, por exemplo, no caso de uma expressão binária ou unária
		// os tipos sempre são bem definidos (Booleano, Inteiro ou String).
		exp.checaTipo(ambiente);

		// Compõe o tipo desta função do resultado para o primeiro parâmetro.
		Tipo result = exp.getTipo(ambiente);

		// Obtêm o tipo inferido de cada parâmetro.
		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		Tipo argTipo;
		for (int i = 0; i < getAridade(); i++) {
			argTipo = ((TipoPolimorfico) ambiente.get(argsId.get(i))).inferir();
			params.add(argTipo);
		}
		result = new TipoFuncao(params, result);

		ambiente.restaura();

		return result;
	}
	
	public DefFuncao clone() {
		List<Id> novaLista = new ArrayList<Id>(this.argsId.size());
		
		for (Id id : this.argsId){
			novaLista.add(id.clone());
		}
		
		return new DefFuncao(novaLista, this.exp.clone());
	}
}
