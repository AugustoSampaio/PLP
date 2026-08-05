import { useRef, useState } from "react";

import { Drawer } from "@base-ui/react/drawer";
import { Menu } from "@base-ui/react/menu";
import { FiMoreVertical, FiUpload, FiDownload, FiFile, FiCpu, FiX } from "react-icons/fi";
import { useWorkspace } from "../../hooks/useWorkspace";
import { exportWorkspace, readWorkspaceFile } from "../../lib/io";
import { NotebooksList } from "./NotebooksList";
import { DebuggerContent } from "./DebuggerContent";

const menuItemClass =
  "flex items-center gap-2 px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 cursor-pointer outline-none w-full text-left";

const drawerBackdropClass =
  "z-50 fixed inset-0 bg-black/40 data-[open]:opacity-100 data-[closed]:opacity-0 data-[starting-style]:opacity-0 transition-opacity duration-300";

const drawerPopupClass =
  "z-50 fixed inset-x-0 bottom-0 max-h-[70vh] rounded-t-2xl bg-white shadow-xl flex flex-col outline-none translate-y-0 data-[starting-style]:translate-y-full data-[ending-style]:translate-y-full transition-transform duration-300";

export function HeaderMenu() {
  const { workspace, availableLanguages, loadWorkspace, addNotebook } = useWorkspace();
  const importInputRef = useRef<HTMLInputElement | null>(null);
  const [notebooksOpen, setNotebooksOpen] = useState(false);
  const [debuggerOpen, setDebuggerOpen] = useState(false);

  const handleImport = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;
    try {
      const imported = await readWorkspaceFile(file, availableLanguages);
      loadWorkspace(imported);
    } catch (err) {
      alert(err instanceof Error ? err.message : "Failed to import workspace");
    } finally {
      e.target.value = "";
    }
  };

  return (
    <>
      <input ref={importInputRef} type="file" accept=".plpnb" className="hidden" onChange={handleImport} />

      <div className="hidden md:flex items-center gap-3">
        <button
          type="button"
          className="flex items-center gap-2 px-4 py-2 text-sm text-gray-900 border border-gray-200 rounded-full hover:bg-gray-100 transition-colors cursor-pointer"
          onClick={() => importInputRef.current?.click()}
        >
          <FiUpload />
          Import
        </button>
        <button
          type="button"
          className="flex items-center gap-2 px-4 py-2 text-sm text-white bg-gray-900 border border-gray-900 rounded-full hover:bg-gray-700 transition-colors cursor-pointer"
          onClick={() => exportWorkspace(workspace)}
        >
          <FiDownload />
          Export
        </button>
      </div>

      <div className="md:hidden">
        <Menu.Root>
          <Menu.Trigger
            className="flex items-center justify-center w-9 h-9 rounded-full transition-colors cursor-pointer text-gray-900"
            aria-label="More options"
          >
            <FiMoreVertical />
          </Menu.Trigger>
          <Menu.Portal>
            <Menu.Positioner side="bottom" align="end" sideOffset={8} className="z-50">
              <Menu.Popup className="bg-white rounded-xl shadow-lg border border-gray-100 py-1 min-w-[160px] z-50 outline-none data-[open]:opacity-100 data-[closed]:opacity-0 data-[starting-style]:opacity-0 transition-opacity duration-150">
                <Menu.Item className={menuItemClass} onClick={() => importInputRef.current?.click()}>
                  <FiUpload size={14} />
                  Import
                </Menu.Item>
                <Menu.Item className={menuItemClass} onClick={() => exportWorkspace(workspace)}>
                  <FiDownload size={14} />
                  Export
                </Menu.Item>
                <Menu.Item className={menuItemClass} onClick={() => setNotebooksOpen(true)}>
                  <FiFile size={14} />
                  Notebooks
                </Menu.Item>
                <Menu.Item className={menuItemClass} onClick={() => setDebuggerOpen(true)}>
                  <FiCpu size={14} />
                  Debugger
                </Menu.Item>
              </Menu.Popup>
            </Menu.Positioner>
          </Menu.Portal>
        </Menu.Root>

        <Drawer.Root open={notebooksOpen} onOpenChange={setNotebooksOpen} swipeDirection="down">
          <Drawer.Portal>
            <Drawer.Backdrop className={drawerBackdropClass} />
            <Drawer.Popup className={drawerPopupClass}>
              <div className="flex items-center justify-between px-[14px] py-3 border-b border-gray-200 shrink-0">
                <Drawer.Title className="text-base font-semibold m-0">Notebooks</Drawer.Title>
                <div className="flex items-center gap-2">
                  <button
                    type="button"
                    className="border border-gray-200 bg-white text-gray-900 w-7 h-7 rounded-md cursor-pointer hover:bg-gray-100"
                    onClick={addNotebook}
                  >
                    +
                  </button>
                  <Drawer.Close className="flex items-center justify-center w-7 h-7 rounded-md text-gray-400 hover:text-gray-600 hover:bg-gray-100 cursor-pointer transition-colors">
                    <FiX size={14} />
                  </Drawer.Close>
                </div>
              </div>
              <NotebooksList onSelect={() => setNotebooksOpen(false)} />
            </Drawer.Popup>
          </Drawer.Portal>
        </Drawer.Root>

        <Drawer.Root open={debuggerOpen} onOpenChange={setDebuggerOpen} swipeDirection="down">
          <Drawer.Portal>
            <Drawer.Backdrop className={drawerBackdropClass} />
            <Drawer.Popup className={drawerPopupClass}>
              <div className="flex items-center justify-between px-[14px] py-3 border-b border-gray-200 shrink-0">
                <Drawer.Title className="text-base font-semibold m-0">Debugger</Drawer.Title>
                <Drawer.Close className="flex items-center justify-center w-7 h-7 rounded-md text-gray-400 hover:text-gray-600 hover:bg-gray-100 cursor-pointer transition-colors">
                  <FiX size={14} />
                </Drawer.Close>
              </div>
              <DebuggerContent />
            </Drawer.Popup>
          </Drawer.Portal>
        </Drawer.Root>
      </div>
    </>
  );
}
