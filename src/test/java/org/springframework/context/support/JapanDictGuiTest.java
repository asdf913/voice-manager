package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.IntFunction;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableLongConsumer;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.BoundingBox;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class JapanDictGuiTest {

	private static final int ZERO = 0;

	private static final int ONE = 1;

	private static Class<?> CLASS_JAPAN_DICT_ENTRY = null;

	private static Method METHOD_TEST_AND_GET, METHOD_SET_TEXT, METHOD_STARTS_WITH, METHOD_APPEND,
			METHOD_TEST_AND_ACCEPT3_OBJECT, METHOD_TEST_AND_ACCEPT3_LONG, METHOD_TEST_AND_ACCEPT4, METHOD_GET_AUDIO_URL,
			METHOD_TEST_AND_RUN, METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_SET_ENABLED, METHOD_TEST_AND_APPLY,
			METHOD_TO_ARRAY, METHOD_GET_JLPT_LEVEL_INDICES, METHOD_GET_JLPT_LEVEL, METHOD_SET_JCB_JLPT_LEVEL,
			METHOD_CHOP_IMAGE1, METHOD_CHOP_IMAGE2, METHOD_TO_DURATION, METHOD_TO_BUFFERED_IMAGE,
			METHOD_GET_COLUMN_NAME, METHOD_GET_TABLE_CELL_RENDERER_COMPONENT, METHOD_GET_STROKE_IMAGE, METHOD_AND,
			METHOD_PREPARE_RENDERER, METHOD_GET_CELL_RENDERER, METHOD_GET_COLUMN_COUNT,
			METHOD_SET_ROW_SELECTION_INTERVAL, METHOD_GET_PITCH_ACCENT_IMAGE, METHOD_CREATE_TABLE_CELL_RENDERER,
			METHOD_OR, METHOD_CREATE_PITCH_ACCENT_LIST_CELL_RENDERER, METHOD_SET_PREFERRED_SIZE,
			METHOD_GET_PITCH_ACCENTS = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = JapanDictGui.class;
		//
		(METHOD_TEST_AND_GET = Util.getDeclaredMethod(clz, "testAndGet", Boolean.TYPE, FailableSupplier.class))
				.setAccessible(true);
		//
		(METHOD_SET_TEXT = Util.getDeclaredMethod(clz, "setText", String.class, JTextComponent[].class))
				.setAccessible(true);
		//
		(METHOD_STARTS_WITH = Util.getDeclaredMethod(clz, "startsWith", Strings.class, String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_APPEND = Util.getDeclaredMethod(clz, "append", StringBuilder.class, Character.TYPE))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3_OBJECT = Util.getDeclaredMethod(clz, "testAndAccept", Predicate.class, Object.class,
				FailableConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3_LONG = Util.getDeclaredMethod(clz, "testAndAccept", LongPredicate.class, Long.TYPE,
				FailableLongConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = Util.getDeclaredMethod(clz, "testAndAccept", BiPredicate.class, Object.class,
				Object.class, BiConsumer.class)).setAccessible(true);
		//
		(METHOD_GET_AUDIO_URL = Util.getDeclaredMethod(clz, "getAudioUrl", String.class, Strings.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_RUN = Util.getDeclaredMethod(clz, "testAndRun", Boolean.TYPE, Runnable.class))
				.setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = Util.getDeclaredMethod(clz, "getSystemClipboard", Toolkit.class))
				.setAccessible(true);
		//
		(METHOD_SET_ENABLED = Util.getDeclaredMethod(clz, "setEnabled", Boolean.TYPE, Component.class, Component.class,
				Component[].class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = Util.getDeclaredMethod(clz, "testAndApply", BiPredicate.class, Object.class,
				Object.class, BiFunction.class, BiFunction.class)).setAccessible(true);
		//
		(METHOD_TO_ARRAY = Util.getDeclaredMethod(clz, "toArray", Stream.class, IntFunction.class)).setAccessible(true);
		//
		(METHOD_GET_JLPT_LEVEL_INDICES = Util.getDeclaredMethod(clz, "getJlptLevelIndices", ComboBoxModel.class,
				String.class)).setAccessible(true);
		//
		(METHOD_GET_JLPT_LEVEL = Util.getDeclaredMethod(clz, "getJlptLevel", ComboBoxModel.class, String.class))
				.setAccessible(true);
		//
		(METHOD_SET_JCB_JLPT_LEVEL = Util.getDeclaredMethod(clz, "setJcbJlptLevel", int[].class)).setAccessible(true);
		//
		(METHOD_CHOP_IMAGE1 = Util.getDeclaredMethod(clz, "chopImage", BufferedImage.class)).setAccessible(true);
		//
		(METHOD_CHOP_IMAGE2 = Util.getDeclaredMethod(clz, "chopImage", byte[].class, BoundingBox.class))
				.setAccessible(true);
		//
		(METHOD_TO_DURATION = Util.getDeclaredMethod(clz, "toDuration", Object.class)).setAccessible(true);
		//
		(METHOD_TO_BUFFERED_IMAGE = Util.getDeclaredMethod(clz, "toBufferedImage", byte[].class)).setAccessible(true);
		//
		(METHOD_GET_COLUMN_NAME = Util.getDeclaredMethod(clz, "getColumnName", JTable.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_TABLE_CELL_RENDERER_COMPONENT = Util.getDeclaredMethod(clz, "getTableCellRendererComponent",
				TableCellRenderer.class, JTable.class, Object.class, Boolean.TYPE, Boolean.TYPE, Integer.TYPE,
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_STROKE_IMAGE = Util.getDeclaredMethod(clz, "getStrokeImage", clz, Page.class, String.class))
				.setAccessible(true);
		//
		(METHOD_AND = Util.getDeclaredMethod(clz, "and", Object.class, Predicate.class, Predicate.class))
				.setAccessible(true);
		//
		(METHOD_PREPARE_RENDERER = Util.getDeclaredMethod(clz, "prepareRenderer", JTable.class, TableCellRenderer.class,
				Integer.TYPE, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_CELL_RENDERER = Util.getDeclaredMethod(clz, "getCellRenderer", JTable.class, Integer.TYPE,
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_COLUMN_COUNT = Util.getDeclaredMethod(clz, "getColumnCount", JTable.class)).setAccessible(true);
		//
		(METHOD_SET_ROW_SELECTION_INTERVAL = Util.getDeclaredMethod(clz, "setRowSelectionInterval", JTable.class,
				Integer.TYPE, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_PITCH_ACCENT_IMAGE = Util.getDeclaredMethod(clz, "getPitchAccentImage",
				CLASS_JAPAN_DICT_ENTRY = Util
						.forName("org.springframework.context.support.JapanDictGui$JapanDictEntry"),
				Page.class)).setAccessible(true);
		//
		(METHOD_CREATE_TABLE_CELL_RENDERER = Util.getDeclaredMethod(clz, "createTableCellRenderer",
				TableCellRenderer.class)).setAccessible(true);
		//
		(METHOD_OR = Util.getDeclaredMethod(clz, "or", Boolean.TYPE, Boolean.TYPE, boolean[].class))
				.setAccessible(true);
		//
		(METHOD_CREATE_PITCH_ACCENT_LIST_CELL_RENDERER = Util.getDeclaredMethod(clz,
				"createPitchAccentListCellRenderer", Component.class, ListCellRenderer.class, Dimension.class))
				.setAccessible(true);
		//
		(METHOD_SET_PREFERRED_SIZE = Util.getDeclaredMethod(clz, "setPreferredSize", Component.class, Dimension.class))
				.setAccessible(true);
		//
		(METHOD_GET_PITCH_ACCENTS = Util.getDeclaredMethod(clz, "getPitchAccents", Iterable.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean test;

		private Integer size, length, columnCount;

		private int[] selectedIndices;

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
			if (proxy instanceof Predicate) {
				//
				if (Objects.equals(name, "test")) {
					//
					return test;
					//
				} // if
					//
			} else if (proxy instanceof BiPredicate) {
				//
				if (Objects.equals(name, "test")) {
					//
					return test;
					//
				} // if
					//
			} else if (proxy instanceof FailableFunction && Objects.equals(name, "apply")) {
				//
				return null;
				//
			} else if (proxy instanceof BiFunction) {
				//
				if (Objects.equals(name, "apply")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Iterable) {
				//
				if (Util.anyMatch(Stream.of("iterator", "spliterator"), x -> Objects.equals(name, x))) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Stream && Objects.equals(name, "toArray")) {
				//
				return null;
				//
			} else if (proxy instanceof ListModel && Objects.equals(name, "getSize")) {
				//
				return size;
				//
			} else if (proxy instanceof Page) {
				//
				if (Util.anyMatch(Stream.of("evaluate", "locator", "querySelectorAll"), x -> Objects.equals(name, x))) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Browser && Objects.equals(name, "newPage")) {
				//
				return null;
				//
			} else if (proxy instanceof Playwright) {
				//
				if (Objects.equals(name, "chromium")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Locator
					&& Util.anyMatch(Stream.of("screenshot", "boundingBox"), x -> Objects.equals(name, x))) {
				//
				return null;
				//
			} else if (proxy instanceof CharSequence && Objects.equals(name, "length")) {
				//
				return length;
				//
			} else if (proxy instanceof LongPredicate && Objects.equals(name, "test")) {
				//
				return test;
				//
			} else if (proxy instanceof TableModel) {
				//
				if (Objects.equals(name, "getValueAt")) {
					//
					return null;
					//
				} else if (Objects.equals(name, "getColumnCount")) {
					//
					return columnCount;
					//
				} // if
					//
				return null;
				//
			} else if (proxy instanceof TableCellRenderer && Objects.equals(name, "getTableCellRendererComponent")) {
				//
				return null;
				//
			} else if (proxy instanceof ElementHandle
					&& Util.anyMatch(Stream.of("boundingBox", "querySelectorAll", "screenshot", "getAttribute"),
							x -> Objects.equals(name, x))) {
				//
				return null;
				//
			} else if (proxy instanceof ListSelectionModel && Objects.equals(name, "getSelectedIndices")) {
				//
				return selectedIndices;
				//
			} else if (proxy instanceof Map && Objects.equals(name, "get")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private static class MH implements MethodHandler {

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String name = Util.getName(thisMethod);
			//
			if (self instanceof Toolkit && Objects.equals(name, "getSystemClipboard")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private JapanDictGui instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = Util.cast(JapanDictGui.class, Narcissus.allocateInstance(JapanDictGui.class));
		//
		ih = new IH();
		//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Method[] ms = JapanDictGui.class.getDeclaredMethods();
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
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()
					|| Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "or"),
							Arrays.equals(parameterTypes = Util.getParameterTypes(m),
									new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class }))) {
				//
				continue;
				//
			} // if
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
				} else if (Objects.equals(parameterType, Long.TYPE)) {
					//
					Util.add(collection, Long.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Character.TYPE)) {
					//
					Util.add(collection, Character.valueOf(' '));
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
				if (or(Boolean.logicalAnd(isPrimitive(returnType = Util.getReturnType(m)),
						!Objects.equals(returnType, Void.TYPE)),
						Boolean.logicalAnd(Objects.equals(name, "getJapanDictEntry"),
								Arrays.equals(parameterTypes,
										new Class<?>[] { Element.class, Pattern.class, Pattern.class,
												ObjectMapper.class, Integer.TYPE, Integer.TYPE, Map.class })),
						Boolean.logicalAnd(Objects.equals(name, "createTableCellRenderer"),
								Arrays.equals(parameterTypes, new Class<?>[] { TableCellRenderer.class })),
						Boolean.logicalAnd(Objects.equals(name, "createPitchAccentListCellRenderer"), Arrays.equals(
								parameterTypes,
								new Class<?>[] { Component.class, ListCellRenderer.class, Dimension.class })))) {
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
				result = Narcissus.invokeMethod(
						instance = ObjectUtils.getIfNull(instance,
								() -> Util.cast(JapanDictGui.class, Narcissus.allocateInstance(JapanDictGui.class))),
						m, os);
				//
				if (Boolean.logicalAnd(Objects.equals(Util.getName(m), "getUserAgent"), m.getParameterCount() == 0)) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
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
	void testNonNull() throws Throwable {
		//
		final Method[] ms = JapanDictGui.class.getDeclaredMethods();
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
		String name, toString = null;
		//
		Object result = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || or(m.isSynthetic(),
					Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "setText"),
							Arrays.equals(parameterTypes = Util.getParameterTypes(m),
									new Class<?>[] { String.class, JTextComponent[].class })),
					Boolean.logicalAnd(Objects.equals(name, "setEditable"),
							Arrays.equals(parameterTypes, new Class<?>[] { Boolean.TYPE, JTextComponent[].class })),
					Boolean.logicalAnd(Objects.equals(name, "addActionListener"),
							Arrays.equals(parameterTypes,
									new Class<?>[] { ActionListener.class, AbstractButton[].class })),
					Boolean.logicalAnd(Objects.equals(name, "setEnabled"), Arrays.equals(parameterTypes,
							new Class<?>[] { Boolean.TYPE, Component.class, Component.class, Component[].class })),
					Boolean.logicalAnd(Objects.equals(name, "getTableCellRendererComponent"),
							Arrays.equals(parameterTypes,
									new Class<?>[] { TableCellRenderer.class, JTable.class, Object.class, Boolean.TYPE,
											Boolean.TYPE, Integer.TYPE, Integer.TYPE })),
					Boolean.logicalAnd(Objects.equals(name, "getJapanDictEntry"),
							Arrays.equals(parameterTypes,
									new Class<?>[] { Element.class, Pattern.class, Pattern.class, ObjectMapper.class,
											Integer.TYPE, Integer.TYPE, Map.class })),
					Boolean.logicalAnd(Objects.equals(name, "createTableCellRenderer"),
							Arrays.equals(parameterTypes, new Class<?>[] { TableCellRenderer.class })),
					Boolean.logicalAnd(Objects.equals(name, "or"), Arrays.equals(parameterTypes,
							new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class })))) {
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
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Long.TYPE)) {
					//
					Util.add(collection, Long.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Character.TYPE)) {
					//
					Util.add(collection, Character.valueOf(' '));
					//
				} else if (isArray(parameterType)) {
					//
					Util.add(collection, Array.newInstance(componentType(parameterType), 0));
					//
				} else if (isInterface(parameterType)) {
					//
					if ((ih = ObjectUtils.getIfNull(ih, IH::new)) != null) {
						//
						ih.test = Boolean.FALSE;
						//
						ih.size = ih.length = ih.columnCount = Integer.valueOf(0);
						//
					} // if
						//
					Util.add(collection, Reflection.newProxy(parameterType, ih));
					//
				} else if (parameterType != null && !Modifier.isAbstract(parameterType.getModifiers())) {
					//
					Util.add(collection, Narcissus.allocateInstance(parameterType));
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
				if (or(Boolean.logicalAnd(isPrimitive(returnType = Util.getReturnType(m)),
						!Objects.equals(returnType, Void.TYPE)),
						Boolean.logicalAnd(Objects.equals(name, "append"),
								Arrays.equals(parameterTypes, new Class<?>[] { StringBuilder.class, Character.TYPE })),
						Boolean.logicalAnd(Objects.equals(name, "createPitchAccentListCellRenderer"), Arrays.equals(
								parameterTypes,
								new Class<?>[] { Component.class, ListCellRenderer.class, Dimension.class })))) {
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
				result = Narcissus.invokeMethod(
						instance = ObjectUtils.getIfNull(instance,
								() -> Util.cast(JapanDictGui.class, Narcissus.allocateInstance(JapanDictGui.class))),
						m, os);
				//
				if (Boolean.logicalAnd(Objects.equals(name, "getUserAgent"), m.getParameterCount() == 0)) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} // if
				//
		} // for
			//
	}

	@Test
	void testOr() throws Throwable {
		//
		Assertions.assertFalse(or(false, false, null));
		//
	}

	private static boolean or(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = invoke(METHOD_OR, null, a, b, bs);
			if (obj instanceof Boolean bool) {
				return BooleanUtils.toBooleanDefaultIfNull(bool, false);
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> componentType(final Class<?> instance) {
		return instance != null ? instance.componentType() : null;
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
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent("", 0, null)));
		//
		// btnCopyHiragana
		//
		final AbstractButton btnCopyHiragana = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyHiragana", btnCopyHiragana, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyHiragana, 0, null)));
		//
		// btnCopyKatakana
		//
		final AbstractButton btnCopyKatakana = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyKatakana", btnCopyKatakana, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyKatakana, 0, null)));
		//
		// btnCopyRomaji
		//
		final AbstractButton btnCopyRomaji = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyRomaji", btnCopyRomaji, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyRomaji, 0, null)));
		//
		// btnCopyAudioUrl
		//
		final AbstractButton btnCopyAudioUrl = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyAudioUrl", btnCopyAudioUrl, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyAudioUrl, 0, null)));
		//
		// btnCopyPitchAccentImage
		//
		final AbstractButton btnCopyPitchAccentImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyPitchAccentImage", btnCopyPitchAccentImage, true);
		//
		Assertions
				.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyPitchAccentImage, 0, null)));
		//
		// btnCopyPitchAccentImage
		//
		final AbstractButton btnSavePitchAccentImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSavePitchAccentImage", btnSavePitchAccentImage, true);
		//
		Assertions
				.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnSavePitchAccentImage, 0, null)));
		//
		// btnDownloadAudio
		//
		final AbstractButton btnDownloadAudio = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnDownloadAudio", btnDownloadAudio, true);
		//
		final ActionEvent actionEvent = new ActionEvent(btnDownloadAudio, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		final JTextComponent tfAudioUrl = new JTextField();
		//
		FieldUtils.writeDeclaredField(instance, "tfAudioUrl", tfAudioUrl, true);
		//
		Util.setText(tfAudioUrl, "");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		Util.setText(tfAudioUrl, " ");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		Util.setText(tfAudioUrl, Integer.toString(ONE));
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		// btnPlayAudio
		//
		final AbstractButton btnPlayAudio = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnPlayAudio", btnPlayAudio, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnPlayAudio, 0, null)));
		//
		// btnCopyStrokeImage
		//
		final AbstractButton btnCopyStrokeImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyStrokeImage", btnCopyStrokeImage, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyStrokeImage, 0, null)));
		//
		// btnSaveStrokeImage
		//
		final AbstractButton btnSaveStrokeImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSaveStrokeImage", btnSaveStrokeImage, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnSaveStrokeImage, 0, null)));
		//
		// btnCopyStrokeWithNumberImage
		//
		final AbstractButton btnCopyStrokeWithNumberImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyStrokeWithNumberImage", btnCopyStrokeWithNumberImage, true);
		//
		Assertions.assertDoesNotThrow(
				() -> instance.actionPerformed(new ActionEvent(btnCopyStrokeWithNumberImage, 0, null)));
		//
		// btnSaveStrokeWithNumberImage
		//
		final AbstractButton btnSaveStrokeWithNumberImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSaveStrokeWithNumberImage", btnSaveStrokeWithNumberImage, true);
		//
		Assertions.assertDoesNotThrow(
				() -> instance.actionPerformed(new ActionEvent(btnSaveStrokeWithNumberImage, 0, null)));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Test
	void testTestAndGet() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_GET, null, Boolean.TRUE, null));
		//
	}

	@Test
	void testSetText() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_TEXT, null, null, null));
		//
		Assertions.assertNull(invoke(METHOD_SET_TEXT, null, null, new JTextComponent[] { new JTextField() }));
		//
	}

	@Test
	void testStartsWith() throws IllegalAccessException, InvocationTargetException {
		//
		final Strings strings = Strings.CS;
		//
		Assertions.assertEquals(Boolean.TRUE, invoke(METHOD_STARTS_WITH, null, strings, null, null));
		//
		Assertions.assertEquals(Boolean.FALSE, invoke(METHOD_STARTS_WITH, null, strings, "", null));
		//
	}

	@Test
	void testAppend() throws IllegalAccessException, InvocationTargetException {
		//
		final StringBuilder sb = new StringBuilder();
		//
		final char c = ' ';
		//
		Assertions.assertEquals(String.valueOf(new char[] { c }),
				Util.toString(invoke(METHOD_APPEND, null, sb, Character.valueOf(c))));
		//
	}

	@Test
	void testTestAndAccept() throws IllegalAccessException, InvocationTargetException {
		//
		final LongPredicate longPredicate = Reflection.newProxy(LongPredicate.class, ih);
		//
		if (ih != null) {
			//
			ih.test = Boolean.TRUE;
			//
		} // if
			//
		final Long l = Long.valueOf(ONE);
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT3_LONG, null, longPredicate, l, null));
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT4, null, org.meeuw.functional.Predicates.biAlwaysTrue(),
				null, null, null));
		//
		final Predicate<?> predicate = Predicates.alwaysTrue();
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT3_OBJECT, null, predicate, null, null));
		//
	}

	@Test
	void testGetAudioUrl() throws Throwable {
		//
		Assertions.assertEquals(":///read?outputFormat=mp3&vid=1&jwt=1.2.3&text=%3Ca%2F%3E&",
				getAudioUrl(null, Strings.CS, Arrays.asList("//", Integer.toString(ONE), "1.2.3", "<a/>", null)));
		//
	}

	private static String getAudioUrl(final String scheme, final Strings strings, final Iterable<?> iterable)
			throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_AUDIO_URL, null, scheme, strings, iterable);
			if (obj == null) {
				return null;
			} else if (obj instanceof String s) {
				return s;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndRun() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_RUN, null, Boolean.TRUE, null));
		//
	}

	@Test
	void testGetSystemClipboard() throws Throwable {
		//
		Assertions
				.assertNull(invoke(METHOD_GET_SYSTEM_CLIP_BOARD, null, ProxyUtil.createProxy(Toolkit.class, new MH())));
		//
	}

	@Test
	void testSetEnabled() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_ENABLED, null, Boolean.TRUE, null, null, null));
		//
	}

	@Test
	void testTestAndApply() throws IllegalAccessException, InvocationTargetException {
		//
		if (ih != null) {
			//
			ih.test = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertNull(invoke(METHOD_TEST_AND_APPLY, null, Reflection.newProxy(BiPredicate.class, ih), null,
				null, null, null));
		//
	}

	@Test
	void testToArray() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TO_ARRAY, null, Reflection.newProxy(Stream.class, ih), null));
		//
		Assertions.assertNull(invoke(METHOD_TO_ARRAY, null, Stream.empty(), null));
		//
	}

	@Test
	void testGetJlptLevelIndices() throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
		//
		Assertions.assertNull(
				invoke(METHOD_GET_JLPT_LEVEL_INDICES, null, new DefaultComboBoxModel<>(new Object[] { null }), null));
		//
		final ObjectMapper objectMapper = new ObjectMapper();
		//
		Assertions.assertEquals(ObjectMapperUtil.writeValueAsString(objectMapper, new int[] { ONE }),
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_GET_JLPT_LEVEL_INDICES, null,
						new DefaultComboBoxModel<>(new Object[] { null, "N1" }), "JLPT N1")));
		//
	}

	@Test
	void testGetJlptLevel() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(
				invoke(METHOD_GET_JLPT_LEVEL, null, new DefaultComboBoxModel<>(new Object[] { null }), null));
		//
		final String n1 = "N1";
		//
		Assertions.assertEquals(n1,
				invoke(METHOD_GET_JLPT_LEVEL, null, new DefaultComboBoxModel<>(new Object[] { null, n1 }), "JLPT N1"));
		//
	}

	@Test
	void testSetJcbJlptLevel() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_JCB_JLPT_LEVEL, instance, new int[] { 0 }));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			try {
				//
				invoke(METHOD_SET_JCB_JLPT_LEVEL, instance, new int[] { ZERO, ONE });
				//
			} catch (final InvocationTargetException e) {
				//
				throw e.getTargetException();
				//
			} // try
				//
		});
		//
		//
	}

	@Test
	void testIH() throws Throwable {
		//
		final InvocationHandler invocationHandler = Util.cast(InvocationHandler.class,
				Narcissus.allocateInstance(Util.forName("org.springframework.context.support.JapanDictGui$IH")));
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(invocationHandler, null, null, null));
		//
		// java.awt.datatransfer.Transferable
		//
		final Transferable transferable = Reflection.newProxy(Transferable.class, invocationHandler);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(invocationHandler, transferable, null, null));
		//
		Assertions.assertNotNull(invoke(invocationHandler, transferable,
				Util.getDeclaredMethod(Transferable.class, "getTransferDataFlavors"), null));
		//
		Assertions.assertNull(invoke(invocationHandler, transferable,
				Util.getDeclaredMethod(Transferable.class, "getTransferData", DataFlavor.class), null));
		//
		// java.util.Map
		//
		final Map<?, ?> map = Reflection.newProxy(Map.class, invocationHandler);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(invocationHandler, map, null, null));
		//
		Assertions.assertNull(invoke(invocationHandler, map, Util.getDeclaredMethod(Map.class, "clear"), null));
		//
		final Method put = Util.getDeclaredMethod(Map.class, "put", Object.class, Object.class);
		//
		Assertions.assertNull(invoke(invocationHandler, map, put, null));
		//
		final Method get = Util.getDeclaredMethod(Map.class, "get", Object.class);
		//
		Assertions.assertThrows(IllegalStateException.class, () -> invoke(invocationHandler, map, get, null));
		//
		final Object k = "k", v = "v";
		//
		Assertions.assertNull(invoke(invocationHandler, map, put, new Object[] { k, v }));
		//
		Assertions.assertSame(v, invoke(invocationHandler, map, get, new Object[] { k }));
		//
	}

	private static Object invoke(final InvocationHandler instance, final Object proxy, final Method method,
			final Object[] args) throws Throwable {
		return instance != null ? instance.invoke(proxy, method, args) : null;
	}

	@Test
	void testChopImage() throws IllegalAccessException, InvocationTargetException, IOException {
		//
		byte[] bs = new byte[] {};
		//
		Assertions.assertNull(invoke(METHOD_CHOP_IMAGE1, null, invoke(METHOD_TO_BUFFERED_IMAGE, instance, bs)));
		//
		Assertions.assertNull(invoke(METHOD_CHOP_IMAGE2, null, bs, null));
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			ImageIO.write(new BufferedImage(ONE, ONE, BufferedImage.TYPE_INT_RGB), "png", baos);
			//
			Assertions.assertNotNull(invoke(METHOD_CHOP_IMAGE1, null,
					invoke(METHOD_TO_BUFFERED_IMAGE, instance, bs = baos.toByteArray())));
			//
			Assertions.assertNotNull(invoke(METHOD_CHOP_IMAGE2, null, bs, null));
			//
			final Object boundingBox = Narcissus.allocateInstance(BoundingBox.class);
			//
			Assertions.assertNotNull(invoke(METHOD_CHOP_IMAGE2, null, bs, boundingBox));
			//
			FieldUtils.writeField(boundingBox, "width", ONE);
			//
			Assertions.assertNotNull(invoke(METHOD_CHOP_IMAGE2, null, bs, boundingBox));
			//
		} // try
			//
	}

	@Test
	void testToDuration() throws Throwable {
		//
		final Duration duration = toDuration(Integer.valueOf(ONE));
		//
		Assertions.assertNotNull(duration);
		//
		Assertions.assertSame(duration, toDuration(duration));
		//
		Assertions.assertEquals(duration, toDuration(toCharArray(Integer.toString(ONE))));
		//
		Assertions.assertEquals(Duration.ofSeconds(ONE), toDuration("PT1S"));
		//
	}

	private static char[] toCharArray(final String instance) throws NoSuchFieldException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		if (Narcissus.getField(instance, Util.getDeclaredField(String.class, "value")) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.toCharArray();
		//
	}

	private static Duration toDuration(final Object object) throws Throwable {
		try {
			final Object obj = invoke(METHOD_TO_DURATION, null, object);
			if (obj == null) {
				return null;
			} else if (obj instanceof Duration duration) {
				return duration;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetColumnName() throws IllegalAccessException, InvocationTargetException {
		//
		final String columnName = "";
		//
		Assertions.assertSame(columnName, invoke(METHOD_GET_COLUMN_NAME, null,
				new JTable(new DefaultTableModel(new Object[] { columnName }, 0)), 0));
		//
	}

	@Test
	void testGetTableCellRendererComponent() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_TABLE_CELL_RENDERER_COMPONENT, null,
				Reflection.newProxy(TableCellRenderer.class, ih), null, null, true, true, 0, 0));
		//
	}

	@Test
	void testValueChanged() throws IllegalAccessException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final ListSelectionModel lsm = Reflection.newProxy(ListSelectionModel.class, ih);
		//
		FieldUtils.writeDeclaredField(instance, "lsm", lsm, true);
		//
		final ListSelectionEvent listSelectionEvent = new ListSelectionEvent(lsm, 0, 0, false);
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		final DefaultTableModel dtm = new DefaultTableModel(new Object[] { null }, 0);
		//
		FieldUtils.writeDeclaredField(instance, "dtm", dtm, true);
		//
		final JTable jTable = new JTable(dtm);
		//
		FieldUtils.writeDeclaredField(instance, "jTable", jTable, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[] {};
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[] { 0 };
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		dtm.addRow(new Object[] { null });
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		dtm.removeRow(0);
		//
		final Object japanDictEntry = Narcissus
				.allocateInstance(Util.forName("org.springframework.context.support.JapanDictGui$JapanDictEntry"));
		//
		dtm.addRow(new Object[] { japanDictEntry });
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		FieldUtils.writeDeclaredField(japanDictEntry, "pageUrl", "", true);
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		FieldUtils.writeDeclaredField(japanDictEntry, "pageUrl", " ", true);
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		FieldUtils.writeDeclaredField(japanDictEntry, "pageUrl", Integer.toString(ONE), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
	}

	@Test
	void testGetStrokeImage() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_STROKE_IMAGE, null, null, null, ""));
		//
	}

	@Test
	void testAnd() throws IllegalAccessException, InvocationTargetException {
		//
		final Predicate<?> alwaysTrue = Predicates.alwaysTrue();
		//
		Assertions.assertEquals(Boolean.FALSE, invoke(METHOD_AND, null, null, alwaysTrue, null));
		//
		Assertions.assertEquals(Boolean.TRUE, invoke(METHOD_AND, null, null, alwaysTrue, alwaysTrue));
		//
	}

	@Test
	void testPrepareRenderer() throws IllegalAccessException, InvocationTargetException {
		//
		final JTable jTable = new JTable(new DefaultTableModel(new Object[] { null }, ONE));
		//
		Assertions.assertNull(
				invoke(METHOD_PREPARE_RENDERER, null, jTable, null, Integer.valueOf(ZERO), Integer.valueOf(ZERO)));
		//
		Assertions.assertNull(invoke(METHOD_PREPARE_RENDERER, null, jTable,
				Reflection.newProxy(TableCellRenderer.class, ih), Integer.valueOf(ZERO), Integer.valueOf(ZERO)));
		//
	}

	@Test
	void testGetCellRenderer() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNotNull(
				invoke(METHOD_GET_CELL_RENDERER, null, new JTable(new DefaultTableModel(new Object[] { null }, ONE)),
						Integer.valueOf(ZERO), Integer.valueOf(ZERO)));
		//
	}

	@Test
	void testGetColumnCount() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(Integer.valueOf(ONE),
				invoke(METHOD_GET_COLUMN_COUNT, null, new JTable(new DefaultTableModel(new Object[] { null }, ZERO))));
		//
	}

	@Test
	void testSetRowSelectionInterval() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_ROW_SELECTION_INTERVAL, null,
				new JTable(new DefaultTableModel(new Object[] { null }, ONE)), ZERO, ZERO));
		//
	}

	@Test
	void testGetPitchAccentImage() throws IllegalAccessException, InvocationTargetException {
		//
		final Object japanDictEntry = Narcissus.allocateInstance(CLASS_JAPAN_DICT_ENTRY);
		//
		FieldUtils.writeDeclaredField(japanDictEntry, "pitchAccent", "a", true);
		//
		Assertions.assertNull(invoke(METHOD_GET_PITCH_ACCENT_IMAGE, null, japanDictEntry, null));
		//
	}

	@Test
	void testCreateTableCellRenderer() throws IllegalAccessException, InvocationTargetException {
		//
		final TableCellRenderer tcr = Util.cast(TableCellRenderer.class,
				invoke(METHOD_CREATE_TABLE_CELL_RENDERER, null, (Object) null));
		//
		if (tcr == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertNull(tcr.getTableCellRendererComponent(null, tcr, false, false, ZERO, ZERO));
		//
		final DefaultTableModel dtm = new DefaultTableModel(new Object[] { "", "Hiragana" }, ZERO);
		//
		dtm.addRow(new Object[] { Narcissus.allocateInstance(CLASS_JAPAN_DICT_ENTRY) });
		//
		Assertions.assertNull(tcr.getTableCellRendererComponent(new JTable(dtm), tcr, false, false, ZERO, ZERO));
		//
	}

	@Test
	void testCreatePitchAccentListCellRenderer()
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
		//
		final ListCellRenderer<?> lcr = Util.cast(ListCellRenderer.class,
				invoke(METHOD_CREATE_PITCH_ACCENT_LIST_CELL_RENDERER, null, null, null, null));
		//
		if (lcr == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertNull(lcr.getListCellRendererComponent(null, null, ZERO, false, false));
		//
		final Object pitchAccent = Narcissus
				.allocateInstance(Class.forName("org.springframework.context.support.JapanDictGui$PitchAccent"));
		//
		final Method getListCellRendererComponent = ListCellRenderer.class.getDeclaredMethod(
				"getListCellRendererComponent", JList.class, Object.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
		//
		Assertions.assertNull(
				Narcissus.invokeMethod(lcr, getListCellRendererComponent, null, pitchAccent, ZERO, false, false));
		//
		FieldUtils.writeDeclaredField(pitchAccent, "image", new BufferedImage(ONE, ONE, BufferedImage.TYPE_INT_RGB),
				true);
		//
		Assertions.assertNotNull(
				Narcissus.invokeMethod(lcr, getListCellRendererComponent, null, pitchAccent, ZERO, false, false));
		//
	}

	@Test
	void testSetPreferredSize() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_PREFERRED_SIZE, null, new JLabel(), null));
		//
	}

	@Test
	void testGetPitchAccents() throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
		//
		final Builder builder = JsonMapper.builder();
		//
		if (builder != null) {
			//
			builder.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
			//
		} // if
			//
		final ObjectMapper objectMapper = builder != null ? builder.build() : null;
		//
		if (objectMapper != null) {
			//
			objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
			//
		} // if
			//
		Assertions.assertEquals("[{\"image\":null,\"pitchAccent\":null}]", ObjectMapperUtil
				.writeValueAsString(objectMapper, invoke(METHOD_GET_PITCH_ACCENTS, null, Collections.singleton(null))));
		//
	}

}