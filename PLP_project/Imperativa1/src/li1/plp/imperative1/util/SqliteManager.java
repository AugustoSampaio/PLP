package li1.plp.imperative1.util;

import li1.plp.expressions2.expression.Id;
import li1.plp.expressions2.expression.Valor;
import li1.plp.expressions1.util.Tipo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;



/**
 * Gerencia conexões e operações no banco SQLite.
 */
public class SqliteManager {

    private static final String URL_BANCO = "jdbc:sqlite:banco_impSQL.db";

    private static SqliteManager instance;

    private SqliteManager() {}

    public static synchronized SqliteManager getInstance() {
        if (instance == null) {
            instance = new SqliteManager();
        }
        return instance;
    }

    /**
     * Cria uma tabela no banco SQLite a partir das colunas da ImpSQL.
     */
    public void criarTabela(Id id, Lista<Coluna> colunas) {

        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sql.append(id.getIdName()).append(" (");

        Lista<Coluna> aux = colunas;

        while (aux != null && aux.getHead() != null) {

            Coluna col = aux.getHead();

            sql.append(col.getId().getIdName())
               .append(" ")
               .append(SqliteHelper.converterParaTipoSql(col.getTipo()));

            aux = aux.getTail();

            if (aux != null && aux.getHead() != null) {
                sql.append(", ");
            }
        }

        sql.append(");");

        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql.toString());

            System.out.println("[ImpSQL] Tabela '" + id.getIdName()
                    + "' criada no SQLite.");

        } catch (Exception e) {
            System.err.println(
                "Erro ao criar tabela no SQLite: " + e.getMessage()
            );
        }
    }

    /**
     * Lê do banco o esquema de uma tabela existente.
     *
     * @return as colunas da tabela, na ordem em que foram declaradas,
     *         ou null se a tabela não existir no banco.
     */
    public Lista<Coluna> lerEsquema(Id id) {

        String sql = "PRAGMA table_info(" + id.getIdName() + ");";
        List<Coluna> colunasLidas = new ArrayList<Coluna>();

        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Id nome = new Id(rs.getString("name"));
                Tipo tipo = SqliteHelper.converterParaTipoImpSQL(rs.getString("type"));
                colunasLidas.add(new Coluna(nome, tipo));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Erro ao ler o esquema da tabela '" + id.getIdName() + "': " + e.getMessage(), e);
        }

        if (colunasLidas.isEmpty()) {
            return null;    // a tabela não existe no banco
        }

        // Monta a Lista da ImpSQL de trás para frente, para manter a ordem das colunas.
        Lista<Coluna> esquema = new Lista<Coluna>();
        for (int i = colunasLidas.size() - 1; i >= 0; i--) {
            esquema = new Lista<Coluna>(colunasLidas.get(i), esquema);
        }
        return esquema;
    }

    /**
     * Grava um registro na tabela, com os valores na ordem das colunas.
     */
    public void inserir(Id id, Lista<Valor> valores) {

        // Monta "INSERT INTO tabela VALUES (?, ?, ...)", com um ? por valor.
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(id.getIdName()).append(" VALUES (");

        int quantidade = valores.length();
        for (int i = 0; i < quantidade; i++) {
            sql.append(i == 0 ? "?" : ", ?");
        }
        sql.append(");");

        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int posicao = 1;
            Lista<Valor> aux = valores;
            while (aux != null && aux.getHead() != null) {
                SqliteHelper.definirParametro(stmt, posicao, aux.getHead());
                posicao++;
                aux = aux.getTail();
            }

            stmt.executeUpdate();
            System.out.println("[ImpSQL] Tabela '" + id.getIdName()
                 + "' com novos dados: " + valores);


        } catch (SQLException e) {
            throw new RuntimeException(
                "Erro ao inserir na tabela '" + id.getIdName() + "': " + e.getMessage(), e);
        }
    }

        /**
     * Lê todos os registros de uma tabela. Cada registro associa o nome de
     * cada coluna ao seu valor, na ordem das colunas da tabela.
     */
    public List<LinkedHashMap<Id, Valor>> lerRegistros(Id id) {

        String sql = "SELECT * FROM " + id.getIdName() + ";";
        List<LinkedHashMap<Id, Valor>> registros = new ArrayList<LinkedHashMap<Id, Valor>>();

        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int quantidade = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                LinkedHashMap<Id, Valor> registro = new LinkedHashMap<Id, Valor>();
                for (int coluna = 1; coluna <= quantidade; coluna++) {
                    Id nome = new Id(rs.getMetaData().getColumnName(coluna));
                    registro.put(nome, SqliteHelper.lerValor(rs, coluna));
                }
                registros.add(registro);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Erro ao ler os registros da tabela '" + id.getIdName() + "': " + e.getMessage(), e);
        }

        return registros;
    }




}