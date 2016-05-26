package lf3.plp.functional3.util.padrao;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.expression.ValorBooleano;
import lf3.plp.expressions2.expression.ValorInteiro;
import lf3.plp.expressions2.expression.ValorString;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;

public abstract class ExpPadrao<T extends Expressao> {
	
	private T expressao;
	
	public ExpPadrao(T expressao) {
		super();
		this.expressao = expressao;
	}
	
	public T getExpressao() {
		return this.expressao;
	}
	
	public abstract int getAridade();
	
	public abstract boolean match(AmbienteExecucao ambiente, Expressao matchExpressao);
	
	public abstract boolean checaTipo(AmbienteCompilacao ambiente);
	
	public abstract Tipo getTipo(AmbienteCompilacao ambiente);
	
	@Override
	public String toString() {
		return this.expressao.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static ExpPadrao createExpPadraoFrom(Expressao expressao) {
		if ( expressao instanceof Id ) {
			Id id = (Id) expressao;
			return new ExpPadraoId(id);
		}
		else if ( expressao instanceof ValorInteiro ) {
			ValorInteiro inteiro = (ValorInteiro) expressao;
			return new ExpPadraoConstante(inteiro);
		}
		else if ( expressao instanceof ValorBooleano ) {
			ValorBooleano booleano = (ValorBooleano) expressao;
			return new ExpPadraoConstante(booleano);
		}
		else if ( expressao instanceof ValorString ) {
			ValorString str = (ValorString) expressao;
			return new ExpPadraoConstante(str);
		}
		
		throw new RuntimeException("Erro ao criar ExpPadrao. Tipo inv�lido.");
	}
	
	public abstract ExpPadrao<T> clone();
}
