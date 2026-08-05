import type { CellType } from "../cell/Cell";
import type { CellOutput, Language } from "./execution";
import type { ID } from "./id";

export interface CellState {
  id: ID;
  type: CellType;
  content: string;
  isEditing: boolean;
  output?: CellOutput;
  createdAt: Date;
  updatedAt: Date;
}

export interface NotebookState {
  id: ID;
  name: string;
  language: Language;
  cells: CellState[];
  createdAt: Date;
  updatedAt: Date;
}

export interface WorkspaceState {
  id: ID;
  name: string;
  notebooks: NotebookState[];
  createdAt: Date;
  updatedAt: Date;
}
