package loo1.plp.orientadaObjetos1.memoria;

import loo1.plp.expressions2.expression.Id;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.util.Tipo;
/**
 * Classe que representa o ambiente de compila��o, contendo o mapeamento
 * entre identificadores e tipos.
 */
public interface AmbienteCompilacaoOO1 extends AmbienteOO1<Tipo> {

    /**
     * Obt�m o tipo associado a um dado identificador
     * @param idArg Identificador
     * @return Tipo associado a um dado identificador
     * @throws VariavelNaoDeclaradaException quando id n�o foi declarado.
     */
    public Tipo getTipo(Id idArg) throws VariavelNaoDeclaradaException;
	/**
     * Mapeia um identificador representando um m�todo aos seus par�metros.
     * @param idArg identificador do m�todo.
     * @param parametrosId Par�metros do m�todo
     * @throws ProcedimentoJaDeclaradoException quando o procedimento j� foi
     * declarado.
     */
    public void mapParametrosProcedimento(Id idArg, ListaDeclaracaoParametro parametrosId) throws ProcedimentoJaDeclaradoException;

    /**
     * Obt�m a tail de par�metros associada a um identificador que representa
     * nome do m�todo.
     * @param idArg Identificador que representa o nome do m�todo.
     * @return Lista de par�metros Lista de par�metros associada a um identificador que representa
     * nome do m�todo.
     * @throws ProcedimentoNaoDeclaradoException quando n�o foi declarado nenhum
     * m�todo com esse id.
     */
    public ListaDeclaracaoParametro getParametrosProcedimento(Id idArg) throws ProcedimentoNaoDeclaradoException;

    /**
     * Obt�m o tipo da entrada atual para este ambiente.
     * @return o tipo da entrada.
     * @throws VariavelNaoDeclaradaException quando a entrada atual �
     * uma vari�vel n�o declarada.
     */
    public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException;

}
