package org.springframework.context.support;

import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.JButton;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;

import com.google.common.reflect.Reflection;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Request;

import io.github.toolfactory.narcissus.Narcissus;

class YukumoJapaneseTtsGuiTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_APPLY_AND_ACCEPT = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = YukumoJapaneseTtsGui.class;
		//
		(METHOD_TEST_AND_APPLY = Util.getDeclaredMethod(clz, "testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_APPLY_AND_ACCEPT = Util.getDeclaredMethod(clz, "applyAndAccept", FailableFunction.class, Object.class,
				Consumer.class)).setAccessible(true);
		//

	}

	private static class IH implements InvocationHandler {

		private Boolean test = null;

		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Util.getReturnType(method), Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String name = Util.getName(method);
			//
			if (proxy instanceof Predicate && Objects.equals(name, "test")) {
				//
				return test;
				//
			} else if (proxy instanceof FailableFunction && Objects.equals(name, "apply")) {
				//
				return null;
				//
			} else if (proxy instanceof Page && Util.contains(Arrays.asList("navigate", "querySelectorAll"), name)) {
				//
				return null;
				//
			} else if (proxy instanceof Request && Util.contains(Arrays.asList("url", "resourceType"), name)) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private static <T, R, E extends Exception> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Process start(final ProcessBuilder instance) throws IOException {
		return instance != null ? instance.start() : null;
	}

	private static InputStream getInputStream(final Process instance) {
		return instance != null ? instance.getInputStream() : null;
	}

	private YukumoJapaneseTtsGui instance = null;

	private IH ih = null;

	private OperatingSystem operatingSystem = null;

	private boolean nmcliExists = false;

	private String connectivity = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		instance = Util.cast(YukumoJapaneseTtsGui.class, Narcissus.allocateInstance(YukumoJapaneseTtsGui.class));
		//
		ih = new IH();
		//
		if (Objects.equals(operatingSystem = OperatingSystemUtil.getOperatingSystem(), OperatingSystem.LINUX)) {
			//
			final Charset charset = StandardCharsets.UTF_8;
			//
			try (final InputStream is = getInputStream(start(new ProcessBuilder(new String[] { "which", "nmcli" })))) {
				//
				nmcliExists = Util.exists(testAndApply(Objects::nonNull,
						StringUtils.trim(IOUtils.toString(is, charset)), File::new, null));
				//
			} // try
				//
			try (final InputStream is = getInputStream(
					start(nmcliExists ? new ProcessBuilder(new String[] { "nmcli", "-mode", "multiline", "general" })
							: null))) {
				//
				final Collection<String> collection = testAndApply(Objects::nonNull, is,
						x -> IOUtils.readLines(x, charset), null);
				//
				connectivity = Util
						.toString(testAndApply(
								x -> IterableUtils
										.size(x) == 1,
								Util.toList(Util.map(
										Util.filter(Util.stream(collection),
												x -> StringsUtil.startsWith(Strings.CI, x, "CONNECTIVITY:")),
										x -> StringUtils.trim(StringUtils.substringAfter(x, ':')))),
								x -> IterableUtils.get(x, 0), null));
				//
			} // try
				//
		} // if
			//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Method[] ms = Util.getDeclaredMethods(YukumoJapaneseTtsGui.class);
		//
		Method m = null;
		//
		Object[] os = null;
		//
		String name, toString = null;
		//
		Object result = null;
		//
		Class<?>[] parameterTypes = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()
					|| (parameterTypes = m.getParameterTypes()) == null) {
				//
				continue;
				//
			} // if
				//
			os = Util.toArray(Collections.nCopies(parameterTypes.length, null));
			//
			name = Util.getName(m);
			//
			toString = Util.toString(m);
			//
			if (Util.isStatic(m)) {
				//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (Objects.equals(Util.getReturnType(m), Boolean.TYPE)
						|| Boolean.logicalAnd(Objects.equals(name, "getPath"), Arrays.equals(parameterTypes,
								new Class<?>[] { String.class, String.class, String[].class, String.class }))) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} else {
				//
				if (Objects.equals(Util.getName(m), "actionPerformed")
						&& Arrays.equals(parameterTypes, new Class<?>[] { ActionEvent.class })
						&& Objects.equals(operatingSystem, OperatingSystem.LINUX)
						&& !StringsUtil.equals(Strings.CI, connectivity, "full") && nmcliExists) {
					//
					final Method m1 = m;
					//
					final Object[] os1 = os;
					//
					Assertions.assertThrows(RuntimeException.class, () -> Narcissus.invokeMethod(instance, m1, os1));
					//
					continue;
					//
				} // if
					//
				Assertions.assertNull(Narcissus.invokeMethod(instance, m, os), toString);
				//
			} // if
				//
		} // for
			//
	}

	@Test
	void testNonNull() throws Throwable {
		//
		final Method[] ms = Util.getDeclaredMethods(YukumoJapaneseTtsGui.class);
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		if (ih != null) {
			//
			ih.test = Boolean.FALSE;
			//
		} // if
			//
		Object[] os = null;
		//
		String toString = null;
		//
		Object name, result = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()
					|| (parameterTypes = m.getParameterTypes()) == null) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			for (int j = 0; j < parameterTypes.length; j++) {
				//
				if (isInterface(parameterType = ArrayUtils.get(parameterTypes, j))) {
					//
					Util.add(collection, Reflection.newProxy(parameterType, ih));
					//
				} else if (isArray(parameterType) && parameterType != null) {
					//
					Util.add(collection, Array.newInstance(parameterType.getComponentType(), 0));
					//
				} else if (Objects.equals(parameterType, InputStream.class)) {
					//
					Util.add(collection, new ByteArrayInputStream(new byte[] {}));
					//
				} else {
					//
					Util.add(collection, Narcissus.allocateInstance(parameterType));
					//
				} // if
					//
			} // for
				//
			os = Util.toArray(collection);
			//
			toString = Objects.toString(m);
			//
			name = Util.getName(m);
			//
			if (Util.isStatic(m)) {
				//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (Objects.equals(Util.getReturnType(m), Boolean.TYPE)
						|| Boolean.logicalAnd(Objects.equals(name, "readAllBytes"),
								Arrays.equals(parameterTypes, new Class<?>[] { InputStream.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "getPath"), Arrays.equals(parameterTypes,
								new Class<?>[] { String.class, String.class, String[].class, String.class }))) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} else {
				//
				if (Objects.equals(Util.getName(m), "actionPerformed")
						&& Arrays.equals(parameterTypes, new Class<?>[] { ActionEvent.class })
						&& Objects.equals(operatingSystem, OperatingSystem.LINUX)
						&& !StringsUtil.equals(Strings.CI, connectivity, "full") && nmcliExists) {
					//
					final Method m1 = m;
					//
					final Object[] os1 = os;
					//
					Assertions.assertThrows(RuntimeException.class, () -> Narcissus.invokeMethod(instance, m1, os1));
					//
					continue;
					//
				} // if
					//
				Assertions.assertNull(Narcissus.invokeMethod(instance, m, os), toString);
				//
			} // if
				//
		} // for
			//
	}

	private static boolean isInterface(final Class<?> instance) {
		return instance != null && instance.isInterface();
	}

	private static boolean isArray(final Class<?> instance) {
		return instance != null && instance.isArray();
	}

	@Test
	void testActionPerformed() throws IllegalAccessException {
		//
		if (instance == null || (Objects.equals(operatingSystem, OperatingSystem.LINUX)
				&& Boolean.logicalAnd(!StringsUtil.equals(Strings.CI, connectivity, "full"), nmcliExists))) {
			//
			return;
			//
		} // if
			//
			// btnDownload
			//
		final AbstractButton btnDownload = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnDownload", btnDownload, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnDownload, 0, null)));
		//
	}

	@Test
	void testApplyAndAccept() throws Exception {
		//
		final FailableFunction<?, ?, ?> failableFunction = x -> {
			//
			throw new RuntimeException();
			//
		};
		//
		Assertions.assertNull(
				METHOD_APPLY_AND_ACCEPT != null ? METHOD_APPLY_AND_ACCEPT.invoke(null, failableFunction, null, null)
						: null);
		//
	}

}