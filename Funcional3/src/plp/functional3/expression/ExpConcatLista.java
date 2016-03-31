package plp.functional3.expression;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.ExpBinaria;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpConcatLista extends ExpBinaria {

	/**
	 * Cria uma instância de ExpConcatLista
	 * 
	 * @param esq -
	 *            expressao à esquerda de ConcatLista
	 * @param dir -
	 *            expressao à direita de ConcatLista
	 */
	public ExpConcatLista(Expressao esq, Expressao dir) {
		super(esq, dir, "concat");
	}

	/**
	 * Verifica se os elementos Terminais são listas, e se os subtipos das
	 * listas possuem o mesmo tipo.
	 * 
	 * @param amb
	 *            Ambiente de Compilação
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
	 * Realiza a concatenação das listas
	 * 
	 * @param amb
	 *            Ambiente de Execução
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
	 * Retorna o tipo da expressão
	 * 
	 * @param amb
	 *            Ambiente de Compilação
	 * @return Tipo das expressão associadas ao concatLista
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
