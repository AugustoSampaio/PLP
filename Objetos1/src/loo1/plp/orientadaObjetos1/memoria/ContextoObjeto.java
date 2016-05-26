package loo1.plp.orientadaObjetos1.memoria;

import java.util.HashMap;

import loo1.plp.expressions2.expression.Id;
import loo1.plp.orientadaObjetos1.expressao.valor.Valor;

public class ContextoObjeto{

	private HashMap<Id, Valor> estado;
	
	@SuppressWarnings("unchecked")
	public ContextoObjeto(HashMap<Id, Valor> hash) {
		this.estado = (HashMap<Id, Valor>)hash.clone();
	}

	public void remove(Id id) {
		this.estado.remove(id);
	}

	public void put(Id id,Valor valor) {
		this.estado.put(id, valor);
	}

	public boolean containsKey(Id idVariavel) {
		return this.estado.containsKey(idVariavel);
	}

	public Valor get(Id id) {
		return this.estado.get(id);
	}

}
