package loo1.plp.orientadaObjetos1.memoria;

import loo1.plp.expressions2.expression.Id;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.util.Tipo;

/**
 * Interface do ambiente de compilação OO1.
 */
public interface AmbienteCompilacaoOO1 extends AmbienteOO1<Tipo> {

    default List<Map<String,Object>> getPilhaSnapshot() {
        return Collections.emptyList();
    }

    default void registraEscopo(InfoEscopo info) {
    }

    public Tipo getTipo(Id idArg) throws VariavelNaoDeclaradaException;

    public void mapParametrosProcedimento(Id idArg, ListaDeclaracaoParametro parametrosId) throws ProcedimentoJaDeclaradoException;

    public ListaDeclaracaoParametro getParametrosProcedimento(Id idArg) throws ProcedimentoNaoDeclaradoException;

    public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException;
}
