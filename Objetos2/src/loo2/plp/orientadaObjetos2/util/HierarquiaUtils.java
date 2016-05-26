package loo2.plp.orientadaObjetos2.util;

import loo2.plp.expressions2.expression.Id;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.util.Tipo;
import loo2.plp.orientadaObjetos1.util.TipoPrimitivo;
import loo2.plp.orientadaObjetos2.memoria.AmbienteCompilacaoOO2;

public class HierarquiaUtils {

	/**
	 * Retorna <code>true</code> se <code>tipoFilho</code> eh um subtipo de <code>tipoPai</code>.
	 * Se <code>tipoFilho == tipoPai</code> OU <code>tipoFilho.equals(tipoPai)</code> forem <code>true</code>,
	 * este metodo retorna <code>false</code>.
	 * @param tipoFilho
	 * @param tipoPai
	 * @param ambiente
	 * @return
	 * @throws ClasseNaoDeclaradaException
	 */
	public static boolean ehSubTipo(Tipo tipoFilho, Tipo tipoPai, AmbienteCompilacaoOO2 ambiente) throws ClasseNaoDeclaradaException {
		if(tipoFilho instanceof TipoPrimitivo || tipoPai instanceof TipoPrimitivo){
			return false;
		}
		
		boolean ehSubTipo = false;
		Id idPai;
		SuperClasseMap superClasseMap = ambiente.getSuperClasse(tipoFilho.getTipo());
		
		if(superClasseMap != null){
			idPai = superClasseMap.getSuperClasse();
			
			while(idPai != null){
				if(idPai.equals(tipoPai.getTipo())){
					ehSubTipo = true;
					break;
				}
				SuperClasseMap superClassePai = ambiente.getSuperClasse(idPai);
				
				if(superClassePai != null){
					idPai = superClassePai.getSuperClasse();
				}else {
					idPai = null;
				}
			}
		}
		
		return ehSubTipo;
	}

}
