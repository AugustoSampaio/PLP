package lf3.plp.functional3.expression;

import static lf3.plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.expression.Valor;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional1.util.TipoFuncao;
import lf3.plp.functional1.util.TipoPolimorfico;
import lf3.plp.functional3.declaration.DecPadrao;
import lf3.plp.functional3.exception.PadraoNaoEncontradoException;
import lf3.plp.functional3.util.AmbienteUtil;

public class Aplicacao implements Expressao {
	
	private Expressao funcao;
	private List<Expressao> argumentos;
	
	public Aplicacao(Expressao funcao, List<Expressao> argumentos) {
		super();
		this.funcao = funcao;
		this.argumentos = argumentos;
	}
	
	public Expressao getFuncao() {
		return this.funcao;
	}
	
	public List<Expressao> getArgumentos() {
		return this.argumentos;
	}
	
	public Valor avaliar(AmbienteExecucao ambiente) throws VariavelNaoDeclaradaException,
			VariavelJaDeclaradaException, PadraoNaoEncontradoException {
		
		ValorFuncao valorFuncao = (ValorFuncao) this.funcao.avaliar(ambiente);
		DecPadrao decPadrao = valorFuncao.getMatch(this.argumentos, ambiente);
		
		Map<Id, Valor> mapIdValor = AmbienteUtil.resolveParametersBindings(ambiente,
				decPadrao, this.argumentos);
		ambiente.incrementa();
		AmbienteUtil.includeValueBindings(ambiente, mapIdValor);
		
		Expressao exp = decPadrao.getExpressao();
		exp.reduzir(ambiente);		
		
		Valor result = exp.avaliar(ambiente);
		
		ambiente.restaura();
		
		return result;
	}
	
	public boolean checaTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		
		// Retorna o tipo da fun��o usada pela aplica��o
		Tipo tipo = this.getFuncType(amb);
		TipoFuncao tipoFuncao = (TipoFuncao) tipo;
		
		return tipoFuncao.checaTipo(amb, this.argumentos);
	}
	
	private Tipo getFuncType(AmbienteCompilacao ambiente) {
		
		// Retorna o tipo da func�o
		Tipo tipoFuncao = null;
		if ( this.funcao instanceof Id ) {
			tipoFuncao = ambiente.get((Id) this.funcao);
		}
		else if ( this.funcao instanceof ValorFuncao ) {
			tipoFuncao = ((ValorFuncao) this.funcao).getTipo(ambiente);
		}
		
		if ( tipoFuncao == null || tipoFuncao instanceof TipoPolimorfico ) {
			ArrayList<Tipo> params = new ArrayList<Tipo>();
			for ( Expressao valorReal : this.argumentos ) {
				params.add(valorReal.getTipo(ambiente));
			}
			
			tipoFuncao = new TipoFuncao(params, new TipoPolimorfico());
		}
		return tipoFuncao;
	}
	
	public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException,
			VariavelJaDeclaradaException {
		
		Tipo tipo = this.getFuncType(amb);
		TipoFuncao tipoFuncao = (TipoFuncao) tipo;
		return tipoFuncao.getTipo(amb, this.argumentos);
	}
	
	@Override
	public String toString() {
		return String.format("%s(%s)", this.funcao, listToString(this.argumentos, ","));
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		this.funcao = this.funcao.reduzir(ambiente);
		
		ArrayList<Expressao> novosArgs =
			new ArrayList<Expressao>(this.argumentos.size());
		
		for(Expressao arg : this.argumentos) {
			novosArgs.add(arg.reduzir(ambiente));
		}
		this.argumentos = novosArgs;
		
		return this;
	}
	
	public Aplicacao clone() {
		List<Expressao> novaLista = new ArrayList<Expressao>(this.argumentos.size());		
		for(Expressao exp : this.argumentos){
			novaLista.add(exp.clone());
		}			
		
		return new Aplicacao(this.funcao.clone(), novaLista);
	}
}
