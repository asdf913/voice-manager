package org.springframework.context.support;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerSpreadsheetToPdfPanelTest {

	private static Method METHOD_FLOAT_VALUE, METHOD_GET_FIELD_BY_NAME = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = VoiceManagerSpreadsheetToPdfPanel.class;
		//
		(METHOD_FLOAT_VALUE = clz.getDeclaredMethod("floatValue", Number.class, Float.TYPE)).setAccessible(true);
		//
		(METHOD_GET_FIELD_BY_NAME = clz.getDeclaredMethod("getFieldByName", Collection.class, String.class))
				.setAccessible(true);
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerSpreadsheetToPdfPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Collection<Object> collection = null;
		//
		String toString = null;
		//
		Object invokeStaticMethod = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(ArrayUtils.get(parameterTypes, j), Float.TYPE)) {
					//
					Util.add(collection, Float.valueOf(0));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // if
				//
			toString = Util.toString(m);
			//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, Util.toArray(collection));
			//
			if (Util.contains(Arrays.asList(Float.TYPE, Boolean.TYPE), m.getReturnType())) {
				//
				Assertions.assertNotNull(invokeStaticMethod, toString);
				//
			} else {
				//
				Assertions.assertNull(invokeStaticMethod, toString);
				//
			} // if
				//
		} // for
			//
	}

	@Test
	void testMain() {
		//
		Assertions.assertDoesNotThrow(() -> VoiceManagerSpreadsheetToPdfPanel.main(new String[] { "." }));
		//
		Assertions.assertThrows(IOException.class,
				() -> VoiceManagerSpreadsheetToPdfPanel.main(new String[] { "pom.xml" }));
		//
	}

	@Test
	void testFloatValue() throws Throwable {
		//
		final float zero = 0f;
		//
		Assertions.assertEquals(zero, floatValue(Float.valueOf(zero), zero));
		//
	}

	private static float floatValue(final Number instance, final float defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_FLOAT_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Float) {
				return ((Float) obj).floatValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFieldByName() throws Throwable {
		//
		final String name = "value";
		//
		final Field field = Util.getDeclaredField(Boolean.class, name);
		//
		Assertions.assertSame(field,
				getFieldByName(Arrays.asList(null, Util.getDeclaredField(Boolean.class, "TRUE"), field), name));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> getFieldByName(Collections.nCopies(2, field), name));
		//
	}

	private static Field getFieldByName(final Collection<Field> collection, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_FIELD_BY_NAME.invoke(null, collection, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}