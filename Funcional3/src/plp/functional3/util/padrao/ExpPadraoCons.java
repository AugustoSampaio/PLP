package plp.functional3.util.padrao;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Id;
import plp.expressions2.expression.Valor;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.functional1.util.TipoPolimorfico;
import plp.functional3.expression.ExpCons;
import plp.functional3.util.TipoLista;

public class ExpPadraoCons extends ExpPadrao<ExpCons> {
	
	public ExpPadraoCons(ExpCons expressao) {
		super(expressao);
	}
	
	@Override
	public int getAridade() {
		return this.getAridadeDir() + this.getAridadeEsq();
	}
	
	private int getAridadeDir() {
		return this.getExpressao().getDir() instanceof Id ? 1 : 0;
	}
	
	private int getAridadeEsq() {
		return this.getExpressao().getEsq() instanceof Id ? 1 : 0;
	}
	
	@Override
	public boolean match(AmbienteExecucao ambiente, Expressao matchExpressao) {
		if ( this.getAridade() == 2 ) {
			return true;
		}
		
		if ( this.getAridade() == 1 ) {
			return false;
		}
		
		Valor valorPadrao = this.getExpressao().avaliar(ambiente);
		Valor valorMatch = matchExpressao.avaliar(ambiente);
		
		return valorPadrao.equals(valorMatch);
	}
	
	@Override
	public boolean checaTipo(AmbienteCompilacao ambiente) {
		Expressao esq = this.getExpressao().getEsq();
		if ( esq instanceof Id ) {
			ambiente.map((Id) esq, new TipoPolimorfico());
		}
		
		Expressao dir = this.getExpressao().getDir();
		if ( dir instanceof Id ) {
			ambiente.map((Id) dir, new TipoLista());
		}
		
		return true;
	}
	
	@Override
	public Tipo getTipo(AmbienteCompilacao ambiente) {
		this.checaTipo(ambiente);
		return this.getExpressao().getTipo(ambiente);
	}

	@Override
	public ExpPadrao<ExpCons> clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
