package plp.imperative1.command;

import plp.imperative1.memory.AmbienteExecucaoImperativa;
import plp.imperative1.memory.AmbienteCompilacaoImperativa;

;

public class Skip implements Comando {

	/**
	 * Não realiza nenhuma alteração no ambiente.
	 * 
	 * @param ambiente
	 *            o ambiente de execução.
	 * 
	 * @return o ambiente inalterado.
	 * 
	 */
	public AmbienteExecucaoImperativa executar(
			AmbienteExecucaoImperativa ambiente) {
		return ambiente;
	}

	/**
	 * Realiza a verificacao de tipos do comando
	 * 
	 * @param ambiente
	 *            o ambiente de compilação.
	 * @return <code>true</code> se o comando é bem tipado; <code>false</code>
	 *         caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente) {
		return true;
	}
}
