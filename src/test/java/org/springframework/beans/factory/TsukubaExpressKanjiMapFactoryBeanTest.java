package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import io.github.toolfactory.narcissus.Narcissus;

class TsukubaExpressKanjiMapFactoryBeanTest {

	private static Class<?> CLASS_ROMAJI_OR_HIRAGANA = null;

	private static Method METHOD_CREATE_ENTRY, METHOD_GET_STRING, METHOD_TEST_AND_APPLY, METHOD_NAME, METHOD_ADD = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = TsukubaExpressKanjiMapFactoryBean.class;
		//
		(METHOD_CREATE_ENTRY = clz.getDeclaredMethod("createEntry", String.class,
				CLASS_ROMAJI_OR_HIRAGANA = Class.forName(
						"org.springframework.beans.factory.TsukubaExpressKanjiMapFactoryBean$RomajiOrHiragana")))
				.setAccessible(true);
		//
		(METHOD_GET_STRING = clz.getDeclaredMethod("getString", Iterable.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_NAME = clz.getDeclaredMethod("name", Enum.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
		//
	}

	private TsukubaExpressKanjiMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new TsukubaExpressKanjiMapFactoryBean();
		//
	}

	@Test
	void testSetRomajiOrHiragana() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field romajiOrHiragana = TsukubaExpressKanjiMapFactoryBean.class.getDeclaredField("romajiOrHiragana");
		//
		if (romajiOrHiragana != null) {
			//
			romajiOrHiragana.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> setRomajiOrHiragana(instance, null));
		//
		Assertions.assertNull(get(romajiOrHiragana, instance));
		//
		Assertions.assertDoesNotThrow(() -> setRomajiOrHiragana(instance, ""));
		//
		Assertions.assertNull(get(romajiOrHiragana, instance));
		//
		Assertions.assertDoesNotThrow(() -> setRomajiOrHiragana(instance, " "));
		//
		Assertions.assertNull(get(romajiOrHiragana, instance));
		//
		Assertions.assertDoesNotThrow(() -> setRomajiOrHiragana(instance, "b"));
		//
		Assertions.assertNull(get(romajiOrHiragana, instance));
		//
		Assertions.assertDoesNotThrow(() -> setRomajiOrHiragana(instance, "".toCharArray()));
		//
		Assertions.assertNull(get(romajiOrHiragana, instance));
		//
		Assertions.assertDoesNotThrow(() -> setRomajiOrHiragana(instance, "h"));
		//
		final Object roh = get(romajiOrHiragana, instance);
		//
		Assertions.assertNotNull(roh);
		//
		Assertions.assertDoesNotThrow(() -> setRomajiOrHiragana(instance, roh));
		//
		Assertions.assertSame(roh, get(romajiOrHiragana, instance));
		//
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void setRomajiOrHiragana(final TsukubaExpressKanjiMapFactoryBean instance, final Object object) {
		if (instance != null) {
			instance.setRomajiOrHiragana(object);
		}
	}

	@Test
	void tsetGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
	}

	@Test
	void tsetGetObjectType() {
		//
		Assertions.assertEquals(Map.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testCreateEntry() throws Throwable {
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}", () -> createEntry(null, null));
		//
		if (CLASS_ROMAJI_OR_HIRAGANA != null && CLASS_ROMAJI_OR_HIRAGANA.isEnum()) {
			//
			for (final Object enumConstant : CLASS_ROMAJI_OR_HIRAGANA.getEnumConstants()) {
				//
				Assertions.assertEquals(ImmutablePair.of(null, null), createEntry(null, enumConstant));
				//
			} // for
				//
		} // for
			//
	}

	private static Entry<String, String> createEntry(final String url, final Object roh) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_ENTRY.invoke(null, url, roh);
			if (obj == null) {
				return null;
			} else if (obj instanceof Entry) {
				return (Entry) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetString() throws Throwable {
		//
		Assertions.assertNull(getString(Collections.singleton(null)));
		//
		Assertions.assertNull(
				getString(Collections.nCopies(2, cast(Element.class, Narcissus.allocateInstance(Element.class)))));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static String getString(final Iterable<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_GET_STRING.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
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
	void testName() throws Throwable {
		//
		Assertions.assertNull(name(null));
		//
	}

	private static String name(final Enum<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
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

	private static <T> void add(final Collection<T> items, final T item) throws Throwable {
		try {
			METHOD_ADD.invoke(null, items, item);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}