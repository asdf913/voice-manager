package org.apache.commons.lang3;

public interface StringsUtil {

	static boolean startsWith(final Strings instance, final CharSequence str, final CharSequence prefix) {
		return instance != null && instance.startsWith(str, prefix);
	}

	static String replace(final Strings instance, final String text, final String searchString,
			final String replacement) {
		return instance != null ? instance.replace(text, searchString, replacement) : null;
	}

}