package loo2.plp.orientadaObjetos1.comando;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import loo2.plp.orientadaObjetos1.expressao.Expressao;
import loo2.plp.orientadaObjetos1.expressao.ListaExpressao;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.expressao.valor.ValorRef;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.memoria.ContextoExecucaoOO1;
import loo2.plp.orientadaObjetos1.memoria.DefClasse;
import loo2.plp.orientadaObjetos1.memoria.Objeto;
import loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor;
import loo2.plp.orientadaObjetos1.util.Tipo;

/**
 * Classe que representa a chamada de um m�todo.
 */
public class ChamadaMetodo implements Comando {
	/**
	 * A express�o que chama o m�todo.
	 */
    protected Expressao expressao;
    /**
     * O identificador que representa o nome do m�todo.
     */
    protected Id nomeMetodo;
    /**
     * Par�metros passados para o m�todo.
     */
    protected ListaExpressao parametrosReais;

    /**
     * Construtor.
     * @param expressao A expressao chamadora do m�todo.
     * @param nomeMetodo O nome do m�todo.
     * @param parametrosReais Os par�metros passados para a execu��o do m�todo.
     * @param
     */
    public ChamadaMetodo(Expressao expressao, Id nomeMetodo, ListaExpressao parametrosReais){
        this.expressao = expressao;
        this.nomeMetodo = nomeMetodo;
        this.parametrosReais = parametrosReais;
    }
	/**
	 * Executa uma chamada de m�todo.
	 * @param ambiente O ambiente de execu��o, que guarda o mapeamento
	 * de identificadores a valores.
	 * @return o Ambiente de Execu��o atualizado.
	 */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
        ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
        ObjetoJaDeclaradoException, ObjetoNaoDeclaradoException,
        ClasseNaoDeclaradaException, ClasseJaDeclaradaException, EntradaInvalidaException{

        ValorRef vr = (ValorRef) expressao.avaliar(ambiente);  // recupera o id do objeto
        Objeto objeto =  ambiente.getObjeto(vr);               // recupera o objeto
        Id idClasse = objeto.getClasse();                      // recupera o tipo do objeto
        DefClasse defClasse = ambiente.getDefClasse((loo2.plp.expressions2.expression.Id)idClasse); // recupera a defini��o da classe
        Procedimento metodo = defClasse.getMetodo(nomeMetodo); // recupera o procedimento
        // cria um novo ambiente para a execucao, pois
        // n�o deve levar em conta as vari�veis definidas na main
        AmbienteExecucaoOO1 aux = new ContextoExecucaoOO1(ambiente);
                                                               // � change pois no construtor do ambiente
        aux.changeValor(new Id("this"),vr);                    // invocado na linha anterior ja � feito
                                                               //  um mapeamento

        ListaValor valoresDosParametros = parametrosReais.avaliar(ambiente);
        new ChamadaProcedimento(metodo, parametrosReais, valoresDosParametros).executar(aux);
        return ambiente;
    }

    /**
    * Realiza a verifica��o de tipos desta chamada de m�todo, onde
    * o tipo do m�todo deve estar na defini��o da classe obtida
    * a partir de express�o.
    *
    * @param ambiente o ambiente que contem o mapeamento entre identificadores
    *  e tipos.
    * @return <code>true</code> se a chamada de m�todo est� bem tipada;
    *          <code>false</code> caso contrario.
    */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
    throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
         ClasseNaoDeclaradaException {
        boolean resposta;
        //Antes de incrementar o ambiente, verifico se o m�todo
        //� v�lido para a definicao de classe obtida a partir de expressao.
        //Se n�o for v�lido, a exce��o ProcedimentoNaoDeclaradoException ser�
        //lan�ada e checaTipo retornar� false.
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
