package org.springframework.context.support;

public interface Lookup {

	boolean contains(final Object row, final Object column);

	Object get(final Object row, final Object column);

}