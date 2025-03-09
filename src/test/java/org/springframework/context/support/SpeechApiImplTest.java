package org.springframework.context.support;

import java.lang.reflect.Executable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class SpeechApiImplTest {

	private static Method METHOD_GET_PARAMETER_COUNT, METHOD_INVOKE, METHOD_AND = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = SpeechApiImpl.class;
		//
		if ((METHOD_GET_PARAMETER_COUNT = Util.getDeclaredMethod(clz, "getParameterCount", Executable.class)) != null) {
			//
			METHOD_GET_PARAMETER_COUNT.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_INVOKE = Util.getDeclaredMethod(clz, "invoke", Method.class, Object.class,
				Object[].class)) != null) {
			//
			METHOD_INVOKE.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_AND = Util.getDeclaredMethod(clz, "and", Boolean.TYPE, Boolean.TYPE, boolean[].class)) != null) {
			//
			METHOD_AND.setAccessible(true);
			//
		} // if
			//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> new SpeechApiImpl().afterPropertiesSet());
		//
	}

	@Test
	void testGetParameterCount() throws Throwable {
		//
		Assertions.assertEquals(0, getParameterCount(null));
		//
	}

	private static int getParameterCount(final Executable instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PARAMETER_COUNT.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testInvoke() throws Throwable {
		//
		Assertions.assertNull(invoke(null, null));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args) throws Throwable {
		try {
			return METHOD_INVOKE.invoke(null, method, instance, args);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnd() throws Throwable {
		//
		Assertions.assertFalse(and(true, false));
		//
		Assertions.assertTrue(and(true, true, null));
		//
		Assertions.assertFalse(and(true, true, false));
		//
	}

	private static boolean and(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIH() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.SpeechApiImpl$IH");
		//
		final Method get = Util.getDeclaredMethod(clz, "get", Map.class, Object.class);
		//
		if (get != null) {
			//
			get.setAccessible(true);
			//
			Assertions.assertNull(get.invoke(null, null, null));
			//
			Assertions.assertNull(get.invoke(null, Collections.emptyMap(), null));
			//
		} // if
			//
		final Method and = Util.getDeclaredMethod(clz, "and", Boolean.TYPE, Boolean.TYPE, boolean[].class);
		//
		if (and != null) {
			//
			and.setAccessible(true);
			//
			Assertions.assertEquals(Boolean.TRUE, and.invoke(null, Boolean.TRUE, Boolean.TRUE, null));
			//
			Assertions.assertEquals(Boolean.FALSE,
					and.invoke(null, Boolean.TRUE, Boolean.TRUE, new boolean[] { false }));
			//
		} // if
			//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, Narcissus.allocateInstance(clz));
		//
		if (ih != null) {
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(null, null, null));
			//
			final SpeechApi speechApi = Reflection.newProxy(SpeechApi.class, ih);
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(speechApi, null, null));
			//
			Assertions.assertThrows(Throwable.class,
					() -> ih.invoke(speechApi, Util.getDeclaredMethod(SpeechApi.class, "isInstalled"), null));
			//
		} // if
			//
	}

}