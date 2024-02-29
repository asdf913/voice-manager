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
import org.meeuw.functional.Predicates;

import com.google.common.collect.Multimap;

class TokyoToeiTodenKanjiRomajiOrHiraganaMapFactoryBeanTest {

	private static Method METHOD_CREATE_MAP, METHOD_TEST_AND_APPLY, METHOD_GET_HIRAGANA, METHOD_GET_ROMAJI,
			METHOD_CREATE_UNICODE_BLOCK_CHARACTER_MULTI_MAP, METHOD_GET_KANJI = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = TokyoToeiTodenKanjiRomajiOrHiraganaMapFactoryBean.class;
		//
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_HIRAGANA = clz.getDeclaredMethod("getHiragana", Element.class)).setAccessible(true);
		//
		(METHOD_GET_ROMAJI = clz.getDeclaredMethod("getRomaji", Element.class)).setAccessible(true);
		//
		(METHOD_CREATE_UNICODE_BLOCK_CHARACTER_MULTI_MAP = clz.getDeclaredMethod("createUnicodeBlockCharacterMultimap",
				String.class)).setAccessible(true);
		//
		(METHOD_GET_KANJI = clz.getDeclaredMethod("getKanji", String.class)).setAccessible(true);
		//
	}

	private TokyoToeiTodenKanjiRomajiOrHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new TokyoToeiTodenKanjiRomajiOrHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl("");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl(" ");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testSetUnicodeBlock() throws Throwable {
		//
		final Field unicodeBlock = TokyoToeiTodenKanjiRomajiOrHiraganaMapFactoryBean.class
				.getDeclaredField("unicodeBlock");
		//
		if (unicodeBlock != null) {
			//
			unicodeBlock.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock(null));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock(""));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("".toCharArray()));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("".getBytes()));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("123"));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("HIRAGANA".toCharArray()));
		//
		final Object hiragana = get(unicodeBlock, instance);
		//
		Assertions.assertEquals("HIRAGANA", toString(hiragana));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock(hiragana));
		//
		Assertions.assertSame(hiragana, get(unicodeBlock, instance));
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance.setUnicodeBlock(Collections.emptyMap()));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> instance.setUnicodeBlock("h"));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> instance.setUnicodeBlock("map"));
		//
	}

	private static Object get(final Field field, final Object instance)
			throws IllegalArgumentException, IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testCreateMap() throws Throwable {
		//
		Assertions.assertNull(createMap(null));
		//
		Assertions.assertNull(createMap(""));
		//
		Assertions.assertNull(createMap(" "));
		//
	}

	private static Map<UnicodeBlock, String> createMap(final String url) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP.invoke(null, url);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
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
	void testGetHiragana() throws Throwable {
		//
		Assertions.assertNull(getHiragana(null));
		//
	}

	private static String getHiragana(final Element element) throws Throwable {
		try {
			final Object obj = METHOD_GET_HIRAGANA.invoke(null, element);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetRomaja() throws Throwable {
		//
		Assertions.assertNull(getRomaja(null));
		//
	}

	private static String getRomaja(final Element element) throws Throwable {
		try {
			final Object obj = METHOD_GET_ROMAJI.invoke(null, element);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateUnicodeBlockCharacterMultimap() throws Throwable {
		//
		final char c = ' ';
		//
		Assertions.assertEquals(String.format("{BASIC_LATIN=[%1$s]}", c),
				toString(createUnicodeBlockCharacterMultimap(new String(new char[] { c }))));
		//
	}

	private static Multimap<UnicodeBlock, Character> createUnicodeBlockCharacterMultimap(final String string)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_UNICODE_BLOCK_CHARACTER_MULTI_MAP.invoke(null, string);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetKanji() throws Throwable {
		//
		Assertions.assertNull(getKanji(null));
		//
	}

	private static String getKanji(final String string) throws Throwable {
		try {
			final Object obj = METHOD_GET_KANJI.invoke(null, string);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}