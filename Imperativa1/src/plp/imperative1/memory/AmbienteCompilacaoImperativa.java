package plp.imperative1.memory;

import plp.expressions1.util.Tipo;
import plp.expressions2.memory.*;

public interface AmbienteCompilacaoImperativa extends AmbienteCompilacao {

	public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
		EntradaVaziaException;

}
