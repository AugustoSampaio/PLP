package plp.debug.objetos1;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.declaracao.classe.DecClasseSimples;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.DecProcedimento;
import loo1.plp.orientadaObjetos1.declaracao.variavel.DecVariavel;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.DefClasse;
import loo1.plp.orientadaObjetos1.util.TipoClasse;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link DecClasseSimples} publicando o escopo da classe com a faixa
 * exata de código-fonte ("classe" ... "}") capturada pela gramática do
 * WebDebug. Os campos nomeClasse/atributos/metodos são protected na classe da
 * linguagem, então são herdados.
 */
public class DecClasseSimplesDebug extends DecClasseSimples {

	private final InfoEscopo infoEscopo;

	public DecClasseSimplesDebug(Id nomeClasse, DecVariavel atributos, DecProcedimento metodos,
			InfoEscopo infoEscopo) {
		super(nomeClasse, atributos, metodos);
		this.infoEscopo = infoEscopo;
	}

	private static void registra(AmbienteCompilacaoOO1 ambiente, InfoEscopo info) {
		if (ambiente instanceof ScopeAware) {
			((ScopeAware) ambiente).registraEscopo(info);
		}
	}

	@Override
	public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
			ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException {

		ambiente.mapDefClasse(nomeClasse, new DefClasse(nomeClasse, atributos, metodos));
		boolean resposta = false;
		ambiente.incrementa();
		// O frame do debugger carrega o nome da classe além do rótulo do escopo.
		registra(ambiente, infoEscopo == null ? null : infoEscopo.comNome(nomeClasse.toString()));
		if (atributos.checaTipo(ambiente)) {
			ambiente.map(new Id("this"), new TipoClasse(nomeClasse));
			resposta = metodos.checaTipo(ambiente);
		}
		ambiente.restaura();
		return resposta;
	}
}
