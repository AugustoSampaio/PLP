package plp.orientadaObjetos1.expressao.leftExpression;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.expressao.valor.Valor;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.util.Tipo;

/**
 * Classe que representa um identificador.
 */
public class Id extends plp.expressions2.expression.Id implements LeftExpression{ //,IDominio{
    
	/**
     * Construtor.
     * @param strName Nome do identificador.
     */
    public Id(String strName) {
        super(strName);
    }
    /**
     * Retorna o nome desse identificador.
     * @return o nome do identificador.
     */
    public String toString() {
        return this.getIdName();
    }

    /**
     * Retorna o valor deste identificador.
     * @param ambiente o ambiente de execução, com o mapeamento de identificadores
     * a valores.
     * @return o valor deste identificador
     * @throws VariavelNaoDeclaradaException 
     * @exception VariavelNaoDeclaradaException se este identificador nao
     *  estiver no ambiente.
     */
    public Valor avaliar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, ClasseNaoDeclaradaException, VariavelNaoDeclaradaException {
        return obterValorDeIdNoAmbiente(ambiente);
    }

    /**
     * Realiza a verificacao de tipos desta expressao.
     * @param ambiente o ambiente de compilação.
     * @return <code>true</code> se os tipos da expressao são válidos;
     *          <code>false</code> caso contrario.
     * @throws VariavelNaoDeclaradaException 
     * @exception VariavelNaoDeclaradaException se este identificador nao
     *          estiver no ambiente.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 amb) throws VariavelNaoDeclaradaException {
        boolean result = true;
        amb.get(this); // verifica se está no ambiente
        return result;
    }

    /**
     * Retorna os tipos possiveis desta expressao.
     * @param ambiente o ambiente de compilação.
     * @return os tipos possiveis desta expressao.
     * @throws VariavelNaoDeclaradaException 
     * @exception VariavelNaoDeclaradaException se este identificador nao
     *          estiver no ambiente.
     */
    public Tipo getTipo(AmbienteCompilacaoOO1 amb) throws VariavelNaoDeclaradaException {
        return amb.get(this);
    }
    /**
     * Obtém este identificador.
     * @return este Id.
     */
    public Id getId(){
        return this;
    }

    /**
     * Retorna o valor do Objeto representado por um certo id
     * @param ambiente é o Ambiente de Execução
     * @return o valor do Objeto representado por um certo id
     * @throws VariavelNaoDeclaradaException 
     */
    private Valor obterValorDeIdNoAmbiente(AmbienteExecucaoOO1 ambiente)
            throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        return ambiente.get(this);
    }
}