package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableBiFunction;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opencsv.CSVReader;

import io.github.toolfactory.narcissus.Narcissus;

class HokkaidoJapanRailwayKanjiHiraganaMapFactoryBeanTest {

	private static Method METHOD_CREATE_MAP, METHOD_GET_CLASS, METHOD_OPEN_STREAM, METHOD_GET_DECLARED_FIELD,
			METHOD_TEST, METHOD_TEST_AND_APPLY, METHOD_READ_NEXT = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = HokkaidoJapanRailwayKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", InputStream.class, String.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_OPEN_STREAM = clz.getDeclaredMethod("openStream", URL.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_FIELD = clz.getDeclaredMethod("getDeclaredField", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				FailableBiFunction.class, FailableBiFunction.class)).setAccessible(true);
		//
		(METHOD_READ_NEXT = clz.getDeclaredMethod("readNext", CSVReader.class)).setAccessible(true);
		//
	}

	private HokkaidoJapanRailwayKanjiHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new HokkaidoJapanRailwayKanjiHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
	}

	@Test
	void testSetUrl() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setUrl(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testSetEncoding() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setEncoding(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testCreateMap() throws Throwable {
		//
		try (final InputStream is = new ByteArrayInputStream(getBytes(""))) {
			//
			Assertions.assertNull(createMap(is, null));
			//
		} // try
			//
		try (final InputStream is = new ByteArrayInputStream(getBytes("1,2"))) {
			//
			Assertions.assertEquals(Collections.emptyMap(), createMap(is, null));
			//
		} // try
			//
		try (final InputStream is = new ByteArrayInputStream(
				getBytes(String.join(System.lineSeparator(), "1,2", "1,2")))) {
			//
			Assertions.assertEquals(Collections.singletonMap("1", "2"), createMap(is, "utf-8"));
			//
		} // try
			//
	}

	private static byte[] getBytes(final String instance) {
		return instance != null ? instance.getBytes() : null;
	}

	private static Map<String, String> createMap(final InputStream is, final String encoding) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP.invoke(null, is, encoding);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetClass() throws Throwable {
		//
		Assert.assertNull(getClass(null));
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
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOpenStream() throws Throwable {
		//
		Assertions.assertNotNull(openStream(new File("pom.xml").toURI().toURL()));
		//
		Assertions.assertNull(openStream(cast(URL.class, Narcissus.allocateInstance(URL.class))));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static InputStream openStream(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_OPEN_STREAM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredField() throws Throwable {
		//
		Assert.assertNull(getDeclaredField(null, null));
		//
		Assert.assertNull(getDeclaredField(Object.class, null));
		//
	}

	private static Field getDeclaredField(final Class<?> instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_FIELD.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTest() throws Throwable {
		//
		Assert.assertFalse(test(null, null));
		//
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assert.assertNull(testAndApply(null, null, null, null, null));
		//
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testReadNext() throws Throwable {
		//
		Assert.assertNull(readNext(cast(CSVReader.class, Narcissus.allocateInstance(CSVReader.class))));
		//
	}

	private static String[] readNext(final CSVReader instance) throws Throwable {
		try {
			final Object obj = METHOD_READ_NEXT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String[]) {
				return (String[]) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}