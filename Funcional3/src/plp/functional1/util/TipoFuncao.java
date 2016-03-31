/*
 * Universidade Federal de Pernambuco - UFPE
 * Centro de Informática - CIn
 * 
 * Paradigmas de Linguagem de Programação - PLP
 * 
 * Tipo: TipoFuncao
 */
package plp.functional1.util;

import static plp.expressions1.util.ToStringProvider.listToString;

import java.util.Iterator;
import java.util.List;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.Expressao;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Esta classe representa o tipo de uma função.
 * 
 * Caso o tipo do domínio da função também seja um objeto dessa classe a função
 * representada por esse objeto é uma função que recebe uma função como
 * parâmetro, logo, trata-se de um caso de suporte a funções de alta ordem.
 * 
 * Se o tipo da imagem da função também for um objeto da classe TipoFuncao
 * trata-se de uma função com múltiplos parâmetros. Assim, o tipo do retorno da
 * função será sempre o tipo da imagem do último objeto dessa classe.
 * 
 * @author Joabe Jesus (jbjj@cin.ufpe.br)
 */
public class TipoFuncao implements Tipo {

	/**
	 * O tipo do domínio da função.
	 */
	private List<Tipo> dominio;

	/**
	 * O tipo da imagem (o tipo de retorno) da função.
	 */
	private Tipo imagem;

	/**
	 * Construtor da classe que representa um tipo função (T1 x ... x Tn -> T).
	 * 
	 * @param dominio
	 *            A lista dos tipos do domínio da função (T1 x ... x Tn).
	 * @param imagem
	 *            O tipo da imagem da função (T).
	 */
	public TipoFuncao(List<Tipo> dominio, Tipo imagem) {
		this.dominio = dominio;
		this.imagem = imagem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plp.expressions1.util.Tipo#getNome()
	 */
	public String getNome() {
		return String.format("(%s) -> %s", listToString(dominio, " x"), imagem);
	}

	public List<Tipo> getDominio() {
		return dominio;
	}

	public Tipo getImagem() {
		return imagem;
	}

	public boolean eBooleano() {
		return imagem.eBooleano();
	}

	public boolean eInteiro() {
		return imagem.eInteiro();
	}

	public boolean eString() {
		return imagem.eString();
	}

	public boolean eValido() {
		boolean ret = dominio != null;
		for (Tipo t : this.dominio) {
			ret &= t.eValido();
		}
		ret &= imagem != null && imagem.eValido();
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plp.expressions1.util.Tipo#eIgual(plp.expressions1.util.Tipo)
	 */
	public boolean eIgual(Tipo tipo) {
		boolean ret = true;
		if (tipo instanceof TipoPolimorfico)
			return tipo.eIgual(this);

		if (tipo instanceof TipoFuncao) {
			TipoFuncao tipoFuncao = (TipoFuncao) tipo;
			if (this.dominio.size() != tipoFuncao.dominio.size())
				return false;
			Iterator<Tipo> it = this.dominio.iterator();
			for (Tipo t : tipoFuncao.dominio) {
				ret &= t.eIgual(it.next());
			}
			return ret && this.imagem.eIgual(tipoFuncao.imagem);
		}

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see plp.expressions1.util.Tipo#intersecao(plp.expressions1.util.Tipo)
	 */
	public Tipo intersecao(Tipo outroTipo) {
		if (outroTipo.eIgual(this))
			return this;
		else
			return null;
	}

	@Override
	public String toString() {
		return getNome();
	}

	/**
	 * Este método é usado para limpar os tipos curingas, pois após a aplicação
	 * os mesmos podem estar instanciados e isto pode influenciar um erro de
	 * tipos na próxima aplicação.
	 */
	private void limparTiposCuringas() {
		for (Tipo tDom : getDominio()) {
			if (tDom instanceof TipoPolimorfico) {
				((TipoPolimorfico) tDom).limpar();
			}

		}
		if (getImagem() instanceof TipoPolimorfico) {
			((TipoPolimorfico) getImagem()).limpar();
		}
	}

	private boolean checkArgumentListSize(
			List<? extends Expressao> parametrosFormais) {
		return getDominio().size() == parametrosFormais.size();
	}

	private boolean checkArgumentTypes(AmbienteCompilacao ambiente,
			List<? extends Expressao> parametrosFormais)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		boolean result = true;

		Iterator<Tipo> it = getDominio().iterator();
		Tipo tipoArg;
		for (Expressao valorReal : parametrosFormais) {

			result &= valorReal.checaTipo(ambiente);

			tipoArg = valorReal.getTipo(ambiente);
			Tipo tipoDom = it.next();

			result &= tipoArg.eIgual(tipoDom);
		}
		return result;
	}

	public boolean checaTipo(AmbienteCompilacao ambiente,
			List<? extends Expressao> parametrosFormais) {
		boolean result = checkArgumentListSize(parametrosFormais)
				&& checkArgumentTypes(ambiente, parametrosFormais);
		limparTiposCuringas();
		return result;
	}

	public Tipo getTipo(AmbienteCompilacao ambiente,
			List<? extends Expressao> parametrosFormais) {
		// Infere os parâmetros
		Iterator<Tipo> it = getDominio().iterator();
		Tipo tipoArg;
		for (Expressao valorReal : parametrosFormais) {
			tipoArg = valorReal.getTipo(ambiente);
			tipoArg.eIgual(it.next());
		}

		// Obtem o resultado. 
		Tipo ret = getImagem();
		// Caso seja um tipo polimorfico procura a mais específica instanciação.
		while (ret instanceof TipoPolimorfico) {
			if (((TipoPolimorfico) ret).getTipoInstanciado() == null) {
				break;
			}
			ret = ((TipoPolimorfico) ret).getTipoInstanciado();
		}

		limparTiposCuringas();
		return ret;
	}

}
