package org.springframework.context.support;

import java.lang.reflect.Member;
import java.util.Collection;
import java.util.stream.Stream;

import javax.annotation.Nullable;

public interface Util {

	@Nullable
	static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Nullable
	static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

}