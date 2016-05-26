package loo2.plp.orientadaObjetos1.declaracao.procedimento;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.imperative1.util.Lista;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
/**
 * Um conjunto de declara��es de par�metro.
 */
public class ListaDeclaracaoParametro extends Lista<DecParametro>{
    /**
     * Construtor.
     */
    public ListaDeclaracaoParametro(){
       
    }
    /**
     * Construtor
     * @param declaracao A declara��o contida por esta tail.
     */
    public ListaDeclaracaoParametro(DecParametro declaracao){
        super(declaracao, null);
    }
    /**
     * Construtor.
     * @param declaracao A declara��o contida por esta tail.
     * @param listaDeclaracao A tail de declara��es que segue declara��o.
     */
    public ListaDeclaracaoParametro(DecParametro declaracao, ListaDeclaracaoParametro listaDeclaracao){
        super(declaracao,listaDeclaracao);
    }
 
    /**
     * Cria um mapeamento do identificador para esta tail de declara��es
     * de par�metro.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     * e valores.
     * @return o ambiente modificado pela declara��o da classe.
     */
    public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente) {
        AmbienteExecucaoOO1 resposta;
        if(getHead() != null) {
            if(getTail() != null) {
                resposta = ((ListaDeclaracaoParametro)getTail()).elabora(getHead().elabora(ambiente));
            }
            else {
                resposta = getHead().elabora(ambiente);
            }
        }
        else {
            resposta = ambiente;
        }
        return resposta;
    }
    /**
     * Verifica se a declara��o e a tail de declara��o est�o bem tipadas, ou seja, se a
     * express�o de inicializa��o est� bem tipada.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos da declara��o s�o v�lidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, ClasseNaoDeclaradaException {
        boolean resposta;
        if(getHead() != null) {
            if(getTail() != null) {
                resposta = getHead().checaTipo(ambiente) &&
				 			((ListaDeclaracaoParametro)getTail()).checaTipo(ambiente);
            }
            else {
                resposta = getHead().checaTipo(ambiente);
            }
        }
        else {
            resposta = true;
        }
        return resposta;
    }

    /**
     * Cria um mapeamento do identificador para o tipo do parametro
     * desta declara��o no AmbienteCompilacao
     *
     * @param ambiente o ambiente que contem o mapeamento entre identificador
     *  e seu tipo.
     * @return o ambiente modificado pela declara��o do parametro.
     */
    public AmbienteCompilacaoOO1 declaraParametro(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

        AmbienteCompilacaoOO1 resposta;
        if(getHead() != null) {
            if(getTail() != null) {
                resposta = ((ListaDeclaracaoParametro)getTail()).declaraParametro(getHead().declaraParametro(ambiente));
            }
            else {
                resposta = getHead().declaraParametro(ambiente);
            }
        }
        else {
            resposta = ambiente;
        }
        return resposta;
    }


}
