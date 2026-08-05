import type { LanguageCode } from "../../teavm";
import type { BNFLanguageDefinition, CellOutput, Language } from "../models/types/execution";

export interface NotebookLanguage extends Language {
  runtimeReady: boolean;
  runtimeStatusMessage?: string;
  preparationMessage?: string;
  prepare?: () => Promise<void>;
  scopeMode: "notebook" | "cell";
}

function defineLanguage(name: string, scopeMode: "notebook" | "cell", bnf: BNFLanguageDefinition): NotebookLanguage {
  return {
    name,
    scopeMode,
    bnf,
    runtimeReady: true,
    preparationMessage: `Importing and compiling ${name} runtime...`,
    async prepare() {
      await Promise.resolve();
    },
    run(sourceCode: string, input = ""): CellOutput {
      const start = performance.now();
      try {
        const result = window.__runCode(name.toLowerCase() as LanguageCode, sourceCode, input);
        const rawCompilationEnv = result.compilationEnv;
        const parsedCompilationEnv = (() => {
          try {
            if (rawCompilationEnv == null) return undefined;
            return typeof rawCompilationEnv === "string" ? JSON.parse(rawCompilationEnv) : rawCompilationEnv;
          } catch {
            return rawCompilationEnv;
          }
        })();

        console.debug(`[${name}] compilationEnv`, {
          raw: rawCompilationEnv,
          parsed: parsedCompilationEnv,
        });

        return {
          stdout: result.output ?? "",
          stderr: result.message ?? "",
          result: result.output,
          compilationEnv: parsedCompilationEnv,
          executionTime: performance.now() - start,
          success: result.success,
        };
      } catch (error) {
        return {
          stdout: "",
          stderr: error instanceof Error ? error.message : "Unknown execution error",
          executionTime: performance.now() - start,
          success: false,
        };
      }
    },
  };
}

export const AVAILABLE_LANGUAGES: NotebookLanguage[] = [
  defineLanguage("Exp1", "cell", {
    keywords: ["not", "length", "and", "or"],
    literals: ["true", "false"],
  }),
  defineLanguage("Exp2", "cell", {
    keywords: ["not", "length", "and", "or", "let", "var", "in"],
    literals: ["true", "false"],
  }),
  defineLanguage("Func1", "cell", {
    keywords: ["not", "length", "and", "or", "let", "var", "in", "fun", "if", "then", "else"],
    literals: ["true", "false"],
  }),
  defineLanguage("Func2", "cell", {
    keywords: ["not", "length", "and", "or", "let", "var", "in", "fun", "fn", "if", "then", "else"],
    literals: ["true", "false"],
  }),
  defineLanguage("Func3", "cell", {
    keywords: ["not", "length", "and", "or", "let", "var", "in", "fun", "fn", "if", "then", "else", "for"],
    literals: ["true", "false"],
    builtins: ["head", "tail"],
  }),
  defineLanguage("Imp1", "notebook", {
    keywords: ["not", "length", "and", "or", "var", "while", "do", "if", "then", "else", "write", "read"],
    literals: ["true", "false"],
  }),
  defineLanguage("Imp2", "notebook", {
    keywords: [
      "not",
      "length",
      "and",
      "or",
      "var",
      "while",
      "do",
      "if",
      "then",
      "else",
      "write",
      "read",
      "proc",
      "call",
    ],
    literals: ["true", "false"],
    types: ["string", "int", "boolean"],
  }),
  defineLanguage("OO1", "notebook", {
    keywords: [
      "not",
      "length",
      "and",
      "or",
      "var",
      "while",
      "do",
      "if",
      "then",
      "else",
      "write",
      "read",
      "proc",
      "new",
      "classe",
      "this",
    ],
    literals: ["true", "false", "null"],
    types: ["string", "int", "boolean"],
  }),
  defineLanguage("OO2", "notebook", {
    keywords: [
      "not",
      "length",
      "and",
      "or",
      "var",
      "while",
      "do",
      "if",
      "then",
      "else",
      "write",
      "read",
      "proc",
      "new",
      "classe",
      "this",
      "extends",
    ],
    literals: ["true", "false", "null"],
    types: ["string", "int", "boolean"],
  }),
];
