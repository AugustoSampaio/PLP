package loo2.plp.orientadaObjetos1.declaracao.procedimento;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;
/**
 * Classe que representa uma declara��o de par�metros.
 */
public class DecParametro {
    /**
     * Identificador declarado.
     */
    private Id id;
    /**
     * Tipo do identificador declarado.
     */
    private Tipo tipo;
    /**
     * Construtor.
     * @param id Identificador declarado.
     * @param tipo Tipo do identificador declarado.
     */
    public DecParametro(Id id, Tipo tipo){
        this.id = id;
        this.tipo = tipo;
    }
    /**
     * Obt�m o identificador declarado.
     * @return o identificador.
     */
    public Id getId() {
        return id;
    }
    /**
     * Obt�m o tipo do identifador declarado.
     * @return o tipo do identifador declarado.
     */
    public Tipo getTipo() {
        return tipo;
    }
    /**
     * Cria um mapeamento do identificador para o valor da express�o
     * desta declara��o no AmbienteExecucao
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela inicializa��o da vari�vel.
     */
    public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente) {
        return ambiente;
    }
    /**
     * Verifica se a declara��o est� bem tipada, ou seja, se a
     * express�o de inicializa��o est� bem tipada.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos da declara��o s�o v�lidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)throws ClasseNaoDeclaradaException {
        return tipo.eValido(ambiente);
    }

    /**
     * Cria um mapeamento do identificador para o tipo do parametro
     * desta declara��o no AmbienteCompilacao
     *
     * @param ambiente o ambiente que contem o mapeamento entre identificador
     *  e seu tipo.
     * @return o ambiente modificado pela declara��o do parametro.
     */
    public AmbienteCompilacaoOO1 declaraParametro(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

        ambiente.map(id, tipo);
        return ambiente;
    }
}
