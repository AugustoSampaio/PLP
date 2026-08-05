import { useWorkspace } from "../../hooks/useWorkspace";
import { NotebooksList } from "./NotebooksList";

export function LeftSidebar() {
  const { addNotebook } = useWorkspace();

  return (
    <aside className="bg-white rounded-2xl h-full flex flex-col overflow-hidden min-h-0">
      <div className="flex items-center justify-between p-[14px] border-b border-gray-200">
        <h2>Notebooks</h2>
        <button
          type="button"
          className="border border-gray-200 bg-white text-gray-900 w-7 h-7 rounded-md cursor-pointer hover:bg-gray-100"
          onClick={addNotebook}
        >
          +
        </button>
      </div>
      <NotebooksList />
    </aside>
  );
}
