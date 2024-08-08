package org.apache.commons.lang3.function;

public interface TriFunctionUtil {

	static <T, U, V, R> R apply(final TriFunction<T, U, V, R> instnace, final T t, final U u, final V v) {
		return instnace != null ? instnace.apply(t, u, v) : null;
	}

}