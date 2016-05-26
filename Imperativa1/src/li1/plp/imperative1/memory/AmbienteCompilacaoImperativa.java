package li1.plp.imperative1.memory;

import li1.plp.expressions1.util.Tipo;
import li1.plp.expressions2.memory.*;

public interface AmbienteCompilacaoImperativa extends AmbienteCompilacao {

	public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
		EntradaVaziaException;

}
