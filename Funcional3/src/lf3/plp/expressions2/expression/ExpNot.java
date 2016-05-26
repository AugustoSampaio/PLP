package lf3.plp.expressions2.expression;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions1.util.TipoPrimitivo;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Um objeto desta classe representa uma Expressao de Negacao logica.
 */
public class ExpNot extends ExpUnaria{

	/**
	 * Controi uma Expressao de negacao logica com expressao
	 * especificada.
	 * 
	 * @param exp Expressao a ser negada. Assume-se que sua avaliacao resulta
	 *        em <code>ValorBooleano</code>.
	 */
	public ExpNot( Expressao exp) {
		super(exp, "~");
	}

	/**
	 * Retorna o valor da Expressao de negacao logica.
	 * 
	 * @param amb o ambiente de execu��o.
	 * @return o valor da express�o avaliada.
	 * @exception VariavelNaoDeclaradaException se a vari�vel n�o est� 
	 *            declarada no ambiente. 
	 */
	public Valor avaliar(AmbienteExecucao amb) throws VariavelJaDeclaradaException, 
			VariavelNaoDeclaradaException {
		return new ValorBooleano(!((ValorBooleano) getExp().avaliar(amb)).valor());
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
		return (getExp().getTipo(amb).eBooleano());
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 *
	 * @param amb o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.BOOLEANO;
	}
	
	@Override
	public ExpUnaria clone() {
		return new ExpNot(exp.clone());
	}
}
