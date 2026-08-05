import { type ChangeEvent, type KeyboardEvent, useLayoutEffect, useMemo, useRef } from "react";
import ReactMarkdown from "react-markdown";
import hljs from "highlight.js/lib/core";

import type { MarkdownCell } from "../../models/cell/MarkdownCell";
import { escapeHtml } from "../../lib/utils";

interface MarkdownCellViewProps {
  cell: MarkdownCell;
  disabled: boolean;
  onChange: (value: string) => void;
  onFinishEditing: () => void;
}

export function MarkdownCellView({ cell, disabled, onChange, onFinishEditing }: MarkdownCellViewProps) {
  const editorRef = useRef<HTMLTextAreaElement | null>(null);

  const highlighted = useMemo(() => {
    const content = cell.content + "\n";
    try {
      return hljs.highlight(content, { language: "markdown" }).value;
    } catch {
      return escapeHtml(content);
    }
  }, [cell.content]);

  useLayoutEffect(() => {
    if (!cell.isEditing) return;
    const textarea = editorRef.current;
    if (!textarea) return;
    textarea.style.height = "0px";
    textarea.style.height = `${textarea.scrollHeight}px`;
  }, [cell.isEditing, cell.content]);

  const handleKeyDown = (e: KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === "Enter" && e.shiftKey) {
      e.preventDefault();
      if (!disabled) onFinishEditing();
    }
  };

  if (!cell.isEditing) {
    return (
      <div className="">
        <div className="markdown-editor leading-[1.6] text-[#25344f] [&_h1]:my-1 [&_h2]:my-1 [&_h3]:my-1 [&_h4]:my-1 [&_h5]:my-1 [&_h6]:my-1">
          {cell.content ? <ReactMarkdown>{cell.content}</ReactMarkdown> : "Empty markdown cell"}
        </div>
      </div>
    );
  }

  return (
    <div className={`p-0 bg-transparent relative${disabled ? " opacity-55" : ""}`}>
      <pre
        aria-hidden="true"
        className="w-full min-h-[calc(1.4em+20px)] rounded-[10px] p-[10px] leading-[1.4] font-mono text-[0.9rem] absolute inset-0 m-0 overflow-hidden pointer-events-none whitespace-pre-wrap break-words border-0 bg-transparent"
      >
        <code dangerouslySetInnerHTML={{ __html: highlighted }} />
      </pre>

      <textarea
        ref={editorRef}
        className="block w-full min-h-[calc(1.4em+20px)] rounded-[10px] p-[10px] leading-[1.4] font-mono text-[0.9rem] relative border-0 outline-none resize-none overflow-hidden bg-transparent text-transparent caret-gray-900 focus:outline-none focus:shadow-none disabled:cursor-not-allowed"
        value={cell.content}
        onChange={(e: ChangeEvent<HTMLTextAreaElement>) => onChange(e.target.value)}
        onKeyDown={handleKeyDown}
        spellCheck={false}
        disabled={disabled}
      />
    </div>
  );
}
