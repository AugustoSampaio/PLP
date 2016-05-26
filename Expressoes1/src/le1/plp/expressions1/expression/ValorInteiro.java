package le1.plp.expressions1.expression;

import le1.plp.expressions1.util.Tipo;
import le1.plp.expressions1.util.TipoPrimitivo;
import le1.plp.expressions2.memory.AmbienteCompilacao;

/**
 * Objetos desta classe encapsulam valor inteiro.
 */
public class ValorInteiro extends ValorConcreto<Integer> {
	
	public ValorInteiro(int valor) {
		super(valor);
	}
	
	/**
	 * Retorna os tipos possiveis desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * 
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.INTEIRO;
	}

}
