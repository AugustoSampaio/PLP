package plp.orientadaObjetos1.comando;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import plp.orientadaObjetos1.expressao.Expressao;
import plp.orientadaObjetos1.expressao.ListaExpressao;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos1.expressao.valor.ValorRef;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.memoria.ContextoExecucaoOO1;
import plp.orientadaObjetos1.memoria.DefClasse;
import plp.orientadaObjetos1.memoria.Objeto;
import plp.orientadaObjetos1.memoria.colecao.ListaValor;
import plp.orientadaObjetos1.util.Tipo;

/**
 * Classe que representa a chamada de um método.
 */
public class ChamadaMetodo implements Comando {
	/**
	 * A expressão que chama o método.
	 */
    protected Expressao expressao;
    /**
     * O identificador que representa o nome do método.
     */
    protected Id nomeMetodo;
    /**
     * Parâmetros passados para o método.
     */
    protected ListaExpressao parametrosReais;

    /**
     * Construtor.
     * @param expressao A expressao chamadora do método.
     * @param nomeMetodo O nome do método.
     * @param parametrosReais Os parâmetros passados para a execução do método.
     * @param
     */
    public ChamadaMetodo(Expressao expressao, Id nomeMetodo, ListaExpressao parametrosReais){
        this.expressao = expressao;
        this.nomeMetodo = nomeMetodo;
        this.parametrosReais = parametrosReais;
    }
	/**
	 * Executa uma chamada de método.
	 * @param ambiente O ambiente de execução, que guarda o mapeamento
	 * de identificadores a valores.
	 * @return o Ambiente de Execução atualizado.
	 */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
        ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
        ObjetoJaDeclaradoException, ObjetoNaoDeclaradoException,
        ClasseNaoDeclaradaException, ClasseJaDeclaradaException, EntradaInvalidaException{

        ValorRef vr = (ValorRef) expressao.avaliar(ambiente);  // recupera o id do objeto
        Objeto objeto =  ambiente.getObjeto(vr);               // recupera o objeto
        Id idClasse = objeto.getClasse();                      // recupera o tipo do objeto
        DefClasse defClasse = ambiente.getDefClasse((plp.expressions2.expression.Id)idClasse); // recupera a definição da classe
        Procedimento metodo = defClasse.getMetodo(nomeMetodo); // recupera o procedimento
        // cria um novo ambiente para a execucao, pois
        // não deve levar em conta as variáveis definidas na main
        AmbienteExecucaoOO1 aux = new ContextoExecucaoOO1(ambiente);
                                                               // é change pois no construtor do ambiente
        aux.changeValor(new Id("this"),vr);                    // invocado na linha anterior ja é feito
                                                               //  um mapeamento

        ListaValor valoresDosParametros = parametrosReais.avaliar(ambiente);
        new ChamadaProcedimento(metodo, parametrosReais, valoresDosParametros).executar(aux);
        return ambiente;
    }

    /**
    * Realiza a verificação de tipos desta chamada de método, onde
    * o tipo do método deve estar na definição da classe obtida
    * a partir de expressão.
    *
    * @param ambiente o ambiente que contem o mapeamento entre identificadores
    *  e tipos.
    * @return <code>true</code> se a chamada de método está bem tipada;
    *          <code>false</code> caso contrario.
    */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
    throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
         ClasseNaoDeclaradaException {
        boolean resposta;
        //Antes de incrementar o ambiente, verifico se o método
        //é válido para a definicao de classe obtida a partir de expressao.
        //Se não for válido, a exceção ProcedimentoNaoDeclaradoException será
        //lançada e checaTipo retornará false.
        Tipo tipoClasse = expressao.getTipo(ambiente);
        DefClasse defClasse = ambiente.getDefClasse(tipoClasse.getTipo());
        try{
            Procedimento metodo = defClasse.getMetodo(nomeMetodo);
            ambiente.incrementa();
            ambiente.map(new Id("this"),tipoClasse);
            resposta = new ChamadaProcedimento(metodo, parametrosReais).checaTipo(ambiente);
            ambiente.restaura();
        }
        catch(ProcedimentoNaoDeclaradoException e){
            resposta = false;
        }
        return resposta;
    }
}
