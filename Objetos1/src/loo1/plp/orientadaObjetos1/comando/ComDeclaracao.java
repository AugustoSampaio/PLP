package loo1.plp.orientadaObjetos1.comando;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.declaracao.Declaracao;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo1.plp.orientadaObjetos1.memoria.InfoEscopo;

/**
 * Classe que representa um comando de declaração.
 */
public class ComDeclaracao implements Comando {

    private Declaracao declaracao;
    private Comando comando;
    private InfoEscopo infoEscopo;

    public ComDeclaracao(Declaracao declaracao, Comando comando){
        this.declaracao = declaracao;
        this.comando = comando;
    }

	public ComDeclaracao(Declaracao declaracao, Comando comando, InfoEscopo infoEscopo){
		this(declaracao, comando);
		this.infoEscopo = infoEscopo;
	}

    /**
     * Declara a(s) variável(is) e executa o comando.
     */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
               ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
               ClasseNaoDeclaradaException, ClasseJaDeclaradaException,
               ObjetoJaDeclaradoException, ObjetoNaoDeclaradoException, EntradaInvalidaException{
        ambiente.incrementa();
        ambiente = comando.executar(declaracao.elabora(ambiente));
        ambiente.restaura();
        return ambiente;
    }

    /**
     * Verifica se o tipo do comando esta correto.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
               ClasseNaoDeclaradaException, ClasseJaDeclaradaException{
        boolean resposta;
        ambiente.incrementa();
		ambiente.registraEscopo(infoEscopo);
        resposta = declaracao.checaTipo(ambiente) && comando.checaTipo(ambiente);
        ambiente.restaura();
        return resposta;
    }
}
