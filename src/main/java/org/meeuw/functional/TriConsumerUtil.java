package org.meeuw.functional;

public interface TriConsumerUtil {

	static <T, U, V> void accept(final TriConsumer<T, U, V> instance, final T t, final U u, final V v) {
		if (instance != null) {
			instance.accept(t, u, v);
		}
	}

}