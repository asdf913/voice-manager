package org.apache.commons.lang3.function;

public interface FailableConsumerUtil {

	public static <T, E extends Throwable> void accept(final FailableConsumer<T, E> instance, final T object) throws E {
		if (instance != null) {
			instance.accept(object);
		}
	}

}