package plp.imperative2.declaration;

import java.util.ArrayList;
import java.util.List;

import plp.expressions1.util.Tipo;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.imperative1.memory.AmbienteCompilacaoImperativa;
import plp.imperative1.util.Lista;

public class ListaDeclaracaoParametro extends Lista<DeclaracaoParametro> {

	public ListaDeclaracaoParametro() {
	}

	public ListaDeclaracaoParametro(DeclaracaoParametro declaracao) {
		super(declaracao, null);
	}

	public ListaDeclaracaoParametro(DeclaracaoParametro declaracao,
			ListaDeclaracaoParametro listaDeclaracao) {
		super(declaracao, listaDeclaracao);
	}

	public boolean checaTipo(AmbienteCompilacaoImperativa ambiente)
			throws VariavelNaoDeclaradaException {
		boolean resposta;
		if (getHead() != null) {
			if (getTail() != null) {
				resposta = getHead().checaTipo(ambiente)
						&& ((ListaDeclaracaoParametro) getTail())
								.checaTipo(ambiente);
			} else {
				resposta = getHead().checaTipo(ambiente);
			}
		} else {
			resposta = true;
		}
		return resposta;
	}

	/**
	 * Cria um mapeamento do identificador para o tipo do parametro desta
	 * declaração no AmbienteCompilacaoImperativa2
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificador e seu
	 *            tipo.
	 * @return o ambiente modificado pela declaração do parametro.
	 */
	public AmbienteCompilacaoImperativa elabora(
			AmbienteCompilacaoImperativa ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		AmbienteCompilacaoImperativa resposta;
		if (getHead() != null) {
			if (getTail() != null) {
				resposta = ((ListaDeclaracaoParametro) getTail())
						.elabora(getHead().elabora(ambiente));
			} else {
				resposta = getHead().elabora(ambiente);
			}
		} else {
			resposta = ambiente;
		}
		return resposta;
	}

	public List<Tipo> getTipos() {
		ArrayList<Tipo> retorno = new ArrayList<Tipo>();

		DeclaracaoParametro headTemp = this.head;
		Lista<DeclaracaoParametro> tailTemp = this.tail;

		while (headTemp != null) {
			//retorno.add(head.getTipo());
			retorno.add(headTemp.getTipo());
			
			if (tailTemp != null) {
				headTemp = tailTemp.getHead();
				tailTemp = tailTemp.getTail();
			} else {
				headTemp = null;
			}
		}

		return retorno;
	}

}
