package plp.imperative1.memory;

public class ErroTipoEntradaException extends Exception {
	
	public ErroTipoEntradaException() {
	    super("Tipo do valor de entrada lido incompátivel");
	  }
	
	public ErroTipoEntradaException(String msg) {
	    super(msg);
	  }

}
