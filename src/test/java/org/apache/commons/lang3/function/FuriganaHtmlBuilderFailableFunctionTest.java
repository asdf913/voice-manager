package org.apache.commons.lang3.function;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class FuriganaHtmlBuilderFailableFunctionTest {

	private static Method METHOD_TO_STRING, METHOD_GET_CLASS, METHOD_CAST, METHOD_AND, METHOD_MATCHES, METHOD_MATCHER,
			METHOD_FILTER = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = FuriganaHtmlBuilderFailableFunction.class;
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_MATCHES = clz.getDeclaredMethod("matches", Matcher.class)).setAccessible(true);
		//
		(METHOD_MATCHER = clz.getDeclaredMethod("matcher", Pattern.class, CharSequence.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Stream && Objects.equals(methodName, "filter")) {
				//
				return proxy;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private FuriganaHtmlBuilderFailableFunction instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new FuriganaHtmlBuilderFailableFunction();
		//
	}

	@Test
	void testApply() throws IOException {
		//
		Assertions.assertEquals(
				"それは<ruby><rb>大変</rb><rp>(</rp><rt>たいへん</rt><rp>)</rp></ruby>ですね。すぐ<ruby><rb>交番</rb><rp>(</rp><rt>こうばん</rt><rp>)</rp></ruby>に<ruby><rb>行</rb><rp>(</rp><rt>い</rt><rp>)</rp></ruby>かないと。",
				FailableFunctionUtil.apply(instance, "それは大変ですね。すぐ交番に行かないと。"));
		//
		Assertions.assertEquals(
				"テーブルに<ruby><rb>花瓶</rb><rp>(</rp><rt>かびん</rt><rp>)</rp></ruby>が<ruby><rb>置</rb><rp>(</rp><rt>お</rt><rp>)</rp></ruby>いてあります。",
				FailableFunctionUtil.apply(instance, "テーブルに花瓶が置いてあります。"));
		//
		Assertions.assertEquals(
				"ごみ<ruby><rb>箱</rb><rp>(</rp><rt>ばこ</rt><rp>)</rp></ruby>は<ruby><rb>台所</rb><rp>(</rp><rt>だいどころ</rt><rp>)</rp></ruby>の<ruby><rb>隅</rb><rp>(</rp><rt>すみ</rt><rp>)</rp></ruby>に<ruby><rb>置</rb><rp>(</rp><rt>お</rt><rp>)</rp></ruby>いてあります。",
				FailableFunctionUtil.apply(instance, "ごみ箱は台所の隅に置いてあります。"));
		//
		Assertions.assertEquals(
				"ハサミは<ruby><rb>引</rb><rp>(</rp><rt>ひ</rt><rp>)</rp></ruby>き<ruby><rb>出</rb><rp>(</rp><rt>だ</rt><rp>)</rp></ruby>しに<ruby><rb>入</rb><rp>(</rp><rt>い</rt><rp>)</rp></ruby>れてあります。",
				FailableFunctionUtil.apply(instance, "ハサミは引き出しに入れてあります。"));
		//
		Assertions.assertEquals(
				"7<ruby><rb>時</rb><rp>(</rp><rt>じ</rt><rp>)</rp></ruby>の<ruby><rb>新幹線</rb><rp>(</rp><rt>しんかんせん</rt><rp>)</rp></ruby>に<ruby><rb>乗</rb><rp>(</rp><rt>の</rt><rp>)</rp></ruby>る<ruby><rb>予定</rb><rp>(</rp><rt>よてい</rt><rp>)</rp></ruby>です。",
				FailableFunctionUtil.apply(instance, "7時の新幹線に乗る予定です。"));
		//
		final String string = "さんじゅうきゅ";
		//
		Assertions.assertEquals(string, FailableFunctionUtil.apply(instance, string));
		//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Class<?> clz = FuriganaHtmlBuilderFailableFunction.class;
		//
		final Method[] ms = getDeclaredMethods(clz);
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		Object invoke = null;
		//
		String toString = null;
		//
		Object[] os = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()
					|| Boolean.logicalAnd(contains(Arrays.asList("and", "or"), getName(m)), Arrays.equals(
							m.getParameterTypes(), new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class }))) {
				//
				continue;
				//
			} // if
				//
			if ((collection = ObjectUtils.getIfNull(collection, ArrayList::new)) != null) {
				//
				collection.clear();
				//
			} // if
				//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = parameterTypes[j], Integer.TYPE)) {
					//
					add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
					//
					add(collection, Boolean.FALSE);
					//
				} else {
					//
					add(collection, null);
					//
				} // if
					//
			} // if
				//
			os = toArray(collection);
			//
			toString = toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (contains(Arrays.asList(Integer.TYPE, Boolean.TYPE), m.getReturnType())) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
					//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(
						instance = ObjectUtils.getIfNull(instance, FuriganaHtmlBuilderFailableFunction::new), m, os),
						toString);
				//
			} // if
				//
		} // for
			//
	}

	private static String toString(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_STRING.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static boolean contains(final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(Object.class, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void tsetAnd() throws Throwable {
		//
		Assertions.assertTrue(and(true, true, null));
		//
	}

	private static boolean and(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMatches() throws Throwable {
		//
		Assertions.assertFalse(matches(cast(Matcher.class, Narcissus.allocateInstance(Matcher.class))));
		//
	}

	private static boolean matches(final Matcher instance) throws Throwable {
		try {
			final Object obj = METHOD_MATCHES.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMatcher() throws Throwable {
		//
		Assertions.assertNull(matcher(cast(Pattern.class, Narcissus.allocateInstance(Pattern.class)), null));
		//
	}

	private static Matcher matcher(final Pattern pattern, final CharSequence input) throws Throwable {
		try {
			final Object obj = METHOD_MATCHER.invoke(null, pattern, input);
			if (obj == null) {
				return null;
			} else if (obj instanceof Matcher) {
				return (Matcher) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		final Stream<?> stream = Reflection.newProxy(Stream.class, new IH());
		//
		Assertions.assertSame(stream, filter(stream, null));
		//
		Assertions.assertNull(filter(Stream.ofNullable(null), null));
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_FILTER.invoke(null, instance, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}