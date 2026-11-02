package li1.plp.imperative1.command;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import li1.plp.expressions2.expression.Expressao;
import li1.plp.expressions2.expression.Id;
import li1.plp.expressions2.expression.Valor;
import li1.plp.expressions2.expression.ValorBooleano;
import li1.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li1.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li1.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li1.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li1.plp.imperative1.memory.EntradaVaziaException;
import li1.plp.imperative1.memory.ErroTipoEntradaException;
import li1.plp.imperative1.util.Lista;
import li1.plp.imperative1.util.Coluna;
import li1.plp.imperative1.util.SqliteManager;

/**
 * Representa o comando {@code select L from T [where e]} da ImpSQL.
 * {@code colunas == null} representa o {@code *}, e
 * {@code condicao == null} representa a ausência de {@code where}.
 */
public class Select implements Comando {

    private Id id;
    private Lista<Id> colunas;
    private Expressao condicao;

    public Select(Id id, Lista<Id> colunas, Expressao condicao) {
        this.id = id;
        this.colunas = colunas;
        this.condicao = condicao;
    }

    /**
     * Lê os registros da tabela, filtra com o where e escreve na saída
     * os valores das colunas selecionadas.
     */
    @Override
    public AmbienteExecucaoImperativa executar(AmbienteExecucaoImperativa ambiente)
            throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException,
            EntradaVaziaException, ErroTipoEntradaException {

        List<LinkedHashMap<Id, Valor>> registros = SqliteManager.getInstance().lerRegistros(id);

        for (LinkedHashMap<Id, Valor> registro : registros) {
            if (condicao == null || satisfazCondicao(registro, ambiente)) {
                escreverRegistro(registro, ambiente);
            }
        }
        return ambiente;
    }

    /**
     * Avalia o where num escopo temporário em que cada coluna está
     * associada ao seu valor no registro.
     */
    private boolean satisfazCondicao(LinkedHashMap<Id, Valor> registro,
            AmbienteExecucaoImperativa ambiente)
            throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException {

        ambiente.incrementa();
        try {
            for (Map.Entry<Id, Valor> coluna : registro.entrySet()) {
                ambiente.map(coluna.getKey(), coluna.getValue());
            }
            return ((ValorBooleano) condicao.avaliar(ambiente)).valor();
        } finally {
            ambiente.restaura();
        }
    }

    /**
     * Escreve na saída os valores das colunas selecionadas:
     * todas, na ordem da tabela, ou as pedidas, na ordem do select.
     */
    private void escreverRegistro(LinkedHashMap<Id, Valor> registro,
            AmbienteExecucaoImperativa ambiente) {

        if (colunas == null) {
            for (Valor valor : registro.values()) {
                ambiente.write(valor);
            }
        } else {
            Lista<Id> aux = colunas;
            while (aux != null && aux.getHead() != null) {
                ambiente.write(registro.get(aux.getHead()));
                aux = aux.getTail();
            }
        }
    }

    /**
     * A tabela precisa ter sido definida, as colunas pedidas precisam
     * existir nela, e o where precisa ser booleano.
     */
    @Override
    public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
            throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException,
            EntradaVaziaException {

        // Regra 1: a tabela foi definida por um create-use.
        Lista<Coluna> esquema = ambiente.getTabela(id);
        if (esquema == null) {
            return false;
        }

        // Regra 2: cada coluna pedida existe na tabela.
        Lista<Id> aux = colunas;
        while (aux != null && aux.getHead() != null) {
            if (!existeColuna(esquema, aux.getHead())) {
                return false;
            }
            aux = aux.getTail();
        }

        // Regra 3: o where é bem tipado e booleano, com as colunas como variáveis.
        if (condicao == null) {
            return true;
        }
        ambiente.incrementa();
        try {
            Lista<Coluna> coluna = esquema;
            while (coluna != null && coluna.getHead() != null) {
                ambiente.map(coluna.getHead().getId(), coluna.getHead().getTipo());
                coluna = coluna.getTail();
            }
            return condicao.checaTipo(ambiente)
                    && condicao.getTipo(ambiente).eBooleano();
        } finally {
            ambiente.restaura();
        }
    }

    /**
     * Indica se a coluna faz parte do esquema da tabela.
     */
    private boolean existeColuna(Lista<Coluna> esquema, Id nome) {
        Lista<Coluna> aux = esquema;
        while (aux != null && aux.getHead() != null) {
            if (aux.getHead().getId().equals(nome)) {
                return true;
            }
            aux = aux.getTail();
        }
        return false;
    }

    @Override
    public String toString() {
        return "SELECT " + (colunas == null ? "*" : colunas.toString())
                + " FROM " + id + (condicao == null ? "" : " WHERE " + condicao);
    }
}