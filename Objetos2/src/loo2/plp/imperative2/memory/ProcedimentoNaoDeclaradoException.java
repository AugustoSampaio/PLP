package loo2.plp.imperative2.memory;

import loo2.plp.expressions2.expression.Id;
import loo2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;

public class ProcedimentoNaoDeclaradoException extends
		IdentificadorNaoDeclaradoException {

	private static final long serialVersionUID = -5023666514074530307L;

	public ProcedimentoNaoDeclaradoException(Id id) {
		super("Procedimento " + id + " nao declarado.");
	}

}
