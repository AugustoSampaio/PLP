package loo1.plp.expressions2.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SnapshotPilha<T> {
	protected List<HashMap<String, Object>> pilhaSnapshot;
	protected Stack<Map<String, Object>> pilhaSnapshotFrames;
	protected Stack<Map<String, Object>> pilhaSnapshotBindings;
	protected Stack<Boolean> pilhaSnapshotMarkers;

	public SnapshotPilha() {
		pilhaSnapshot = new ArrayList<HashMap<String, Object>>();
		pilhaSnapshotFrames = new Stack<Map<String, Object>>();
		pilhaSnapshotBindings = new Stack<Map<String, Object>>();
		pilhaSnapshotMarkers = new Stack<Boolean>();
	}

	public void incrementa() {
		pilhaSnapshotMarkers.push(Boolean.TRUE);
		HashMap<String, Object> snapshotFrame = new LinkedHashMap<String, Object>();
		Map<String, Object> bindings = new LinkedHashMap<String, Object>();
		snapshotFrame.put("bindings", bindings);
		pilhaSnapshot.add(snapshotFrame);
		pilhaSnapshotFrames.push(snapshotFrame);
		pilhaSnapshotBindings.push(bindings);
	}

	public void restaura() {
		if (!pilhaSnapshotMarkers.empty() && pilhaSnapshotMarkers.pop()) {
			if (!pilhaSnapshotFrames.empty()) pilhaSnapshotFrames.pop();
			if (!pilhaSnapshotBindings.empty()) pilhaSnapshotBindings.pop();
		}
	}

	public void map(String idArg, T valorId) {
		if (!pilhaSnapshotFrames.empty()) {
			Map<String, Object> bindings = pilhaSnapshotBindings.peek();
			bindings.put(idArg == null ? "null" : idArg, createBindingSnapshot(valorId));
		}
	}

	public List<Map<String, Object>> getPilhaSnapshot() {
		List<Map<String, Object>> snapshot = new ArrayList<Map<String, Object>>();
		for (HashMap<String, Object> frame : pilhaSnapshot) {
			Map<String, Object> frameSnapshot = new LinkedHashMap<String, Object>();
			frameSnapshot.put("bindings", frame.get("bindings"));
			snapshot.add(frameSnapshot);
		}
		return snapshot;
	}

	protected Map<String, Object> createBindingSnapshot(T valorId) {
		Map<String, Object> binding = new LinkedHashMap<String, Object>();
		binding.put("type", valorId == null ? null : valorId.getClass().getSimpleName());
		binding.put("value", valorId == null ? null : valorId.toString());
		binding.put("display", valorId == null ? null : valorId.toString());
		return binding;
	}
}
