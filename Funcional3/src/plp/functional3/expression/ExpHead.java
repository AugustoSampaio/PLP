package plp.functional3.expression;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.ExpUnaria;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.functional1.util.TipoPolimorfico;
import plp.functional3.util.ListaVaziaException;
import plp.functional3.util.TipoLista;

public class ExpHead extends ExpUnaria {

	/**
	 * Cria uma instãncia de ExpHead
	 * 
	 * @param exp -
	 *            expressão onde será aplicada o Head
	 */
	public ExpHead(Expressao exp) {
		super(exp, "head");
	}

	/**
	 * Checa o tipo da expressão head. É necessário que a expressão seja uma
	 * Lista.
	 * 
	 * @param amb
	 *            Ambiente de Compilação
	 * @return "True" se expressão é uma lista e "false" se expressão não é uma
	 *         lista
	 */
	@Override
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		Tipo tipoExp = getExp().getTipo(amb);
		return tipoExp.eIgual(new TipoLista());
	}

	/**
	 * Avalia a expressão Head até obter seus valores.
	 * 
	 * @param amb
	 *            Ambiente de Execução
	 * @return Retorna o valor(avaliado) do primeiro elemento da lista
	 *         (expressão do Head)
	 */
	public Valor avaliar(AmbienteExecucao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		ValorLista lista = (ValorLista) this.getExp().avaliar(amb);
		if (lista.isEmpty())
			throw new ListaVaziaException();

		return (Valor) lista.getHead();
	}

	/**
	 * Retorna o tipo da Expressão, ou seja, os tipos do elemento da lista, onde
	 * é aplicado o Head
	 * 
	 * @param amb
	 *            Ambiente de Compilação
	 * @return tipo do elemento da lista
	 */
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		Tipo tipoExp = this.getExp().getTipo(amb);
		if (tipoExp instanceof TipoPolimorfico)
			tipoExp = ((TipoPolimorfico) tipoExp).getTipoInstanciado();

		if (tipoExp instanceof TipoLista)
			return ((TipoLista) tipoExp).getSubTipo();

		return tipoExp;
	}
	
	public ExpHead clone() {
		return new ExpHead(this.exp.clone());
	}
}