package org.springframework.context.support;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.JButton;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.jena.ext.com.google.common.base.Predicates;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.reflect.Reflection;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class OnlineNHKJapanesePronunciationAccentGuiTest {

	private static final String EMPTY = "";

	private static final int ONE = 1;

	private static Method METHOD_CAST, METHOD_GET_SRC_MAP, METHOD_GET_IMAGE_SRCS, METHOD_CREATE_MERGED_BUFFERED_IMAGE,
			METHOD_TEST_AND_APPLY, METHOD_GET_GRAPHICS, METHOD_GET_WIDTH, METHOD_GET_HEIGHT, METHOD_INT_VALUE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = OnlineNHKJapanesePronunciationAccentGui.class;
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_SRC_MAP = clz.getDeclaredMethod("getSrcMap", Element.class)).setAccessible(true);
		//
		(METHOD_GET_IMAGE_SRCS = clz.getDeclaredMethod("getImageSrcs", Element.class)).setAccessible(true);
		//
		(METHOD_CREATE_MERGED_BUFFERED_IMAGE = clz.getDeclaredMethod("createMergedBufferedImage", String.class,
				List.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_GRAPHICS = clz.getDeclaredMethod("getGraphics", Image.class)).setAccessible(true);
		//
		(METHOD_GET_WIDTH = clz.getDeclaredMethod("getWidth", RenderedImage.class)).setAccessible(true);
		//
		(METHOD_GET_HEIGHT = clz.getDeclaredMethod("getHeight", RenderedImage.class)).setAccessible(true);
		//
		(METHOD_INT_VALUE = clz.getDeclaredMethod("intValue", Number.class, Integer.TYPE)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Integer width, height = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof RenderedImage) {
				//
				if (Objects.equals(methodName, "getWidth")) {
					//
					return width;
					//
				} else if (Objects.equals(methodName, "getHeight")) {
					//
					return height;
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

		private Graphics graphics = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof Image) {
				//
				if (Objects.equals(methodName, "getGraphics")) {
					//
					return graphics;
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

	private RenderedImage renderedImage = null;

	@BeforeEach
	void beforeEach() throws ReflectiveOperationException {
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
		} // if
			//
		renderedImage = Reflection.newProxy(RenderedImage.class, ih = new IH());
		//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
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

	@Test
	void testGetSrcMap() throws Throwable {
		//
		Assertions.assertNull(getSrcMap(null));
		//
	}

	private static Map<String, String> getSrcMap(final Element input) throws Throwable {
		try {
			final Object obj = METHOD_GET_SRC_MAP.invoke(null, input);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testGetImageSrcs() throws Throwable {
		//
		Assertions.assertNull(getImageSrcs(null));
		//
	}

	private static List<String> getImageSrcs(final Element element) throws Throwable {
		try {
			final Object obj = METHOD_GET_IMAGE_SRCS.invoke(null, element);
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
	void testCreateMergedBufferedImage() throws Throwable {
		//
		Assertions.assertNull(createMergedBufferedImage(null, null));
		//
		Assertions.assertThrows(UncheckedIOException.class,
				() -> createMergedBufferedImage(null, Collections.singletonList(null)));
		//
	}

	private static BufferedImage createMergedBufferedImage(final String urlString, final List<String> srcs)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MERGED_BUFFERED_IMAGE.invoke(null, urlString, srcs);
			if (obj == null) {
				return null;
			} else if (obj instanceof BufferedImage) {
				return (BufferedImage) obj;
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

	@Test
	void testGetGraphics() throws Throwable {
		//
		Assertions.assertNull(getGraphics(null));
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(Image.class);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		final Object instance = constructor != null ? constructor.newInstance() : null;
		//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(new MH());
			//
		} // if
			//
		Assertions.assertNull(getGraphics(cast(Image.class, instance)));
		//
	}

	private static Graphics getGraphics(final Image instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_GRAPHICS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Graphics) {
				return (Graphics) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetWidth() throws Throwable {
		//
		Assertions.assertNull(getWidth(null));
		//
		if (ih != null) {
			//
			ih.width = ONE;
			//
		} // if
			//
		Assertions.assertEquals(ONE, getWidth(renderedImage));
		//
	}

	private static Integer getWidth(final RenderedImage instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_WIDTH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetHeight() throws Throwable {
		//
		Assertions.assertNull(getHeight(null));
		//
		if (ih != null) {
			//
			ih.height = ONE;
			//
		} // if
			//
		Assertions.assertEquals(ONE, getHeight(renderedImage));
		//
	}

	private static Integer getHeight(final RenderedImage instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_HEIGHT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIntValue() throws Throwable {
		//
		Assertions.assertEquals(ONE, intValue(null, ONE));
		//
		final int zero = 0;
		//
		Assertions.assertEquals(zero, intValue(Integer.valueOf(zero), ONE));
		//
	}

	private static int intValue(final Number instance, final int defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_INT_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(getClass(obj)));
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
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, null, null, null));
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