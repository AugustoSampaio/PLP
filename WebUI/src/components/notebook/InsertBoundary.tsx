interface InsertBoundaryProps {
  index: number;
  locked: boolean;
  onInsertCode: (index: number) => void;
  onInsertMarkdown: (index: number) => void;
}

export function InsertBoundary({ index, locked, onInsertCode, onInsertMarkdown }: InsertBoundaryProps) {
  return (
    <div className="group relative h-2 flex items-center justify-center z-10">
      <div className="absolute inset-x-0 border-t border-transparent transition-colors group-hover:border-gray-200 group-focus-within:border-gray-200" />
      <div className="relative flex gap-[10px] justify-center opacity-0 pointer-events-none translate-y-[2px] transition-[opacity,transform] duration-[120ms] ease-[ease] group-hover:opacity-100 group-hover:pointer-events-auto group-hover:translate-y-0 group-focus-within:opacity-100 group-focus-within:pointer-events-auto group-focus-within:translate-y-0">
        <button
          type="button"
          className="text-sm border border-gray-200 bg-white text-gray-900 rounded-full px-4 py-1.5 cursor-pointer font-medium hover:bg-gray-100 disabled:opacity-55 disabled:cursor-not-allowed"
          disabled={locked}
          onClick={() => onInsertCode(index)}
        >
          + Code
        </button>
        <button
          type="button"
          className="text-sm border border-gray-200 bg-white text-gray-900 rounded-full px-4 py-1.5 cursor-pointer font-medium hover:bg-gray-100 disabled:opacity-55 disabled:cursor-not-allowed"
          disabled={locked}
          onClick={() => onInsertMarkdown(index)}
        >
          + Text
        </button>
      </div>
    </div>
  );
}
