package org.d2ab.function;

public interface ObjIntFunctionUtil {

	static <T, U> U apply(final ObjIntFunction<T, U> instance, final T o, final int i) {
		return instance != null ? instance.apply(o, i) : null;
	}

}