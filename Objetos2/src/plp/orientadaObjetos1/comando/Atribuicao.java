package plp.orientadaObjetos1.comando;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.expressao.Expressao;
import plp.orientadaObjetos1.expressao.leftExpression.AcessoAtributo;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos1.expressao.leftExpression.LeftExpression;
import plp.orientadaObjetos1.expressao.valor.ValorRef;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.memoria.Objeto;
import plp.orientadaObjetos1.util.TipoClasse;
/**
 * Classe que representa um comando de atribuição.
 */
public class Atribuicao implements Comando {
	/**
	 * Lado esquerdo do comando de atribuição.
	 */
    protected LeftExpression av;
    
	/**
	 * Expressão cujo valor será atribuído ao lado esquerdo.
	 */
    protected Expressao expressao;
    
	/**
	 * Construtor.
	 * @param av Lado esquerdo
	 * @param expressao Expressão cujo valor será atribuído ao lado esquerdo.
	 */
    public Atribuicao(LeftExpression av, Expressao expressao){
        this.av = av;
        this.expressao = expressao;
    }

    /**
     * Executa  a atribuição.
     *
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return o ambiente modificado pela execução da atribuição.
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
     * Um comando de atribuição está bem tipado, se o tipo do identificador é
     * o mesmo da expressão. O tipo de um identificador é determinado pelo
     * tipo da expressão que o inicializou (na declaração).
     *
     * @param ambiente o ambiente que contem o mapeamento entre identificadores
     *  e valores.
     * @return <code>true</code> se os tipos da atribuição são válidos;
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