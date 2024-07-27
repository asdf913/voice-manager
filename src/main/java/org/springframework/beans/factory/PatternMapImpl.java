package org.springframework.beans.factory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

public class PatternMapImpl implements PatternMap {

	private Map<Object, Pattern> patternMap = null;

	private Map<Object, Pattern> getPatternMap() {
		if (patternMap == null) {
			patternMap = new LinkedHashMap<>();
		}
		return patternMap;
	}

	@Override
	public Pattern getPattern(final String pattern) {
		//
		final Map<Object, Pattern> map = getPatternMap();
		//
		if (!Util.containsKey(map, pattern)) {
			//
			Util.put(map, pattern, testAndApply(x -> x != null, pattern, Pattern::compile, null));
			//
		} // if
			//
		return Util.get(map, pattern);
		//
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			@Nullable final Function<T, R> functionFalse) {
		return Util.test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R> R apply(@Nullable final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

}