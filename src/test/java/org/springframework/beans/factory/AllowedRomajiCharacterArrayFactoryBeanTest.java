package org.springframework.beans.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AllowedRomajiCharacterArrayFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_TO_CHAR_ARRAY, METHOD_TO_STRING = null;

	private AllowedRomajiCharacterArrayFactoryBean instance = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = AllowedRomajiCharacterArrayFactoryBean.class;
		//
		(METHOD_TO_CHAR_ARRAY = clz.getDeclaredMethod("toCharArray", String.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
	}

	@BeforeEach
	void beforeEach() {
		//
		instance = new AllowedRomajiCharacterArrayFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setAllowedRomajiCharacters(null);
			//
		} // if
			//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(char[].class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testToCharArray() throws Throwable {
		//
		Assertions.assertArrayEquals(new char[] {}, toCharArray(EMPTY));
		//
	}

	private static char[] toCharArray(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_CHAR_ARRAY.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof char[]) {
				return (char[]) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToString() throws Throwable {
		//
		Assertions.assertSame(EMPTY, toString(EMPTY));
		//
	}

	private static String toString(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_STRING.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}