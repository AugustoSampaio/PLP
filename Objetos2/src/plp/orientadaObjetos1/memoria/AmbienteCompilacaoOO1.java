package plp.orientadaObjetos1.memoria;

import plp.expressions2.expression.Id;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.util.Tipo;
/**
 * Classe que representa o ambiente de compilação, contendo o mapeamento
 * entre identificadores e tipos.
 */
public interface AmbienteCompilacaoOO1 extends AmbienteOO1<Tipo> {

    /**
     * Obtém o tipo associado a um dado identificador
     * @param idArg Identificador
     * @return Tipo associado a um dado identificador
     * @throws VariavelNaoDeclaradaException quando id não foi declarado.
     */
    public Tipo getTipo(Id idArg) throws VariavelNaoDeclaradaException;
	/**
     * Mapeia um identificador representando um método aos seus parâmetros.
     * @param idArg identificador do método.
     * @param parametrosId Parâmetros do método
     * @throws ProcedimentoJaDeclaradoException quando o procedimento já foi
     * declarado.
     */
    public void mapParametrosProcedimento(Id idArg, ListaDeclaracaoParametro parametrosId) throws ProcedimentoJaDeclaradoException;

    /**
     * Obtém a tail de parâmetros associada a um identificador que representa
     * nome do método.
     * @param idArg Identificador que representa o nome do método.
     * @return Lista de parâmetros Lista de parâmetros associada a um identificador que representa
     * nome do método.
     * @throws ProcedimentoNaoDeclaradoException quando não foi declarado nenhum
     * método com esse id.
     */
    public ListaDeclaracaoParametro getParametrosProcedimento(Id idArg) throws ProcedimentoNaoDeclaradoException;

    /**
     * Obtém o tipo da entrada atual para este ambiente.
     * @return o tipo da entrada.
     * @throws VariavelNaoDeclaradaException quando a entrada atual é
     * uma variável não declarada.
     */
    public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException;

}
