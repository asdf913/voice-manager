package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.function.FailableFunction;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import io.github.toolfactory.narcissus.Narcissus;

class TiZuKiGouKanjiHiraganaMapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_TEXT, METHOD_TEST_AND_APPLY, METHOD_TO_MAP1, METHOD_TO_MAP3, METHOD_SPLIT,
			METHOD_ALL_MATCH, METHOD_GET_STRING_BY_UNICODE_BLOCK, METHOD_APPEND = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = TiZuKiGouKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_TEXT = clz.getDeclaredMethod("text", Elements.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_MAP1 = clz.getDeclaredMethod("toMap", Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_MAP3 = clz.getDeclaredMethod("toMap", Iterable.class, Iterable.class, UnaryOperator.class))
				.setAccessible(true);
		//
		(METHOD_SPLIT = clz.getDeclaredMethod("split", String.class, String.class)).setAccessible(true);
		//
		(METHOD_ALL_MATCH = clz.getDeclaredMethod("allMatch", String.class, UnicodeBlock.class)).setAccessible(true);
		//
		(METHOD_GET_STRING_BY_UNICODE_BLOCK = clz.getDeclaredMethod("getStringByUnicodeBlock", String.class,
				UnicodeBlock.class)).setAccessible(true);
		//
		(METHOD_APPEND = clz.getDeclaredMethod("append", StringBuilder.class, Character.TYPE)).setAccessible(true);
		//
	}

	private TiZuKiGouKanjiHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new TiZuKiGouKanjiHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.TiZuKiGouKanjiHiraganaMapFactoryBean.url")) {
			//
			instance.setUrl(Util.toString(Util.get(properties,
					"org.springframework.beans.factory.TiZuKiGouKanjiHiraganaMapFactoryBean.url")));
			//
			System.out.println(getObject(instance));
			//
		} // if
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testText() throws Throwable {
		//
		Assertions.assertNull(text(null));
		//
		Assertions.assertEquals(EMPTY, text(Util.cast(Elements.class, Narcissus.allocateInstance(Elements.class))));
		//
	}

	private static String text(final Elements instance) throws Throwable {
		try {
			final Object obj = METHOD_TEXT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMap() throws Throwable {
		//
		Assertions.assertNull(toMap(Collections.singleton(null)));
		//
		Assertions.assertNull(
				toMap(Collections.singleton(Util.cast(Element.class, Narcissus.allocateInstance(Element.class)))));
		//
		Assertions.assertNull(toMap(Collections.singleton(null), null, null));
		//
		Assertions.assertEquals(Collections.singletonMap(null, null),
				toMap(Collections.singleton(null), Collections.singleton(null), null));
		//
	}

	private static Map<String, String> toMap(final Iterable<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_TO_MAP1.invoke(null, es);
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

	private static Map<String, String> toMap(final Iterable<String> ss1, final Iterable<String> ss2,
			final UnaryOperator<String> function) throws Throwable {
		try {
			final Object obj = METHOD_TO_MAP3.invoke(null, ss1, ss2, function);
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
	void testSplit() throws Throwable {
		//
		Assertions.assertNull(split(EMPTY, null));
		//
		Assertions.assertArrayEquals(new String[] { EMPTY }, split(EMPTY, EMPTY));
		//
	}

	private static String[] split(final String a, final String b) throws Throwable {
		try {
			final Object obj = METHOD_SPLIT.invoke(null, a, b);
			if (obj == null) {
				return null;
			} else if (obj instanceof String[]) {
				return (String[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAllMatch() throws Throwable {
		//
		Assertions.assertTrue(allMatch("a", null));
		//
		Assertions.assertFalse(allMatch("a", UnicodeBlock.HIRAGANA));
		//
		Assertions.assertTrue(allMatch("a", UnicodeBlock.BASIC_LATIN));
		//
	}

	private static boolean allMatch(final String string, final UnicodeBlock unicodeBlock) throws Throwable {
		try {
			final Object obj = METHOD_ALL_MATCH.invoke(null, string, unicodeBlock);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetStringByUnicodeBlock() throws Throwable {
		//
		Assertions.assertEquals("a", getStringByUnicodeBlock("a", null));
		//
		Assertions.assertEquals("a", getStringByUnicodeBlock("a", UnicodeBlock.BASIC_LATIN));
		//
	}

	private static String getStringByUnicodeBlock(final String string, final UnicodeBlock unicodeBlock)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_STRING_BY_UNICODE_BLOCK.invoke(null, string, unicodeBlock);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAppend() {
		//
		Assertions.assertDoesNotThrow(() -> append(null, ' '));
		//
	}

	private static void append(final StringBuilder instance, final char c) throws Throwable {
		try {
			METHOD_APPEND.invoke(null, instance, c);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}