package le1.plp.expressions1.expression;

import le1.plp.expressions1.util.Tipo;
import le1.plp.expressions1.expression.Valor;
import le1.plp.expressions2.memory.AmbienteCompilacao;
import le1.plp.expressions2.memory.AmbienteExecucao;

/** 
 * Uma expressao &eacute; a unidade basica na Linguagem de Expressoes.
 */
public interface Expressao {

	/**
	 * Avalia a expressao retornando seu Valor.
	 */
	Valor avaliar(AmbienteExecucao amb);

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 *
	 * @param amb o ambiente que contem o mapeamento entre identificadores
	 *          e tipos.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 */
	boolean checaTipo(AmbienteCompilacao amb);

	/**
	 * Retorna os tipos possiveis desta expressao.
	 *
	 * @param amb o ambiente que contem o mapeamento entre identificadores
	 *          e tipos.
	 * @return os tipos possiveis desta expressao.
	 */
	Tipo getTipo(AmbienteCompilacao amb);

}
