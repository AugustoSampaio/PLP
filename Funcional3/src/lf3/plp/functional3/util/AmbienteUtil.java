package lf3.plp.functional3.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lf3.plp.expressions2.expression.Expressao;
import lf3.plp.expressions2.expression.Id;
import lf3.plp.expressions2.expression.Valor;
import lf3.plp.expressions2.memory.AmbienteExecucao;
import lf3.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf3.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf3.plp.functional3.declaration.DecPadrao;
import lf3.plp.functional3.expression.ExpCons;
import lf3.plp.functional3.expression.ValorLista;
import lf3.plp.functional3.util.padrao.ExpPadrao;

public class AmbienteUtil {
	
	public static void includeValueBindings(AmbienteExecucao ambiente,
			Map<Id, Valor> mapIdValor) throws VariavelJaDeclaradaException {
		
		for ( Map.Entry<Id, Valor> mapeamento : mapIdValor.entrySet() ) {
			Id id = mapeamento.getKey();
			Valor valor = mapeamento.getValue();
			ambiente.map(id, valor);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<Id, Valor> resolveParametersBindings(AmbienteExecucao ambiente,
			DecPadrao decPadrao, List<Expressao> argumentosAplicacao)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		
		Map<Id, Valor> mapIdValor = new HashMap<Id, Valor>();
		
		List<ExpPadrao> parametrosFuncao = decPadrao.getPadrao().getListaExpPadrao();
		Iterator<Expressao> iterArgsApplicao = argumentosAplicacao.iterator();
		
		for ( ExpPadrao expParam : parametrosFuncao ) {
			Expressao argumento = iterArgsApplicao.next();
			
			if ( expParam.getAridade() == 1 ) {
				Id id = (Id) expParam.getExpressao();
				mapIdAmbiente(ambiente, mapIdValor, id, argumento);
			}
			else if ( expParam.getAridade() == 2 ) {
				ExpCons expCons = (ExpCons) expParam.getExpressao();
				mapExpConsAmbiente(ambiente, mapIdValor, expCons, argumento);
			}
		}
		
		return mapIdValor;
	}
	
	private static void mapIdAmbiente(AmbienteExecucao ambiente,
			Map<Id, Valor> mapIdValor, Id id, Expressao argumento) {
		
		Valor valorReal = argumento.avaliar(ambiente);
		mapIdValor.put(id, valorReal);
	}
	
	private static void mapExpConsAmbiente(AmbienteExecucao ambiente,
			Map<Id, Valor> mapIdValor, ExpCons expCons, Expressao argumento) {
		
		ValorLista valorLista = (ValorLista) argumento.avaliar(ambiente);
		
		/* Mapeia no ambiente a express�o � esquerda */
		Id expEsq = (Id) expCons.getEsq();
		mapIdAmbiente(ambiente, mapIdValor, expEsq, valorLista.getHead());
		
		/* Mapeia no ambiente a express�o � direita */
		Id expDir = (Id) expCons.getDir();
		Valor valorTail = valorLista.getTail();
		if ( valorTail == null ) {
			valorTail = ValorLista.getInstancia(null, null);
		}
		mapIdAmbiente(ambiente, mapIdValor, expDir, valorTail);
	}
}
