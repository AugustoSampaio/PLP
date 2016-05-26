/*
 * Universidade Federal de Pernambuco - UFPE
 * Centro de Inform�tica - CIn
 * 
 * Paradigmas de Linguagem de Programa��o - PLP
 * 
 * Tipo: TipoFuncao
 */
package lf1.plp.functional1.util;

import static lf1.plp.expressions1.util.ToStringProvider.listToString;

import java.util.Iterator;
import java.util.List;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Esta classe representa o tipo de uma fun��o.
 * 
 * Caso o tipo do dom�nio da fun��o tamb�m seja um objeto dessa classe a fun��o
 * representada por esse objeto � uma fun��o que recebe uma fun��o como
 * par�metro, logo, trata-se de um caso de suporte a fun��es de alta ordem.
 * 
 * Se o tipo da imagem da fun��o tamb�m for um objeto da classe TipoFuncao
 * trata-se de uma fun��o com m�ltiplos par�metros. Assim, o tipo do retorno da
 * fun��o ser� sempre o tipo da imagem do �ltimo objeto dessa classe.
 * 
 * @author Joabe Jesus (jbjj@cin.ufpe.br)
 */
public class TipoFuncao implements Tipo {

	/**
	 * O tipo do dom�nio da fun��o.
	 */
	private List<Tipo> dominio;

	/**
	 * O tipo da imagem (o tipo de retorno) da fun��o.
	 */
	private Tipo imagem;

	/**
	 * Construtor da classe que representa um tipo fun��o (T1 x ... x Tn -> T).
	 * 
	 * @param dominio
	 *            A lista dos tipos do dom�nio da fun��o (T1 x ... x Tn).
	 * @param imagem
	 *            O tipo da imagem da fun��o (T).
	 */
	public TipoFuncao(List<Tipo> dominio, Tipo imagem) {
		this.dominio = dominio;
		this.imagem = imagem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lf1.plp.expressions1.util.Tipo#getNome()
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
	 * @see lf1.plp.expressions1.util.Tipo#eIgual(lf1.plp.expressions1.util.Tipo)
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
	 * @see lf1.plp.expressions1.util.Tipo#intersecao(lf1.plp.expressions1.util.Tipo)
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
	 * Este m�todo � usado para limpar os tipos curingas, pois ap�s a aplica��o
	 * os mesmos podem estar instanciados e isto pode influenciar um erro de
	 * tipos na pr�xima aplica��o.
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
		// Infere os par�metros
		Iterator<Tipo> it = getDominio().iterator();
		Tipo tipoArg;
		for (Expressao valorReal : parametrosFormais) {
			tipoArg = valorReal.getTipo(ambiente);
			tipoArg.eIgual(it.next());
		}

		// Obtem o resultado. 
		Tipo ret = getImagem();
		// Caso seja um tipo polimorfico procura a mais espec�fica instancia��o.
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
