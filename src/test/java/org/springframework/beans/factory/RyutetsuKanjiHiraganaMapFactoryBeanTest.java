package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import io.github.toolfactory.narcissus.Narcissus;

class RyutetsuKanjiHiraganaMapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY4, METHOD_TEST_AND_APPLY5, METHOD_GET_UNICODE_BLOCKS,
			METHOD_TEST_AND_ACCEPT, METHOD_CREATE_KANJI_HIRAGANA_ROMAJI_LIST, METHOD_CREATE_ENTRY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = RyutetsuKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				FailableBiFunction.class, FailableBiFunction.class)).setAccessible(true);
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", char[].class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_CREATE_KANJI_HIRAGANA_ROMAJI_LIST = clz.getDeclaredMethod("createKanjiHiraganaRomajiList", List.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_ENTRY = clz.getDeclaredMethod("createEntry", Field[].class, Object.class)).setAccessible(true);
		//
	}

	private RyutetsuKanjiHiraganaMapFactoryBean instance = null;

	@BeforeEach
	private void beforeEach() {
		//
		instance = new RyutetsuKanjiHiraganaMapFactoryBean();
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
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
		//
		Assertions.assertNull(testAndApply(null, null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.biAlwaysTrue(), null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.biAlwaysFalse(), null, null, null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY4.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY5.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetUnicodeBlocks() throws Throwable {
		//
		Assertions.assertNull(getUnicodeBlocks(null));
		//
		Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN),
				getUnicodeBlocks(new char[] { ' ' }));
		//
	}

	private static List<UnicodeBlock> getUnicodeBlocks(final char[] cs) throws Throwable {
		try {
			final Object obj = METHOD_GET_UNICODE_BLOCKS.invoke(null, cs);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
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
	void testCreateKanjiHiraganaRomajiList() throws Throwable {
		//
		Assertions.assertNull(createKanjiHiraganaRomajiList(
				Arrays.asList(null, Util.cast(Element.class, Narcissus.allocateInstance(Element.class)))));
		//
	}

	private static List<?> createKanjiHiraganaRomajiList(final List<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_KANJI_HIRAGANA_ROMAJI_LIST.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List<?>) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateEntry() throws Throwable {
		//
		Assertions.assertEquals(Pair.of(null, null), createEntry(null, null));
		//
		final Field[] fs = new Field[] { null, Boolean.class.getDeclaredField("value") };
		//
		final Object value = Boolean.TRUE;
		//
		Assertions.assertEquals(Pair.of(null, null), createEntry(fs, value));
		//
		if (instance != null) {
			//
			instance.setKeyUnicodeBlock(UnicodeBlock.BASIC_LATIN);
			//
		} // if
			//
		Assertions.assertEquals(Pair.of(Unit.with(Util.toString(value)), null), createEntry(fs, value));
		//
		if (instance != null) {
			//
			instance.setKeyUnicodeBlock(null);
			//
			instance.setValueUnicodeBlock(UnicodeBlock.BASIC_LATIN);
			//
		} // if
			//
		Assertions.assertEquals(Pair.of(null, Unit.with(Util.toString(value))), createEntry(fs, value));
		//
	}

	private Entry<IValue0<String>, IValue0<String>> createEntry(final Field[] fs, final Object v) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_ENTRY.invoke(instance, fs, v);
			if (obj == null) {
				return null;
			} else if (obj instanceof Entry) {
				return (Entry) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}