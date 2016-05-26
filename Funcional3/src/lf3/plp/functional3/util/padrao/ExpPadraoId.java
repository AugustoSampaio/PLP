package lf3.plp.functional3.util.padrao;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.functional1.util.TipoPolimorfico;

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
