package org.meeuw.functional;

public interface ThrowingRunnableUtil {

	static <T extends Throwable> void runThrows(final ThrowingRunnable<T> instance) throws T {
		if (instance != null) {
			instance.runThrows();
		}
	}

}