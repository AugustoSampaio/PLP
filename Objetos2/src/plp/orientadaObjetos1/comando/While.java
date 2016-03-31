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
import plp.orientadaObjetos1.expressao.valor.ValorBooleano;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.util.TipoPrimitivo;

/**
 * Classe que representa um comando While.
 */
public class While implements Comando{
	/**
	 * Expressão booleana a ser avaliada.
	 */
    private Expressao expressao;
	/**
	 * Comando que será executado caso a expressão seja avaliada como verdadeira.
	 */
    private Comando comando;
	/**
	 * Construtor.
	 * @param expressao A expressão booleana a ser avaliada.
	 * @param o comando a ser executado caso a expressão seja verdadeira.
	 */
    public While(Expressao expressao, Comando comando){
        this.expressao  = expressao;
        this.comando = comando;
    }

    /**
     * Implementa o comando <code>while</code>.
     * @param ambiente o ambiente de execução.
     * @return o ambiente depois de modificado pela execução
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
     * Realiza a verificacao de tipos da expressão e dos
     * comandos do comando <code>while</code>
     * @param ambiente o ambiente de compilação.
     * @return <code>true</code> se os comando são bem tipados;
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
