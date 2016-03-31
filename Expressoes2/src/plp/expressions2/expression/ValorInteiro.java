package plp.expressions2.expression;

import plp.expressions1.util.Tipo;
import plp.expressions1.util.TipoPrimitivo;
import plp.expressions2.memory.AmbienteCompilacao;

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
	 *            o ambiente de compilação.
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.INTEIRO;
	}

	public ValorInteiro clone(){
		return new ValorInteiro(this.valor());
	}
}
