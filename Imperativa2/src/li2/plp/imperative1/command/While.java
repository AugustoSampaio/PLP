package li2.plp.imperative1.command;

import li2.plp.expressions2.expression.Expressao;
import li2.plp.expressions2.expression.ValorBooleano;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative1.memory.ErroTipoEntradaException;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;

public class While implements Comando {

	private Expressao expressao;

	private Comando comando;

	public While(Expressao expressao, Comando comando) {
		this.expressao = expressao;
		this.comando = comando;
	}

	/**
	 * Implementa o comando <code>while</code>.
	 * 
	 * @param ambiente
	 *            o ambiente de execu��o.
	 * 
	 * @return o ambiente depois de modificado pela execu��o do comando
	 *         <code>while</code>.
	 * @throws ErroTipoEntradaException 
	 * 
	 */
	public AmbienteExecucaoImperativa executar(
			AmbienteExecucaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException, ErroTipoEntradaException {
		while (((ValorBooleano) expressao.avaliar(ambiente)).valor()) {
			ambiente = comando.executar(ambiente);
		}
		return ambiente;
	}

	/**
	 * Realiza a verificacao de tipos da express�o e dos comandos do comando
	 * <code>while</code>
	 * 
	 * @param ambiente
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se os comando s�o bem tipados;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		return expressao.checaTipo(ambiente)
				&& expressao.getTipo(ambiente).eBooleano()
				&& comando.checaTipo(ambiente);
	}

}
