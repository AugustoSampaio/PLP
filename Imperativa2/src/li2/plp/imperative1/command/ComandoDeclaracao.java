package li2.plp.imperative1.command;

import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.expressions2.memory.InfoEscopo;
import li2.plp.imperative1.declaration.Declaracao;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative1.memory.ErroTipoEntradaException;

/**
 * Comando que introduz um bloco de declarações seguido de um comando.
 */
public class ComandoDeclaracao implements Comando {

	private Declaracao declaracao;
	private Comando comando;
	private InfoEscopo infoEscopo;

	public ComandoDeclaracao(Declaracao declaracao, Comando comando) {
		this.declaracao = declaracao;
		this.comando = comando;
	}

	public ComandoDeclaracao(Declaracao declaracao, Comando comando, InfoEscopo infoEscopo) {
		this(declaracao, comando);
		this.infoEscopo = infoEscopo;
	}

	/**
	 * Declara a(s) variável(is) e executa o comando.
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
	 * Verifica se o tipo do comando esta correto.
	 */
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		boolean resposta;
		ambiente.incrementa();
		ambiente.registraEscopo(infoEscopo);
		resposta = declaracao.checaTipo(ambiente)
				&& comando.checaTipo(ambiente);
		ambiente.restaura();
		return resposta;
	}
}
