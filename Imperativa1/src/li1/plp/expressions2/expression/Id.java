package li1.plp.expressions2.expression;

import li1.plp.expressions1.util.Tipo;
import li1.plp.expressions2.memory.AmbienteCompilacao;
import li1.plp.expressions2.memory.AmbienteExecucao;
import li1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li1.plp.functional2.expression.ValorIrredutivel;

public class Id implements Expressao {

	private String idName;

	public Id(String strName) {
		idName = strName;
	}

	@Override
	public String toString() {
		return idName;
	}

	public Valor avaliar(AmbienteExecucao ambiente)
			throws VariavelNaoDeclaradaException {
		return ambiente.get(this);
	}

	/**
	 * Realiza a verificacao de tipos desta expressao. Ser&aacute; v&aacute;lida
	 * se o identificador estiver declarado.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *         <code>false</code> caso contrario.
	 * @exception VariavelNaoDeclaradaException
	 *                se este identificador nao estiver no ambiente.
	 */
	public boolean checaTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException {
		boolean result = true;
		amb.get(this); // se estiver no ambiente, entao esta ok.
		return result;
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 * @exception VariavelNaoDeclaradaException
	 *                se este identificador nao estiver no ambiente.
	 */
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException {
		return amb.get(this);
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (idName == null ? 0 : idName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!obj.getClass().isAssignableFrom(getClass())) {
			return false;
		}
		boolean r;

		Id other = (Id) obj;
		if (this.idName == null) {
			r = other.idName == null;
		} else {
			r = this.idName.equals(other.idName);
		}

		return r;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		try {
			Valor valor = ambiente.get(this);
			
			if (valor instanceof ValorIrredutivel) {
				return this;
			}
						
			return valor.clone();
			
		} catch (VariavelNaoDeclaradaException e) {
			return this;
		}
	}
	
	public Id clone() {
		return this;
	}
}
