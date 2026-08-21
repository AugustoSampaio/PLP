package plp.debug.imperativa1;

import li1.plp.expressions1.util.Tipo;
import li1.plp.expressions2.expression.Id;
import li1.plp.expressions2.memory.VariavelJaDeclaradaException;
import li1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import li1.plp.imperative1.memory.AmbienteCompilacaoImperativa;
import li1.plp.imperative1.memory.EntradaVaziaException;
import plp.debug.core.SnapshotRecorder;

/**
 * Implementa {@link AmbienteCompilacaoImperativa} delegando todas as
 * chamadas a um ambiente real, sem alterar Imperativa1. Observa
 * incrementa()/restaura()/map() para alimentar um {@link SnapshotRecorder}.
 */
public class AmbienteCompilacaoImperativaDebug implements AmbienteCompilacaoImperativa {

	private final AmbienteCompilacaoImperativa target;
	private final SnapshotRecorder recorder = new SnapshotRecorder();

	public AmbienteCompilacaoImperativaDebug(AmbienteCompilacaoImperativa target) {
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
	public void map(Id idArg, Tipo tipoId) throws VariavelJaDeclaradaException {
		target.map(idArg, tipoId);
		recorder.recordBinding(idArg, tipoId);
	}

	@Override
	public Tipo get(Id idArg) throws VariavelNaoDeclaradaException {
		return target.get(idArg);
	}

	@Override
	public Tipo getTipoEntrada() throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException, EntradaVaziaException {
		return target.getTipoEntrada();
	}
}
