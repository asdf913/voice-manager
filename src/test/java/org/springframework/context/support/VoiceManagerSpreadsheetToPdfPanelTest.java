package org.springframework.context.support;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDInlineImage;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.poi.hssf.usermodel.HSSFObjectData;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.DeferredSXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFPicture;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFChartSheet;
import org.apache.poi.xssf.usermodel.XSSFDialogsheet;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.d2ab.function.ObjIntFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class VoiceManagerSpreadsheetToPdfPanelTest {

	private static final int ZERO = 0;

	private static Method METHOD_FLOAT_VALUE, METHOD_GET_FIELD_BY_NAME, METHOD_GET_WIDTH_PD_RECTANGLE,
			METHOD_GET_WIDTH_PD_IMAGE, METHOD_GET_HEIGHT_PD_RECTANGLE, METHOD_GET_HEIGHT_PD_IMAGE,
			METHOD_GET_DRAWING_PATRIARCH, METHOD_GET_VOICE, METHOD_GET_PICTURE_DATA, METHOD_GET_DATA_ITERABLE,
			METHOD_TEST_AND_ACCEPT, METHOD_SET_FIELD, METHOD_SAVE, METHOD_GET_ANNOTATIONS = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = VoiceManagerSpreadsheetToPdfPanel.class;
		//
		(METHOD_FLOAT_VALUE = clz.getDeclaredMethod("floatValue", Number.class, Float.TYPE)).setAccessible(true);
		//
		(METHOD_GET_FIELD_BY_NAME = clz.getDeclaredMethod("getFieldByName", Collection.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_WIDTH_PD_RECTANGLE = clz.getDeclaredMethod("getWidth", PDRectangle.class)).setAccessible(true);
		//
		(METHOD_GET_WIDTH_PD_IMAGE = clz.getDeclaredMethod("getWidth", PDImage.class)).setAccessible(true);
		//
		(METHOD_GET_HEIGHT_PD_RECTANGLE = clz.getDeclaredMethod("getHeight", PDRectangle.class)).setAccessible(true);
		//
		(METHOD_GET_HEIGHT_PD_IMAGE = clz.getDeclaredMethod("getHeight", PDImage.class)).setAccessible(true);
		//
		(METHOD_GET_DRAWING_PATRIARCH = clz.getDeclaredMethod("getDrawingPatriarch", Sheet.class)).setAccessible(true);
		//
		(METHOD_GET_VOICE = clz.getDeclaredMethod("getVoice", SpeechApi.class, ObjIntFunction.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_PICTURE_DATA = clz.getDeclaredMethod("getPictureData", Picture.class)).setAccessible(true);
		//
		(METHOD_GET_DATA_ITERABLE = clz.getDeclaredMethod("getDataIterable", Iterable.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				FailableConsumer.class)).setAccessible(true);
		//
		(METHOD_SET_FIELD = clz.getDeclaredMethod("setField", Object.class, Field.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_SAVE = clz.getDeclaredMethod("save", PDDocument.class, File.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_GET_ANNOTATIONS = clz.getDeclaredMethod("getAnnotations", PDPage.class, Consumer.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private String[] voiceIds = null;

		private String voiceAttribute, stringCellValue = null;

		private List<Cell> cells = null;

		private CellType cellType = null;

		private Double numericCellValue = null;

		private Integer width, height = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = Util.getName(method);
			//
			if (proxy instanceof Sheet && Objects.equals(name, "getDrawingPatriarch")) {
				//
				return null;
				//
			} else if (proxy instanceof SpeechApi) {
				//
				if (Objects.equals(name, "getVoiceIds")) {
					//
					return voiceIds;
					//
				} else if (Objects.equals(name, "getVoiceAttribute")) {
					//
					return voiceAttribute;
					//
				} // if
					//
			} else if (proxy instanceof Picture && Objects.equals(name, "getPictureData")) {
				//
				return null;
				//
			} else if (proxy instanceof Row) {
				//
				if (Objects.equals(name, "getLastCellNum")) {
					//
					return Short.valueOf((short) IterableUtils.size(cells));
					//
				} else if (Objects.equals(name, "getCell") && args != null && args.length > ZERO
						&& args[ZERO] instanceof Integer i && i != null) {
					//
					return IterableUtils.get(cells, i);
					//
				} // if
					//
			} else if (proxy instanceof Cell) {
				//
				if (Objects.equals(name, "getStringCellValue")) {
					//
					return stringCellValue;
					//
				} else if (Objects.equals(name, "getNumericCellValue")) {
					//
					return numericCellValue;
					//
				} else if (Objects.equals(name, "getCellType")) {
					//
					return cellType;
					//
				} // if
					//
			} else if (proxy instanceof PDImage) {
				//
				if (Objects.equals(name, "getWidth")) {
					//
					return width;
					//
				} else if (Objects.equals(name, "getHeight")) {
					//
					return height;
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

		private IOException ioException = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (Boolean.logicalOr(Boolean.logicalAnd(self instanceof PDDocument, Objects.equals(methodName, "save")),
					Boolean.logicalAnd(self instanceof PDPage, Objects.equals(methodName, "getAnnotations")))) {
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

	private IH ih = null;

	private PDImage pdImage = null;

	private PDRectangle pdRectangle = null;

	private MH mh = null;

	@BeforeEach
	void beforeEach() {
		//
		pdImage = Reflection.newProxy(PDImage.class, ih = new IH());
		//
		pdRectangle = Util.cast(PDRectangle.class, Narcissus.allocateInstance(PDRectangle.class));
		//
		mh = new MH();
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerSpreadsheetToPdfPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		String toString = null;
		//
		Object[] os = null;
		//
		Object invoke = null;
		//
		VoiceManagerSpreadsheetToPdfPanel instance = null;
		//
		for (int i = ZERO; ms != null && i < ms.length; i++) {
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
			for (int j = ZERO; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Float.TYPE)) {
					//
					Util.add(collection, Float.valueOf(ZERO));
					//
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // if
				//
			os = Util.toArray(collection);
			//
			toString = Util.toString(m);
			//
			invoke = Util.isStatic(m) ? Narcissus.invokeStaticMethod(m, os)
					: Narcissus
							.invokeMethod(
									instance = ObjectUtils.getIfNull(instance,
											() -> Util.cast(VoiceManagerSpreadsheetToPdfPanel.class, Narcissus
													.allocateInstance(VoiceManagerSpreadsheetToPdfPanel.class))),
									m, os);
			//
			if (Util.contains(Arrays.asList(Float.TYPE, Boolean.TYPE, Integer.TYPE), m.getReturnType())) {
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
	void testFloatValue() throws Throwable {
		//
		final float zero = 0f;
		//
		Assertions.assertEquals(zero, floatValue(Float.valueOf(zero), zero));
		//
	}

	private static float floatValue(final Number instance, final float defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_FLOAT_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Float) {
				return ((Float) obj).floatValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFieldByName() throws Throwable {
		//
		final String name = "value";
		//
		final Field field = Util.getDeclaredField(Boolean.class, name);
		//
		Assertions.assertSame(field,
				getFieldByName(Arrays.asList(null, Util.getDeclaredField(Boolean.class, "TRUE"), field), name));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> getFieldByName(Collections.nCopies(2, field), name));
		//
	}

	private static Field getFieldByName(final Collection<Field> collection, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_FIELD_BY_NAME.invoke(null, collection, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetWidth() throws Throwable {
		//
		Assertions.assertEquals(0f, getWidth(Util.cast(PDRectangle.class, pdRectangle)));
		//
		if (ih != null) {
			//
			ih.width = Integer.valueOf(ZERO);
			//
		} // if
			//
		Assertions.assertEquals(ZERO, getWidth(pdImage));
		//
		final Class<?>[] classes = new Class<?>[] { PDImageXObject.class, PDInlineImage.class };
		//
		Class<?> clz = null;
		//
		for (int i = ZERO; classes != null && i < classes.length; i++) {
			//
			Assertions.assertEquals(ZERO,
					getWidth(Util.cast(PDImage.class, Narcissus.allocateInstance(clz = ArrayUtils.get(classes, i)))),
					Util.getName(clz));
			//
		} // for
			//
	}

	private static float getWidth(final PDRectangle instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_WIDTH_PD_RECTANGLE.invoke(null, instance);
			if (obj instanceof Float) {
				return ((Float) obj).floatValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static int getWidth(final PDImage instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_WIDTH_PD_IMAGE.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetHeight() throws Throwable {
		//
		Assertions.assertEquals(0f, getHeight(Util.cast(PDRectangle.class, pdRectangle)));
		//
		if (ih != null) {
			//
			ih.height = Integer.valueOf(ZERO);
			//
		} // if
			//
		Assertions.assertEquals(ZERO, getHeight(pdImage));
		//
		final Class<?>[] classes = new Class<?>[] { PDImageXObject.class, PDInlineImage.class };
		//
		Class<?> clz = null;
		//
		for (int i = ZERO; classes != null && i < classes.length; i++) {
			//
			Assertions.assertEquals(ZERO,
					getHeight(Util.cast(PDImage.class, Narcissus.allocateInstance(clz = ArrayUtils.get(classes, i)))),
					Util.getName(clz));
			//
		} // for
			//
	}

	private static float getHeight(final PDRectangle instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_HEIGHT_PD_RECTANGLE.invoke(null, instance);
			if (obj instanceof Float) {
				return ((Float) obj).floatValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static int getHeight(final PDImage instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_HEIGHT_PD_IMAGE.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDrawingPatriarch() throws Throwable {
		//
		Assertions.assertNull(getDrawingPatriarch(Reflection.newProxy(Sheet.class, ih)));
		//
		final Class<?>[] classes = new Class<?>[] { HSSFSheet.class, SXSSFSheet.class, DeferredSXSSFSheet.class,
				XSSFDialogsheet.class, XSSFSheet.class, XSSFChartSheet.class, XSSFDialogsheet.class };
		//
		Class<?> clz = null;
		//
		for (int i = ZERO; classes != null && i < classes.length; i++) {
			//
			Assertions.assertNull(
					getDrawingPatriarch(
							Util.cast(Sheet.class, Narcissus.allocateInstance(clz = ArrayUtils.get(classes, i)))),
					Util.getName(clz));
			//
		} // for
			//
	}

	private static Drawing<?> getDrawingPatriarch(final Sheet instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DRAWING_PATRIARCH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Drawing) {
				return (Drawing) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetVoice() throws Throwable {
		//
		final SpeechApi speechApi = Reflection.newProxy(SpeechApi.class, ih);
		//
		if (ih != null) {
			//
			ih.voiceIds = new String[] { null };
			//
		} // if
			//
		Assertions.assertNull(getVoice(speechApi, null, null));
		//
		if (ih != null) {
			//
			ih.voiceIds = new String[] { null, null };
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getVoice(speechApi, null, null));
		//
		final String abc = "abc";
		//
		if (ih != null) {
			//
			ih.voiceIds = new String[] { abc };
			//
		} // if
			//
		final String b = "b";
		//
		Assertions.assertSame(abc, getVoice(speechApi, null, b));
		//
		if (ih != null) {
			//
			ih.voiceIds = new String[] { abc, abc };
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getVoice(speechApi, null, b));
		//
		final String empty = "";
		//
		if (ih != null) {
			//
			ih.voiceIds = new String[] { empty };
			//
		} // if
			//
		Assertions.assertSame(empty, getVoice(speechApi, null, null));
		//
		if (ih != null) {
			//
			ih.voiceIds = new String[] { empty, empty };
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getVoice(speechApi, null, null));
		//
	}

	private static String getVoice(final SpeechApi speechApi, final ObjIntFunction<String, String> objIntFunction,
			final String voice) throws Throwable {
		try {
			final Object obj = METHOD_GET_VOICE.invoke(null, speechApi, objIntFunction, voice);
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
	void testGetPictureData() throws Throwable {
		//
		Assertions.assertNull(getPictureData(Reflection.newProxy(Picture.class, ih)));
		//
		final Class<?>[] classes = new Class<?>[] { HSSFPicture.class, HSSFObjectData.class, SXSSFPicture.class,
				XSSFPicture.class };
		//
		Class<?> clz = null;
		//
		for (int i = ZERO; classes != null && i < classes.length; i++) {
			//
			Assertions.assertNull(
					getPictureData(
							Util.cast(Picture.class, Narcissus.allocateInstance(clz = ArrayUtils.get(classes, i)))),
					Util.getName(clz));
			//
		} // for
			//
	}

	private static PictureData getPictureData(final Picture instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PICTURE_DATA.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof PictureData) {
				return (PictureData) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDataIterable() throws Throwable {
		//
		Assertions.assertNull(getDataIterable(Collections.singleton(null)));
		//
		if (ih != null) {
			//
			Util.add(ih.cells = ObjectUtils.getIfNull(ih.cells, ArrayList::new), Reflection.newProxy(Cell.class, ih));
			//
		} // if
			//
		final Row row = Reflection.newProxy(Row.class, ih);
		//
		final ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		//
		Assertions.assertEquals("[{}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, getDataIterable(Collections.nCopies(2, row))));
		//
		if (ih != null) {
			//
			ih.stringCellValue = "text";
			//
		} // if
			//
		Assertions.assertEquals("[{}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, getDataIterable(Collections.nCopies(2, row))));
		//
		if (ih != null) {
			//
			ih.stringCellValue = "x";
			//
			ih.cellType = CellType.NUMERIC;
			//
			ih.numericCellValue = Double.valueOf(0.1);
			//
		} // if
			//
		Assertions.assertEquals("[{}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, getDataIterable(Collections.nCopies(2, row))));
		//
	}

	private static Iterable<?> getDataIterable(final Iterable<Row> rows) throws Throwable {
		try {
			final Object obj = METHOD_GET_DATA_ITERABLE.invoke(null, rows);
			if (obj == null) {
				return null;
			} else if (obj instanceof Iterable) {
				return (Iterable) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetField() {
		//
		Assertions.assertDoesNotThrow(() -> setField("", null, null));
		//
	}

	private static void setField(final Object instance, final Field field, final Object value) throws Throwable {
		try {
			METHOD_SET_FIELD.invoke(null, instance, field, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSave() throws Throwable {
		//
		final PDDocument pdDocument = ProxyUtil.createProxy(PDDocument.class, mh);
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

}