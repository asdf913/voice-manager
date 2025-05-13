package org.springframework.context.support;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import j2html.tags.Tag;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class VoiceManagerImageToPdfPanelTest {

	private static Method METHOD_GET_WIDTH, METHOD_GET_HEIGHT, METHOD_IS_PD_IMAGE, METHOD_GET_ANNOTATIONS,
			METHOD_GET_MESSAGE, METHOD_WRITE_VOICE_TO_FILE = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = VoiceManagerImageToPdfPanel.class;
		//
		(METHOD_GET_WIDTH = clz.getDeclaredMethod("getWidth", PDImage.class)).setAccessible(true);
		//
		(METHOD_GET_HEIGHT = clz.getDeclaredMethod("getHeight", PDImage.class)).setAccessible(true);
		//
		(METHOD_IS_PD_IMAGE = clz.getDeclaredMethod("isPDImage", byte[].class)).setAccessible(true);
		//
		(METHOD_GET_ANNOTATIONS = clz.getDeclaredMethod("getAnnotations", PDPage.class, Consumer.class))
				.setAccessible(true);
		//
		(METHOD_GET_MESSAGE = clz.getDeclaredMethod("getMessage", Throwable.class)).setAccessible(true);
		//
		(METHOD_WRITE_VOICE_TO_FILE = clz.getDeclaredMethod("writeVoiceToFile", SpeechApi.class, String.class,
				String.class, Integer.TYPE, Integer.TYPE, Map.class, File.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Integer width, height = null;

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
			if (proxy instanceof PDImage) {
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

		private IOException ioException = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof PDPage) {
				//
				if (Objects.equals(methodName, "getAnnotations")) {
					//
					if (ioException != null) {
						//
						throw ioException;
						//
					} // if
						//
					return null;
					//
				} // if
					//
			} else if (self instanceof Throwable && Objects.equals(methodName, "getMessage")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManagerImageToPdfPanel instance = null;

	private PDImage pdImage = null;

	private IH ih = null;

	private MH mh = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerImageToPdfPanel();
		//
		pdImage = Reflection.newProxy(PDImage.class, ih = new IH());
		//
		mh = new MH();
		//
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
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(null));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerImageToPdfPanel.class.getDeclaredMethods();
		//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Method m = null;
		//
		Object invokeStaticMethod = null;
		//
		String name, toString = null;
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
			for (int j = 0; (parameterTypes = Util.getParameterTypes(m)) != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.TRUE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Float.TYPE)) {
					//
					Util.add(collection, Float.valueOf(0));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // for
				//
			if (Util.isStatic(m)) {
				//
				invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(collection));
				//
				toString = Objects.toString(m);
				//
				if (Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE, Float.TYPE), Util.getReturnType(m))
						|| Boolean.logicalAnd(Objects.equals(name = Util.getName(m),
								"getPDImageXObjectCreateFromFileByContentDetectFileTypeMethodAndAllowedFileTypes"),
								Arrays.equals(parameterTypes, new Class<?>[] {}))
						|| Boolean.logicalAnd(Objects.equals(name, "isPDImage"),
								Arrays.equals(parameterTypes, new Class<?>[] { byte[].class }))) {
					//
					Assertions.assertNotNull(invokeStaticMethod, toString);
					//
				} else {
					//
					Assertions.assertNull(invokeStaticMethod, toString);
					//
				} // if
					//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(
						instance = ObjectUtils.getIfNull(instance, VoiceManagerImageToPdfPanel::new), m,
						toArray(collection)), toString);
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
	void testGetWidth() throws Throwable {
		//
		final int zero = 0;
		//
		if (ih != null) {
			//
			ih.width = zero;
			//
		} // if
			//
		Assertions.assertEquals(zero, getWidth(Reflection.newProxy(PDImage.class, ih)));
		//
	}

	private static int getWidth(final PDImage instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_WIDTH.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.getName(Util.getClass(instance)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetHeight() throws Throwable {
		//
		final int zero = 0;
		//
		if (ih != null) {
			//
			ih.height = zero;
			//
		} // if
			//
		Assertions.assertEquals(zero, getHeight(pdImage));
		//
	}

	private static int getHeight(final PDImage instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_HEIGHT.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.getName(Util.getClass(instance)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsPDImage() throws Throwable {
		//
		final BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		//
		Assertions.assertEquals(Boolean.FALSE, isPDImage(new byte[] {}));
		//
		Assertions.assertEquals(Boolean.FALSE, isPDImage(new byte[] { 0 }));
		//
		byte[] bs = null;
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			ImageIO.write(bi, "png", baos);
			//
			bs = baos.toByteArray();
			//
		} // try
			//
		Assertions.assertEquals(Boolean.TRUE, isPDImage(bs));
		//
	}

	private static Boolean isPDImage(final byte[] bs) throws Throwable {
		try {
			final Object obj = METHOD_IS_PD_IMAGE.invoke(null, bs);
			if (obj == null) {
				return null;
			} else if (obj instanceof Boolean) {
				return (Boolean) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAnnotations() throws Throwable {
		//
		if (mh != null) {
			//
			mh.ioException = new IOException();
			//
		} // if
			//
		Assertions.assertNull(getAnnotations(ProxyUtil.createProxy(PDPage.class, mh), null));
		//
	}

	private static List<PDAnnotation> getAnnotations(final PDPage instance, final Consumer<IOException> consumer)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_ANNOTATIONS.invoke(null, instance, consumer);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMsssage() throws Throwable {
		//
		Assertions.assertNull(getMessage(ProxyUtil.createProxy(Throwable.class, mh)));
		//
	}

	private static String getMessage(final Throwable instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_MESSAGE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testWriteVoiceToFile() {
		//
		Assertions.assertDoesNotThrow(
				() -> writeVoiceToFile(Reflection.newProxy(SpeechApi.class, ih), null, null, 0, 0, null, null));
		//
	}

	private static void writeVoiceToFile(final SpeechApi instance, final String text, final String voiceId,
			final int rate, final int volume, final Map<String, Object> map, final File file) throws Throwable {
		try {
			METHOD_WRITE_VOICE_TO_FILE.invoke(null, instance, text, voiceId, rate, volume, map, file);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}