package plp.functional3.expression;

import java.util.HashMap;
import java.util.Map;

import plp.expressions1.util.Tipo;
import plp.expressions2.expression.Expressao;
import plp.expressions2.expression.Id;
import plp.expressions2.expression.Valor;
import plp.expressions2.expression.ValorBooleano;
import plp.expressions2.memory.AmbienteCompilacao;
import plp.expressions2.memory.AmbienteExecucao;
import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.functional1.util.TipoPolimorfico;
import plp.functional2.expression.ValorIrredutivel;
import plp.functional3.util.TipoLista;

public class Gerador {

	private Id id;
	private Expressao expressao;
	private Gerador proximo;

	public Gerador(Id id, Expressao expressao) {
		this.id = id;
		this.expressao = expressao;
	}

	public Gerador getProximoGerador() {
		return proximo;
	}

	public void addProximoGerador(Gerador gerador) {
		if (this.proximo == null) {
			this.proximo = gerador;
		} else {
			this.proximo.addProximoGerador(gerador);
		}
	}

	public void gerarValores(AmbienteExecucao amb, ValorLista resultado,
			Expressao expressao, Expressao filtro)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		// Usamos uma lista auxiliar pois a lista original pode ser  
		// iterada mais de uma vez.
		ValorLista temp = (ValorLista) this.expressao.avaliar(amb);

		// Enquanto houver elementos na lista
		while (temp != null && !temp.isEmpty()) {
			amb.incrementa();

			// Avalia o proximo valor da lista
			Valor valor = temp.getHead().avaliar(amb);
			amb.map(this.id, valor);
			temp = temp.getTail();

			// Se esse for o último gerador, então devemos avaliar a expressão
			// de filtro e, se o resultado for 'true', devemos avaliar a expressão
			// em si adicionando seu resultado a lista de saída.
			if (getProximoGerador() == null) {
				if (filtro == null
						|| ((ValorBooleano) filtro.avaliar(amb)).valor()) {
					resultado.cons(expressao.avaliar(amb));
				}
			} else {
				// Se um próximo gerador encadeado devemos chamar 'gerarValores' para 
				// que esse gerador faça o bind de sua variável.
				getProximoGerador().gerarValores(amb, resultado, expressao,
						filtro);
			}

			amb.restaura();
		}
	}

	public boolean temProximoGerador() {
		return proximo != null;
	}

	public Map<Id, Tipo> checkTypeBindings(AmbienteCompilacao amb) {
		HashMap<Id, Tipo> tipos = new HashMap<Id, Tipo>();

		Tipo tipo = expressao.getTipo(amb);
		if (tipo instanceof TipoPolimorfico) {
			TipoPolimorfico tp = (TipoPolimorfico) tipo;
			tipo = tp.getTipoInstanciado();
		}
		TipoLista tipoLista = (TipoLista) tipo;
		tipos.put(id, tipoLista.getSubTipo());
		if (temProximoGerador()) {
			tipos.putAll(proximo.checkTypeBindings(amb));
		}

		return tipos;

	}

	public boolean checaTipo(AmbienteCompilacao amb) {
		return this.expressao.checaTipo(amb)
				&& (!temProximoGerador() || proximo.checaTipo(amb));
	}

	public void reduzir(AmbienteExecucao ambiente) {
		this.expressao.reduzir(ambiente);
		
		ambiente.map(this.id, new ValorIrredutivel());
	}
	
	public String toString() {
		return " for " + this.id + " in " + this.expressao;
	}
}
