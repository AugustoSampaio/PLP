package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Um objeto desta classe representa uma Expressao de Igualdade entre Expressoes
 * cuja avaliacao resulta num mesmo valor primitivo.
 */
public class ExpEquals extends ExpBinaria {

	/**
	 * Controi uma Expressao de Igualdade com as sub-expressoes especificadas.
	 * Assume-se que estas sub-expressoes resultam num mesmo valor primitivo
	 * quando avaliadas.
	 * 
	 * @param esq
	 *            Expressao da esquerda
	 * @param dir
	 *            Expressao da direita
	 */
	public ExpEquals(Expressao esq, Expressao dir) {
		super(esq, dir, "==");
	}

	/**
	 * Retorna o valor da Expressao de Igualdade
	 */
	@SuppressWarnings("unchecked")
	public Valor avaliar(AmbienteExecucao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ValorConcreto esq = (ValorConcreto) getEsq().avaliar(amb);
		ValorConcreto dir = (ValorConcreto) getDir().avaliar(amb);
		return new ValorBooleano(esq.isEquals(dir));
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 * 
	 * @param ambiente
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *         <code>false</code> caso contrario.
	 * @exception VariavelNaoDeclaradaException
	 *                se existir um identificador nao declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException
	 *                se existir um identificador declarado mais de uma vez no
	 *                mesmo bloco do ambiente.
	 */
	@Override
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return getEsq().getTipo(ambiente).eIgual(getDir().getTipo(ambiente));
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 * 
	 * @param ambiente
	 *            o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao ambiente) {
		return TipoPrimitivo.BOOLEANO;
	}
	
	@Override
	public ExpBinaria clone() {
		return new ExpEquals(esq.clone(), dir.clone());
	}
}
