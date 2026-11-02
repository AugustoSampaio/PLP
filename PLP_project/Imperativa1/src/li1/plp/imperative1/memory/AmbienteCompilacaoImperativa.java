package li1.plp.imperative1.memory;

import li1.plp.expressions1.util.Tipo;
import li1.plp.expressions2.expression.Id;
import li1.plp.expressions2.memory.*;
import li1.plp.imperative1.util.Coluna;
import li1.plp.imperative1.util.Lista;

public interface AmbienteCompilacaoImperativa extends AmbienteCompilacao {

	public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
		EntradaVaziaException;

	/**
	 * Registra o esquema de uma tabela no mapa de tabelas.
	 */
	public void mapTabela(Id id, Lista<Coluna> colunas);

	/**
	 * Devolve as colunas de uma tabela, ou {@code null} se ela não estiver no mapa.
	 */
	public Lista<Coluna> getTabela(Id id);

	/**
	 * Indica se uma tabela já está no mapa de tabelas.
	 */
	public boolean existeTabela(Id id);

	

}
