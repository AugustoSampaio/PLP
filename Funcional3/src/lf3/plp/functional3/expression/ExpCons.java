package lf3.plp.functional3.expression;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.ExpBinaria;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Valor;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional3.util.TipoLista;

public class ExpCons extends ExpBinaria {

	/**
	 * Cria uma inst�ncia de ExpCons
	 * 
	 * @param esq -
	 *            express�o � esquerda do operador cons
	 * @param dir -
	 *            express�o � direita do operador cons
	 */
	public ExpCons(Expressao esq, Expressao dir) {
		super(esq, dir, "cons");
	}

	/**
	 * Checa os tipos v�lidos para as express�es de um operador cons. A
	 * express�o � direita deve ser uma lista (ValorLista) e a express�o �
	 * esquerda deve ter o mesmo tipo dos elementos da lista
	 * 
	 * @param amb
	 *            Ambiente de Compila��o
	 * @return "true" tipos v�lidos e "false" tipos invalidos
	 */
	@Override
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		boolean result = true;

		Tipo tipoDir = getDir().getTipo(amb);

		if (!tipoDir.eIgual(new TipoLista()))
			return false;

		Tipo tipoEsq = this.getEsq().getTipo(amb);
		if (tipoDir instanceof TipoLista) {
			TipoLista tipoListaDir = (TipoLista) tipoDir;
			return tipoEsq.eIgual(tipoListaDir.getSubTipo());
		}

		return result;

	}

	/**
	 * Avalia o resultado a opera��o cons com suas express�es
	 * 
	 * @param amb
	 *            Ambiente de Execu��o
	 * @return lista avaliada com o novo elemento no in�cio da lista
	 */
	public Valor avaliar(AmbienteExecucao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ValorLista listResult = ((ValorLista) this.getDir().avaliar(amb))
				.clone();
		listResult.cons(this.getEsq().avaliar(amb));

		return listResult;
	}

	/**
	 * Retorna o tipo da express�o Cons. Equivale ao tipo dos elementos da lista
	 * (expressao � direita)
	 * 
	 * @param amb
	 *            Ambiente de Compila��o
	 * @return tipo da Express�o Cons
	 * 
	 */
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		return this.getDir().getTipo(amb);
	}
	
	public ExpCons clone() {
		return new ExpCons(this.esq.clone(), this.dir.clone());
	}
}
