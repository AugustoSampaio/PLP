import { useState } from "react";

import type { ID } from "../models/types/id";
import { useWorkspaceStore } from "../contexts/workspace-store-context";

export function useNotebook(notebookId: ID) {
  const store = useWorkspaceStore();

  const notebook = store((state) => state.workspace.getNotebook(notebookId));
  const availableLanguages = store((state) => state.availableLanguages);
  const selectedCellId = store((state) => state.selectedCellIds[notebookId]);

  if (!notebook) throw new Error(`Notebook ${notebookId} not found`);

  const [isPreparingLanguage, setIsPreparingLanguage] = useState(false);
  const [preparationMessage, setPreparationMessage] = useState<string | undefined>();

  const selectedLanguage = availableLanguages.find((lang) => lang.name === notebook.language.name);
  const runtimeReady = selectedLanguage?.runtimeReady ?? false;
  const runtimeStatusMessage = selectedLanguage?.runtimeStatusMessage;

  const renameNotebook = store((state) => state.renameNotebook);
  const setNotebookLanguage = store((state) => state.setNotebookLanguage);
  const setNotebookScope = store((state) => state.setNotebookScope);
  const insertCodeCell = store((state) => state.insertCodeCell);
  const insertMarkdownCell = store((state) => state.insertMarkdownCell);
  const selectCell = store((state) => state.selectCell);

  const changeLanguage = async (languageName: string) => {
    const language = availableLanguages.find((lang) => lang.name === languageName);
    if (!language || language.name === notebook.language.name) return;

    setPreparationMessage(language.preparationMessage ?? `Loading ${language.name}...`);
    setIsPreparingLanguage(true);

    try {
      await language.prepare?.();
      setNotebookLanguage(notebookId, language);
    } finally {
      setIsPreparingLanguage(false);
      setPreparationMessage(undefined);
    }
  };

  return {
    notebook,
    isPreparingLanguage,
    preparationMessage,
    runtimeReady,
    runtimeStatusMessage,
    selectedCellId,
    rename: (name: string) => renameNotebook(notebookId, name),
    changeLanguage: (languageName: string) => changeLanguage(languageName),
    setNotebookScope: (enabled: boolean) => setNotebookScope(notebookId, enabled),
    insertCodeCell: (index: number) => insertCodeCell(notebookId, index),
    insertMarkdownCell: (index: number) => insertMarkdownCell(notebookId, index),
    selectCell: (cellId: ID) => selectCell(notebookId, cellId),
  };
}
