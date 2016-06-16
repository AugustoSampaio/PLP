package lf1.plp.functional1.declaration;


import java.util.Map;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.memory.AmbienteExecucaoFuncional;
import lf1.plp.functional1.util.DefFuncao;

public interface DeclaracaoFuncional {

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
	public boolean checaTipo(AmbienteCompilacao ambiente) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException;
	public void elabora(AmbienteExecucaoFuncional amb, AmbienteExecucaoFuncional aux) throws VariavelJaDeclaradaException;
	public void elabora(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException;
	public void incluir(AmbienteExecucaoFuncional amb, AmbienteExecucaoFuncional aux) throws VariavelJaDeclaradaException;
	public void incluir(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException;

	public DeclaracaoFuncional clone();
}
