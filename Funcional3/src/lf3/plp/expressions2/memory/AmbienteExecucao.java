package lf3.plp.expressions2.memory;

import lf3.plp.expressions2.expression.Valor;


public interface AmbienteExecucao extends Ambiente<Valor> {

	public AmbienteExecucao clone();

}
