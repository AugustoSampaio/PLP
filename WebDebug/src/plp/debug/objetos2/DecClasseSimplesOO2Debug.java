package plp.debug.objetos2;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.declaracao.procedimento.DecProcedimento;
import loo2.plp.orientadaObjetos1.declaracao.variavel.DecVariavel;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.util.TipoClasse;
import loo2.plp.orientadaObjetos2.declaracao.ConstrutorNaoDeclaradoException;
import loo2.plp.orientadaObjetos2.declaracao.DecConstrutor;
import loo2.plp.orientadaObjetos2.declaracao.classe.DecClasseSimplesOO2;
import loo2.plp.orientadaObjetos2.memoria.AmbienteCompilacaoOO2;
import loo2.plp.orientadaObjetos2.memoria.DefClasseOO2;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link DecClasseSimplesOO2} publicando o escopo da classe com a
 * faixa exata de código-fonte ("classe" ... "}") capturada pela gramática do
 * WebDebug.
 *
 * DecClasseSimplesOO2 guarda nomeSuperClasse/construtor em campos privados e
 * o auxiliar checaTipoVariaveisClasseMae também é privado, então esta subclasse
 * mantém suas próprias cópias.
 */
public class DecClasseSimplesOO2Debug extends DecClasseSimplesOO2 {

	private final Id nomeSuperClasse;
	private final DecConstrutor construtor;
	private final InfoEscopo infoEscopo;

	public DecClasseSimplesOO2Debug(Id nomeClasse, Id nomeSuperClasse, DecVariavel atributos,
			DecConstrutor construtor, DecProcedimento metodos, InfoEscopo infoEscopo) {
		super(nomeClasse, nomeSuperClasse, atributos, construtor, metodos);
		this.nomeSuperClasse = nomeSuperClasse;
		this.construtor = construtor;
		this.infoEscopo = infoEscopo;
	}

	private static void registra(AmbienteCompilacaoOO2 ambiente, InfoEscopo info) {
		if (ambiente instanceof ScopeAware) {
			((ScopeAware) ambiente).registraEscopo(info);
		}
	}

	@Override
	public boolean checaTipo(AmbienteCompilacaoOO2 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
			ProcedimentoNaoDeclaradoException,
			ProcedimentoJaDeclaradoException, ConstrutorNaoDeclaradoException {

		// Verifica se a super classe já foi declarada
		if (nomeSuperClasse != null) {
			ambiente.mapSuperClasse(nomeClasse, nomeSuperClasse);
		}

		// Adiciona a classe no mapeameento de classes
		ambiente.mapDefClasse(nomeClasse, new DefClasseOO2(nomeClasse, nomeSuperClasse, this.atributos, construtor, metodos));

		boolean resposta = false;
		ambiente.incrementa();
		// O frame do debugger carrega o nome da classe além do rótulo do escopo.
		registra(ambiente, infoEscopo == null ? null : infoEscopo.comNome(nomeClasse.toString()));

		DecVariavel atr = (DecVariavel) this.atributos;
		if (atr.checaTipo(ambiente)) {
			ambiente.map(new Id("this"), new TipoClasse(nomeClasse));

			if (nomeSuperClasse != null) {
				this.checaTipoVariaveisClasseMae(ambiente, this.nomeSuperClasse);
			}
			resposta = metodos.checaTipo(ambiente);
		}

		// Verifica se construtor está declarado corretamente
		resposta = resposta && construtor.checaTipo(ambiente);

		ambiente.restaura();

		return resposta;
	}

	private void checaTipoVariaveisClasseMae(AmbienteCompilacaoOO2 ambiente, Id nomeSuperClasse)
			throws ClasseNaoDeclaradaException, VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ClasseJaDeclaradaException {
		if (nomeSuperClasse != null) {
			DefClasseOO2 defClasseMae = (DefClasseOO2) ambiente.getDefClasse(nomeSuperClasse);
			defClasseMae.getDecVariavel().checaTipo(ambiente);
			this.checaTipoVariaveisClasseMae(ambiente, defClasseMae.getNomeSuperClasse());
		}
	}
}
