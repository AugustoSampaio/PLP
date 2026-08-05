package loo2.plp.orientadaObjetos1.memoria;

import java.util.HashMap;
import java.util.Stack;

import loo2.plp.expressions2.expression.Id;
import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor;
import loo2.plp.orientadaObjetos1.util.Tipo;

/**
 * Representa o contexto de compilação OO2.
 */
public class ContextoCompilacaoOO1 implements AmbienteCompilacaoOO1 {

    private Stack<HashMap<Id, Tipo>> pilha;
    private Stack<HashMap<Id, ListaDeclaracaoParametro>> pilhaProcedimento;
    private HashMap<Id, DefClasse> mapDefClasse;
    private final MetadadosDepuracao<Tipo> metadadosDepuracao;
    private ListaValor entrada;

    public ContextoCompilacaoOO1(ListaValor entrada){
        pilha = new Stack<HashMap<Id, Tipo>>();
        pilhaProcedimento = new Stack<HashMap<Id, ListaDeclaracaoParametro>>();
        mapDefClasse = new HashMap<Id, DefClasse>();
        this.entrada = entrada;
        metadadosDepuracao = new MetadadosDepuracao<Tipo>();
    }

    public void incrementa(){
        pilha.push(new HashMap<Id, Tipo>());
        pilhaProcedimento.push(new HashMap<Id, ListaDeclaracaoParametro>());
        metadadosDepuracao.getPilhaSnapshot().incrementa();
    }

    public void registraEscopo(InfoEscopo info) {
        metadadosDepuracao.getPilhaSnapshot().registraEscopo(info);
    }

    /** Registra o escopo com um nome legível para o debugger. */
	public void registraEscopo(InfoEscopo info, String nome) {
		metadadosDepuracao.getPilhaSnapshot().registraEscopo(info, nome);
	}

    public void restaura(){
        pilha.pop();
        pilhaProcedimento.pop();
        metadadosDepuracao.getPilhaSnapshot().restaura();
    }

    public void map(Id idArg, Tipo tipoId) throws VariavelJaDeclaradaException {
        HashMap<Id, Tipo> aux = pilha.peek();
        if (aux.put(idArg, tipoId) != null) {
            throw new VariavelJaDeclaradaException(idArg);
        }
        metadadosDepuracao.getPilhaSnapshot().map(idArg == null ? "null" : idArg.toString(), tipoId);
    }

    public void mapParametrosProcedimento(Id idArg, ListaDeclaracaoParametro parametrosId)
        throws ProcedimentoJaDeclaradoException {
        HashMap<Id, ListaDeclaracaoParametro> aux = pilhaProcedimento.peek();
        if (aux.put(idArg, parametrosId) != null) {
            throw new ProcedimentoJaDeclaradoException(idArg);
        }
    }

    public void mapDefClasse(Id idArg, DefClasse defClasse)
        throws ClasseJaDeclaradaException {
        if (mapDefClasse.put(idArg, defClasse) != null) {
            throw new ClasseJaDeclaradaException(idArg);
        }
    }

    public Tipo get(Id idArg) throws VariavelNaoDeclaradaException {
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
        }
        return result;
    }

    public ListaDeclaracaoParametro getParametrosProcedimento(Id idArg)
        throws ProcedimentoNaoDeclaradoException {
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
        }
        return result;
    }

    public DefClasse getDefClasse(Id idArg) throws ClasseNaoDeclaradaException {
        DefClasse result = mapDefClasse.get(idArg);
        if (result == null) {
            throw new ClasseNaoDeclaradaException(idArg);
        }
        return result;
    }

    public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException {
        Tipo aux = entrada.getHead().getTipo(this);
        entrada = (ListaValor) entrada.getTail();
        return aux;
    }

    public Tipo getTipo(Id idArg) throws VariavelNaoDeclaradaException {
        return get(idArg);
    }

    public java.util.List<java.util.Map<String, Object>> getPilhaSnapshot() {
        return metadadosDepuracao.toSnapshot();
    }
}
