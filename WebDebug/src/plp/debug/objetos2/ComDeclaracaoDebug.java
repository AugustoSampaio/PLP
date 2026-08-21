package plp.debug.objetos2;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.comando.ComDeclaracao;
import loo2.plp.orientadaObjetos1.comando.Comando;
import loo2.plp.orientadaObjetos1.declaracao.Declaracao;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.debug.core.InfoEscopo;
import plp.debug.core.ScopeAware;

/**
 * Estende {@link ComDeclaracao} de Objetos2 publicando o escopo do bloco
 * "{ ... }" com a faixa exata de código-fonte capturada pela gramática do
 * WebDebug.
 *
 * É uma classe distinta de {@code plp.debug.objetos1.ComDeclaracaoDebug}:
 * apesar do nome, {@code loo2.plp.orientadaObjetos1.comando.ComDeclaracao} e
 * {@code loo1.plp.orientadaObjetos1.comando.ComDeclaracao} são tipos
 * diferentes, em projetos diferentes, sem nenhuma relação de herança.
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
