package org.apache.commons.lang3.function;

public interface FailableBiFunctionUtil {

	static <T, R, U, E extends Throwable> R apply(final FailableBiFunction<T, U, R, E> instance, final T t, final U u)
			throws E {
		return instance != null ? instance.apply(t, u) : null;
	}

}