package li1.plp.imperative1.declaration;

import li1.plp.expressions2.memory.IdentificadorJaDeclaradoException;
import li1.plp.expressions2.memory.IdentificadorNaoDeclaradoException;
import li1.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li1.plp.imperative1.memory.AmbienteExecucaoImperativa;
import li1.plp.imperative1.util.Lista;


/**
 * Lista de create-use do início do programa.
 * Segue o formato da ListaDeclaracaoOO de Objetos 2.
 */
public class ListaCreateUse extends Lista<CreateUse> {

    public ListaCreateUse() {
    }

    public ListaCreateUse(CreateUse createUse) {
        super(createUse, new ListaCreateUse());
    }

    public ListaCreateUse(CreateUse createUse, ListaCreateUse listaCreateUse) {
        super(createUse, listaCreateUse);
    }

    /**
     * Elabora cada create-use, na ordem em que aparece.
     */
    public AmbienteExecucaoImperativa elabora(AmbienteExecucaoImperativa ambiente)
            throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException {
        if (length() == 0) { // já que pode estar vazia
            return ambiente;
        }
        return ((ListaCreateUse) getTail()).elabora(getHead().elabora(ambiente));
    }

    /**
     * Verifica cada create-use, na ordem em que aparece.
     * Para no primeiro que não estiver bem tipado.
     */
    public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
            throws IdentificadorJaDeclaradoException, IdentificadorNaoDeclaradoException {
        if (length() == 0) { // já que pode estar vazia
            return true;
        }
        return getHead().checaTipo(ambiente)
                && ((ListaCreateUse) getTail()).checaTipo(ambiente);
    }
}
