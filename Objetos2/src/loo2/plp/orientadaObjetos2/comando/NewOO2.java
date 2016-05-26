package loo2.plp.orientadaObjetos2.comando;

import java.util.HashMap;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.comando.ChamadaProcedimento;
import loo2.plp.orientadaObjetos1.comando.New;
import loo2.plp.orientadaObjetos1.comando.Procedimento;
import loo2.plp.orientadaObjetos1.declaracao.variavel.DecVariavel;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import loo2.plp.orientadaObjetos1.expressao.ListaExpressao;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.LeftExpression;
import loo2.plp.orientadaObjetos1.expressao.valor.Valor;
import loo2.plp.orientadaObjetos1.expressao.valor.ValorRef;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.memoria.ContextoExecucaoOO1;
import loo2.plp.orientadaObjetos1.memoria.Objeto;
import loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor;
import loo2.plp.orientadaObjetos2.memoria.AmbienteExecucaoOO2;
import loo2.plp.orientadaObjetos2.memoria.ContextoExecucaoOO2;
import loo2.plp.orientadaObjetos2.memoria.DefClasseOO2;

public class NewOO2 extends New {

	private ListaExpressao parametrosReais;

	public NewOO2(LeftExpression av, Id classe, ListaExpressao parametrosReais) {
		super(av, classe);
		this.parametrosReais = parametrosReais;
	}
	
	private void extendsClasse(AmbienteExecucaoOO2 ambiente, DefClasseOO2 classe, Objeto objeto) throws ClasseNaoDeclaradaException, 
				VariavelNaoDeclaradaException, VariavelJaDeclaradaException, ObjetoNaoDeclaradoException, 
				ClasseJaDeclaradaException, ObjetoJaDeclaradoException {
		if (classe.getNomeSuperClasse() != null) {
			DefClasseOO2 classeMae = (DefClasseOO2) ambiente.getDefClasse(classe.getNomeSuperClasse());
			this.extendsClasse(ambiente, classeMae,objeto);
		}
		
		DecVariavel decVariavel = classe.getDecVariavel();
		AmbienteExecucaoOO1 aux = decVariavel.elabora(new ContextoExecucaoOO1(ambiente));
		HashMap<loo2.plp.expressions2.expression.Id, Valor> estadoObj = aux.getPilha().pop();
		
		for (loo2.plp.expressions2.expression.Id id : estadoObj.keySet()) {
			// Se a variavel nao havia sido declarada adicione
			if (!objeto.getEstado().containsKey(id) ) {
				objeto.getEstado().put(id, estadoObj.get(id));	
			}
		}
	}


	public AmbienteExecucaoOO2 executar(AmbienteExecucaoOO2 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException, ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
			ObjetoJaDeclaradoException, ObjetoNaoDeclaradoException, ProcedimentoNaoDeclaradoException,
			ProcedimentoJaDeclaradoException, EntradaInvalidaException {
		
		// Inicializa elementos da classe
		super.executar(ambiente);

		// Recupera a definicao da classe
		DefClasseOO2 defClasse = (DefClasseOO2) ambiente.getDefClasse(getClasse());
		
		// Recupera o valor referencia
		ValorRef vr = (ValorRef) getAv().avaliar(ambiente);
		
		// Extends classe mae
		if (defClasse.getNomeSuperClasse() != null) {
			DefClasseOO2 classeMae = (DefClasseOO2) ambiente.getDefClasse(defClasse.getNomeSuperClasse());
			
			this.extendsClasse(ambiente, classeMae, ambiente.getObjeto(vr));
		}

				
		Procedimento metodo = defClasse.getConstrutor().getProcedimento();
		AmbienteExecucaoOO2 aux = new ContextoExecucaoOO2(ambiente);

		aux.changeValor(new Id("this"), vr);

		ListaValor valoresDosParametros = parametrosReais.avaliar(ambiente);
		new ChamadaProcedimento(metodo, parametrosReais, valoresDosParametros).executar(aux);

		return ambiente;
	}

}
