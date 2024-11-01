package org.d2ab.function;

public interface ObjIntPredicateUtil {

	static <T> boolean test(final ObjIntPredicate<T> instance, final T t, int i) {
		return instance != null && instance.test(t, i);
	}

}