package plp.functional3.exception;

import plp.expressions2.expression.Id;

public class NumeroParametrosException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static String formatStr = "Número de parâmetros incorretos para a função %s";
	
	public NumeroParametrosException(Id id) {
		super(String.format(formatStr, id.getIdName()));
	}
	
}
