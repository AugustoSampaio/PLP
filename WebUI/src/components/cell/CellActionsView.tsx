import type { MouseEventHandler } from "react";
import { FiArrowDown, FiArrowUp, FiEdit2, FiEdit3, FiTrash2 } from "react-icons/fi";

interface CellActionsViewProps {
  disabled: boolean;
  showEdit?: boolean;
  isEditing?: boolean;
  onEdit?: MouseEventHandler<HTMLButtonElement>;
  onMoveUp: MouseEventHandler<HTMLButtonElement>;
  onMoveDown: MouseEventHandler<HTMLButtonElement>;
  onDelete: MouseEventHandler<HTMLButtonElement>;
}

export function CellActionsView({
  disabled,
  showEdit = false,
  isEditing = false,
  onEdit,
  onMoveUp,
  onMoveDown,
  onDelete,
}: CellActionsViewProps) {
  const btnClass =
    "w-7 h-7 p-0 inline-flex items-center justify-center text-[0.95rem] leading-none border-0 bg-transparent text-gray-900 rounded-[5px] cursor-pointer hover:bg-gray-100 disabled:opacity-50 disabled:cursor-default [&_svg]:w-[14px] [&_svg]:h-[14px]";

  return (
    <div className="flex gap-2 items-center p-1 rounded-md bg-white border border-gray-200 pointer-events-auto">
      <button type="button" className={btnClass} onClick={onMoveUp} disabled={disabled} aria-label="Move cell up" title="Move up">
        <FiArrowUp aria-hidden="true" />
      </button>
      <button type="button" className={btnClass} onClick={onMoveDown} disabled={disabled} aria-label="Move cell down" title="Move down">
        <FiArrowDown aria-hidden="true" />
      </button>
      {showEdit && (
        <button
          type="button"
          className={btnClass}
          onClick={onEdit}
          disabled={disabled}
          aria-label={isEditing ? "Close markdown editor" : "Edit markdown"}
          title={isEditing ? "Close markdown editor" : "Edit markdown"}
        >
          {isEditing ? <FiEdit3 aria-hidden="true" /> : <FiEdit2 aria-hidden="true" />}
        </button>
      )}
      <button
        type="button"
        className={`${btnClass} text-[#b42318]`}
        onClick={onDelete}
        disabled={disabled}
        aria-label="Delete cell"
        title="Delete"
      >
        <FiTrash2 aria-hidden="true" />
      </button>
    </div>
  );
}
