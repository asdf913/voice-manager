package org.springframework.beans.factory;

import java.util.regex.Pattern;

import javax.annotation.Nullable;

interface PatternMap {

	Pattern getPattern(final String pattern);

	@Nullable
	static Pattern getPattern(@Nullable final PatternMap instance, final String pattern) {
		return instance != null ? instance.getPattern(pattern) : null;
	}

}