package org.springframework.beans.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class UtilTest {

	private static Method METHOD_GET_NAME, METHOD_CAST, METHOD_IS_ASSIGNABLE_FROM, METHOD_ACCEPT = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = Util.class;
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_IS_ASSIGNABLE_FROM = clz.getDeclaredMethod("isAssignableFrom", Class.class, Class.class))
				.setAccessible(true);
		//
		(METHOD_ACCEPT = clz.getDeclaredMethod("accept", Consumer.class, Object.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

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
				} else if (Objects.equals(methodName, "map") || Objects.equals(methodName, "collect")) {
					//
					return null;
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
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
		Assertions.assertNull(cast(Object.class, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName(null));
		//
	}

	private static String getName(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME.invoke(null, instance);
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

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testIsAssignableFrom() throws Throwable {
		//
		Assertions.assertFalse(isAssignableFrom(null, null));
		//
		Assertions.assertFalse(isAssignableFrom(Object.class, null));
		//
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) throws Throwable {
		try {
			final Object obj = METHOD_IS_ASSIGNABLE_FROM.invoke(null, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAccept() {
		//
		Assertions.assertDoesNotThrow(() -> accept(null, null));
		//
	}

	private static <T> void accept(final Consumer<T> instance, final T value) throws Throwable {
		try {
			METHOD_ACCEPT.invoke(null, instance, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContains() {
		//
		Assertions.assertFalse(Util.contains(null, null));
		//
	}

	@Test
	void testGetKey() {
		//
		Assertions.assertNull(Util.getKey(null));
		//
	}

	@Test
	void testGetValue() {
		//
		Assertions.assertNull(Util.getValue(null));
		//
	}

	@Test
	void testAdd() {
		//
		Assertions.assertDoesNotThrow(() -> Util.add(null, null));
		//
	}

	@Test
	void testFilter() {
		//
		Assertions.assertSame(stream, Util.filter(stream, null));
		//
	}

	@Test
	void testMap() {
		//
		Assertions.assertNull(Util.map(stream, null));
		//
	}

	@Test
	void testGetType() {
		//
		Assertions.assertNull(Util.getType(null));
		//
	}

	@Test
	void testMatcher() {
		//
		Assertions.assertNull(Util.matcher(null, null));
		//
		Assertions.assertNull(Util.matcher(Pattern.compile(""), null));
		//
		Assertions.assertNull(Util.matcher(Util.cast(Pattern.class, Narcissus.allocateInstance(Pattern.class)), ""));
		//
	}

	@Test
	void testMatches() {
		//
		Assertions.assertTrue(Util.matches(Util.matcher(Pattern.compile("\\d"), "1")));
		//
		Assertions.assertFalse(Util.matches(Util.cast(Matcher.class, Narcissus.allocateInstance(Matcher.class))));
		//
	}

	@Test
	void testCollect() {
		//
		Assertions.assertNull(Util.collect(stream, null, null, null));
		//
		final Stream<Object> empty = Stream.empty();
		//
		Assertions.assertNull(Util.collect(empty, null, null, null));
		//
		final Supplier<Object> supplier = () -> null;
		//
		Assertions.assertNull(Util.collect(empty, supplier, null, null));
		//
		Assertions.assertNull(Util.collect(empty, supplier, (a, b) -> {
		}, null));
		//
	}
}