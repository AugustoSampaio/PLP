package plp.debug.imperativa2;

import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative2.declaration.DeclaracaoProcedimento;
import li2.plp.imperative2.declaration.DefProcedimento;
import li2.plp.imperative2.declaration.ListaDeclaracaoParametro;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link DeclaracaoProcedimento} publicando o escopo do procedimento
 * com a faixa exata de código-fonte capturada pelo parser do WebDebug. O nome
 * do procedimento só é conhecido aqui, então ele é anexado ao
 * {@link InfoEscopo} na verificação de tipos.
 *
 * Construída apenas pela gramática do WebDebug; Imperativa2 permanece
 * inalterada e não conhece esta classe.
 */
public class DeclaracaoProcedimentoDebug extends DeclaracaoProcedimento {

	private final Id id;
	private final DefProcedimento defProcedimento;
	private final InfoEscopo infoEscopo;

	public DeclaracaoProcedimentoDebug(Id id, DefProcedimento defProcedimento, InfoEscopo infoEscopo) {
		super(id, defProcedimento);
		this.id = id;
		this.defProcedimento = defProcedimento;
		this.infoEscopo = infoEscopo;
	}

	private static void registra(AmbienteCompilacaoImperativa ambiente, InfoEscopo info) {
		if (ambiente instanceof ScopeAware) {
			((ScopeAware) ambiente).registraEscopo(info);
		}
	}

	/** Faixa do corpo do procedimento, usada como alternativa à faixa completa. */
	private InfoEscopo infoEscopoEfetivo() {
		if (infoEscopo != null) {
			return infoEscopo;
		}
		if (defProcedimento instanceof DefProcedimentoDebug) {
			return ((DefProcedimentoDebug) defProcedimento).getInfoEscopo();
		}
		return null;
	}

	@Override
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		boolean resposta;

		ambiente.map(id, defProcedimento.getTipo());

		ListaDeclaracaoParametro parametrosFormais = defProcedimento
				.getParametrosFormais();
		if (parametrosFormais.checaTipo(ambiente)) {
			ambiente.incrementa();
			// O nome do procedimento já está disponível aqui, então ele pode
			// ser publicado junto com o frame do debugger.
			InfoEscopo info = infoEscopoEfetivo();
			registra(ambiente, info == null ? null : info.comNome(id.toString()));
			ambiente = parametrosFormais.elabora(ambiente);
			resposta = defProcedimento.getComando().checaTipo(ambiente);
			ambiente.restaura();
		} else {
			resposta = false;
		}
		return resposta;
	}
}
