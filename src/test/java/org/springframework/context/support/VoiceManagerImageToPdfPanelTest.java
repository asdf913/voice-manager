package org.springframework.context.support;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.ListModel;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Consumers;
import org.meeuw.functional.Predicates;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;
import org.springframework.core.env.PropertyResolver;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class VoiceManagerImageToPdfPanelTest {

	private static Method METHOD_GET_WIDTH, METHOD_GET_HEIGHT, METHOD_IS_PD_IMAGE, METHOD_GET_ANNOTATIONS,
			METHOD_GET_MESSAGE, METHOD_WRITE_VOICE_TO_FILE, METHOD_SAVE, METHOD_CREATE_PD_EMBEDDED_FILE,
			METHOD_TEST_AND_ACCEPT, METHOD_GET_FONT_NAME_3, METHOD_GET_FONT_NAME_2, METHOD_GET_INDEX, METHOD_ADD_IMAGE,
			METHOD_SIZE = null;

	private static Class<?> CLASS_OBJECT_MAP = null;

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
		(METHOD_SAVE = clz.getDeclaredMethod("save", PDDocument.class, File.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_CREATE_PD_EMBEDDED_FILE = clz.getDeclaredMethod("createPDEmbeddedFile", PDDocument.class, Path.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_GET_FONT_NAME_3 = clz.getDeclaredMethod("getFontName", String.class, PropertyResolver.class, Map.class))
				.setAccessible(true);
		//
		(METHOD_GET_FONT_NAME_2 = clz.getDeclaredMethod("getFontName", FontName[].class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_INDEX = clz.getDeclaredMethod("getIndex", ListModel.class, Object.class)).setAccessible(true);
		//
		(METHOD_ADD_IMAGE = clz.getDeclaredMethod("addImage",
				CLASS_OBJECT_MAP = Util
						.forName("org.springframework.context.support.VoiceManagerImageToPdfPanel$ObjectMap"),
				Float.TYPE, Float.TYPE, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_SIZE = clz.getDeclaredMethod("size", COSDictionary.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Integer width, height = null;

		private Map<?, ?> properties = null;

		private List<?> list = null;

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
			} else if (proxy instanceof PropertyResolver) {
				//
				if (Objects.equals(methodName, "containsProperty") && args != null && args.length > 0) {
					//
					return Util.containsKey(properties = ObjectUtils.getIfNull(properties, LinkedHashMap::new),
							args[0]);
					//
				} else if (Objects.equals(methodName, "getProperty") && args != null && args.length > 0) {
					//
					return Util.get(properties = ObjectUtils.getIfNull(properties, LinkedHashMap::new), args[0]);
					//
				} // if
					//
			} else if (proxy instanceof ListModel) {
				//
				if (Objects.equals(methodName, "getSize")) {
					//
					return IterableUtils.size(list = ObjectUtils.getIfNull(list, ArrayList::new));
					//
				} else if (Objects.equals(methodName, "getElementAt") && args != null && args.length > 0
						&& args[0] instanceof Integer i && i != null) {
					//
					return IterableUtils.get(list = ObjectUtils.getIfNull(list, ArrayList::new), i.intValue());
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
			} else if (self instanceof PDDocument && Objects.equals(methodName, "save")) {
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
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManagerImageToPdfPanel instance = null;

	private PDImage pdImage = null;

	private IH ih = null;

	private MH mh = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		instance = new VoiceManagerImageToPdfPanel();
		//
		pdImage = Reflection.newProxy(PDImage.class, ih = new IH());
		//
		mh = new MH();
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
		final AbstractButton btnImageFile = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnImageFile", btnImageFile, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnImageFile, 0, null)));
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
		Object invoke = null;
		//
		String name, toString = null;
		//
		if (!Objects.equals(OperatingSystem.WINDOWS, OperatingSystemUtil.getOperatingSystem())) {
			//
			Util.put(System.getProperties(), "org.springframework.context.support.SpeechApi.isInstalled",
					Boolean.toString(false));
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
			name = Util.getName(m);
			//
			if (Util.isStatic(m)) {
				//
				invoke = Narcissus.invokeStaticMethod(m, Util.toArray(collection));
				//
				toString = Objects.toString(m);
				//
				if (Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE, Float.TYPE), Util.getReturnType(m))
						|| Boolean.logicalAnd(Objects.equals(name,
								"getPDImageXObjectCreateFromFileByContentDetectFileTypeMethodAndAllowedFileTypes"),
								Arrays.equals(parameterTypes, new Class<?>[] {}))
						|| Boolean.logicalAnd(Objects.equals(name, "isPDImage"),
								Arrays.equals(parameterTypes, new Class<?>[] { byte[].class }))) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
					//
			} else {
				//
				Util.forEach(Util.filter(
						Arrays.stream(Util.getDeclaredFields(Util.getClass(
								instance = ObjectUtils.getIfNull(instance, VoiceManagerImageToPdfPanel::new)))),
						x -> !Util.isStatic(x)), x -> {
							Narcissus.setField(instance, x, null);
						});
				//
				invoke = Narcissus.invokeMethod(instance, m, Util.toArray(collection));
				//
				if (Boolean.logicalAnd(Objects.equals(name, "getTitle"),
						Arrays.equals(parameterTypes, new Class<?>[] {}))) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
					//
			} // if
				//
		} // for
			//
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

	@Test
	void testSave() throws Throwable {
		//
		final PDDocument pdDocument = ProxyUtil.createProxy(PDDocument.class, mh = new MH());
		//
		Assertions.assertDoesNotThrow(() -> save(pdDocument, null, null));
		//
		if (mh != null) {
			//
			mh.ioException = new IOException();
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> save(pdDocument, null, null));
		//
	}

	private static void save(final PDDocument instance, final File file, final Consumer<IOException> consumer)
			throws Throwable {
		try {
			METHOD_SAVE.invoke(null, instance, file, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreatePDEmbeddedFile() throws Throwable {
		//
		Assertions.assertEquals(null, createPDEmbeddedFile(new PDDocument(), null, null));
		//
	}

	private static PDEmbeddedFile createPDEmbeddedFile(final PDDocument pdDocument, final Path path,
			final Consumer<IOException> consumer) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_PD_EMBEDDED_FILE.invoke(null, pdDocument, path, consumer);
			if (obj == null) {
				return null;
			} else if (obj instanceof PDEmbeddedFile) {
				return (PDEmbeddedFile) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		final BiPredicate<?, ?> biAlwaysTrue = Predicates.biAlwaysTrue();
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(biAlwaysTrue, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(biAlwaysTrue, null, null, Consumers.biNop()));
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, instance, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFontName() throws Throwable {
		//
		Assertions.assertNull(getFontName(new FontName[] { null }, null));
		//
		final FontName fontName = FontName.TIMES_ROMAN;
		//
		Assertions.assertThrows(IllegalStateException.class,
				() -> getFontName(new FontName[] { fontName, fontName }, Util.name(fontName)));
		//
		final String key = "org.springframework.context.support.VoiceManagerPdfPanel.fontName";
		//
		Assertions.assertSame(fontName, getFontName(key, null, Collections.singletonMap(key, Util.name(fontName))));
		//
		Assertions.assertSame(fontName,
				getFontName(key, null, Collections.singletonMap(key, fontName != null ? fontName.getName() : null)));
		//
		Assertions.assertSame(fontName, getFontName(key, null, Collections.singletonMap(key,
				fontName != null ? StringUtils.substring(fontName.getName(), 0, 7) : null)));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> getFontName(key, null, Collections.singletonMap(key,
				fontName != null ? StringUtils.substring(fontName.getName(), 0, 6) : null)));
		//
		if (ih != null && (ih.properties = ObjectUtils.getIfNull(ih.properties, LinkedHashMap::new)) != null) {
			//
			MethodUtils.invokeMethod(ih.properties, "put", key, Util.name(fontName));
			//
		} // if
			//
		Assertions.assertSame(fontName, getFontName(key, Reflection.newProxy(PropertyResolver.class, ih), null));
		//
	}

	private static FontName getFontName(final String key, final PropertyResolver propertyResolver, final Map<?, ?> map)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_FONT_NAME_3.invoke(null, key, propertyResolver, map);
			if (obj == null) {
				return null;
			} else if (obj instanceof FontName) {
				return (FontName) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static IValue0<FontName> getFontName(final FontName[] fontNames, final String prefix) throws Throwable {
		try {
			final Object obj = METHOD_GET_FONT_NAME_2.invoke(null, fontNames, prefix);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetIndex() {
		//
		if (ih != null && (ih.list = ObjectUtils.getIfNull(ih.list, ArrayList::new)) != null) {
			//
			Util.addAll(ih.list, Collections.nCopies(2, null));
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class,
				() -> getIndex(Reflection.newProxy(ListModel.class, ih), null));
		//
	}

	private static Integer getIndex(final ListModel<?> instance, final Object object) throws Throwable {
		try {
			final Object obj = METHOD_GET_INDEX.invoke(null, instance, object);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddImage() {
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, Util.cast(InvocationHandler.class, Narcissus
				.allocateInstance(Util.forName("org.springframework.context.support.VoiceManagerImageToPdfPanel$IH"))));
		//
		final Iterable<Method> ms = Util.toList(Util.filter(Arrays.stream(Util.getMethods(CLASS_OBJECT_MAP)),
				m -> Boolean.logicalAnd(Objects.equals(Util.getName(m), "setObject"),
						Arrays.equals(Util.getParameterTypes(m), new Class<?>[] { Class.class, Object.class }))));
		//
		if (IterableUtils.size(ms) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Method m = IterableUtils.size(ms) == 1 ? IterableUtils.get(ms, 0) : null;
		//
		Util.forEach(Stream.of(PDDocument.class, PDRectangle.class, PDPageContentStream.class, URL.class, File.class,
				VoiceManagerImageToPdfPanel.class), clz -> {
					//
					if (!Util.isStatic(m)) {
						//
						Narcissus.invokeMethod(objectMap, m, clz, null);
						//
					} // if
						//
				});
		//
		Assertions.assertDoesNotThrow(() -> addImage(objectMap, 0, 0, 0));
		//
	}

	private static void addImage(final Object objectMap, final float pageWidth, final float size, final int textHeight)
			throws Throwable {
		try {
			METHOD_ADD_IMAGE.invoke(null, objectMap, pageWidth, size, textHeight);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSize() throws Throwable {
		//
		Assertions.assertEquals(0,
				size(Util.cast(COSDictionary.class, Narcissus.allocateInstance(COSDictionary.class))));
		//
	}

	private static int size(final COSDictionary instance) throws Throwable {
		try {
			final Object obj = METHOD_SIZE.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
}