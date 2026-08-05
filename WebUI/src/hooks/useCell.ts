import { useMemo, useState } from "react";

import type { CellOutput, SourceRange } from "../models/types/execution";
import { parseCompilationSnapshot } from "../models/types/execution";
import type { ID } from "../models/types/id";
import { useWorkspaceStore } from "../contexts/workspace-store-context";
import { useNotebook } from "./useNotebook";
import { CodeCell } from "../models/cell/CodeCell";
import type { NotebookLanguage } from "../config/languages";
import { buildNotebookScopeCode } from "../lib/utils";

export function useCell(notebookId: ID, cellId: ID) {
  const { isPreparingLanguage, runtimeReady, selectedCellId, selectCell } = useNotebook(notebookId);

  const store = useWorkspaceStore();

  const notebook = store((state) => state.workspace.getNotebook(notebookId));
  const cell = notebook?.getCell(cellId);

  if (!notebook) throw new Error(`Notebook ${notebookId} not found`);
  if (!cell) throw new Error(`Cell ${cellId} not found in notebook ${notebookId}`);

  const [isExecuting, setIsExecuting] = useState<boolean>(false);

  const updateCellContent = store((state) => state.updateCellContent);
  const updateCellInput = store((state) => state.updateCellInput);
  const setCellEditing = store((state) => state.setCellEditing);
  const setCellOutput = store((state) => state.setCellOutput);
  const clearCellOutput = store((state) => state.clearCellOutput);
  const moveCellUp = store((state) => state.moveCellUp);
  const moveCellDown = store((state) => state.moveCellDown);
  const deleteCell = store((state) => state.deleteCell);

  const activeSourceRange = store((state) => state.activeSourceRange);
  const activeSourceCellId = store((state) => state.activeSourceCellId);
  const selectedSourceRange = store((state) => state.selectedSourceRange);
  const setActiveSourceRange = store((state) => state.setActiveSourceRange);
  const setSelectedSourceRange = store((state) => state.setSelectedSourceRange);

  const rawCompilationEnv = cell instanceof CodeCell ? cell.output?.compilationEnv : undefined;
  const compilationEnv = useMemo(() => parseCompilationSnapshot(rawCompilationEnv)?.frames, [rawCompilationEnv]);

  const localActiveSourceRange = useMemo<SourceRange | undefined>(() => {
    if (!activeSourceRange || !compilationEnv || activeSourceCellId !== cell.id) return undefined;
    return compilationEnv.find((frame) => {
      const range = frame.sourceRange;
      return (
        range &&
        range.startLine === activeSourceRange.startLine &&
        range.startColumn === activeSourceRange.startColumn &&
        range.endLine === activeSourceRange.endLine &&
        range.endColumn === activeSourceRange.endColumn
      );
    })?.sourceRange;
  }, [activeSourceRange, compilationEnv, activeSourceCellId, cell.id]);

  const runCell = async (input = "") => {
    if (isPreparingLanguage || !runtimeReady) return;

    setIsExecuting(true);
    updateCellInput(notebookId, cellId, input);

    try {
      if (!(cell instanceof CodeCell)) return;
      // ensure the running cell stays selected so Debugger/RightPanel shows its output
      selectCell(cellId);

      const language = notebook.language as NotebookLanguage;
      const useNotebookScope = notebook.notebookScopeEnabled && language.scopeMode === "notebook";
      const sourceCode = useNotebookScope ? buildNotebookScopeCode(notebook.cells, cellId) : cell.content;

      console.log(sourceCode);

      const output: CellOutput = await notebook.language.run(sourceCode, input);
      setCellOutput(notebookId, cellId, output);
    } finally {
      setIsExecuting(false);
    }
  };

  return {
    cell,
    isRunning: isExecuting,
    isSelected: selectedCellId === cellId,
    scopeMode: (notebook.language as NotebookLanguage).scopeMode,
    compilationEnv,
    localActiveSourceRange,
    selectedSourceRange,
    updateContent: (content: string) => updateCellContent(notebookId, cellId, content),
    setEditing: (isEditing: boolean) => setCellEditing(notebookId, cellId, isEditing),
    clearOutput: () => clearCellOutput(notebookId, cellId),
    moveUp: () => moveCellUp(notebookId, cellId),
    moveDown: () => moveCellDown(notebookId, cellId),
    delete: () => deleteCell(notebookId, cellId),
    runCell: (input = "") => runCell(input),
    selectCell: () => selectCell(cellId),
    activateScopeRange: (range: SourceRange) => setActiveSourceRange(range, cell.id),
    clearActiveScope: () => setActiveSourceRange(undefined),
    commitSelectionRange: () => {
      if (activeSourceRange) setSelectedSourceRange(activeSourceRange, cell.id);
    },
  };
}
