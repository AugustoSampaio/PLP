import { useEffect, useRef, useState } from "react";

import { useWorkspace } from "./hooks/useWorkspace";
import { RightPanel } from "./components/workspace/RightPanel";
import { LeftSidebar } from "./components/workspace/LeftSidebar";
import { NotebookView } from "./components/notebook/NotebookView";
import { HeaderMenu } from "./components/workspace/HeaderMenu";
import { FiCode } from "react-icons/fi";

const MIN_LEFT_WIDTH = 180;
const MIN_CENTER_WIDTH = 420;
const MIN_RIGHT_WIDTH = 220;
const INITIAL_LEFT_WIDTH = 250;
const INITIAL_RIGHT_WIDTH = 280;
const HANDLE_SPACE = 20;

function App() {
  const { selectedNotebookId, workspace, renameWorkspace, clearActiveScope } = useWorkspace();

  const containerRef = useRef<HTMLDivElement | null>(null);
  const titleInputRef = useRef<HTMLInputElement | null>(null);

  const [leftPanelWidth, setLeftPanelWidth] = useState(INITIAL_LEFT_WIDTH);
  const [rightPanelWidth, setRightPanelWidth] = useState(INITIAL_RIGHT_WIDTH);
  const [draggingSide, setDraggingSide] = useState<"left" | "right" | null>(null);
  const [isEditingTitle, setIsEditingTitle] = useState(false);
  const [titleDraft, setTitleDraft] = useState(workspace.name);

  useEffect(() => {
    setTitleDraft(workspace.name);
  }, [workspace.name]);

  useEffect(() => {
    if (isEditingTitle) {
      titleInputRef.current?.focus();
      titleInputRef.current?.select();
    }
  }, [isEditingTitle]);

  const commitTitle = () => {
    const name = titleDraft.trim() ?? workspace.name;

    if (name !== workspace.name) {
      renameWorkspace(name);
    }

    setTitleDraft(name);
    setIsEditingTitle(false);
  };

  useEffect(() => {
    if (!draggingSide) return;

    const onMouseMove = (event: MouseEvent) => {
      const rect = containerRef.current?.getBoundingClientRect();
      if (!rect) return;

      if (draggingSide === "left") {
        const max = rect.width - rightPanelWidth - MIN_CENTER_WIDTH - HANDLE_SPACE;
        const raw = event.clientX - rect.left;
        setLeftPanelWidth(Math.max(MIN_LEFT_WIDTH, Math.min(raw, Math.max(MIN_LEFT_WIDTH, max))));
      }

      if (draggingSide === "right") {
        const max = rect.width - leftPanelWidth - MIN_CENTER_WIDTH - HANDLE_SPACE;
        const raw = rect.right - event.clientX;
        setRightPanelWidth(Math.max(MIN_RIGHT_WIDTH, Math.min(raw, Math.max(MIN_RIGHT_WIDTH, max))));
      }
    };

    const onMouseUp = () => setDraggingSide(null);

    window.addEventListener("mousemove", onMouseMove);
    window.addEventListener("mouseup", onMouseUp);

    return () => {
      window.removeEventListener("mousemove", onMouseMove);
      window.removeEventListener("mouseup", onMouseUp);
    };
  }, [draggingSide, leftPanelWidth, rightPanelWidth]);

  useEffect(() => {
    const handlePointerDown = (event: PointerEvent) => {
      const target = event.target;
      if (!(target instanceof HTMLElement)) return;

      if (target.closest("[data-debugger-panel]") || target.closest("[data-code-cell]")) {
        return;
      }

      clearActiveScope();
    };

    window.addEventListener("pointerdown", handlePointerDown);
    return () => window.removeEventListener("pointerdown", handlePointerDown);
  }, [clearActiveScope]);

  if (!selectedNotebookId) {
    return <div className="empty-state">No notebooks available.</div>;
  }

  return (
    <div className="flex flex-col h-full">
      <div className="flex items-center px-[14px] pt-3 shrink-0 gap-2">
        <div className="bg-gray-900 p-2 rounded-full text-white text-2xl">
          <FiCode />
        </div>
        {isEditingTitle ? (
          <input
            ref={titleInputRef}
            type="text"
            className="m-0 text-xl text-gray-900 border-0 rounded-md py-1 px-2 font-[inherit] outline-none focus:shadow-[0_0_0_1px_rgba(17,24,39,0.1)] w-auto"
            value={titleDraft}
            onChange={(e) => setTitleDraft(e.target.value)}
            onBlur={commitTitle}
            onKeyDown={(e) => {
              if (e.key === "Enter") e.currentTarget.blur();
              else if (e.key === "Escape") {
                setTitleDraft(workspace.name);
                setIsEditingTitle(false);
              }
            }}
            aria-label="Edit workspace name"
          />
        ) : (
          <button
            type="button"
            className="m-0 text-xl text-gray-900 cursor-pointer py-1 px-2 border-0 bg-transparent rounded-md transition-colors font-[inherit] hover:shadow-[0_0_0_1px_rgba(17,24,39,0.1)]"
            onClick={() => setIsEditingTitle(true)}
          >
            {workspace.name}
          </button>
        )}

        <div className="ml-auto flex items-center gap-3">
          <HeaderMenu />
        </div>
      </div>

      <div
        ref={containerRef}
        className={`flex items-stretch flex-1 min-h-0 p-[14px] gap-0${draggingSide ? " select-none cursor-col-resize" : ""}`}
      >
        <div className="hidden md:block min-w-0 h-full relative" style={{ width: `${leftPanelWidth}px` }}>
          <LeftSidebar />
        </div>

        <div
          className="hidden md:block relative w-[10px] h-full cursor-col-resize shrink-0 before:content-[''] before:absolute before:left-1/2 before:top-[10px] before:bottom-[10px] before:w-px before:bg-gray-300 before:-translate-x-1/2 before:transition-colors hover:before:bg-gray-400"
          aria-label="Resize left panel"
          onMouseDown={() => setDraggingSide("left")}
        />

        <div className="flex-1 min-w-0 h-full md:px-[10px] relative">
          <NotebookView notebookId={selectedNotebookId} />
        </div>

        <div
          className="hidden md:block relative w-[10px] h-full cursor-col-resize shrink-0 before:content-[''] before:absolute before:left-1/2 before:top-[10px] before:bottom-[10px] before:w-px before:bg-gray-300 before:-translate-x-1/2 before:transition-colors hover:before:bg-gray-400"
          aria-label="Resize right panel"
          onMouseDown={() => setDraggingSide("right")}
        />

        <div className="hidden md:block min-w-0 h-full relative" style={{ width: `${rightPanelWidth}px` }}>
          <RightPanel />
        </div>
      </div>
    </div>
  );
}

export default App;
