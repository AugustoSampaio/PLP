import { Menu } from "@base-ui/react/menu";
import { FiCheck, FiChevronDown } from "react-icons/fi";

import type { NotebookLanguage } from "../../config/languages";

export interface NotebookSettingsMenuProps {
  availableLanguages: NotebookLanguage[];
  currentLanguageName: string;
  notebookScopeEnabled: boolean;
  supportsNotebookScope: boolean;
  locked: boolean;
  onChangeLanguage: (name: string) => void;
  onToggleScope: () => void;
}

export function NotebookSettingsMenu({
  availableLanguages,
  currentLanguageName,
  notebookScopeEnabled,
  supportsNotebookScope,
  locked,
  onChangeLanguage,
  onToggleScope,
}: NotebookSettingsMenuProps) {
  return (
    <Menu.Root>
      <Menu.Trigger
        disabled={locked}
        className="flex items-center gap-1.5 border border-gray-200 rounded-md py-2 px-[10px] text-sm text-gray-900 hover:bg-gray-100 focus:outline-0 disabled:opacity-55 disabled:cursor-not-allowed cursor-pointer shrink-0"
        aria-label="Notebook settings"
      >
        {currentLanguageName}
        <FiChevronDown size={13} className="text-gray-500" />
      </Menu.Trigger>
      <Menu.Portal>
        <Menu.Positioner side="bottom" align="end" sideOffset={6} className="z-50">
          <Menu.Popup className="bg-white rounded-xl shadow-lg border border-gray-100 py-1 min-w-[200px] outline-none data-[open]:opacity-100 data-[closed]:opacity-0 data-[starting-style]:opacity-0 transition-opacity duration-150">
            <div className="px-3 py-1 text-[11px] font-medium text-gray-400 uppercase tracking-wide">Language</div>
            {availableLanguages.map((lang) => (
              <Menu.Item
                key={lang.name}
                className="flex items-center gap-2 px-3 py-2 text-sm text-gray-700 hover:bg-gray-100 cursor-pointer outline-none"
                onClick={() => onChangeLanguage(lang.name)}
              >
                <span className="w-4 flex items-center justify-center">
                  {lang.name === currentLanguageName && <FiCheck size={13} />}
                </span>
                {lang.name}
              </Menu.Item>
            ))}
            <Menu.Separator className="my-1 border-t border-gray-100" />
            <Menu.Item
              className={`flex items-center gap-2 px-3 py-2 text-sm outline-none ${
                supportsNotebookScope
                  ? "text-gray-700 hover:bg-gray-100 cursor-pointer"
                  : "text-gray-300 cursor-not-allowed"
              }`}
              disabled={!supportsNotebookScope}
              onClick={() => supportsNotebookScope && onToggleScope()}
            >
              <span className="w-4 flex items-center justify-center">
                {notebookScopeEnabled && supportsNotebookScope && <FiCheck size={13} />}
              </span>
              Enable scope by notebook (experimental)
            </Menu.Item>
          </Menu.Popup>
        </Menu.Positioner>
      </Menu.Portal>
    </Menu.Root>
  );
}
