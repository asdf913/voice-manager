package org.apache.poi.ss.usermodel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class CellUtilTest {

	private static Method METHOD_CONTAINS, METHOD_TO_STRING, METHOD_GET_CLASS, METHOD_TEST_AND_APPLY, METHOD_TO_LIST,
			METHOD_FILTER, METHOD_GET_DECLARED_FIELDS, METHOD_GET_NAME_CLASS, METHOD_GET_NAME_MEMBER = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = CellUtil.class;
		//
		(METHOD_CONTAINS = clz.getDeclaredMethod("contains", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_LIST = clz.getDeclaredMethod("toList", Stream.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_FIELDS = clz.getDeclaredMethod("getDeclaredFields", Class.class)).setAccessible(true);
		//
		(METHOD_GET_NAME_CLASS = clz.getDeclaredMethod("getName", Class.class)).setAccessible(true);
		//
		(METHOD_GET_NAME_MEMBER = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private CellType type = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Cell) {
				//
				if (Objects.equals(methodName, "getStringCellValue")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof RichTextString) {
				//
				if (Objects.equals(methodName, "getString")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "filter")) {
					//
					return proxy;
					//
				} // if
					//
			} else if (Class.forName("org.apache.poi.xssf.streaming.SXSSFCell$Value").isInstance(proxy)) {
				//
				if (Objects.equals(methodName, "getType")) {
					//
					return type;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		ih = new IH();
		//
	}

	@Test
	void testSetCellValue() {
		//
		Assertions.assertDoesNotThrow(() -> CellUtil.setCellValue(null, null));
		//
	}

	@Test
	void testSetCellStyle() {
		//
		Assertions.assertDoesNotThrow(() -> CellUtil.setCellStyle(null, null));
		//
	}

	@Test
	void testGetStringCellValue() throws ClassNotFoundException, IOException, IllegalAccessException {
		//
		Assertions.assertNull(CellUtil.getStringCellValue(null));
		//
		Assertions.assertNull(CellUtil.getStringCellValue(null, null));
		//
		Assertions.assertNull(CellUtil.getStringCellValue(Reflection.newProxy(Cell.class, ih), null));
		//
		Assertions.assertNull(CellUtil.getStringCellValue(
				cast(Cell.class, Narcissus.allocateInstance(Class.forName("org.apache.poi.xssf.usermodel.XSSFCell"))),
				null));
		//
		Assertions.assertNull(CellUtil.getStringCellValue(
				cast(Cell.class, Narcissus.allocateInstance(Class.forName("org.apache.poi.hssf.usermodel.HSSFCell"))),
				null));
		//
		// org.apache.poi.xssf.streaming.SXSSFCell
		//
		final Cell cell = cast(Cell.class,
				Narcissus.allocateInstance(Class.forName("org.apache.poi.xssf.streaming.SXSSFCell")));
		//
		Assertions.assertNull(CellUtil.getStringCellValue(cell, null));
		//
		FieldUtils.writeField(cell, "_value",
				Reflection.newProxy(Class.forName("org.apache.poi.xssf.streaming.SXSSFCell$Value"), ih), true);
		//
		Assertions.assertThrows(IllegalStateException.class, () -> CellUtil.getStringCellValue(cell, null));
		//
		if (ih != null) {
			//
			ih.type = CellType.STRING;
			//
			final Object _value = Narcissus
					.allocateInstance(Class.forName("org.apache.poi.xssf.streaming.SXSSFCell$RichTextValue"));
			//
			FieldUtils.writeField(cell, "_value", _value, true);
			//
			FieldUtils.writeField(_value, "_value", Reflection.newProxy(RichTextString.class, ih), true);
			//
		} // if
			//
		Assertions.assertNull(CellUtil.getStringCellValue(cell, null));
		//
		if (ih != null) {
			//
			FieldUtils.writeField(cell, "_value",
					Narcissus.allocateInstance(Class.forName("org.apache.poi.xssf.streaming.SXSSFCell$BooleanValue")),
					true);
			//
		} // if
			//
		Assertions.assertEquals("false", CellUtil.getStringCellValue(cell, null));
		//
		if (ih != null) {
			//
			FieldUtils.writeField(cell, "_value",
					Narcissus.allocateInstance(Class.forName("org.apache.poi.xssf.streaming.SXSSFCell$NumericValue")),
					true);
			//
		} // if
			//
		Assertions.assertEquals("0.0", CellUtil.getStringCellValue(cell, null));
		//
		final boolean b = true;
		//
		final double d = 1.2;
		//
		final String string = "1";
		//
		final Iterable<Object> os = new FailableStream<>(Arrays.stream(Boolean.class.getDeclaredFields()).filter(
				f -> f != null && Objects.equals(Boolean.class, f.getType()) && Modifier.isStatic(f.getModifiers())))
				.map(f -> f != null ? f.get(null) : null).collect(Collectors.toList());
		//
		if (os != null && os.iterator() != null) {
			//
			Cell c = null;
			//
			for (final Object o : os) {
				//
				if (!(o instanceof Boolean)) {
					//
					continue;
					//
				} // if
					//
				try (final Workbook wb = WorkbookFactory.create(((Boolean) o).booleanValue())) {
					//
					final Sheet sheet = WorkbookUtil.createSheet(wb);
					//
					final Row row = SheetUtil.createRow(sheet, sheet != null ? sheet.getPhysicalNumberOfRows() : 0);
					//
					Assertions.assertEquals("", CellUtil.getStringCellValue(
							c = RowUtil.createCell(row, row != null ? row.getPhysicalNumberOfCells() : 0), null));
					//
					if (c != null) {
						//
						c.setCellValue(b);
						//
					} // if
						//
					Assertions.assertEquals(Boolean.toString(b), CellUtil.getStringCellValue(c, null));
					//
					if (c != null) {
						//
						c.setCellValue(d);
						//
					} // if
						//
					Assertions.assertEquals(Double.toString(d), CellUtil.getStringCellValue(c, null));
					//
					if (c != null) {
						//
						c.setCellFormula(string);
						//
					} // if
						//
					Assertions.assertEquals(string, CellUtil.getStringCellValue(c, null));
					//
				} // try
					//
			} // if
				//
		} // if
			//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(contains(null, null));
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS.invoke(null, items, item);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToString() throws Throwable {
		//
		final String empty = "";
		//
		Assertions.assertSame(empty, toString(empty));
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
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null));
		//
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Cell instance, final FormulaEvaluator formulaEvaluator) throws Throwable {
		//
		final CellValue cellValue = FormulaEvaluatorUtil.evaluate(formulaEvaluator, instance);
		//
		final CellType cellValueType = CellValueUtil.getCellType(cellValue);
		//
		if (Objects.equals(CellType.ERROR, cellValueType)) {
			//
			return Byte.toString(cellValue.getErrorValue());
			//
		} else if (Objects.equals(CellType.BOOLEAN, cellValueType)) {
			//
			return Boolean.toString(cellValue.getBooleanValue());
			//
		} else if (Objects.equals(CellType.NUMERIC, cellValueType)) {
			//
			return Double.toString(cellValue.getNumberValue());
			//
		} else if (Objects.equals(CellType.STRING, cellValueType)) {
			//
			return cellValue.getStringValue();
			//
		} // if
			//
		if (cellValue != null) {
			//
			throw new IllegalStateException(toString(cellValueType));
			//
		} // if
			//
		return instance != null ? instance.getCellFormula() : null;
		//
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysFalse(), null, null, null));
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
			} else if (obj instanceof List<?>) {
				return (List) obj;
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
			} else if (obj instanceof Stream<?>) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredFields() throws Throwable {
		//
		Assertions.assertNull(getDeclaredFields(null));
		//
	}

	private static Field[] getDeclaredFields(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_FIELDS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field[]) {
				return (Field[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName((Class<?>) null));
		//
		Assertions.assertNull(getName((Member) null));
		//
	}

	private static String getName(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME_CLASS.invoke(null, instance);
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

	private static String getName(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME_MEMBER.invoke(null, instance);
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

}