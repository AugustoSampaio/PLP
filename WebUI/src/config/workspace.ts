import { CodeCell } from "../models/cell/CodeCell";
import { MarkdownCell } from "../models/cell/MarkdownCell";
import { Notebook } from "../models/notebook/Notebook";
import { Workspace } from "../models/workspace/Workspace";
import { AVAILABLE_LANGUAGES } from "./languages";

export const INITIAL_WORKSPACE = new Workspace("Untitled Workspace", [
	new Notebook("Notebook 1", AVAILABLE_LANGUAGES[0], [
		new MarkdownCell("# Welcome\nWrite notes and explanations here."),
		new CodeCell('length "abcd" + 6'),
	]),
]);
