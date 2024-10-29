package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

import io.github.toolfactory.narcissus.Narcissus;

class KantetsuKanjiRomajiOrHiraganaMapFactoryBeanTest {

	private static Class<?> CLASS_KANJI_HIRAGANA_ROMAJI = null;

	private static Method METHOD_GET_OBJECT, METHOD_GET_KANJI_HIRAGANA_ROMAJI, METHOD_TEST_AND_APPLY4,
			METHOD_TEST_AND_APPLY5, METHOD_GET_PATH, METHOD_TEST_AND_ACCEPT, METHOD_ACCUMULATE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = KantetsuKanjiRomajiOrHiraganaMapFactoryBean.class;
		//
		(METHOD_GET_OBJECT = clz.getDeclaredMethod("getObject", Stream.class)).setAccessible(true);
		//
		(METHOD_GET_KANJI_HIRAGANA_ROMAJI = clz.getDeclaredMethod("getKanjiHiraganaRomaji", Element.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				FailableBiFunction.class, FailableBiFunction.class)).setAccessible(true);
		//
		(METHOD_GET_PATH = clz.getDeclaredMethod("getPath", URL.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_ACCUMULATE = clz.getDeclaredMethod("accumulate", Map.class, CLASS_KANJI_HIRAGANA_ROMAJI = Class.forName(
				"org.springframework.beans.factory.KantetsuKanjiRomajiOrHiraganaMapFactoryBean$KanjiHiraganaRomaji"),
				Field[].class)).setAccessible(true);
		//
	}

	private KantetsuKanjiRomajiOrHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new KantetsuKanjiRomajiOrHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertSame(Map.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setUrl("");
			//
		} // if
			//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setUrl(" ");
			//
		} // if
			//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		Assertions.assertNull(getObject(null));
		//
		Assertions.assertEquals(Collections.emptyMap(), getObject(Stream.of((Object) null)));
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_KANJI_HIRAGANA_ROMAJI);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Object kanjiHiraganaRomaji = newInstance(constructor);
		//
		Assertions.assertEquals(Collections.emptyMap(), getObject(Stream.of(kanjiHiraganaRomaji)));
		//
		if (instance != null) {
			//
			instance.setKeyUnicodeBlock(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
			//
		} // if
			//
		FieldUtils.writeDeclaredField(kanjiHiraganaRomaji, "kanji", "1", true);
		//
		Assertions.assertEquals(Collections.emptyMap(), getObject(Stream.of(kanjiHiraganaRomaji)));
		//
		final String kanji = "一";
		//
		FieldUtils.writeDeclaredField(kanjiHiraganaRomaji, "kanji", "一", true);
		//
		Assertions.assertEquals(Collections.emptyMap(), getObject(Stream.of(kanjiHiraganaRomaji)));
		//
		if (instance != null) {
			//
			instance.setValueUnicodeBlock(UnicodeBlock.HIRAGANA);
			//
		} // if
			//
		FieldUtils.writeDeclaredField(kanjiHiraganaRomaji, "hiragana", "1", true);
		//
		Assertions.assertEquals(Collections.emptyMap(), getObject(Stream.of(kanjiHiraganaRomaji)));
		//
		final String hiragana = "いち";
		//
		FieldUtils.writeDeclaredField(kanjiHiraganaRomaji, "hiragana", "いち", true);
		//
		Assertions.assertEquals(Collections.singletonMap(kanji, hiragana), getObject(Stream.of(kanjiHiraganaRomaji)));
		//
	}

	private static <T> Constructor<T> getDeclaredConstructor(final Class<T> instance, final Class<?>... parameterTypes)
			throws NoSuchMethodException, SecurityException {
		return instance != null ? instance.getDeclaredConstructor(parameterTypes) : null;
	}

	private static <T> T newInstance(final Constructor<T> instance, final Object... initargs)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		return instance != null ? instance.newInstance(initargs) : null;
	}

	private Map<String, String> getObject(final Stream<?> stream) throws Throwable {
		try {
			final Object obj = METHOD_GET_OBJECT.invoke(instance, stream);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetKanjiHiraganaRomaji() throws Throwable {
		//
		final ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		//
		Assertions.assertEquals("{\"kanji\":null,\"hiragana\":null,\"romaji\":null}",
				ObjectMapperUtil.writeValueAsString(objectMapper, getKanjiHiraganaRomaji(null)));
		//
		final Element element = Util.cast(Element.class, Narcissus.allocateInstance(Element.class));
		//
		Assertions.assertEquals("{\"kanji\":null,\"hiragana\":null,\"romaji\":null}",
				ObjectMapperUtil.writeValueAsString(objectMapper, getKanjiHiraganaRomaji(null)));
		//
		if (element != null) {
			//
			element.attr("href", ".pdf");
			//
		} // if
			//
		Assertions.assertNull(getKanjiHiraganaRomaji(element));
		//
		if (element != null) {
			//
			element.attr("href", "");
			//
		} // if
			//
		Assertions.assertEquals("{\"kanji\":null,\"hiragana\":null,\"romaji\":null}",
				ObjectMapperUtil.writeValueAsString(objectMapper, getKanjiHiraganaRomaji(null)));
		//
	}

	private static Object getKanjiHiraganaRomaji(final Element instance) throws Throwable {
		try {
			return METHOD_GET_KANJI_HIRAGANA_ROMAJI.invoke(null, instance);
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

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, @Nullable final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY5.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void tesGetPath() throws Throwable {
		//
		Assertions.assertNull(getPath(Util.cast(URL.class, Narcissus.allocateInstance(URL.class))));
		//
	}

	private static String getPath(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PATH.invoke(null, instance);
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
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.biAlwaysTrue(), null, null, null));
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
	void testAccumulate() throws NoSuchMethodException, NoSuchFieldException {
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

	private void accumulate(final Map<String, String> m, final Object v, final Field[] fs) throws Throwable {
		try {
			METHOD_ACCUMULATE.invoke(instance, m, v, fs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}