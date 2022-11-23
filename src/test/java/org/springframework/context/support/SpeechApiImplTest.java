package org.springframework.context.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SpeechApiImplTest {

	private static Method METHOD_CAST, METHOD_GET_VALUE0 = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = SpeechApiImpl.class;
		//
		if ((METHOD_CAST = clz != null ? clz.getDeclaredMethod("cast", Class.class, Object.class) : null) != null) {
			//
			METHOD_CAST.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_GET_VALUE0 = clz != null ? clz.getDeclaredMethod("getValue0", IValue0.class) : null) != null) {
			//
			METHOD_GET_VALUE0.setAccessible(true);
			//
		} // if
			//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> new SpeechApiImpl().afterPropertiesSet());
		//
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
		Assertions.assertNull(cast(Object.class, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue0() throws Throwable {
		//
		Assertions.assertNull(getValue0(null));
		//
	}

	private static <X> X getValue0(final IValue0<X> instance) throws Throwable {
		try {
			return (X) METHOD_GET_VALUE0.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}