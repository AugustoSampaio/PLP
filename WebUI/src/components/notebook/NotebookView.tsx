import { FiFile } from "react-icons/fi";
import { useNotebook } from "../../hooks/useNotebook";
import { useWorkspace } from "../../hooks/useWorkspace";
import type { ID } from "../../models/types/id";
import { CellView } from "../cell/CellView";
import { NotebookSettingsMenu } from "./NotebookSettingsMenu";
import { InsertBoundary } from "./InsertBoundary";

interface NotebookViewProps {
  notebookId: ID;
}

export function NotebookView({ notebookId }: NotebookViewProps) {
  const { availableLanguages } = useWorkspace();
  const {
    notebook,
    isPreparingLanguage,
    preparationMessage,
    runtimeReady,
    runtimeStatusMessage,
    rename,
    changeLanguage,
    setNotebookScope,
    insertCodeCell,
    insertMarkdownCell,
  } = useNotebook(notebookId);

  const locked = isPreparingLanguage;
  const currentLanguage = availableLanguages.find((l) => l.name === notebook.language.name);
  const supportsNotebookScope = currentLanguage?.scopeMode === "notebook";

  return (
    <main className="bg-white rounded-2xl h-full flex flex-col overflow-hidden">
      <header className="p-[14px] border-b border-gray-200 flex items-center justify-between gap-3 shrink-0">
        <input
          className="border-0 text-[1.1rem] font-bold w-full text-gray-900 focus:outline-none disabled:opacity-55 disabled:cursor-not-allowed bg-transparent"
          value={notebook.name}
          onChange={(e) => rename(e.target.value)}
          aria-label="Notebook name"
          disabled={locked}
        />
        <NotebookSettingsMenu
          availableLanguages={availableLanguages}
          currentLanguageName={notebook.language.name}
          notebookScopeEnabled={notebook.notebookScopeEnabled}
          supportsNotebookScope={supportsNotebookScope}
          locked={locked}
          onChangeLanguage={changeLanguage}
          onToggleScope={() => setNotebookScope(!notebook.notebookScopeEnabled)}
        />
      </header>

      {locked ? (
        <div className="mx-4 mt-[10px] border border-gray-900 bg-[#f8f8f8] text-gray-900 rounded-lg px-[10px] py-2 text-[0.9rem]">
          {preparationMessage ?? "Language is loading. Cells are temporarily disabled."}
        </div>
      ) : !runtimeReady ? (
        <div className="mx-4 mt-[10px] border border-gray-200 bg-[#fafafa] text-gray-500 rounded-lg px-[10px] py-2 text-[0.9rem]">
          {runtimeStatusMessage ?? "Selected language runtime is unavailable."}
        </div>
      ) : null}

      <div className="overflow-auto p-4 flex flex-col gap-2 content-start flex-1">
        <InsertBoundary index={0} locked={locked} onInsertCode={insertCodeCell} onInsertMarkdown={insertMarkdownCell} />

        {notebook.cells.length === 0 && (
          <div className="flex-1 flex flex-col items-center justify-center py-16 text-gray-400 select-none">
            <span className="text-4xl mb-4">
              <FiFile />
            </span>
            <p className="text-sm">No cells yet. Add a code or text cell to get started.</p>
          </div>
        )}

        {notebook.cells.map((cell) => {
          const index = notebook.getCellIndex(cell.id);
          return (
            <div key={cell.id} className="flex flex-col gap-2">
              <CellView notebookId={notebookId} cellId={cell.id} />
              <InsertBoundary
                index={index + 1}
                locked={locked}
                onInsertCode={insertCodeCell}
                onInsertMarkdown={insertMarkdownCell}
              />
            </div>
          );
        })}
      </div>
    </main>
  );
}
