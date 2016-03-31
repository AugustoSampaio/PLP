package plp.functional3.declaration;

import static plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.List;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Id;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.functional1.util.TipoFuncao;
import plp.functional1.util.TipoPolimorfico;
import plp.functional3.util.Padrao;
import plp.functional3.util.padrao.ExpPadrao;
import plp.functional3.util.padrao.ExpPadraoId;

public class DecPadrao {
	
	private Id idFuncao;
	private Padrao padrao;
	private Expressao expressao;
	private Expressao filtro;
	
	public DecPadrao(Id id, Padrao padrao, Expressao expressao) {
		this.idFuncao = id;
		this.padrao = padrao;
		this.expressao = expressao;
	}
	
	public DecPadrao(Id idFuncao, Padrao padrao, Expressao expressao, Expressao filtro) {
		this(idFuncao, padrao, expressao);
		this.filtro = filtro;
	}
	
	public DecPadrao(Id id, List<Expressao> listaExpressoes, Expressao expressao) {
		this.idFuncao = id;
		this.padrao = Padrao.createPadraoFrom(listaExpressoes);
		this.expressao = expressao;
	}
	
	public DecPadrao(Id id, List<Expressao> listaExpressoes, Expressao expressao,
			Expressao filtro) {
		this(id, listaExpressoes, expressao);
		this.filtro = filtro;
	}
	
	public Id getIdFuncao() {
		return this.idFuncao;
	}
	
	public void setIdFuncao(Id idFuncao) {
		this.idFuncao = idFuncao;
	}
	
	public Padrao getPadrao() {
		return this.padrao;
	}
	
	public Expressao getExpressao() {
		return this.expressao;
	}
	
	public Expressao getFiltro() {
		return this.filtro;
	}
	
	public int getNumeroExpressoes() {
		return this.padrao.getListaExpPadrao().size();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(String.format("%s . %s", listToString(
				this.getPadrao().getListaExpPadrao(), ""), this.getExpressao()));
		if ( this.filtro != null ) {
			result.append(", if ");
			result.append(this.filtro);
		}
		
		return result.toString();
	}
	
	@SuppressWarnings("unchecked")
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		
		for ( ExpPadrao expPadrao : this.padrao.getListaExpPadrao() ) {
			expPadrao.checaTipo(ambiente);
		}
		return this.expressao.checaTipo(ambiente);
	}
	
	@SuppressWarnings("unchecked")
	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		
		ambiente.incrementa();
		
		for ( ExpPadrao expPadrao : this.padrao.getListaExpPadrao() ) {
			expPadrao.checaTipo(ambiente);
		}
		
		this.expressao.checaTipo(ambiente);
		
		// Compõe o tipo desta função do resultado para o primeiro parâmetro.
		Tipo result = this.expressao.getTipo(ambiente);
		
		// Obtêm o tipo inferido de cada parâmetro.
		List<Tipo> params = new ArrayList<Tipo>(this.getNumeroExpressoes());
		for ( ExpPadrao expPadrao : this.padrao.getListaExpPadrao() ) {
			if ( expPadrao instanceof ExpPadraoId ) {
				Id id = (Id) expPadrao.getExpressao();
				Tipo argTipo = ((TipoPolimorfico) ambiente.get(id)).inferir();
				params.add(argTipo);
			}
			else {
				Tipo argTipo = expPadrao.getTipo(ambiente);
				params.add(argTipo);
			}
		}
		result = new TipoFuncao(params, result);
		
		ambiente.restaura();
		
		return result;
	}
	
	public DecPadrao clone(){		
		DecPadrao retorno = new DecPadrao(this.idFuncao.clone(), this.padrao.clone(), this.expressao.clone());
		retorno.filtro = this.filtro.clone();
		
		return retorno;
	}

	public DecPadrao reduzir(AmbienteExecucao ambiente) {
		// TODO Auto-generated method stub
		return null;
	}
}
