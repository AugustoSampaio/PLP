package plp.debug.imperativa2;

import li2.plp.imperative1.command.Comando;
import li2.plp.imperative2.declaration.DefProcedimento;
import li2.plp.imperative2.declaration.ListaDeclaracaoParametro;
import plp.debug.core.InfoEscopo;

/**
 * Estende {@link DefProcedimento} apenas para transportar a faixa exata de
 * código-fonte do corpo do procedimento (<code>{ ... }</code>), capturada
 * pelo parser do WebDebug.
 *
 * O escopo em si é publicado por {@link DeclaracaoProcedimentoDebug}, que é
 * quem conhece o nome do procedimento.
 */
public class DefProcedimentoDebug extends DefProcedimento {

	private final InfoEscopo infoEscopo;

	public DefProcedimentoDebug(ListaDeclaracaoParametro parametrosFormais, Comando comando, InfoEscopo infoEscopo) {
		super(parametrosFormais, comando);
		this.infoEscopo = infoEscopo;
	}

	public InfoEscopo getInfoEscopo() {
		return infoEscopo;
	}
}
