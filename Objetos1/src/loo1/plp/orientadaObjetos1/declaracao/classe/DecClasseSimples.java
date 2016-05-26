package loo1.plp.orientadaObjetos1.declaracao.classe;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.DecProcedimento;
import loo1.plp.orientadaObjetos1.declaracao.variavel.DecVariavel;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo1.plp.orientadaObjetos1.memoria.DefClasse;
import loo1.plp.orientadaObjetos1.util.TipoClasse;
/**
 * Classe que representa a declaracao de uma unica classe.
 */
public class DecClasseSimples implements DecClasse {
	/**
	 * Identificador do nome da classe.
	 */
    protected Id nomeClasse;
    
	/**
	 * Atributos da classe.
	 */
    protected DecVariavel atributos;
    
	/**
	 * Metodos da classe.
	 */
    protected DecProcedimento metodos;
    
    
	/**
	 * Construtor.
	 * @param nomeClasse Nome da classe
	 * @param atributos Atributos da classe
	 * @param metodos Metodos da classe.
	 */
    public  DecClasseSimples(Id nomeClasse, DecVariavel atributos, DecProcedimento metodos){
        this.nomeClasse = nomeClasse;
        this.atributos = atributos;
        this.metodos = metodos;
    }

    /**
     * Verifica se a declaracao esta bem tipada, ou seja, se a checagem dos tipos
     * dos metodos e atributos esta ok.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos da declara vlidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
               ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
               ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException {

        ambiente.mapDefClasse(nomeClasse, new DefClasse(nomeClasse, atributos, metodos));
        boolean resposta = false;
        ambiente.incrementa();
        if (atributos.checaTipo(ambiente)){
            ambiente.map(new Id("this"), new TipoClasse(nomeClasse));
            resposta =  metodos.checaTipo(ambiente);
        }
        ambiente.restaura();
        return resposta;
    }

    /**
     * Cria um mapeamento do identificador para a declarao desta classe.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela declarao da classe.
     */
	public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
			ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException {

		ambiente.mapDefClasse(nomeClasse, new DefClasse(nomeClasse, atributos,metodos));
		
		return ambiente;
	}
}