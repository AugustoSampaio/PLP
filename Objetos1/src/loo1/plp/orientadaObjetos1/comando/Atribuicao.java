package loo1.plp.orientadaObjetos1.comando;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.expressao.Expressao;
import loo1.plp.orientadaObjetos1.expressao.leftExpression.AcessoAtributo;
import loo1.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo1.plp.orientadaObjetos1.expressao.leftExpression.LeftExpression;
import loo1.plp.orientadaObjetos1.expressao.valor.ValorRef;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo1.plp.orientadaObjetos1.memoria.Objeto;
import loo1.plp.orientadaObjetos1.util.TipoClasse;
/**
 * Classe que representa um comando de atribui��o.
 */
public class Atribuicao implements Comando {
	/**
	 * Lado esquerdo do comando de atribui��o.
	 */
    protected LeftExpression av;
    
	/**
	 * Express�o cujo valor ser� atribu�do ao lado esquerdo.
	 */
    protected Expressao expressao;
    
	/**
	 * Construtor.
	 * @param av Lado esquerdo
	 * @param expressao Express�o cujo valor ser� atribu�do ao lado esquerdo.
	 */
    public Atribuicao(LeftExpression av, Expressao expressao){
        this.av = av;
        this.expressao = expressao;
    }

    /**
     * Executa  a atribui��o.
     *
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela execu��o da atribui��o.
     * @throws ClasseNaoDeclaradaException 
     *
     */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
               ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException {

        Id idVariavel =  av.getId();
        if ( av instanceof AcessoAtributo){
            
        	// se for acesso a atributo, tem de alterar o ambiente do objeto!
            Expressao expAV = ((AcessoAtributo)av).getExpressaoObjeto();
            ValorRef referencia = (ValorRef)expAV.avaliar(ambiente);
            Objeto obj = ambiente.getObjeto(referencia);
            obj.changeAtributo(idVariavel, expressao.avaliar(ambiente));
        }
        else
            ambiente.changeValor(idVariavel, expressao.avaliar(ambiente));
        return ambiente;
    }

    /**
     * Um comando de atribui��o est� bem tipado, se o tipo do identificador �
     * o mesmo da express�o. O tipo de um identificador � determinado pelo
     * tipo da express�o que o inicializou (na declara��o).
     *
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return <code>true</code> se os tipos da atribui��o s�o v�lidos;
     *          <code>false</code> caso contrario.
     *
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, ClasseNaoDeclaradaException {
        return expressao.checaTipo(ambiente) &&
              (av.getTipo(ambiente).equals(expressao.getTipo(ambiente))
               || expressao.getTipo(ambiente).equals(TipoClasse.TIPO_NULL));
    }
}