package plp.functional1.declaration;

import java.util.Map;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.Id;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.functional1.memory.AmbienteExecucaoFuncional;
import plp.functional1.util.DefFuncao;

public interface DeclaracaoFuncional {

	/**
	 * Realiza a verificacao de tipos desta declaração.
	 *
	 * @param amb o ambiente de compilação.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          nao declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          declarado mais de uma vez no mesmo bloco do ambiente.
	 */
	public boolean checaTipo(AmbienteCompilacao ambiente) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException;
	public void elabora(AmbienteExecucaoFuncional amb, Map<Id,Valor> declaracoes, Map<Id,DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException;
	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException;
	public void incluir(AmbienteExecucaoFuncional amb, Map<Id,Valor> declaracoes, Map<Id,DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException;
	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException;

	public DeclaracaoFuncional clone();
}
