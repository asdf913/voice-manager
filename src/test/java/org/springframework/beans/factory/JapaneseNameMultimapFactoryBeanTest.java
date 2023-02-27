package org.springframework.beans.factory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import io.github.toolfactory.narcissus.Narcissus;

class JapaneseNameMultimapFactoryBeanTest {

	private static Method METHOD_TEST, METHOD_GET_PROTOCOL, METHOD_CREATE_MULTI_MAP, METHOD_PUT = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = JapaneseNameMultimapFactoryBean.class;
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_PROTOCOL = clz.getDeclaredMethod("getProtocol", URL.class)).setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP = clz.getDeclaredMethod("createMultimap", Element.class, Pattern.class))
				.setAccessible(true);
		//
		(METHOD_PUT = clz.getDeclaredMethod("put", Multimap.class, Object.class, Object.class)).setAccessible(true);
		//
	}

	private JapaneseNameMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new JapaneseNameMultimapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		instance.setUrl(new File("pom.xml").toURI().toURL().toString());
		//
		Assertions.assertNull(getObject(instance));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
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

	@Test
	void testGetProtocol() throws Throwable {
		//
		Assertions.assertNull(getProtocol(null));
		//
		final String protocol = "http";
		//
		Assertions.assertEquals(protocol, getProtocol(new URL(String.format("%1$s://www.google.com", protocol))));
		//
	}

	private static String getProtocol(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROTOCOL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMultimap() throws Throwable {
		//
		Assertions.assertNull(createMultimap(null, null));
		//
		Assertions.assertNull(createMultimap(cast(Element.class, Narcissus.allocateInstance(Element.class)), null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static Multimap<String, String> createMultimap(final Element input, final Pattern pattern)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP.invoke(null, input, pattern);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPut() {
		//
		Assertions.assertDoesNotThrow(() -> put(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> put(LinkedHashMultimap.create(), null, null));
		//
	}

	private static <K, V> void put(final Multimap<K, V> instance, final K key, final V value) throws Throwable {
		try {
			METHOD_PUT.invoke(null, instance, key, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}