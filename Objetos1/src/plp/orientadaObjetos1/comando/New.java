package plp.orientadaObjetos1.comando;

/*
 * A execucao de um comando ocorre em um determinado ambiente. O
 * resultado de tal execucao é a modificação deste ambiente, i.e., comandos
 * tem efeito colateral.
 */
import java.util.HashMap;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.declaracao.variavel.DecVariavel;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos1.expressao.leftExpression.LeftExpression;
import plp.orientadaObjetos1.expressao.valor.Valor;
import plp.orientadaObjetos1.expressao.valor.ValorRef;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.memoria.ContextoExecucaoOO1;
import plp.orientadaObjetos1.memoria.DefClasse;
import plp.orientadaObjetos1.memoria.ContextoObjeto;
import plp.orientadaObjetos1.memoria.Objeto;
import plp.orientadaObjetos1.util.TipoClasse;

/**
 * Comando de criação de objeto e atribuição deste a uma expressão esquerda.
 */
public class New implements Comando{
	/**
	 * Lado esquerdo da atribuição.
	 */
    private LeftExpression av;
	/**
	 * Identificador da classe, com o seu nome.
	 */
    protected Id classe;
	/**
	 * Construtor.
	 * @param av Lado esquerdo da atribuição.
	 * @param classe Identificador com o nome da classe.
	 */
    public New(LeftExpression av, Id classe){
          this.av = av;
          this.classe = classe;
    }
	/**
	 * Execução da atribuição de um novo objeto criado a uma left expression.
	 * @param ambiente O ambiente contendo o mapeamento entre identificadores
	 * e valores.
	 * @return o ambiente de execução atualizado.
	 */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente)    
    		throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
          ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
          ObjetoJaDeclaradoException,ObjetoNaoDeclaradoException{

          DefClasse defClasse = ambiente.getDefClasse(classe);
          DecVariavel decVariavel = defClasse.getDecVariavel();
          
          /*
           * Cria uma instancia auxiliar do ambiente para fazer o elabora da decVariavel
           * obs.: a criacao desse ambiente eh apenas para facilitar o elabora que pode ser bem complexo
           * dependendo da declaracao
           */
          AmbienteExecucaoOO1 aux = decVariavel.elabora(new ContextoExecucaoOO1(ambiente));
          ContextoObjeto estadoObj = new ContextoObjeto(aux.getPilha().pop());
          Objeto objeto = new Objeto(classe, estadoObj);
          
          /*
           * Mapeia o objeto no ambiente
           */
          ValorRef vr = ambiente.getProxRef();
          ambiente.mapObjeto(vr, objeto);
          ambiente = new Atribuicao(av,vr).executar(ambiente);
          
          return ambiente;
    }

	/**
	 * Verifica se a atribuição é possível comparando os tipos do objeto
	 * e da left expression.
	 * @param ambiente O ambiente de compilação, com o mapeamento
	 * entre identificadores e tipos.
	 */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente) throws VariavelNaoDeclaradaException,
      ClasseJaDeclaradaException, ClasseNaoDeclaradaException {
          TipoClasse tpClasse = new TipoClasse(classe);
          return av.checaTipo(ambiente) &&
            tpClasse.eValido(ambiente) &&
            tpClasse.equals(av.getTipo(ambiente));
    }
    
    public Id getClasse() {
		return classe;
	}
    
    public LeftExpression getAv() {
		return av;
	}
}