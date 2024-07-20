package org.apache.commons.text;

public interface TextStringBuilderUtil {

	static void clear(final TextStringBuilder instance) {
		if (instance != null) {
			instance.clear();
		}
	}

}