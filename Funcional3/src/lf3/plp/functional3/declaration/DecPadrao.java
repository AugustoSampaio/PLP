package lf3.plp.functional3.declaration;

import static lf3.plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.List;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional1.util.TipoFuncao;
import lf3.plp.functional1.util.TipoPolimorfico;
import lf3.plp.functional3.util.Padrao;
import lf3.plp.functional3.util.padrao.ExpPadrao;
import lf3.plp.functional3.util.padrao.ExpPadraoId;

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
		
		// Comp�e o tipo desta fun��o do resultado para o primeiro par�metro.
		Tipo result = this.expressao.getTipo(ambiente);
		
		// Obt�m o tipo inferido de cada par�metro.
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
