package plp.functional3.util.padrao;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Id;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.functional1.util.TipoPolimorfico;

public class ExpPadraoId extends ExpPadrao<Id> {
	
	public ExpPadraoId(Id expressao) {
		super(expressao);
	}
	
	@Override
	public int getAridade() {
		return 1;
	}
	
	@Override
	public boolean match(AmbienteExecucao ambiente, Expressao matchExpressao) {
		return true;
	}
	
	@Override
	public boolean checaTipo(AmbienteCompilacao ambiente) {
		ambiente.map(this.getExpressao(), new TipoPolimorfico());
		return true;
	}
	
	@Override
	public Tipo getTipo(AmbienteCompilacao ambiente) {
		this.checaTipo(ambiente);
		return this.getExpressao().getTipo(ambiente);
	}

	@Override
	public ExpPadrao<Id> clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
