package loo2.plp.orientadaObjetos2.declaracao.classe;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.declaracao.classe.DecClasseSimples;
import loo2.plp.orientadaObjetos1.declaracao.procedimento.DecProcedimento;
import loo2.plp.orientadaObjetos1.declaracao.variavel.DecVariavel;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.util.TipoClasse;
import loo2.plp.orientadaObjetos1.memoria.InfoEscopo;
import loo2.plp.orientadaObjetos2.declaracao.ConstrutorNaoDeclaradoException;
import loo2.plp.orientadaObjetos2.declaracao.DecConstrutor;
import loo2.plp.orientadaObjetos2.memoria.AmbienteCompilacaoOO2;
import loo2.plp.orientadaObjetos2.memoria.AmbienteExecucaoOO2;
import loo2.plp.orientadaObjetos2.memoria.DefClasseOO2;

/**
 * Declaração de classe com herança e construtor (OO2).
 */
public class DecClasseSimplesOO2 extends DecClasseSimples {
	
	private Id nomeSuperClasse;
	private DecConstrutor construtor;

	public DecClasseSimplesOO2(Id nomeClasse, Id nomeSuperClasse, DecVariavel atributos,
			DecConstrutor construtor, DecProcedimento metodos) {
		super(nomeClasse, atributos, metodos);
		this.construtor = construtor;
		this.nomeSuperClasse = nomeSuperClasse;
	}

	public DecClasseSimplesOO2(Id nomeClasse, Id nomeSuperClasse, DecVariavel atributos,
			DecConstrutor construtor, DecProcedimento metodos, InfoEscopo infoEscopo) {
		super(nomeClasse, atributos, metodos, infoEscopo);
		this.construtor = construtor;
		this.nomeSuperClasse = nomeSuperClasse;
	}

	public AmbienteExecucaoOO2 elabora(AmbienteExecucaoOO2 ambiente)
			throws ClasseJaDeclaradaException, ClasseNaoDeclaradaException, ConstrutorNaoDeclaradoException {
		ambiente.mapDefClasse(nomeClasse, new DefClasseOO2(nomeClasse, nomeSuperClasse, this.atributos, construtor, metodos));
		if (nomeSuperClasse != null) {
			ambiente.mapSuperClasse(nomeClasse, nomeSuperClasse);
		}
		return ambiente;
	}

	/**
	 * Verifica se a declaracao esta bem tipada.
	 */
	public boolean checaTipo(AmbienteCompilacaoOO2 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
			ProcedimentoNaoDeclaradoException,
			ProcedimentoJaDeclaradoException, ConstrutorNaoDeclaradoException {
		
		if (nomeSuperClasse != null) {
			ambiente.mapSuperClasse(nomeClasse, nomeSuperClasse);
		}
		
		ambiente.mapDefClasse(nomeClasse, new DefClasseOO2(nomeClasse, nomeSuperClasse, this.atributos, construtor, metodos));

		boolean resposta = false;
		ambiente.incrementa();
		// O debugger pode mostrar o nome da classe além do tipo de escopo.
		((loo2.plp.orientadaObjetos1.memoria.ContextoCompilacaoOO1) ambiente).registraEscopo(infoEscopo, nomeClasse.toString());

		DecVariavel atr = (DecVariavel) this.atributos;
		if (atr.checaTipo(ambiente)){
			ambiente.map(new Id("this"), new TipoClasse(nomeClasse));
			if (nomeSuperClasse != null) {
				this.checaTipoVariaveisClasseMae(ambiente, this.nomeSuperClasse);
			}
			resposta = metodos.checaTipo(ambiente);
		}
		
		resposta = resposta && construtor.checaTipo(ambiente);
		ambiente.restaura();

		return resposta;
	}
	
	private void checaTipoVariaveisClasseMae(AmbienteCompilacaoOO2 ambiente, Id nomeSuperClasse) throws ClasseNaoDeclaradaException, VariavelJaDeclaradaException, VariavelNaoDeclaradaException, ClasseJaDeclaradaException {
		if (nomeSuperClasse != null) {
			DefClasseOO2 defClasseMae = (DefClasseOO2) ambiente.getDefClasse(nomeSuperClasse);
			defClasseMae.getDecVariavel().checaTipo(ambiente);
			this.checaTipoVariaveisClasseMae(ambiente, defClasseMae.getNomeSuperClasse());
		}
	}
}
