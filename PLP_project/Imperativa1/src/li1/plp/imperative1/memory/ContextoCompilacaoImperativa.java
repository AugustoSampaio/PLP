package li1.plp.imperative1.memory;

import java.util.HashMap;

import li1.plp.expressions1.util.Tipo;
import li1.plp.expressions2.expression.Id;
import li1.plp.expressions2.memory.ContextoCompilacao;
import li1.plp.expressions2.memory.VariavelJaDeclaradaException;
import li1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li1.plp.imperative1.util.Coluna;
import li1.plp.imperative1.util.Lista;

public class ContextoCompilacaoImperativa extends ContextoCompilacao 
	implements AmbienteCompilacaoImperativa {

    /**
     * A pilha de blocos de contexto.
     */    
    private ListaValor entrada;

     /**
     * Mapa de tabelas: associa o nome de cada tabela ao seu esquema.
     * Fica fora da pilha de escopos, porque as tabelas têm escopo global.
     */

    private HashMap<Id, Lista<Coluna>> tabelas;

    /**
     * Construtor da classe.
     */
    public ContextoCompilacaoImperativa(ListaValor entrada){
        super();
        this.entrada = entrada;    
        this.tabelas = new HashMap<Id, Lista<Coluna>>();    
    }

    public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
    		EntradaVaziaException {
        if(entrada == null || entrada.getHead() == null) {
            throw new EntradaVaziaException();
        }
        Tipo aux = entrada.getHead().getTipo(this);
        entrada = (ListaValor)entrada.getTail();
        return aux;            
    }
     public void mapTabela(Id id, Lista<Coluna> colunas) {
        tabelas.put(id, colunas);
    }

    public Lista<Coluna> getTabela(Id id) {
        return tabelas.get(id);
    }

    public boolean existeTabela(Id id) {
        return tabelas.containsKey(id);
    }
    
}

