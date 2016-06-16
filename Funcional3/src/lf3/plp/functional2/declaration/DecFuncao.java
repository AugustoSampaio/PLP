package lf3.plp.functional2.declaration;

import static lf3.plp.expressions1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lf3.plp.expressions1.util.Tipo;
import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.expression.Valor;
import lf3.plp.expressions2.memory.AmbienteCompilacao;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional1.declaration.DeclaracaoFuncional;
import lf3.plp.functional1.util.TipoFuncao;
import lf3.plp.functional1.util.TipoPolimorfico;
import lf3.plp.functional2.expression.ValorFuncao;

/**
 * @author S�rgio
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class DecFuncao implements DeclaracaoFuncional {
	private Id id;
	private ValorFuncao valorFuncao;

	public DecFuncao(Id idFun, ValorFuncao valorFuncao) {
		this.id = idFun;
		this.valorFuncao = valorFuncao;
	}

	/**
	 * Retorna uma representacao String desta expressao. Util para depuracao.
	 * 
	 * @return uma representacao String desta expressao.
	 */
	@Override
	public String toString() {
		return String.format("fun %s (%s) = %s", id, listToString(valorFuncao
				.getListaId(), ","), getExpressao());
	}

	public Id getId() {
		return id;
	}

	public Expressao getExpressao() {
		return valorFuncao.getExp();
	}

	public ValorFuncao getFuncao() {
		return valorFuncao;
	}

	/**
	 * Retorna a aridade da funcao declarada. Variaveis tem aridade 0.
	 * 
	 * @return a aridade da funcao declarada.
	 */
	private int getAridade() {
		return valorFuncao.getAridade();
	}

	/**
	 * Realiza a verificacao de tipos desta declara��o.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *         <code>false</code> caso contrario.
	 * @exception VariavelNaoDeclaradaException
	 *                se existir um identificador nao declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException
	 *                se existir um identificador declarado mais de uma vez no
	 *                mesmo bloco do ambiente.
	 */
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		// Mapeia a pr�pria fun��o no ambiente para permitir recurs�o.
		ambiente.map(id, tipo);

		boolean result = valorFuncao.checaTipo(ambiente);
		ambiente.restaura();
		return result;
	}

	/**
	 * Retorna os tipos possiveis da fun��o declarada.
	 * 
	 * @param amb
	 *            o ambiente que contem o mapeamento entre identificadores e
	 *            tipos.
	 * @return os tipos possiveis desta declara��o.
	 * @exception VariavelNaoDeclaradaException
	 *                se houver uma vari&aacute;vel n&atilde;o declarada no
	 *                ambiente.
	 * @exception VariavelJaDeclaradaException
	 *                se houver uma mesma vari&aacute;vel declarada duas vezes
	 *                no mesmo bloco do ambiente.
	 * @precondition this.checaTipo();
	 */
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		amb.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		amb.map(id, tipo);

		Tipo result = valorFuncao.getTipo(amb);
		amb.restaura();
		return result;
	}

	public void setValorFuncao(ValorFuncao valorFuncao) {
		this.valorFuncao = valorFuncao;
	}

	public DecFuncao clone() {
		return new DecFuncao(this.id.clone(), this.valorFuncao.clone());
	}
	
	@Override
	public void elabora(AmbienteExecucao amb, AmbienteExecucao aux) throws VariavelJaDeclaradaException {
		aux.map(getId(), getFuncao());
		
		//passos a mais
		AmbienteExecucao ambienteClone = amb.clone();
		ambienteClone.incrementa();
		ambienteClone.map(getId(), getFuncao());
		getFuncao().setId(getId());
	}

	@Override
	public void elabora(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException {
		aux.map(getId(), getTipo(amb));
		
	}

	@Override
	public void incluir(AmbienteExecucao amb, AmbienteExecucao aux) throws VariavelJaDeclaradaException {
		amb.map(getId(), aux.get(getId()));
		
	}

	@Override
	public void incluir(AmbienteCompilacao amb, AmbienteCompilacao aux, boolean incluirCuringa) throws VariavelJaDeclaradaException {
		boolean ehCuringa = (aux.get(getId()) == TipoPolimorfico.CURINGA);
		boolean incluir = (ehCuringa&&incluirCuringa) || (!ehCuringa);
		if(incluir) amb.map(getId(), aux.get(getId()));
	}

	@Override
	public void reduzir(AmbienteExecucao amb) {
		setValorFuncao((ValorFuncao)getFuncao().reduzir(amb));
	}
}
