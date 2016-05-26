package loo2.plp.expressions2.expression;

import loo2.plp.expressions1.util.Tipo;
import loo2.plp.expressions1.util.TipoPrimitivo;
import loo2.plp.expressions2.memory.AmbienteCompilacao;

/**
 * Este valor primitivo encapsula um String.
 */
public class ValorString extends ValorConcreto<String> {

	/**
	 * cria um objeto encapsulando o String fornecido
	 */
	public ValorString(String valor) {
		super(valor);
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.STRING;
	}

	@Override
	public String toString() {
		return String.format("\"%s\"", super.toString());
	}
	
	public ValorString clone() {
		return new ValorString(this.valor());
	}
}
