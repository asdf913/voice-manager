package org.springframework.beans.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.stream.Streams.FailableStream;
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
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class KominatoKanjiRomajiOrHiraganaMapFactoryBeanTest {

	private static Class<?> CLASS_KANJI_HIRAGANA_ROMAJI = null;

	private static Method METHOD_GET_KANJI_HIRAGANA_ROMAJI_STRING, METHOD_GET_KANJI_HIRAGANA_ROMAJI_LIST,
			METHOD_ACCUMULATE2, METHOD_ACCUMULATE3, METHOD_TEST_AND_ACCEPT, METHOD_TEST_AND_APPLY4,
			METHOD_TEST_AND_APPLY5, METHOD_IS_ALL_FIELD_NON_BLANK = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = KominatoKanjiRomajiOrHiraganaMapFactoryBean.class;
		//
		(METHOD_GET_KANJI_HIRAGANA_ROMAJI_STRING = clz.getDeclaredMethod("getKanjiHiraganaRomaji", String.class))
				.setAccessible(true);
		//
		(METHOD_GET_KANJI_HIRAGANA_ROMAJI_LIST = clz.getDeclaredMethod("getKanjiHiraganaRomaji", List.class))
				.setAccessible(true);
		//
		(METHOD_ACCUMULATE2 = clz.getDeclaredMethod("accumulate", Map.class,
				CLASS_KANJI_HIRAGANA_ROMAJI = Class.forName(
						"org.springframework.beans.factory.KominatoKanjiRomajiOrHiraganaMapFactoryBean$KanjiHiraganaRomaji")))
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
		(METHOD_IS_ALL_FIELD_NON_BLANK = clz.getDeclaredMethod("isAllFieldNonBlank", Field[].class,
				CLASS_KANJI_HIRAGANA_ROMAJI)).setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		private String text = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof Element) {
				//
				if (Objects.equals(methodName, "text")) {
					//
					return text;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private KominatoKanjiRomajiOrHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void before() {
		//
		instance = new KominatoKanjiRomajiOrHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertSame(Map.class, FactoryBeanUtil.getObjectType(instance));
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
		Assertions.assertNull(getKanjiHiraganaRomaji(Collections.singletonList(null)));
		//
		final ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		//
		Assertions.assertEquals("{\"kanji\":null,\"hiragana\":null,\"romaji\":null}",
				ObjectMapperUtil.writeValueAsString(objectMapper, getKanjiHiraganaRomaji(
						Collections.nCopies(2, Util.cast(Element.class, Narcissus.allocateInstance(Element.class))))));
		//
		final MH mh1 = new MH();
		//
		mh1.text = "中";
		//
		final MH mh2 = new MH();
		//
		mh2.text = "あ";
		//
		final MH mh3 = new MH();
		//
		mh3.text = "a";
		//
		Assertions
				.assertEquals("{\"kanji\":\"中\",\"hiragana\":\"あ\",\"romaji\":\"a\"}",
						ObjectMapperUtil
								.writeValueAsString(objectMapper,
										getKanjiHiraganaRomaji(
												new FailableStream<>(Stream.of(mh1, mh2, mh3))
														.map(x -> createProxy(Element.class, x,
																y -> newInstance(
																		getDeclaredConstructor(y, String.class), "a")))
														.collect(Collectors.toList()))));
		//
	}

	private static <T, E extends ReflectiveOperationException> T createProxy(final Class<T> clz, final MethodHandler mh,
			final FailableFunction<Class<?>, Object, E> function) throws ReflectiveOperationException {
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(Element.class);
		//
		final Class<?> c = proxyFactory.createClass();
		//
		Object instance = function != null ? function.apply(c) : null;
		//
		if (instance == null) {
			//
			instance = newInstance(c != null ? c.getDeclaredConstructor() : null);
			//
		} // if
			//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(mh);
			//
		} // if
			//
		return Util.cast(clz, instance);
		//
	}

	private static Object getKanjiHiraganaRomaji(final String urlString) throws Throwable {
		try {
			return METHOD_GET_KANJI_HIRAGANA_ROMAJI_STRING.invoke(null, urlString);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Object getKanjiHiraganaRomaji(final List<Element> elements) throws Throwable {
		try {
			return METHOD_GET_KANJI_HIRAGANA_ROMAJI_LIST.invoke(null, elements);
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
	void testIsAllFieldNonBlank() throws Throwable {
		//
		Assertions.assertFalse(isAllFieldNonBlank(null, null));
		//
		Assertions.assertFalse(isAllFieldNonBlank(new Field[] { null }, null));
		//
		final Constructor<?> constructor = getDeclaredConstructor(CLASS_KANJI_HIRAGANA_ROMAJI);
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		Assertions.assertTrue(
				isAllFieldNonBlank(new Field[] { Boolean.class.getDeclaredField("TRUE") }, newInstance(constructor)));
		//
		Assertions.assertFalse(
				isAllFieldNonBlank(new Field[] { Boolean.class.getDeclaredField("value") }, newInstance(constructor)));
		//
		Assertions.assertFalse(
				isAllFieldNonBlank(new Field[] { Long.class.getDeclaredField("value") }, newInstance(constructor)));
		//
	}

	private static boolean isAllFieldNonBlank(final Field[] fs, final Object kanjiHiraganaRomaji) throws Throwable {
		try {
			final Object obj = METHOD_IS_ALL_FIELD_NON_BLANK.invoke(null, fs, kanjiHiraganaRomaji);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}