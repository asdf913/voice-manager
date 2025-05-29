package org.springframework.context.support;

import java.io.IOException;
import java.lang.reflect.Field;
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

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerSpreadsheetToPdfPanelTest {

	private static Method METHOD_FLOAT_VALUE, METHOD_GET_FIELD_BY_NAME, METHOD_GET_WIDTH, METHOD_GET_HEIGHT,
			METHOD_GET_DRAWING_PATRIARCH, METHOD_GET_VOICE, METHOD_GET_PICTURE_DATA, METHOD_GET_DATA_ITERABLE = null;

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
		(METHOD_GET_WIDTH = clz.getDeclaredMethod("getWidth", PDRectangle.class)).setAccessible(true);
		//
		(METHOD_GET_HEIGHT = clz.getDeclaredMethod("getHeight", PDRectangle.class)).setAccessible(true);
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
	}

	private static class IH implements InvocationHandler {

		private String[] voiceIds = null;

		private String voiceAttribute, stringCellValue = null;

		private List<Cell> cells = null;

		private CellType cellType = null;

		private Double numericCellValue = null;

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
				} else if (Objects.equals(name, "getCell") && args != null && args.length > 0
						&& args[0] instanceof Integer i && i != null) {
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
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private IH ih = null;

	private PDRectangle pdRectangle = null;

	@BeforeEach
	void beforeEach() {
		//
		ih = new IH();
		//
		pdRectangle = Util.cast(PDRectangle.class, Narcissus.allocateInstance(PDRectangle.class));
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
		Object invokeStaticMethod = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
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
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Float.TYPE)) {
					//
					Util.add(collection, Float.valueOf(0));
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
			toString = Util.toString(m);
			//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, Util.toArray(collection));
			//
			if (Util.contains(Arrays.asList(Float.TYPE, Boolean.TYPE), m.getReturnType())) {
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
	void testMain() {
		//
		Assertions.assertDoesNotThrow(() -> VoiceManagerSpreadsheetToPdfPanel.main(new String[] { "." }));
		//
		Assertions.assertThrows(IOException.class,
				() -> VoiceManagerSpreadsheetToPdfPanel.main(new String[] { "pom.xml" }));
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
	}

	private static float getWidth(final PDRectangle instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_WIDTH.invoke(null, instance);
			if (obj instanceof Float) {
				return ((Float) obj).floatValue();
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
	}

	private static float getHeight(final PDRectangle instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_HEIGHT.invoke(null, instance);
			if (obj instanceof Float) {
				return ((Float) obj).floatValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDrawingPatriarch() throws Throwable {
		//
		Assertions.assertNull(getDrawingPatriarch(Reflection.newProxy(Sheet.class, new IH())));
		//
		final Class<?>[] classes = new Class<?>[] { HSSFSheet.class, SXSSFSheet.class, DeferredSXSSFSheet.class,
				XSSFDialogsheet.class, XSSFSheet.class, XSSFChartSheet.class, XSSFDialogsheet.class };
		//
		Class<?> clz = null;
		//
		for (int i = 0; classes != null && i < classes.length; i++) {
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
		Assertions.assertNull(getPictureData(Reflection.newProxy(Picture.class, new IH())));
		//
		final Class<?>[] classes = new Class<?>[] { HSSFPicture.class, HSSFObjectData.class, SXSSFPicture.class,
				XSSFPicture.class };
		//
		Class<?> clz = null;
		//
		for (int i = 0; classes != null && i < classes.length; i++) {
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

}