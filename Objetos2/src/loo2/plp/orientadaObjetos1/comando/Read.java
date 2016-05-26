package loo2.plp.orientadaObjetos1.comando;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;

/**
 * Representa um comando de leitura.
 */
public class Read implements IO{
	/**
	 * O identificador ao qual ser� atribu�do o valor lido.
	 */
    private Id id;
	/**
	 * O tipo do identificador cujo novo valor ser� lido.
	 */
    private Tipo tipoId;
	/**
	 * Construtor.
	 * @param id O identificador ao qual ser� a atribu�do o valor lido.
	 */
    public Read ( Id id){
        this.id = id;
    }

    /**
     * L� da entrada padr�o.
     * @param ambiente o ambiente de execu��o.
     * @return o ambiente depois de modificado pela execu��o
     * do comando read.
     *
     */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException, EntradaInvalidaException {
        ambiente.changeValor(id, ambiente.read(this.tipoId));
        return ambiente;
    }

    /**
     * Realiza a verificacao de tipos da entrada
     * @param ambiente o ambiente de compila��o.
     * @return <code>true</code> se a express�o da entrada est� bem tipada;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException {
//      Alteramos a implementa��o, pois em tempo de compila��o n�o se pode saber
//      o tipo da entrada que ser� lida.

//        Tipo tipo = ambiente.getTipoEntrada();
//        Tipo tipo2 = id.getTipo(ambiente);

        this.tipoId = id.getTipo(ambiente);
        return id.checaTipo(ambiente);

        //return id.getTipo(ambiente).equals(ambiente.getTipoEntrada());
    }

}
