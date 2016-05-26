package li1.plp.imperative1.command;

import li1.plp.expressions2.expression.Expressao;
import li1.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li1.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li1.plp.expressions2.memory.VariavelJaDeclaradaException;

public class Write implements IO {

	private Expressao expressao;

	public Write(Expressao expressao) {
		this.expressao = expressao;
	}

	/**
	 * Escreve na saida padr�o.
	 * 
	 * @param ambiente
	 *            o ambiente de execu��o.
	 * 
	 * @return o ambiente depois de modificado pela execu��o do comando
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
	 * Realiza a verificacao de tipos da express�o a ser escrita na pelo comando
	 * <code>write</code>
	 * 
	 * @param ambiente
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se a express�o a ser escrita est� bem tipada;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		return expressao.checaTipo(ambiente);
	}

}
