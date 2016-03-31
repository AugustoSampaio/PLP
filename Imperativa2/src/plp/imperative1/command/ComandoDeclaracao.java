package plp.imperative1.command;

import plp.expressions2.memory.IdentificadorJaDeclaradoException;
import plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import plp.imperative1.declaration.Declaracao;
import plp.imperative1.memory.AmbienteCompilacaoImperativa;
import plp.imperative1.memory.AmbienteExecucaoImperativa;
import plp.imperative1.memory.EntradaVaziaException;
import plp.imperative1.memory.ErroTipoEntradaException;

public class ComandoDeclaracao implements Comando {

	private Declaracao declaracao;

	private Comando comando;

	public ComandoDeclaracao(Declaracao declaracao, Comando comando) {
		this.declaracao = declaracao;
		this.comando = comando;
	}

	/**
	 * Declara a(s) variável(is) e executa o comando.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            valores.
	 * 
	 * @return o ambiente modificado pela execução da declaração e do comando.
	 * @throws ErroTipoEntradaException 
	 * 
	 */
	public AmbienteExecucaoImperativa executar(
			AmbienteExecucaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException, ErroTipoEntradaException {
		ambiente.incrementa();
		ambiente = comando.executar(declaracao.elabora(ambiente));
		ambiente.restaura();
		return ambiente;
	}

	/**
	 * Verifica se o tipo do comando esta correto, levando em conta que o tipo
	 * de uma variavel é o tipo do valor da sua primeira atribuicao.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		boolean resposta;
		ambiente.incrementa();
		resposta = declaracao.checaTipo(ambiente)
				&& comando.checaTipo(ambiente);
		ambiente.restaura();
		return resposta;
	}

}
