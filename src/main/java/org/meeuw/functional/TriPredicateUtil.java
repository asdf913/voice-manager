package org.meeuw.functional;

public interface TriPredicateUtil {

	static <T, U, V> boolean test(final TriPredicate<T, U, V> instance, final T t, final U u, final V v) {
		return instance != null && instance.test(t, u, v);
	}

}