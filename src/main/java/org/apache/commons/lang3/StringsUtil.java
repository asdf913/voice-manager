package org.apache.commons.lang3;

public interface StringsUtil {

	static boolean startsWith(final Strings instance, final CharSequence str, final CharSequence prefix) {
		return instance != null && instance.startsWith(str, prefix);
	}

	static boolean endsWith(final Strings instance, final CharSequence str, final CharSequence suffix) {
		return instance != null && instance.endsWith(str, suffix);
	}

	static String replace(final Strings instance, final String text, final String searchString,
			final String replacement) {
		return instance != null ? instance.replace(text, searchString, replacement) : null;
	}

	static boolean equals(final Strings instance, final CharSequence cs1, final CharSequence cs2) {
		return instance != null && instance.equals(cs1, cs2);
	}

	static boolean equalsAny(final Strings instance, final CharSequence string, final CharSequence... searchStrings) {
		return instance != null && instance.equalsAny(string, searchStrings);
	}

	static boolean contains(final Strings instance, final CharSequence str, final CharSequence searchStr) {
		return instance != null && instance.contains(str, searchStr);
	}

	static int indexOf(final Strings instance, final CharSequence seq, final CharSequence searchSeq) {
		return instance != null ? instance.indexOf(seq, searchSeq) : -1;
	}

	static int compare(final Strings instance, final String str1, final String str2) {
		return instance != null ? instance.compare(str1, str2) : 0;
	}

}