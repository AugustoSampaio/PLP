package li2.plp.imperative1.memory;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.memory.*;

public interface AmbienteCompilacaoImperativa extends AmbienteCompilacao {

	public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
		EntradaVaziaException;

}
