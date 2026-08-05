import { FiFile, FiTrash2 } from "react-icons/fi";

import { useWorkspace } from "../../hooks/useWorkspace";
import { languageToShortName, languageToStyles } from "../../lib/utils";

interface NotebooksListProps {
  onSelect?: () => void;
}

export function NotebooksList({ onSelect }: NotebooksListProps) {
  const { workspace, selectedNotebookId, selectNotebook, removeNotebook } = useWorkspace();

  return (
    <ul className="list-none m-0 p-[10px] grid gap-2 flex-1 min-h-0 overflow-y-auto content-start">
      {workspace.notebooks.map((notebook) => (
        <li key={notebook.id} className="overflow-hidden">
          <div className="group/item relative overflow-hidden">
            <button
              type="button"
              className={`w-full flex items-center overflow-hidden gap-3 text-left border rounded-lg px-3 py-2 cursor-pointer text-gray-900 hover:border-gray-200 ${
                notebook.id === selectedNotebookId ? "border-gray-100 bg-gray-100" : "border-white"
              }`}
              onClick={() => {
                selectNotebook(notebook.id);
                onSelect?.();
              }}
            >
              <div className="relative flex flex-col items-center justify-center">
                <FiFile className="text-gray-500" />
                <span
                  className={`absolute -bottom-1.5 translate-x-1.5 ${languageToStyles(notebook.language)} px-0.5 rounded-[4px] text-[8px] font-bold`}
                >
                  {languageToShortName(notebook.language)}
                </span>
              </div>
              <div className="flex-1 whitespace-nowrap overflow-hidden text-ellipsis">{notebook.name}</div>
            </button>
            {workspace.notebooks.length > 1 && (
              <button
                type="button"
                className={`absolute right-2 top-1/2 -translate-y-1/2 opacity-0 group-hover/item:opacity-100 transition-opacity p-1 rounded text-gray-400 hover:text-gray-600 ${notebook.id === selectedNotebookId ? "bg-gray-100/75" : "bg-white/75"} cursor-pointer disabled:opacity-30 disabled:cursor-not-allowed`}
                disabled={workspace.notebooks.length === 1}
                onClick={(e) => {
                  e.stopPropagation();
                  if (confirm(`Delete "${notebook.name}"? This cannot be undone.`)) {
                    removeNotebook(notebook.id);
                  }
                }}
                aria-label="Delete notebook"
              >
                <FiTrash2 size={14} />
              </button>
            )}
          </div>
        </li>
      ))}
    </ul>
  );
}
