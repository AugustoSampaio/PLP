import { useEffect, useRef, useState } from "react";

import { useWorkspaceStore } from "../contexts/workspace-store-context";
import { CodeCell } from "../models/cell/CodeCell";
import { parseCompilationSnapshotResult, type SourceRange } from "../models/types/execution";

export function useDebugger() {
  const store = useWorkspaceStore();

  const workspace = store((state) => state.workspace);
  const selectedNotebookId = store((state) => state.selectedNotebookId);
  const selectedCellId = store((state) => state.selectedCellIds[selectedNotebookId]);
  const activeSourceRange = store((state) => state.activeSourceRange);
  const setActiveSourceRange = store((state) => state.setActiveSourceRange);

  const notebook = selectedNotebookId ? workspace.getNotebook(selectedNotebookId) : undefined;
  const selectedCell = notebook && selectedCellId ? notebook.getCell(selectedCellId) : undefined;

  const debuggerCell = selectedCell instanceof CodeCell ? selectedCell : undefined;
  const debuggerLanguageName = notebook?.language.name;

  const rawCompilationEnv = debuggerCell?.output?.compilationEnv;
  const compilationSnapshotResult = parseCompilationSnapshotResult(rawCompilationEnv);
  const compilationEnv = compilationSnapshotResult.snapshot?.frames;

  // Track the content snapshot at compile time to detect staleness.
  // Using a ref so the effect only re-runs when rawCompilationEnv changes (cell ran),
  // not on every content keystroke.
  const debuggerCellRef = useRef(debuggerCell);
  debuggerCellRef.current = debuggerCell;

  const [compiledSnapshot, setCompiledSnapshot] = useState<{ cellId: string; content: string } | undefined>(undefined);

  useEffect(() => {
    if (rawCompilationEnv && debuggerCellRef.current) {
      setCompiledSnapshot({ cellId: debuggerCellRef.current.id, content: debuggerCellRef.current.content });
    } else {
      setCompiledSnapshot(undefined);
    }
  }, [rawCompilationEnv]);

  const isStale = Boolean(
    compilationEnv &&
      debuggerCell &&
      compiledSnapshot?.cellId === debuggerCell.id &&
      compiledSnapshot.content !== debuggerCell.content,
  );

  return {
    debuggerCellCode: debuggerCell?.content,
    debuggerCellExecutionOrder: debuggerCell?.executionOrder,
    debuggerLanguageName,
    compilationEnv,
    compilationEnvError: compilationSnapshotResult.error,
    hasCompilationEnv: rawCompilationEnv != null,
    activeSourceRange,
    isStale,
    selectScope: (range?: SourceRange) => setActiveSourceRange(range, debuggerCell?.id),
  };
}
