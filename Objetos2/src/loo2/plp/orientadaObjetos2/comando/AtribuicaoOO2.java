package loo2.plp.orientadaObjetos2.comando;

import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.comando.Atribuicao;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.expressao.Expressao;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.LeftExpression;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;
import loo2.plp.orientadaObjetos1.util.TipoClasse;
import loo2.plp.orientadaObjetos2.memoria.DefClasseOO2;

public class AtribuicaoOO2 extends Atribuicao {

	public AtribuicaoOO2(LeftExpression av, Expressao expressao) {
		super(av, expressao);
	}
	
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente) throws VariavelNaoDeclaradaException, ClasseNaoDeclaradaException {
    	boolean retorno = super.checaTipo(ambiente);
    	
    	if (!retorno) {
    		Tipo tipoLeftExpression = av.getTipo(ambiente);
    		Tipo tipoExpressao = expressao.getTipo(ambiente);
  
    		if ((tipoLeftExpression instanceof TipoClasse) && (tipoExpressao instanceof TipoClasse)) {
    			DefClasseOO2 defClasseLeft = (DefClasseOO2) ambiente.getDefClasse(tipoLeftExpression.getTipo());
    			DefClasseOO2 defClasseRight = (DefClasseOO2) ambiente.getDefClasse(tipoExpressao.getTipo());
    			retorno = this.defClasseRightExtendsDefClasseLeft(ambiente, defClasseLeft, defClasseRight);
    		}
    	}
    	
    	return retorno;
    }

	private boolean defClasseRightExtendsDefClasseLeft(AmbienteCompilacaoOO1 ambiente, DefClasseOO2 defClasseLeft, DefClasseOO2 defClasseRight) throws ClasseNaoDeclaradaException {
		boolean retorno = false;
		
		if (defClasseRight.getNomeSuperClasse() != null) {
			DefClasseOO2 defClasseMae = (DefClasseOO2) ambiente.getDefClasse(defClasseRight.getNomeSuperClasse());
			
			if (defClasseMae.getIdClasse().getIdName().equals(defClasseLeft.getIdClasse().getIdName())) {
				retorno = true;
			} else {
				retorno = this.defClasseRightExtendsDefClasseLeft(ambiente, defClasseLeft, defClasseMae);
			}
		}
		return retorno;
	}

}
