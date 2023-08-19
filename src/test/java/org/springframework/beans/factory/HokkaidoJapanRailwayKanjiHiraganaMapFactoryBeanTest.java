package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import io.github.toolfactory.narcissus.Narcissus;

class HokkaidoJapanRailwayKanjiHiraganaMapFactoryBeanTest {

	private static Method METHOD_CREATE_MAP, METHOD_FOR_NAME, METHOD_OPEN_STREAM, METHOD_GET_DECLARED_FIELD,
			METHOD_TEST, METHOD_TEST_AND_APPLY, METHOD_CREATE_PAIR, METHOD_IS_ALL_CHARACTER_IN_SAME_UNICODE_BLOCK,
			METHOD_CONTAINS, METHOD_TEST_AND_ACCEPT, METHOD_ADD = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = HokkaidoJapanRailwayKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", InputStream.class, Charset.class)).setAccessible(true);
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
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
		(METHOD_CREATE_PAIR = clz.getDeclaredMethod("createPair", String[].class)).setAccessible(true);
		//
		(METHOD_IS_ALL_CHARACTER_IN_SAME_UNICODE_BLOCK = clz.getDeclaredMethod("isAllCharacterInSameUnicodeBlock",
				String.class, UnicodeBlock.class)).setAccessible(true);
		//
		(METHOD_CONTAINS = clz.getDeclaredMethod("contains", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
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
				getBytes(String.join(System.lineSeparator(), "1,2", "1,2,3")))) {
			//
			Assertions.assertEquals(Collections.emptyMap(), createMap(is, forName("utf-8")));
			//
		} // try
			//
	}

	private static byte[] getBytes(final String instance) {
		return instance != null ? instance.getBytes() : null;
	}

	private static Map<String, String> createMap(final InputStream is, final Charset encoding) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP.invoke(null, is, encoding);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForName() throws Throwable {
		//
		Assertions.assertNull(forName(null));
		//
		Assertions.assertNull(forName(""));
		//
		Assertions.assertNull(forName(" "));
		//
		Assertions.assertNull(forName("A"));
		//
	}

	private static Charset forName(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_FOR_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Charset) {
				return (Charset) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredField() throws Throwable {
		//
		Assertions.assertNull(getDeclaredField(null, null));
		//
		Assertions.assertNull(getDeclaredField(Object.class, null));
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null, null));
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
	void testCreatePair() throws Throwable {
		//
		Assertions.assertNull(createPair(null));
		//
		Assertions.assertEquals(Pair.of(null, null), createPair(new String[] { null, null, null }));
		//
	}

	private static Pair<String, String> createPair(final String[] ss) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_PAIR.invoke(null, (Object) ss);
			if (obj == null) {
				return null;
			} else if (obj instanceof Pair) {
				return (Pair) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAllCharacterInSameUnicodeBlock() throws Throwable {
		//
		Assertions.assertTrue(isAllCharacterInSameUnicodeBlock(null, null));
		//
	}

	private static boolean isAllCharacterInSameUnicodeBlock(final String string, final UnicodeBlock unicodeBlock)
			throws Throwable {
		try {
			final Object obj = METHOD_IS_ALL_CHARACTER_IN_SAME_UNICODE_BLOCK.invoke(null, string, unicodeBlock);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(contains(null, null));
		//
		Assertions.assertTrue(contains(Collections.singleton(null), null));
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS.invoke(null, items, item);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.biAlwaysTrue(), null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.biAlwaysFalse(), null, null, null));
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, instance, t, u, consumer);
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