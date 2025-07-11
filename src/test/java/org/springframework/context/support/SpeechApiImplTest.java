package org.springframework.context.support;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class SpeechApiImplTest {

	private static Method METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		if ((METHOD_TEST_AND_APPLY = Util.getDeclaredMethod(SpeechApiImpl.class, "testAndApply", Predicate.class,
				Object.class, Function.class, Function.class)) != null) {
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
									new Class<?>[] { String.class, String.class, Integer.TYPE, Integer.TYPE,
											Map.class }))
					|| Boolean.logicalAnd(Objects.equals(Util.getName(m), "writeVoiceToFile"),
							Arrays.equals(Util.getParameterTypes(m),
									new Class<?>[] { String.class, String.class, Integer.TYPE, Integer.TYPE, Map.class,
											File.class }))
					|| Boolean.logicalAnd(m.getParameterCount() == 0, Util.contains(Arrays.asList("getProviderName",
							"getVoiceIds", "getProviderPlatform", "getProviderVersion"), Util.getName(m)))) {
				//
				continue;
				//
			} // if
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
			os = Util.toArray(collection);
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
				if (Boolean.logicalAnd(Objects.equals(Util.getName(m), "isInstalled"), m.getParameterCount() == 0)
						&& !Objects.equals(OperatingSystem.WINDOWS, OperatingSystemUtil.getOperatingSystem())) {
					//
					continue;
					//
				} // if
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
			final Executable executable = () -> ih.invoke(speechApi,
					Util.getDeclaredMethod(SpeechApi.class, "isInstalled"), null);
			//
			if (Util.containsKey(System.getProperties(), "org.springframework.context.support.SpeechApi.isInstalled")) {
				//
				Assertions.assertDoesNotThrow(executable);
				//
			} else {
				//
				Assertions.assertThrows(Throwable.class, executable);
				//
			} // if
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