package org.springframework.context.support;

import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SpeechApiImplTest {

	private static Method METHOD_GET_PARAMETER_COUNT, METHOD_IS_STATIC, METHOD_INVOKE, METHOD_AND = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = SpeechApiImpl.class;
		//
		if ((METHOD_GET_PARAMETER_COUNT = clz != null ? clz.getDeclaredMethod("getParameterCount", Executable.class)
				: null) != null) {
			//
			METHOD_GET_PARAMETER_COUNT.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_IS_STATIC = clz != null ? clz.getDeclaredMethod("isStatic", Member.class) : null) != null) {
			//
			METHOD_IS_STATIC.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_INVOKE = clz != null ? clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class)
				: null) != null) {
			//
			METHOD_INVOKE.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_AND = clz != null ? clz.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE, boolean[].class)
				: null) != null) {
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

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsStatic() throws Throwable {
		//
		Assertions.assertFalse(isStatic(null));
		//
		Assertions.assertFalse(isStatic(Object.class.getDeclaredMethod("toString")));
		//
	}

	private static boolean isStatic(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_STATIC.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}