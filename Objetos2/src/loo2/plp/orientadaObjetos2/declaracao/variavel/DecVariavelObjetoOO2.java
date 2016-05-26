package loo2.plp.orientadaObjetos2.declaracao.variavel;

import loo2.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.comando.ChamadaProcedimento;
import loo2.plp.orientadaObjetos1.comando.Procedimento;
import loo2.plp.orientadaObjetos1.declaracao.variavel.DecVariavelObjeto;
import loo2.plp.orientadaObjetos1.declaracao.variavel.SimplesDecVariavel;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.expressao.ListaExpressao;
import loo2.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo2.plp.orientadaObjetos1.expressao.valor.ValorNull;
import loo2.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo2.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo2.plp.orientadaObjetos1.util.Tipo;
import loo2.plp.orientadaObjetos1.util.TipoClasse;
import loo2.plp.orientadaObjetos2.comando.NewOO2;
import loo2.plp.orientadaObjetos2.memoria.AmbienteCompilacaoOO2;
import loo2.plp.orientadaObjetos2.memoria.AmbienteExecucaoOO2;
import loo2.plp.orientadaObjetos2.memoria.DefClasseOO2;
import loo2.plp.orientadaObjetos2.util.HierarquiaUtils;

public class DecVariavelObjetoOO2 extends DecVariavelObjeto {

	private ListaExpressao parametrosReais;

	public DecVariavelObjetoOO2(Tipo tipo, Id objeto, Id classe,
			ListaExpressao parametrosReais) {
		super(tipo, objeto, classe);
		this.parametrosReais = parametrosReais;
	}

	public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ObjetoNaoDeclaradoException, ObjetoJaDeclaradoException,
			ClasseNaoDeclaradaException {

		AmbienteExecucaoOO2 aux = (AmbienteExecucaoOO2) new SimplesDecVariavel(
				getTipo(), getObjeto(), new ValorNull()).elabora(ambiente);

		try {
			aux = new NewOO2(getObjeto(), getClasse(), parametrosReais)
					.executar(aux);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aux;
	}

	public boolean checaTipo(AmbienteCompilacaoOO1 ambiente) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException, ClasseJaDeclaradaException, ClasseNaoDeclaradaException {

		boolean booleanSuper = false;
		Tipo tpClasse = new TipoClasse(this.getClasse());
		if (tpClasse.eValido(ambiente) && this.getTipo().eValido(ambiente)) {
			booleanSuper = tpClasse.equals(this.getTipo()) ||
							HierarquiaUtils.ehSubTipo(tpClasse, this.getTipo(),
														(AmbienteCompilacaoOO2) ambiente);
			ambiente.map(this.getObjeto(), tpClasse);
		}
		
		Tipo tipoClasse = getObjeto().getTipo(ambiente);
		DefClasseOO2 defClasse = (DefClasseOO2) ambiente.getDefClasse(tipoClasse.getTipo());
		
		Procedimento metodo = defClasse.getConstrutor().getProcedimento();
		
		boolean resposta = false;
		
		if (metodo != null) {
			try {
				ambiente.incrementa();
				resposta = new ChamadaProcedimento(metodo, parametrosReais).checaTipo(ambiente);
				ambiente.restaura();
			} catch (ProcedimentoNaoDeclaradoException e) {
				throw new RuntimeException("Construtor nao declarado.");
			}
		}
		
		return booleanSuper && resposta;
	}
}
