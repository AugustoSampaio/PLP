package le1.plp.expressions1.expression;

import le1.plp.expressions1.util.Tipo;
import le1.plp.expressions1.util.TipoPrimitivo;
import le1.plp.expressions2.memory.AmbienteCompilacao;

/**
 * Este valor primitivo encapsula um valor booleano.
 */
public class ValorBooleano extends ValorConcreto<Boolean> {

	
	/**
	 * Cria um objeto encapsulando o valor booleano fornecido.
	 */
	public ValorBooleano(boolean valor) {
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
		return TipoPrimitivo.BOOLEANO;
	}


}