package plp.expressions2.memory;

import java.util.HashMap;
import java.util.Stack;
import java.util.Map.Entry;

import plp.expressions2.expression.Id;
import plp.expressions2.expression.Valor;
import plp.functional1.memory.ContextoExecucaoFuncional;


public class ContextoExecucao extends Contexto<Valor>
        implements AmbienteExecucao {

	public ContextoExecucao clone() {
		ContextoExecucaoFuncional retorno = new ContextoExecucaoFuncional();
		
		Stack<HashMap<Id, Valor>> novaPilha = new Stack<HashMap<Id, Valor>>();
		
		HashMap<Id, Valor> novoMap = new HashMap<Id, Valor>();
		novaPilha.add(novoMap);

		for (HashMap<Id, Valor> map : this.pilha){
			for(Entry<Id, Valor> entry : map.entrySet()){
				novoMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		retorno.setPilha(novaPilha);
		
		return retorno;
	}
}
