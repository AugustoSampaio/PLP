package plp.imperative1.command;

import plp.expressions2.expression.Id;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.imperative1.memory.AmbienteCompilacaoImperativa;
import plp.imperative1.memory.AmbienteExecucaoImperativa;
import plp.imperative1.memory.EntradaVaziaException;
import plp.imperative1.memory.ErroTipoEntradaException;

public class Read implements IO {

	private Id id;

	public Read(Id id) {
		this.id = id;
	}

	/**
	 * Lê da entrada padrão.
	 * 
	 * @param ambiente
	 *            o ambiente de execução.
	 * 
	 * @return o ambiente depois de modificado pela execução do comando read.
	 * @throws ErroTipoEntradaException 
	 * 
	 */
	public AmbienteExecucaoImperativa executar(
			AmbienteExecucaoImperativa ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			EntradaVaziaException, ErroTipoEntradaException {
				
		Valor valorID = ambiente.get(id);
		Valor valorRead =ambiente.read();
		if (valorID.getTipo(null).eIgual(valorRead.getTipo(null))) {
			ambiente.changeValor(id, valorRead );
		}else{
			throw new ErroTipoEntradaException("Tipo do valor de entrada lido incompátivel" +
					" com tipo da variável (" + id.getIdName() + ")");
		}
		
		
		
		
		return ambiente;
	}

	/**
	 * Realiza a verificacao de tipos da entrada
	 * 
	 * @param ambiente
	 *            o ambiente de compilação.
	 * @return <code>true</code> se a expressão da entrada está bem tipada;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws VariavelNaoDeclaradaException, EntradaVaziaException,
			VariavelJaDeclaradaException {
		//return id.getTipo(ambiente).equals(ambiente.getTipoEntrada());
		return true;
	}

}
