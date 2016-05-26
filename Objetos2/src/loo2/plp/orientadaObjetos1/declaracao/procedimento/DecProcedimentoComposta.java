package loo2.plp.orientadaObjetos1.declaracao.procedimento;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.comando.Procedimento;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;

/**
 * Classe que representa mais de uma declara��o de procedimento.
 */
public class DecProcedimentoComposta implements DecProcedimento{
    /**
     * Primeira Declara��o de procedimento.
     */
    private DecProcedimento declaracao1;
    /**
     * Restante da declara��o de procedimento composta.
     */
    private DecProcedimento declaracao2;
    /**
     * Construtor
     * @param parametro1 Primeira Declara��o de procedimento.
     * @param parametro2 Restante da declara��o de procedimento composta.
     */
    public  DecProcedimentoComposta(DecProcedimento declaracao1, DecProcedimento declaracao2){
        this.declaracao1 = declaracao1;
        this.declaracao2 = declaracao2;
    }
    /**
     * Obt�m o procedimento Obt�m o procedimento identificado por id.
     * @param id O identificador do procedimento.
     * @return o procedimento Obt�m o procedimento identificado por id.
     * @throws ProcedimentoNaoDeclaradoException se n�o houver
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
     * Verifica se a declara��o est� bem tipada, ou seja, se a
     * express�o de inicializa��o est� bem tipada.
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e seus tipos.
     * @return <code>true</code> se os tipos da declara��o s�o v�lidos;
     *          <code>false</code> caso contrario.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
        ProcedimentoJaDeclaradoException, ProcedimentoNaoDeclaradoException,
        ClasseNaoDeclaradaException,ClasseJaDeclaradaException{
        return declaracao1.checaTipo(ambiente) && declaracao2.checaTipo(ambiente);
    }
}
