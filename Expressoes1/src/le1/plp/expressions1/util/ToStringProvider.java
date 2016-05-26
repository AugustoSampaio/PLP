package le1.plp.expressions1.util;

import java.util.List;

public class ToStringProvider {

	public static String listToString(List<?> list, CharSequence before,
			CharSequence after, CharSequence separator) {
		StringBuilder sb = new StringBuilder();
		sb.append(before);

		for (Object object : list) {
			sb.append(object.toString());
			sb.append(separator);
			sb.append(' ');
		}
		if (sb.length() >= 2) {
			sb.delete(sb.length() - 1 - separator.length(), sb.length());
		}
		sb.append(after);
		return sb.toString();
	}

	public static String listToString(List<?> list, CharSequence separator) {
		return listToString(list, "", "", separator);
	}
}
