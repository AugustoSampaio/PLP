package plp.functional3.util.padrao;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Id;
import plp.expressions2.expression.ValorBooleano;
import plp.expressions2.expression.ValorInteiro;
import plp.expressions2.expression.ValorString;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;

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
		
		throw new RuntimeException("Erro ao criar ExpPadrao. Tipo inválido.");
	}
	
	public abstract ExpPadrao<T> clone();
}
