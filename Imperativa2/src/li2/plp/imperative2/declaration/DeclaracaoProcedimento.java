package li2.plp.imperative2.declaration;

import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li2.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li2.plp.expressions2.memory.InfoEscopo;
import li2.plp.imperative1.declaration.Declaracao;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li2.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li2.plp.imperative1.memory.EntradaVaziaException;
import li2.plp.imperative2.memory.AmbienteExecucaoImperativa2;

/**
 * Declaração de um procedimento com parâmetros formais.
 */
public class DeclaracaoProcedimento extends Declaracao {

	private Id id;
	private DefProcedimento defProcedimento;
	private InfoEscopo infoEscopo;

	public DeclaracaoProcedimento(Id id, DefProcedimento defProcedimento) {
		super();
		this.id = id;
		this.defProcedimento = defProcedimento;
	}

	public DeclaracaoProcedimento(Id id, DefProcedimento defProcedimento, InfoEscopo infoEscopo) {
		this(id, defProcedimento);
		this.infoEscopo = infoEscopo;
	}

	@Override
	public AmbienteExecucaoImperativa elabora(
			AmbienteExecucaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		((AmbienteExecucaoImperativa2) ambiente).mapProcedimento(getId(),
				getDefProcedimento());
		return ambiente;
	}

	private Id getId() {
		return this.id;
	}

	@Override
	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws IdentificadorJaDeclaradoException,
			IdentificadorNaoDeclaradoException, EntradaVaziaException {
		boolean resposta;

		ambiente.map(id, defProcedimento.getTipo());

		ListaDeclaracaoParametro parametrosFormais = getDefProcedimento()
				.getParametrosFormais();
		if (parametrosFormais.checaTipo(ambiente)) {
			ambiente.incrementa();
			// O nome do procedimento já está disponível aqui, então ele pode ser
			// publicado junto com o frame do debugger.
			((li2.plp.expressions2.memory.Contexto<?>) ambiente).registraEscopo(
				infoEscopo != null ? infoEscopo : defProcedimento.getInfoEscopo(),
				getId().toString()
			);
			ambiente = parametrosFormais.elabora(ambiente);
			resposta = getDefProcedimento().getComando().checaTipo(ambiente);
			ambiente.restaura();
		} else {
			resposta = false;
		}
		return resposta;
	}

	private DefProcedimento getDefProcedimento() {
		return this.defProcedimento;
	}
}
