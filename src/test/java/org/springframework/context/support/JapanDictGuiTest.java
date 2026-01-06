package org.springframework.context.support;

import java.awt.Component;
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
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableLongConsumer;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.BoundingBox;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class JapanDictGuiTest {

	private static Method METHOD_SET_VISIBLE, METHOD_TEST_AND_GET, METHOD_SET_EDITABLE, METHOD_SET_TEXT,
			METHOD_STARTS_WITH, METHOD_APPEND, METHOD_TEST_AND_ACCEPT3_OBJECT, METHOD_TEST_AND_ACCEPT3_LONG,
			METHOD_TEST_AND_ACCEPT4, METHOD_TEST_AND_ACCEPT6, METHOD_GET_AUDIO_URL, METHOD_TEST_AND_RUN,
			METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_SET_ENABLED, METHOD_TEST_AND_APPLY, METHOD_TO_ARRAY,
			METHOD_GET_JLPT_LEVEL_INDICES, METHOD_GET_JLPT_LEVEL, METHOD_SET_JCB_JLPT_LEVEL, METHOD_CHOP_IMAGE1,
			METHOD_CHOP_IMAGE2, METHOD_GET_AS_BOOLEAN, METHOD_TO_DURATION, METHOD_TO_BUFFERED_IMAGE,
			METHOD_GET_COLUMN_NAME, METHOD_GET_TABLE_CELL_RENDERER_COMPONENT = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = JapanDictGui.class;
		//
		(METHOD_SET_VISIBLE = Util.getDeclaredMethod(clz, "setVisible", Component.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_GET = Util.getDeclaredMethod(clz, "testAndGet", Boolean.TYPE, FailableSupplier.class))
				.setAccessible(true);
		//
		(METHOD_SET_EDITABLE = Util.getDeclaredMethod(clz, "setEditable", Boolean.TYPE, JTextComponent[].class))
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
		(METHOD_TEST_AND_ACCEPT6 = Util.getDeclaredMethod(clz, "testAndAccept", Predicate.class, Object.class,
				Consumer.class, Predicate.class, Object.class, Consumer.class)).setAccessible(true);
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
		(METHOD_GET_AS_BOOLEAN = Util.getDeclaredMethod(clz, "getAsBoolean", BooleanSupplier.class))
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
	}

	private static class IH implements InvocationHandler {

		private Boolean test, booleanValue;

		private Integer size, status, length;

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
			} else if (proxy instanceof Response && Objects.equals(name, "status")) {
				//
				return status;
				//
			} else if (proxy instanceof BooleanSupplier) {
				//
				if (Objects.equals(name, "getAsBoolean")) {
					//
					return booleanValue;
					//
				} // if
					//
			} else if (proxy instanceof CharSequence && Objects.equals(name, "length")) {
				//
				return length;
				//
			} else if (proxy instanceof LongPredicate && Objects.equals(name, "test")) {
				//
				return test;
				//
			} else if (proxy instanceof TableModel && Objects.equals(name, "getValueAt")) {
				//
				return null;
				//
			} else if (proxy instanceof TableCellRenderer && Objects.equals(name, "getTableCellRendererComponent")) {
				//
				return null;
				//
			} else if (proxy instanceof ElementHandle && Objects.equals(name, "boundingBox")) {
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
	void testNull() {
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
	void testNonNull() {
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
							Arrays.equals(parameterTypes, new Class<?>[] { TableCellRenderer.class, JTable.class,
									Object.class, Boolean.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE })))) {
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
						ih.test = ih.booleanValue = Boolean.FALSE;
						//
						ih.size = ih.status = ih.length = Integer.valueOf(0);
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
				if (Boolean.logicalOr(
						Boolean.logicalAnd(isPrimitive(returnType = Util.getReturnType(m)),
								!Objects.equals(returnType, Void.TYPE)),
						Boolean.logicalAnd(Objects.equals(name, "append"), Arrays.equals(parameterTypes,
								new Class<?>[] { StringBuilder.class, Character.TYPE })))) {
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

	private static boolean or(final boolean a, final boolean b, final boolean... bs) {
		//
		boolean result = a || b;
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
		// btnCopyHiragana
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
		Util.setText(tfAudioUrl, "1");
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
	}

	@Test
	void testSetVisible() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_VISIBLE, null, new JLabel(), Boolean.TRUE));
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
	void testSetEditable() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_EDITABLE, null, Boolean.TRUE, null));
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
		final Long l = Long.valueOf(1);
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
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT6, null, predicate, null, null, null, null, null));
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT6, null, null, null, null, predicate, null, null));
		//
	}

	@Test
	void testGetAudioUrl() throws Throwable {
		//
		Assertions.assertEquals(":///read?outputFormat=mp3&vid=1&jwt=1.2.3&text=%3Ca%2F%3E&",
				getAudioUrl(null, Strings.CS, Arrays.asList("//", "1", "1.2.3", "<a/>", null)));
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
		Assertions.assertEquals(ObjectMapperUtil.writeValueAsString(objectMapper, new int[] { 1 }),
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
				invoke(METHOD_SET_JCB_JLPT_LEVEL, instance, new int[] { 0, 1 });
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
		final Object object = Reflection.newProxy(Transferable.class, invocationHandler);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(invocationHandler, object, null, null));
		//
		Assertions.assertNotNull(invoke(invocationHandler, object,
				Util.getDeclaredMethod(Transferable.class, "getTransferDataFlavors"), null));
		//
		Assertions.assertNull(invoke(invocationHandler, object,
				Util.getDeclaredMethod(Transferable.class, "getTransferData", DataFlavor.class), null));
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
			ImageIO.write(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), "png", baos);
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
			FieldUtils.writeField(boundingBox, "width", 1);
			//
			Assertions.assertNotNull(invoke(METHOD_CHOP_IMAGE2, null, bs, boundingBox));
			//
		} // try
			//
	}

	@Test
	void testGetAsBoolean() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(ih != null ? ih.booleanValue = Boolean.TRUE : null,
				invoke(METHOD_GET_AS_BOOLEAN, null, Reflection.newProxy(BooleanSupplier.class, ih)));
		//
	}

	@Test
	void testToDuration() throws Throwable {
		//
		final int one = 1;
		//
		final Duration duration = toDuration(Integer.valueOf(one));
		//
		Assertions.assertNotNull(duration);
		//
		Assertions.assertSame(duration, toDuration(duration));
		//
		Assertions.assertEquals(duration, toDuration(toCharArray(Integer.toString(one))));
		//
		Assertions.assertEquals(Duration.ofSeconds(1), toDuration("PT1S"));
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

}