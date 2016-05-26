package lf1.plp.expressions2.memory;

import lf1.plp.expressions2.expression.Valor;


public interface AmbienteExecucao extends Ambiente<Valor> {

	public AmbienteExecucao clone();

}
