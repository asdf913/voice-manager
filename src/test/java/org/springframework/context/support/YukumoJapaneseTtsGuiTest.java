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
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableConsumer;
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

	private static Method METHOD_TEST_AND_APPLY, METHOD_APPLY_AND_ACCEPT, METHOD_ACCEPT_AND_ACCEPT, METHOD_IIF,
			METHOD_TEST_AND_ACCEPT, METHOD_TEST_AND_GET, METHOD_GET_ITEM_AT = null;

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
		(METHOD_ACCEPT_AND_ACCEPT = Util.getDeclaredMethod(clz, "acceptAndAccept", FailableConsumer.class, Object.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_IIF = Util.getDeclaredMethod(clz, "iif", Boolean.TYPE, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = Util.getDeclaredMethod(clz, "testAndAccept", BiPredicate.class, Object.class,
				Object.class, BiConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_GET = Util.getDeclaredMethod(clz, "testAndGet", Boolean.TYPE, Supplier.class, Supplier.class))
				.setAccessible(true);
		//
		(METHOD_GET_ITEM_AT = Util.getDeclaredMethod(clz, "getItemAt", JComboBox.class, Integer.TYPE))
				.setAccessible(true);
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
			if (Boolean.logicalOr(proxy instanceof Predicate, proxy instanceof BiPredicate)
					&& Objects.equals(name, "test")) {
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
			} else if (proxy instanceof Supplier && Objects.equals(name, "get")) {
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
			return (R) invoke(METHOD_TEST_AND_APPLY, null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
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
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
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
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.TRUE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // for
				//
			os = Util.toArray(collection);
			//
			name = Util.getName(m);
			//
			toString = Util.toString(m);
			//
			if (Util.isStatic(m)) {
				//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE), Util.getReturnType(m))
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
					|| (parameterTypes = m.getParameterTypes()) == null
					|| Util.and(StringsUtil.equals(Strings.CI, "true", System.getenv("GITHUB_ACTIONS")),
							Objects.equals(name = Util.getName(m), "play"),
							Arrays.equals(parameterTypes, new Class<?>[] { byte[].class }))) {
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
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.TRUE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
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
			if (Util.isStatic(m)) {
				//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE), Util.getReturnType(m))
						|| Boolean.logicalAnd(Objects.equals(name, "readAllBytes"),
								Arrays.equals(parameterTypes, new Class<?>[] { InputStream.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "getPath"),
								Arrays.equals(parameterTypes,
										new Class<?>[] { String.class, String.class, String[].class, String.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "iif"), Arrays.equals(parameterTypes,
								new Class<?>[] { Boolean.TYPE, Object.class, Object.class }))) {
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
	void testApplyAndAccept() throws IllegalAccessException, InvocationTargetException {
		//
		final FailableFunction<?, ?, ?> failableFunction = x -> {
			//
			throw new RuntimeException();
			//
		};
		//
		Assertions.assertNull(invoke(METHOD_APPLY_AND_ACCEPT, null, failableFunction, null, null));
		//
	}

	@Test
	void testAcceptAndAccept() throws IllegalAccessException, InvocationTargetException {
		//
		final FailableConsumer<?, ?> failableConsumer = x -> {
			//
			throw new RuntimeException();
			//
		};
		//
		Assertions.assertNull(invoke(METHOD_ACCEPT_AND_ACCEPT, null, failableConsumer, null, null));
		//
	}

	@Test
	void testIif() throws IllegalAccessException, InvocationTargetException {
		//
		final Object object = new Object();
		//
		Assertions.assertSame(object, invoke(METHOD_IIF, null, Boolean.FALSE, null, object));
		//
	}

	@Test
	void testTestAndAccept() throws IllegalAccessException, InvocationTargetException {
		//
		if (ih != null) {
			//
			ih.test = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertNull(
				invoke(METHOD_TEST_AND_ACCEPT, null, Reflection.newProxy(BiPredicate.class, ih), null, null, null));
		//
	}

	@Test
	void testTestAndGet() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_GET, null, Boolean.TRUE, null, null));
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_GET, null, Boolean.FALSE, null, null));
		//
	}

	@Test
	void testGetItemAt() throws IllegalAccessException, InvocationTargetException {
		//
		final Object object = new Object();
		//
		Assertions.assertSame(object, invoke(METHOD_GET_ITEM_AT, null, new JComboBox<>(new Object[] { object }), 0));
		//
	}

}