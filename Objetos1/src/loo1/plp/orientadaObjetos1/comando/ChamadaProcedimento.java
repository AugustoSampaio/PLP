package loo1.plp.orientadaObjetos1.comando;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import loo1.plp.orientadaObjetos1.expressao.ListaExpressao;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo1.plp.orientadaObjetos1.memoria.colecao.ListaValor;
import loo1.plp.orientadaObjetos1.util.ListaTipo;

/**
 * Classe que representa uma chamada de um procedimento.
 */
public class ChamadaProcedimento implements Comando {

    /**
     * � o procedimento
     */
    private Procedimento procedimento;

    /**
     * S�o os parametros do procedimento
     */
    private ListaExpressao parametrosReais;

    /**
     * Valores que serao atribu�dos aos parametros reais
     */
    private ListaValor valoresParametros;

    /**
     * Contrutor Default.
     * @param procedimento � o procedimento
     * @param parametrosReais sao os par�metros do procedimento
     * @param valoresParametros sao os valores dos parametros
     */
    public ChamadaProcedimento(Procedimento procedimento, ListaExpressao parametrosReais,
                               ListaValor valoresParametros){
        this.procedimento = procedimento;
        this.parametrosReais = parametrosReais;
        this.valoresParametros = valoresParametros;
    }

    /**
     * Contrutor Default.
     * @param procedimento � o procedimento
     * @param parametrosReais sao os par�metros do procedimento
     */
    public ChamadaProcedimento(Procedimento procedimento, ListaExpressao parametrosReais){
        this.procedimento = procedimento;
        this.parametrosReais = parametrosReais;
        this.valoresParametros = null;
    }
    /**
     * Executa este comando.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela execu��o do comando.
     */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
               ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
               ObjetoNaoDeclaradoException, ObjetoJaDeclaradoException,
               ClasseNaoDeclaradaException, ClasseJaDeclaradaException, EntradaInvalidaException{

        ambiente.incrementa();
        ambiente = bindParameters(ambiente, procedimento.getParametrosFormais());
        ambiente = procedimento.getComando().executar(ambiente);
        ambiente.restaura();
        return ambiente;
    }

    /**
     * insere no contexto o resultado da associacao entre cada parametro formal
     * e seu correspondente parametro atual
     * @throws ClasseNaoDeclaradaException 
     */
    private AmbienteExecucaoOO1 bindParameters (AmbienteExecucaoOO1 ambiente, ListaDeclaracaoParametro parametrosFormais)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
               ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException{
        ListaValor listaValor = this.valoresParametros;
        if(listaValor == null) {
            listaValor = parametrosReais.avaliar(ambiente);
        }
        while (listaValor.length() > 0){
            ambiente.map(parametrosFormais.getHead().getId(), listaValor.getHead());
            parametrosFormais = ((ListaDeclaracaoParametro) parametrosFormais.getTail());
            listaValor = (ListaValor)listaValor.getTail();
        }
        return ambiente;
    }

    /**
     * Realiza a verificacao de tipos desta chamada de procedimento, onde
     * os tipos dos parametros formais devem ser iguais aos tipos dos
     * parametros reais na ordem em que se apresentam.
     *
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e tipos.
     *
     * @return <code>true</code> se a chamada de procedimeno est� bem tipada;
     *          <code>false</code> caso contrario.
     */
     public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ProcedimentoNaoDeclaradoException,ClasseNaoDeclaradaException {
        boolean resposta;
        ambiente.incrementa();
        ListaDeclaracaoParametro parametrosFormais = procedimento.getParametrosFormais();
        ListaTipo listaTipo = parametrosReais.getTipos(ambiente);
        //tem o mesmo numero de parametros formais e reais?
        if(listaTipo.length() == parametrosFormais.length()) {
            // a funcao tem algum parametro?
            if(listaTipo.head() == null || parametrosFormais.getHead() == null) {
                resposta = true;
            }
            else {
                resposta = true;
                // tem parametros formais de tipos diferentes
                // de parametros reais na ordem em que se apresentam?
                while(listaTipo != null && parametrosFormais != null) {
                    if( ! listaTipo.head().equals(parametrosFormais.getHead().getTipo())) {
                        resposta = false;
                        break;
                    }
                    listaTipo = listaTipo.tail();
                    parametrosFormais = ((ListaDeclaracaoParametro) parametrosFormais.getTail());
                }
            }
        }
        else {
            resposta = false;
        }
        ambiente.restaura();
        return resposta;
    }
}