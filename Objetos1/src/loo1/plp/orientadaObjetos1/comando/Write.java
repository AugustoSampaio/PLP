package loo1.plp.orientadaObjetos1.comando;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.expressao.Expressao;
import loo1.plp.orientadaObjetos1.expressao.valor.Valor;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;

/**
 * Comando de escrita.
 */
public class Write implements IO {
	/**
	 * Express�o a ser escrita.
	 */
    private Expressao expressao;
	/**
	 * Construtor.
	 * @param express�o Express�o a ser escrita.
	 */
    public Write(Expressao expressao){
        this.expressao = expressao;
    }

    /**
     * Escreve na saida padr�o.
     * @param ambiente o ambiente de execu��o.
     * @return o ambiente depois de modificado pela execu��o
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
     * Realiza a verificacao de tipos da express�o a ser escrita na
     * pelo comando <code>write</code>
     * @param ambiente o ambiente de compila��o.
     * @return <code>true</code> se a express�o a ser escrita est� bem tipada;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
        ClasseNaoDeclaradaException {
        return expressao.checaTipo(ambiente);
    }
}
