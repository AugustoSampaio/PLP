package lf2.plp.functional2.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lf2.plp.expressions2.expression.ExpAnd;
import lf2.plp.expressions2.expression.ExpConcat;
import lf2.plp.expressions2.expression.ExpEquals;
import lf2.plp.expressions2.expression.ExpLength;
import lf2.plp.expressions2.expression.ExpMenos;
import lf2.plp.expressions2.expression.ExpNot;
import lf2.plp.expressions2.expression.ExpOr;
import lf2.plp.expressions2.expression.ExpSoma;
import lf2.plp.expressions2.expression.ExpSub;
import lf2.plp.expressions2.expression.Expressao;
import lf2.plp.expressions2.expression.Id;
import lf2.plp.expressions2.expression.ValorBooleano;
import lf2.plp.expressions2.expression.ValorInteiro;
import lf2.plp.expressions2.expression.ValorString;
import lf2.plp.expressions2.memory.AmbienteExecucao;
import lf2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf2.plp.functional1.declaration.DecVariavel;
import lf2.plp.functional1.declaration.DeclaracaoFuncional;
import lf2.plp.functional1.expression.IfThenElse;
import lf2.plp.functional2.declaration.DecFuncao;
import lf2.plp.functional2.expression.Aplicacao;
import lf2.plp.functional2.expression.ExpDeclaracao;
import lf2.plp.functional2.expression.ValorFuncao;

/**
 * @author S�rgio
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class PartialInstantiatorVisitor {
	private Map<String, Method> metodosVisit;

	private static PartialInstantiatorVisitor instance = null;

	protected PartialInstantiatorVisitor() {
		metodosVisit = new HashMap<String, Method>();
		Method metodos[] = this.getClass().getMethods();
		for (Method method : metodos) {
			if (method.getName().startsWith("_visit")) {
				metodosVisit.put(method.getName(), method);
			}
		}
	}

	public static PartialInstantiatorVisitor getInstance() {
		if (instance == null) {
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
		methodName = "_visit"
				+ methodName.substring(methodName.lastIndexOf('.') + 1);

		Expressao result = null;
		// Now we try to invoke the method visit
		try {
			// Get the method visitFoo(Foo foo)
			Method m = getMethod(methodName);
			// o metodo visit existe
			// Try to invoke visitFoo(Foo foo)
			result = (Expressao) m.invoke(this, new Object[] { exp, ambiente,
					localVariables });
		} catch (IllegalAccessException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new IllegalStateException(
					"N�o foi poss�vel executar o m�todo (" + methodName
							+ "). IllegalAccessException");

		} catch (InvocationTargetException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			throw new IllegalStateException(
					"N�o foi poss�vel executar o m�todo (" + methodName
							+ "). InvocationTargetException");

		} catch (NoSuchMethodException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			throw new IllegalStateException("O m�todo visit chamado ("
					+ methodName + ") n�o foi implementado");
		}
		return result;
	}

	private Method getMethod(String name) throws NoSuchMethodException {
		Object method = metodosVisit.get(name);
		if (method == null)
			throw new NoSuchMethodException("O m�todo '" + name
					+ "' especificado n�o foi encontrado");
		return (Method) method;
	}

	public Expressao _visitAplicacao(Aplicacao expressao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		List<Expressao> novosValoresReais = new ArrayList<Expressao>(expressao
				.getArgsExpressao().size());
		Set<Id> novasVariaveisLocais = new HashSet<Id>(localVariables);

		Expressao func = expressao.getFunc();
		if (func instanceof Id)
			novasVariaveisLocais.add((Id) func);

		for (Expressao argReal : expressao.getArgsExpressao()) {
			Expressao novoArg = visit(argReal, ambiente, novasVariaveisLocais);
			novosValoresReais.add(novoArg);
		}
		
		Aplicacao resultado;
		try {
			resultado = new Aplicacao(func.avaliar(ambiente),
				novosValoresReais);

		} catch (VariavelNaoDeclaradaException e) {			
			resultado = expressao;
		}

		return resultado;
	}

	public Expressao _visitExpAnd(ExpAnd expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {

		Expressao esquerda = visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = visit(expressao.getDir(), ambiente, localVariables);
		ExpAnd resultado = new ExpAnd(esquerda, direita);
		return resultado;
	}

	public Expressao _visitExpConcat(ExpConcat expressao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		Expressao esquerda = visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = visit(expressao.getDir(), ambiente, localVariables);
		ExpConcat resultado = new ExpConcat(esquerda, direita);
		return resultado;
	}

//	public Expressao _visitExpDeclaracao(ExpDeclaracao expressao,
//			AmbienteExecucao ambiente, Set<Id> localVariables) {
//
//		// Adicionando as variaveis locais da declaracao funcional
//		Set<Id> novasVariaveisLocais = new HashSet<Id>(localVariables);
//		List<DeclaracaoFuncional> novaListaDeclaracao = new ArrayList<DeclaracaoFuncional>(
//				expressao.getSeqdecFuncional().size());
//		for (Object element : expressao.getSeqdecFuncional()) {
//			DeclaracaoFuncional declaracao = (DeclaracaoFuncional) element;
//			if (declaracao instanceof DecFuncao) {
//				DecFuncao novaDec = visitDecFuncao((DecFuncao) declaracao,
//						ambiente, localVariables);
//				novaListaDeclaracao.add(novaDec);
//			} else if (declaracao instanceof DecVariavel) {
//				DecVariavel novaDec = visitDecVariavel(
//						(DecVariavel) declaracao, ambiente, localVariables);
//				novaListaDeclaracao.add(novaDec);
//			} else
//				throw new IllegalStateException(
//						"DeclaracaoFuncional desconhecida em PartialInstantiatorVisitor");
//			Id idAtual = declaracao.getId();
//			novasVariaveisLocais.add(idAtual);
//		}
//
//		Expressao novaExpressao = visit(expressao.getExpressao(), ambiente,
//				novasVariaveisLocais);
//		ExpDeclaracao resultado = new ExpDeclaracao(novaListaDeclaracao,
//				novaExpressao);
//		return resultado;
//	}

	private DecFuncao visitDecFuncao(DecFuncao declaracao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		Set<Id> novasVariaveisLocais = new HashSet<Id>(localVariables);
		novasVariaveisLocais.add(declaracao.getId());
		ValorFuncao novaExpressao = (ValorFuncao) _visitValorFuncao(declaracao
				.getFuncao(), ambiente, novasVariaveisLocais);
		DecFuncao resultado = new DecFuncao(declaracao.getId(), novaExpressao);
		return resultado;
	}

	private DecVariavel visitDecVariavel(DecVariavel declaracao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		Set<Id> novasVariaveisLocais = new HashSet<Id>(localVariables);
		novasVariaveisLocais.add(declaracao.getId());
		Expressao novaExpressao = visit(declaracao.getExpressao(), ambiente,
				novasVariaveisLocais);
		DecVariavel resultado = new DecVariavel(declaracao.getId(),
				novaExpressao);
		return resultado;
	}

	public Expressao _visitExpEquals(ExpEquals expressao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		Expressao esquerda = visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = visit(expressao.getDir(), ambiente, localVariables);
		ExpEquals resultado = new ExpEquals(esquerda, direita);
		return resultado;
	}

	public Expressao _visitExpLength(ExpLength expressao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {

		Expressao expInterna = visit(expressao.getExp(), ambiente,
				localVariables);
		ExpLength resultado = new ExpLength(expInterna);
		return resultado;
	}

	public Expressao _visitExpMenos(ExpMenos expressao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		Expressao expInterna = visit(expressao.getExp(), ambiente,
				localVariables);
		ExpMenos resultado = new ExpMenos(expInterna);
		return resultado;
	}

	public Expressao _visitExpNot(ExpNot expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao expInterna = visit(expressao.getExp(), ambiente,
				localVariables);
		ExpNot resultado = new ExpNot(expInterna);
		return resultado;
	}

	public Expressao _visitExpOr(ExpOr expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao esquerda = visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = visit(expressao.getDir(), ambiente, localVariables);
		ExpOr resultado = new ExpOr(esquerda, direita);
		return resultado;
	}

	public Expressao _visitExpSoma(ExpSoma expressao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		Expressao esquerda = visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = visit(expressao.getDir(), ambiente, localVariables);
	
		
		
		ExpSoma resultado = new ExpSoma(esquerda, direita);
		return resultado;
	}

	public Expressao _visitExpSub(ExpSub expressao, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao esquerda = visit(expressao.getEsq(), ambiente, localVariables);
		Expressao direita = visit(expressao.getDir(), ambiente, localVariables);
		ExpSub resultado = new ExpSub(esquerda, direita);
		return resultado;
	}

	public Expressao _visitIfThenElse(IfThenElse expressao,
			AmbienteExecucao ambiente, Set<Id> localVariables) {

		Expressao condicao = visit(expressao.getCondicao(), ambiente,
				localVariables);
		Expressao then = visit(expressao.getThen(), ambiente, localVariables);
		Expressao elseExpressao = visit(expressao.getElseExpressao(), ambiente,
				localVariables);
		IfThenElse resultado = new IfThenElse(condicao, then, elseExpressao);
		return resultado;
	}

	public Expressao _visitId(Id thisId, AmbienteExecucao ambiente,
			Set<Id> localVariables) {
		Expressao resultado;
		if (localVariables.contains(thisId)) {
			resultado = thisId;
		} else {
			try {
				resultado = thisId.avaliar(ambiente);
			} catch (VariavelNaoDeclaradaException e) {
				resultado = thisId;
			}
		}
		return resultado;
	}

	public Expressao _visitValorInteiro(ValorInteiro valor,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		return valor;
	}

	public Expressao _visitValorString(ValorString valor,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		return valor;
	}

	public Expressao _visitValorBooleano(ValorBooleano valor,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		return valor;
	}

	// TODO extender o visitor para incluir isso aqui??
	//	public Expressao _visitValorLista(ValorLista valor,
	//			AmbienteExecucao ambiente, Set<Id> localVariables) {
	//		Expressao novoHead = visit(valor.getHead(), ambiente, localVariables);
	//		ValorLista novoTail = (ValorLista) visit(valor.getTail(), ambiente,
	//				localVariables);
	//		return ValorLista.getInstancia(novoHead, novoTail);
	//	}

	public Expressao _visitValorFuncao(ValorFuncao valor,
			AmbienteExecucao ambiente, Set<Id> localVariables) {
		Set<Id> novasVariaveisLocais = new HashSet<Id>(localVariables);
		novasVariaveisLocais.addAll(valor.getListaId());
		Expressao novaExpressao = visit(valor.getExp(), ambiente,
				novasVariaveisLocais);
		
		List<Id> listaId = valor.getListaId();
		if (novaExpressao instanceof ValorFuncao){
			List<Id> listaIdValor = ((ValorFuncao) novaExpressao).getListaId();
			listaId.addAll(listaIdValor);
		}
		
		ValorFuncao resultado = new ValorFuncao(listaId, novaExpressao);
		return resultado;
	}

}
