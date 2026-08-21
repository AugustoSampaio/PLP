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

/**
 * Classe que representa um comando de declara��o.
 */
public class ComDeclaracao implements Comando {
	/**
	 * A declara��o em si.
	 */
    private Declaracao declaracao;
	/**
	 * O comando executado ap�s a declara��o.
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
     * Declara a(s) vari�vel(is) e executa o comando.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela execu��o da declara��o e do comando.
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
     * o tipo de uma variavel � o tipo do valor da sua primeira atribuicao.
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
