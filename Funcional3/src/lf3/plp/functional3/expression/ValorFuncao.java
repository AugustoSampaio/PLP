	package lf3.plp.functional3.expression;

import static lf3.plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.expression.Valor;
import lf3.plp.expressions2.expression.ValorBooleano;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional1.util.TipoFuncao;
import lf3.plp.functional1.util.TipoPolimorfico;
import lf3.plp.functional2.expression.ValorAbstrato;
import lf3.plp.functional3.declaration.DecPadrao;
import lf3.plp.functional3.exception.FuncaoNomeDiferenteException;
import lf3.plp.functional3.exception.NumeroParametrosException;
import lf3.plp.functional3.exception.PadraoNaoEncontradoException;
import lf3.plp.functional3.exception.TipoParametrosException;
import lf3.plp.functional3.exception.TipoRetornoPadraoException;
import lf3.plp.functional3.util.AmbienteUtil;
import lf3.plp.functional3.util.Padrao;
import lf3.plp.functional3.util.PartialInstantiatorVisitor;
import lf3.plp.functional3.util.padrao.ExpPadrao;

public class ValorFuncao implements ValorAbstrato {
	
	private List<DecPadrao> decPadroes;
	
	public ValorFuncao(List<DecPadrao> listaDecPadroes) {
		this.decPadroes = listaDecPadroes;
	}
	
	public List<DecPadrao> getDecPadroes() {
		return this.decPadroes;
	}
	
	public int getAridade() {
		return this.decPadroes.get(0).getNumeroExpressoes();
	}
	
	public DecPadrao getMatch(List<Expressao> expressoes, AmbienteExecucao ambiente) {
		for ( DecPadrao decPadrao : this.decPadroes ) {
			Padrao padrao = decPadrao.getPadrao();
			
			boolean match = padrao.match(ambiente, expressoes);
			match &= this.avaliarFiltro(ambiente, decPadrao, expressoes);
			
			if ( match ) {
				return decPadrao;
			}
		}
		throw new PadraoNaoEncontradoException();
	}
	
	private boolean avaliarFiltro(AmbienteExecucao ambiente, DecPadrao decPadrao,
			List<Expressao> padraoAplicacao) {
		if ( decPadrao.getFiltro() == null ) {
			return true;
		}
		
		Map<Id, Valor> mapIdValor = AmbienteUtil.resolveParametersBindings(ambiente,
				decPadrao, padraoAplicacao);
		ambiente.incrementa();
		AmbienteUtil.includeValueBindings(ambiente, mapIdValor);
		
		Valor valorFiltro = decPadrao.getFiltro().avaliar(ambiente);
		
		ambiente.restaura();
		return ((ValorBooleano) valorFiltro).valor();
	}
	
	public Valor avaliar(AmbienteExecucao ambiente) throws VariavelNaoDeclaradaException,
			VariavelJaDeclaradaException {
		
		Set<Id> variaveisLocais = Collections.unmodifiableSet(new HashSet<Id>());
		
		return (Valor) PartialInstantiatorVisitor.getInstance().visit(this, ambiente,
				variaveisLocais);
	}
	
	@Override
	public String toString() {
		return String.format("fn %s", listToString(this.decPadroes, "", "", " |"));
	}
	
	public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException,
			VariavelJaDeclaradaException {
		
		// Retorna o tipo do ValorFuncao atrav�s do tipo do ValorPadrao
		return this.decPadroes.get(0).getTipo(amb);
	}
	
	public boolean checaTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
			NumeroParametrosException {
		
		Id funcaoId = null;
		int numeroExpressoes = -1;
		Tipo tipoRetorno = null;
		List<Tipo> listaTipoCasamento = null;
		
		for ( DecPadrao decPadrao : this.decPadroes ) {
			amb.incrementa();
			
			// Checa o tipo de cada ValorPadrao
			decPadrao.checaTipo(amb);
			
			// Verifica se a func�o possui o mesmo nome em todos os padr�es
			funcaoId = this.verificaNomeFuncao(funcaoId, decPadrao);
			
			// Verifica se os padr�es tem a mesma quantidade de expressoes
			numeroExpressoes = this.verificaQuantidadeExpressoes(numeroExpressoes,
					decPadrao);
			
			// Verifica o tipo das express�es
			listaTipoCasamento = this.verificaTipoExpressoes(amb, listaTipoCasamento,
					decPadrao);
			
			// Adiciona o tipo do resultado da express�o valor do padr�o
			tipoRetorno = this.verificaTipoRetorno(amb, tipoRetorno, decPadrao);
			
			amb.restaura();
		}
		
		return true;
	}
	
	private Tipo verificaTipoRetorno(AmbienteCompilacao amb, Tipo tipoExpResultado,
			DecPadrao decPadrao) {
		if ( tipoExpResultado == null ) {
			tipoExpResultado = decPadrao.getExpressao().getTipo(amb);
		}
		else {
			this.comparaTipoRetorno(amb, tipoExpResultado, decPadrao);
		}
		return tipoExpResultado;
	}
	
	private void comparaTipoRetorno(AmbienteCompilacao amb, Tipo tipoExpResultado,
			DecPadrao decPadrao) {
		
		Tipo t = decPadrao.getExpressao().getTipo(amb);
		if ( tipoExpResultado.intersecao(t) == null ) {
			if ( !(t instanceof TipoFuncao) ) {
				throw new TipoRetornoPadraoException(decPadrao.getIdFuncao());
			}
		}
	}
	
	private int verificaQuantidadeExpressoes(int numeroExpressoes, DecPadrao decPadrao) {
		if ( numeroExpressoes == -1 ) {
			numeroExpressoes = decPadrao.getNumeroExpressoes();
		}
		else if ( !this.mesmoNumeroExpressoes(numeroExpressoes, decPadrao) ) {
			throw new NumeroParametrosException(decPadrao.getIdFuncao());
		}
		return numeroExpressoes;
	}
	
	private Id verificaNomeFuncao(Id funcaoId, DecPadrao decPadrao) {
		if ( funcaoId == null ) {
			funcaoId = decPadrao.getIdFuncao();
		}
		else if ( !this.mesmoNomeFuncao(funcaoId, decPadrao.getIdFuncao()) ) {
			throw new FuncaoNomeDiferenteException();
		}
		return funcaoId;
	}
	
	private List<Tipo> verificaTipoExpressoes(AmbienteCompilacao amb,
			List<Tipo> listaTipoCasamento, DecPadrao decPadrao) {
		
		if ( listaTipoCasamento == null ) {
			listaTipoCasamento = this.mapeiaTiposExpressoes(amb, decPadrao);
		}
		else {
			this.comparaTiposExpressoes(amb, listaTipoCasamento, decPadrao);
		}
		return listaTipoCasamento;
	}
	
	@SuppressWarnings("unchecked")
	private void comparaTiposExpressoes(AmbienteCompilacao amb,
			List<Tipo> listaTipoCasamento, DecPadrao decPadrao) {
		
		int contatorExpressao = 0;
		for ( ExpPadrao expPadrao : decPadrao.getPadrao().getListaExpPadrao() ) {
			Expressao exp = expPadrao.getExpressao();
			Tipo tipoCasamento = listaTipoCasamento.get(contatorExpressao);
			
			if ( !(exp instanceof Id) && tipoCasamento instanceof TipoPolimorfico ) {
				Tipo tipoExpressao = exp.getTipo(amb);
				
				if ( tipoCasamento.intersecao(tipoExpressao) == null ) {
					throw new TipoParametrosException(decPadrao.getIdFuncao());
				}
			}
			contatorExpressao++;
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Tipo> mapeiaTiposExpressoes(AmbienteCompilacao amb, DecPadrao decPadrao) {
		List<Tipo> listaTipoCasamento;
		listaTipoCasamento = new ArrayList<Tipo>();
		
		for ( ExpPadrao expPadrao : decPadrao.getPadrao().getListaExpPadrao() ) {
			Expressao exp = expPadrao.getExpressao();
			
			if ( exp instanceof Id ) {
				listaTipoCasamento.add(new TipoPolimorfico());
			}
			else {
				Tipo tipo = exp.getTipo(amb);
				listaTipoCasamento.add(tipo);
			}
		}
		return listaTipoCasamento;
	}
	
	private boolean mesmoNumeroExpressoes(int qtdPadraoCasamento, DecPadrao decPadrao) {
		if ( qtdPadraoCasamento != decPadrao.getPadrao().getListaExpPadrao().size() ) {
			return false;
		}
		return true;
	}
	
	private boolean mesmoNomeFuncao(Id oldId, Id id) {
		if ( id != null && id.getIdName().equals(oldId.getIdName()) ) {
			return true;
		}
		return false;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		List<DecPadrao> novaLista = new ArrayList<DecPadrao>(this.decPadroes.size());
		
		for (DecPadrao dec : this.decPadroes)
			novaLista.add(dec.reduzir(ambiente));
		
		this.decPadroes = novaLista;
		
		return this;
	}
	
	public ValorFuncao clone() {
		List<DecPadrao> novaLista = new ArrayList<DecPadrao>(this.decPadroes.size());
		
		for (DecPadrao pad : this.decPadroes){
			novaLista.add(pad.clone());
		}
		
		return null;
	}
}
