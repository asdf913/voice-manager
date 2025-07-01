package org.springframework.context.support;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.MutableComboBoxModel;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Suppliers;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class VoiceManagerOjadAccentPanelTest {

	private static final String EMPTY = "";

	private static Class<?> CLASS_TEXT_AND_IMAGE = null;

	private static Method METHOD_GET_FILE_EXTENSIONS, METHOD_FIND_MATCH, METHOD_QUERY_SELECTOR_ALL,
			METHOD_QUERY_SELECTOR, METHOD_SET_ICON, METHOD_PACK, METHOD_GET_KANJI, METHOD_GET_HEIGHT,
			METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_GET_SELECTED_ITEM, METHOD_LENGTH, METHOD_TO_TEXT_AND_IMAGES,
			METHOD_TO_TEXT_AND_IMAGES1, METHOD_TO_TEXT_AND_IMAGES2, METHOD_TO_BYTE_ARRAY, METHOD_GET_IF_NULL = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = VoiceManagerOjadAccentPanel.class;
		//
		(METHOD_GET_FILE_EXTENSIONS = clz.getDeclaredMethod("getFileExtensions", ContentInfo.class))
				.setAccessible(true);
		//
		(METHOD_FIND_MATCH = clz.getDeclaredMethod("findMatch", ContentInfoUtil.class, byte[].class))
				.setAccessible(true);
		//
		(METHOD_QUERY_SELECTOR_ALL = clz.getDeclaredMethod("querySelectorAll", Page.class, String.class))
				.setAccessible(true);
		//
		(METHOD_QUERY_SELECTOR = clz.getDeclaredMethod("querySelector", ElementHandle.class, String.class))
				.setAccessible(true);
		//
		(METHOD_SET_ICON = clz.getDeclaredMethod("setIcon", JLabel.class, Icon.class)).setAccessible(true);
		//
		(METHOD_PACK = clz.getDeclaredMethod("pack", Window.class)).setAccessible(true);
		//
		(METHOD_GET_KANJI = clz.getDeclaredMethod("getKanji",
				CLASS_TEXT_AND_IMAGE = Util
						.forName("org.springframework.context.support.VoiceManagerOjadAccentPanel$TextAndImage")))
				.setAccessible(true);
		//
		(METHOD_GET_HEIGHT = clz.getDeclaredMethod("getHeight", Dimension2D.class)).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_GET_SELECTED_ITEM = clz.getDeclaredMethod("getSelectedItem", JComboBox.class)).setAccessible(true);
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", Object[].class)).setAccessible(true);
		//
		(METHOD_TO_TEXT_AND_IMAGES = clz.getDeclaredMethod("toTextAndImages", Iterable.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_TO_TEXT_AND_IMAGES1 = clz.getDeclaredMethod("toTextAndImages1", Iterable.class, String.class,
				Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_TEXT_AND_IMAGES2 = clz.getDeclaredMethod("toTextAndImages2", Iterable.class, String.class,
				Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_BYTE_ARRAY = clz.getDeclaredMethod("toByteArray", RenderedImage.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_IF_NULL = clz.getDeclaredMethod("getIfNull", Object.class, Iterable.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final Class<?> returnType = Util.getReturnType(method);
			//
			if (Objects.equals(returnType, Integer.TYPE)) {
				//
				return 0;
				//
			} else if (Objects.equals(returnType, Void.TYPE)) {
				//
				return null;
				//
			} else if (method != null && method.getParameterCount() == 0
					&& Objects.equals(returnType, method.getDeclaringClass())) {
				//
				return proxy;
				//
			} // if
				//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof ElementHandle) {
				//
				if (Util.contains(Arrays.asList("screenshot", "textContent", "querySelector"), methodName)) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Playwright) {
				//
				if (Objects.equals(methodName, "chromium")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Browser) {
				//
				if (Objects.equals(methodName, "newPage")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Page && Objects.equals(methodName, "querySelectorAll")) {
				//
				return null;
				//
			} else if (proxy instanceof Iterable && Objects.equals(methodName, "iterator")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static class MH implements MethodHandler {

		private Double height = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof Dimension2D && Objects.equals(methodName, "getHeight")) {
				//
				return height;
				//
			} else if (self instanceof Toolkit && Objects.equals(methodName, "getSystemClipboard")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private IH ih = null;

	private VoiceManagerOjadAccentPanel instance = null;

	private Object textAndImage = null;

	private MH mh = null;

	private ObjectMapper objectMapper = null;

	@BeforeEach
	void beforeEach() {
		//
		ih = new IH();
		//
		instance = new VoiceManagerOjadAccentPanel();
		//
		textAndImage = Narcissus.allocateInstance(CLASS_TEXT_AND_IMAGE);
		//
		mh = new MH();
		//
		objectMapper = new ObjectMapper();
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerOjadAccentPanel.class.getDeclaredMethods();
		//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Method m = null;
		//
		Object[] os = null;
		//
		Object invoke = null;
		//
		String toString = null;
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
			parameterTypes = Util.getParameterTypes(m);
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.TRUE);
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
			invoke = Util.isStatic(m) ? Narcissus.invokeStaticMethod(m, os) : Narcissus.invokeMethod(instance, m, os);
			//
			toString = Util.toString(m);
			//
			if (Util.contains(Arrays.asList(Double.TYPE, Boolean.TYPE, Integer.TYPE), Util.getReturnType(m))) {
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

	@Test
	void testMethodWithOneInterfaceParameter() {
		//
		final Method[] ms = VoiceManagerOjadAccentPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Object invokeStaticMethod = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || (parameterTypes = Util.getParameterTypes(m)) == null
					|| parameterTypes.length != 1 || (parameterType = ArrayUtils.get(parameterTypes, 0)) == null
					|| !Modifier.isInterface(parameterType.getModifiers())
					|| StringUtils.startsWith(Util.getName(m), "lambda$")) {
				//
				continue;
				//
			} // if
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m,
					new Object[] { Reflection.newProxy(parameterType, ih) });
			//
			toString = Util.toString(m);
			//
			if (Objects.equals(Util.getReturnType(m), Boolean.TYPE)
					|| Objects.equals(Util.getReturnType(m), parameterType)) {
				//
				Assertions.assertNotNull(invokeStaticMethod, toString);
				//
			} else {
				//
				Assertions.assertNull(invokeStaticMethod, toString);
				//
			} // if
				//
		} // for
			//
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
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(null));
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(EMPTY, 0, null)));
		//
		// jcbTextAndImage
		//
		final JComboBox<?> jcbTextAndImage = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbTextAndImage", jcbTextAndImage, true);
		//
		final ActionEvent actionEventJcbTextAndImage = new ActionEvent(jcbTextAndImage, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventJcbTextAndImage));
		//
		Util.cast(MutableComboBoxModel.class, jcbTextAndImage.getModel()).addElement(textAndImage);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventJcbTextAndImage));
		//
		// btnCopyImage
		//
		final AbstractButton btnCopyImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyImage", btnCopyImage, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyImage, 0, null)));
		//
		// btnCopyKanji
		//
		final AbstractButton btnCopyKanji = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyKanji", btnCopyKanji, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyKanji, 0, null)));
		//
		// btnCopyHiragana
		//
		final AbstractButton btnCopyHiragana = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyHiragana", btnCopyHiragana, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyHiragana, 0, null)));
		//
	}

	@Test
	void testGetFileExtensions() throws Throwable {
		//
		Assertions.assertNull(
				getFileExtensions(Util.cast(ContentInfo.class, Narcissus.allocateInstance(ContentInfo.class))));
		//
	}

	private static String[] getFileExtensions(final ContentInfo instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_FILE_EXTENSIONS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String[]) {
				return (String[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFindMatch() throws Throwable {
		//
		final ContentInfoUtil cic = new ContentInfoUtil();
		//
		Assertions.assertNull(findMatch(cic, null));
		//
		Assertions.assertSame(ContentInfo.EMPTY_INFO, findMatch(cic, new byte[] {}));
		//
	}

	private static ContentInfo findMatch(final ContentInfoUtil instance, final byte[] bytes) throws Throwable {
		try {
			final Object obj = METHOD_FIND_MATCH.invoke(null, instance, bytes);
			if (obj == null) {
				return null;
			} else if (obj instanceof ContentInfo) {
				return (ContentInfo) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testQuerySelectorAll() throws Throwable {
		//
		Assertions.assertNull(querySelectorAll(Reflection.newProxy(Page.class, ih), null));
		//
	}

	private static List<ElementHandle> querySelectorAll(final Page instance, final String selector) throws Throwable {
		try {
			final Object obj = METHOD_QUERY_SELECTOR_ALL.invoke(null, instance, selector);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testQuerySelector() throws Throwable {
		//
		Assertions.assertNull(querySelector(Reflection.newProxy(ElementHandle.class, ih), null));
		//
	}

	private static ElementHandle querySelector(final ElementHandle instance, final String selector) throws Throwable {
		try {
			final Object obj = METHOD_QUERY_SELECTOR.invoke(null, instance, selector);
			if (obj == null) {
				return null;
			} else if (obj instanceof ElementHandle) {
				return (ElementHandle) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetIcon() {
		//
		Assertions.assertDoesNotThrow(
				() -> setIcon(Util.cast(JLabel.class, Narcissus.allocateInstance(JLabel.class)), null));
		//
	}

	private static void setIcon(final JLabel instance, final Icon icon) throws Throwable {
		try {
			METHOD_SET_ICON.invoke(null, instance, icon);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPack() {
		//
		Assertions.assertDoesNotThrow(() -> pack(Util.cast(Window.class, Narcissus.allocateInstance(Window.class))));
		//
	}

	private static void pack(final Window instance) throws Throwable {
		try {
			METHOD_PACK.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetKanji() throws Throwable {
		//
		Assertions.assertNull(getKanji(textAndImage));
		//
	}

	private static String getKanji(final Object textAndImage) throws Throwable {
		try {
			final Object obj = METHOD_GET_KANJI.invoke(null, textAndImage);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetHeight() throws Throwable {
		//
		final double height = 0;
		//
		mh.height = Double.valueOf(height);
		//
		Assertions.assertEquals(height, getHeight(ProxyUtil.createProxy(Dimension2D.class, mh)));
		//
	}

	private static double getHeight(final Dimension2D instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_HEIGHT.invoke(null, instance);
			if (obj instanceof Double) {
				return ((Double) obj).doubleValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIH() throws Throwable {
		//
		final InvocationHandler invocationHandler = Util.cast(InvocationHandler.class, Narcissus
				.allocateInstance(Util.forName("org.springframework.context.support.VoiceManagerOjadAccentPanel$IH")));
		//
		if (invocationHandler == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertThrows(Throwable.class, () -> invocationHandler.invoke(null, null, null));
		//
		final Transferable transferable = Reflection.newProxy(Transferable.class, invocationHandler);
		//
		Assertions.assertThrows(Throwable.class, () -> invocationHandler.invoke(transferable, null, null));
		//
		Assertions.assertEquals(
				ObjectMapperUtil.writeValueAsString(objectMapper, new DataFlavor[] { DataFlavor.imageFlavor }),
				ObjectMapperUtil.writeValueAsString(objectMapper, invocationHandler.invoke(transferable,
						Narcissus.findMethod(Transferable.class, "getTransferDataFlavors"), null)));
		//
		Assertions.assertNull(invocationHandler.invoke(transferable,
				Narcissus.findMethod(Transferable.class, "getTransferData", DataFlavor.class), null));
		//
	}

	@Test
	void testGetSystemClipboard() throws Throwable {
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSelectedItem() throws Throwable {
		//
		Assertions.assertNull(getSelectedItem(Util.cast(JComboBox.class, Narcissus.allocateInstance(JComboBox.class))));
		//
	}

	private static Object getSelectedItem(final JComboBox<?> instance) throws Throwable {
		try {
			return METHOD_GET_SELECTED_ITEM.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testLength() throws Throwable {
		//
		Assertions.assertEquals(0, length(new Object[] {}));
		//
	}

	private static int length(final Object[] instance) throws Throwable {
		try {
			final Object obj = METHOD_LENGTH.invoke(null, (Object) instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToTextAndImages() throws Throwable {
		//
		if (objectMapper != null) {
			//
			objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
			//
		} // if
			//
		Assertions.assertEquals("[{\"kanji\":null,\"hiragana\":null,\"image\":null}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, toTextAndImages(Collections.singleton(null), null)));
		//
		Assertions.assertEquals(
				"[{\"kanji\":null,\"hiragana\":null,\"image\":null},{\"kanji\":null,\"hiragana\":null,\"image\":null}]",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						toTextAndImages(Collections.nCopies(2, null), Collections.nCopies(2, null))));
		//
	}

	private static Collection<?> toTextAndImages(final Iterable<ElementHandle> ehs, final Iterable<ElementHandle> words)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_TEXT_AND_IMAGES.invoke(null, ehs, words);
			if (obj == null) {
				return null;
			} else if (obj instanceof Collection) {
				return (Collection) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToTextAndImages1() throws Throwable {
		//
		if (objectMapper != null) {
			//
			objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
			//
		} // if
			//
		Assertions.assertEquals("[{\"kanji\":null,\"hiragana\":null,\"image\":null}]",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						toTextAndImages1(Collections.nCopies(2, null), null, Collections.nCopies(1, null))));
		//
	}

	private static Collection<?> toTextAndImages1(final Iterable<ElementHandle> ehs, final String textInput,
			final Iterable<ElementHandle> words) throws Throwable {
		try {
			final Object obj = METHOD_TO_TEXT_AND_IMAGES1.invoke(null, ehs, textInput, words);
			if (obj == null) {
				return null;
			} else if (obj instanceof Collection) {
				return (Collection) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToTextAndImages2() throws Throwable {
		//
		Assertions.assertNull(toTextAndImages2(null, null, Collections.nCopies(1, null)));
		//
		Assertions.assertNull(toTextAndImages2(null, null, Collections.nCopies(2, null)));
		//
	}

	private static Collection<?> toTextAndImages2(final Iterable<ElementHandle> ehs, final String textInput,
			final Iterable<ElementHandle> words) throws Throwable {
		try {
			final Object obj = METHOD_TO_TEXT_AND_IMAGES2.invoke(null, ehs, textInput, words);
			if (obj == null) {
				return null;
			} else if (obj instanceof Collection) {
				return (Collection) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToByteArray() throws Throwable {
		//
		final RenderedImage renderedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		//
		Assertions.assertNull(toByteArray(renderedImage, null));
		//
		Assertions.assertNotNull(toByteArray(renderedImage, "png"));
		//
	}

	private static byte[] toByteArray(final RenderedImage image, final String format) throws Throwable {
		try {
			final Object obj = METHOD_TO_BYTE_ARRAY.invoke(null, image, format);
			if (obj == null) {
				return null;
			} else if (obj instanceof byte[]) {
				return (byte[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetIfNull() throws Throwable {
		//
		Assertions.assertSame(EMPTY, getIfNull(EMPTY, null));
		//
		Assertions.assertSame(EMPTY, getIfNull(null, Collections.singleton(Suppliers.ofInstance(EMPTY))));
		//
	}

	private static <T> T getIfNull(final T object, final Iterable<Supplier<T>> suppliers) throws Throwable {
		try {
			return (T) METHOD_GET_IF_NULL.invoke(null, object, suppliers);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}