package org.springframework.context.support;

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
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.MutableComboBoxModel;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Consumers;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;
import com.google.common.util.concurrent.Runnables;

import domain.Pronunciation;
import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class OnlineNHKJapanesePronunciationAccentGuiTest {

	private static final String EMPTY = "";

	private static final int ONE = 1;

	private static Method METHOD_TEST_AND_APPLY, METHOD_GET_WIDTH, METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_SET_CONTENTS,
			METHOD_SET_PITCH_ACCENT_IMAGE_TO_SYSTEM_CLIPBOARD_CONTENTS, METHOD_SAVE_PITCH_ACCENT_IMAGE,
			METHOD_PLAY_AUDIO, METHOD_SAVE_AUDIO, METHOD_PRONOUNICATION_CHANGED, METHOD_GET_FIELD, METHOD_SAVE_FILE,
			METHOD_IIF, METHOD_SORT, METHOD_CREATE_IMAGE_FORMAT_COMPARATOR, METHOD_SET_PREFERRED_SIZE, METHOD_MAX,
			METHOD_TEST_AND_RUN, METHOD_TEST_AND_ACCEPT3, METHOD_TEST_AND_ACCEPT4, METHOD_REMOVE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = OnlineNHKJapanesePronunciationAccentGui.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_WIDTH = clz.getDeclaredMethod("getWidth", Dimension2D.class)).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
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
		(METHOD_GET_FIELD = clz.getDeclaredMethod("get", Field.class, Object.class)).setAccessible(true);
		//
		(METHOD_SAVE_FILE = clz.getDeclaredMethod("saveFile", File.class, String.class)).setAccessible(true);
		//
		(METHOD_IIF = clz.getDeclaredMethod("iif", Boolean.TYPE, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_SORT = clz.getDeclaredMethod("sort", List.class, Comparator.class)).setAccessible(true);
		//
		(METHOD_CREATE_IMAGE_FORMAT_COMPARATOR = clz.getDeclaredMethod("createImageFormatComparator", List.class))
				.setAccessible(true);
		//
		(METHOD_SET_PREFERRED_SIZE = clz.getDeclaredMethod("setPreferredSize", Component.class, Dimension.class))
				.setAccessible(true);
		//
		(METHOD_MAX = clz.getDeclaredMethod("max", Stream.class, Comparator.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_RUN = clz.getDeclaredMethod("testAndRun", Boolean.TYPE, Runnable.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3 = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_REMOVE = clz.getDeclaredMethod("remove", List.class, Integer.TYPE)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Integer size = null;

		private Object key, value = null;

		private Set<Entry<?, ?>> entrySet = null;

		private Iterator<?> iterator = null;

		private Boolean hasNext, isEmpty = null;

		private Optional<?> max = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Util.getReturnType(method), Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Iterable) {
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
					return Util.hasNext(iterator);
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "isEmpty")) {
					//
					return isEmpty;
					//
				} else if (Objects.equals(methodName, "size")) {
					//
					return size;
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
				} // if
					//
			} else if (proxy instanceof Stream && Objects.equals(methodName, "max")) {
				//
				return max;
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
			final String methodName = Util.getName(thisMethod);
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

	private Entry<?, ?> entry = null;

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
			instance = Util.cast(OnlineNHKJapanesePronunciationAccentGui.class,
					Narcissus.allocateInstance(OnlineNHKJapanesePronunciationAccentGui.class));
			//
		} //
			//
		mh = new MH();
		//
		pronunciation = new Pronunciation();
		//
		entry = Reflection.newProxy(Entry.class, ih = new IH());
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
		Assertions.assertEquals(Arrays.asList(Util.toString(object)), get(imageFormatOrders, instance));
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
				"{localizedMessage=class java.util.LinkedHashMap, message=class java.util.LinkedHashMap}",
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

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
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
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSystemClipboard() throws Throwable {
		//
		Assertions.assertNull(getSystemClipboard(null));
		//
		Assertions.assertNull(getSystemClipboard(ProxyUtil.createProxy(Toolkit.class, mh)));
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
			throw new Throwable(Util.toString(getClass(obj)));
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
	void testSetPitchAccentImageToSystemClipboardContents() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> setPitchAccentImageToSystemClipboardContents(pronunciation));
		//
		// org.springframework.context.support.OnlineNHKJapanesePronunciationAccentGui$Pronounication.pitchAccentImage
		//
		if (pronunciation != null) {
			//
			pronunciation.setPitchAccentImage(
					Util.cast(BufferedImage.class, Narcissus.allocateInstance(BufferedImage.class)));
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
			pronunciation.setPitchAccentImage(
					Util.cast(BufferedImage.class, Narcissus.allocateInstance(BufferedImage.class)));
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
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get((Field) null, null));
		//
	}

	private static Object get(final Field field, final Object instance) throws Throwable {
		try {
			return METHOD_GET_FIELD.invoke(null, field, instance);
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
		Assertions.assertEquals("png,jpeg,gif,bmp,tiff,wbmp", collect(Util.stream(list), Collectors.joining(",")));
		//
	}

	private static <T, R, A> R collect(final Stream<T> instance, final Collector<? super T, A, R> collector) {
		//
		return instance != null && (collector != null || Proxy.isProxyClass(getClass(instance)))
				? instance.collect(collector)
				: null;
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
			throw new Throwable(Util.toString(getClass(obj)));
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
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndRun() {
		//
		Assertions.assertDoesNotThrow(() -> testAndRun(true, null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> testAndRun(true, Runnables.doNothing()));
			//
		} else {
			//
			Assertions.assertDoesNotThrow(() -> testAndRun(false, null));
			//
		} // if
			//
	}

	private static void testAndRun(final boolean b, final Runnable runnable) throws Throwable {
		try {
			METHOD_TEST_AND_RUN.invoke(null, b, runnable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, Consumers.nop()));
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, null, null));
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, null, (a, b) -> {
			}));
			//
		} else {
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysFalse(), null, null));
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, null, null));
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> false, null, null, null));
			//
		} // if
			//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final Consumer<T> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT3.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiConsumer<T, U> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT4.invoke(null, predicate, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testemove() {
		//
		Assertions.assertDoesNotThrow(() -> remove(null, 0));
		//
	}

	private static void remove(final List<?> instance, final int index) throws Throwable {
		try {
			METHOD_REMOVE.invoke(null, instance, index);
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
		final InvocationHandler ih = Util.cast(InvocationHandler.class,
				constructor != null ? constructor.newInstance() : null);
		//
		AssertionsUtil.assertThrowsAndEquals(Throwable.class, "{}", () -> invoke(ih, null, null, null));
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