package plp.orientadaObjetos1.memoria;

import java.util.HashMap;
import java.util.Stack;

import plp.expressions2.expression.Id;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import plp.orientadaObjetos1.expressao.valor.Valor;
import plp.orientadaObjetos1.expressao.valor.ValorRef;
import plp.orientadaObjetos1.memoria.colecao.ListaValor;
import plp.orientadaObjetos1.util.Tipo;

/**
 * Classe que representa um ambiente de execução, contendo om mapeamento
 * entre identificadores e valores.
 */
public interface AmbienteExecucaoOO1 extends AmbienteOO1<Valor> {
    /**
     * Obtém a pilha de valores associados a identificadores
     * @return a pilha de valores associados a identificadores.
     */
	public Stack<HashMap<Id, Valor>> getPilha();

    /**
     * Retorna a pilha com as definiçoes das classes.
     * @return a pilha com as definiçoes das classes.
     */
    public HashMap<Id, DefClasse> getMapDefClasse();

    /**
     * Obtém o mapeamento com os objetos e seus valores.
     * @return o mapeamento com os objetos e seus valores.
     */
    public HashMap<ValorRef, Objeto> getMapObjetos();

    /**
     * Mapeia um valor referência a um objeto.
     * @param valorRef Valor referência.
     * @param objeto Objeto.
     * @throws ObjetoJaDeclaradoException Quando esse objeto já foi declarado.
     */
    public void mapObjeto(ValorRef valorRef, Objeto objeto) throws ObjetoJaDeclaradoException;

    /**
     * Altera o valor associado a um identificador.
     * @param idArg Identificador.
     * @param valorId O valor a ser associado ao identificador.
     * @throws VariavelNaoDeclaradaException Quando a variável não foi
     * declarada.
     */
    public void changeValor(Id idArg, Valor valorId) throws VariavelNaoDeclaradaException;

    /**
     * Obtém o objeto associado a um dado valor referencia.
     * @param valorRef Valor referência
     * @return o objeto associado a um dado valor referencia.
     * @throws ObjetoNaoDeclaradoException Quando o objeto não foi declarado.
     */
    public Objeto getObjeto(ValorRef valorRef) throws ObjetoNaoDeclaradoException;

    /**
     * Obtém a próxima referência de acordo com o contexto atual de execução.
     * @return a próxima referência de acordo com o contexto atual de execução.
     */
    public ValorRef getProxRef();

    /**
     * Obtém o valor referencia atual.
     * @return o  valor referencia atual.
     */
    public ValorRef getRef();

    /**
     * Lê da entrada padrão e associa o conteúdo a um determinado identificador.
     * @param tipoIdLido Tipo do identificador ao qual será associado o valor
     * lido.
     * @return o valor lido.
     * @throws EntradaInvalidaException Quando a entrada fornecida não pode
     * ser atribuída ao tipo do identificador.
     */
    public Valor read(Tipo tipoIdLido) throws EntradaInvalidaException;

    /**
     * Escreve um valor 'v' na saída.
     * @param v O valor a ser escrito.
     * @return o ambiente de execução, que representa o estado atual.
     */
    public AmbienteExecucaoOO1 write(Valor v);

    /**
     * Obtém a entrada.
     * @return a entrada.
     */
    public ListaValor getEntrada();

    /**
     * Obtém a saída.
     * @return a saída.
     */
    public ListaValor getSaida();

    /**
     * Obtém um novo contexto de execução com a mesma entrada, saída e pilha
     * de mapeamentos id/valor.
     * @return um novo contexto de execução com a mesma entrada, saída e pilha
     * de mapeamentos id/valor.
     */
    public ContextoExecucaoOO1 getContextoIdValor();
    
    /**
     * Obtém o valor associado a um determinado identificador.
     * @param idArg Identificador
     * @return o valor associado a um determinado identificador.
     * @throws VariavelNaoDeclaradaException Quando a variável não foi
     * declarada.
     */
    public Valor getValor(Id idArg) throws VariavelNaoDeclaradaException;
}
