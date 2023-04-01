package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.jena.ext.com.google.common.base.Predicates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.reflect.Reflection;

import domain.Pronunciation;
import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

class OnlineNHKJapanesePronunciationAccentGuiTest {

	private static final String EMPTY = "";

	private static final int ONE = 1;

	private static Method METHOD_CAST, METHOD_TO_STRING, METHOD_TEST_AND_APPLY, METHOD_GET_WIDTH, METHOD_GET_TEXT,
			METHOD_GET_VALUE, METHOD_ADD_ELEMENT, METHOD_REMOVE_ELEMENT_AT, METHOD_GET_SELECTED_ITEM, METHOD_GET_SIZE,
			METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_SET_CONTENTS, METHOD_FOR_EACH_ITERABLE, METHOD_FOR_EACH_INT_STREAM,
			METHOD_MAP_INT_STREAM, METHOD_MAP_STREAM, METHOD_SET_PITCH_ACCENT_IMAGE_TO_SYSTEM_CLIPBOARD_CONTENTS,
			METHOD_SAVE_PITCH_ACCENT_IMAGE, METHOD_PLAY_AUDIO, METHOD_SAVE_AUDIO, METHOD_PRONOUNICATION_CHANGED,
			METHOD_GET_DECLARED_FIELD, METHOD_FOR_NAME, METHOD_OPEN_STREAM, METHOD_PLAY, METHOD_ADD_ACTION_LISTENER,
			METHOD_GET_MAP, METHOD_GET_FIELD, METHOD_SET_TEXT, METHOD_SET_FORE_GROUND,
			METHOD_GET_LIST_CELL_RENDERER_COMPONENT, METHOD_SAVE_FILE, METHOD_CONTAINS_KEY, METHOD_IIF, METHOD_GET_NAME,
			METHOD_SORT, METHOD_CREATE_IMAGE_FORMAT_COMPARATOR, METHOD_IS_ANNOTATION_PRESENT, METHOD_GET_ANNOTATION,
			METHOD_GET_PREFERRED_SIZE, METHOD_STREAM, METHOD_FILTER, METHOD_TO_LIST, METHOD_SET_PREFERRED_SIZE,
			METHOD_MAX, METHOD_TO_ARRAY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = OnlineNHKJapanesePronunciationAccentGui.class;
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_WIDTH = clz.getDeclaredMethod("getWidth", Dimension2D.class)).setAccessible(true);
		//
		(METHOD_GET_TEXT = clz.getDeclaredMethod("getText", JTextComponent.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Entry.class)).setAccessible(true);
		//
		(METHOD_ADD_ELEMENT = clz.getDeclaredMethod("addElement", MutableComboBoxModel.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_REMOVE_ELEMENT_AT = clz.getDeclaredMethod("removeElementAt", MutableComboBoxModel.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_SELECTED_ITEM = clz.getDeclaredMethod("getSelectedItem", ComboBoxModel.class)).setAccessible(true);
		//
		(METHOD_GET_SIZE = clz.getDeclaredMethod("getSize", ListModel.class)).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
		//
		(METHOD_FOR_EACH_ITERABLE = clz.getDeclaredMethod("forEach", Iterable.class, Consumer.class))
				.setAccessible(true);
		//
		(METHOD_FOR_EACH_INT_STREAM = clz.getDeclaredMethod("forEach", IntStream.class, IntConsumer.class))
				.setAccessible(true);
		//
		(METHOD_MAP_INT_STREAM = clz.getDeclaredMethod("map", IntStream.class, IntUnaryOperator.class))
				.setAccessible(true);
		//
		(METHOD_MAP_STREAM = clz.getDeclaredMethod("map", Stream.class, Function.class)).setAccessible(true);
		//
		(METHOD_SET_PITCH_ACCENT_IMAGE_TO_SYSTEM_CLIPBOARD_CONTENTS = clz
				.getDeclaredMethod("setPitchAccentImageToSystemClipboardContents", Pronunciation.class))
				.setAccessible(true);
		//
		(METHOD_SAVE_PITCH_ACCENT_IMAGE = clz.getDeclaredMethod("savePitchAccentImage", Pronunciation.class))
				.setAccessible(true);
		//
		(METHOD_PLAY_AUDIO = clz.getDeclaredMethod("playAudio", Pronunciation.class)).setAccessible(true);
		//
		(METHOD_SAVE_AUDIO = clz.getDeclaredMethod("saveAudio", Boolean.TYPE, Pronunciation.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_PRONOUNICATION_CHANGED = clz.getDeclaredMethod("pronounicationChanged", Pronunciation.class,
				MutableComboBoxModel.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_FIELD = clz.getDeclaredMethod("getDeclaredField", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
		//
		(METHOD_OPEN_STREAM = clz.getDeclaredMethod("openStream", URL.class)).setAccessible(true);
		//
		(METHOD_PLAY = clz.getDeclaredMethod("play", Player.class)).setAccessible(true);
		//
		(METHOD_ADD_ACTION_LISTENER = clz.getDeclaredMethod("addActionListener", ActionListener.class,
				AbstractButton[].class)).setAccessible(true);
		//
		(METHOD_GET_MAP = clz.getDeclaredMethod("get", Map.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_FIELD = clz.getDeclaredMethod("get", Field.class, Object.class)).setAccessible(true);
		//
		(METHOD_SET_TEXT = clz.getDeclaredMethod("setText", JLabel.class, String.class)).setAccessible(true);
		//
		(METHOD_SET_FORE_GROUND = clz.getDeclaredMethod("setForeground", Component.class, Color.class))
				.setAccessible(true);
		//
		(METHOD_GET_LIST_CELL_RENDERER_COMPONENT = clz.getDeclaredMethod("getListCellRendererComponent",
				ListCellRenderer.class, JList.class, Object.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_SAVE_FILE = clz.getDeclaredMethod("saveFile", File.class, String.class)).setAccessible(true);
		//
		(METHOD_CONTAINS_KEY = clz.getDeclaredMethod("containsKey", Map.class, Object.class)).setAccessible(true);
		//
		(METHOD_IIF = clz.getDeclaredMethod("iif", Boolean.TYPE, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Class.class)).setAccessible(true);
		//
		(METHOD_SORT = clz.getDeclaredMethod("sort", List.class, Comparator.class)).setAccessible(true);
		//
		(METHOD_CREATE_IMAGE_FORMAT_COMPARATOR = clz.getDeclaredMethod("createImageFormatComparator", List.class))
				.setAccessible(true);
		//
		(METHOD_IS_ANNOTATION_PRESENT = clz.getDeclaredMethod("isAnnotationPresent", AnnotatedElement.class,
				Class.class)).setAccessible(true);
		//
		(METHOD_GET_ANNOTATION = clz.getDeclaredMethod("getAnnotation", AnnotatedElement.class, Class.class))
				.setAccessible(true);
		//
		(METHOD_GET_PREFERRED_SIZE = clz.getDeclaredMethod("getPreferredSize", Component.class)).setAccessible(true);
		//
		(METHOD_STREAM = clz.getDeclaredMethod("stream", Collection.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_TO_LIST = clz.getDeclaredMethod("toList", Stream.class)).setAccessible(true);
		//
		(METHOD_SET_PREFERRED_SIZE = clz.getDeclaredMethod("setPreferredSize", Component.class, Dimension.class))
				.setAccessible(true);
		//
		(METHOD_MAX = clz.getDeclaredMethod("max", Stream.class, Comparator.class)).setAccessible(true);
		//
		(METHOD_TO_ARRAY = clz.getDeclaredMethod("toArray", Collection.class, Object[].class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Integer size = null;

		private Object key, value, selectedItem, get = null;

		private Set<Entry<?, ?>> entrySet = null;

		private Iterator<?> iterator = null;

		private Boolean hasNext, containsKey, isEmpty = null;

		private Component component = null;

		private Stream<?> stream = null;

		private Optional<?> max = null;

		private Object[] toArray = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(method != null ? method.getReturnType() : null, Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof ListModel) {
				//
				if (Objects.equals(methodName, "getSize")) {
					//
					return size;
					//
				} // if
					//
			} else if (proxy instanceof Iterable) {
				//
				if (Objects.equals(methodName, "iterator")) {
					//
					return iterator;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof Iterator) {
				//
				if (Objects.equals(methodName, "hasNext")) {
					//
					if (iterator == proxy) {
						//
						return hasNext;
						//
					} // if
						//
					return iterator != null && iterator.hasNext();
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "get")) {
					//
					return get;
					//
				} else if (Objects.equals(methodName, "isEmpty")) {
					//
					return isEmpty;
					//
				} else if (Objects.equals(methodName, "size")) {
					//
					return size;
					//
				} // if
					//
			} else if (proxy instanceof Collection) {
				//
				if (Objects.equals(methodName, "size")) {
					//
					return size;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof ComboBoxModel) {
				//
				if (Objects.equals(methodName, "getSelectedItem")) {
					//
					return selectedItem;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof Entry) {
				//
				if (Objects.equals(methodName, "getValue") || Objects.equals(methodName, "setValue")) {
					//
					return value;
					//
				} else if (Objects.equals(methodName, "getKey")) {
					//
					return key;
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return entrySet;
					//
				} else if (Objects.equals(methodName, "containsKey")) {
					//
					return containsKey;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "filter")) {
					//
					return proxy;
					//
				} else if (Objects.equals(methodName, "map")) {
					//
					return stream;
					//
				} else if (Objects.equals(methodName, "max")) {
					//
					return max;
					//
				} // if
					//
			} else if (proxy instanceof Collection) {
				//
				if (Objects.equals(methodName, "toArray")) {
					//
					return toArray;
					//
				} // if
					//
			} else if (proxy instanceof ListCellRenderer) {
				//
				if (Objects.equals(methodName, "getListCellRendererComponent")) {
					//
					return component;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static class MH implements MethodHandler {

		private Clipboard systemClipboard = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof Toolkit) {
				//
				if (Objects.equals(methodName, "getSystemClipboard")) {
					//
					return systemClipboard;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OnlineNHKJapanesePronunciationAccentGui instance = null;

	private IH ih = null;

	private MH mh = null;

	private Pronunciation pronunciation = null;

	private JLabel jLabel = null;

	private Entry<?, ?> entry = null;

	private MutableComboBoxModel<?> mutableComboBoxModel = null;

	private Map<?, ?> map = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			final Constructor<OnlineNHKJapanesePronunciationAccentGui> constructor = OnlineNHKJapanesePronunciationAccentGui.class
					.getDeclaredConstructor();
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			instance = constructor != null ? constructor.newInstance() : null;
			//
		} else {
			//
			instance = cast(OnlineNHKJapanesePronunciationAccentGui.class,
					Narcissus.allocateInstance(OnlineNHKJapanesePronunciationAccentGui.class));
			//
		} //
			//
		mh = new MH();
		//
		pronunciation = new Pronunciation();
		//
		jLabel = new JLabel();
		//
		entry = Reflection.newProxy(Entry.class, ih = new IH());
		//
		mutableComboBoxModel = Reflection.newProxy(MutableComboBoxModel.class, ih);
		//
		map = Reflection.newProxy(Map.class, ih);
		//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
		if (instance != null) {
			//
			instance.setBufferedImageType(BufferedImage.TYPE_INT_RGB);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
	}

	private static void afterPropertiesSet(final InitializingBean instance) throws Exception {
		if (instance != null) {
			instance.afterPropertiesSet();
		}
	}

	@Test
	void testSetImageFormatOrders1() throws Throwable {
		//
		final Field imageFormatOrders = OnlineNHKJapanesePronunciationAccentGui.class
				.getDeclaredField("imageFormatOrders");
		//
		if (imageFormatOrders != null) {
			//
			imageFormatOrders.setAccessible(true);
			//
		} // if
			//
			// null
			//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, null));
		//
		Assertions.assertNull(get(imageFormatOrders, instance));
		//
		// java.lang.Iterable
		//
		final Iterable<String> iterable = Collections.emptySet();
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, iterable));
		//
		final List<String> emptyList = Collections.emptyList();
		//
		Assertions.assertEquals(emptyList, get(imageFormatOrders, instance));
		//
		// java.lang.String[]
		//
		final String[] ss = new String[] { Integer.toString(ONE) };
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, ss));
		//
		Assertions.assertEquals(Arrays.asList(ss), get(imageFormatOrders, instance));
		//
		// int[]
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, new int[] { ONE }));
		//
		Assertions.assertEquals(Arrays.asList(Integer.toString(ONE)), get(imageFormatOrders, instance));
		//
		// short[]
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, new short[] { ONE }));
		//
		Assertions.assertEquals(Arrays.asList(Integer.toString(ONE)), get(imageFormatOrders, instance));
		//
		// double[]
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, new double[] { ONE }));
		//
		Assertions.assertEquals(Arrays.asList(Double.toString(Integer.valueOf(ONE).doubleValue())),
				get(imageFormatOrders, instance));
		//
		// float[]
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, new float[] { ONE }));
		//
		Assertions.assertEquals(Arrays.asList(Double.toString(Integer.valueOf(ONE).doubleValue())),
				get(imageFormatOrders, instance));
		//
		// byte[]
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, new byte[] { ONE }));
		//
		Assertions.assertEquals(Arrays.asList(Integer.toString(ONE)), get(imageFormatOrders, instance));
		//
		// char[]
		//
		final char[] chars = Integer.toString(ONE).toCharArray();
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, chars));
		//
		Assertions.assertEquals(Arrays.asList(Integer.toString(ONE)), get(imageFormatOrders, instance));
		//
		// java.lang.Object[]
		//
		final Object object = new Object();
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, new Object[] { object }));
		//
		Assertions.assertEquals(Arrays.asList(toString(object)), get(imageFormatOrders, instance));
		//
		// java.lang.String
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, EMPTY));
		//
		Assertions.assertEquals(Collections.singletonList(EMPTY), get(imageFormatOrders, instance));
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, "[]"));
		//
		Assertions.assertEquals(emptyList, get(imageFormatOrders, instance));
		//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class,
				"{localizedMessage=class java.util.LinkedHashMap, suppressed=[], message=class java.util.LinkedHashMap}",
				() -> setImageFormatOrders(instance, "{}"));
		//
	}

	@Test
	void testSetImageFormatOrders2() throws Throwable {
		//
		final Field imageFormatOrders = OnlineNHKJapanesePronunciationAccentGui.class
				.getDeclaredField("imageFormatOrders");
		//
		if (imageFormatOrders != null) {
			//
			imageFormatOrders.setAccessible(true);
			//
		} // if
			//
		final boolean b = true;
		//
		Assertions.assertDoesNotThrow(() -> setImageFormatOrders(instance, Boolean.toString(b)));
		//
		Assertions.assertEquals(Collections.singletonList(Boolean.toString(b)), get(imageFormatOrders, instance));
		//
	}

	private static void setImageFormatOrders(final OnlineNHKJapanesePronunciationAccentGui instance,
			final Object object) {
		if (instance != null) {
			instance.setImageFormatOrders(object);
		}
	}

	@Test
	void testActionPerformed() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(EMPTY, 0, null)));
		//
		// btnPlayAudio
		//
		final AbstractButton btnPlayAudio = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnPlayAudio", btnPlayAudio, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnPlayAudio, 0, null)));
		//
		// btnCopyPitchAccentImage
		//
		final AbstractButton btnCopyPitchAccentImage = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCopyPitchAccentImage", btnCopyPitchAccentImage, true);
			//
		} // if
			//
		Assertions
				.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnCopyPitchAccentImage, 0, null)));
		//
		// btnSavePitchAccentImage
		//
		final AbstractButton btnSavePitchAccentImage = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnSavePitchAccentImage", btnSavePitchAccentImage, true);
			//
		} // if
			//
		Assertions
				.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnSavePitchAccentImage, 0, null)));
		//
		// btnSaveAudio
		//
		final AbstractButton btnSaveAudio = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnSaveAudio", btnSaveAudio, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnSaveAudio, 0, null)));
		//
		// jcbPronounication
		//
		final JComboBox<?> jcbPronounication = new JComboBox();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jcbPronounication", jcbPronounication, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(jcbPronounication, 0, null)));
		//
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent e) {
		if (instance != null) {
			instance.actionPerformed(e);
		}
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
		Assertions.assertSame(EMPTY, cast(String.class, EMPTY));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Test
	void testToString() throws Throwable {
		//
		Assertions.assertNull(toString(null));
		//
		Assertions.assertSame(EMPTY, toString(EMPTY));
		//
	}

	private static String toString(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_STRING.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> T createProxy(final Class<T> superClass, final MethodHandler mh) throws Throwable {
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(superClass);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		final Object instance = constructor != null ? constructor.newInstance() : null;
		//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(mh);
			//
		} // if
			//
		return (T) cast(clz, instance);
		//
	}

	@Test
	void testGetWidth() throws Throwable {
		//
		Assertions.assertNull(getWidth(null));
		//
	}

	private static Double getWidth(final Dimension2D instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_WIDTH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Double) {
				return (Double) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetText() throws Throwable {
		//
		Assertions.assertNull(getText(null));
		//
		Assertions.assertEquals(EMPTY, getText(new JTextField()));
		//
	}

	private static String getText(final JTextComponent instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TEXT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		Assertions.assertNull(getValue(null));
		//
	}

	private static <V> V getValue(final Entry<?, V> instance) throws Throwable {
		try {
			return (V) METHOD_GET_VALUE.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddElement() {
		//
		Assertions.assertDoesNotThrow(() -> addElement(null, null));
		//
		Assertions.assertDoesNotThrow(() -> addElement(mutableComboBoxModel, null));
		//
	}

	private static <E> void addElement(final MutableComboBoxModel<E> instance, final E item) throws Throwable {
		try {
			METHOD_ADD_ELEMENT.invoke(null, instance, item);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testRemoveElementAt() {
		//
		Assertions.assertDoesNotThrow(() -> removeElementAt(null, 0));
		//
		Assertions.assertDoesNotThrow(() -> removeElementAt(mutableComboBoxModel, 0));
		//
	}

	private static void removeElementAt(final MutableComboBoxModel<?> instance, final int index) throws Throwable {
		try {
			METHOD_REMOVE_ELEMENT_AT.invoke(null, instance, index);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSelectedItem() throws Throwable {
		//
		Assertions.assertNull(getSelectedItem(Reflection.newProxy(ComboBoxModel.class, ih)));
		//
	}

	private static Object getSelectedItem(final ComboBoxModel<?> instance) throws Throwable {
		try {
			return METHOD_GET_SELECTED_ITEM.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSize() throws Throwable {
		//
		if (ih != null) {
			//
			ih.size = ONE;
			//
		} // if
			//
		Assertions.assertEquals(ONE, getSize(Reflection.newProxy(ListModel.class, ih)));
		//
	}

	private static int getSize(final ListModel<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SIZE.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSystemClipboard() throws Throwable {
		//
		Assertions.assertNull(getSystemClipboard(null));
		//
		Assertions.assertNull(getSystemClipboard(createProxy(Toolkit.class, mh)));
		//
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SYSTEM_CLIP_BOARD.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Clipboard) {
				return (Clipboard) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetContents() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> setContents(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> setContents(new Clipboard(null), null, null));
		//
	}

	private static void setContents(final Clipboard instance, final Transferable contents, final ClipboardOwner owner)
			throws Throwable {
		try {
			METHOD_SET_CONTENTS.invoke(null, instance, contents, owner);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForEach() {
		//
		Assertions.assertDoesNotThrow(() -> forEach(null, (Consumer<?>) null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Collections.emptyList(), null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(null, (IntConsumer) null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(IntStream.empty(), null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Reflection.newProxy(IntStream.class, ih), null));
		//
	}

	private static <T> void forEach(final Iterable<T> instance, final Consumer<? super T> action) throws Throwable {
		try {
			METHOD_FOR_EACH_ITERABLE.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void forEach(final IntStream instance, final IntConsumer action) throws Throwable {
		try {
			METHOD_FOR_EACH_INT_STREAM.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMap() throws Throwable {
		//
		Assertions.assertNull(map((IntStream) null, null));
		//
		Assertions.assertNull(map((Stream<?>) null, null));
		//
		Assertions.assertNull(map(Stream.empty(), null));
		//
		Assertions.assertNull(map(Reflection.newProxy(Stream.class, ih), null));
		//
	}

	private static IntStream map(final IntStream instance, final IntUnaryOperator mapper) throws Throwable {
		try {
			final Object obj = METHOD_MAP_INT_STREAM.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof IntStream) {
				return (IntStream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP_STREAM.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetPitchAccentImageToSystemClipboardContents() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> setPitchAccentImageToSystemClipboardContents(pronunciation));
		//
		// org.springframework.context.support.OnlineNHKJapanesePronunciationAccentGui$Pronounication.pitchAccentImage
		//
		if (pronunciation != null) {
			//
			pronunciation
					.setPitchAccentImage(cast(BufferedImage.class, Narcissus.allocateInstance(BufferedImage.class)));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> setPitchAccentImageToSystemClipboardContents(pronunciation));
		//
		if (pronunciation != null) {
			//
			pronunciation.setPitchAccentImage(new BufferedImage(ONE, ONE, BufferedImage.TYPE_4BYTE_ABGR));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> setPitchAccentImageToSystemClipboardContents(pronunciation));
		//
	}

	private static Object createPronounicatio2n() {
		//
		return new Pronunciation();
		//
	}

	private static void setPitchAccentImageToSystemClipboardContents(final Object pronunciation) throws Throwable {
		try {
			METHOD_SET_PITCH_ACCENT_IMAGE_TO_SYSTEM_CLIPBOARD_CONTENTS.invoke(null, pronunciation);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSavePitchAccentImage() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> savePitchAccentImage(pronunciation));
		//
		// org.springframework.context.support.OnlineNHKJapanesePronunciationAccentGui$Pronounication.pitchAccentImage
		//
		if (pronunciation != null) {
			//
			pronunciation
					.setPitchAccentImage(cast(BufferedImage.class, Narcissus.allocateInstance(BufferedImage.class)));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> savePitchAccentImage(pronunciation));
		//
		if (pronunciation != null) {
			//
			pronunciation.setPitchAccentImage(new BufferedImage(ONE, ONE, BufferedImage.TYPE_4BYTE_ABGR));
			//
		} // if
			//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> savePitchAccentImage(pronunciation));
			//
		} // if
			//
	}

	private void savePitchAccentImage(final Object pronunciation) throws Throwable {
		try {
			METHOD_SAVE_PITCH_ACCENT_IMAGE.invoke(instance, pronunciation);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPlayAudio() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> playAudio(pronunciation));
		//
		// org.springframework.context.support.OnlineNHKJapanesePronunciationAccentGui$Pronounication.audioUrls
		//
		if (pronunciation != null) {
			//
			pronunciation.setAudioUrls((Map) map);
			//
		} // if
			//
		if (ih != null) {
			//
			ih.entrySet = Reflection.newProxy(Set.class, ih);
			//
			ih.iterator = Reflection.newProxy(Iterator.class, ih);
			//
			ih.hasNext = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> playAudio(pronunciation));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> playAudio(pronunciation));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(entry);
			//
			ih.key = "audio/wav";
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> playAudio(pronunciation));
		//
	}

	private static void playAudio(final Object pronunciation) throws Throwable {
		try {
			METHOD_PLAY_AUDIO.invoke(null, pronunciation);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSaveAudio() throws Throwable {
		//
		// org.springframework.context.support.OnlineNHKJapanesePronunciationAccentGui$Pronounication.audioUrls
		//
		if (pronunciation != null) {
			//
			pronunciation.setAudioUrls((Map) map);
			//
		} // if
			//
		if (ih != null) {
			//
			ih.size = Integer.valueOf(0);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> saveAudio(true, pronunciation, null));
		//
		if (ih != null) {
			//
			ih.size = Integer.valueOf(1);
			//
			ih.entrySet = Collections.singleton(entry);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> saveAudio(true, pronunciation, null));
		//
		Assertions.assertDoesNotThrow(() -> saveAudio(true, pronunciation, EMPTY));
		//
	}

	private static void saveAudio(final boolean headless, final Object pronunciation, final Object audioFormat)
			throws Throwable {
		try {
			METHOD_SAVE_AUDIO.invoke(null, headless, pronunciation, audioFormat);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPronounicationChanged() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> pronounicationChanged(pronunciation, null));
		//
		if (pronunciation != null) {
			//
			FieldUtils.writeDeclaredField(pronunciation, "audioUrls", map, true);
			//
		} // if
			//
		if (ih != null) {
			//
			ih.isEmpty = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> pronounicationChanged(pronunciation, null));
		//
	}

	private static void pronounicationChanged(final Object pronunciation,
			final MutableComboBoxModel<String> mcbmAudioFormat) throws Throwable {
		try {
			METHOD_PRONOUNICATION_CHANGED.invoke(null, pronunciation, mcbmAudioFormat);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredField() throws Throwable {
		//
		Assertions.assertNull(getDeclaredField(null, null));
		//
	}

	private static Field getDeclaredField(final Class<?> instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_FIELD.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForName() throws Throwable {
		//
		Assertions.assertNull(forName(null));
		//
		Assertions.assertNull(forName(Integer.toString(ONE)));
		//
	}

	private static Class<?> forName(final String className) throws Throwable {
		try {
			final Object obj = METHOD_FOR_NAME.invoke(null, className);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOpenStream() throws Throwable {
		//
		Assertions.assertNotNull(openStream(new File("pom.xml").toURI().toURL()));
		//
		Assertions.assertNull(openStream(cast(URL.class, Narcissus.allocateInstance(URL.class))));
		//
	}

	private static InputStream openStream(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_OPEN_STREAM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPlay() throws Throwable {
		//
		try (final ByteArrayInputStream bais = new ByteArrayInputStream("".getBytes())) {
			//
			final FileSystem fs = FileSystems.getDefault();
			//
			if (Objects.equals("sun.nio.fs.WindowsFileSystemProvider",
					getName(getClass(fs != null ? fs.provider() : null)))) {
				//
				Assertions.assertDoesNotThrow(() -> play(new Player(bais)));
				//
			} else {
				//
				AssertionsUtil.assertThrowsAndEquals(JavaLayerException.class, "", () -> play(new Player(bais)));
				//
			} // if
				//
		} // try
			//
	}

	private static void play(final Player instance) throws Throwable {
		try {
			METHOD_PLAY.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddActionListener() {
		//
		Assertions.assertDoesNotThrow(() -> addActionListener(null, (AbstractButton[]) null));
		//
		Assertions.assertDoesNotThrow(() -> addActionListener(null, (AbstractButton) null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> addActionListener(null, new JButton()));
			//
		} // if
			//
	}

	private static void addActionListener(final ActionListener actionListener, final AbstractButton... bs)
			throws Throwable {
		try {
			METHOD_ADD_ACTION_LISTENER.invoke(null, actionListener, bs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get((Map<?, ?>) null, null));
		//
		Assertions.assertNull(get((Field) null, null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertNull(get(map, null));
			//
		} // if
			//
	}

	private static <V> V get(final Map<?, V> instance, final Object key) throws Throwable {
		try {
			return (V) METHOD_GET_MAP.invoke(null, instance, key);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Object get(final Field field, final Object instance) throws Throwable {
		try {
			return METHOD_GET_FIELD.invoke(null, field, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetText() {
		//
		Assertions.assertDoesNotThrow(() -> setText(null, null));
		//
		Assertions.assertDoesNotThrow(() -> setText(jLabel, null));
		//
	}

	private static void setText(final JLabel instance, final String text) throws Throwable {
		try {
			METHOD_SET_TEXT.invoke(null, instance, text);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetForeground() {
		//
		Assertions.assertDoesNotThrow(() -> setForeground(null, null));
		//
		Assertions.assertDoesNotThrow(() -> setForeground(jLabel, null));
		//
	}

	private static void setForeground(final Component instance, final Color color) throws Throwable {
		try {
			METHOD_SET_FORE_GROUND.invoke(null, instance, color);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetListCellRendererComponent() throws Throwable {
		//
		Assertions.assertNull(getListCellRendererComponent(null, null, null, 0, false, false));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertNull(getListCellRendererComponent(Reflection.newProxy(ListCellRenderer.class, ih), null,
					null, 0, false, false));
			//
		} // if
			//
	}

	private static <E> Component getListCellRendererComponent(final ListCellRenderer<E> instance,
			final JList<? extends E> list, final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) throws Throwable {
		try {
			final Object obj = METHOD_GET_LIST_CELL_RENDERER_COMPONENT.invoke(null, instance, list, value, index,
					isSelected, cellHasFocus);
			if (obj == null) {
				return null;
			} else if (obj instanceof Component) {
				return (Component) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSaveFile() {
		//
		Assertions.assertDoesNotThrow(() -> saveFile(null, null));
		//
	}

	private static void saveFile(final File file, final String url) throws Throwable {
		try {
			METHOD_SAVE_FILE.invoke(null, file, url);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContainsKey() throws Throwable {
		//
		if (ih != null) {
			//
			ih.containsKey = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertFalse(containsKey(map, null));
		//
		if (ih != null) {
			//
			ih.containsKey = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertTrue(containsKey(map, null));
		//
	}

	private static boolean containsKey(final Map<?, ?> instance, final Object key) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS_KEY.invoke(null, instance, key);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIif() throws Throwable {
		//
		Assertions.assertNull(iif(true, null, null));
		//
		Assertions.assertNull(iif(false, null, null));
		//
	}

	private static <T> T iif(final boolean condition, final T trueValue, final T falseValue) throws Throwable {
		try {
			return (T) METHOD_IIF.invoke(null, condition, trueValue, falseValue);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName(null));
		//
	}

	private static String getName(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSort() {
		//
		Assertions.assertDoesNotThrow(() -> sort(null, null));
		//
		Assertions.assertDoesNotThrow(() -> sort(Collections.emptyList(), null));
		//
		Assertions.assertDoesNotThrow(() -> sort(new ArrayList<>(Collections.nCopies(2, null)), null));
		//
		if (ih != null) {
			//
			ih.size = Integer.valueOf(0);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> sort(Reflection.newProxy(List.class, ih), null));
		//
	}

	private static <E> void sort(final List<E> instance, final Comparator<? super E> comparator) throws Throwable {
		try {
			METHOD_SORT.invoke(null, instance, comparator);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateImageFormatComparator() throws Throwable {
		//
		final List<String> list = Arrays.asList("bmp", "jpeg", "tiff", "png", "wbmp", "gif");
		//
		sort(list, createImageFormatComparator(Arrays.asList("png", "jpeg", "gif")));
		//
		Assertions.assertEquals("png,jpeg,gif,bmp,tiff,wbmp", StringUtils.join(list, ','));
		//
	}

	private static Comparator<String> createImageFormatComparator(final List<?> imageFormatOrders) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_IMAGE_FORMAT_COMPARATOR.invoke(null, imageFormatOrders);
			if (obj == null) {
				return null;
			} else if (obj instanceof Comparator) {
				return (Comparator) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAnnotationPresent() throws Throwable {
		//
		Assertions.assertFalse(isAnnotationPresent(null, null));
		//
		Assertions.assertFalse(isAnnotationPresent(Object.class, null));
		//
	}

	private static boolean isAnnotationPresent(final AnnotatedElement instance,
			final Class<? extends Annotation> annotationClass) throws Throwable {
		try {
			final Object obj = METHOD_IS_ANNOTATION_PRESENT.invoke(null, instance, annotationClass);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAnnotation() throws Throwable {
		//
		Assertions.assertNull(getAnnotation(null, null));
		//
		Assertions.assertNull(getAnnotation(Object.class, null));
		//
	}

	private static <T extends Annotation> T getAnnotation(final AnnotatedElement instance,
			final Class<T> annotationClass) throws Throwable {
		try {
			final Object obj = METHOD_GET_ANNOTATION.invoke(null, instance, annotationClass);
			if (obj == null) {
				return null;
			} else if (annotationClass != null && annotationClass.isInstance(obj)) {
				return annotationClass.cast(obj);
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPreferredSize() throws Throwable {
		//
		Assertions.assertNull(getPreferredSize(null));
		//
	}

	private static Dimension getPreferredSize(final Component instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PREFERRED_SIZE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Dimension) {
				return (Dimension) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testStream() throws Throwable {
		//
		Assertions.assertNull(stream(null));
		//
	}

	private static <E> Stream<E> stream(final Collection<E> instance) throws Throwable {
		try {
			final Object obj = METHOD_STREAM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(null, null));
		//
		Assertions.assertNull(filter(Stream.empty(), null));
		//
		final Stream<?> stream = Reflection.newProxy(Stream.class, ih);
		//
		Assertions.assertSame(stream, filter(stream, null));
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_FILTER.invoke(null, instance, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToList() throws Throwable {
		//
		Assertions.assertNull(toList(null));
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_LIST.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetPreferredSize() {
		//
		Assertions.assertDoesNotThrow(() -> setPreferredSize(null, null));
		//
	}

	private static void setPreferredSize(final Component instance, final Dimension preferredSize) throws Throwable {
		try {
			METHOD_SET_PREFERRED_SIZE.invoke(null, instance, preferredSize);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMax() throws Throwable {
		//
		Assertions.assertNull(max(null, null));
		//
		Assertions.assertNull(max(Stream.empty(), null));
		//
		Assertions.assertNull(max(Reflection.newProxy(Stream.class, ih), null));
		//
	}

	private static <T> Optional<T> max(final Stream<T> instance, final Comparator<? super T> comparator)
			throws Throwable {
		try {
			final Object obj = METHOD_MAX.invoke(null, instance, comparator);
			if (obj == null) {
				return null;
			} else if (obj instanceof Optional) {
				return (Optional) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToArray() throws Throwable {
		//
		Assertions.assertNull(toArray(null, null));
		//
		Collection<?> collection = Collections.emptyList();
		//
		Assertions.assertNull(toArray(collection, null));
		//
		Assertions.assertNull(toArray(collection = Reflection.newProxy(Collection.class, ih), null));
		//
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) throws Throwable {
		try {
			return (T[]) METHOD_TO_ARRAY.invoke(null, instance, array);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIH() throws Throwable {
		//
		final Class<?> clz = Class
				.forName("org.springframework.context.support.OnlineNHKJapanesePronunciationAccentGui$IH");
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler ih = cast(InvocationHandler.class,
				constructor != null ? constructor.newInstance() : null);
		//
		AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{suppressed=[]}", () -> invoke(ih, null, null, null));
		//
		final Transferable transferable = Reflection.newProxy(Transferable.class, ih);
		//
		Assertions.assertNull(invoke(ih, transferable,
				Transferable.class.getDeclaredMethod("getTransferData", DataFlavor.class), null));
		//
		Assertions.assertNull(
				invoke(ih, transferable, Transferable.class.getDeclaredMethod("getTransferDataFlavors"), null));
		//
	}

	private static Object invoke(final InvocationHandler instance, final Object proxy, final Method method,
			final Object[] args) throws Throwable {
		return instance != null ? instance.invoke(proxy, method, args) : null;
	}

}