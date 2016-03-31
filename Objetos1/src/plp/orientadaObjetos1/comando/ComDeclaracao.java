package plp.orientadaObjetos1.comando;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.declaracao.Declaracao;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;

/**
 * Classe que representa um comando de declaração.
 */
public class ComDeclaracao implements Comando {
	/**
	 * A declaração em si.
	 */
    private Declaracao declaracao;
	/**
	 * O comando executado após a declaração.
	 */
    private Comando comando;
	/**
	 * Construtor.
	 */
    public ComDeclaracao(Declaracao declaracao, Comando comando){
        this.declaracao = declaracao;
        this.comando = comando;
    }

    /**
     * Declara a(s) variável(is) e executa o comando.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela execução da declaração e do comando.
     *
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
     * Verifica se o tipo do comando esta correto, levando em conta que
     * o tipo de uma variavel é o tipo do valor da sua primeira atribuicao.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
               ClasseNaoDeclaradaException, ClasseJaDeclaradaException{
        boolean resposta;
        ambiente.incrementa();
        resposta = declaracao.checaTipo(ambiente) && comando.checaTipo(ambiente);
        ambiente.restaura();
        return resposta;
    }
}
