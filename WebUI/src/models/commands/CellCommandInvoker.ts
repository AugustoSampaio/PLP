import type { CellCommand } from "./CellCommands";

export class CellCommandInvoker {
  async execute(command: CellCommand) {
    return await command.execute();
  }
}
