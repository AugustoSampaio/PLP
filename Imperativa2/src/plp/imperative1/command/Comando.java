package plp.imperative1.command;

/**
 A execucao de um comando ocorre em um determinado ambiente. O
 resultado de tal execucao é a modificação deste ambiente, i.e., comandos
 tem efeito colateral.
 */

import plp.imperative1.memory.AmbienteCompilacaoImperativa;
import plp.imperative1.memory.AmbienteExecucaoImperativa;
import plp.imperative1.memory.EntradaVaziaException;
import plp.imperative1.memory.ErroTipoEntradaException;
import plp.expressions2.memory.IdentificadorJaDeclaradoException;
import plp.expressions2.memory.IdentificadorNaoDeclaradoException;

public interface Comando {

	/**
	 * Executa este comando.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            valores.
	 * 
	 * @return o ambiente modificado pela execução do comando.
	 * @throws ErroTipoEntradaException 
	 */
	AmbienteExecucaoImperativa executar(AmbienteExecucaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException, ErroTipoEntradaException;

	/**
	 * Realiza a verificacao de tipos deste comando.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            tipos.
	 * 
	 * @return <code>true</code> se os comando são bem tipados;
	 *         <code>false</code> caso contrario.
	 */
	boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException;

}
