package lf1.plp.expressions2.expression;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions1.util.TipoPrimitivo;
import lf1.plp.expressions2.memory.AmbienteCompilacao;

/**
 * Objetos desta classe encapsulam valor inteiro.
 */
public class ValorInteiro extends ValorConcreto<Integer> {

	/**
	 * Cria <code>ValorInteiro</code> contendo o valor fornecido.
	 */
	public ValorInteiro(Integer valor) {
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
		return TipoPrimitivo.INTEIRO;
	}

	public ValorInteiro clone(){
		return new ValorInteiro(this.valor());
	}
}
