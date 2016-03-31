package plp.orientadaObjetos1.declaracao.procedimento;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.imperative1.util.Lista;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
/**
 * Um conjunto de declarações de parâmetro.
 */
public class ListaDeclaracaoParametro extends Lista<DecParametro>{
    /**
     * Construtor.
     */
    public ListaDeclaracaoParametro(){
       
    }
    /**
     * Construtor
     * @param declaracao A declaração contida por esta tail.
     */
    public ListaDeclaracaoParametro(DecParametro declaracao){
        super(declaracao, null);
    }
    /**
     * Construtor.
     * @param declaracao A declaração contida por esta tail.
     * @param listaDeclaracao A tail de declarações que segue declaração.
     */
    public ListaDeclaracaoParametro(DecParametro declaracao, ListaDeclaracaoParametro listaDeclaracao){
        super(declaracao,listaDeclaracao);
    }
 
    /**
     * Cria um mapeamento do identificador para esta tail de declarações
     * de parâmetro.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     * e valores.
     * @return o ambiente modificado pela declaração da classe.
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
     * Verifica se a declaração e a tail de declaração estão bem tipadas, ou seja, se a
     * expressão de inicialização está bem tipada.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos da declaração são válidos;
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
     * desta declaração no AmbienteCompilacao
     *
     * @param ambiente o ambiente que contem o mapeamento entre identificador
     *  e seu tipo.
     * @return o ambiente modificado pela declaração do parametro.
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
