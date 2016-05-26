package li1.plp.imperative1.memory;

import li1.plp.expressions1.util.Tipo;
import li1.plp.expressions2.memory.ContextoCompilacao;
import li1.plp.expressions2.memory.VariavelJaDeclaradaException;
import li1.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ContextoCompilacaoImperativa extends ContextoCompilacao 
	implements AmbienteCompilacaoImperativa {

    /**
     * A pilha de blocos de contexto.
     */    
    private ListaValor entrada;

    /**
     * Construtor da classe.
     */
    public ContextoCompilacaoImperativa(ListaValor entrada){
        super();
        this.entrada = entrada;        
    }

    public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
    		EntradaVaziaException {
        if(entrada == null || entrada.getHead() == null) {
            throw new EntradaVaziaException();
        }
        Tipo aux = entrada.getHead().getTipo(this);
        entrada = (ListaValor)entrada.getTail();
        return aux;            
    }
    
}

