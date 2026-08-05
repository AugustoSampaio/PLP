import { create } from "zustand";

import type { NotebookLanguage } from "../config/languages";
import { CodeCell } from "../models/cell/CodeCell";
import { MarkdownCell } from "../models/cell/MarkdownCell";
import { Notebook } from "../models/notebook/Notebook";
import type {
	CellOutput,
	Language,
	SourceRange,
} from "../models/types/execution";
import type { ID } from "../models/types/id";
import type { Workspace } from "../models/workspace/Workspace";

/** Creates a new instance with the same prototype, forcing Zustand to detect the change. */
function clone<T extends object>(instance: T): T {
	return Object.assign(
		Object.create(Object.getPrototypeOf(instance)),
		instance,
	);
}

export interface WorkspaceStore {
	workspace: Workspace;
	availableLanguages: NotebookLanguage[];
	selectedNotebookId: ID;
	selectedCellIds: Record<ID, ID | undefined>;
	executionCounters: Record<ID, number>;
	activeSourceRange?: SourceRange;
	activeSourceCellId?: ID;
	selectedSourceRange?: SourceRange;

	// Workspace
	selectNotebook: (id: ID) => void;
	addNotebook: () => void;
	removeNotebook: (notebookId: ID) => void;
	renameWorkspace: (name: string) => void;

	// Notebook
	renameNotebook: (notebookId: ID, name: string) => void;
	setNotebookLanguage: (notebookId: ID, language: Language) => void;
	setNotebookScope: (notebookId: ID, enabled: boolean) => void;
	selectCell: (notebookId: ID, cellId: ID) => void;
	setActiveSourceRange: (range?: SourceRange, cellId?: ID) => void;
	setSelectedSourceRange: (range?: SourceRange, cellId?: ID) => void;

	// Cells
	insertCodeCell: (notebookId: ID, index: number) => void;
	insertMarkdownCell: (notebookId: ID, index: number) => void;
	updateCellContent: (notebookId: ID, cellId: ID, content: string) => void;
	updateCellInput: (notebookId: ID, cellId: ID, input: string) => void;
	setCellEditing: (notebookId: ID, cellId: ID, isEditing: boolean) => void;
	setCellOutput: (notebookId: ID, cellId: ID, output: CellOutput) => void;
	clearCellOutput: (notebookId: ID, cellId: ID) => void;
	moveCellUp: (notebookId: ID, cellId: ID) => void;
	moveCellDown: (notebookId: ID, cellId: ID) => void;
	deleteCell: (notebookId: ID, cellId: ID) => void;

	// Import
	loadWorkspace: (workspace: Workspace) => void;

	// Selectors
	getSelectedNotebook: () => Notebook | undefined;
}

export function createWorkspaceStore(
	initialWorkspace: Workspace,
	availableLanguages: NotebookLanguage[],
) {
	const defaultLanguage = availableLanguages[0];

	return create<WorkspaceStore>()((set, get) => ({
		workspace: initialWorkspace,
		availableLanguages: availableLanguages,
		selectedNotebookId: initialWorkspace.notebooks[0]?.id,
		selectedCellIds: Object.fromEntries(
			initialWorkspace.notebooks.map((nb) => [nb.id, nb.cells[0]?.id]),
		),
		executionCounters: Object.fromEntries(
			initialWorkspace.notebooks.map((nb) => [nb.id, 0]),
		),
		activeSourceRange: undefined,
		activeSourceCellId: undefined,
		selectedSourceRange: undefined,

		selectNotebook: (id) => set({ selectedNotebookId: id }),

		addNotebook: () =>
			set((state) => {
				const nextName = `Notebook ${state.workspace.notebooks.length + 1}`;
				const notebook = new Notebook(nextName, defaultLanguage);
				state.workspace.addNotebook(notebook);
				return {
					workspace: clone(state.workspace),
					selectedNotebookId: notebook.id,
				};
			}),

		removeNotebook: (notebookId) =>
			set((state) => {
				state.workspace.removeNotebook(notebookId);
				const stillSelected = state.workspace.notebooks.some(
					(nb) => nb.id === state.selectedNotebookId,
				);
				const { [notebookId]: _dropped, ...remainingCellIds } =
					state.selectedCellIds;
				return {
					workspace: clone(state.workspace),
					selectedNotebookId: stillSelected
						? state.selectedNotebookId
						: (state.workspace.notebooks[0]?.id ?? state.selectedNotebookId),
					selectedCellIds: remainingCellIds,
				};
			}),

		renameWorkspace: (name) =>
			set((state) => {
				state.workspace.rename(name);
				return { workspace: clone(state.workspace) };
			}),

		renameNotebook: (notebookId, name) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				state.workspace.updateNotebook(notebookId, notebook.rename(name));
				return { workspace: clone(state.workspace) };
			}),

		setNotebookLanguage: (notebookId, language) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				state.workspace.updateNotebook(
					notebookId,
					notebook.setLanguage(language),
				);
				return { workspace: clone(state.workspace) };
			}),

		setNotebookScope: (notebookId, enabled) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				state.workspace.updateNotebook(
					notebookId,
					notebook.setNotebookScope(enabled),
				);
				return { workspace: clone(state.workspace) };
			}),

		selectCell: (notebookId, cellId) =>
			set((state) => {
				const prevCellId = state.selectedCellIds[notebookId];
				const updatedCellIds = {
					...state.selectedCellIds,
					[notebookId]: cellId,
				};

				if (prevCellId === cellId) {
					return { selectedCellIds: updatedCellIds };
				}

				if (prevCellId && prevCellId !== cellId) {
					const notebook = state.workspace.getNotebook(notebookId);
					const prev = notebook?.getCell(prevCellId);
					if (prev instanceof MarkdownCell && prev.isEditing) {
						prev.setEditing(false);
						notebook!.updateCell(prevCellId, clone(prev));
						state.workspace.updateNotebook(notebookId, clone(notebook!));
						return {
							workspace: clone(state.workspace),
							selectedCellIds: updatedCellIds,
						};
					}
				}

				return {
					selectedCellIds: updatedCellIds,
					activeSourceRange: undefined,
					activeSourceCellId: undefined,
					selectedSourceRange: undefined,
				};
			}),

		setActiveSourceRange: (range, cellId) =>
			set({
				activeSourceRange: range,
				activeSourceCellId: cellId,
				selectedSourceRange: undefined,
			}),

		setSelectedSourceRange: (range, cellId) =>
			set({
				activeSourceRange: range,
				activeSourceCellId: cellId,
				selectedSourceRange: range,
			}),

		insertCodeCell: (notebookId, index) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				notebook.insertCell(new CodeCell(""), index);
				state.workspace.updateNotebook(notebookId, clone(notebook));
				return { workspace: clone(state.workspace) };
			}),

		insertMarkdownCell: (notebookId, index) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				notebook.insertCell(new MarkdownCell(""), index);
				state.workspace.updateNotebook(notebookId, clone(notebook));
				return { workspace: clone(state.workspace) };
			}),

		updateCellContent: (notebookId, cellId, content) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				const cell = notebook.getCell(cellId);
				if (!cell) return state;
				cell.updateContent(content);
				notebook.updateCell(cellId, clone(cell));
				state.workspace.updateNotebook(notebookId, clone(notebook));
				return { workspace: clone(state.workspace) };
			}),

		updateCellInput: (notebookId, cellId, input) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				const cell = notebook.getCell(cellId);
				if (!(cell instanceof CodeCell)) return state;
				cell.updateInput(input);
				notebook.updateCell(cellId, clone(cell));
				state.workspace.updateNotebook(notebookId, clone(notebook));
				return { workspace: clone(state.workspace) };
			}),

		setCellEditing: (notebookId, cellId, isEditing) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				const cell = notebook.getCell(cellId);
				if (!cell) return state;
				cell.setEditing(isEditing);
				notebook.updateCell(cellId, clone(cell));
				state.workspace.updateNotebook(notebookId, clone(notebook));
				return { workspace: clone(state.workspace) };
			}),

		setCellOutput: (notebookId, cellId, output) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				const cell = notebook.getCell(cellId);
				if (!(cell instanceof CodeCell)) return state;
				const executionOrder = (state.executionCounters[notebookId] ?? 0) + 1;
				cell.withOutput(output, output.success ? executionOrder : undefined);
				notebook.updateCell(cellId, clone(cell));
				state.workspace.updateNotebook(notebookId, clone(notebook));
				return {
					workspace: clone(state.workspace),
					executionCounters: {
						...state.executionCounters,
						[notebookId]: executionOrder,
					},
				};
			}),

		clearCellOutput: (notebookId, cellId) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				const cell = notebook.getCell(cellId);
				if (!(cell instanceof CodeCell)) return state;
				cell.clearOutput();
				notebook.updateCell(cellId, clone(cell));
				state.workspace.updateNotebook(notebookId, clone(notebook));
				return { workspace: clone(state.workspace) };
			}),

		moveCellUp: (notebookId, cellId) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				notebook.moveCellUp(cellId);
				state.workspace.updateNotebook(notebookId, clone(notebook));
				return { workspace: clone(state.workspace) };
			}),

		moveCellDown: (notebookId, cellId) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				notebook.moveCellDown(cellId);
				state.workspace.updateNotebook(notebookId, clone(notebook));
				return { workspace: clone(state.workspace) };
			}),

		deleteCell: (notebookId, cellId) =>
			set((state) => {
				const notebook = state.workspace.getNotebook(notebookId);
				if (!notebook) return state;
				notebook.removeCell(cellId);
				state.workspace.updateNotebook(notebookId, clone(notebook));
				return { workspace: clone(state.workspace) };
			}),

		loadWorkspace: (workspace) =>
			set(() => ({
				workspace,
				selectedNotebookId: workspace.notebooks[0]?.id,
				selectedCellIds: Object.fromEntries(
					workspace.notebooks.map((nb) => [nb.id, nb.cells[0]?.id]),
				),
				executionCounters: Object.fromEntries(
					workspace.notebooks.map((nb) => {
						const max = nb.cells.reduce((acc, cell) => {
							if (
								cell instanceof CodeCell &&
								cell.executionOrder !== undefined
							) {
								return Math.max(acc, cell.executionOrder);
							}
							return acc;
						}, 0);
						return [nb.id, max];
					}),
				),
			})),

		getSelectedNotebook: () => {
			const { workspace, selectedNotebookId } = get();
			return workspace.getNotebook(selectedNotebookId);
		},
	}));
}

export type WorkspaceStoreInstance = ReturnType<typeof createWorkspaceStore>;
