package lf3.plp.functional1.declaration;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;

public interface DeclaracaoFuncional {

	public Id getId();

	/**
	 * Retorna a aridade da funcao declarada. Variaveis tem aridade 0.
	 *
	 * @return a aridade da funcao declarada.
	 */
	public int getAridade();

	/**
	 * Retorna a expressao associada.
	 *
	 * @return a expressao associada.
	 */
	public Expressao getExpressao();

	/**
	 * Realiza a verificacao de tipos desta declara��o.
	 *
	 * @param amb o ambiente de compila��o.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          nao declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          declarado mais de uma vez no mesmo bloco do ambiente.
	 */
	public boolean checaTipo(AmbienteCompilacao ambiente)
		throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException;

	/**
	 * Retorna os tipos possiveis desta declara��o.
	 *
	 * @param amb o ambiente que contem o mapeamento entre identificadores
	 *          e tipos.
	 * @return os tipos possiveis desta declara��o.
	 * @exception VariavelNaoDeclaradaException se houver uma vari&aacute;vel
	 *          n&atilde;o declarada no ambiente.
	 * @exception VariavelJaDeclaradaException se houver uma mesma
	 *           vari&aacute;vel declarada duas vezes no mesmo bloco do
	 *           ambiente.
	 */
	Tipo getTipo(AmbienteCompilacao amb)
		throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException;

	public DeclaracaoFuncional clone();
}
