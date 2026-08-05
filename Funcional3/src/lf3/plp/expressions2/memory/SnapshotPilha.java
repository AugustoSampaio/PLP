package lf3.plp.expressions2.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SnapshotPilha<T> {
	protected List<HashMap<String, Object>> pilhaSnapshot;
	protected Stack<HashMap<String, Object>> pilhaSnapshotFrames;
	protected Stack<Map<String, Object>> pilhaSnapshotBindings;
	protected Stack<Boolean> pilhaSnapshotMarkers;

	public SnapshotPilha() {
		pilhaSnapshot = new ArrayList<HashMap<String, Object>>();
		pilhaSnapshotFrames = new Stack<HashMap<String, Object>>();
		pilhaSnapshotBindings = new Stack<Map<String, Object>>();
		pilhaSnapshotMarkers = new Stack<Boolean>();
	}

	public void incrementa() {
		pilhaSnapshotMarkers.push(Boolean.FALSE);
	}

	public void registraSourceRange(SourceRange sourceRange) {
		registraSourceRange(sourceRange, null);
	}

	public void registraSourceRange(SourceRange sourceRange, String scope) {
		if (!pilhaSnapshotMarkers.empty()) {
			boolean visible = pilhaSnapshotMarkers.pop();
			if (!visible) {
				pilhaSnapshotMarkers.push(Boolean.TRUE);

				HashMap<String, Object> snapshotFrame = new LinkedHashMap<String, Object>();
				Map<String, Object> bindings = new LinkedHashMap<String, Object>();
				snapshotFrame.put("bindings", bindings);
				if (scope != null) {
					snapshotFrame.put("scope", scope);
				}
				if (sourceRange != null) {
					HashMap<String, Integer> range = new LinkedHashMap<String, Integer>();
					range.put("startLine", sourceRange.getStartLine());
					range.put("startColumn", sourceRange.getStartColumn());
					range.put("endLine", sourceRange.getEndLine());
					range.put("endColumn", sourceRange.getEndColumn());
					snapshotFrame.put("sourceRange", range);
				}
				pilhaSnapshot.add(snapshotFrame);
				pilhaSnapshotFrames.push(snapshotFrame);
				pilhaSnapshotBindings.push(bindings);
			} else {
				pilhaSnapshotMarkers.push(Boolean.TRUE);
				if (sourceRange != null && !pilhaSnapshotFrames.empty()) {
					HashMap<String, Integer> range = new LinkedHashMap<String, Integer>();
					range.put("startLine", sourceRange.getStartLine());
					range.put("startColumn", sourceRange.getStartColumn());
					range.put("endLine", sourceRange.getEndLine());
					range.put("endColumn", sourceRange.getEndColumn());
					pilhaSnapshotFrames.peek().put("sourceRange", range);
				}
				if (scope != null && !pilhaSnapshotFrames.empty()) {
					pilhaSnapshotFrames.peek().put("scope", scope);
				}
			}
		}
	}

	public void restaura() {
		if (!pilhaSnapshotMarkers.empty()) {
			boolean visible = pilhaSnapshotMarkers.pop();
			if (visible) {
				if (!pilhaSnapshotFrames.empty()) {
					pilhaSnapshotFrames.pop();
				}
				if (!pilhaSnapshotBindings.empty()) {
					pilhaSnapshotBindings.pop();
				}
			}
		}
	}

	public void map(String idArg, T valorId) {
		if (!pilhaSnapshotMarkers.empty() && pilhaSnapshotMarkers.peek() && !pilhaSnapshotFrames.empty()) {
			Map<String, Object> bindings = pilhaSnapshotBindings.peek();
			bindings.put(idArg == null ? "null" : idArg, createBindingSnapshot(valorId));
		}
	}

	protected Map<String, Object> createBindingSnapshot(T valorId) {
		Map<String, Object> binding = new LinkedHashMap<String, Object>();
		binding.put("type", valorId == null ? null : valorId.getClass().getSimpleName());
		binding.put("value", valorId == null ? null : valorId.toString());
		binding.put("display", valorId == null ? null : valorId.toString());
		return binding;
	}

	public List<java.util.Map<String,Object>> getPilhaSnapshot() {
		List<java.util.Map<String,Object>> snapshot = new java.util.ArrayList<java.util.Map<String,Object>>();
		if (pilhaSnapshot != null) {
			for (HashMap<String, Object> frame : pilhaSnapshot) {
				java.util.LinkedHashMap<String, Object> frameSnapshot = new java.util.LinkedHashMap<String, Object>();
				frameSnapshot.put("bindings", frame.get("bindings"));
				if (frame.containsKey("scope")) {
					frameSnapshot.put("scope", frame.get("scope"));
				}
				if (frame.containsKey("sourceRange")) {
					frameSnapshot.put("sourceRange", frame.get("sourceRange"));
				}
				snapshot.add(frameSnapshot);
			}
		}
		return snapshot;
	}
}
