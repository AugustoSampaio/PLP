package loo1.plp.orientadaObjetos1.expressao.leftExpression;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.expressao.Expressao;
import loo1.plp.orientadaObjetos1.expressao.valor.Valor;
import loo1.plp.orientadaObjetos1.expressao.valor.ValorRef;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo1.plp.orientadaObjetos1.memoria.ContextoObjeto;
import loo1.plp.orientadaObjetos1.memoria.DefClasse;
import loo1.plp.orientadaObjetos1.memoria.Objeto;
import loo1.plp.orientadaObjetos1.util.Tipo;
import loo1.plp.orientadaObjetos1.util.TipoClasse;

/**
 * Classe que representa um acesso de atributo a partir de uma expressao.
 */
public class AcessoAtributoId extends AcessoAtributo{
    /**
     * Expressao que acessa o atributo.
     */
    protected LeftExpression av;
    /**
     * Construtor.
     * @param av Expressao do lado esquerdo, que acessa o atributo.
     * @param id O atributo sendo acessado.
     */
    public AcessoAtributoId(LeftExpression av, Id id){
        super(id);
        this.av = av;
    }
    /**
     * Avalia esse acesso de atributo obtendo o valor do atributo no ambiente.
     * @param ambiente o ambiente de execu��o, que apresenta o mapeamento de
     * identificadores a valores.
     * @return o valor do atributo acessado no ambiente.
     * @throws VariavelNaoDeclaradaException
     * @throws VariavelJaDeclaradaException
     * @throws ObjetoNaoDeclaradoException
     */
    public Valor avaliar(AmbienteExecucaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
               ObjetoNaoDeclaradoException, ClasseNaoDeclaradaException{
        return obterValorDeIdNoAmbiente(ambiente);
    }
    /**
     * Obt�m a express�o acessadora do atributo.
     * @return a express�o acessadora do atributo.
     */
    public Expressao getExpressaoObjeto(){
        return av;
    }
    /**
     * Verifica se os atributos associados foram declarados e se seus tipos
     * existem no ambiente.
     * @param ambiente o ambiente de compila��o, com o mapeamento de identificadores
     * a tipos.
     * @return true, se as vari�veis acessadas j� foram declaradas e seus
     * tipos existem.
     * @throws VariavelNaoDeclaradaException
     * @throws ClasseNaoDeclaradaException
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, ClasseNaoDeclaradaException{
        boolean resposta = false;
        if(av.checaTipo(ambiente)) {
            try{
                Tipo t = av.getTipo(ambiente);
                DefClasse defClasse = ambiente.getDefClasse(((TipoClasse)t).getTipo());
                defClasse.getTipoAtributo(super.getId());
                resposta = true;
            }
            catch(VariavelNaoDeclaradaException atrib){
                resposta = false;
            }
            catch(ClasseNaoDeclaradaException clas){
                resposta = false;
            }

        }
        return resposta;
    }
    /**
     * Obt�m o tipo do atributo no ambiente.
     * @param ambiente que apresenta o mapeamento de identificadores a tipos.
     * @return o tipo do atributo acessado.
     * @throws VariavelNaoDeclaradaException
     * @throws ClasseNaoDeclaradaException
     */
    public Tipo getTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelNaoDeclaradaException, ClasseNaoDeclaradaException{
          //Logo abaixo obtenho a definicao da Classe (seus m�todos e atributos).
          //av.getTipo devera retornar uma instancia de TipoClasse e assim, TipoClasse.getTipo()
          //retorna o id (contendo o nome da classe) associado ao tipo dela
        Id nomeClasse = ((TipoClasse)av.getTipo(ambiente)).getTipo();
        DefClasse defClasse = ambiente.getDefClasse(nomeClasse);
        Tipo tipoAtr = defClasse.getTipoAtributo(super.getId());
        //Em seguida retorno o tipo do atributo, caso ele esteja definido na classe.
        //caso n�o esteja, uma exce��o ser� lan�ada
        return tipoAtr;
    }
    /**
     * Obt�m a expressao que acessa o atributo.
     * @return a LeftExpression que representa a express�o que acessa o atributo.
     */
    public LeftExpression getAv() {
        return av;
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
        ValorRef referencia = (ValorRef) av.avaliar(ambiente);
        Objeto objeto = ambiente.getObjeto(referencia);
        
        // Recuperando o mapeamento de valores do objeto (atributos do objeto)
        ContextoObjeto aux = objeto.getEstado();
        
        // Recuperando o valor do atributo "id" do objeto
        return aux.get(super.getId());
    }
}