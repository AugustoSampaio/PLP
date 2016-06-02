package lf3.plp.functional3.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lf3.plp.expressions2.expression.ExpAnd;
import lf3.plp.expressions2.expression.ExpConcat;
import lf3.plp.expressions2.expression.ExpEquals;
import lf3.plp.expressions2.expression.ExpLength;
import lf3.plp.expressions2.expression.ExpMenos;
import lf3.plp.expressions2.expression.ExpNot;
import lf3.plp.expressions2.expression.ExpOr;
import lf3.plp.expressions2.expression.ExpSoma;
import lf3.plp.expressions2.expression.ExpSub;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.expression.ValorBooleano;
import lf3.plp.expressions2.expression.ValorInteiro;
import lf3.plp.expressions2.expression.ValorString;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional1.declaration.DecVariavel;
import lf3.plp.functional1.declaration.DeclaracaoFuncional;
import lf3.plp.functional1.expression.IfThenElse;
import lf3.plp.functional3.declaration.DecPadrao;
import lf3.plp.functional3.expression.Aplicacao;
import lf3.plp.functional3.expression.ExpMaiorQue;
import lf3.plp.functional3.expression.ExpMenorQue;
import lf3.plp.functional3.expression.ExpMult;
import lf3.plp.functional3.expression.ValorFuncao;

/**
 * @author S�rgio
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class PartialInstantiatorVisitor {
	private Map<String, Method> metodosVisit;
	
	private static PartialInstantiatorVisitor instance = null;
	
	protected PartialInstantiatorVisitor() {
		this.metodosVisit = new HashMap<String, Method>();
		Method metodos[] = this.getClass().getMethods();
		for ( Method method : metodos ) {
			if ( method.getName().startsWith("_visit") ) {
				this.metodosVisit.put(method.getName(), method);
			}
		}
	}
	
	public static PartialInstantiatorVisitor getInstance() {
		if ( instance == null ) {
			instance = new PartialInstantiatorVisitor();
		}
		return instance;
	}
	
	public Expressao visit(Expressao exp, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		
		// Class.getName() returns package information as well.
		// This strips off the package information giving us
		// just the class name
		String methodName = exp.getClass().getName();
		methodName = "_visit" + methodName.substring(methodName.lastIndexOf('.') + 1);
		
		Expressao result = null;
		// Now we try to invoke the method visit
		try {
			// Get the method visitFoo(Foo foo)
			Method m = this.getMethod(methodName);
			// o metodo visit existe
			// Try to invoke visitFoo(Foo foo)
			result = (Expressao) m.invoke(this, new Object[] { exp, ambiente,
					localVariables });
		}
		catch ( IllegalAccessException e ) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new IllegalStateException("N�o foi poss�vel executar o m�todo ("
					+ methodName + "). IllegalAccessException");
			
		}
		catch ( InvocationTargetException e ) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			throw new IllegalStateException(e.getTargetException().getMessage());
			
		}
		catch ( NoSuchMethodException e ) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			throw new IllegalStateException("O m�todo visit chamado (" + methodName
					+ ") n�o foi implementado");
		}
		
		return result;
	}
	
	private Method getMethod(String name) throws NoSuchMethodException {
		Object method = this.metodosVisit.get(name);
		if ( method == null ) {
			throw new NoSuchMethodException("O m�todo '" + name
					+ "' especificado n�o foi encontrado");
		}
		return (Method) method;
	}
	
	public Expressao _visitAplicacao(Aplicacao aplicacao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		
		List<Expressao> novosValoresReais = new ArrayList<Expressao>();
		Set<Id> novasVariaveisLocais = new HashSet<Id>(localVariables);
		
		Expressao func = aplicacao.getFuncao();
		if ( func instanceof Id ) {
			novasVariaveisLocais.add((Id) func);
		}
		
		for ( Expressao argReal : aplicacao.getArgumentos() ) {
			Expressao novoArg = this.visit(argReal, ambiente, novasVariaveisLocais);
			
			novosValoresReais.add(novoArg);
		}
		Aplicacao resultado = new Aplicacao(func.avaliar(ambiente), novosValoresReais);
		
		return resultado;
	}
	
	/*public Expressao _visitExpDeclaracao(ExpDeclaracao expressao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		
		// Adicionando as variaveis locais da declaracao funcional
		Set<Id> novasVariaveisLocais = new HashSet<Id>(localVariables);
		List<DeclaracaoFuncional> novaListaDeclaracao = new ArrayList<DeclaracaoFuncional>(
				expressao.getSeqdecFuncional().size());
		for ( Object element : expressao.getSeqdecFuncional() ) {
			DeclaracaoFuncional declaracao = (DeclaracaoFuncional) element;
			if ( declaracao instanceof DecFuncao ) {
				DecFuncao novaDec = this.visitDecFuncao((DecFuncao) declaracao, ambiente,
						localVariables);
				novaListaDeclaracao.add(novaDec);
			}
			else if ( declaracao instanceof DecVariavel ) {
				DecVariavel novaDec = this.visitDecVariavel((DecVariavel) declaracao,
						ambiente, localVariables);
				novaListaDeclaracao.add(novaDec);
			}
			else {
				throw new IllegalStateException(
						"DeclaracaoFuncional desconhecida em PartialInstantiatorVisitor");
			}
			Id idAtual = declaracao.getId();
			novasVariaveisLocais.add(idAtual);
		}
		
		Expressao novaExpressao = this.visit(expressao.getExpressao(), ambiente,
				novasVariaveisLocais);
		ExpDeclaracao resultado = new ExpDeclaracao(novaListaDeclaracao, novaExpressao);
		return resultado;
	}
	
	private DecFuncao visitDecFuncao(DecFuncao declaracao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Set<Id> novasVariaveisLocais = new HashSet<Id>(localVariables);
		novasVariaveisLocais.add(declaracao.getId());
		List<DecPadrao> listDecPadroes = declaracao.getDecPadroes();
		List<DecPadrao> visited = new ArrayList<DecPadrao>();
		
		for ( DecPadrao dp : listDecPadroes ) {
			DecPadrao decPadrao = this._visitDecPadrao(dp, ambiente, localVariables);
			
			visited.add(decPadrao);
		}
		
		return new DecFuncao(visited);
	}*/
	
	private DecPadrao _visitDecPadrao(DecPadrao decPadrao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		
		Set<Id> novasVariaveisLocais = new HashSet<Id>(localVariables);
		novasVariaveisLocais.addAll(decPadrao.getPadrao().getListaIds());
		
		Expressao novaExpressao = this.visit(decPadrao.getExpressao(), ambiente,
				novasVariaveisLocais);
		Expressao filtro = decPadrao.getFiltro();
		
		if ( filtro != null ) {
			filtro = this.visit(filtro, ambiente, localVariables);
		}
		DecPadrao resultado = new DecPadrao(decPadrao.getIdFuncao(), decPadrao
				.getPadrao(), novaExpressao, filtro);
		
		return resultado;
	}
	
	private DecVariavel visitDecVariavel(DecVariavel declaracao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		Set<Id> novasVariaveisLocais = new HashSet<Id>(localVariables);
		novasVariaveisLocais.add(declaracao.getId());
		Expressao novaExpressao = this.visit(declaracao.getExpressao(), ambiente,
				novasVariaveisLocais);
		DecVariavel resultado = new DecVariavel(declaracao.getId(), novaExpressao);
		return resultado;
	}
	
	public Expressao _visitExpLength(ExpLength expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		
		Expressao expInterna = this.visit(expressao.getExp(), ambiente, localVariables);
		ExpLength resultado = new ExpLength(expInterna);
		return resultado;
	}
	
	public Expressao _visitExpMenos(ExpMenos expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao expInterna = this.visit(expressao.getExp(), ambiente, localVariables);
		ExpMenos resultado = new ExpMenos(expInterna);
		return resultado;
	}
	
	public Expressao _visitExpNot(ExpNot expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao expInterna = this.visit(expressao.getExp(), ambiente, localVariables);
		ExpNot resultado = new ExpNot(expInterna);
		return resultado;
	}
	
	public Expressao _visitExpSoma(ExpSoma expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao esquerda = this.visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = this.visit(expressao.getDir(), ambiente, localVariables);
		ExpSoma resultado = new ExpSoma(esquerda, direita);
		return resultado;
	}
	
	public Expressao _visitExpSub(ExpSub expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao esquerda = this.visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = this.visit(expressao.getDir(), ambiente, localVariables);
		ExpSub resultado = new ExpSub(esquerda, direita);
		return resultado;
	}
	
	public Expressao _visitExpMult(ExpMult expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao esquerda = this.visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = this.visit(expressao.getDir(), ambiente, localVariables);
		ExpMult resultado = new ExpMult(esquerda, direita);
		return resultado;
	}
	
	public Expressao _visitExpMaiorQue(ExpMaiorQue expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao esquerda = this.visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = this.visit(expressao.getDir(), ambiente, localVariables);
		ExpMaiorQue resultado = new ExpMaiorQue(esquerda, direita);
		return resultado;
	}
	
	public Expressao _visitExpMenorQue(ExpMenorQue expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao esquerda = this.visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = this.visit(expressao.getDir(), ambiente, localVariables);
		ExpMenorQue resultado = new ExpMenorQue(esquerda, direita);
		return resultado;
	}
	
	public Expressao _visitExpAnd(ExpAnd expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		
		Expressao esquerda = this.visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = this.visit(expressao.getDir(), ambiente, localVariables);
		ExpAnd resultado = new ExpAnd(esquerda, direita);
		return resultado;
	}
	
	public Expressao _visitExpOr(ExpOr expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao esquerda = this.visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = this.visit(expressao.getDir(), ambiente, localVariables);
		ExpOr resultado = new ExpOr(esquerda, direita);
		return resultado;
	}
	
	public Expressao _visitExpEquals(ExpEquals expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao esquerda = this.visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = this.visit(expressao.getDir(), ambiente, localVariables);
		ExpEquals resultado = new ExpEquals(esquerda, direita);
		return resultado;
	}
	
	public Expressao _visitExpConcat(ExpConcat expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao esquerda = this.visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = this.visit(expressao.getDir(), ambiente, localVariables);
		ExpConcat resultado = new ExpConcat(esquerda, direita);
		return resultado;
	}
	
	public Expressao _visitIfThenElse(IfThenElse expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		
		Expressao condicao = this
				.visit(expressao.getCondicao(), ambiente, localVariables);
		Expressao then = this.visit(expressao.getThen(), ambiente, localVariables);
		Expressao elseExpressao = this.visit(expressao.getElseExpressao(), ambiente,
				localVariables);
		IfThenElse resultado = new IfThenElse(condicao, then, elseExpressao);
		return resultado;
	}
	
	public Expressao _visitId(Id thisId, AmbienteExecucao ambiente, Set<Id> localVariables) {
		Expressao resultado;
		if ( localVariables.contains(thisId) ) {
			resultado = thisId;
		}
		else {
			try {
				resultado = thisId.avaliar(ambiente);
			}
			catch ( VariavelNaoDeclaradaException e ) {
				resultado = thisId;
			}
		}
		return resultado;
	}
	
	public Expressao _visitValorInteiro(ValorInteiro valor, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		return valor;
	}
	
	public Expressao _visitValorString(ValorString valor, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		return valor;
	}
	
	public Expressao _visitValorBooleano(ValorBooleano valor, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		return valor;
	}
	
	// public Expressao _visitDecPadrao(DecPadrao decPadrao, AmbienteExecucao
	// ambiente,
	// Set<Id> localVariables) {
	// Set<Id> novasVariaveisLocais = new HashSet<Id>(localVariables);
	// novasVariaveisLocais.addAll(decPadrao.getListaId());
	// Expressao novaExpressao = this.visit(decPadrao.getExpressao(), ambiente,
	// novasVariaveisLocais);
	//		
	// Expressao filtro = decPadrao.getFiltro();
	//		
	// if ( filtro != null ) {
	// filtro = this.visit(filtro, ambiente, localVariables);
	// }
	// DecPadrao resultado = new DecPadrao(decPadrao.getIdFuncao(), decPadrao
	// .getPadrao().getListaExpressoes(), novaExpressao, filtro);
	//		
	// return resultado;
	// }
	
	public Expressao _visitValorFuncao(ValorFuncao valorFuncao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		
		List<DecPadrao> listDecPadroes = valorFuncao.getDecPadroes();
		List<DecPadrao> listResult = new ArrayList<DecPadrao>();
		
		for ( DecPadrao decPadrao : listDecPadroes ) {
			
			Set<Id> variaveisLocais = Collections.unmodifiableSet(new HashSet<Id>(
					decPadrao.getPadrao().getListaIds()));
			
			DecPadrao valorResult = this._visitDecPadrao(decPadrao, ambiente,
					variaveisLocais);
			listResult.add(valorResult);
		}
		
		return new ValorFuncao(listResult);
		
	}
	
}
