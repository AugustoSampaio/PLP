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
import loo2.plp.orientadaObjetos2.declaracao.ConstrutorNaoDeclaradoException;
import loo2.plp.orientadaObjetos2.declaracao.DecConstrutor;
import loo2.plp.orientadaObjetos2.memoria.AmbienteCompilacaoOO2;
import loo2.plp.orientadaObjetos2.memoria.AmbienteExecucaoOO2;
import loo2.plp.orientadaObjetos2.memoria.DefClasseOO2;

public class DecClasseSimplesOO2 extends DecClasseSimples {
	
	/**
	 * Identificador da super classe
	 */
	private Id nomeSuperClasse;
	
	/**
	 * Declarassa do construtor
	 */
	private DecConstrutor construtor;

	public DecClasseSimplesOO2(Id nomeClasse, Id nomeSuperClasse, DecVariavel atributos,
			DecConstrutor construtor, DecProcedimento metodos) {
		super(nomeClasse, atributos, metodos);

		this.construtor = construtor;
		this.nomeSuperClasse = nomeSuperClasse;
	}

	/**
	 * Cria um mapeamento do identificador para a declara��o desta classe.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            valores.
	 * @return o ambiente modificado pela declara��o da classe.
	 * @throws ConstrutorNaoDeclaradoException 
	 */
	public AmbienteExecucaoOO2 elabora(AmbienteExecucaoOO2 ambiente)
			throws ClasseJaDeclaradaException, ClasseNaoDeclaradaException, ConstrutorNaoDeclaradoException {
		
		// Adiciona a classe no mapeameento de classes
		ambiente.mapDefClasse(nomeClasse, new DefClasseOO2(nomeClasse, nomeSuperClasse, this.atributos, construtor, metodos));
		
		// Verifica se a super classe j� foi declarada
		if (nomeSuperClasse != null) {
			ambiente.mapSuperClasse(nomeClasse, nomeSuperClasse);
		}		
		
		return ambiente;
	}

	/**
	 * Verifica se a declaracao esta bem tipada, ou seja, se a checagem dos
	 * tipos dos metodos e atributos esta ok.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            seus tipos.
	 * @return <code>true</code> se os tipos da declaracao sao validos;
	 *         <code>false</code> caso contrario.
	 * @throws ConstrutorNaoDeclaradoException
	 */
	public boolean checaTipo (AmbienteCompilacaoOO2 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
			ProcedimentoNaoDeclaradoException,
			ProcedimentoJaDeclaradoException, ConstrutorNaoDeclaradoException {
		
		// Verifica se a super classe j� foi declarada
		if (nomeSuperClasse != null) {
			ambiente.mapSuperClasse(nomeClasse, nomeSuperClasse);
		}
		
		// Adiciona a classe no mapeameento de classes
		ambiente.mapDefClasse(nomeClasse, new DefClasseOO2(nomeClasse, nomeSuperClasse, this.atributos, construtor, metodos));

		boolean resposta = false;
		ambiente.incrementa();

		DecVariavel atr = (DecVariavel) this.atributos;
		if (atr.checaTipo(ambiente)){
			ambiente.map(new Id("this"), new TipoClasse(nomeClasse));

			if (nomeSuperClasse != null) {
				this.checaTipoVariaveisClasseMae(ambiente, this.nomeSuperClasse);
			}
			resposta =  metodos.checaTipo(ambiente);
		}
		
		//Verifica se construtor est� declarado corretamente
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
