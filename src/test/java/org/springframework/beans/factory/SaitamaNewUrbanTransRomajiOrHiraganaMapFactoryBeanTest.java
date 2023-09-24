package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableFunction;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

class SaitamaNewUrbanTransRomajiOrHiraganaMapFactoryBeanTest {

	private static Class<?> CLASS_KANJI_HIRAGANA_ROMAJI = null;

	private static Method METHOD_GET_KANJI_HIRAGANA_ROMAJI_STRING, METHOD_GET_KANJI_HIRAGANA_ROMAJI_LIST,
			METHOD_ACCUMULATE2, METHOD_ACCUMULATE3, METHOD_TEST_AND_ACCEPT, METHOD_TEST_AND_APPLY4,
			METHOD_TEST_AND_APPLY5, METHOD_GET_KEY, METHOD_GET_VALUE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = SaitamaNewUrbanTransRomajiOrHiraganaMapFactoryBean.class;
		//
		(METHOD_GET_KANJI_HIRAGANA_ROMAJI_STRING = clz.getDeclaredMethod("getKanjiHiraganaRomaji", String.class))
				.setAccessible(true);
		//
		(METHOD_GET_KANJI_HIRAGANA_ROMAJI_LIST = clz.getDeclaredMethod("getKanjiHiraganaRomaji", List.class))
				.setAccessible(true);
		//
		(METHOD_ACCUMULATE2 = clz.getDeclaredMethod("accumulate", Map.class,
				CLASS_KANJI_HIRAGANA_ROMAJI = Class.forName(
						"org.springframework.beans.factory.SaitamaNewUrbanTransRomajiOrHiraganaMapFactoryBean$KanjiHiraganaRomaji")))
				.setAccessible(true);
		//
		(METHOD_ACCUMULATE3 = clz.getDeclaredMethod("accumulate", Map.class, CLASS_KANJI_HIRAGANA_ROMAJI,
				Field[].class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				FailableBiFunction.class, FailableBiFunction.class)).setAccessible(true);
		//
		(METHOD_GET_KEY = clz.getDeclaredMethod("getKey", List.class, String.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", List.class, String.class)).setAccessible(true);
		//
	}

	private SaitamaNewUrbanTransRomajiOrHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void before() {
		//
		instance = new SaitamaNewUrbanTransRomajiOrHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertSame(Map.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
	}

	@Test
	void testGetKanjiHiraganaRomaji() throws Throwable {
		//
		Assertions.assertNull(getKanjiHiraganaRomaji((String) null));
		//
		final ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		//
		Assertions.assertEquals("{\"kanji\":null,\"hiragana\":null,\"romaji\":null}", ObjectMapperUtil
				.writeValueAsString(objectMapper, getKanjiHiraganaRomaji(Collections.singletonList(null))));
		//
		Assertions.assertEquals("{\"kanji\":null,\"hiragana\":null,\"romaji\":null}", ObjectMapperUtil
				.writeValueAsString(objectMapper, getKanjiHiraganaRomaji(Arrays.asList(null, "", "NS 01"))));
		//
		Assertions.assertEquals("{\"kanji\":\"中\",\"hiragana\":\"あ\",\"romaji\":\"Ō\"}", ObjectMapperUtil
				.writeValueAsString(objectMapper, getKanjiHiraganaRomaji(Arrays.asList(null, "", "Ō", "中", "あ"))));
		//
	}

	private static Object getKanjiHiraganaRomaji(final String urlString) throws Throwable {
		try {
			return METHOD_GET_KANJI_HIRAGANA_ROMAJI_STRING.invoke(null, urlString);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Object getKanjiHiraganaRomaji(final List<String> ss) throws Throwable {
		try {
			return METHOD_GET_KANJI_HIRAGANA_ROMAJI_LIST.invoke(null, ss);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> Constructor<T> getDeclaredConstructor(final Class<T> instance, final Class<?>... parameterTypes)
			throws NoSuchMethodException {
		return instance != null ? instance.getDeclaredConstructor(parameterTypes) : null;
	}

	private static <T> T newInstance(final Constructor<T> instance, final Object... initargs)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		return instance != null ? instance.newInstance(initargs) : null;
	}

	@Test
	void testAccumulate() throws NoSuchMethodException, NoSuchFieldException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertDoesNotThrow(() -> accumulate(null, null));
		//
		Assertions.assertDoesNotThrow(() -> accumulate(null, null, new Field[] { null }));
		//
		final Field[] fs = new Field[] { Boolean.class.getDeclaredField("value") };
		//
		Assertions.assertDoesNotThrow(() -> accumulate(null, null, fs));
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_KANJI_HIRAGANA_ROMAJI);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> accumulate(null, newInstance(constructor), fs));
		//
	}

	private void accumulate(final Map<String, String> m, final Object v) throws Throwable {
		try {
			METHOD_ACCUMULATE2.invoke(instance, m, v);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private void accumulate(final Map<String, String> m, final Object v, final Field[] fs) throws Throwable {
		try {
			METHOD_ACCUMULATE3.invoke(instance, m, v, fs);
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
	void testGetKey() throws Throwable {
		//
		Assertions.assertEquals(Unit.with(null), getKey(null, null));
		//
		Assertions.assertEquals(Unit.with(null), getKey(Collections.emptyList(), null));
		//
		final List<UnicodeBlock> list = Collections.singletonList(null);
		//
		Assertions.assertEquals(Unit.with(null), getKey(list, null));
		//
		if (instance != null) {
			//
			instance.setKeyUnicodeBlock(UnicodeBlock.ADLAM);
			//
		} // if
			//
		Assertions.assertNull(getKey(list, null));
		//
	}

	private IValue0<String> getKey(final List<UnicodeBlock> unicodeBlocks, final String s) throws Throwable {
		try {
			final Object obj = METHOD_GET_KEY.invoke(instance, unicodeBlocks, s);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		Assertions.assertNull(getValue(null, null));
		//
		Assertions.assertNull(getValue(Collections.emptyList(), null));
		//
		List<UnicodeBlock> list = Collections.singletonList(null);
		//
		Assertions.assertEquals(Unit.with(null), getValue(list, null));
		//
		if (instance != null) {
			//
			instance.setValueUnicodeBlock(UnicodeBlock.BASIC_LATIN);
			//
		} // if
			//
		Assertions.assertNull(getValue(list, null));
		//
		Assertions.assertNull(getValue(Arrays.asList(null, null), null));
		//
		Assertions.assertEquals(Unit.with(null), getValue(
				list = Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.COMBINING_DIACRITICAL_MARKS), null));
		//
		if (instance != null) {
			//
			instance.setValueUnicodeBlock(null);
			//
		} // if
			//
		Assertions.assertNull(getValue(list, null));
		//
	}

	private IValue0<String> getValue(final List<UnicodeBlock> unicodeBlocks, final String s) throws Throwable {
		try {
			final Object obj = METHOD_GET_VALUE.invoke(instance, unicodeBlocks, s);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}