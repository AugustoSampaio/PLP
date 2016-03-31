package plp.orientadaObjetos2.declaracao;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.comando.Comando;
import plp.orientadaObjetos1.comando.Procedimento;
import plp.orientadaObjetos1.declaracao.procedimento.DecProcedimentoSimples;
import plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos2.memoria.AmbienteCompilacaoOO2;

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
