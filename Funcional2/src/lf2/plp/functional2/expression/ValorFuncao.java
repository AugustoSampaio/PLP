package lf2.plp.functional2.expression;

import static lf2.plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.List;

import lf2.plp.expressions2.expression.Expressao;
import lf2.plp.expressions2.expression.Id;
import lf2.plp.expressions2.expression.Valor;
import lf2.plp.expressions2.memory.AmbienteExecucao;
import lf2.plp.functional1.util.DefFuncao;

/**
 * @author S�rgio
 */
public class ValorFuncao extends DefFuncao implements ValorAbstrato {

	private Id id;

	public ValorFuncao(List<Id> argsId, Expressao exp) {
		super(argsId, exp);
	}

	public Valor avaliar(AmbienteExecucao ambiente) {
		this.reduzir(ambiente);
		return this;
	}

	@Override
	public String toString() {
	
		return String.format("fn %s . %s", listToString(getListaId(), " "),
				getExp());
	}
	
	public Id getId() {
		return this.id;
	}
	
	public void setId (Id id){
		this.id = id;
	}
	
	public Expressao reduzir(AmbienteExecucao ambiente) {
		ambiente.incrementa();

		if(this.id != null){
			ambiente.map(this.id, new ValorIrredutivel());
		}
		
		for(Id id : this.argsId){
			ambiente.map(id, new ValorIrredutivel());
		}
		this.exp = exp.reduzir(ambiente);
		
		ambiente.restaura();
		
		return this;
	}
	
	public ValorFuncao clone() {
		ValorFuncao retorno;
		List<Id> novaLista = new ArrayList<Id>(this.argsId.size());
		
		for (Id id : this.argsId) {
			novaLista.add(id.clone());
		}
		
		retorno = new ValorFuncao(novaLista, this.exp.clone());
		
		if (this.id != null)
			retorno.setId(this.id.clone());
		
		return retorno;
	}
}
