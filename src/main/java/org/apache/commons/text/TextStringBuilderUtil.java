package org.apache.commons.text;

public interface TextStringBuilderUtil {

	static TextStringBuilder clear(final TextStringBuilder instance) {
		return instance != null ? instance.clear() : instance;
	}

}