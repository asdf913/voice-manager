package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.Consumers;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFObjectData;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
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
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class VoiceManagerSpreadsheetToPdfPanelTest {

	private static final int ZERO = 0;

	private static Class<?> CLASS_DATA = null;

	private static Method METHOD_FLOAT_VALUE, METHOD_GET_FIELD_BY_NAME, METHOD_GET_DRAWING_PATRIARCH, METHOD_GET_VOICE,
			METHOD_GET_PICTURE_DATA, METHOD_GET_DATA_ITERABLE, METHOD_SET_FIELD, METHOD_TO_BIG_DECIMAL,
			METHOD_SET_SELECTED_INDEX, METHOD_TEST_AND_ACCEPT3, METHOD_TEST_AND_ACCEPT4, METHOD_OR, METHOD_SET_ICON,
			METHOD_TEST_AND_APPLY, METHOD_TEST_AND_GET, METHOD_TO_MAP, METHOD_GET_VALUE, METHOD_TO_ARRAY,
			METHOD_TO_DATA, METHOD_GET_LAYOUT_MANAGER, METHOD_SET_PREFERRED_SIZE, METHOD_WRITE_VOICE_TO_FILE,
			METHOD_FOR_EACH_REMAINING, METHOD_GET_HEIGHT = null;

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
		(METHOD_GET_DRAWING_PATRIARCH = clz.getDeclaredMethod("getDrawingPatriarch", Sheet.class)).setAccessible(true);
		//
		(METHOD_GET_VOICE = clz.getDeclaredMethod("getVoice", SpeechApi.class, ObjIntFunction.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_PICTURE_DATA = clz.getDeclaredMethod("getPictureData", Picture.class)).setAccessible(true);
		//
		(METHOD_GET_DATA_ITERABLE = clz.getDeclaredMethod("getDataIterable", Iterable.class, FormulaEvaluator.class))
				.setAccessible(true);
		//
		(METHOD_SET_FIELD = clz.getDeclaredMethod("setField", Object.class, Field.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_TO_BIG_DECIMAL = clz.getDeclaredMethod("toBigDecimal", Float.TYPE)).setAccessible(true);
		//
		(METHOD_SET_SELECTED_INDEX = clz.getDeclaredMethod("setSelectedIndex", JComboBox.class, Number.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3 = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_OR = clz.getDeclaredMethod("or", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_SET_ICON = clz.getDeclaredMethod("setIcon", JLabel.class, Icon.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				BiFunction.class, BiFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_GET = clz.getDeclaredMethod("testAndGet", Boolean.TYPE, Supplier.class, Supplier.class))
				.setAccessible(true);
		//
		(METHOD_TO_MAP = clz.getDeclaredMethod("toMap", Row.class, FormulaEvaluator.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Cell.class, FormulaEvaluator.class)).setAccessible(true);
		//
		(METHOD_TO_ARRAY = clz.getDeclaredMethod("toArray",
				CLASS_DATA = Util
						.forName("org.springframework.context.support.VoiceManagerSpreadsheetToPdfPanel$Data")))
				.setAccessible(true);
		//
		(METHOD_TO_DATA = clz.getDeclaredMethod("toData", Map.class, Row.class, FormulaEvaluator.class))
				.setAccessible(true);
		//
		(METHOD_GET_LAYOUT_MANAGER = clz.getDeclaredMethod("getLayoutManager", AutowireCapableBeanFactory.class,
				Iterable.class)).setAccessible(true);
		//
		(METHOD_SET_PREFERRED_SIZE = clz.getDeclaredMethod("setPreferredSize", Component.class, Dimension.class))
				.setAccessible(true);
		//
		(METHOD_WRITE_VOICE_TO_FILE = clz.getDeclaredMethod("writeVoiceToFile", SpeechApi.class, String.class,
				String.class, Integer.TYPE, Integer.TYPE, Map.class, File.class)).setAccessible(true);
		//
		(METHOD_FOR_EACH_REMAINING = clz.getDeclaredMethod("forEachRemaining", Iterator.class, Consumer.class))
				.setAccessible(true);
		//
		(METHOD_GET_HEIGHT = clz.getDeclaredMethod("getHeight", Dimension2D.class, Double.TYPE)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private String[] voiceIds = null;

		private String voiceAttribute, stringCellValue = null;

		private List<Cell> cells = null;

		private CellType cellType = null;

		private Double numericCellValue = null;

		private CellValue cellValue = null;

		private Object value = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Util.getReturnType(method), Void.TYPE)) {
				//
				return null;
				//
			} // if
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
			} else if (proxy instanceof FormulaEvaluator && Objects.equals(name, "evaluate")) {
				//
				return cellValue;
				//
			} else if (proxy instanceof Entry && Objects.equals(name, "getValue")) {
				//
				return value;
				//
			} else if (proxy instanceof ListCellRenderer && Objects.equals(name, "getListCellRendererComponent")) {
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

		private Double height = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			if (Objects.equals(Util.getReturnType(thisMethod), Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String name = Util.getName(thisMethod);
			//
			if (self instanceof Dimension2D) {
				//
				if (Objects.equals(name, "getHeight")) {
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

	private IH ih = null;

	private VoiceManagerSpreadsheetToPdfPanel instance = null;

	private Row row = null;

	private Cell cell = null;

	private ObjectMapper objectMapper = null;

	private SpeechApi speechApi = null;

	private MH mh = null;

	@BeforeEach
	void beforeEach() {
		//
		row = Reflection.newProxy(Row.class, ih = new IH());
		//
		cell = Reflection.newProxy(Cell.class, ih);
		//
		speechApi = Reflection.newProxy(SpeechApi.class, ih);
		//
		instance = Util.cast(VoiceManagerSpreadsheetToPdfPanel.class,
				Narcissus.allocateInstance(VoiceManagerSpreadsheetToPdfPanel.class));
		//
		if ((objectMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)) != null) {
			//
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			//
			objectMapper.setDefaultPropertyInclusion(Include.NON_NULL);
			//
		} // if
			//
		mh = new MH();
		//
	}

	@Test
	void testNull() throws Throwable {
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
		String toString, name = null;
		//
		Object[] os = null;
		//
		Object invoke = null;
		//
		for (int i = ZERO; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()
					|| Boolean.logicalAnd(Objects.equals(Util.getName(m), "or"),
							Arrays.equals(Util.getParameterTypes(m),
									new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class }))) {
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
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(ZERO));
					//
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else if (Objects.equals(parameterType, Double.TYPE)) {
					//
					Util.add(collection, Double.valueOf(ZERO));
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
			if (or(Util.contains(Arrays.asList(Float.TYPE, Boolean.TYPE, Integer.TYPE, Double.TYPE), m.getReturnType()),
					Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "getWidth"),
							Arrays.equals(parameterTypes, new Class<?>[] { Dimension.class })),
					Boolean.logicalAnd(Objects.equals(name, "toBigDecimal"),
							Arrays.equals(parameterTypes, new Class<?>[] { Float.TYPE })),
					Boolean.logicalAnd(Objects.equals(name, "createPDDocument"),
							Arrays.equals(parameterTypes, new Class<?>[] { File.class, Boolean.TYPE })),
					Boolean.logicalAnd(Objects.equals(name, "getAllowedFileMagicMethodAndCollection"),
							Arrays.equals(parameterTypes, new Class<?>[] {})),
					Boolean.logicalAnd(Objects.equals(name, "getTitle"),
							Arrays.equals(parameterTypes, new Class<?>[] {})))) {
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
	void testOr() throws Throwable {
		//
		Assertions.assertFalse(or(false, false, null));
		//
	}

	private static boolean or(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_OR.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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
		Assertions.assertNull(getDataIterable(Collections.singleton(null), null));
		//
		if (ih != null) {
			//
			Util.add(ih.cells = ObjectUtils.getIfNull(ih.cells, ArrayList::new), cell);
			//
		} // if
			//
		Assertions.assertEquals("[null]",
				ObjectMapperUtil.writeValueAsString(objectMapper, getDataIterable(Collections.nCopies(2, row), null)));
		//
		if (ih != null) {
			//
			ih.stringCellValue = "text";
			//
		} // if
			//
		Assertions.assertEquals("[{\"text\":\"text\"}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, getDataIterable(Collections.nCopies(2, row), null)));
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
		Assertions.assertEquals("[{\"x\":0.1}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, getDataIterable(Collections.nCopies(2, row), null)));
		//
	}

	private static Iterable<?> getDataIterable(final Iterable<Row> rows, final FormulaEvaluator formulaEvaluator)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_DATA_ITERABLE.invoke(null, rows, formulaEvaluator);
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
	void testToBigDecimal() throws Throwable {
		//
		final BigDecimal bd = new BigDecimal("1.0099999904632568");
		//
		Assertions.assertEquals(bd, toBigDecimal(bd.floatValue()));
		//
	}

	private static BigDecimal toBigDecimal(final float f) throws Throwable {
		try {
			final Object obj = METHOD_TO_BIG_DECIMAL.invoke(null, f);
			if (obj == null) {
				return null;
			} else if (obj instanceof BigDecimal) {
				return (BigDecimal) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetSelectedIndex() {
		//
		Assertions.assertDoesNotThrow(
				() -> setSelectedIndex(Util.cast(JComboBox.class, Narcissus.allocateInstance(JComboBox.class)), null));
		//
	}

	private static void setSelectedIndex(final JComboBox<?> instance, final Number index) throws Throwable {
		try {
			METHOD_SET_SELECTED_INDEX.invoke(null, instance, index);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent("", ZERO, null)));
		//
		final AbstractButton btnPreview = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnPreview", btnPreview, true);
		//
		final ActionEvent actionEvent = new ActionEvent(btnPreview, ZERO, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		FieldUtils.writeDeclaredField(instance, "tableModel", new DefaultTableModel(), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		FieldUtils.writeDeclaredField(instance, "lblThumbnail", new JLabel(), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.biAlwaysTrue(), null, null, null));
		//
	}

	private static <T> void testAndAccept(final Predicate<T> predicate, final T value, final Consumer<T> consumer)
			throws Throwable {
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
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(Predicates.biAlwaysTrue(), null, null, (a, b) -> null, null));
		//
	}

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMouseClicked() throws IllegalAccessException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		FieldUtils.writeDeclaredField(instance, "bufferedImage", Narcissus.allocateInstance(BufferedImage.class), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.mouseClicked(null));
		//
		Assertions.assertDoesNotThrow(
				() -> instance.mouseClicked(new MouseEvent(new JLabel(), ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, false)));
		//
	}

	@Test
	void testTestAndGet() throws Throwable {
		//
		Assertions.assertNull(testAndGet(true, null, null));
		//
	}

	private static <T> T testAndGet(final boolean condition, final Supplier<T> supplierTrue,
			final Supplier<T> supplierFalse) throws Throwable {
		try {
			return (T) METHOD_TEST_AND_GET.invoke(null, condition, supplierTrue, supplierFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMap() throws Throwable {
		//
		if (ih != null) {
			//
			ih.cells = Collections.singletonList(cell);
			//
			ih.cellType = CellType.FORMULA;
			//
		} // if
			//
		Assertions.assertEquals(Collections.singletonMap(Integer.valueOf(ZERO), null), toMap(row, null));
		//
	}

	private static Map<Integer, String> toMap(final Row row, final FormulaEvaluator formulaEvaluator) throws Throwable {
		try {
			final Object obj = METHOD_TO_MAP.invoke(null, row, formulaEvaluator);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		if (ih != null) {
			//
			ih.cellType = CellType.FORMULA;
			//
		} // if
			//
		Assertions.assertNull(getValue(cell, Reflection.newProxy(FormulaEvaluator.class, ih)));
		//
		if (ih != null) {
			//
			ih.cellValue = new CellValue(ZERO);
			//
		} // if
			//
		Assertions.assertEquals(Double.valueOf(ZERO), getValue(cell, Reflection.newProxy(FormulaEvaluator.class, ih)));
		//
		final boolean b = true;
		//
		if (ih != null) {
			//
			ih.cellValue = CellValue.valueOf(b);
			//
		} // if
			//
		Assertions.assertEquals(Boolean.valueOf(b), getValue(cell, Reflection.newProxy(FormulaEvaluator.class, ih)));
		//
	}

	private static Object getValue(final Cell cell, final FormulaEvaluator formulaEvaluator) throws Throwable {
		try {
			return METHOD_GET_VALUE.invoke(null, cell, formulaEvaluator);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToArray() throws Throwable {
		//
		final Object data = Narcissus.allocateInstance(CLASS_DATA);
		//
		Assertions.assertArrayEquals(Util.toArray(Collections.nCopies(7, null)), toArray(data));
		//
		Util.forEach(FieldUtils.getAllFieldsList(Util.getClass(data)).stream(), f -> {
			//
			if (Objects.equals(Util.getType(f), Float.class)) {
				//
				Narcissus.setField(data, f, Float.valueOf(ZERO));
				//
			} // if
				//
		});
		//
		Assertions.assertArrayEquals(ArrayUtils.addAll(Util.toArray(Collections.nCopies(3, null)),
				Util.toArray(Collections.nCopies(4, BigDecimal.valueOf(ZERO)))), toArray(data));
		//
	}

	private static Object[] toArray(final Object data) throws Throwable {
		try {
			final Object obj = METHOD_TO_ARRAY.invoke(null, data);
			if (obj == null) {
				return null;
			} else if (obj instanceof Object[]) {
				return (Object[]) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToData() throws Throwable {
		//
		if (ih != null) {
			//
			ih.cells = Collections.singletonList(cell);
			//
		} // if
			//
		Assertions.assertEquals("{}", ObjectMapperUtil.writeValueAsString(objectMapper,
				toData(Collections.singletonMap(Integer.valueOf(0), "text"), row, null)));
		//
		if (ih != null) {
			//
			ih.cellType = CellType.NUMERIC;
			//
			ih.numericCellValue = Double.valueOf(ZERO);
			//
		} // if
			//
		Assertions.assertEquals("{\"text\":\"0\"}", ObjectMapperUtil.writeValueAsString(objectMapper,
				toData(Collections.singletonMap(Integer.valueOf(0), "text"), row, null)));
		//
	}

	private static Object toData(final Map<?, String> map, final Row row, final FormulaEvaluator formulaEvaluator)
			throws Throwable {
		try {
			return METHOD_TO_DATA.invoke(null, map, row, formulaEvaluator);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetLayoutManager() throws Throwable {
		//
		final Entry<String, Object> entry = Reflection.newProxy(Entry.class, ih);
		//
		Assertions.assertNull(getLayoutManager(null, Collections.singleton(entry)));
		//
		if (ih != null) {
			//
			ih.value = Reflection.newProxy(LayoutManager.class, ih);
			//
		} // if
			//
		Assertions.assertNull(getLayoutManager(null, Collections.singleton(entry)));
		//
	}

	private static LayoutManager getLayoutManager(final AutowireCapableBeanFactory acbf,
			final Iterable<Entry<String, Object>> entrySet) throws Throwable {
		try {
			final Object obj = METHOD_GET_LAYOUT_MANAGER.invoke(null, acbf, entrySet);
			if (obj == null) {
				return null;
			} else if (obj instanceof LayoutManager) {
				return (LayoutManager) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetPreferredSize() {
		//
		Assertions.assertDoesNotThrow(() -> setPreferredSize(ProxyUtil.createProxy(Component.class, mh), null));
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
	void testForEachRemaining() {
		//
		Assertions.assertDoesNotThrow(() -> forEachRemaining(Reflection.newProxy(Iterator.class, ih), null));
		//
		final Iterable<?> iterable = Collections.emptyList();
		//
		Assertions.assertDoesNotThrow(() -> forEachRemaining(Util.iterator(iterable), null));
		//
		Assertions.assertDoesNotThrow(() -> forEachRemaining(Util.iterator(iterable), Consumers.nop()));
		//
	}

	private static <E> void forEachRemaining(final Iterator<E> instance, final Consumer<? super E> action)
			throws Throwable {
		try {
			METHOD_FOR_EACH_REMAINING.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetHeight() throws Throwable {
		//
		if (mh != null) {
			//
			mh.height = Integer.valueOf(ZERO).doubleValue();
			//
		} // if
			//
		Assertions.assertEquals(ZERO, getHeight(ProxyUtil.createProxy(Dimension2D.class, mh), ZERO));
		//
	}

	private static double getHeight(final Dimension2D instance, final double defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_GET_HEIGHT.invoke(null, instance, defaultValue);
			if (obj instanceof Double) {
				return ((Double) obj).doubleValue();
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}