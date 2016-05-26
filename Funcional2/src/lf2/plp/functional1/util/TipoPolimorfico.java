/*
 * Universidade Federal de Pernambuco - UFPE
 * Centro de Inform�tica - CIn
 * 
 * Paradigmas de Linguagem de Programa��o - PLP
 * 
 * Tipo: TipoQualquer
 */
package lf2.plp.functional1.util;

import lf2.plp.expressions1.util.Tipo;
import lf2.plp.expressions1.util.TipoPrimitivo;

/**
 * Esta classe representa um tipo polim�rfico, ou seja, que � v�lido como
 * qualquer dos tipos da linguagem.
 * 
 * Exemplo: let fun Id x = x in Id(2)
 * 
 * O tipo inferido para "x" ser� um curinga ("?"). O tipo instanciado ser�
 * {@link TipoPrimitivo#INTEIRO} (do valor 2).
 * 
 * @author Joabe Jesus (jbjj@cin.ufpe.br)
 */
public class TipoPolimorfico implements Tipo {

	public static final Tipo CURINGA = new TipoPolimorfico();

	/**
	 * O tipo inferido. <code>null</code> at� ser inferido.
	 * <code>CURINGA</code> se for realmente polimorfico.
	 */
	private Tipo tipoInferido = null;

	/**
	 * O tipo instanciado. Deve estar de acordo com o tipo inferido caso haja
	 * algum tipo inferido.
	 */
	private Tipo tipoInstanciado;

	/**
	 * Construtor da classe que representa tipo polim�rfico.
	 */
	public TipoPolimorfico() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lf2.plp.expressions1.util.Tipo#getNome()
	 */
	public String getNome() {
		String nome = "?";
		if (ehCoringa()) {
			if (jaInstanciou()) {
				nome = tipoInstanciado.getNome();
			}
		} else {
			if (jaInferiu()) {
				nome = tipoInferido.getNome();
			}
		}
		return nome;
	}

	public Tipo getTipoInstanciado() {
		return this.tipoInstanciado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lf2.plp.expressions1.util.Tipo#eInteiro()
	 */
	public boolean eInteiro() {
		return this.eIgual(TipoPrimitivo.INTEIRO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lf2.plp.expressions1.util.Tipo#eBooleano()
	 */
	public boolean eBooleano() {
		return this.eIgual(TipoPrimitivo.BOOLEANO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lf2.plp.expressions1.util.Tipo#eString()
	 */
	public boolean eString() {
		return this.eIgual(TipoPrimitivo.STRING);
	}

	public boolean eIgual(Tipo tipo) {
		boolean ret = false;

		if (tipo == this)
			return true;

		if (jaInferiu()) {
			if (ehCoringa()) {
				if (jaInstanciou())
					return this.tipoInstanciado.eIgual(tipo);
				else {
					this.tipoInstanciado = tipo;
					return true;
				}
			} else
				return this.tipoInferido.eIgual(tipo);

		}

		if (tipo instanceof TipoPolimorfico) {
			if (((TipoPolimorfico) tipo).tipoInferido != null)
				return ((TipoPolimorfico) tipo).eIgual(this);
			else {
				// Ainda n�o inferiu nem this nem obj.

				// "Inferiu" o tipo de obj!
				((TipoPolimorfico) tipo).tipoInferido = this;
				((TipoPolimorfico) tipo).tipoInstanciado = this;
				// OBS: this ainda continua n�o inferido.

				ret = true;
			}
		} else {
			// Inferiu o tipo!
			this.tipoInferido = tipo;
			this.tipoInstanciado = tipo;
			ret = true;
		}

		return ret;
	}

	private boolean jaInstanciou() {
		return tipoInstanciado != null;
	}

	private boolean ehCoringa() {
		return tipoInferido == CURINGA;
	}

	private boolean jaInferiu() {
		return tipoInferido != null;
	}

	public boolean eValido() {
		boolean ret = false;
		if (jaInferiu()) {
			if (ehCoringa()) {
				ret = jaInstanciou() && tipoInstanciado.eValido();
			} else {
				ret = tipoInferido.eValido();
			}
		}
		return ret;
	}

	/**
	 * Retorna o tipo inferido por este tipo polim�rfico, se estiver v�lido.
	 * Caso contr�rio, retorna o pr�prio tipo polim�rfico.
	 * 
	 * @return
	 */
	public Tipo inferir() {
		if (eValido())
			return this.tipoInferido;

		if (!(this.tipoInferido instanceof TipoPolimorfico)) {
			this.tipoInferido = CURINGA;
		}
		// Este tipo deve ser o mesmo de outro tipo polimorfico.
		return this;

	}

	public void limpar() {
		this.tipoInstanciado = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lf2.plp.expressions1.util.Tipo#intersecao(lf2.plp.expressions1.util.Tipo)
	 */
	public Tipo intersecao(Tipo outroTipo) {
		if (outroTipo.eIgual(this))
			return outroTipo;
		else
			return null;
	}

	@Override
	public String toString() {
		return getNome();
	}

}
