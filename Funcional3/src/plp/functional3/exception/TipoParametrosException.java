package plp.functional3.exception;

import plp.expressions2.expression.Id;

public class TipoParametrosException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static String formatStr = "Tipo de parâmetros incorretos para a função %s";
	
	public TipoParametrosException(Id id) {
		super(String.format(formatStr, id.getIdName()));
	}
	
}
