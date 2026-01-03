package org.apache.commons.validator.routines;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class UrlValidatorUtilTest {

	private static Method METHOD_FILTER, METHOD_GET_CLASS = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = UrlValidatorUtil.class;
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = getName(method);
			//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(name, "filter")) {
					//
					return proxy;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private static class MH implements MethodHandler {

		private Boolean isValid = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = getName(thisMethod);
			//
			if (self instanceof UrlValidator && Objects.equals(methodName, "isValid")) {
				//
				return isValid;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	@Test
	void testNull() {
		//
		final Method[] ms = UrlValidatorUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Object result = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic() || !Modifier.isStatic(m.getModifiers())) {
				//
				continue;
				//
			} // if
				//
			result = Narcissus.invokeStaticMethod(m, toArray(Collections.nCopies(m.getParameterCount(), null)));
			//
			toString = m.toString();
			//
			if (isPrimitive(m.getReturnType())) {
				//
				Assertions.assertNotNull(result, toString);
				//
			} else {
				//
				Assertions.assertNull(result, toString);
				//
			} // if
				//
		} // for
			//
	}

	private static boolean isPrimitive(final Class<?> instance) {
		return instance != null && instance.isPrimitive();
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testIsValid() throws Throwable {
		//
		Assertions.assertFalse(UrlValidatorUtil.isValid(null, null));
		//
		Assertions.assertFalse(UrlValidatorUtil
				.isValid(cast(UrlValidator.class, Narcissus.allocateInstance(UrlValidator.class)), null));
		//
		final MH mh = new MH();
		//
		mh.isValid = Boolean.TRUE;
		//
		final UrlValidator urlValidator = ProxyUtil.createProxy(UrlValidator.class, mh);
		//
		Assertions.assertTrue(UrlValidatorUtil.isValid(urlValidator, null));
		//
		Assertions.assertFalse(
				UrlValidatorUtil.isValid(urlValidator, cast(String.class, Narcissus.allocateInstance(String.class))));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
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
			final Object obj = invoke(METHOD_FILTER, null, instance, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream s) {
				return s;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_CLASS, null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class c) {
				return c;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

}