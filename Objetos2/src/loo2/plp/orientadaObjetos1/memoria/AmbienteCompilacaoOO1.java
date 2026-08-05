package loo2.plp.orientadaObjetos1.memoria;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import loo2.plp.expressions2.expression.Id;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.util.Tipo;

/**
 * Interface do ambiente de compilação OO1/OO2.
 */
public interface AmbienteCompilacaoOO1 extends AmbienteOO1<Tipo> {

    public Tipo getTipo(Id idArg) throws VariavelNaoDeclaradaException;

	default void registraEscopo(InfoEscopo info) {
	}

    public void mapParametrosProcedimento(Id idArg, ListaDeclaracaoParametro parametrosId) throws ProcedimentoJaDeclaradoException;

    public ListaDeclaracaoParametro getParametrosProcedimento(Id idArg) throws ProcedimentoNaoDeclaradoException;

    public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException;

    default List<Map<String, Object>> getPilhaSnapshot() {
        return Collections.emptyList();
    }
}
