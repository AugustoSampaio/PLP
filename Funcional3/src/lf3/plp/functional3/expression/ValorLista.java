package lf3.plp.functional3.expression;

import java.util.ArrayList;
import java.util.List;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Valor;
import lf3.plp.expressions2.expression.ValorConcreto;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional3.util.ListaVaziaException;
import lf3.plp.functional3.util.TipoLista;

public class ValorLista extends ValorConcreto<List<Expressao>> {
	/*
	 * ValorLista precisa ser um valor concreto, pois � necess�rio realizar
	 * opera��es de elementos concretos com lista, ex. Op. de Igualdade -
	 * ExpEquals
	 */

	private Expressao head; // primeiro elemento da lista
	private ValorLista tail; // lista sem o primeiro elemento

	/**
	 * Contrutor de valorLista que referencia o construtor da classe super. �
	 * declarado como private para n�o ser instanciado diretamente por outra
	 * classe, obrigando a instanica��o via getInstancia
	 * 
	 * @param valor:
	 *            Lista
	 */
	private ValorLista(List<Expressao> valor) {
		// S� � poss�vel ter um construtor nesta classe devido a superClasse (valorConcreto), e deve obedecer a assinatura da sua superClasse
		super(valor);
	}

	/**
	 * 
	 * Cria uma instancia de ValorLista de acordo com os argumentos passados
	 * 
	 * @param head -
	 *            primeiro elemento da lista a ser criada
	 * @param tail -
	 *            elementos da lista a ser criada, sem o primeiro elemento
	 * @return
	 */
	public static ValorLista getInstancia(Expressao head, ValorLista tail) {
		/*
		 * A instancia do ValorLista � criada com o padr�o Factory Method devido
		 * a necessidade de ter construtores com parametros diferentes de List.
		 */

		List<Expressao> lista = new ArrayList<Expressao>();
		ValorLista instancia;

		if (head == null) {
			instancia = new ValorLista(lista);
		} else {
			lista.add(head);
			if (tail != null) {
				/*
				 * Ser� montada uma lista do tipo List com todos os elementos
				 * (head + tail) para ser passando na instancia de ValorLista,
				 * que referencia a instancia de ValorConcreta, que por sua vez
				 * espera um valor unico do tipo generico
				 */
				ValorLista list = tail;
				while (list != null && !list.isEmpty()) {
					lista.add(list.getHead());
					list = list.getTail();
				}
				List<Expressao> aux = new ArrayList<Expressao>();
				for (Expressao exp : lista) {
					aux.add(exp);
				}
				lista = aux;
			}
			instancia = new ValorLista(lista);
			instancia.head = head;
			instancia.tail = tail;
		}
		return instancia;
	}

	/**
	 * 
	 * Adiciona um elemento no in�cio da lista
	 * 
	 * @param value
	 *            -O elemento que ser� adicionada no in�cio da lista
	 */

	public ValorLista cons(Expressao value) {
		Expressao aux = this.head;
		this.head = value;
		this.valor().add(0, head);
		if (aux != null) {
			this.tail = ValorLista.getInstancia(aux, this.tail);
		}
		return this;
	}

	/**
	 * 
	 * Inverte os elementos de uma lista
	 * 
	 * @return - Retorna uma lista com a posi��o dos elementos invertida
	 */
	public ValorLista inverter() {
		ValorLista listReturn = ValorLista.getInstancia(null, null);
		ValorLista list = this.clone();

		if (!list.isEmpty()) {
			listReturn = ValorLista.getInstancia(list.getHead(), null);
			list = list.getTail();
			while (list != null && !list.isEmpty()) {
				listReturn.cons(list.getHead());
				list = list.getTail();
			}

		}

		return listReturn;
	}

	/**
	 * Retorna um clone da lista
	 * 
	 * @return Uma lista clonada
	 */

	@Override
	public ValorLista clone() {
		return ValorLista.getInstancia(this.head, this.tail);
	}

	/**
	 * Concatena uma lista ao final da inst�ncia da lista
	 * 
	 * @param lista2
	 *            Lista que ser� concatenada
	 * @return Retorna uma lista que � a concatena��o da inst�ncia da lista com
	 *         a lista passada como argumento
	 */

	public ValorLista concat(ValorLista lista) throws ListaVaziaException {
		if (lista == null)
			throw new ListaVaziaException();

		return concat(lista, this.clone());
	}

	private ValorLista concat(ValorLista esq, ValorLista dir)
			throws ListaVaziaException {
		if (dir == null || dir.getHead() == null)
			return esq;

		ValorLista retorno = concat(esq, dir.getTail());

		retorno.cons(dir.getHead());

		return retorno;
	}

	/**
	 * Avalia o valor primitivo de cada elemento da lista
	 * 
	 * @param amb
	 *            Ambiente de Execu��o
	 * @return ValorLista com os elementos avaliados
	 */
	@Override
	public Valor avaliar(AmbienteExecucao amb) {

		ValorLista retorno = getInstancia(null, null);

		ValorLista listAux = this.inverter();

		while (listAux != null && !listAux.isEmpty()) {
			retorno.cons(listAux.getHead().avaliar(amb));
			listAux = listAux.getTail();
		}
		return retorno;
	}

	/**
	 * Verifica se todos os elementos da lista s�o do mesmo tipo
	 * 
	 * @param amb
	 *            Ambiente de Compila��o
	 * @return Retorna "False" de os tipos dos elementos da lista forem
	 *         diferentes e "True" se forem iguais.
	 */

	@Override
	public boolean checaTipo(AmbienteCompilacao amb) {
		// Se vazia, n�o tem tipo para ser checado
		if (isEmpty())
			return true;

		ValorLista tail = getTail();
		if (tail == null)
			return true;

		TipoLista tipoTail = (TipoLista) tail.getTipo(amb);

		if (!getHead().getTipo(amb).eIgual(tipoTail.getSubTipo()))
			return false;

		return tail.checaTipo(amb);
	}

	/**
	 * Tipo da lista.
	 * 
	 * @param amb
	 *            Ambiente de Compilacao
	 * @return Retorna o tipo da lista que corresponde ao tipo de seus elementos
	 * 
	 */
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		Tipo tipoAux;

		if (this != null && !this.isEmpty()) {
			tipoAux = new TipoLista(this.getHead().getTipo(amb));
		} else {
			tipoAux = new TipoLista();
		}

		return tipoAux;
	}

	/**
	 * Retorna o primeiro elemento da lista
	 * 
	 * @return Elemento do in�cio da lista
	 */
	public Expressao getHead() {
		return this.head;
	}

	/**
	 * Retorna uma lista sem o primeiro elemento
	 * 
	 * @return Lista sem o primeiro elemento
	 */
	public ValorLista getTail() {
		return this.tail;
	}

	/**
	 * Verifica se a lista est� vazia
	 * 
	 * @return true - lista vazia false - lista n�o-vazia
	 */
	public boolean isEmpty() {
		if (this.head == null)
			return true;
		return false;
	}

	/**
	 * Atribui o elemento passado como argumento ao primeiro elemento da lista
	 * 
	 * @param head -
	 *            Elemento que representa o primeiro elemento da lista
	 */
	public void setHead(Expressao head) {
		this.head = head;
	}

	/**
	 * atribui a lista sem o primeiro elemento ao atributo tail
	 * 
	 * @param tail -
	 *            lista sem o primeiro elemento
	 */
	public void setTail(ValorLista tail) {
		this.tail = tail;
	}

	/**
	 * @return Retorna a lista com o seu formato padr�o: [] | [expressao
	 *         (,expressao)*]
	 */
	@Override
	public String toString() {
		return valor().toString();

	}

	@Override
	public boolean isEquals(ValorConcreto<List<Expressao>> obj) {
		ValorLista other = (ValorLista) obj;

		return valor().equals(other.valor());
	}
}
