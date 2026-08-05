export interface CellOutput {
  stdout: string;
  stderr: string;
  result?: unknown;
  compilationEnv?: unknown;
  executionTime: number;
  success: boolean;
}

export interface SourceCode {
  code: string;
  language: string;
}

export interface SourceRange {
  startLine: number;
  startColumn: number;
  endLine: number;
  endColumn: number;
}

export interface BindingSnapshot {
  type?: string;
  value?: string;
  display?: string;
}

export type BindingSnapshotValue = string | BindingSnapshot;

export interface ScopeSnapshot {
  name?: string;
  scope?: string;
  bindings: Record<string, BindingSnapshotValue>;
  sourceRange?: SourceRange;
}

export interface CompilationSnapshot {
  languageId?: string;
  frames: ScopeSnapshot[];
}

export interface CompilationSnapshotParseResult {
  snapshot?: CompilationSnapshot;
  error?: string;
}

export interface BNFLanguageDefinition {
  keywords?: string[];
  literals?: string[];
  types?: string[];
  builtins?: string[];
}

function isSourceRange(value: unknown): value is SourceRange {
  return (
    !!value &&
    typeof value === "object" &&
    typeof (value as SourceRange).startLine === "number" &&
    typeof (value as SourceRange).startColumn === "number" &&
    typeof (value as SourceRange).endLine === "number" &&
    typeof (value as SourceRange).endColumn === "number"
  );
}

function isScopeSnapshot(value: unknown): value is ScopeSnapshot {
  return (
    !!value &&
    typeof value === "object" &&
    !Array.isArray(value) &&
    typeof (value as ScopeSnapshot).bindings === "object" &&
    (value as ScopeSnapshot).bindings !== null
  );
}

export function describeBindingSnapshot(value: BindingSnapshotValue): BindingSnapshot {
  if (typeof value === "string") {
    return { value, display: value };
  }

  return {
    type: typeof value.type === "string" ? value.type : undefined,
    value: typeof value.value === "string" ? value.value : undefined,
    display:
      typeof value.display === "string"
        ? value.display
        : typeof value.value === "string"
          ? value.value
          : typeof value.type === "string"
            ? value.type
            : undefined,
  };
}

export function parseCompilationSnapshot(raw: unknown): CompilationSnapshot | undefined {
  return parseCompilationSnapshotResult(raw).snapshot;
}

export function parseCompilationSnapshotResult(raw: unknown): CompilationSnapshotParseResult {
  if (raw == null) return { snapshot: undefined };

  const normalized = typeof raw === "string" ? safeParseJson(raw) : raw;
  if (!normalized) {
    return {
      snapshot: undefined,
      error:
        typeof raw === "string"
          ? "Debugger compilation env is not valid JSON."
          : "Debugger compilation env is empty or undefined.",
    };
  }

  if (Array.isArray(normalized)) {
    const frames = normalized.filter(isScopeSnapshot).map((frame) => ({
      name:
        typeof (frame as any).name === "string"
          ? (frame as any).name
          : undefined,
      scope:
        typeof (frame as any).scope === "string"
          ? (frame as any).scope
          : undefined,
      bindings: frame.bindings as Record<string, BindingSnapshotValue>,
      sourceRange: isSourceRange(frame.sourceRange) ? frame.sourceRange : undefined,
    }));

    if (frames.length === 0) {
      return {
        snapshot: undefined,
        error: "Debugger compilation env array did not contain any valid scope frames.",
      };
    }
    return { snapshot: { frames } };
  }

  if (typeof normalized === "object") {
    const snapshot = normalized as Partial<CompilationSnapshot> & { frames?: unknown };
    const frames = Array.isArray(snapshot.frames)
      ? snapshot.frames.filter(isScopeSnapshot).map((frame) => ({
          name:
            typeof (frame as any).name === "string"
              ? (frame as any).name
              : undefined,
          scope:
            typeof (frame as any).scope === "string"
              ? (frame as any).scope
              : undefined,
          bindings: frame.bindings as Record<string, BindingSnapshotValue>,
          sourceRange: isSourceRange(frame.sourceRange) ? frame.sourceRange : undefined,
        }))
      : [];

    if (frames.length === 0) {
      return {
        snapshot: undefined,
        error: Array.isArray(snapshot.frames)
          ? "Debugger compilation env frames were present but none matched the expected shape."
          : "Debugger compilation env did not contain any scope frames.",
      };
    }

    return {
      snapshot: {
        languageId: typeof snapshot.languageId === "string" ? snapshot.languageId : undefined,
        frames,
      },
    };
  }

  return {
    snapshot: undefined,
    error: "Debugger compilation env has an unsupported shape.",
  };
}

function safeParseJson(raw: string): unknown {
  try {
    return JSON.parse(raw);
  } catch {
    return undefined;
  }
}
export interface Language {
  name: string;
  version?: string;
  bnf: BNFLanguageDefinition;
  run(sourceCode: string, input?: string): CellOutput | Promise<CellOutput>;
}
