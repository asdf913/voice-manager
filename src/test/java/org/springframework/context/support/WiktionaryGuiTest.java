package org.springframework.context.support;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Value;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.google.common.reflect.Reflection;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import io.github.toolfactory.narcissus.Narcissus;

class WiktionaryGuiTest {

	private static Method METHOD_GET_WIKTIONARY_ENTRIES1, METHOD_GET_WIKTIONARY_ENTRIES3, METHOD_SET_ROW_HEIGHT,
			METHOD_TEST_AND_GET, METHOD_TEST_AND_RUN, METHOD_TO_IMAGE, METHOD_TEST_AND_GET_AS_BOOLEAN,
			METHOD_SET_ROW_SELECTION_INTERVAL, METHOD_ENABLE = null;

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
		(METHOD_TEST_AND_GET = Util.getDeclaredMethod(clz, "testAndGet", Boolean.TYPE, Supplier.class, Supplier.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_RUN = Util.getDeclaredMethod(clz, "testAndRun", Boolean.TYPE, Runnable.class))
				.setAccessible(true);
		//
		(METHOD_TO_IMAGE = Util.getDeclaredMethod(clz, "toImage", byte[].class)).setAccessible(true);
		//
		(METHOD_TEST_AND_GET_AS_BOOLEAN = Util.getDeclaredMethod(clz, "testAndGetAsBoolean", Boolean.TYPE,
				BooleanSupplier.class)).setAccessible(true);
		//
		(METHOD_SET_ROW_SELECTION_INTERVAL = Util.getDeclaredMethod(clz, "setRowSelectionInterval", JTable.class,
				Integer.TYPE, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_ENABLE = Util.getDeclaredMethod(clz, "enable", ObjectMapper.class, SerializationFeature.class))
				.setAccessible(true);
		//
	}

	private WiktionaryGui instance = null;

	private ObjectMapper objectMapper = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
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
		ih = new IH();
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean test, equals, getAsBoolean = null;

		private int[] selectedIndices = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Void.TYPE, Util.getReturnType(method))) {
				//
				return null;
				//
			} // if
				//
			if (ReflectionUtils.isEqualsMethod(method)) {
				//
				return equals;
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
			} else if (proxy instanceof ElementHandle && Objects.equals(name, "querySelector")) {
				//
				return null;
				//
			} else if (proxy instanceof ListSelectionModel && Objects.equals(name, "getSelectedIndices")) {
				//
				return selectedIndices;
				//
			} else if (proxy instanceof Supplier && Objects.equals(name, "get")) {
				//
				return null;
				//
			} else if (proxy instanceof BooleanSupplier && Objects.equals(name, "getAsBoolean")) {
				//
				return getAsBoolean;
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
		if (ih != null) {
			//
			ih.getAsBoolean = Boolean.FALSE;
			//
		} // if
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
		if ((ih = ObjectUtils.getIfNull(ih, IH::new)) != null) {
			//
			ih.test = Boolean.TRUE;
			//
		} // if
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
				} else if (Objects.equals(parameterType, Toolkit.class)) {
					//
					Util.add(collection, Toolkit.getDefaultToolkit());
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
								Arrays.equals(parameterTypes, new Class<?>[] { Element.class })),
						Boolean.logicalAnd(!GraphicsEnvironment.isHeadless(),
								Boolean.logicalAnd(Objects.equals(name, "getSystemClipboard"),
										Arrays.equals(parameterTypes, new Class<?>[] { Toolkit.class }))),
						Boolean.logicalAnd(Objects.equals(name, "textNodes"),
								Arrays.equals(parameterTypes, new Class<?>[] { Elements.class })),
						Boolean.logicalAnd(Objects.equals(name, "enable"), Arrays.equals(parameterTypes,
								new Class<?>[] { ObjectMapper.class, SerializationFeature.class })))) {
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
	void testActionPerformed() throws IllegalAccessException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent("", 0, null)));
		//
		// btnCopy
		//
		final AbstractButton btnCopy = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopy", btnCopy, true);
		//
		final ActionEvent actionEventBtnCopy = new ActionEvent(btnCopy, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopy));
		//
		FieldUtils.writeDeclaredField(instance, "lsm", Reflection.newProxy(ListSelectionModel.class, ih), true);
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[] { -1 };
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopy));
		//
		final AbstractButton btnEnableIndentOutput = new JCheckBox();
		//
		btnEnableIndentOutput.setSelected(true);
		//
		FieldUtils.writeDeclaredField(instance, "btnEnableIndentOutput", btnEnableIndentOutput, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopy));
		//
		// btnCopyHiraganaImage
		//
		final AbstractButton btnCopyHiraganaImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyHiraganaImage", btnCopyHiraganaImage, true);
		//
		final ActionEvent actionEventBtnCopyHiraganaImage = new ActionEvent(btnCopyHiraganaImage, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopyHiraganaImage));
		//
		FieldUtils.writeDeclaredField(instance, "lsm", Reflection.newProxy(ListSelectionModel.class, ih), true);
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[] { -1 };
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopyHiraganaImage));
		//
		// btnSaveHiraganaImage
		//
		final AbstractButton btnSaveHiraganaImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSaveHiraganaImage", btnSaveHiraganaImage, true);
		//
		final ActionEvent actionEventBtnSaveHiraganaImage = new ActionEvent(btnSaveHiraganaImage, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSaveHiraganaImage));
		//
		FieldUtils.writeDeclaredField(instance, "lsm", Reflection.newProxy(ListSelectionModel.class, ih), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSaveHiraganaImage));
		//
		// btnCopyTextImage
		//
		final AbstractButton btnCopyTextImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyTextImage", btnCopyTextImage, true);
		//
		final ActionEvent actionEventBtnCopyTextImage = new ActionEvent(btnCopyTextImage, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopyTextImage));
		//
		FieldUtils.writeDeclaredField(instance, "lsm", Reflection.newProxy(ListSelectionModel.class, ih), true);
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[] { -1 };
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopyTextImage));
		//
		// btnSaveTextImage
		//
		final AbstractButton btnSaveTextImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSaveTextImage", btnSaveTextImage, true);
		//
		final ActionEvent actionEventBtnSaveTextImage = new ActionEvent(btnSaveTextImage, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSaveTextImage));
		//
		FieldUtils.writeDeclaredField(instance, "lsm", Reflection.newProxy(ListSelectionModel.class, ih), true);
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[] { -1 };
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSaveTextImage));
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

	@Test
	void testTestAndGet() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_GET, null, Boolean.TRUE, null, null));
		//
	}

	@Test
	void testTestAndRun() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_RUN, null, Boolean.TRUE, null));
		//
	}

	@Test
	void testToImage() throws IllegalAccessException, InvocationTargetException, IOException {
		//
		byte[] bs = null;
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			ImageIO.write(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), "png", baos);
			//
			bs = baos.toByteArray();
			//
		} // try
			//
		Assertions.assertNotNull(invoke(METHOD_TO_IMAGE, null, bs));
		//
	}

	@Test
	void testWiktionaryEntry() {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.WiktionaryGui$WiktionaryEntry");
		//
		final Method[] ms = Util.getDeclaredMethods(clz);
		//
		final Object wiktionaryEntry = Narcissus.allocateInstance(clz);
		//
		Method m = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			os = toArray(Collections.nCopies(m.getParameterCount(), null));
			//
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(wiktionaryEntry, m, os), toString);
				//
			} // if
				//
		} // for
			//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = Util.getParameterTypes(m);
			//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(ArrayUtils.get(parameterTypes, j), clz)) {
					//
					Util.add(collection, wiktionaryEntry);
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
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(wiktionaryEntry, m, os), toString);
				//
			} // if
				//
		} // for
			//
	}

	@Test
	void testIH() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.WiktionaryGui$IH");
		//
		final InvocationHandler invocationHandler = Util.cast(InvocationHandler.class, Narcissus.allocateInstance(clz));
		//
		if (invocationHandler != null) {
			//
			Assertions.assertThrows(Throwable.class, () -> invocationHandler.invoke(null, null, null));
			//
			final Transferable transferable = Reflection.newProxy(Transferable.class, invocationHandler);
			//
			Assertions.assertThrows(Throwable.class, () -> invocationHandler.invoke(transferable, null, null));
			//
			Assertions.assertNull(invocationHandler.invoke(transferable,
					Util.getDeclaredMethod(Transferable.class, "getTransferData", DataFlavor.class), null));
			//
			Assertions.assertNotNull(invocationHandler.invoke(transferable,
					Util.getDeclaredMethod(Transferable.class, "getTransferDataFlavors"), null));
			//
		} // if
			//
	}

	@Test
	void testValueChanged() throws Throwable {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(new ListSelectionEvent("", 0, 0, false)));
		//
		final ListSelectionModel lsm = Reflection.newProxy(ListSelectionModel.class, ih);
		//
		if (ih != null) {
			//
			ih.equals = Boolean.FALSE;
			//
			ih.selectedIndices = new int[1];
			//
		} // if
			//
		FieldUtils.writeDeclaredField(instance, "lsm", lsm, true);
		//
		final ListSelectionEvent lse = new ListSelectionEvent(lsm, 0, 0, false);
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(lse));
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[2];
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> instance.valueChanged(lse));
		//
	}

	@Test
	void testTestAndGetAsBoolean() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(Boolean.FALSE, invoke(METHOD_TEST_AND_GET_AS_BOOLEAN, null, Boolean.TRUE, null));
		//
		final BooleanSupplier booleanSupplier = Reflection.newProxy(BooleanSupplier.class, ih);
		//
		if (ih != null) {
			//
			ih.getAsBoolean = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertEquals(ih != null ? ih.getAsBoolean : null,
				invoke(METHOD_TEST_AND_GET_AS_BOOLEAN, null, Boolean.TRUE, booleanSupplier));
		//
		if (ih != null) {
			//
			ih.getAsBoolean = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertEquals(ih != null ? ih.getAsBoolean : null,
				invoke(METHOD_TEST_AND_GET_AS_BOOLEAN, null, Boolean.TRUE, booleanSupplier));
		//
	}

	@Test
	void testSetRowSelectionInterval() throws IllegalAccessException, InvocationTargetException {
		//
		final Integer zero = Integer.valueOf(0);
		//
		Assertions.assertNull(invoke(METHOD_SET_ROW_SELECTION_INTERVAL, null, new JTable(), zero, zero));
		//
		Assertions.assertNull(
				invoke(METHOD_SET_ROW_SELECTION_INTERVAL, null, new JTable(new DefaultTableModel(1, 1)), zero, zero));
		//
	}

	@Test
	void testEnable() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertSame(objectMapper, invoke(METHOD_ENABLE, null, objectMapper, null));
		//
	}

}