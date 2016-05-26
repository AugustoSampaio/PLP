package le2.plp.expressions2.declaration;

import java.util.Map;

import le2.plp.expressions1.util.Tipo;
import le2.plp.expressions2.expression.Id;
import le2.plp.expressions2.expression.Valor;
import le2.plp.expressions2.memory.AmbienteCompilacao;
import le2.plp.expressions2.memory.AmbienteExecucao;
import le2.plp.expressions2.memory.VariavelJaDeclaradaException;
import le2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public interface Declaracao {
	public void elabora(AmbienteExecucao amb, Map<Id, Valor> declaracoes) throws VariavelJaDeclaradaException;
	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException;
	public void incluir(AmbienteExecucao amb, Map<Id, Valor> declaracoes) throws VariavelJaDeclaradaException;
	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException;
	public boolean checaTipo(AmbienteCompilacao amb) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException;
	public void reduzir(AmbienteExecucao amb);
}
