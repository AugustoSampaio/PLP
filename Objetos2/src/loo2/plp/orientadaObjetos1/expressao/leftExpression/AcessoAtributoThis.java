package loo2.plp.orientadaObjetos1.expressao.leftExpression;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.Expressao;
import loo2.plp.orientadaObjetos1.expressao.This;
import loo2.plp.orientadaObjetos1.expressao.valor.Valor;
import loo2.plp.orientadaObjetos1.expressao.valor.ValorRef;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.memoria.ContextoObjeto;
import loo2.plp.orientadaObjetos1.memoria.DefClasse;
import loo2.plp.orientadaObjetos1.memoria.Objeto;
import loo2.plp.orientadaObjetos1.util.Tipo;

/**
 * Representa um acesso de atributo a partir de um objeto this.
 */
public class AcessoAtributoThis extends AcessoAtributo {
    /**
     * O objeto this.
     */
    protected This varThis;
    /**
     * Construtor.
     * @param varThis O objeto this.
     * @param id O identificador sendo acessado.
     */
    public AcessoAtributoThis(This varThis, Id id){
        super(id);
        this.varThis = varThis;
    }
    /**
     * O valor do atributo acessado no ambiente.
     * @param ambiente o ambiente contendoo mapeamento de identificadores
     * a valores.
     * @return o valor do atributo acessado.
     * @throws VariavelNaoDeclaradaException
     * @throws VariavelJaDeclaradaException
     * @throws ObjetoNaoDeclaradoException
     */
    public Valor avaliar(AmbienteExecucaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException, ObjetoNaoDeclaradoException, 
        		ClasseNaoDeclaradaException {
        return obterValorDeIdNoAmbiente(ambiente);
    }
    /**
     * Obt�m a express�o que acessa o identificador.
     * @return a expressao que acessa o identificador.
     */
    public Expressao getExpressaoObjeto(){
        return varThis;
    }
    /**
     * Verifica se o this est� associado a um objeto e se o atributo existe.
     * @param ambiente o ambiente com o mapeamento de identificadores a tipos.
     * @return true, se o this est� associado a um objeto e se o atributo existe,
     * ou false, caso contr�rio.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente) {
        boolean resposta = false;
        try {
              resposta = varThis.checaTipo(ambiente);
              if (resposta) {
                  Tipo tipo = varThis.getTipo(ambiente);
                  DefClasse defClasse = ambiente.getDefClasse(tipo.getTipo());
                  defClasse.getTipoAtributo(super.getId());
                  resposta = true;
              }
        } catch(VariavelNaoDeclaradaException atrib){
              resposta = false;
        } catch(ClasseNaoDeclaradaException clas){
              resposta = false;
        }
        return resposta;
    }
    /**
     * Obt�m o tipo do atributo acessado.
     * @param ambiente o ambiente com o mapeamento de identificadores a tipos.
     * @return true, se foi associado um tipo a esse identificador acessado no
     * escopo corrente.
     * @throws VariavelNaoDeclaradaException
     * @throws ClasseNaoDeclaradaException
     */
    public Tipo getTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException,ClasseNaoDeclaradaException{
        //Logo abaixo obtenho a definicao da Classe (seus m�todos e atributos).
        //this.getTipo() devera retornar uma instancia de TipoClasse e assim, TipoClasse.getTipo()
        //retorna o id (contendo o nome da classe) associado ao tipo dela
        DefClasse defClasse = ambiente.getDefClasse(varThis.getTipo(ambiente).getTipo());
        //Em seguida retorno o tipo do atributo, caso ele esteja definido na classe.
        //caso n�o esteja, uma exce��o ser� lan�ada
        return defClasse.getTipoAtributo(super.getId());
    }

    /**
     * Retorna o valor do Objeto representado por um certo id
     * @param ambiente � o Ambiente de Execu��o
     * @return o valor do Objeto representado por um certo id
     * @throws ClasseNaoDeclaradaException 
     */
    private Valor obterValorDeIdNoAmbiente(AmbienteExecucaoOO1 ambiente)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException {
        
    	// Pegando o objeto no ambiente
        ValorRef referencia = (ValorRef)varThis.avaliar(ambiente);
        Objeto objeto = ambiente.getObjeto(referencia);
        
        // Recuperando o mapeamento de valores do objeto (atributos do objeto)
       ContextoObjeto aux = objeto.getEstado();
        
        // Recuperando o valor do atributo "id" do objeto
        return aux.get(super.getId());
    }
}