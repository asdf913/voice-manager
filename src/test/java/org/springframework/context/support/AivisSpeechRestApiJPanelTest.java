package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.ItemSelectable;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64.Decoder;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.net.HostAndPort;
import com.google.common.reflect.Reflection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class AivisSpeechRestApiJPanelTest {

	private static final String EMPTY = "";

	private static Class<?> CLASS_STYLE, CLASS_SPEAKER = null;

	private static Method METHOD_ADD_ACTION_LISTENER, METHOD_CREATE_HOST_AND_PORT, METHOD_WRITE, METHOD_GET_BYTES,
			METHOD_REMOVE_ALL_ELEMENTS, METHOD_GET_SCREEN_SIZE, METHOD_GET_HOST, METHOD_TEST_AND_ACCEPT,
			METHOD_SET_VISIBLE, METHOD_PACK, METHOD_ADD, METHOD_SET_DEFAULT_CLOSE_OPERATION,
			METHOD_SPEAKERS_HOST_AND_PORT, METHOD_SPEAKERS_ITERABLE, METHOD_AUDIO_QUERY, METHOD_SYNTHESIS,
			METHOD_LENGTH, METHOD_TEST_AND_RUN, METHOD_ADD_ITEM_LISTENER, METHOD_SPEAKER_INFO_HOST_AND_PORT,
			METHOD_SPEAKER_INFO_MAP, METHOD_DECODE, METHOD_GET_STYLE_INFO_BY_ID, METHOD_SET_STYLE_INFO, METHOD_LINES,
			METHOD_TO_JSON, METHOD_FROM_JSON, METHOD_CREATE = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = AivisSpeechRestApiJPanel.class;
		//
		(METHOD_ADD_ACTION_LISTENER = clz.getDeclaredMethod("addActionListener", ActionListener.class,
				AbstractButton[].class)).setAccessible(true);
		//
		(METHOD_CREATE_HOST_AND_PORT = clz.getDeclaredMethod("createHostAndPort", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_WRITE = clz.getDeclaredMethod("write", OutputStream.class, byte[].class)).setAccessible(true);
		//
		(METHOD_GET_BYTES = clz.getDeclaredMethod("getBytes", String.class)).setAccessible(true);
		//
		(METHOD_REMOVE_ALL_ELEMENTS = clz.getDeclaredMethod("removeAllElements", DefaultComboBoxModel.class))
				.setAccessible(true);
		//
		(METHOD_GET_SCREEN_SIZE = clz.getDeclaredMethod("getScreenSize", Toolkit.class)).setAccessible(true);
		//
		(METHOD_GET_HOST = clz.getDeclaredMethod("getHost", HostAndPort.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_SET_VISIBLE = clz.getDeclaredMethod("setVisible", Component.class, Boolean.TYPE)).setAccessible(true);
		//
		(METHOD_PACK = clz.getDeclaredMethod("pack", Window.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Container.class, Component.class)).setAccessible(true);
		//
		(METHOD_SET_DEFAULT_CLOSE_OPERATION = clz.getDeclaredMethod("setDefaultCloseOperation", JFrame.class,
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_SPEAKERS_HOST_AND_PORT = clz.getDeclaredMethod("speakers", HostAndPort.class)).setAccessible(true);
		//
		(METHOD_SPEAKERS_ITERABLE = clz.getDeclaredMethod("speakers", Iterable.class)).setAccessible(true);
		//
		(METHOD_AUDIO_QUERY = clz.getDeclaredMethod("audioQuery", HostAndPort.class,
				CLASS_STYLE = Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$Style"),
				String.class)).setAccessible(true);
		//
		(METHOD_SYNTHESIS = clz.getDeclaredMethod("synthesis", HostAndPort.class, CLASS_STYLE, String.class))
				.setAccessible(true);
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", byte[].class)).setAccessible(true);
		//
		(METHOD_TEST_AND_RUN = clz.getDeclaredMethod("testAndRun", Boolean.TYPE, Runnable.class)).setAccessible(true);
		//
		(METHOD_ADD_ITEM_LISTENER = clz.getDeclaredMethod("addItemListener", ItemListener.class,
				ItemSelectable[].class)).setAccessible(true);
		//
		(METHOD_SPEAKER_INFO_HOST_AND_PORT = clz.getDeclaredMethod("speakerInfo", HostAndPort.class, String.class))
				.setAccessible(true);
		//
		(METHOD_SPEAKER_INFO_MAP = clz.getDeclaredMethod("speakerInfo", Map.class)).setAccessible(true);
		//
		(METHOD_DECODE = clz.getDeclaredMethod("decode", Decoder.class, String.class)).setAccessible(true);
		//
		(METHOD_GET_STYLE_INFO_BY_ID = clz.getDeclaredMethod("getStyleInfoById", Iterable.class, String.class))
				.setAccessible(true);
		//
		(METHOD_SET_STYLE_INFO = clz.getDeclaredMethod("setStyleInfo",
				CLASS_SPEAKER = Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$Speaker"),
				HostAndPort.class, DefaultComboBoxModel.class)).setAccessible(true);
		//
		(METHOD_LINES = clz.getDeclaredMethod("lines", String.class)).setAccessible(true);
		//
		(METHOD_TO_JSON = clz.getDeclaredMethod("toJson", Gson.class, Object.class)).setAccessible(true);
		//
		(METHOD_FROM_JSON = clz.getDeclaredMethod("fromJson", Gson.class, String.class, Class.class))
				.setAccessible(true);
		//
		(METHOD_CREATE = clz.getDeclaredMethod("create", GsonBuilder.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean test, equals = null;

		private Long count = null;

		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (ReflectionUtils.isEqualsMethod(method)) {
				//
				return equals;
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
			} else if (proxy instanceof BiFunction) {
				//
				if (Objects.equals(name, "apply")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof ListCellRenderer) {
				//
				if (Objects.equals(name, "getListCellRendererComponent")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof FailableFunction) {
				//
				if (Objects.equals(name, "apply")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Iterable) {
				//
				if (IterableUtils.contains(Arrays.asList("iterator", "spliterator"), name)) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Map && Objects.equals(name, "get")) {
				//
				return null;
				//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(name, "count")) {
					//
					return count;
					//
				} // if
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
			if (Objects.equals(getReturnType(thisMethod), Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String name = Util.getName(thisMethod);
			//
			if (self instanceof Toolkit && Objects.equals(name, "getScreenSize")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private AivisSpeechRestApiJPanel instance = null;

	private IH ih = null;

	private MH mh = null;

	private Object style, styleInfo, speaker = null;

	private ObjectMapper objectMapper = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = Util.cast(AivisSpeechRestApiJPanel.class,
				Narcissus.allocateInstance(AivisSpeechRestApiJPanel.class));
		//
		ih = new IH();
		//
		mh = new MH();
		//
		style = Narcissus.allocateInstance(CLASS_STYLE);
		//
		styleInfo = Narcissus.allocateInstance(
				Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$StyleInfo"));
		//
		speaker = Narcissus.allocateInstance(CLASS_SPEAKER);
		//
		objectMapper = new ObjectMapper();
		//
	}

	private static Class<?> getReturnType(final Method instance) {
		return instance != null ? instance.getReturnType() : null;
	}

	@Test
	void testNull() {
		//
		final Method[] ms = AivisSpeechRestApiJPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		Object invoke = null;
		//
		String toString = null;
		//
		Object[] arguments = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
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
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // if
				//
			arguments = toArray(collection);
			//
			toString = Util.toString(m);
			//
			invoke = Modifier.isStatic(m.getModifiers()) ? Narcissus.invokeStaticMethod(m, arguments)
					: Narcissus.invokeMethod(instance, m, arguments);
			//
			if (IterableUtils.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE, Long.TYPE), getReturnType(m))) {
				//
				Assertions.assertNotNull(invoke, toString);
				//
			} else {
				//
				Assertions.assertNull(invoke, toString);
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
	void testMethodWithInterfaceParameter() {
		//
		final Method[] ms = AivisSpeechRestApiJPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		Object invoke = null;
		//
		String toString = null;
		//
		Object[] arguments = null;
		//
		if (ih != null) {
			//
			ih.test = Boolean.FALSE;
			//
			ih.count = Long.valueOf(0);
			//
		} // if
			//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
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
				if (isInterface(parameterType = ArrayUtils.get(parameterTypes, j))) {
					//
					Util.add(collection, Reflection.newProxy(parameterType, ih));
					//
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
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
			} // if
				//
			arguments = toArray(collection);
			//
			toString = Util.toString(m);
			//
			invoke = Modifier.isStatic(m.getModifiers()) ? Narcissus.invokeStaticMethod(m, arguments)
					: Narcissus.invokeMethod(instance, m, arguments);
			//
			if (Boolean.logicalOr(
					IterableUtils.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE, Long.TYPE), getReturnType(m)),
					Boolean.logicalAnd(Objects.equals(Util.getName(m), "speakerInfo"),
							Arrays.equals(parameterTypes, new Class<?>[] { Map.class })))) {
				//
				Assertions.assertNotNull(invoke, toString);
				//
			} else {
				//
				Assertions.assertNull(invoke, toString);
				//
			} // if
				//
		} // for
			//
	}

	private static boolean isInterface(final Class<?> instance) {
		return instance != null && instance.isInterface();
	}

	@Test
	void testActionPerformed() throws Exception {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
			// btnAudioQuery
			//
		final Object btnAudioQuery = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnAudioQuery", btnAudioQuery, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnAudioQuery, 0, null)));
		//
		// btnViewAudioQuery
		//
		final Object btnViewAudioQuery = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnViewAudioQuery", btnViewAudioQuery, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnViewAudioQuery, 0, null)));
		//
		// btnSynthesis
		//
		final Object btnSynthesis = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSynthesis", btnSynthesis, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnSynthesis, 0, null)));
		//
		// btnViewPortrait
		//
		final Object btnViewPortrait = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnViewPortrait", btnViewPortrait, true);
		//
		final ActionEvent actionEventBtnViewPortrait = new ActionEvent(btnViewPortrait, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewPortrait));
		//
		final JComboBox<?> jcbSpeaker = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbSpeaker", jcbSpeaker, true);
		//
		ComboBoxModel<?> cbm = jcbSpeaker.getModel();
		//
		invoke(Util.getDeclaredMethod(Util.getClass(cbm), "addElement", Object.class), cbm, speaker);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewPortrait));
		//
		FieldUtils.writeDeclaredField(speaker, "speakerInfo",
				Narcissus.allocateInstance(
						Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$SpeakerInfo")),
				true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewPortrait));
		//
		// btnViewIcon
		//
		final Object btnViewIcon = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnViewIcon", btnViewIcon, true);
		//
		final ActionEvent actionEventBtnViewIcon = new ActionEvent(btnViewIcon, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewIcon));
		//
		final JComboBox<?> jcbStyle = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbStyle", jcbStyle, true);
		//
		invoke(Util.getDeclaredMethod(Util.getClass(cbm = jcbStyle.getModel()), "addElement", Object.class), cbm,
				style);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewIcon));
		//
		FieldUtils.writeDeclaredField(style, "styleInfo", styleInfo, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewIcon));
		//
		// btnCopyVoiceSampleTranscriptToText
		//
		final Object btnCopyVoiceSampleTranscriptToText = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyVoiceSampleTranscriptToText",
				btnCopyVoiceSampleTranscriptToText, true);
		//
		Assertions.assertDoesNotThrow(
				() -> instance.actionPerformed(new ActionEvent(btnCopyVoiceSampleTranscriptToText, 0, null)));
		//
	}

	@Test
	void testItemStateChanged() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		if (ih != null) {
			//
			ih.equals = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance
				.itemStateChanged(new ItemEvent(Reflection.newProxy(ItemSelectable.class, ih), 0, null, 0)));
		//
		// jcbSpeaker
		//
		final JComboBox<?> jcbSpeaker = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbSpeaker", jcbSpeaker, true);
		//
		ComboBoxModel<?> cbm = jcbSpeaker.getModel();
		//
		invoke(Util.getDeclaredMethod(Util.getClass(cbm), "addElement", Object.class), cbm, speaker);
		//
		final ItemEvent itemEventJcbSpeaker = new ItemEvent(jcbSpeaker, 0, null, 0);
		//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEventJcbSpeaker));
		//
		FieldUtils.writeDeclaredField(speaker, "speakerInfo",
				Narcissus.allocateInstance(
						Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$SpeakerInfo")),
				true);
		//
		FieldUtils.writeDeclaredField(instance, "jLabelPortrait", new JLabel(), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEventJcbSpeaker));
		//
		// jcbStyle
		//
		final JComboBox<?> jcbStyle = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbStyle", jcbStyle, true);
		//
		final ItemEvent itemEventJcbStyle = new ItemEvent(jcbStyle, 0, null, 0);
		//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEventJcbStyle));
		//
		invoke(Util.getDeclaredMethod(Util.getClass(cbm = jcbStyle.getModel()), "addElement", Object.class), cbm,
				style);
		//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEventJcbStyle));
		//
		FieldUtils.writeDeclaredField(style, "styleInfo", Narcissus.allocateInstance(
				Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$StyleInfo")), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEventJcbStyle));
		//
	}

	@Test
	void testAddActionListener() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_ADD_ACTION_LISTENER, null, null, null));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Test
	void testCreateHostAndPort() throws Throwable {
		//
		final int port = 12345;
		//
		Assertions.assertNull(createHostAndPort(null, Integer.toString(port)));
		//
		Assertions.assertEquals(HostAndPort.fromParts(EMPTY, port), createHostAndPort(EMPTY, Integer.toString(port)));
		//
	}

	private static HostAndPort createHostAndPort(final String host, final String port) throws Throwable {
		try {
			final Object obj = invoke(METHOD_CREATE_HOST_AND_PORT, null, host, port);
			if (obj == null) {
				return null;
			} else if (obj instanceof HostAndPort) {
				return (HostAndPort) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testWrite() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> write(ProxyUtil.createProxy(OutputStream.class, mh), null));
		//
		try (final OutputStream os = new ByteArrayOutputStream()) {
			//
			Assertions.assertDoesNotThrow(() -> write(os, null));
			//
			Assertions.assertDoesNotThrow(() -> write(os, new byte[] {}));
			//
		} // try
			//
	}

	private static void write(final OutputStream instance, final byte[] bs) throws Throwable {
		try {
			invoke(METHOD_WRITE, null, instance, bs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetBytes() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertTrue(Objects.deepEquals(new byte[] {}, invoke(METHOD_GET_BYTES, null, EMPTY)));
		//
	}

	@Test
	void testRemoveAllElements() throws IllegalAccessException, InvocationTargetException, NoSuchFieldException,
			InstantiationException, NoSuchMethodException {
		//
		final Object dcbm = Narcissus.allocateInstance(DefaultComboBoxModel.class);
		//
		Assertions.assertNull(invoke(METHOD_REMOVE_ALL_ELEMENTS, null, dcbm));
		//
		Narcissus.setField(dcbm, Narcissus.findField(Util.getClass(dcbm), "objects"),
				Util.newInstance(Vector.class.getDeclaredConstructor()));
		//
		Assertions.assertNull(invoke(METHOD_REMOVE_ALL_ELEMENTS, null, dcbm));
		//
	}

	@Test
	void testGetScreenSize() throws Throwable {
		//
		Assertions.assertNull(invoke(METHOD_GET_SCREEN_SIZE, null, ProxyUtil.createProxy(Toolkit.class, mh)));
		//
	}

	@Test
	void testGetHost() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_HOST, null, Narcissus.allocateInstance(HostAndPort.class)));
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
	void testSetVisible() throws Throwable {
		//
		Assertions
				.assertNull(invoke(METHOD_SET_VISIBLE, null, ProxyUtil.createProxy(Component.class, mh), Boolean.TRUE));
		//
	}

	@Test
	void testPack() throws IllegalAccessException, InvocationTargetException, NoSuchFieldException {
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			return;
			//
		} // if
			//
		final Object window = Narcissus.allocateInstance(Window.class);
		//
		Assertions.assertNull(invoke(METHOD_PACK, null, window));
		//
		Narcissus.setField(window, Narcissus.findField(Util.getClass(instance), "objectLock"), new Object());
		//
		Assertions.assertNull(invoke(METHOD_PACK, null, window));
		//
		Narcissus.setField(window, Narcissus.findField(Util.getClass(instance), "objectLock"), new Object());
		//
		Assertions.assertNull(invoke(METHOD_PACK, null, window));
		//
	}

	@Test
	void testAdd() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_ADD, null, Narcissus.allocateInstance(Container.class), null));
		//
	}

	@Test
	void testSetDefaultCloseOperation() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_DEFAULT_CLOSE_OPERATION, null, Narcissus.allocateInstance(JFrame.class),
				Integer.valueOf(0)));
		//
	}

	@Test
	void testSpeakers() throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
		//
		Assertions.assertNull(invoke(METHOD_SPEAKERS_HOST_AND_PORT, null, HostAndPort.fromHost(EMPTY)));
		//
		Assertions.assertNull(invoke(METHOD_SPEAKERS_HOST_AND_PORT, null, HostAndPort.fromParts(EMPTY, 1)));
		//
		Assertions.assertNull(invoke(METHOD_SPEAKERS_ITERABLE, null, Collections.singleton(null)));
		//
		if (objectMapper != null) {
			//
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			//
		} // if
			//
		Assertions.assertEquals(
				"[{\"name\":null,\"speakerUuid\":null,\"styles\":[{\"id\":null,\"name\":null,\"styleInfo\":null}],\"speakerInfo\":null}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_SPEAKERS_ITERABLE, null,
						ObjectMapperUtil.readValue(objectMapper, "[{\"styles\":[null,{}]}]", Object.class))));
		//
	}

	@Test
	void testAudioQuery() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_AUDIO_QUERY, null, HostAndPort.fromHost(EMPTY), null, null));
		//
		final HostAndPort hostAndPort = HostAndPort.fromParts(EMPTY, 1);
		//
		Assertions.assertNull(invoke(METHOD_AUDIO_QUERY, null, hostAndPort, null, null));
		//
		Assertions.assertNull(invoke(METHOD_AUDIO_QUERY, null, hostAndPort, style, null));
		//
	}

	@Test
	void testSynthesis() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SYNTHESIS, null, HostAndPort.fromHost(EMPTY), null, null));
		//
		final HostAndPort hostAndPort = HostAndPort.fromParts(EMPTY, 1);
		//
		Assertions.assertNull(invoke(METHOD_SYNTHESIS, null, hostAndPort, null, null));
		//
		Assertions.assertNull(invoke(METHOD_SYNTHESIS, null, hostAndPort, style, null));
		//
	}

	@Test
	void testLength() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(Integer.valueOf(0), invoke(METHOD_LENGTH, null, new byte[] {}));
		//
	}

	@Test
	void testTestAndRun() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_RUN, null, Boolean.TRUE, null));
		//
	}

	@Test
	void testAddItemListener() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_ADD_ITEM_LISTENER, null, null, (Object) null));
		//
	}

	@Test
	void testSpeakInfo() throws Throwable {
		//
		Assertions.assertNull(invoke(METHOD_SPEAKER_INFO_HOST_AND_PORT, null, HostAndPort.fromHost(EMPTY), null));
		//
		Assertions.assertNull(invoke(METHOD_SPEAKER_INFO_HOST_AND_PORT, null, HostAndPort.fromParts(EMPTY, 1), null));
		//
		if (objectMapper != null) {
			//
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			//
			objectMapper.setDefaultPropertyInclusion(Include.NON_NULL);
			//
		} // if
			//
		Assertions.assertEquals("{\"portrait\":\"\"}", ObjectMapperUtil.writeValueAsString(objectMapper,
				invoke(METHOD_SPEAKER_INFO_MAP, null, Collections.singletonMap("portrait", EMPTY))));
		//
		Assertions.assertEquals("{\"styleInfos\":[{}]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_SPEAKER_INFO_MAP, null,
						ObjectMapperUtil.readValue(objectMapper, "{\"style_infos\":[null,{}]}", Object.class))));
		//
		Assertions.assertEquals("{\"styleInfos\":[{\"icon\":\"\",\"voiceSamples\":[\"\"]}]}",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						invoke(METHOD_SPEAKER_INFO_MAP, null, ObjectMapperUtil.readValue(objectMapper,
								"{\"style_infos\":[{\"icon\":\"\",\"voice_samples\":[\"\"]}]}", Object.class))));
		//
	}

	@Test
	void testDecode() throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
		//
		final Decoder decoder = java.util.Base64.getDecoder();
		//
		Assertions.assertNull(invoke(METHOD_DECODE, null, decoder, (Object) null));
		//
		Assertions.assertEquals(StringUtils.repeat('"', 2),
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_DECODE, null, decoder, EMPTY)));
		//
	}

	@Test
	void testGetStyleInfobyId() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_STYLE_INFO_BY_ID, null, Collections.singleton(null), null));
		//
		Assertions.assertSame(styleInfo,
				invoke(METHOD_GET_STYLE_INFO_BY_ID, null, Collections.singleton(styleInfo), null));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			try {
				//
				invoke(METHOD_GET_STYLE_INFO_BY_ID, null, Collections.nCopies(2, styleInfo), null);
				//
			} catch (final InvocationTargetException e) {
				//
				throw e.getTargetException();
				//
			} // try
				//
		});
		//
	}

	@Test
	void testSetStyleInfo() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_STYLE_INFO, null, speaker, null, null));
		//
	}

	@Test
	void testLines() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_LINES, null, Narcissus.allocateInstance(String.class)));
		//
	}

	@Test
	void testToJson() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TO_JSON, null, Narcissus.allocateInstance(Gson.class), null));
		//
		Assertions.assertEquals(Objects.toString(null), invoke(METHOD_TO_JSON, null, new Gson(), null));
		//
	}

	@Test
	void testFromJson() throws IllegalAccessException, InvocationTargetException {
		//
		final Object gson = Narcissus.allocateInstance(Gson.class);
		//
		Assertions.assertNull(invoke(METHOD_FROM_JSON, null, gson, null, null));
		//
		Assertions.assertNull(invoke(METHOD_FROM_JSON, null, gson, null, Object.class));
		//
		Assertions.assertNull(invoke(METHOD_FROM_JSON, null, gson, "", Object.class));
		//
		Assertions.assertNull(invoke(METHOD_FROM_JSON, null, gson, "{}", Object.class));
		//
	}

	@Test
	void testCreate() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_CREATE, null, Narcissus.allocateInstance(GsonBuilder.class)));
		//
	}

}