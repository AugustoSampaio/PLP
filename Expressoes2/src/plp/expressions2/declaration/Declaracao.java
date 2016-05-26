package plp.expressions2.declaration;

import java.util.Map;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.Id;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;

public interface Declaracao {
	public void elabora(AmbienteExecucao amb, Map<Id, Valor> declaracoes) throws VariavelJaDeclaradaException;
	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException;
	public void incluir(AmbienteExecucao amb, Map<Id, Valor> declaracoes) throws VariavelJaDeclaradaException;
	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException;
	public boolean checaTipo(AmbienteCompilacao amb) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException;
	public void reduzir(AmbienteExecucao amb);
}
