package org.springframework.context.support;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class SpeechApiImplTest {

	private static Method METHOD_AND, METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = SpeechApiImpl.class;
		//
		if ((METHOD_AND = Util.getDeclaredMethod(clz, "and", Boolean.TYPE, Boolean.TYPE, boolean[].class)) != null) {
			//
			METHOD_AND.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_TEST_AND_APPLY = Util.getDeclaredMethod(clz, "testAndApply", Predicate.class, Object.class,
				Function.class, Function.class)) != null) {
			//
			METHOD_TEST_AND_APPLY.setAccessible(true);
			//
		} // if
			//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Method[] ms = SpeechApiImpl.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		Object invoke = null;
		//
		String toString = null;
		//
		Class<?> returnType = null;
		//
		SpeechApiImpl instance = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()
					|| Boolean.logicalAnd(Objects.equals(Util.getName(m), "and"),
							Arrays.equals(Util.getParameterTypes(m),
									new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class }))
					|| Boolean.logicalAnd(Objects.equals(Util.getName(m), "getVoiceAttribute"),
							Arrays.equals(Util.getParameterTypes(m), new Class<?>[] { String.class, String.class }))
					|| Boolean.logicalAnd(Objects.equals(Util.getName(m), "speak"),
							Arrays.equals(Util.getParameterTypes(m),
									new Class<?>[] { String.class, String.class, Integer.TYPE, Integer.TYPE }))
					|| Boolean.logicalAnd(Objects.equals(Util.getName(m), "writeVoiceToFile"),
							Arrays.equals(Util.getParameterTypes(m),
									new Class<?>[] { String.class, String.class, Integer.TYPE, Integer.TYPE,
											File.class }))
					|| Boolean.logicalAnd(m.getParameterCount() == 0, Util.contains(Arrays.asList("getProviderName",
							"getVoiceIds", "getProviderPlatform", "getProviderVersion"), Util.getName(m)))) {
				//
				continue;
				//
			} // if
				//
			System.out.println(m);// TODO
			//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // if
				//
			os = toArray(collection);
			//
			toString = Objects.toString(m);
			//
			returnType = Util.getReturnType(m);
			//
			if (Util.isStatic(m)) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (Boolean.logicalAnd(Objects.equals(Util.getName(m), "IsWindows10OrGreater"),
						m.getParameterCount() == 0)) {
					//
					if (Objects.equals(OperatingSystem.WINDOWS, OperatingSystemUtil.getOperatingSystem())) {
						//
						Assertions.assertNotNull(invoke, toString);
						//
					} else {
						//
						Assertions.assertNull(invoke, toString);
						//
					} // if
						//
					continue;
				} // if
					//
				if (Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE), returnType)) {
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
				invoke = Narcissus.invokeMethod(instance = ObjectUtils.getIfNull(instance, SpeechApiImpl::new), m, os);
				//
				if (Objects.equals(returnType, Boolean.TYPE) || Boolean
						.logicalAnd(Objects.equals(Util.getName(m), "getInstance"), m.getParameterCount() == 0)) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
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
	@Disabled // TODO
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> new SpeechApiImpl().afterPropertiesSet());
		//
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
	void testIH() {
		//
		final InvocationHandler ih = Util.cast(InvocationHandler.class,
				Narcissus.allocateInstance(Util.forName("org.springframework.context.support.SpeechApiImpl$IH")));
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

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
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