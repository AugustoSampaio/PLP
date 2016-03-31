package plp.orientadaObjetos1.memoria;

import java.util.HashMap;

import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos1.expressao.valor.Valor;
import plp.orientadaObjetos1.expressao.valor.ValorRef;
/**
 * Conjunto formado pelo nome da classe de um objeto e o seu estado
 * representado pelo ambiente de execução.
 */
public class Objeto {
    /**
     * Identificador da classe a que pertence o objeto.
     */
    private Id classeObjeto;

    /**
     * Estado do objeto no ambiente de execução.
     */
    private ContextoObjeto estado;

    /**
     * Construtor.
     * @param classeObjeto Classe a que pertence este objeto.
     * @param estadoObj Estado do objeto no ambiente de execução.
     */
    public Objeto(Id classeObjeto,  ContextoObjeto estadoObj) {
        this.classeObjeto = classeObjeto; 
        this.estado = estadoObj;
    }

	/**
     * Obtem o identificador da classe do objeto.
     * @return o identificador da classe do objeto.
     */
    public Id getClasse() {
         return classeObjeto;
    }

    /**
     * Obtém o atual estado do objeto, conforme o ambiente de execução.
     * @return o atual estado do objeto, conforme o ambiente de execução.
     */
    public ContextoObjeto getEstado() {
         return estado;
    }

    /**
     * Altera o ambiente de Execução, que representa o novo estado do objeto.
     * @param novoEstado o novo estado do objeto.
     */
    public void setEstado(ContextoObjeto novoEstado) {
         this.estado = novoEstado;
    }

    /**
     * insere e mapeia o atributo this do objeto
     * @param vr
     */
	public void mapThis(ValorRef vr) { 
		Id id = new Id("this");
		this.getEstado().remove(id);
        this.getEstado().put(id, vr);
	}

	/**
	 * muda o valor de um atributo do objeto
	 * @param idVariavel
	 * @param valor
	 * @throws VariavelNaoDeclaradaException
	 */
	public void changeAtributo(Id idVariavel, Valor valor) throws VariavelNaoDeclaradaException{
		
		if (this.getEstado().containsKey(idVariavel)) {
        	this.getEstado().remove(idVariavel);
        	this.getEstado().put(idVariavel, valor);
        }
        else
        {
        	throw new VariavelNaoDeclaradaException(idVariavel);
        }
	}

}
