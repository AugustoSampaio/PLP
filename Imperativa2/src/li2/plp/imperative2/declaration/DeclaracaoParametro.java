package li2.plp.imperative2.declaration;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions2.expression.Id;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li2.plp.imperative1.memory.AmbienteCompilacaoImperativa;

public class DeclaracaoParametro {

	private Id id;

	private Tipo tipo;

	public DeclaracaoParametro(Id id, Tipo tipo) {
		this.id = id;
		this.tipo = tipo;
	}

	public Id getId() {
		return id;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente) {
		return tipo.eValido();
	}

	/**
	 * Cria um mapeamento do identificador para o tipo do parametro desta
	 * declara��o no AmbienteCompilacaoImperativa2
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificador e seu
	 *            tipo.
	 * 
	 * @return o ambiente modificado pela declara��o do parametro.
	 */
	public AmbienteCompilacaoImperativa elabora(
			AmbienteCompilacaoImperativa ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.map(id, tipo);
		return ambiente;
	}

}
