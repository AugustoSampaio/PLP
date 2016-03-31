package plp.functional3.exception;

import plp.expressions2.expression.Id;

public class TipoRetornoPadraoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static String formatStr = "Tipo de retorno incorreto na função %s";
	
	public TipoRetornoPadraoException(Id id) {
		super(String.format(formatStr, id.getIdName()));
	}
	
}
