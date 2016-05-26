package li1.plp.expressions2.expression;

import li1.plp.expressions1.util.Tipo;
import li1.plp.expressions1.util.TipoPrimitivo;
import li1.plp.expressions2.memory.AmbienteCompilacao;
import li1.plp.expressions2.memory.AmbienteExecucao;
import li1.plp.expressions2.memory.VariavelJaDeclaradaException;
import li1.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Um objeto desta classe representa uma Expressao de menos unario.
 */
public class ExpMenos extends ExpUnaria {

	/**
	 * Controi uma Expressao de menos unario com expressao especificada
	 *
	 * @param exp Expressao cuja avaliacao resulta <code>ValorInteiro</code>.
	 */
	public ExpMenos(Expressao exp){
		super(exp, "-");
	}

	/**
	 * Retorna o valor da Expressao de menos unario.
	 * 
	 * @param amb o ambiente de execu��o.
	 * @return o valor da express�o avaliada.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          nao declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          declarado mais de uma vez no mesmo bloco do ambiente.
	 */
	public Valor avaliar(AmbienteExecucao amb) throws VariavelJaDeclaradaException, 
			VariavelNaoDeclaradaException {
		return new ValorInteiro(- ((ValorInteiro)getExp().avaliar(amb)).valor());
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 * 
	 * @param amb o ambiente de compila��o.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          nao declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          declarado mais de uma vez no mesmo bloco do ambiente.
	 */
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) throws VariavelJaDeclaradaException, 
			VariavelNaoDeclaradaException {
		return (getExp().getTipo(amb).eInteiro());
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 * 
	 * @param amb o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.INTEIRO;
	}
	
	@Override
	public ExpUnaria clone() {
	
		return new ExpMenos(exp.clone());
	}
}
