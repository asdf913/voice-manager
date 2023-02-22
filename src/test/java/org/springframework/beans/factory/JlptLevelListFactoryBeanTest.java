package org.springframework.beans.factory;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JlptLevelListFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_GET_CLASS, METHOD_TO_STRING, METHOD_ADD = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = JlptLevelListFactoryBean.class;
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
		//
	}

	private JlptLevelListFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new JlptLevelListFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(List.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testSetTimeout() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field timeout = JlptLevelListFactoryBean.class.getDeclaredField("timeout");
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
				instance.setTimeout(EMPTY);
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
	void testSetValues() throws Throwable {
		//
		final Field values = JlptLevelListFactoryBean.class.getDeclaredField("values");
		//
		if (values != null) {
			//
			values.setAccessible(true);
			//
		} // if
			//
			// null
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setValues(null);
				//
			} // if
				//
		});
		//
		Assertions.assertEquals("[[null]]", toString(get(values, instance)));
		//
		// java.lang.String
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setValues(EMPTY);
				//
			} // if
				//
		});
		//
		Assertions.assertEquals("[[null]]", toString(get(values, instance)));
		//
		// java.util.List
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setValues(String.format("[%1$s]", EMPTY));
				//
			} // if
				//
		});
		//
		Assertions.assertEquals("[null]", toString(get(values, instance)));
		//
		// java.lang.Number
		//
		final Integer zero = Integer.valueOf(0);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setValues(toString(zero));
				//
			} // if
				//
		});
		//
		Assertions.assertEquals(String.format("[[%1$s]]", zero), toString(get(values, instance)));
		///
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setValues(String.format("[%1$s]", zero));
				//
			} // if
				//
		});
		//
		Assertions.assertEquals(String.format("[[%1$s]]", zero), toString(get(values, instance)));
		//
		// java.util.Map
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			//
			if (instance != null) {
				//
				instance.setValues(String.format("{\"%1$s\":%1$s}", zero));
				//
			} // if
				//
		});
		//
		// Invalid Format
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> {
				//
				if (instance != null) {
					//
					instance.setValues(String.format("{%1$s:%1$s}", zero));
					//
				} // if
					//
			});
			//
		} else {
			//
			Assertions.assertThrows(RuntimeException.class, () -> {
				//
				if (instance != null) {
					//
					instance.setValues(String.format("{%1$s:%1$s}", zero));
					//
				} // if
					//
			});
			//
		} // if
			//
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
		Assertions.assertEquals(String.class, getClass(EMPTY));
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

	@Test
	void testAdd() {
		//
		Assertions.assertDoesNotThrow(() -> add(null, null));
		//
	}

	private static <E> void add(final Collection<E> items, final E item) throws Throwable {
		try {
			METHOD_ADD.invoke(null, items, item);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}