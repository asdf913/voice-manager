package org.apache.commons.lang3.function;

public interface FailableBiConsumerUtil {

	static <T, U, E extends Throwable> void accept(final FailableBiConsumer<T, U, E> instance, final T t, final U u)
			throws E {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

}