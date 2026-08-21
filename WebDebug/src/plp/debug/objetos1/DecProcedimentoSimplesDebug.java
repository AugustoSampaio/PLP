package plp.debug.objetos1;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.comando.Comando;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.DecProcedimentoSimples;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link DecProcedimentoSimples} publicando o escopo do procedimento
 * com a faixa exata de código-fonte ("proc" ... "}") capturada pela gramática
 * do WebDebug. Funciona também para procedimentos sem parâmetros, que não
 * criam nenhum binding.
 */
public class DecProcedimentoSimplesDebug extends DecProcedimentoSimples {

	private final InfoEscopo infoEscopo;

	public DecProcedimentoSimplesDebug(Id nome, ListaDeclaracaoParametro parametrosFormais, Comando comando,
			InfoEscopo infoEscopo) {
		super(nome, parametrosFormais, comando);
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
			ProcedimentoJaDeclaradoException, ProcedimentoNaoDeclaradoException,
			ClasseNaoDeclaradaException, ClasseJaDeclaradaException {
		boolean resposta;
		if (parametrosFormais.checaTipo(ambiente)) {
			ambiente.mapParametrosProcedimento(nome, parametrosFormais);
			ambiente.incrementa();
			// O frame do debugger carrega o nome do procedimento.
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
