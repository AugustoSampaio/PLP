import type { ID } from "../types/id";
import { Cell } from "./Cell";

export class MarkdownCell extends Cell {
  readonly type = "markdown" as const;

  constructor(content: string, id?: ID, createdAt?: Date, updatedAt?: Date, isEditing?: boolean) {
    super(content, id, createdAt, updatedAt, isEditing);
  }

  updateContent(content: string) {
    this.content = content;
    this.updatedAt = new Date();
  }

  setEditing(isEditing: boolean) {
    this.isEditing = isEditing;
    this.updatedAt = new Date();
  }
}
