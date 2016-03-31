package plp.imperative1.command;

import plp.imperative1.memory.AmbienteCompilacaoImperativa;
import plp.imperative1.memory.AmbienteExecucaoImperativa;
import plp.imperative1.memory.EntradaVaziaException;
import plp.imperative1.memory.ErroTipoEntradaException;
import plp.expressions2.memory.IdentificadorJaDeclaradoException;
import plp.expressions2.memory.IdentificadorNaoDeclaradoException;

public class SequenciaComando implements Comando {

	private Comando comando1;
	private Comando comando2;

	public SequenciaComando(Comando comando1, Comando comando2) {
		this.comando1 = comando1;
		this.comando2 = comando2;
	}

	/**
	 * Executa os comandos sequencialmente.
	 * 
	 * @param ambiente
	 *            o ambiente de execução.
	 * 
	 * @return o ambiente depois de modificado pela execução dos comandos.
	 * @throws ErroTipoEntradaException 
	 * 
	 */
	public AmbienteExecucaoImperativa executar(
			AmbienteExecucaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException, ErroTipoEntradaException {
		return comando2.executar(comando1.executar(ambiente));
	}

	/**
	 * Realiza a verificacao de tipos dos comandos
	 * 
	 * @param ambiente
	 *            o ambiente de compilação.
	 * @return <code>true</code> se os comandos são bem tipados;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		return comando1.checaTipo(ambiente) && comando2.checaTipo(ambiente);
	}
}
