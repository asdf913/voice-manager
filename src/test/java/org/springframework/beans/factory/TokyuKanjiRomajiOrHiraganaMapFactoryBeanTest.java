package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;

class TokyuKanjiRomajiOrHiraganaMapFactoryBeanTest {

	private static Method METHOD_GET_OBJECT, METHOD_GET_ROMAJI_OR_HIRAGANA_MAP,
			METHOD_IS_ALL_CHARACTER_IN_SAME_UNICODE_BLOCK, METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = TokyuKanjiRomajiOrHiraganaMapFactoryBean.class;
		//
		(METHOD_GET_OBJECT = clz.getDeclaredMethod("getObject", Iterable.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_ROMAJI_OR_HIRAGANA_MAP = clz.getDeclaredMethod("getRomajiOrHiraganaMap", Iterable.class))
				.setAccessible(true);
		//
		(METHOD_IS_ALL_CHARACTER_IN_SAME_UNICODE_BLOCK = clz.getDeclaredMethod("isAllCharacterInSameUnicodeBlock",
				String.class, UnicodeBlock.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
	}

	private TokyuKanjiRomajiOrHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new TokyuKanjiRomajiOrHiraganaMapFactoryBean();
		//
	}

	@Test
	void testSetUrl() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field url = TokyuKanjiRomajiOrHiraganaMapFactoryBean.class.getDeclaredField("url");
		//
		if (url != null) {
			//
			url.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setUrl(null));
		//
		Assertions.assertNull(get(url, instance));
		//
		final String string = "";
		//
		Assertions.assertDoesNotThrow(() -> instance.setUrl(string));
		//
		Assertions.assertSame(string, get(url, instance));
		//
	}

	@Test
	void testSetRomajiOrHiragana() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field romajiOrHiragana = TokyuKanjiRomajiOrHiraganaMapFactoryBean.class
				.getDeclaredField("romajiOrHiragana");
		//
		if (romajiOrHiragana != null) {
			//
			romajiOrHiragana.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setRomajiOrHiragana(null));
		//
		Assertions.assertNull(get(romajiOrHiragana, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setRomajiOrHiragana(""));
		//
		Assertions.assertNull(get(romajiOrHiragana, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setRomajiOrHiragana("".toCharArray()));
		//
		Assertions.assertNull(get(romajiOrHiragana, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setRomajiOrHiragana("".getBytes()));
		//
		Assertions.assertNull(get(romajiOrHiragana, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setRomajiOrHiragana("R".toCharArray()));
		//
		final Object romaji = get(romajiOrHiragana, instance);
		//
		Assertions.assertEquals("ROMAJI", toString(romaji));
		//
		Assertions.assertDoesNotThrow(() -> instance.setRomajiOrHiragana(romaji));
		//
		Assertions.assertSame(romaji, get(romajiOrHiragana, instance));
		//
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> instance.setRomajiOrHiragana(Collections.emptyMap()));
		//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static Object get(final Field field, final Object instance)
			throws IllegalArgumentException, IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		Assertions.assertNull(getObject(Collections.singletonList(null), null));
		//
		final Element element = new Element("A");
		//
		Iterable<Element> elements = Collections.singletonList(element);
		//
		Assertions.assertNull(getObject(elements, null));
		//
		element.appendText("一一");
		//
		Assertions.assertNull(getObject(elements, null));
		//
	}

	private static Map<String, String> getObject(final Iterable<Element> es, final Object romajiOrHiragana)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_OBJECT.invoke(null, es, romajiOrHiragana);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(obj != null ? obj.getClass() : null));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetRomajiOrHiraganaMap() throws Throwable {
		//
		Assertions.assertNull(getRomajiOrHiraganaMap(Collections.singletonList(null)));
		//
		final Element element = new Element("A");
		//
		Iterable<Element> elements = Collections.singletonList(element);
		//
		Assertions.assertNull(getRomajiOrHiraganaMap(elements));
		//
		element.addClass("name-sub01");
		//
		Assertions.assertEquals("{ROMAJI=}", toString(getRomajiOrHiraganaMap(elements)));
		//
		Assertions.assertThrows(IllegalStateException.class,
				() -> getRomajiOrHiraganaMap(Collections.nCopies(2, element)));
		//
		element.removeClass("name-sub01");
		//
		element.addClass("name-sub02");
		//
		Assertions.assertEquals("{HIRAGANA=}", toString(getRomajiOrHiraganaMap(elements)));
		//
		Assertions.assertThrows(IllegalStateException.class,
				() -> getRomajiOrHiraganaMap(Collections.nCopies(2, element)));
		//
	}

	private static Map<?, String> getRomajiOrHiraganaMap(final Iterable<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_GET_ROMAJI_OR_HIRAGANA_MAP.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(obj != null ? obj.getClass() : null));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAllCharacterInSameUnicodeBlock() throws Throwable {
		//
		Assertions.assertTrue(isAllCharacterInSameUnicodeBlock(null, null));
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
			throw new Throwable(toString(obj != null ? obj.getClass() : null));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() {
		//
		Assertions.assertDoesNotThrow(() -> testAndApply(null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndApply(Predicates.alwaysTrue(), null, null, null));
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

}