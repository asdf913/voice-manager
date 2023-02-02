package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Multimap;

class GaKuNenBeTsuKanJiMultimapFactoryBeanTest {

	private static Method METHOD_GET_CLASS, METHOD_TO_STRING = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = GaKuNenBeTsuKanJiMultimapFactoryBean.class;
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
	}

	private GaKuNenBeTsuKanJiMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new GaKuNenBeTsuKanJiMultimapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl("A");
			//
		} // if
			//
		Assertions.assertThrows(MalformedURLException.class, () -> getObject(instance));
		//
	}

	@Test
	void testSetTimeout() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field timeout = GaKuNenBeTsuKanJiMultimapFactoryBean.class.getDeclaredField("timeout");
		//
		if (timeout != null) {
			//
			timeout.setAccessible(true);
			//
		} // if
			//
			// null
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(null);
				//
			} // if
				//
		});
		//
		Assertions.assertNull(get(timeout, instance));
		//
		// java.time.Duration
		//
		final Duration duration = Duration.ZERO;
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(duration);
				//
			} // if
				//
		});
		//
		Assertions.assertSame(duration, get(timeout, instance));
		//
		// java.lang.Number
		//
		final long l = 1;
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(Long.valueOf(l));
				//
			} // if
				//
		});
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(Long.toString(l));
				//
			} // if
				//
		});
		//
		Assertions.assertEquals(Duration.ofMillis(l), get(timeout, instance));
		//
		// java.lang.String
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout("");
				//
			} // if
				//
		});
		//
		Assertions.assertEquals(Duration.ofMillis(l), get(timeout, instance));
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(" ");
				//
			} // if
				//
		});
		//
		Assertions.assertEquals(Duration.ofMillis(l), get(timeout, instance));
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(String.format("PT%1$sS", l));
				//
			} // if
				//
		});
		//
		Assertions.assertEquals(Duration.ofMillis(l * 1000), get(timeout, instance));
		//
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Test
	void testToString() throws Throwable {
		//
		Assertions.assertNull(toString(null));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null));
		//
		Assertions.assertEquals(String.class, getClass(""));
		//
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

}