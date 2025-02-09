package org.apache.commons.lang3.function;

public interface FailableRunnableUtil {

	static <E extends Throwable> void run(final FailableRunnable<E> instance) throws E {
		if (instance != null) {
			instance.run();
		}
	}

}