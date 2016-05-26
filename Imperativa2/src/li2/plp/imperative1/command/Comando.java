package li2.plp.imperative1.command;

/**
 A execucao de um comando ocorre em um determinado ambiente. O
 resultado de tal execucao � a modifica��o deste ambiente, i.e., comandos
 tem efeito colateral.
 */

import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative1.memory.ErroTipoEntradaException;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;

public interface Comando {

	/**
	 * Executa este comando.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            valores.
	 * 
	 * @return o ambiente modificado pela execu��o do comando.
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
	 * @return <code>true</code> se os comando s�o bem tipados;
	 *         <code>false</code> caso contrario.
	 */
	boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException;

}
