package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Um objeto desta classe representa uma Expressao de tamanho de String.
 */
public class ExpLength extends ExpUnaria {
 
	/**
	 * Controi uma Expressao de tamanho  com a expressao especificada
	 * assume-se que <code>exp</code> &eacute; uma expressao cuja avaliacao
	 * resulta num <code>ValorString</code>
	 * 
	 * @param exp a express�o em quest�o.
	 */
	public ExpLength(Expressao exp) {
		super(exp, "length");
	}

	/**
	 * Retorna o valor da Expressao de tamanho.
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
		return new ValorInteiro (((ValorString)getExp().avaliar(amb)).valor().length());
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
		return (getExp().getTipo(amb).eString());
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
		return new ExpLength(exp.clone());
	}
}
