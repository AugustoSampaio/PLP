package loo1.plp.orientadaObjetos1.declaracao.procedimento;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.comando.Comando;
import loo1.plp.orientadaObjetos1.comando.Procedimento;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
/**
 * Representa uma declara��o de procedimento simples.
 */
public class DecProcedimentoSimples implements  DecProcedimento {
    /**
     * Identificador do procedimento.
     */
    protected Id nome;
    /**
     * Par�metros do procedimento.
     */
    protected ListaDeclaracaoParametro parametrosFormais;
    /**
     * Comando, que pode ser simples ou composto, executado pelo procedimento.
     */
    protected Comando comando;
    /**
     * Construtor.
     * @param nome Nome do procedimento.
     * @param parametrosFormais Par�metros do procedimento.
     * @param comando Comando(s) executado(s) pelo procedimento.
     */
    public DecProcedimentoSimples(Id nome, ListaDeclaracaoParametro parametrosFormais,Comando comando){
        this.nome = nome;
        this.parametrosFormais = parametrosFormais;
        this.comando = comando;
    }
    /**
     * Obt�m o procedimento representado por nome.
     * @param nome O nome do procedimento procurado.
     * @return o procedimento identificado por nome.
     * @throws ProcedimentoNaoDeclaradoException quando n�o existe nenhum
     * procedimento declarado com esse nome.
     */
    public Procedimento getProcedimento(Id nome) throws ProcedimentoNaoDeclaradoException {
        if(this.nome.equals(nome)){
            return new Procedimento(parametrosFormais, comando);
        }
        else {
            throw new ProcedimentoNaoDeclaradoException(nome);
        }
    }
    /**
     * Verifica se a declara��o est� bem tipada, ou seja, se os
     * comandos est�o bem tipados.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos dos comandos s�o v�lidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
       throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
              ProcedimentoJaDeclaradoException, ProcedimentoNaoDeclaradoException,
              ClasseNaoDeclaradaException,ClasseJaDeclaradaException {
       boolean resposta;
        if(parametrosFormais.checaTipo(ambiente)) {
            ambiente.mapParametrosProcedimento(nome, parametrosFormais);
            ambiente.incrementa();
            ambiente = parametrosFormais.declaraParametro(ambiente);
            resposta = comando.checaTipo(ambiente);
            ambiente.restaura();
        }
        else {
            resposta = false;
        }
        return resposta;
    }
}
