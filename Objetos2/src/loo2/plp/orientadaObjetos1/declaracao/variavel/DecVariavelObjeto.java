package loo2.plp.orientadaObjetos1.declaracao.variavel;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.comando.New;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.expressao.valor.ValorNull;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;
import loo2.plp.orientadaObjetos1.util.TipoClasse;

/**
 * Classe que representa a declara�ao de uma vari�vel do tipo objeto.
 */
public class DecVariavelObjeto implements DecVariavel {
	/**
	 * Tipo da vari�vel declarado.
	 */
	private Tipo tipo;
	/**
	 * Identificador representando o objeto.
	 */
	private Id objeto;
	/**
	 * Idenficador representando a classe da qual objeto � uma inst�ncia.
	 */
	private Id classe;

	/**
	 * Construtor.
	 * 
	 * @param tipo
	 *            Tipo declarado da vari�vel.
	 * @param objeto
	 *            Identificador do objeto.
	 * @param classe
	 *            Classe da qual objeto � uma inst�ncia.
	 */
	public DecVariavelObjeto(Tipo tipo, Id objeto, Id classe) {
		this.tipo = tipo;
		this.objeto = objeto;
		this.classe = classe;
	}

	/**
	 * Retorna o tipo do identificador a ser declarado no AmbienteCompilacao
	 * 
	 * @param id
	 *            o identificador da declaracao
	 * @return o tipo do identificador
	 */
	public Tipo getTipo(Id id) throws VariavelNaoDeclaradaException {
		if (this.objeto.equals(id)) {
			return tipo;
		} else {
			throw new VariavelNaoDeclaradaException(id);
		}
	}

	/**
	 * Cria um mapeamento do identificador para o objeto no ambiente de
	 * execu��o.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            valores.
	 * @return o ambiente modificado pela inicializa��o da vari�vel.
	 */
	public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
			ObjetoJaDeclaradoException, ObjetoNaoDeclaradoException {
		AmbienteExecucaoOO1 aux = new SimplesDecVariavel(tipo, objeto,
				new ValorNull()).elabora(ambiente);
		aux = new New(objeto, classe).executar(aux);
		return aux;
	}

	/**
	 * 
	 * Verifica se o tipo da classe associada � v�lido (se existe).
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre objetos e suas
	 *            classes.
	 * @return <code>true</code> a classe existe <code>false</code> caso
	 *         contrario.
	 * 
	 */
	public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ClasseJaDeclaradaException, ClasseNaoDeclaradaException {
		boolean resposta = false;
		TipoClasse tpClasse = new TipoClasse(classe);
		if (tpClasse.eValido(ambiente) && tipo.eValido(ambiente)) {
			resposta = tpClasse.equals(tipo);
			ambiente.map(objeto, tpClasse);
		}
		return resposta;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public Id getObjeto() {
		return objeto;
	}

	public Id getClasse() {
		return classe;
	}
}