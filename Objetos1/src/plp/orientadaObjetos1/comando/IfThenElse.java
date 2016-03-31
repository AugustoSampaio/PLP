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
 * Classe que representa o comando If Then Else
 */
public class IfThenElse implements Comando {
	/**
	 * Expressão de avaliação.
	 */
    private Expressao expressao;
	/**
	 * Comando executado caso a expressão seja verdadeira.
	 */
    private Comando comandoThen;
	/**
	 * Comando executado caso a expressão seja falsa.
	 */
    private Comando comandoElse;
	/**
	 * Construtor.
	 * @param expressao Expressão de avaliação.
	 * @param comandoThen Comando executado caso a expressão seja verdadeira.
	 * @param comandoElse Comando executado caso a expressão seja falsa.
	 */
    public IfThenElse (Expressao expressao, Comando comandoThen, Comando comandoElse){
        this.expressao = expressao;
        this.comandoThen = comandoThen;
        this.comandoElse = comandoElse;
    }

    /**
     * Implementa o comando <code>if then else</code>.
     * @param ambiente o ambiente de execução.
     * @return o ambiente depois de modificado pela execução
     * do comando <code>if then else</code>.
     */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
               ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
               ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
               ObjetoJaDeclaradoException, ObjetoNaoDeclaradoException, EntradaInvalidaException {
        if ( ((ValorBooleano)expressao.avaliar(ambiente)).valor() )
            return comandoThen.executar(ambiente);
        else return comandoElse.executar(ambiente);
    }

    /**
     * Realiza a verificacao de tipos da expressão e dos
     * comandos do comando <code>if then else</code>
     * @param ambiente o ambiente de compilação.
     * @return <code>true</code> se a expressão e os comando são bem tipados;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException,
               ClasseJaDeclaradaException, ClasseNaoDeclaradaException {
        return expressao.checaTipo(ambiente) &&
               ((TipoPrimitivo)expressao.getTipo(ambiente)).eBooleano() &&
               comandoThen.checaTipo(ambiente) &&
               comandoElse.checaTipo(ambiente);
    }

}
