import { createRoot } from "react-dom/client";

import App from "./App";
import { WorkspaceStoreProvider } from "./contexts/workspace-store-context";
import { INITIAL_WORKSPACE } from "./config/workspace";
import { AVAILABLE_LANGUAGES } from "./config/languages";
import { registerLanguages } from "./lib/hljs";

import "./styles/main.css";
import "./styles/markdown.css";

registerLanguages(AVAILABLE_LANGUAGES);

createRoot(document.getElementById("root")!).render(
  <WorkspaceStoreProvider workspace={INITIAL_WORKSPACE} languages={AVAILABLE_LANGUAGES}>
    <App />
  </WorkspaceStoreProvider>,
);
