package lf3.plp.functional3.expression;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.ExpBinaria;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Valor;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpConcatLista extends ExpBinaria {

	/**
	 * Cria uma inst�ncia de ExpConcatLista
	 * 
	 * @param esq -
	 *            expressao � esquerda de ConcatLista
	 * @param dir -
	 *            expressao � direita de ConcatLista
	 */
	public ExpConcatLista(Expressao esq, Expressao dir) {
		super(esq, dir, "concat");
	}

	/**
	 * Verifica se os elementos Terminais s�o listas, e se os subtipos das
	 * listas possuem o mesmo tipo.
	 * 
	 * @param amb
	 *            Ambiente de Compila��o
	 * @return "true" se os tipos forem iguais e "false" de os tipos forem
	 *         diferentes
	 * @throws VariavelNaoDeclaradaException,
	 *             VariavelJaDeclaradaException
	 */
	@Override
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		Tipo tipoEsq = getEsq().getTipo(amb);
		Tipo tipoDir = getDir().getTipo(amb);

		return tipoEsq.eIgual(tipoDir);

	}

	/**
	 * Realiza a concatena��o das listas
	 * 
	 * @param amb
	 *            Ambiente de Execu��o
	 * @return lista avaliada e concatenada
	 * @throws VariavelNaoDeclaradaException,
	 *             VariavelJaDeclaradaException
	 */
	public Valor avaliar(AmbienteExecucao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ValorLista lista = ValorLista.getInstancia(null, null);
		lista = ((ValorLista) this.getEsq().avaliar(amb)).clone();
		lista = lista.concat((ValorLista) this.getDir().avaliar(amb));
		return lista;
	}

	/**
	 * Retorna o tipo da express�o
	 * 
	 * @param amb
	 *            Ambiente de Compila��o
	 * @return Tipo das express�o associadas ao concatLista
	 * @throws VariavelNaoDeclaradaException,
	 *             VariavelJaDeclaradaException
	 */
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return this.getDir().getTipo(amb);
	}
	
	public ExpConcatLista clone() {
		return new ExpConcatLista(this.esq.clone(), this.dir.clone());
	}
}
