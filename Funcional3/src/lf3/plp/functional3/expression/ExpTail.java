package lf3.plp.functional3.expression;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.ExpUnaria;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Valor;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional3.util.ListaVaziaException;
import lf3.plp.functional3.util.TipoLista;

public class ExpTail extends ExpUnaria {

	/**
	 * Cria uma inst�ncia da Express�o Tail
	 * @param exp - lista a ser aplicada a opera��o Tail
	 */
	public ExpTail(Expressao exp) {
		super(exp, "tail");
	}

	/**
	 * Verifica a compatilibilidade de tipos da expressao a ser aplicada a opera��o Tail
	 * O tipo da express�o tem que ser lista
	 * @param amb Ambiente de Compila��o
	 * @return Retorna true se os tipos s�o compat�veis, e false se n�o s�o compat�veis.
	 */
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {		
		Tipo tipoExp = getExp().getTipo(amb);			
		return tipoExp.eIgual(new TipoLista());
	}

	/**
	 * Avalia o valor da lista e retira o seu primeiro elemento
	 * @param Ambiente de Execu��o
	 * @return Lista avaliada sem o primeiro elemento
	 * */
	public Valor avaliar(AmbienteExecucao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		
		ValorLista lista = (ValorLista) this.getExp().avaliar(amb);
		
		// Tail de uma lista vazia n�o existe � como se fosse uma divis�o por zero
		if (lista.isEmpty()) {
			throw new ListaVaziaException();
		}
		
		ValorLista tail = lista.getTail();
		
		// Se o tail for nulo (lista de apenas um elemento)
		// retornamos uma lista vazia pois tail([x]) = [] 
		if (tail == null)
		{
			tail = ValorLista.getInstancia(null, null);
		}
		
		return tail;
	}

	/**
	 * Retorna o tipo da express�o do Tail
	 * @param  Ambiente de Compila��o
	 * @return Tipo da express�o
	 */
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {		
		return this.getExp().getTipo(amb);
	}

	@Override
	public ExpTail clone() {
		return new ExpTail(this.exp.clone());
	}
}
