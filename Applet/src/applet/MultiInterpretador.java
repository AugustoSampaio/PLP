package applet;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JTextArea;

import le1.plp.expressions1.parser.Exp1Parser;
import le1.plp.expressions1.parser.ParseException;
//import le2.plp.expressions2.expression.ValorBooleano;
//import le2.plp.expressions2.expression.ValorInteiro;
//import le2.plp.expressions2.expression.ValorString;
import le2.plp.expressions2.parser.Exp2Parser;
import lf1.plp.functional1.parser.Func1Parser;
import lf2.plp.functional2.parser.Func2Parser;
import lf3.plp.functional3.parser.Func3Parser;
import li1.plp.imperative1.memory.ContextoCompilacaoImperativa;
import li1.plp.imperative1.memory.ContextoExecucaoImperativa;
//import li1.plp.imperative1.memory.ListaValor;
import li1.plp.imperative1.parser.Imp1Parser;
import li2.plp.imperative2.memory.ContextoExecucaoImperativa2;
import li2.plp.imperative2.parser.Imp2Parser;
//import loo1.plp.orientadaObjetos1.expressao.valor.ValorConcreto;
import loo1.plp.orientadaObjetos1.parser.OO1Parser;
import loo2.plp.orientadaObjetos2.parser.OO2Parser;

public class MultiInterpretador {

	private static final int EXP1 = 0;

	private static final int EXP2 = 1;

	private static final int FUNC1 = 2;

	private static final int FUNC2 = 3;

	private static final int FUNC3 = 4;

	private static final int IMP1 = 5;

	private static final int IMP2 = 6;

	private static final int OO1 = 7;

	private static final int OO2 = 8;

	private JTextArea messageBoard;

	private Exp1Parser exp1Parser = null;
	private Exp2Parser exp2Parser = null;
	private Func1Parser func1Parser = null;
	private Func2Parser func2Parser = null;
	private Func3Parser func3Parser = null;
	private Imp1Parser imp1Parser = null;
	private Imp2Parser imp2Parser = null;
	private OO1Parser oo1Parser = null;
	private OO2Parser oo2Parser = null;

	public MultiInterpretador(JTextArea textAreaMensagens) {
		super();
		messageBoard = textAreaMensagens;
	}

	public void interpretarCodigo(String sourceCode, String listaEntrada,
			int selectedIndex) {
		try {
			ByteArrayInputStream fis = new ByteArrayInputStream(sourceCode
					.getBytes());

			switch (selectedIndex) {
			case EXP1:
				interpretarExp1(fis);
				break;
			case EXP2:
				interpretarExp2(fis);
				break;
			case FUNC1:
				interpretarFunc1(fis);
				break;
			case FUNC2:
				interpretarFunc2(fis);
				break;
			case FUNC3:
				interpretarFunc3(fis);
				break;
			case IMP1:
				interpretarImp1(fis, listaEntrada);
				break;
			case IMP2:
				interpretarImp2(fis, listaEntrada);
				break;
			case OO1:
				interpretarOO1(fis, listaEntrada);
				break;
			case OO2:
				interpretarOO2(fis, listaEntrada);
			}
		} catch (Exception e1) {
			messageBoard.setText(e1.getMessage());
			e1.printStackTrace();
		} catch (Throwable t) {
			messageBoard.setText(t.getMessage());
			t.printStackTrace();
		}

	}

	private void interpretarExp1(InputStream fis) throws ParseException {
		le1.plp.expressions1.Programa prog;
		if (exp1Parser == null) {
			exp1Parser = new Exp1Parser(fis);
		} else {
			Exp1Parser.ReInit(fis);
		}
		prog = Exp1Parser.Input();
		messageBoard.setText("sintaxe verificada com sucesso!\n");
		if (prog.checaTipo()) {
			messageBoard.append("resultado = " + prog.executar().toString());
		} else {
			messageBoard.append("erro de tipos!");
		}
	}

	private void interpretarExp2(InputStream fis) throws Exception {
		le2.plp.expressions2.Programa prog;
		if (exp2Parser == null) {
			exp2Parser = new Exp2Parser(fis);
		} else {
			Exp2Parser.ReInit(fis);
		}
		prog = Exp2Parser.Input();

		messageBoard.setText("sintaxe verificada com sucesso!\n");
		if (prog.checaTipo()) {
			messageBoard.append("resultado = " + prog.executar().toString());
		} else {
			messageBoard.append("erro de tipos!");
		}
	}

	private void interpretarFunc1(InputStream fis) throws Exception {
		lf1.plp.functional1.Programa prog;
		if (func1Parser == null) {
			func1Parser = new Func1Parser(fis);
		} else {
			Func1Parser.ReInit(fis);
		}
		prog = Func1Parser.Input();

		messageBoard.setText("sintaxe verificada com sucesso!\n");
		if (prog.checaTipo()) {
			messageBoard.append("resultado = " + prog.executar().toString());
		} else {
			messageBoard.append("erro de tipos!");
		}
	}

	private void interpretarFunc2(InputStream fis) throws Exception {
		lf2.plp.functional2.Programa prog;
		if (func2Parser == null) {
			func2Parser = new Func2Parser(fis);
		} else {
			Func2Parser.ReInit(fis);
		}

		prog = Func2Parser.Input();

		messageBoard.setText("sintaxe verificada com sucesso!\n");
		messageBoard.append("resultado = " + prog.executar().toString());
	}

	private void interpretarFunc3(InputStream fis) throws Exception {
		lf3.plp.functional3.Programa prog;
		if (func3Parser == null) {
			func3Parser = new Func3Parser(fis);
		} else {
			Func3Parser.ReInit(fis);
		}

		prog = Func3Parser.Input();

		messageBoard.setText("sintaxe verificada com sucesso!\n");
		messageBoard.append("resultado = " + prog.executar().toString());
	}

	private void interpretarImp1(InputStream fis, String entradaStr)
			throws Exception {
		li1.plp.imperative1.Programa prog;
		if (imp1Parser == null) {
			imp1Parser = new Imp1Parser(fis);
		} else {
			Imp1Parser.ReInit(fis);
		}
		prog = Imp1Parser.Input();

		messageBoard.setText("sintaxe verificada com sucesso!\n");
		li1.plp.imperative1.memory.ListaValor entrada = obterListaEntradaImp1(entradaStr);
		if (prog.checaTipo(new ContextoCompilacaoImperativa(entrada))) {
			messageBoard.append("resultado = "
					+ prog.executar(new ContextoExecucaoImperativa(entrada))
							.toString());
		} else {
			messageBoard.append("erro de tipos!");
		}
	}

	private void interpretarImp2(InputStream fis, String entradaStr)
			throws Exception {
		li2.plp.imperative2.Programa prog;
		if (imp2Parser == null) {
			imp2Parser = new Imp2Parser(fis);
		} else {
			Imp2Parser.ReInit(fis);
		}

		prog = Imp2Parser.Input();

		messageBoard.setText("sintaxe verificada com sucesso!\n");
		li2.plp.imperative1.memory.ListaValor entrada = obterListaEntradaImp2(entradaStr);
		if (prog.checaTipo(new li2.plp.imperative1.memory.ContextoCompilacaoImperativa(entrada))) {
			messageBoard.append("resultado = "
					+ prog.executar(new li2.plp.imperative2.memory.ContextoExecucaoImperativa2(entrada))
							.toString());
		} else {
			messageBoard.append("erro de tipos!");
		}
	}

	private void interpretarOO1(InputStream fis, String entradaStr)
			throws Exception {
		loo1.plp.orientadaObjetos1.Programa prog;
		if (oo1Parser == null) {
			this.oo1Parser = new OO1Parser(fis);
		} else {
			oo1Parser.ReInit(fis);
		}
		prog = oo1Parser.processaEntrada();

		messageBoard.setText("sintaxe verificada com sucesso!\n");
		loo1.plp.orientadaObjetos1.memoria.colecao.ListaValor entrada = obterListaEntradaOO1(entradaStr);
		if (prog
				.checaTipo(new loo1.plp.orientadaObjetos1.memoria.ContextoCompilacaoOO1(
						entrada))) {
			messageBoard
					.append("resultado = "
							+ prog
									.executar(
											new loo1.plp.orientadaObjetos1.memoria.ContextoExecucaoOO1(
													entrada)).toString());
		} else {
			messageBoard.append("erro de tipos!");
		}
	}
	
	private void interpretarOO2(InputStream fis, String entradaStr) throws Exception {
		loo2.plp.orientadaObjetos2.Programa prog;
		if (oo2Parser == null) {
			this.oo2Parser = new OO2Parser(fis);
		} else {
			oo2Parser.ReInit(fis);
		}
		prog = oo2Parser.processaEntrada();

		messageBoard.setText("sintaxe verificada com sucesso!\n");
		loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor entrada = obterListaEntradaOO2(entradaStr);
		if (prog.checaTipo(new loo2.plp.orientadaObjetos2.memoria.ContextoCompilacaoOO2(
				entrada))) {
			messageBoard
					.append("resultado = "
							+ prog.executar(
									new loo2.plp.orientadaObjetos2.memoria.ContextoExecucaoOO2(
											entrada)).toString());
		} else {
			messageBoard.append("erro de tipos!");
		}
	}

	@SuppressWarnings("unchecked")
	private li1.plp.imperative1.memory.ListaValor obterListaEntradaImp1(String texto) {
		@SuppressWarnings("rawtypes")
		List valores = new LinkedList<li1.plp.expressions2.expression.ValorConcreto>();
		li1.plp.imperative1.memory.ListaValor entrada = new li1.plp.imperative1.memory.ListaValor();
		StringTokenizer parser = new StringTokenizer(texto);

		while (parser.hasMoreTokens()) {
			String parametro = parser.nextToken();
			
			try {
				Integer inteiro = Integer.valueOf(parametro);
				valores.add(new li1.plp.expressions2.expression.ValorInteiro(inteiro.intValue()));
				continue;
			} catch (NumberFormatException e) {

			}

			if (parametro.equalsIgnoreCase("true")
					|| parametro.equalsIgnoreCase("false")) {
				Boolean booleano = Boolean.valueOf(parametro);
				valores.add(new li1.plp.expressions2.expression.ValorBooleano(booleano.booleanValue()));
			} else {
				valores.add(new li1.plp.expressions2.expression.ValorString(parametro));
			}
		}
		entrada = Imp1Parser.criaListaValor(valores);
		return entrada;
	}
	
	@SuppressWarnings("unchecked")
	private li2.plp.imperative1.memory.ListaValor obterListaEntradaImp2(String texto) {
		@SuppressWarnings("rawtypes")
		List valores = new LinkedList<li2.plp.expressions2.expression.ValorConcreto>();
		li2.plp.imperative1.memory.ListaValor entrada = new li2.plp.imperative1.memory.ListaValor();
		StringTokenizer parser = new StringTokenizer(texto);

		while (parser.hasMoreTokens()) {
			String parametro = parser.nextToken();
			
			try {
				Integer inteiro = Integer.valueOf(parametro);
				valores.add(new li1.plp.expressions2.expression.ValorInteiro(inteiro.intValue()));
				continue;
			} catch (NumberFormatException e) {

			}

			if (parametro.equalsIgnoreCase("true")
					|| parametro.equalsIgnoreCase("false")) {
				Boolean booleano = Boolean.valueOf(parametro);
				valores.add(new li2.plp.expressions2.expression.ValorBooleano(booleano.booleanValue()));
			} else {
				valores.add(new li2.plp.expressions2.expression.ValorString(parametro));
			}
		}
		entrada = Imp2Parser.criaListaValor(valores);
		return entrada;
	}


	@SuppressWarnings("unchecked")
	private loo1.plp.orientadaObjetos1.memoria.colecao.ListaValor obterListaEntradaOO1(
			String texto) {
		List valores = new LinkedList<loo1.plp.expressions2.expression.ValorConcreto>();
		loo1.plp.orientadaObjetos1.memoria.colecao.ListaValor entrada = new loo1.plp.orientadaObjetos1.memoria.colecao.ListaValor();
		StringTokenizer parser = new StringTokenizer(texto);

		while (parser.hasMoreTokens()) {
			String parametro = parser.nextToken();

			try {
				Integer inteiro = Integer.valueOf(parametro);
				valores
						.add(new loo2.plp.orientadaObjetos1.expressao.valor.ValorInteiro(
								inteiro.intValue()));
				continue;
			} catch (NumberFormatException e) {

			}

			if (parametro.equalsIgnoreCase("true")
					|| parametro.equalsIgnoreCase("false")) {
				Boolean booleano = Boolean.valueOf(parametro);
				valores
						.add(new loo1.plp.orientadaObjetos1.expressao.valor.ValorBooleano(
								booleano.booleanValue()));
			} else {
				valores
						.add(new loo1.plp.orientadaObjetos1.expressao.valor.ValorString(
								parametro));
			}
		}
		entrada = OO1Parser.criaListaValor(valores);
		return entrada;
	}

	@SuppressWarnings("unchecked")
	private loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor obterListaEntradaOO2(
			String texto) {
		List valores = new LinkedList<loo2.plp.expressions2.expression.ValorConcreto>();
		loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor entrada = new loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor();
		StringTokenizer parser = new StringTokenizer(texto);

		while (parser.hasMoreTokens()) {
			String parametro = parser.nextToken();

			try {
				Integer inteiro = Integer.valueOf(parametro);
				valores
						.add(new loo2.plp.orientadaObjetos1.expressao.valor.ValorInteiro(
								inteiro.intValue()));
				continue;
			} catch (NumberFormatException e) {

			}

			if (parametro.equalsIgnoreCase("true")
					|| parametro.equalsIgnoreCase("false")) {
				Boolean booleano = Boolean.valueOf(parametro);
				valores
						.add(new loo2.plp.orientadaObjetos1.expressao.valor.ValorBooleano(
								booleano.booleanValue()));
			} else {
				valores
						.add(new loo2.plp.orientadaObjetos1.expressao.valor.ValorString(
								parametro));
			}
		}
		entrada = OO2Parser.criaListaValor(valores);
		return entrada;
	}
}