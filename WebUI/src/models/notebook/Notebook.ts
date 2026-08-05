import type { Cell } from "../cell/Cell";
import type { Language } from "../types/execution";
import { createID, type ID } from "../types/id";

export class Notebook {
  id: ID;
  name: string;
  language: Language;
  cells: Cell[];
  notebookScopeEnabled: boolean;
  createdAt: Date;
  updatedAt: Date;

  constructor(
    name: string,
    language: Language,
    cells?: Cell[],
    id?: ID,
    createdAt?: Date,
    updatedAt?: Date,
    notebookScopeEnabled?: boolean,
  ) {
    this.id = id ?? createID();
    this.name = name;
    this.language = language;
    this.cells = cells ?? [];
    this.notebookScopeEnabled = notebookScopeEnabled ?? false;
    this.createdAt = createdAt ?? new Date();
    this.updatedAt = updatedAt ?? new Date();
  }

  addCell(cell: Cell) {
    this.cells = [...this.cells, cell];
    this.updatedAt = new Date();
  }

  insertCell(cell: Cell, index: number) {
    this.cells.splice(index, 0, cell);
    this.updatedAt = new Date();
  }

  removeCell(cellId: ID) {
    this.cells = this.cells.filter((cell) => cell.id !== cellId);
    this.updatedAt = new Date();
  }

  updateCell(cellId: ID, updatedCell: Cell) {
    this.cells = this.cells.map((cell) => (cell.id === cellId ? updatedCell : cell));
    this.updatedAt = new Date();
  }

  moveCellUp(cellId: ID) {
    const index = this.cells.findIndex((cell) => cell.id === cellId);
    if (index <= 0) return this;

    [this.cells[index], this.cells[index - 1]] = [this.cells[index - 1], this.cells[index]];
    this.updatedAt = new Date();
  }

  moveCellDown(cellId: ID) {
    const index = this.cells.findIndex((cell) => cell.id === cellId);
    if (index === -1 || index === this.cells.length - 1) return this;

    [this.cells[index], this.cells[index + 1]] = [this.cells[index + 1], this.cells[index]];
    this.updatedAt = new Date();
  }

  getCell(cellId: ID) {
    return this.cells.find((cell) => cell.id === cellId);
  }

  getCellIndex(cellId: ID) {
    return this.cells.findIndex((cell) => cell.id === cellId);
  }

  rename(name: string) {
    return new Notebook(name, this.language, this.cells, this.id, this.createdAt, new Date(), this.notebookScopeEnabled);
  }

  setLanguage(language: Language) {
    return new Notebook(this.name, language, this.cells, this.id, this.createdAt, new Date(), this.notebookScopeEnabled);
  }

  setNotebookScope(enabled: boolean) {
    return new Notebook(this.name, this.language, this.cells, this.id, this.createdAt, new Date(), enabled);
  }
}
