/**
 * Command pattern for cell-level notebook actions.
 * Each command encapsulates one request and returns the next immutable Notebook state.
 */

import type { Cell } from "../cell/Cell";
import { CodeCell } from "../cell/CodeCell";
import type { Notebook } from "../notebook/Notebook";
import type { ID } from "../types/id";

export type CellCommandType =
  | "add"
  | "insert"
  | "edit"
  | "setEditing"
  | "clearOutput"
  | "delete"
  | "moveUp"
  | "moveDown"
  | "run";

export interface CellCommand {
  readonly type: CellCommandType;
  execute(): Promise<void>;
}

export class AddCellCommand implements CellCommand {
  readonly type = "add" as const;
  private readonly notebook: Notebook;
  private readonly cell: Cell;

  constructor(notebook: Notebook, cell: Cell) {
    this.notebook = notebook;
    this.cell = cell;
  }

  async execute() {
    this.notebook.addCell(this.cell);
  }
}

export class InsertCellCommand implements CellCommand {
  readonly type = "insert" as const;
  private readonly notebook: Notebook;
  private readonly cell: Cell;
  private readonly index: number;

  constructor(notebook: Notebook, cell: Cell, index: number) {
    this.notebook = notebook;
    this.cell = cell;
    this.index = index;
  }

  async execute() {
    this.notebook.insertCell(this.cell, this.index);
  }
}

export class EditCellCommand implements CellCommand {
  readonly type = "edit" as const;
  private readonly notebook: Notebook;
  private readonly cellId: ID;
  private readonly content: string;

  constructor(notebook: Notebook, cellId: ID, content: string) {
    this.notebook = notebook;
    this.cellId = cellId;
    this.content = content;
  }

  async execute() {
    const cell = this.notebook.getCell(this.cellId);
    if (!cell) return;

    cell.updateContent(this.content);
  }
}

export class SetCellEditingCommand implements CellCommand {
  readonly type = "setEditing" as const;
  private readonly notebook: Notebook;
  private readonly cellId: ID;
  private readonly isEditing: boolean;

  constructor(notebook: Notebook, cellId: ID, isEditing: boolean) {
    this.notebook = notebook;
    this.cellId = cellId;
    this.isEditing = isEditing;
  }

  async execute() {
    const cell = this.notebook.getCell(this.cellId);
    if (!cell) return;

    cell.setEditing(this.isEditing);
  }
}

export class DeleteCellCommand implements CellCommand {
  readonly type = "delete" as const;
  private readonly notebook: Notebook;
  private readonly cellId: ID;

  constructor(notebook: Notebook, cellId: ID) {
    this.notebook = notebook;
    this.cellId = cellId;
  }

  async execute() {
    this.notebook.removeCell(this.cellId);
  }
}

export class ClearCellOutputCommand implements CellCommand {
  readonly type = "clearOutput" as const;
  private readonly notebook: Notebook;
  private readonly cellId: ID;

  constructor(notebook: Notebook, cellId: ID) {
    this.notebook = notebook;
    this.cellId = cellId;
  }

  async execute() {
    const cell = this.notebook.getCell(this.cellId);
    if (!cell || !(cell instanceof CodeCell)) return;

    cell.clearOutput();
  }
}

export class MoveCellUpCommand implements CellCommand {
  readonly type = "moveUp" as const;
  private readonly notebook: Notebook;
  private readonly cellId: ID;

  constructor(notebook: Notebook, cellId: ID) {
    this.notebook = notebook;
    this.cellId = cellId;
  }

  async execute() {
    this.notebook.moveCellUp(this.cellId);
  }
}

export class MoveCellDownCommand implements CellCommand {
  readonly type = "moveDown" as const;
  private readonly notebook: Notebook;
  private readonly cellId: ID;

  constructor(notebook: Notebook, cellId: ID) {
    this.notebook = notebook;
    this.cellId = cellId;
  }

  async execute() {
    this.notebook.moveCellDown(this.cellId);
  }
}

export class RunCellCommand implements CellCommand {
  readonly type = "run" as const;
  private readonly notebook: Notebook;
  private readonly cellId: ID;

  constructor(notebook: Notebook, cellId: ID) {
    this.notebook = notebook;
    this.cellId = cellId;
  }

  async execute() {
    const cell = this.notebook.getCell(this.cellId);
    if (!cell || !(cell instanceof CodeCell)) return;

    const output = await this.notebook.language.run(cell.content);
    cell.withOutput(output);
  }
}
