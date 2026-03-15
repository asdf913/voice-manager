package org.springframework.context.support;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.FailablePredicate;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Value;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.google.common.reflect.Reflection;
import com.microsoft.playwright.Page;

import io.github.toolfactory.narcissus.Narcissus;

class WiktionaryGuiTest {

	private static Method METHOD_GET_WIKTIONARY_ENTRIES1, METHOD_GET_WIKTIONARY_ENTRIES3, METHOD_SET_ROW_HEIGHT = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = WiktionaryGui.class;
		//
		(METHOD_GET_WIKTIONARY_ENTRIES1 = Util.getDeclaredMethod(clz, "getWiktionaryEntries", String.class))
				.setAccessible(true);
		//
		(METHOD_GET_WIKTIONARY_ENTRIES3 = Util.getDeclaredMethod(clz, "getWiktionaryEntries",
				Util.forName("org.springframework.context.support.WiktionaryGui$WiktionaryEntry"), ObjectMapper.class,
				Iterable.class)).setAccessible(true);
		//
		(METHOD_SET_ROW_HEIGHT = Util.getDeclaredMethod(clz, "setRowHeight", JTable.class, Integer.TYPE))
				.setAccessible(true);
		//
	}

	private WiktionaryGui instance = null;

	private boolean nmcliExists = false;

	private String connectivity = null;

	private OperatingSystem operatingSystem = null;

	private ObjectMapper objectMapper = null;

	@BeforeEach
	void beforeEach() throws IOException {
		//
		instance = Util.cast(WiktionaryGui.class, Narcissus.allocateInstance(WiktionaryGui.class));
		//
		final Builder builder = JsonMapper.builder();
		//
		if (builder != null) {
			//
			builder.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
			//
			builder.visibility(PropertyAccessor.ALL, Visibility.ANY);
			//
			builder.defaultPropertyInclusion(Value.ALL_NON_NULL);
			//
		} // if
			//
		objectMapper = builder != null ? builder.build() : null;
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

	private static <T, R, E extends Exception> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static Process start(final ProcessBuilder instance) throws IOException {
		return instance != null ? instance.start() : null;
	}

	private static InputStream getInputStream(final Process instance) {
		return instance != null ? instance.getInputStream() : null;
	}

	private static class IH implements InvocationHandler {

		private Boolean test = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Void.TYPE, Util.getReturnType(method))) {
				//
				return null;
				//
			} // if
				//
			final String name = Util.getName(method);
			//
			if (proxy instanceof Iterable && Objects.equals(name, "iterator")) {
				//
				return null;
				//
			} // if
				//
			if (proxy instanceof FailableFunction && Objects.equals(name, "apply")) {
				//
				return null;
				//
			} else if (proxy instanceof TableCellRenderer && Objects.equals(name, "getTableCellRendererComponent")) {
				//
				return null;
				//
			} else if (proxy instanceof TableModel && Objects.equals(name, "getValueAt")) {
				//
				return null;
				//
			} else if (Boolean.logicalAnd(Objects.equals(name, "test"),
					Boolean.logicalOr(proxy instanceof FailablePredicate, proxy instanceof BiPredicate))) {
				//
				return test;
				//
			} else if (proxy instanceof TableColumnModel && Objects.equals(name, "getColumn")) {
				//
				return null;
				//
			} else if (proxy instanceof Page && Objects.equals(name, "querySelector")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	@Test
	void testNull() {
		//
		final Method[] ms = WiktionaryGui.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType, returnType = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		Object result = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			parameterTypes = Util.getParameterTypes(m);
			//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
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
			os = toArray(collection);
			//
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (Boolean.logicalAnd(isPrimitive(returnType = Util.getReturnType(m)),
						!Objects.equals(returnType, Void.TYPE))) {
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
					Assertions
							.assertThrows(RuntimeException.class,
									() -> Narcissus.invokeMethod(
											instance = ObjectUtils.getIfNull(instance,
													() -> Util.cast(WiktionaryGui.class,
															Narcissus.allocateInstance(WiktionaryGui.class))),
											m1, os1));
					//
					continue;
					//
				} // if
					//
				Assertions
						.assertNull(
								Narcissus
										.invokeMethod(
												instance = ObjectUtils.getIfNull(instance,
														() -> Util.cast(WiktionaryGui.class,
																Narcissus.allocateInstance(WiktionaryGui.class))),
												m, os),
								toString);
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
	void testNotNull() throws IOException {
		//
		final Method[] ms = WiktionaryGui.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType, returnType = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		String toString, name = null;
		//
		Object result = null;
		//
		final IH ih = new IH();
		//
		ih.test = Boolean.TRUE;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			parameterTypes = Util.getParameterTypes(m);
			//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Class.class)) {
					//
					Util.add(collection, Object.class);
					//
				} else if (Objects.equals(parameterType, Component.class)) {
					//
					Util.add(collection, new JLabel());
					//
				} else if (Objects.equals(parameterType, URLConnection.class)) {
					//
					Util.add(collection, Util.openConnection(Util.toURL(toUri(Path.of("pom.xml")))));
					//
				} else if (isArray(parameterType)) {
					//
					Util.add(collection, Array.newInstance(getComponentType(parameterType), 0));
					//
				} else if (isInterface(parameterType)) {
					//
					Util.add(collection, Reflection.newProxy(parameterType, ih));
					//
				} else if (Objects.equals(parameterType, Image.class)) {
					//
					Util.add(collection, new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
					//
				} else {
					//
					Util.add(collection, Narcissus.allocateInstance(parameterType));
					//
				} // if
					//
			} // for
				//
			os = toArray(collection);
			//
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (or(Boolean.logicalAnd(isPrimitive(returnType = Util.getReturnType(m)),
						!Objects.equals(returnType, Void.TYPE)),
						Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "stream"),
								Arrays.equals(parameterTypes, new Class<?>[] { Element.class })),
						Boolean.logicalAnd(Objects.equals(name, "classNames"),
								Arrays.equals(parameterTypes, new Class<?>[] { Element.class })))) {
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
					Assertions
							.assertThrows(RuntimeException.class,
									() -> Narcissus.invokeMethod(
											instance = ObjectUtils.getIfNull(instance,
													() -> Util.cast(WiktionaryGui.class,
															Narcissus.allocateInstance(WiktionaryGui.class))),
											m1, os1));
					//
					continue;
					//
				} // if
					//
				Assertions
						.assertNull(
								Narcissus
										.invokeMethod(
												instance = ObjectUtils.getIfNull(instance,
														() -> Util.cast(WiktionaryGui.class,
																Narcissus.allocateInstance(WiktionaryGui.class))),
												m, os),
								toString);
				//
			} // if
				//
		} // for
			//
	}

	private static URI toUri(final Path instance) {
		return instance != null ? instance.toUri() : null;
	}

	private static boolean or(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = Boolean.logicalOr(a, b);
		//
		if (result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (result |= bs[i]) {
				//
				return result;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	private static boolean isInterface(final Class<?> instance) {
		return instance != null && instance.isInterface();
	}

	private static Class<?> getComponentType(final Class<?> instance) {
		return instance != null ? instance.getComponentType() : null;
	}

	private static boolean isArray(final Class<?> instance) {
		return instance != null && instance.isArray();
	}

	@Test
	void testActionPerformed() {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent("", 0, null)));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Test
	void testGetWiktionaryEntries() throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
		//
		Assertions.assertEquals("[{\"ipa\":\"[it͡ɕi]\",\"language\":\"Japanese\"}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_GET_WIKTIONARY_ENTRIES1, null,
						"<html><body><div class=\"mw-heading mw-heading2\"><h2>Japanese</h2></div><div class=\"mw-heading mw-heading4\"><h4>Pronunciation</h4></div><ul><li><span class=\"usage-label-accent\"></span></li><li>IPA(key): <span class=\"IPA nowrap\">[it͡ɕi]</span></li></ul></body></html>")));
		//
		Assertions.assertEquals(
				"[{\"hiragana\":\"いち\",\"hiraganaCssSelector\":\"html > body > ul > li:nth-child(1) > span.Jpan\",\"ipa\":\"[it͡ɕi]\",\"language\":\"Japanese\",\"pitchAccent\":\"[ìchíꜜ]\",\"pitchAccentPattern\":\"尾高型\"}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_GET_WIKTIONARY_ENTRIES1, null,
						"<html><body><div class=\"mw-heading mw-heading2\"><h2>Japanese</h2></div><div class=\"mw-heading mw-heading4\"><h4>Pronunciation</h4></div><ul><li><span class=\"usage-label-accent\"></span><span lang=\"ja\" class=\"Jpan\">いち</span><span class=\"Latn\">[ìchíꜜ]</span><a title=\"尾高型\"></a></li><li>IPA(key): <span class=\"IPA nowrap\">[it͡ɕi]</span></li></ul></body></html>")));
		//
		Assertions.assertNull(invoke(METHOD_GET_WIKTIONARY_ENTRIES3, null, null, null, Collections.singleton(null)));
		//
		Assertions.assertNull(invoke(METHOD_GET_WIKTIONARY_ENTRIES3, null, null, null,
				Collections.singleton(Narcissus.allocateInstance(Element.class))));
		//
	}

	@Test
	void testSetRowHeight() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_ROW_HEIGHT, null, new JTable(), Integer.valueOf(1)));
		//
	}

}