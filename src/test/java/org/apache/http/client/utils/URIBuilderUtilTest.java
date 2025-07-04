package org.apache.http.client.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class URIBuilderUtilTest {

	@Test
	void testNull() {
		//
		final Method[] ms = URIBuilderUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertNull(
					Narcissus.invokeStaticMethod(m, toArray(Collections.nCopies(m.getParameterCount(), null))),
					Objects.toString(m));
			//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testBuild() throws Throwable {
		//
		Assertions.assertNotNull(
				URIBuilderUtil.build(cast(URIBuilder.class, Narcissus.allocateInstance(URIBuilder.class))));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}