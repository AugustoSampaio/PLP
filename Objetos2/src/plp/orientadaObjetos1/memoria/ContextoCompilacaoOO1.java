package plp.orientadaObjetos1.memoria;

import java.util.HashMap;
import java.util.Stack;

import plp.expressions2.expression.Id;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.memoria.colecao.ListaValor;
import plp.orientadaObjetos1.util.Tipo;
/**
 * Representa o contexto de compilaçao.
 */
public class ContextoCompilacaoOO1 implements AmbienteCompilacaoOO1 {

    /**
     * A pilha de tipos do contexto. Onde o tipo do id pode ser
     * um tipo primitivo ou uma classe.
     */
    private Stack<HashMap<Id, Tipo>> pilha;

    /**
     * A pilha de procedimentos do contexto.
     */
    private Stack<HashMap<Id, ListaDeclaracaoParametro>> pilhaProcedimento;


    /**
     * mapeamento de classes do contexto.
     * nao é necessaria uma pilha, pois ha apenas um nivel de mapeamentos
     */
    private HashMap<Id, DefClasse> mapDefClasse;  

     /**
     * A tail de valores inicias do contexto.
     */
    private ListaValor entrada;

    /**
     * O Construtor da classe.
     */
    public ContextoCompilacaoOO1(ListaValor entrada){
        pilha = new Stack<HashMap<Id, Tipo>>();
        pilhaProcedimento = new Stack<HashMap<Id, ListaDeclaracaoParametro>>();
        mapDefClasse = new HashMap<Id, DefClasse>();  //cria mapeamento ids def classes
        this.entrada = entrada;
    }

    /**
     * Incrementa a pilha do ambiente, passando para o próximo estado.
     */
    public void incrementa(){
        pilha.push(new HashMap<Id, Tipo>());
        pilhaProcedimento.push(new HashMap<Id, ListaDeclaracaoParametro>());
    }

    /**
     * Restaura o estado do ambiente.
     */
    public void restaura(){
        pilha.pop();
        pilhaProcedimento.pop();
    }
    /**
     * Mapeia um identificador a um tipo.
     * @param idArg Identificador
     * @param tipoId Tipo que deve ser associado ao identificador.
     * @throws VariavelJaDeclaradaException quando o id já foi declarado.
     */
    public void map(Id idArg, Tipo tipoId)
        throws VariavelJaDeclaradaException {
        HashMap<plp.expressions2.expression.Id, Tipo> aux = pilha.peek();
        if (aux.put(idArg, tipoId) != null) {
            throw new VariavelJaDeclaradaException(idArg);
        }
    }

    /**
     * Mapeia um identificador representando um método aos seus parâmetros.
     * @param idArg identificador do método.
     * @param parametrosId Parâmetros do método
     * @throws ProcedimentoJaDeclaradoException quando o procedimento já foi
     * declarado.
     */
    public void mapParametrosProcedimento(Id idArg, ListaDeclaracaoParametro parametrosId)
        throws ProcedimentoJaDeclaradoException {
        HashMap<Id, ListaDeclaracaoParametro> aux = pilhaProcedimento.peek();
        if (aux.put(idArg, parametrosId) != null) {
            throw new ProcedimentoJaDeclaradoException(idArg);
        }
    }

    /**
     * Mapeia um identificador a um definição de classe.
     * @param idArg o nome da classe
     * @param defClasse Definição da Classe.
     * @throws ClasseJaDeclaradaException quando a classe já foi declarada.
     */
    public void mapDefClasse(Id idArg, DefClasse defClasse)
        throws ClasseJaDeclaradaException {
        if (mapDefClasse.put(idArg, defClasse) != null) {
            throw new ClasseJaDeclaradaException(idArg);
        }
    }

    /**
     * Obtém o tipo associado a um dado identificador
     * @param idArg Identificador
     * @return Tipo associado a um dado identificador
     * @throws VariavelNaoDeclaradaException quando id não foi declarado.
     */
    public Tipo get(Id idArg)
        throws VariavelNaoDeclaradaException {
        Tipo result = null;
        Stack<HashMap<Id, Tipo>> auxStack = new Stack<HashMap<Id, Tipo>>();
        while (result == null && !pilha.empty()) {
			HashMap<Id, Tipo> aux = pilha.pop();
            auxStack.push(aux);
            result = aux.get(idArg);
        }
        while (!auxStack.empty()) {
            pilha.push(auxStack.pop());
        }
        if (result == null) {
            throw new VariavelNaoDeclaradaException(idArg);
        } else {
            return result;
        }
    }

    /**
     * Obtém a tail de parâmetros associada a um identificador que representa
     * nome do método.
     * @param idArg Identificador que representa o nome do método.
     * @return Lista de parâmetros Lista de parâmetros associada a um identificador que representa
     * nome do método.
     * @throws ProcedimentoNaoDeclaradoException quando não foi declarado nenhum
     * método com esse id.
     */
    public ListaDeclaracaoParametro getParametrosProcedimento(Id idArg)
        throws ProcedimentoNaoDeclaradoException  {
        ListaDeclaracaoParametro result = null;
        Stack<HashMap<Id, ListaDeclaracaoParametro>> auxStack = new Stack<HashMap<Id, ListaDeclaracaoParametro>>();
        while (result == null && !pilhaProcedimento.empty()) {
            HashMap<Id, ListaDeclaracaoParametro> aux = pilhaProcedimento.pop();
            auxStack.push(aux);
            result = aux.get(idArg);
        }
        while (!auxStack.empty()) {
            pilhaProcedimento.push(auxStack.pop());
        }
        if (result == null) {
            throw new ProcedimentoNaoDeclaradoException(idArg);
        } else {
            return result;
        }
    }

    /**
     * Obtém a definição da classe cujo nome é idArg
     * @param idArg Nome da classe.
     * @return a definição da classe.
     * @throws ClasseNaoDeclaradaException quando nao foi declarada nenhuma
     * classe com esse nome.
     */
    public DefClasse getDefClasse(Id idArg)
        throws ClasseNaoDeclaradaException  {
        DefClasse result = null;
        result = this.mapDefClasse.get(idArg);
        if (result == null) {
            throw new ClasseNaoDeclaradaException(idArg);
        } else {
            return result;
        }
    }

    /**
     * Obtém o tipo da entrada atual para este ambiente.
     * @return o tipo da entrada.
     * @throws VariavelNaoDeclaradaException quando a entrada atual é
     * uma variável não declarada.
     */
    public Tipo getTipoEntrada()
        throws VariavelNaoDeclaradaException {
        Tipo aux =  entrada.getHead().getTipo(this);
        entrada = (ListaValor)entrada.getTail();
        return aux;
    }
    
    /**
     * Obtém o tipo associado a um dado identificador
     * @param idArg Identificador
     * @return Tipo associado a um dado identificador
     * @throws VariavelNaoDeclaradaException quando id não foi declarado.
     */
    public Tipo getTipo(Id idArg)
        throws VariavelNaoDeclaradaException {
        Tipo result = null;
        Stack<HashMap<Id, Tipo>> auxStack = new Stack<HashMap<Id, Tipo>>();
        while (result == null && !pilha.empty()) {
			HashMap<Id, Tipo> aux = pilha.pop();
            auxStack.push(aux);
            result = aux.get(idArg);
        }
        while (!auxStack.empty()) {
            pilha.push(auxStack.pop());
        }
        if (result == null) {
            throw new VariavelNaoDeclaradaException(idArg);
        } else {
            return result;
        }
    }    

}

