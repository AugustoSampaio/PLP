import { CodeCell } from "../models/cell/CodeCell";
import { MarkdownCell } from "../models/cell/MarkdownCell";
import { Notebook } from "../models/notebook/Notebook";
import { Workspace } from "../models/workspace/Workspace";
import type { CellOutput } from "../models/types/execution";
import type { ID } from "../models/types/id";
import type { NotebookLanguage } from "../config/languages";

interface SerializedCell {
  id: string;
  type: "code" | "markdown";
  content: string;
  input?: string;
  output?: CellOutput;
  executionOrder?: number;
  createdAt: string;
  updatedAt: string;
}

interface SerializedNotebook {
  id: string;
  name: string;
  languageName: string;
  notebookScopeEnabled: boolean;
  cells: SerializedCell[];
  createdAt: string;
  updatedAt: string;
}

interface SerializedWorkspace {
  version: "1.0";
  id: string;
  name: string;
  notebooks: SerializedNotebook[];
  createdAt: string;
  updatedAt: string;
}

export function exportWorkspace(workspace: Workspace): void {
  const data: SerializedWorkspace = {
    version: "1.0",
    id: workspace.id,
    name: workspace.name,
    createdAt: workspace.createdAt.toISOString(),
    updatedAt: workspace.updatedAt.toISOString(),
    notebooks: workspace.notebooks.map((nb) => ({
      id: nb.id,
      name: nb.name,
      languageName: nb.language.name,
      notebookScopeEnabled: nb.notebookScopeEnabled,
      createdAt: nb.createdAt.toISOString(),
      updatedAt: nb.updatedAt.toISOString(),
      cells: nb.cells.map((cell) => {
        const base: SerializedCell = {
          id: cell.id,
          type: cell.type,
          content: cell.content,
          createdAt: cell.createdAt.toISOString(),
          updatedAt: cell.updatedAt.toISOString(),
        };
        if (cell instanceof CodeCell) {
          base.input = cell.input ?? undefined;
          base.output = cell.output;
          base.executionOrder = cell.executionOrder;
        }
        return base;
      }),
    })),
  };

  const blob = new Blob([JSON.stringify(data, null, 2)], {
    type: "application/json",
  });
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = `${workspace.name.replace(/[/\\?%*:|"<>]/g, "_")}.plpnb`;
  a.click();
  URL.revokeObjectURL(url);
}

export function readWorkspaceFile(file: File, availableLanguages: NotebookLanguage[]): Promise<Workspace> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      try {
        const data = JSON.parse(e.target?.result as string) as SerializedWorkspace;
        if (data.version !== "1.0") {
          reject(new Error(`Unsupported file version: ${data.version}`));
          return;
        }

        const notebooks = data.notebooks.map((nb) => {
          const language = availableLanguages.find((l) => l.name === nb.languageName) ?? availableLanguages[0];
          const cells = nb.cells.map((sc) => {
            if (sc.type === "code") {
              const cell = new CodeCell(
                sc.content,
                undefined,
                sc.id as ID,
                new Date(sc.createdAt),
                new Date(sc.updatedAt),
                false,
                sc.input,
              );
              if (sc.output) cell.withOutput(sc.output, sc.executionOrder);
              return cell;
            }
            return new MarkdownCell(sc.content, sc.id as ID, new Date(sc.createdAt), new Date(sc.updatedAt), false);
          });

          return new Notebook(
            nb.name,
            language,
            cells,
            nb.id as ID,
            new Date(nb.createdAt),
            new Date(nb.updatedAt),
            nb.notebookScopeEnabled ?? false,
          );
        });

        resolve(new Workspace(data.name, notebooks, data.id as ID, new Date(data.createdAt), new Date(data.updatedAt)));
      } catch {
        reject(new Error("Invalid .plpnb file"));
      }
    };
    reader.onerror = () => reject(new Error("Failed to read file"));
    reader.readAsText(file);
  });
}
