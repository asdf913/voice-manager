package org.springframework.context.support;

public interface Lookup {

	boolean contains(final Object row, final Object column);

	static boolean contains(@Nullable final Lookup instance, final Object row, final Object column) {
		return instance != null && instance.contains(row, column);
	}

	Object get(final Object row, final Object column);

}