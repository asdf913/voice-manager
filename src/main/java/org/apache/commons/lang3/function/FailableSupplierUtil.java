package org.apache.commons.lang3.function;

public interface FailableSupplierUtil {

	static <T, E extends Throwable> T get(final FailableSupplier<T, E> instance) throws E {
		return instance != null ? instance.get() : null;
	}

}