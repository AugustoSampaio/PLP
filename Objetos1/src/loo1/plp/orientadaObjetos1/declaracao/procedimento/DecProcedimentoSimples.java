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
import loo1.plp.orientadaObjetos1.memoria.InfoEscopo;

/**
 * Representa uma declaração de procedimento simples.
 */
public class DecProcedimentoSimples implements DecProcedimento {

    protected Id nome;
    protected ListaDeclaracaoParametro parametrosFormais;
    protected Comando comando;
    protected InfoEscopo infoEscopo;

    public DecProcedimentoSimples(Id nome, ListaDeclaracaoParametro parametrosFormais, Comando comando){
        this.nome = nome;
        this.parametrosFormais = parametrosFormais;
        this.comando = comando;
    }

    public DecProcedimentoSimples(Id nome, ListaDeclaracaoParametro parametrosFormais, Comando comando, InfoEscopo infoEscopo){
        this(nome, parametrosFormais, comando);
        this.infoEscopo = infoEscopo;
    }

    public Procedimento getProcedimento(Id nome) throws ProcedimentoNaoDeclaradoException {
        if(this.nome.equals(nome)){
            return new Procedimento(parametrosFormais, comando);
        } else {
            throw new ProcedimentoNaoDeclaradoException(nome);
        }
    }

    /**
     * Verifica se a declaração está bem tipada.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
       throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
              ProcedimentoJaDeclaradoException, ProcedimentoNaoDeclaradoException,
              ClasseNaoDeclaradaException, ClasseJaDeclaradaException {
       boolean resposta;
        if(parametrosFormais.checaTipo(ambiente)) {
            ambiente.mapParametrosProcedimento(nome, parametrosFormais);
            ambiente.incrementa();
    			// O frame do debugger deve carregar o nome do procedimento,
    			// porque o escopo OO não é suficiente por si só.
            ((loo1.plp.orientadaObjetos1.memoria.ContextoCompilacaoOO1) ambiente).registraEscopo(infoEscopo, nome.toString());
            ambiente = parametrosFormais.declaraParametro(ambiente);
            resposta = comando.checaTipo(ambiente);
            ambiente.restaura();
        } else {
            resposta = false;
        }
        return resposta;
    }
}
