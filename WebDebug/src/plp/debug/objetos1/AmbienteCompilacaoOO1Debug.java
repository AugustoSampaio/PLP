package plp.debug.objetos1;

import loo1.plp.expressions2.expression.Id;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.DefClasse;
import loo1.plp.orientadaObjetos1.util.Tipo;
import plp.debug.core.SnapshotRecorder;

/**
 * Implementa {@link AmbienteCompilacaoOO1} delegando todas as chamadas a um
 * ambiente real, sem alterar Objetos1. Observa incrementa()/restaura()/
 * map() para alimentar um {@link SnapshotRecorder}.
 */
public class AmbienteCompilacaoOO1Debug implements AmbienteCompilacaoOO1 {

	private final AmbienteCompilacaoOO1 target;
	private final SnapshotRecorder recorder = new SnapshotRecorder();

	public AmbienteCompilacaoOO1Debug(AmbienteCompilacaoOO1 target) {
		this.target = target;
	}

	public SnapshotRecorder getRecorder() {
		return recorder;
	}

	@Override
	public void incrementa() {
		target.incrementa();
		recorder.pushFrame();
	}

	@Override
	public void restaura() {
		target.restaura();
		recorder.popFrame();
	}

	@Override
	public void map(Id idArg, Tipo tipoId) throws loo1.plp.expressions2.memory.VariavelJaDeclaradaException {
		target.map(idArg, tipoId);
		recorder.recordBinding(idArg, tipoId);
	}

	@Override
	public Tipo get(Id idArg) throws VariavelNaoDeclaradaException {
		return target.get(idArg);
	}

	@Override
	public void mapDefClasse(Id idArg, DefClasse defClasse) throws ClasseJaDeclaradaException {
		target.mapDefClasse(idArg, defClasse);
	}

	@Override
	public DefClasse getDefClasse(Id idArg) throws ClasseNaoDeclaradaException {
		return target.getDefClasse(idArg);
	}

	@Override
	public Tipo getTipo(Id idArg) throws VariavelNaoDeclaradaException {
		return target.getTipo(idArg);
	}

	@Override
	public void mapParametrosProcedimento(Id idArg, ListaDeclaracaoParametro parametrosId) throws ProcedimentoJaDeclaradoException {
		target.mapParametrosProcedimento(idArg, parametrosId);
	}

	@Override
	public ListaDeclaracaoParametro getParametrosProcedimento(Id idArg) throws ProcedimentoNaoDeclaradoException {
		return target.getParametrosProcedimento(idArg);
	}

	@Override
	public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException {
		return target.getTipoEntrada();
	}
}
