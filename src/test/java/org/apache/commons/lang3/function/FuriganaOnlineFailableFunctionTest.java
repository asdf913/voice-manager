package org.apache.commons.lang3.function;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class FuriganaOnlineFailableFunctionTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_GET, METHOD_TO_STRING, METHOD_CAST, METHOD_WRITE,
			METHOD_OPEN_CONNECTION, METHOD_GET_INPUT_STREAM, METHOD_GET_OUTPUT_STREAM = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = FuriganaOnlineFailableFunction.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET = clz.getDeclaredMethod("get", Map.class, Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_WRITE = clz.getDeclaredMethod("write", OutputStream.class, byte[].class)).setAccessible(true);
		//
		(METHOD_OPEN_CONNECTION = clz.getDeclaredMethod("openConnection", URL.class)).setAccessible(true);
		//
		(METHOD_GET_INPUT_STREAM = clz.getDeclaredMethod("getInputStream", URLConnection.class)).setAccessible(true);
		//
		(METHOD_GET_OUTPUT_STREAM = clz.getDeclaredMethod("getOutputStream", URLConnection.class)).setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof OutputStream) {
				//
				if (Objects.equals(methodName, "write")) {
					//
					return null;
					//
				} // if
					//
			} else if (self instanceof URLConnection
					&& contains(Arrays.asList("getInputStream", "getOutputStream"), methodName)) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private FuriganaOnlineFailableFunction instance = null;

	private URLConnection urlConnection = null;

	private MH mh = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		instance = new FuriganaOnlineFailableFunction();
		//
		urlConnection = ProxyUtil.createProxy(URLConnection.class, mh = new MH(), x -> {
			//
			final Constructor<?> constructor = x != null ? x.getDeclaredConstructor(URL.class) : null;
			//
			return constructor != null ? constructor.newInstance((Object) null) : null;
			//
		});
		//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Class<?> clz = FuriganaOnlineFailableFunction.class;
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
			if ((m = ms[i]) == null || m.isSynthetic()) {
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
			toString = Objects.toString(m);
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
				Assertions.assertNull(
						Narcissus.invokeMethod(
								instance = ObjectUtils.getIfNull(instance, FuriganaOnlineFailableFunction::new), m, os),
						toString);
				//
			} // if
				//
		} // for
			//
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

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	@Test
	void testTestAndApply() throws Throwable {
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
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get(Collections.emptyMap(), null));
		//
	}

	private static <V> V get(final Map<?, V> instance, final Object key) throws Throwable {
		try {
			return (V) METHOD_GET.invoke(null, instance, key);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToString() throws Throwable {
		//
		Assertions.assertSame(StringUtils.EMPTY, toString(StringUtils.EMPTY));
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

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertSame(StringUtils.EMPTY, cast(Object.class, StringUtils.EMPTY));
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
	void testWrite() {
		//
		Assertions.assertDoesNotThrow(() -> write(ProxyUtil.createProxy(OutputStream.class, mh), null));
		//
	}

	private static void write(final OutputStream instance, final byte[] bs) throws Throwable {
		try {
			METHOD_WRITE.invoke(null, instance, bs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOpenConnection() throws Throwable {
		//
		Assertions.assertNull(openConnection(cast(URL.class, Narcissus.allocateInstance(URL.class))));
		//
	}

	private static URLConnection openConnection(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_OPEN_CONNECTION.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URLConnection) {
				return (URLConnection) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetInputStream() throws Throwable {
		//
		Assertions.assertNull(getInputStream(urlConnection));
		//
	}

	private static InputStream getInputStream(final URLConnection instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_INPUT_STREAM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetOutputStream() throws Throwable {
		//
		Assertions.assertNull(getOutputStream(urlConnection));
		//
	}

	private static OutputStream getOutputStream(final URLConnection instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_OUTPUT_STREAM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof OutputStream) {
				return (OutputStream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}