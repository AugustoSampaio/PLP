package loo2.plp.orientadaObjetos2.expressao.leftExpression;

import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.expressao.This;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.AcessoAtributoThis;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;
import loo2.plp.orientadaObjetos2.memoria.DefClasseOO2;

public class AcessoAtributoThisOO2 extends AcessoAtributoThis {

	public AcessoAtributoThisOO2(This varThis, Id id) {
		super(varThis, id);
	}
	
	private boolean checaTipoClasseMae (AmbienteCompilacaoOO1 ambiente, Id idClasseMae) throws ClasseNaoDeclaradaException {
		boolean retorno = false;
		DefClasseOO2 defSuperClasse = (DefClasseOO2) ambiente.getDefClasse(idClasseMae);
		
		try {
			defSuperClasse.getTipoAtributo(super.getId());
			retorno = true;
		} catch (VariavelNaoDeclaradaException atrib) {
			if (defSuperClasse.getNomeSuperClasse() != null) {
				retorno = this.checaTipoClasseMae(ambiente, defSuperClasse.getNomeSuperClasse());	
			}
   	 	}
   	 		
		return retorno;
	}
	
	@Override
	public boolean checaTipo(AmbienteCompilacaoOO1 ambiente) {
        boolean resposta = false;
        try {
              resposta = varThis.checaTipo(ambiente);
              if (resposta) {
            	  resposta = false;
                  Tipo tipo = varThis.getTipo(ambiente);
                  DefClasseOO2 defClasse = (DefClasseOO2) ambiente.getDefClasse(tipo.getTipo());
                  
                  if (defClasse.getNomeSuperClasse() != null) {
                	  resposta = this.checaTipoClasseMae(ambiente, defClasse.getNomeSuperClasse());
                  }
                  
                  // Se a verificacao pelo atributo nas classes maes nao encontrou nada
                  if (!resposta) {
                      defClasse.getTipoAtributo(super.getId());
                      resposta = true;  
                  }
              }
        } catch (VariavelNaoDeclaradaException atrib) {
              resposta = false;
        } catch (ClasseNaoDeclaradaException clas) {
              resposta = false;
        }
        return resposta;
	}
	
	private Tipo getTipoAtributoHerdado(AmbienteCompilacaoOO1 ambiente, Id idClasse, Id idAtributo) throws ClasseNaoDeclaradaException {
		Tipo retorno = null;
    	DefClasseOO2 defClasse = (DefClasseOO2) ambiente.getDefClasse(idClasse);
    	
		try {
			retorno = defClasse.getTipoAtributo(idAtributo);
		} catch (VariavelNaoDeclaradaException atrib) {
			if (defClasse.getNomeSuperClasse() != null) {
				retorno = this.getTipoAtributoHerdado(ambiente, defClasse.getNomeSuperClasse(), idAtributo);	
			}
   	 	}
		return retorno;
	}
	
    /**
     * Obt�m o tipo do atributo acessado.
     * @param ambiente o ambiente com o mapeamento de identificadores a tipos.
     * @return true, se foi associado um tipo a esse identificador acessado no
     * escopo corrente.
     * @throws VariavelNaoDeclaradaException
     * @throws ClasseNaoDeclaradaException
     */
    public Tipo getTipo(AmbienteCompilacaoOO1 ambiente) throws VariavelNaoDeclaradaException,ClasseNaoDeclaradaException{
        //Logo abaixo obtenho a definicao da Classe (seus m�todos e atributos).
        //this.getTipo() devera retornar uma instancia de TipoClasse e assim, TipoClasse.getTipo()
        //retorna o id (contendo o nome da classe) associado ao tipo dela
    	DefClasseOO2 defClasse = (DefClasseOO2) ambiente.getDefClasse(varThis.getTipo(ambiente).getTipo());
        
    	Tipo retorno = null;
        if (defClasse.getNomeSuperClasse() != null) {
        	retorno = this.getTipoAtributoHerdado(ambiente, defClasse.getNomeSuperClasse(), super.getId());
        }
        
        // Se nenhuma das minhas classes m�es possuem o atributo procuro localmente
        if (retorno == null) {
        	retorno = defClasse.getTipoAtributo(super.getId());
        }
        return retorno;
    }

}
