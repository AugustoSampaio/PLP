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
import loo2.plp.orientadaObjetos1.expressao.valor.ValorBooleano;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.TipoPrimitivo;

/**
 * Classe que representa um comando While.
 */
public class While implements Comando{
	/**
	 * Express�o booleana a ser avaliada.
	 */
    private Expressao expressao;
	/**
	 * Comando que ser� executado caso a express�o seja avaliada como verdadeira.
	 */
    private Comando comando;
	/**
	 * Construtor.
	 * @param expressao A express�o booleana a ser avaliada.
	 * @param o comando a ser executado caso a express�o seja verdadeira.
	 */
    public While(Expressao expressao, Comando comando){
        this.expressao  = expressao;
        this.comando = comando;
    }

    /**
     * Implementa o comando <code>while</code>.
     * @param ambiente o ambiente de execu��o.
     * @return o ambiente depois de modificado pela execu��o
     * do comando <code>while</code>.
     */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
               ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
                ObjetoJaDeclaradoException, ObjetoNaoDeclaradoException,
                ClasseJaDeclaradaException, ClasseNaoDeclaradaException, EntradaInvalidaException{
        while ( ((ValorBooleano)expressao.avaliar(ambiente)).valor() ) {
            ambiente = comando.executar(ambiente);
        }
        return ambiente;
    }

    /**
     * Realiza a verificacao de tipos da express�o e dos
     * comandos do comando <code>while</code>
     * @param ambiente o ambiente de compila��o.
     * @return <code>true</code> se os comando s�o bem tipados;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
        ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
        ClasseNaoDeclaradaException, ClasseJaDeclaradaException {
        return expressao.checaTipo(ambiente) &&
               ((TipoPrimitivo)expressao.getTipo(ambiente)).eBooleano() &&
               comando.checaTipo(ambiente);
    }

}
