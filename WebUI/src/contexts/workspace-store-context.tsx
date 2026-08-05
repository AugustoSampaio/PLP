import { createContext, useContext, useRef, type ReactNode } from "react";

import { createWorkspaceStore, type WorkspaceStoreInstance } from "../stores/workspace-store";
import type { Workspace } from "../models/workspace/Workspace";
import type { NotebookLanguage } from "../config/languages";

const WorkspaceStoreContext = createContext<WorkspaceStoreInstance | null>(null);

export function WorkspaceStoreProvider({
  workspace,
  languages,
  children,
}: {
  workspace: Workspace;
  languages: NotebookLanguage[];
  children: ReactNode;
}) {
  const storeRef = useRef<WorkspaceStoreInstance>(null);

  if (!storeRef.current) {
    storeRef.current = createWorkspaceStore(workspace, languages);
  }

  return <WorkspaceStoreContext.Provider value={storeRef.current}>{children}</WorkspaceStoreContext.Provider>;
}

export function useWorkspaceStore(): WorkspaceStoreInstance {
  const store = useContext(WorkspaceStoreContext);
  if (!store) throw new Error("useWorkspaceStore must be used within WorkspaceStoreProvider");
  return store;
}
