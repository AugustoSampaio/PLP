import type { Notebook } from "../notebook/Notebook";
import { createID, type ID } from "../types/id";

export class Workspace {
  id: ID;
  name: string;
  notebooks: Notebook[];
  createdAt: Date;
  updatedAt: Date;

  constructor(name: string, notebooks?: Notebook[], id?: ID, createdAt?: Date, updatedAt?: Date) {
    this.id = id ?? createID();
    this.name = name;
    this.notebooks = notebooks ?? [];
    this.createdAt = createdAt ?? new Date();
    this.updatedAt = updatedAt ?? new Date();
  }

  addNotebook(notebook: Notebook) {
    this.notebooks = [...this.notebooks, notebook];
    this.updatedAt = new Date();
  }

  removeNotebook(notebookId: ID) {
    this.notebooks = this.notebooks.filter((nb) => nb.id !== notebookId);
    this.updatedAt = new Date();
  }

  updateNotebook(notebookId: ID, updatedNotebook: Notebook) {
    this.notebooks = this.notebooks.map((nb) => (nb.id === notebookId ? updatedNotebook : nb));
    this.updatedAt = new Date();
  }

  getNotebook(notebookId: ID) {
    return this.notebooks.find((nb) => nb.id === notebookId);
  }

  getAllCells() {
    return this.notebooks.flatMap((notebook) => notebook.cells.map((cell) => ({ notebookId: notebook.id, cell })));
  }

  getNotebookIndex(notebookId: ID) {
    return this.notebooks.findIndex((nb) => nb.id === notebookId);
  }

  rename(name: string) {
    this.name = name;
    this.updatedAt = new Date();
  }
}
