package org.htmlunit.html;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class DomElementUtilTest {

	private static Method METHOD_GET_NAME, METHOD_TEST, METHOD_GET_CLASS, METHOD_FILTER = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = DomElementUtil.class;
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, @Nullable final Method method, @Nullable final Object[] args)
				throws Throwable {
			//
			final String methodName = getName(method);
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

		private static String getName(final Member instance) throws Throwable {
			try {
				final Object obj = METHOD_GET_NAME.invoke(null, instance);
				if (obj == null) {
					return null;
				} else if (obj instanceof String) {
					return (String) obj;
				}
				throw new Throwable(Objects.toString(DomElementUtilTest.getClass(obj)));
			} catch (final InvocationTargetException e) {
				throw e.getTargetException();
			}
		}

	}

	@Test
	void testClick() throws IOException {
		//
		Assertions
				.assertNull(DomElementUtil.click(cast(DomElement.class, Narcissus.allocateInstance(DomElement.class))));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

	@Test
	void testNull() {
		//
		final Method[] ms = DomElementUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Object invokeStaticMethod;
		//
		String toString;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m,
					toArray(Collections.nCopies(m.getParameterCount(), null)));
			//
			toString = Objects.toString(m);
			//
			if (ArrayUtils.contains(new Class<?>[] { Boolean.TYPE, Integer.TYPE }, m.getReturnType())) {
				//
				Assertions.assertNotNull(invokeStaticMethod, toString);
				//
			} else {
				//
				Assertions.assertNull(invokeStaticMethod, toString);
				//
			} // if
				//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(Predicates.alwaysFalse(), null));
		//
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Objects.toString(getClass(obj)));
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
				return (Class) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Stream<?> stream = Stream.empty();
		//
		Assertions.assertSame(stream, filter(stream, null));
		//
		Assertions.assertSame(stream = Reflection.newProxy(Stream.class, new IH()), filter(stream, null));
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
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}