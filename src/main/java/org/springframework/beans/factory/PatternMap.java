package org.springframework.beans.factory;

import java.util.regex.Pattern;

interface PatternMap {

	Pattern getPattern(final String pattern);

	static Pattern getPattern(final PatternMap instance, final String pattern) {
		return instance != null ? instance.getPattern(pattern) : null;
	}

}