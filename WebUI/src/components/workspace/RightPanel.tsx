import { useDebugger } from "../../hooks/useDebugger";
import { describeBindingSnapshot, type ScopeSnapshot } from "../../models/types/execution";

export function RightPanel() {
  const {
    debuggerCellCode,
    debuggerCellExecutionOrder,
    debuggerLanguageName,
    compilationEnv,
    compilationEnvError,
    hasCompilationEnv,
    activeSourceRange,
    isStale,
    selectScope,
  } = useDebugger();

  const renderCompilationEnv = (env: ScopeSnapshot[]) => {
    if (isStale) {
      return (
        <div className="rounded-md border border-amber-200 bg-amber-50 p-3 text-sm text-amber-700">
          Code has changed. Run the cell again to update the debugger.
        </div>
      );
    }

    const scopesInDisplayOrder = [...env].reverse();

    return (
      <div className="space-y-3">
        {scopesInDisplayOrder.map((frame, index) => {
          const originalIndex = env.length - index - 1;
          const entries = Object.entries(frame.bindings);
          const range = frame.sourceRange;
          const scopeLabel = frame.name
            ? frame.scope
              ? `${frame.name} · ${frame.scope}`
              : frame.name
            : frame.scope
              ? `${frame.scope}`
              : `Scope ${originalIndex + 1}`;
          return (
            <button
              key={`${originalIndex}-${range ? `${range.startLine}:${range.startColumn}` : "no-range"}`}
              type="button"
              className={`w-full rounded-md border p-3 text-left transition-colors hover:border-slate-400 hover:bg-slate-100 ${
                range &&
                activeSourceRange &&
                range.startLine === activeSourceRange.startLine &&
                range.startColumn === activeSourceRange.startColumn &&
                range.endLine === activeSourceRange.endLine &&
                range.endColumn === activeSourceRange.endColumn
                  ? "border-cyan-600 bg-cyan-50 ring-1 ring-cyan-600"
                  : "border-slate-200 bg-slate-50"
              }`}
              onClick={() => selectScope(range)}
            >
              <div className="mb-2 flex items-center justify-between gap-3">
                <div className="text-[0.72rem] font-semibold uppercase tracking-[0.18em] text-slate-500">
                  {scopeLabel}
                </div>
                {range && (
                  <div className="font-mono text-[0.72rem] text-slate-400">
                    {range.startLine}:{range.startColumn} - {range.endLine}:{range.endColumn}
                  </div>
                )}
              </div>
              {entries.length > 0 ? (
                <div className="space-y-2">
                  {entries.map(([name, value]) => {
                    const binding = describeBindingSnapshot(value as never);
                    return (
                      <div
                        key={name}
                        className="flex items-start justify-between gap-3 rounded-sm bg-white px-3 py-2 text-sm"
                      >
                        <span className="font-mono text-slate-700">{name}</span>
                        <div className="min-w-0 text-right font-mono text-xs text-slate-500">
                          <div className="truncate text-slate-700">{binding.display ?? binding.value ?? binding.type ?? "?"}</div>
                          <div className="truncate text-[0.68rem] uppercase tracking-[0.16em] text-slate-400">
                            {binding.type ? `type: ${binding.type}` : "type: unknown"}
                            {binding.value && binding.value !== binding.display ? ` · value: ${binding.value}` : ""}
                          </div>
                        </div>
                      </div>
                    );
                  })}
                </div>
              ) : (
                <div className="font-mono text-sm text-slate-400">{`{}`}</div>
              )}
            </button>
          );
        })}
      </div>
    );
  };

  return (
    <div data-debugger-panel="true" className="bg-white rounded-2xl h-full flex flex-col overflow-hidden min-h-0">
      <div className="flex items-center justify-between p-[14px] border-b border-gray-200">
        <h2>Debugger</h2>
      </div>
      <div className="p-[14px] flex-1 min-h-0 overflow-auto space-y-4">
        {debuggerCellCode ? (
          <section className="rounded-md border border-slate-200 bg-slate-50 p-3">
            <div className="mb-2 flex items-center justify-between">
              <div className="text-[0.72rem] font-semibold uppercase tracking-[0.18em] text-slate-500">
                Cell [{debuggerCellExecutionOrder ?? " "}]
              </div>
              {isStale && <div className="text-[0.72rem] text-amber-600">run to refresh</div>}
            </div>
            <pre className="m-0 overflow-auto whitespace-pre-wrap break-words font-mono text-[0.82rem] leading-[1.5] text-slate-800">
              {debuggerCellCode}
            </pre>
          </section>
        ) : (
          <section className="rounded-md border border-slate-200 bg-slate-50 p-3">
            <div className="text-sm text-slate-500">Select a code cell to inspect its source here.</div>
          </section>
        )}

        {compilationEnv ? (
          renderCompilationEnv(compilationEnv)
        ) : debuggerCellCode ? (
          <span className="block text-center text-sm text-gray-400 p-4">
            {hasCompilationEnv
              ? compilationEnvError === "Debugger compilation env frames were present but none matched the expected shape." &&
                debuggerLanguageName === "Exp1"
                ? "This language does not expose debugger scope frames."
                : compilationEnvError ?? "The selected cell did not produce any debugger frames."
              : "Run the selected code cell to view compilation env output."}
          </span>
        ) : (
          <span className="block text-center text-sm text-gray-400 p-4">
            Select a code cell to inspect its source here.
          </span>
        )}
      </div>
    </div>
  );
}
