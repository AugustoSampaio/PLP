package loo2.plp.orientadaObjetos2.declaracao;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.comando.Comando;
import loo2.plp.orientadaObjetos1.comando.Procedimento;
import loo2.plp.orientadaObjetos1.declaracao.procedimento.DecProcedimentoSimples;
import loo2.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos2.memoria.AmbienteCompilacaoOO2;

public class DecConstrutor extends DecProcedimentoSimples {
	private Id nomeClasse;
	
	public DecConstrutor(Id nomeClasse, Id nome, ListaDeclaracaoParametro parametrosFormais, Comando comando) {
		super(nome, parametrosFormais, comando);
		this.nomeClasse = nomeClasse;
	}

	public boolean checaTipo(AmbienteCompilacaoOO2 ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException, 
					ProcedimentoJaDeclaradoException, ProcedimentoNaoDeclaradoException, ClasseNaoDeclaradaException, ClasseJaDeclaradaException, ConstrutorNaoDeclaradoException {
		boolean resposta = ( this.nomeClasse.toString().equals(this.nome.toString()) );
		
		if (resposta) {
			return super.checaTipo(ambiente);
		} else {
			throw new ConstrutorNaoDeclaradoException(nomeClasse);
		}
	}
	
	public Procedimento getProcedimento() {
        return new Procedimento(parametrosFormais, comando);
	}
}
