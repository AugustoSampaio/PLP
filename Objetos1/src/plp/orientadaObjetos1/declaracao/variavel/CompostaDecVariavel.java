package plp.orientadaObjetos1.declaracao.variavel;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.util.Tipo;

/**
 * Declaração de variável composta.
 */
public class CompostaDecVariavel implements DecVariavel {
	/**
	 * Primeira declaração de variável.
	 */
	private DecVariavel declaracao1;
	/**
	 * Restante da declaração de variáveis.
	 */
	private DecVariavel declaracao2;

	/**
	 * Construtor.
	 * 
	 * @param parametro1
	 *            Primeira declaraçao de variável.
	 * @param parametro2
	 *            Restante da declaração de variáveis.
	 */
	public CompostaDecVariavel(DecVariavel declaracao1, DecVariavel declaracao2) {
		this.declaracao1 = declaracao1;
		this.declaracao2 = declaracao2;
	}

	/**
	 * Obtém o tipo de uma variável nessa declaração.
	 * 
	 * @param id
	 *            O identificador da variável.
	 * @return o tipo deste identificador nessa declaraçao.
	 * @throws VariavelNaoDeclaradaException
	 *             Quando nao há nenhuma variável declarada com esse id na
	 *             declaração.
	 */
	public Tipo getTipo(Id id) throws VariavelNaoDeclaradaException {
		Tipo tipo;
		try {
			tipo = declaracao1.getTipo(id);
		} catch (VariavelNaoDeclaradaException e) {
			tipo = declaracao2.getTipo(id);
		}
		return tipo;

	}

	/**
	 * Cria um mapeamento dos identificadores para os valores das expressões
	 * desta declaração composta no AmbienteExecucao
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            valores.
	 * @return o ambiente modificado pelas inicializações das variáveis.
	 * @throws ObjetoJaDeclaradoException
	 * @throws ObjetoNaoDeclaradoException
	 * @throws ClasseNaoDeclaradaException
	 * @throws ClasseJaDeclaradaException
	 * @throws VariavelNaoDeclaradaException
	 * @throws VariavelJaDeclaradaException
	 * @throws ProcedimentoNaoDeclaradoException
	 * @throws ProcedimentoJaDeclaradoException
	 */
	public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
			ObjetoNaoDeclaradoException, ObjetoJaDeclaradoException {
		return declaracao2.elabora(declaracao1.elabora(ambiente));
	}

	/**
	 * Verifica se as declarações estão bem tipadas, ou seja, se as expressões
	 * de inicialização estão bem tipadas.
	 * 
	 * @param ambiente
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            seus tipos.
	 * @return <code>true</code> se os tipos da declaração são válidos;
	 *         <code>false</code> caso contrario.
	 */
	public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException,
			ClasseNaoDeclaradaException, ClasseJaDeclaradaException {
		return declaracao1.checaTipo(ambiente)
				&& declaracao2.checaTipo(ambiente);
	}
}
