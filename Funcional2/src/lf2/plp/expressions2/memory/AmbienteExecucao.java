package lf2.plp.expressions2.memory;

import lf2.plp.expressions2.expression.Valor;


public interface AmbienteExecucao extends Ambiente<Valor> {

	public AmbienteExecucao clone();

}
