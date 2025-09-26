package j2html.tags.specialized;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class ATagUtilTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_GET_INPUT_STREAM, METHOD_SET_REQUEST_PROPERTY,
			METHOD_OPEN_CONNECTION, METHOD_TO_URL, METHOD_FOR_NAME = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = ATagUtil.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_INPUT_STREAM = clz.getDeclaredMethod("getInputStream", URLConnection.class)).setAccessible(true);
		//
		(METHOD_SET_REQUEST_PROPERTY = clz.getDeclaredMethod("setRequestProperty", URLConnection.class, String.class,
				String.class)).setAccessible(true);
		//
		(METHOD_OPEN_CONNECTION = clz.getDeclaredMethod("openConnection", URL.class)).setAccessible(true);
		//
		(METHOD_TO_URL = clz.getDeclaredMethod("toURL", URI.class)).setAccessible(true);
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String name = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof URLConnection) {
				//
				if (Objects.equals(name, "getInputStream")) {
					//
					return null;
					//
				} else if (Objects.equals(name, "setRequestProperty")) {
					//
					return proceed != null ? proceed.invoke(self, args) : null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private URLConnection urlConnection = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		urlConnection = ProxyUtil.createProxy(URLConnection.class, new MH(), x -> {
			//
			final Constructor<?> constructor = x != null ? x.getDeclaredConstructor(URL.class) : null;
			//
			return constructor != null ? constructor.newInstance((Object) null) : null;
			//
		});
		//
	}

	@Test
	void testTestAndApply() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(
				Narcissus.invokeStaticMethod(METHOD_TEST_AND_APPLY, Predicates.alwaysTrue(), null, null, null));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = ATagUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		List<Object> list = null;
		//
		Object invokeStaticMethod = null;
		//
		String toString = null;
		//
		Class<?>[] parameterTypes = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			if ((list = ObjectUtils.getIfNull(list, ArrayList::new)) != null
					&& (parameterTypes = m.getParameterTypes()) != null) {
				//
				list.clear();
				//
				for (final Class<?> parameterType : parameterTypes) {
					//
					if (Objects.equals(parameterType, Boolean.TYPE)) {
						//
						list.add(Boolean.TRUE);
						//
					} else {
						//
						list.add(null);
						//
					} // if
						//
				} // for
					//
			} // if
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(list));
			//
			toString = Objects.toString(m);
			//
			if (Objects.equals(m.getReturnType(), Boolean.TYPE) || Boolean.logicalAnd(
					Objects.equals(m.getName(), "createByUrl"),
					Boolean.logicalOr(
							Arrays.equals(parameterTypes = m.getParameterTypes(), new Class<?>[] { String.class }),
							Arrays.equals(parameterTypes, new Class<?>[] { String.class, String.class })))) {
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
	public void testCreateByUrl() throws Exception {
		//
		String url = "";
		//
		Assertions.assertEquals(String.format("<a href=\"%1$s\">null</a>", url),
				Objects.toString(ATagUtil.createByUrl(url)));
		//
		Assertions.assertEquals(String.format("<a href=\"%1$s\">null</a>", url = " "),
				Objects.toString(ATagUtil.createByUrl(url)));
		//
	}

	@Test
	void testGetInputStream() throws Throwable {
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(METHOD_GET_INPUT_STREAM, urlConnection));
		//
	}

	@Test
	void testSetRequestProperty() throws Throwable {
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(METHOD_SET_REQUEST_PROPERTY, urlConnection, null, null));
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(METHOD_SET_REQUEST_PROPERTY, urlConnection, "", null));
		//
		Narcissus.setField(urlConnection, Narcissus.findField(URLConnection.class, "connected"), Boolean.TRUE);
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(METHOD_SET_REQUEST_PROPERTY, urlConnection, "", null));
		//
	}

	@Test
	void testOpenConnection() {
		//
		Assertions.assertNull(
				Narcissus.invokeStaticMethod(METHOD_OPEN_CONNECTION, Narcissus.allocateInstance(URL.class)));
		//
		Assertions.assertNotNull(Narcissus.invokeStaticMethod(METHOD_OPEN_CONNECTION,
				Class.class.getResource("/java/lang/String.class")));
		//
	}

	@Test
	void testToURL() throws Throwable {
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(METHOD_TO_URL, Narcissus.allocateInstance(URI.class)));
		//
		Assertions.assertNotNull(Narcissus.invokeStaticMethod(METHOD_TO_URL, new URI("http://127.0.0.1")));
		//
	}

	@Test
	void testForName() throws Throwable {
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(METHOD_FOR_NAME, ""));
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(METHOD_FOR_NAME, " "));
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(METHOD_FOR_NAME, "a"));
		//
	}

}