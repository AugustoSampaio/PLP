package plp.imperative1.command;

import plp.expressions2.expression.Expressao;
import plp.imperative1.memory.AmbienteExecucaoImperativa;
import plp.imperative1.memory.AmbienteCompilacaoImperativa;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.expressions2.memory.VariavelJaDeclaradaException;

public class Write implements IO {

	private Expressao expressao;

	public Write(Expressao expressao) {
		this.expressao = expressao;
	}

	/**
	 * Escreve na saida padrão.
	 * 
	 * @param ambiente
	 *            o ambiente de execução.
	 * 
	 * @return o ambiente depois de modificado pela execução do comando
	 *         <code>write</code>.
	 * 
	 */
	public AmbienteExecucaoImperativa executar(
			AmbienteExecucaoImperativa ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		ambiente.write(expressao.avaliar(ambiente));
		return ambiente;
	}

	/**
	 * Realiza a verificacao de tipos da expressão a ser escrita na pelo comando
	 * <code>write</code>
	 * 
	 * @param ambiente
	 *            o ambiente de compilação.
	 * @return <code>true</code> se a expressão a ser escrita está bem tipada;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		return expressao.checaTipo(ambiente);
	}

}
