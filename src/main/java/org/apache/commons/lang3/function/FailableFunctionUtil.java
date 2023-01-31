package org.apache.commons.lang3.function;

public interface FailableFunctionUtil {

	static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> instance, final T value) throws E {
		return instance != null ? instance.apply(value) : null;
	}

}