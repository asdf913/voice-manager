package org.apache.commons.lang3;

public interface StringsUtil {

	static boolean startsWith(final Strings instance, final CharSequence str, final CharSequence prefix) {
		return instance != null && instance.startsWith(str, prefix);
	}

}