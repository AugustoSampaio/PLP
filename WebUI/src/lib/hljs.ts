import hljs from "highlight.js/lib/core";
import markdown from "highlight.js/lib/languages/markdown";

import type { Language } from "../models/types/execution";
import { languageToHljs } from "./utils";

import "highlight.js/styles/github.css";

export const registerLanguages = (languages: Language[]) => {
  hljs.registerLanguage("markdown", markdown);

  for (const language of languages) {
    hljs.registerLanguage(language.name.toLowerCase(), languageToHljs(language));
  }
};
