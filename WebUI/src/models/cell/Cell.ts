import { createID, type ID } from "../types/id";

export type CellType = "code" | "markdown";

export abstract class Cell {
  id: ID;
  content: string;
  isEditing: boolean;
  createdAt: Date;
  updatedAt: Date;
  abstract type: CellType;

  constructor(content: string, id?: ID, createdAt?: Date, updatedAt?: Date, isEditing?: boolean) {
    this.id = id ?? createID();
    this.content = content;
    this.isEditing = isEditing ?? false;
    this.createdAt = createdAt ?? new Date();
    this.updatedAt = updatedAt ?? new Date();
  }

  abstract updateContent(content: string): void;
  abstract setEditing(isEditing: boolean): void;
}
