package org.slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.bcel.classfile.Code;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

class LoggerUtilTest {

	private static Method METHOD_GET_CODE, METHOD_MATCHER, METHOD_MATCHES, METHOD_TO_LIST, METHOD_IS_EMPTY,
			METHOD_FILTER, METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = LoggerUtil.class;
		//
		(METHOD_GET_CODE = clz.getDeclaredMethod("getCode", Code.class)).setAccessible(true);
		//
		(METHOD_MATCHER = clz.getDeclaredMethod("matcher", Pattern.class, CharSequence.class)).setAccessible(true);
		//
		(METHOD_MATCHES = clz.getDeclaredMethod("matches", Matcher.class)).setAccessible(true);
		//
		(METHOD_TO_LIST = clz.getDeclaredMethod("toList", Stream.class)).setAccessible(true);
		//
		(METHOD_IS_EMPTY = clz.getDeclaredMethod("isEmpty", Collection.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class, Function.class,
				Function.class)).setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private List<?> toList = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "filter")) {
					//
					return proxy;
					//
				} else if (Objects.equals(methodName, "toList")) {
					//
					return toList;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private Stream<?> stream = null;

	@BeforeEach
	void beforeEach() {
		//
		stream = Reflection.newProxy(Stream.class, new IH());
		//
	}

	@Test
	void testIsNOPLogger() {
		//
		Assertions.assertFalse(LoggerUtil.isNOPLogger(null));
		//
	}

	@Test
	void testGetCode() throws Throwable {
		//
		Assertions.assertNull(getCode(null));
		//
	}

	private static byte[] getCode(final Code instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CODE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof byte[]) {
				return (byte[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testMatcher() throws Throwable {
		//
		Assertions.assertNull(matcher(null, null));
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
	void testMatches() throws Throwable {
		//
		Assertions.assertFalse(matches(null));
		//
		final Pattern pattern = Pattern.compile("\\d+");
		//
		Assertions.assertFalse(matches(matcher(pattern, null)));
		//
		Assertions.assertFalse(matches(matcher(pattern, "")));
		//
		Assertions.assertTrue(matches(matcher(pattern, "1")));
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
	void testIsEmpty() throws Throwable {
		//
		Assertions.assertTrue(isEmpty(Collections.emptySet()));
		//
		Assertions.assertFalse(isEmpty(Collections.singleton(null)));
		//
	}

	private static boolean isEmpty(final Collection<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_EMPTY.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToList() throws Throwable {
		//
		Assertions.assertNull(toList(stream));
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_LIST.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(null, null));
		//
		Assertions.assertNotNull(filter(stream, null));
		//
		Assertions.assertNull(filter(Stream.empty(), null));
		//
		Assertions.assertNotNull(filter(Stream.empty(), Predicates.alwaysTrue()));
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

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			final Function<T, R> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}