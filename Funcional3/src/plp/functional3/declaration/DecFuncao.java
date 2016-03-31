package plp.functional3.declaration;

import java.util.ArrayList;
import java.util.List;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.Id;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.functional1.declaration.DeclaracaoFuncional;
import plp.functional1.util.TipoFuncao;
import plp.functional1.util.TipoPolimorfico;
import plp.functional3.expression.ValorFuncao;

public class DecFuncao implements DeclaracaoFuncional {
	
	private ValorFuncao valorFuncao;
	
	public DecFuncao(List<DecPadrao> listaDecPadroes) {
		this.valorFuncao = new ValorFuncao(listaDecPadroes);
	}
	
	public DecFuncao(ValorFuncao valorFuncao) {
		this.valorFuncao = valorFuncao;
	}
	
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		
		ambiente.incrementa();
		
		// Adiciona os parametros da funcão como TipoPolimorfico
		List<Tipo> params = new ArrayList<Tipo>(this.getAridade());
		for ( int i = 0; i < this.getAridade(); i++ ) {
			params.add(new TipoPolimorfico());
		}
		
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		
		// Mapeia a própria função no ambiente para permitir recursão.
		ambiente.map(this.getId(), tipo);
		
		// Checa o tipo do ValorFuncao da DecFuncao
		boolean result = this.valorFuncao.checaTipo(ambiente);
		
		ambiente.restaura();
		return result;
	}
	
	public int getAridade() {
		return this.valorFuncao.getAridade();
	}
	
	public ValorFuncao getExpressao() {
		return this.valorFuncao;
	}
	
	public void setValorFuncao(ValorFuncao novoValorFuncao) {
		this.valorFuncao = novoValorFuncao;
	}
	
	public List<DecPadrao> getDecPadroes() {
		return this.valorFuncao.getDecPadroes();
	}
	
	public Id getId() {
		return this.getDecPadroes().get(0).getIdFuncao();
	}
	
	public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException,
			VariavelJaDeclaradaException {
		amb.incrementa();
		
		List<Tipo> params = new ArrayList<Tipo>(this.getAridade());
		for ( int i = 0; i < this.getAridade(); i++ ) {
			params.add(new TipoPolimorfico());
		}
		
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		amb.map(this.getId(), tipo);
		
		// Retorna o tipo do ValorFuncao da DecFuncao
		Tipo result = this.valorFuncao.getTipo(amb);
		
		amb.restaura();
		return result;
	}
	
	public DecFuncao clone() {
		return new DecFuncao(this.valorFuncao.clone());		
	}
}
