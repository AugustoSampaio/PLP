package plp.functional3.expression;

import plp.expressions1.util.Tipo;
import plp.expressions1.util.TipoPrimitivo;
import plp.expressions2.expression.ExpBinaria;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Valor;
import plp.expressions2.expression.ValorInteiro;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.functional3.util.TipoLista;

public class ExpSequencia extends ExpBinaria {

	public ExpSequencia(Expressao inicio, Expressao fim) {
		super(inicio, fim, "..");
	}

	@Override
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return getEsq().getTipo(amb).eInteiro()
				&& getDir().getTipo(amb).eInteiro();
	}

	public Valor avaliar(AmbienteExecucao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		ValorInteiro esq = (ValorInteiro) getEsq().avaliar(amb);
		ValorInteiro dir = (ValorInteiro) getDir().avaliar(amb);

		ValorLista retorno = ValorLista.getInstancia(null, null);

		int fim = dir.valor();
		int inicio = esq.valor();
		int incremento = inicio < fim ? 1 : -1;
		if (inicio < fim) {
			for (int i = fim; i >= inicio; i -= incremento) {
				retorno.cons(new ValorInteiro(i));
			}
		} else {
			for (int i = fim; i <= inicio; i -= incremento) {
				retorno.cons(new ValorInteiro(i));
			}
		}

		return retorno;
	}

	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return new TipoLista(TipoPrimitivo.INTEIRO);
	}

	@Override
	public String toString() {
		return String.format("[%s]", super.toString());
	}
	
	public ExpSequencia clone() {
		return new ExpSequencia(this.esq.clone(), this.dir.clone());
	}
}
