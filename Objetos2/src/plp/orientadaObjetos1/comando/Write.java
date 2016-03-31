package plp.orientadaObjetos1.comando;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.expressao.Expressao;
import plp.orientadaObjetos1.expressao.valor.Valor;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;

/**
 * Comando de escrita.
 */
public class Write implements IO {
	/**
	 * Expressão a ser escrita.
	 */
    private Expressao expressao;
	/**
	 * Construtor.
	 * @param expressão Expressão a ser escrita.
	 */
    public Write(Expressao expressao){
        this.expressao = expressao;
    }

    /**
     * Escreve na saida padrão.
     * @param ambiente o ambiente de execução.
     * @return o ambiente depois de modificado pela execução
     * do comando <code>write</code>.
     * @throws ClasseNaoDeclaradaException 
     */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
        ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException {
        Valor valor = expressao.avaliar(ambiente);
        System.out.println(valor);
        return ambiente.write( valor);
    }

    /**
     * Realiza a verificacao de tipos da expressão a ser escrita na
     * pelo comando <code>write</code>
     * @param ambiente o ambiente de compilação.
     * @return <code>true</code> se a expressão a ser escrita está bem tipada;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
        ClasseNaoDeclaradaException {
        return expressao.checaTipo(ambiente);
    }
}
