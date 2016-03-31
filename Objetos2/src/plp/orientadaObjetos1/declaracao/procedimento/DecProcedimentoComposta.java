package plp.orientadaObjetos1.declaracao.procedimento;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.comando.Procedimento;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;

/**
 * Classe que representa mais de uma declaração de procedimento.
 */
public class DecProcedimentoComposta implements DecProcedimento{
    /**
     * Primeira Declaração de procedimento.
     */
    private DecProcedimento declaracao1;
    /**
     * Restante da declaração de procedimento composta.
     */
    private DecProcedimento declaracao2;
    /**
     * Construtor
     * @param parametro1 Primeira Declaração de procedimento.
     * @param parametro2 Restante da declaração de procedimento composta.
     */
    public  DecProcedimentoComposta(DecProcedimento declaracao1, DecProcedimento declaracao2){
        this.declaracao1 = declaracao1;
        this.declaracao2 = declaracao2;
    }
    /**
     * Obtém o procedimento Obtém o procedimento identificado por id.
     * @param id O identificador do procedimento.
     * @return o procedimento Obtém o procedimento identificado por id.
     * @throws ProcedimentoNaoDeclaradoException se não houver
     * nenhum procedimento identificado por id.
     */
    public Procedimento getProcedimento(Id id) throws ProcedimentoNaoDeclaradoException {
        Procedimento procedimento;
        try {
            procedimento = declaracao1.getProcedimento(id);
        }
        catch(ProcedimentoNaoDeclaradoException e) {
            procedimento = declaracao2.getProcedimento(id);
        }
        return procedimento;
    }
    /**
     * Verifica se a declaração está bem tipada, ou seja, se a
     * expressão de inicialização está bem tipada.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos da declaração são válidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
        ProcedimentoJaDeclaradoException, ProcedimentoNaoDeclaradoException,
        ClasseNaoDeclaradaException,ClasseJaDeclaradaException{
        return declaracao1.checaTipo(ambiente) && declaracao2.checaTipo(ambiente);
    }
}
