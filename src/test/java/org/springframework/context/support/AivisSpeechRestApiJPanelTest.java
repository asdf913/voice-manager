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
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Vector;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ListCellRenderer;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.net.HostAndPort;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class AivisSpeechRestApiJPanelTest {

	private static Method METHOD_ADD_ACTION_LISTENER, METHOD_CREATE_HOST_AND_PORT, METHOD_WRITE, METHOD_GET_BYTES,
			METHOD_REMOVE_ALL_ELEMENTS, METHOD_GET_SCREEN_SIZE, METHOD_GET_HOST, METHOD_TEST_AND_ACCEPT,
			METHOD_SET_VISIBLE, METHOD_PACK, METHOD_ADD, METHOD_SET_DEFAULT_CLOSE_OPERATION,
			METHOD_SPEAKERS_HOST_AND_PORT, METHOD_SPEAKERS_ITERABLE, METHOD_AUDIO_QUERY, METHOD_SYNTHESIS = null;

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
		(METHOD_AUDIO_QUERY = clz.getDeclaredMethod("audioQuery", HostAndPort.class, String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_SYNTHESIS = clz.getDeclaredMethod("synthesis", HostAndPort.class, String.class, String.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean test, equals = null;

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
			} else if (proxy instanceof Iterable && Objects.equals(name, "iterator")) {
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
			if (Objects.equals(m.getReturnType(), Boolean.TYPE)) {
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
			if (Objects.equals(m.getReturnType(), Boolean.TYPE)) {
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
		final Object btnAudioQuery = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnAudioQuery", btnAudioQuery, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnAudioQuery, 0, null)));
		//
		final Object btnViewAudioQuery = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnViewAudioQuery", btnViewAudioQuery, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnViewAudioQuery, 0, null)));
		//
		final Object btnSynthesis = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSynthesis", btnSynthesis, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnSynthesis, 0, null)));
		//
	}

	@Test
	void testItemStateChanged() {
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
		final String host = "";
		//
		Assertions.assertEquals(HostAndPort.fromParts(host, port), createHostAndPort(host, Integer.toString(port)));
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
		Assertions.assertTrue(Objects.deepEquals(new byte[] {}, invoke(METHOD_GET_BYTES, null, "")));
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
		Assertions.assertNull(invoke(METHOD_SPEAKERS_HOST_AND_PORT, null, HostAndPort.fromParts("", 1)));
		//
		Assertions.assertNull(invoke(METHOD_SPEAKERS_ITERABLE, null, Collections.singleton(null)));
		//
		final ObjectMapper objectMapper = new ObjectMapper();
		//
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		//
		Assertions.assertEquals("[{\"name\":null,\"styles\":[{\"id\":null,\"name\":null}]}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_SPEAKERS_ITERABLE, null,
						ObjectMapperUtil.readValue(objectMapper, "[{\"styles\":[null,{}]}]", Object.class))));
		//
	}

	@Test
	void testAudioQuery() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_AUDIO_QUERY, null, HostAndPort.fromParts("", 1), null, null));
		//
	}

	@Test
	void testSynthesis() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SYNTHESIS, null, HostAndPort.fromParts("", 1), null, null));
		//
	}

}