package plp.debug.objetos2;

import loo2.plp.expressions2.expression.Id;
import loo2.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo2.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo2.plp.orientadaObjetos1.memoria.DefClasse;
import loo2.plp.orientadaObjetos1.util.Tipo;
import loo2.plp.orientadaObjetos2.memoria.AmbienteCompilacaoOO2;
import loo2.plp.orientadaObjetos2.util.SuperClasseMap;
import plp.debug.core.SnapshotRecorder;

/**
 * Implementa {@link AmbienteCompilacaoOO2} delegando todas as chamadas a um
 * ambiente real, sem alterar Objetos2. Observa incrementa()/restaura()/
 * map() para alimentar um {@link SnapshotRecorder}.
 */
public class AmbienteCompilacaoOO2Debug implements AmbienteCompilacaoOO2 {

	private final AmbienteCompilacaoOO2 target;
	private final SnapshotRecorder recorder = new SnapshotRecorder();

	public AmbienteCompilacaoOO2Debug(AmbienteCompilacaoOO2 target) {
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
	public void map(Id idArg, Tipo tipoId) throws loo2.plp.expressions2.memory.VariavelJaDeclaradaException {
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

	@Override
	public void mapSuperClasse(Id classe, Id superClasse) throws ClasseNaoDeclaradaException {
		target.mapSuperClasse(classe, superClasse);
	}

	@Override
	public SuperClasseMap getSuperClasse(Id classe) throws ClasseNaoDeclaradaException {
		return target.getSuperClasse(classe);
	}
}
