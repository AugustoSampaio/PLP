package plp.functional3.expression;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.ExpBinaria;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.functional3.util.TipoLista;

public class ExpCons extends ExpBinaria {

	/**
	 * Cria uma instância de ExpCons
	 * 
	 * @param esq -
	 *            expressão à esquerda do operador cons
	 * @param dir -
	 *            expressão à direita do operador cons
	 */
	public ExpCons(Expressao esq, Expressao dir) {
		super(esq, dir, "cons");
	}

	/**
	 * Checa os tipos válidos para as expressões de um operador cons. A
	 * expressão à direita deve ser uma lista (ValorLista) e a expressão à
	 * esquerda deve ter o mesmo tipo dos elementos da lista
	 * 
	 * @param amb
	 *            Ambiente de Compilação
	 * @return "true" tipos válidos e "false" tipos invalidos
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
	 * Avalia o resultado a operação cons com suas expressões
	 * 
	 * @param amb
	 *            Ambiente de Execução
	 * @return lista avaliada com o novo elemento no início da lista
	 */
	public Valor avaliar(AmbienteExecucao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ValorLista listResult = ((ValorLista) this.getDir().avaliar(amb))
				.clone();
		listResult.cons(this.getEsq().avaliar(amb));

		return listResult;
	}

	/**
	 * Retorna o tipo da expressão Cons. Equivale ao tipo dos elementos da lista
	 * (expressao à direita)
	 * 
	 * @param amb
	 *            Ambiente de Compilação
	 * @return tipo da Expressão Cons
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
