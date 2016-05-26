package loo2.plp.orientadaObjetos1.memoria;

import java.util.HashMap;
import java.util.Stack;

import loo2.plp.expressions2.expression.Id;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import loo2.plp.orientadaObjetos1.expressao.valor.Valor;
import loo2.plp.orientadaObjetos1.expressao.valor.ValorRef;
import loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor;
import loo2.plp.orientadaObjetos1.util.Tipo;

/**
 * Classe que representa um ambiente de execu��o, contendo om mapeamento
 * entre identificadores e valores.
 */
public interface AmbienteExecucaoOO1 extends AmbienteOO1<Valor> {
    /**
     * Obt�m a pilha de valores associados a identificadores
     * @return a pilha de valores associados a identificadores.
     */
	public Stack<HashMap<Id, Valor>> getPilha();

    /**
     * Retorna a pilha com as defini�oes das classes.
     * @return a pilha com as defini�oes das classes.
     */
    public HashMap<Id, DefClasse> getMapDefClasse();

    /**
     * Obt�m o mapeamento com os objetos e seus valores.
     * @return o mapeamento com os objetos e seus valores.
     */
    public HashMap<ValorRef, Objeto> getMapObjetos();

    /**
     * Mapeia um valor refer�ncia a um objeto.
     * @param valorRef Valor refer�ncia.
     * @param objeto Objeto.
     * @throws ObjetoJaDeclaradoException Quando esse objeto j� foi declarado.
     */
    public void mapObjeto(ValorRef valorRef, Objeto objeto) throws ObjetoJaDeclaradoException;

    /**
     * Altera o valor associado a um identificador.
     * @param idArg Identificador.
     * @param valorId O valor a ser associado ao identificador.
     * @throws VariavelNaoDeclaradaException Quando a vari�vel n�o foi
     * declarada.
     */
    public void changeValor(Id idArg, Valor valorId) throws VariavelNaoDeclaradaException;

    /**
     * Obt�m o objeto associado a um dado valor referencia.
     * @param valorRef Valor refer�ncia
     * @return o objeto associado a um dado valor referencia.
     * @throws ObjetoNaoDeclaradoException Quando o objeto n�o foi declarado.
     */
    public Objeto getObjeto(ValorRef valorRef) throws ObjetoNaoDeclaradoException;

    /**
     * Obt�m a pr�xima refer�ncia de acordo com o contexto atual de execu��o.
     * @return a pr�xima refer�ncia de acordo com o contexto atual de execu��o.
     */
    public ValorRef getProxRef();

    /**
     * Obt�m o valor referencia atual.
     * @return o  valor referencia atual.
     */
    public ValorRef getRef();

    /**
     * L� da entrada padr�o e associa o conte�do a um determinado identificador.
     * @param tipoIdLido Tipo do identificador ao qual ser� associado o valor
     * lido.
     * @return o valor lido.
     * @throws EntradaInvalidaException Quando a entrada fornecida n�o pode
     * ser atribu�da ao tipo do identificador.
     */
    public Valor read(Tipo tipoIdLido) throws EntradaInvalidaException;

    /**
     * Escreve um valor 'v' na sa�da.
     * @param v O valor a ser escrito.
     * @return o ambiente de execu��o, que representa o estado atual.
     */
    public AmbienteExecucaoOO1 write(Valor v);

    /**
     * Obt�m a entrada.
     * @return a entrada.
     */
    public ListaValor getEntrada();

    /**
     * Obt�m a sa�da.
     * @return a sa�da.
     */
    public ListaValor getSaida();

    /**
     * Obt�m um novo contexto de execu��o com a mesma entrada, sa�da e pilha
     * de mapeamentos id/valor.
     * @return um novo contexto de execu��o com a mesma entrada, sa�da e pilha
     * de mapeamentos id/valor.
     */
    public ContextoExecucaoOO1 getContextoIdValor();
    
    /**
     * Obt�m o valor associado a um determinado identificador.
     * @param idArg Identificador
     * @return o valor associado a um determinado identificador.
     * @throws VariavelNaoDeclaradaException Quando a vari�vel n�o foi
     * declarada.
     */
    public Valor getValor(Id idArg) throws VariavelNaoDeclaradaException;
}
