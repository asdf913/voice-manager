package org.springframework.core.io;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ResourceContentInfoUtilTest {

	private static Method METHOD_TEST = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		(METHOD_TEST = ResourceContentInfoUtil.class.getDeclaredMethod("test", Predicate.class, Object.class))
				.setAccessible(true);
		//
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}