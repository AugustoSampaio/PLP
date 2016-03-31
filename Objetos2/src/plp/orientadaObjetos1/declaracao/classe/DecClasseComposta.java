package plp.orientadaObjetos1.declaracao.classe;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;

/**
 * Classe representando a declaração de mais de uma classe.
 */
public class DecClasseComposta implements DecClasse{
	/**
	 * Primeira declaração de classe.
	 */
    private DecClasse declaracao1;
    /**
     * Segunda declaração de classe, que pode ser simples ou composta.
     */
    private DecClasse declaracao2;
	/**
	 * Construtor.
	 * @param parametro1 Primeira declaração de classe.
	 * @param parametro2 Segunda parte da declaração de classe composta.
	 */
    public  DecClasseComposta(DecClasse declaracao1, DecClasse declaracao2){
        this.declaracao1 = declaracao1;
        this.declaracao2 = declaracao2;
    }
    /**
     * Cria um mapeamento do identificador para esta declaração 
     * de classe no ambiente  de execucao
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela inicialização da classe.
     */
    public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ClasseNaoDeclaradaException, ClasseJaDeclaradaException,
               ProcedimentoNaoDeclaradoException,ProcedimentoJaDeclaradoException{
        return declaracao2.elabora(declaracao1.elabora(ambiente));
    }
    /**
     * Verifica se a declaração está bem tipada, ou seja, se as duas
     * declarações estão bem tipadas
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos da declaração são válidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
        ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
        ProcedimentoNaoDeclaradoException,ProcedimentoJaDeclaradoException{
        return declaracao1.checaTipo(ambiente) && declaracao2.checaTipo(ambiente);
    }
}
