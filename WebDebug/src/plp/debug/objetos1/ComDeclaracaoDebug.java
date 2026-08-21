package plp.debug.objetos1;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.comando.ComDeclaracao;
import loo1.plp.orientadaObjetos1.comando.Comando;
import loo1.plp.orientadaObjetos1.declaracao.Declaracao;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link ComDeclaracao} publicando o escopo do bloco "{ ... }" com a
 * faixa exata de código-fonte capturada pela gramática do WebDebug.
 *
 * Construída apenas pela gramática do WebDebug; Objetos1 permanece inalterada
 * e não conhece esta classe.
 *
 * ComDeclaracao guarda declaracao/comando em campos privados, então esta
 * subclasse mantém suas próprias cópias.
 */
public class ComDeclaracaoDebug extends ComDeclaracao {

	private final Declaracao declaracao;
	private final Comando comando;
	private final InfoEscopo infoEscopo;

	public ComDeclaracaoDebug(Declaracao declaracao, Comando comando, InfoEscopo infoEscopo) {
		super(declaracao, comando);
		this.declaracao = declaracao;
		this.comando = comando;
		this.infoEscopo = infoEscopo;
	}

	private static void registra(AmbienteCompilacaoOO1 ambiente, InfoEscopo info) {
		if (ambiente instanceof ScopeAware) {
			((ScopeAware) ambiente).registraEscopo(info);
		}
	}

	@Override
	public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
			ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
			ClasseNaoDeclaradaException, ClasseJaDeclaradaException {
		boolean resposta;
		ambiente.incrementa();
		registra(ambiente, infoEscopo);
		resposta = declaracao.checaTipo(ambiente) && comando.checaTipo(ambiente);
		ambiente.restaura();
		return resposta;
	}
}
