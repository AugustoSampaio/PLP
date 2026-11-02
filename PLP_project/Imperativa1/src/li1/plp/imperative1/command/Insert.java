package li1.plp.imperative1.command;

import li1.plp.expressions2.expression.Id;
import li1.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li1.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li1.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li1.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li1.plp.imperative1.memory.EntradaVaziaException;
import li1.plp.imperative1.memory.ErroTipoEntradaException;
import li1.plp.imperative1.util.Lista;
import li1.plp.imperative1.util.SqliteManager;
import java.util.List;
import li1.plp.imperative1.util.Coluna;

import li1.plp.expressions1.util.Tipo;
import li1.plp.imperative1.memory.ListaValor;

/**
 * Representa o comando {@code insert into T values (e1, ..., en)} da ImpSQL.
 */
public class Insert implements Comando {

    private Id id;
    private ListaExpressao expressoes;

    public Insert(Id id, ListaExpressao expressoes) {
        this.id = id;
        this.expressoes = expressoes;
    }

    /**
     * Avalia as expressões no ambiente atual e grava os valores na tabela.
     */
    @Override
    public AmbienteExecucaoImperativa executar(AmbienteExecucaoImperativa ambiente)
            throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException,
            EntradaVaziaException, ErroTipoEntradaException {

        ListaValor valores = expressoes.avaliar(ambiente);
        SqliteManager.getInstance().inserir(id, valores);
        return ambiente;
    }

    /**
     * A tabela precisa ter sido definida, e cada expressão precisa ter
     * o tipo da coluna na mesma posição.
     */
    @Override
    public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
            throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException,
            EntradaVaziaException {

                // Regra 1: a tabela foi definida por um create-use.
        Lista<Coluna> colunas = ambiente.getTabela(id);
        if (colunas == null) {
            return false;
        }

        // Regra 2: todas as expressões são bem tipadas.
        if (!expressoes.checaTipo(ambiente)) {
            return false;
        }

        // Regra 3: mesma quantidade e mesmos tipos, posição a posição.
        List<Tipo> tipos = expressoes.getTipos(ambiente);
        if (tipos.size() != colunas.length()) {
            return false;
        }
        Lista<Coluna> coluna = colunas;
        for (Tipo tipo : tipos) {
            if (!tipo.eIgual(coluna.getHead().getTipo())) {
                return false;
            }
            coluna = coluna.getTail();
        }
        return true;
    }



    @Override
    public String toString() {
        return "INSERT INTO " + id + " VALUES (" + expressoes + ")";
    }
}
