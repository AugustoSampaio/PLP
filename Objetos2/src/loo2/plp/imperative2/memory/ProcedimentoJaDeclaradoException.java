package loo2.plp.imperative2.memory;

import loo2.plp.expressions2.expression.Id;
import loo2.plp.expressions2.memory.IdentificadorJaDeclaradoException;

public class ProcedimentoJaDeclaradoException extends
		IdentificadorJaDeclaradoException {

	private static final long serialVersionUID = -1793156786677618760L;

	public ProcedimentoJaDeclaradoException(Id id) {
		super("Procedimento " + id + " j� declarado.");
	}

}
