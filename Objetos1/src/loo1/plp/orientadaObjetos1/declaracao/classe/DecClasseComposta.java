package loo1.plp.orientadaObjetos1.declaracao.classe;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;

/**
 * Classe representando a declara��o de mais de uma classe.
 */
public class DecClasseComposta implements DecClasse{
	/**
	 * Primeira declara��o de classe.
	 */
    private DecClasse declaracao1;
    /**
     * Segunda declara��o de classe, que pode ser simples ou composta.
     */
    private DecClasse declaracao2;
	/**
	 * Construtor.
	 * @param parametro1 Primeira declara��o de classe.
	 * @param parametro2 Segunda parte da declara��o de classe composta.
	 */
    public  DecClasseComposta(DecClasse declaracao1, DecClasse declaracao2){
        this.declaracao1 = declaracao1;
        this.declaracao2 = declaracao2;
    }
    /**
     * Cria um mapeamento do identificador para esta declara��o 
     * de classe no ambiente  de execucao
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela inicializa��o da classe.
     */
    public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ClasseNaoDeclaradaException, ClasseJaDeclaradaException,
               ProcedimentoNaoDeclaradoException,ProcedimentoJaDeclaradoException{
        return declaracao2.elabora(declaracao1.elabora(ambiente));
    }
    /**
     * Verifica se a declara��o est� bem tipada, ou seja, se as duas
     * declara��es est�o bem tipadas
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos da declara��o s�o v�lidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
        ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
        ProcedimentoNaoDeclaradoException,ProcedimentoJaDeclaradoException{
        return declaracao1.checaTipo(ambiente) && declaracao2.checaTipo(ambiente);
    }
}
