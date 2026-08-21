package plp.debug.imperativa1;

import li1.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li1.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li1.plp.imperative1.command.Comando;
import li1.plp.imperative1.command.ComandoDeclaracao;
import li1.plp.imperative1.declaration.Declaracao;
import li1.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li1.plp.imperative1.memory.EntradaVaziaException;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link ComandoDeclaracao} publicando o escopo do bloco
 * <code>{ declaracoes; comando }</code> com a faixa exata de código-fonte
 * capturada pelo parser do WebDebug.
 *
 * Construída apenas pela gramática do WebDebug; Imperativa1 permanece
 * inalterada e não conhece esta classe.
 */
public class ComandoDeclaracaoDebug extends ComandoDeclaracao {

	private final Declaracao declaracao;
	private final Comando comando;
	private final InfoEscopo infoEscopo;

	public ComandoDeclaracaoDebug(Declaracao declaracao, Comando comando, InfoEscopo infoEscopo) {
		super(declaracao, comando);
		this.declaracao = declaracao;
		this.comando = comando;
		this.infoEscopo = infoEscopo;
	}

	private static void registra(AmbienteCompilacaoImperativa ambiente, InfoEscopo info) {
		if (ambiente instanceof ScopeAware) {
			((ScopeAware) ambiente).registraEscopo(info);
		}
	}

	/**
	 * Verifica se o tipo do comando esta correto, levando em conta que o tipo
	 * de uma variavel e o tipo do valor da sua primeira atribuicao.
	 */
	@Override
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		boolean resposta;
		ambiente.incrementa();
		registra(ambiente, infoEscopo);
		resposta = declaracao.checaTipo(ambiente)
				&& comando.checaTipo(ambiente);
		ambiente.restaura();
		return resposta;
	}
}
