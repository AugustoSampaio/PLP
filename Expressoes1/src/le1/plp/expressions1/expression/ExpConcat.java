package le1.plp.expressions1.expression;

import le1.plp.expressions1.util.Tipo;
import le1.plp.expressions1.util.TipoPrimitivo;
import le1.plp.expressions2.memory.AmbienteCompilacao;
import le1.plp.expressions2.memory.AmbienteExecucao;

/**
* Um objeto desta classe representa uma Expressao de Concatenacao entre
* objetos <code>ValorString</code>
*/
public class ExpConcat extends ExpBinaria{
  

	/**
	 * Controi uma Expressao de Concatenacao com as sub-expressoes especificadas.
	 * Estas sub-expressoes devem ser tais que a avaliacao das mesmas resulta
	 * em <code>ValorString</code>
	 *
	 * @param esq Expressao da esquerda
	 * @param dir Expressao da direita
	 */
	public ExpConcat(Expressao esq, Expressao dir){
		super(esq, dir, "++");
	} 

	/**
	 * Retorna o valor da Expressao de Conjuncao Logica
	 * 
	 * @param amb o ambiente de execu��o.
	 */
	 public Valor avaliar(AmbienteExecucao amb){
		return new ValorString(
					( (ValorString) getEsq().avaliar(amb)).valor() +
					( (ValorString) getDir().avaliar(amb)).valor()
		);
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 *
	 * @param amb o ambiente de compila��o.
	 *
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 */
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) {
		return (getEsq().getTipo(amb).eString() && getDir().getTipo(amb).eString());
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 * 
	 * @param amb o ambiente de compila��o.
	 * 
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.STRING;
	}

}
