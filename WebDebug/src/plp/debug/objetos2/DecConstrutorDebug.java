package plp.debug.objetos2;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.comando.Comando;
import loo2.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos2.declaracao.ConstrutorNaoDeclaradoException;
import loo2.plp.orientadaObjetos2.declaracao.DecConstrutor;
import loo2.plp.orientadaObjetos2.memoria.AmbienteCompilacaoOO2;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link DecConstrutor} publicando o escopo do construtor com a faixa
 * exata de código-fonte (nome do construtor ... "}" do corpo) capturada pela
 * gramática do WebDebug.
 *
 * {@code DecConstrutor.checaTipo} delega para
 * {@code DecProcedimentoSimples.checaTipo}, que é onde o {@code incrementa()}
 * realmente acontece. Como não é possível interceptar aquele ponto por herança
 * (DecConstrutor é irmã de {@link DecProcedimentoSimplesDebug}), o corpo de
 * {@code DecProcedimentoSimples.checaTipo} é reproduzido aqui, com o registro
 * do escopo imediatamente após o {@code incrementa()}.
 *
 * Assim os parâmetros do construtor — que fazem shadowing dos atributos de
 * mesmo nome — ficam num frame próprio, separado do frame da classe.
 *
 * O campo nomeClasse é privado em DecConstrutor, então esta subclasse mantém
 * sua própria cópia; nome/parametrosFormais/comando são protected em
 * DecProcedimentoSimples e são herdados.
 */
public class DecConstrutorDebug extends DecConstrutor {

	private final Id nomeClasse;
	private final InfoEscopo infoEscopo;

	public DecConstrutorDebug(Id nomeClasse, Id nome, ListaDeclaracaoParametro parametrosFormais, Comando comando,
			InfoEscopo infoEscopo) {
		super(nomeClasse, nome, parametrosFormais, comando);
		this.nomeClasse = nomeClasse;
		this.infoEscopo = infoEscopo;
	}

	private static void registra(AmbienteCompilacaoOO1 ambiente, InfoEscopo info) {
		if (ambiente instanceof ScopeAware) {
			((ScopeAware) ambiente).registraEscopo(info);
		}
	}

	@Override
	public boolean checaTipo(AmbienteCompilacaoOO2 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ProcedimentoJaDeclaradoException, ProcedimentoNaoDeclaradoException,
			ClasseNaoDeclaradaException, ClasseJaDeclaradaException, ConstrutorNaoDeclaradoException {
		boolean resposta = (this.nomeClasse.toString().equals(this.nome.toString()));

		if (resposta) {
			return checaTipoProcedimento(ambiente);
		} else {
			throw new ConstrutorNaoDeclaradoException(nomeClasse);
		}
	}

	/**
	 * Cópia de {@code DecProcedimentoSimples.checaTipo} com o registro do
	 * escopo logo após o incrementa().
	 */
	private boolean checaTipoProcedimento(AmbienteCompilacaoOO1 ambienteArg)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ProcedimentoJaDeclaradoException, ProcedimentoNaoDeclaradoException,
			ClasseNaoDeclaradaException, ClasseJaDeclaradaException {
		boolean resposta;
		AmbienteCompilacaoOO1 ambiente = ambienteArg;
		if (parametrosFormais.checaTipo(ambiente)) {
			ambiente.mapParametrosProcedimento(nome, parametrosFormais);
			ambiente.incrementa();
			// O frame do debugger carrega o nome do construtor.
			registra(ambiente, infoEscopo == null ? null : infoEscopo.comNome(nome.toString()));
			ambiente = parametrosFormais.declaraParametro(ambiente);
			resposta = comando.checaTipo(ambiente);
			ambiente.restaura();
		} else {
			resposta = false;
		}
		return resposta;
	}
}
