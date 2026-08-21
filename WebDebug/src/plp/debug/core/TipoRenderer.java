package plp.debug.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Formata o valor de um binding (um Tipo, na verificação de tipos) para
 * exibição no debugger.
 *
 * Cada linguagem tem sua própria interface {@code Tipo}, então o wrapper de
 * ambiente — que conhece o tipo concreto — fornece o texto preferido
 * (normalmente {@code getNome()}, que descreve tipos compostos como
 * procedimentos e funções). Aqui só tratamos o caso genérico e evitamos
 * expor o {@code toString()} padrão de Object (Classe@1a2b3c), que não
 * informa nada ao aluno.
 */
public final class TipoRenderer {

	private TipoRenderer() {
	}

	public static Map<String, Object> describe(Object valor, String preferredDisplay) {
		Map<String, Object> binding = new LinkedHashMap<String, Object>();
		if (valor == null) {
			binding.put("type", null);
			binding.put("value", null);
			binding.put("display", null);
			return binding;
		}
		String simpleName = simpleName(valor);
		String display = firstUsable(preferredDisplay, valor.toString(), simpleName);
		binding.put("type", simpleName);
		binding.put("value", display);
		binding.put("display", display);
		return binding;
	}

	private static String firstUsable(String preferred, String toString, String fallback) {
		if (isUsable(preferred)) {
			return preferred;
		}
		if (isUsable(toString) && !isDefaultToString(toString)) {
			return toString;
		}
		return fallback;
	}

	private static boolean isUsable(String text) {
		return text != null && text.trim().length() > 0;
	}

	/** Detecta o toString() padrão de Object: pacote.Classe@hexadecimal. */
	private static boolean isDefaultToString(String text) {
		int at = text.lastIndexOf('@');
		if (at < 0 || at == text.length() - 1) {
			return false;
		}
		for (int i = at + 1; i < text.length(); i++) {
			char c = text.charAt(i);
			boolean hex = (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
			if (!hex) {
				return false;
			}
		}
		return true;
	}

	private static String simpleName(Object valor) {
		String name = valor.getClass().getName();
		int dot = name.lastIndexOf('.');
		return dot < 0 ? name : name.substring(dot + 1);
	}
}
