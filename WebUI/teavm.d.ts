export type LanguageCode = "exp1" | "exp2" | "func1" | "func2" | "func3" | "imp1" | "imp2" | "oo1"  | "oo2";

export interface RunCodeResult {
  success: boolean;
  output: string | null;
  message: string | null;
  compilationEnv: string | null;
}

declare global {
  interface Window {
    __runCode: (language: LanguageCode, sourceCode: string, input: string) => RunCodeResult;
  }
}
