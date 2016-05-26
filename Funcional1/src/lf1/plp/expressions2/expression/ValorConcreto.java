package lf1.plp.expressions2.expression;

import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;

/**
 * @author bldb, efas, jcbr, srmq
 * 
 * This class groups different types of ValorConcreto.
 */
public abstract class ValorConcreto<T> implements Valor {

	private T valor;

	/**
	 * Retorna texto representando o valor string do objeto desta classe.
	 */
	@Override
	public String toString() {
		return String.valueOf(valor);
	}

	/**
	 * cria um objeto encapsulando o String fornecido
	 */
	public ValorConcreto(T valor) {
		this.valor = valor;
	}

	/**
	 * Retorna o string encapsulado pelo objeto desta classe
	 */
	public T valor() {
		return valor;
	}

	/**
	 * Determina igualdade entre objetos desta classe
	 */
	public boolean isEquals(ValorConcreto<T> obj) {
		return valor().equals(obj.valor());

	}

	/**
	 * Retorna o valor deste valor primitivo, i.e, ele mesmo.
	 */
	public Valor avaliar(AmbienteExecucao amb) {
		return this;
	}

	/**
	 * Realiza a verificacao de tipos desta expressao. Ser&aacute; sempre
	 * v&aacute;lida.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacao amb) {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (valor == null ? 0 : valor.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ValorConcreto<T> other = (ValorConcreto<T>) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	public Expressao reduzir(AmbienteExecucao ambiente) {
		return this;
	}
	
	public abstract ValorConcreto<T> clone();
}
