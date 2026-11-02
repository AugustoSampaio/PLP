package li1.plp.imperative1.util;
import li1.plp.expressions2.expression.Id;
import li1.plp.expressions1.util.Tipo;


/**
 * Representa a definição de uma coluna na sintaxe da ImpSQL.
 *
 * Cada coluna é composta por um identificador, que representa seu nome,
 * e um tipo, representado pela interface {@link Tipo}.
 *
 * BNF: Coluna ::= Id Tipo
 * Tipo pode ser "int", "boolean" ou "string".
 */
public class Coluna {

    private Id id;
    private Tipo tipo;

    public Coluna(Id id, Tipo tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Id getId() {
        return this.id;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    
    @Override
    public String toString() {
        return id.getIdName() + " " + tipo.getNome();
    }
}