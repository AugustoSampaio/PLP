package plp.functional3.expression;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.ExpUnaria;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.functional3.util.ListaVaziaException;
import plp.functional3.util.TipoLista;

public class ExpTail extends ExpUnaria {

	/**
	 * Cria uma instância da Expressão Tail
	 * @param exp - lista a ser aplicada a operação Tail
	 */
	public ExpTail(Expressao exp) {
		super(exp, "tail");
	}

	/**
	 * Verifica a compatilibilidade de tipos da expressao a ser aplicada a operação Tail
	 * O tipo da expressão tem que ser lista
	 * @param amb Ambiente de Compilação
	 * @return Retorna true se os tipos são compatíveis, e false se não são compatíveis.
	 */
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {		
		Tipo tipoExp = getExp().getTipo(amb);			
		return tipoExp.eIgual(new TipoLista());
	}

	/**
	 * Avalia o valor da lista e retira o seu primeiro elemento
	 * @param Ambiente de Execução
	 * @return Lista avaliada sem o primeiro elemento
	 * */
	public Valor avaliar(AmbienteExecucao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		
		ValorLista lista = (ValorLista) this.getExp().avaliar(amb);
		
		// Tail de uma lista vazia não existe é como se fosse uma divisão por zero
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
	 * Retorna o tipo da expressão do Tail
	 * @param  Ambiente de Compilação
	 * @return Tipo da expressão
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
