package li2.plp.imperative1.memory;

import java.util.HashMap;
import java.util.Stack;

import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.expression.Valor;
import li2.plp.expressions2.memory.ContextoExecucao;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ContextoExecucaoImperativa extends ContextoExecucao 
	implements AmbienteExecucaoImperativa {

    /**
     * A pilha de blocos de contexto.
     */    
    private ListaValor entrada;
 
    /**
     * A pilha de blocos de contexto.
     */ 
    private ListaValor saida;

    /**
     * Construtor da classe.
     */
    public ContextoExecucaoImperativa(ListaValor entrada){
    	super();
        this.entrada = entrada;
        this.saida = new ListaValor();        
    }
   
    public Valor read() throws EntradaVaziaException {
        if(entrada == null || entrada.getHead() == null) {
            throw new EntradaVaziaException();
        }
        Valor aux =  entrada.getHead();
        entrada = (ListaValor) entrada.getTail();
        return aux;
    }
   
    public ListaValor getSaida() {
        return saida;    
    }
     
    public void write(Valor v){
    	saida.write(v);
    }

    /**
     * Altera o valor mapeado do id dado.
     *
     * @exception VariavelNaoDeclaradaException se n�o existir nenhum valor
     *          mapeado ao id dado nesta tabela.
     */
    public void changeValor(Id idArg, Valor valorId) 
        	throws VariavelNaoDeclaradaException {   
    	
    	Object result = null;
		Stack<HashMap<Id,Valor>> auxStack = new Stack<HashMap<Id,Valor>>();
		Stack<HashMap<Id,Valor>> stack = this.getPilha();
		
		while (result == null && !stack.empty()) {
			HashMap<Id,Valor> aux = stack.pop();
			auxStack.push(aux);
			result = aux.get(idArg);
			if (result != null) {
				aux.put(idArg, valorId);
			}
		}
		while (!auxStack.empty()) {
			stack.push(auxStack.pop());
		}
		if (result == null) {
			throw new VariavelNaoDeclaradaException(idArg);
		}
    }
}

