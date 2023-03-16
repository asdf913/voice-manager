package org.springframework.beans.factory;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriPredicate;
import org.springframework.core.io.ByteArrayResource;

import com.google.common.reflect.Reflection;

class StringMapFromResourceFactoryBeanTest {

	private static Method METHOD_TEST_AND_ACCEPT, METHOD_GET_PHYSICAL_NUMBER_OF_CELLS, METHOD_TEST, METHOD_CREATE_MAP,
			METHOD_GET_VALUE_CELL = null;

	private static Class<?> CLASS_OBJECT_INT_MAP = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = StringMapFromResourceFactoryBean.class;
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", TriPredicate.class, Object.class, Object.class,
				Object.class, TriConsumer.class)).setAccessible(true);
		//
		(METHOD_GET_PHYSICAL_NUMBER_OF_CELLS = clz.getDeclaredMethod("getPhysicalNumberOfCells", Row.class,
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", Sheet.class, IValue0.class, IValue0.class))
				.setAccessible(true);
		//
		(METHOD_GET_VALUE_CELL = clz.getDeclaredMethod("getValueCell", Row.class,
				CLASS_OBJECT_INT_MAP = Class
						.forName("org.springframework.beans.factory.StringMapFromResourceFactoryBean$ObjectIntMap"),
				IValue0.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean containsKey = null;

		private Iterator<?> iterator = null;

		private Integer integer = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (method != null && Objects.equals(CLASS_OBJECT_INT_MAP, method.getDeclaringClass())) {
				//
				if (Objects.equals(methodName, "containsKey")) {
					//
					return containsKey;
					//
				} else if (Objects.equals(methodName, "get")) {
					//
					return integer;
					//
				} // if
					//
			} // if
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
	void testGetObject() throws Exception {
		//
		final StringMapFromResourceFactoryBean instance = new StringMapFromResourceFactoryBean();
		//
		Assertions.assertNull(instance.getObject());
		//
		instance.setResource(new ByteArrayResource("".getBytes()));
		//
		FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
		//
		Assertions.assertNull(instance.getObject());
		//
		// org.springframework.beans.factory.HSSFWorkbook
		//
		try (final Workbook wb = new HSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			wb.write(baos);
			//
			instance.setResource(new ByteArrayResource(baos.toByteArray()));
			//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertThrows(IllegalArgumentException.class, () -> instance.getObject());
			//
		} // try
			//
			// org.springframework.beans.factory.XSSFWorkbook
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			wb.write(baos);
			//
			instance.setResource(new ByteArrayResource(baos.toByteArray()));
			//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertThrows(IllegalArgumentException.class, () -> instance.getObject());
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			IntStream.range(0, 2).forEach(x -> WorkbookUtil.createSheet(wb));
			//
			wb.write(baos);
			//
			instance.setResource(new ByteArrayResource(baos.toByteArray()));
			//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertThrows(IllegalArgumentException.class, () -> instance.getObject());
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			WorkbookUtil.createSheet(wb);
			//
			wb.write(baos);
			//
			instance.setResource(new ByteArrayResource(baos.toByteArray()));
			//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertNull(instance.getObject());
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = WorkbookUtil.createSheet(wb);
			//
			IntStream.range(0, 2).forEach(x -> {
				//
				if (sheet != null) {
					//
					sheet.createRow(sheet.getPhysicalNumberOfRows());
					//
				} // if
					//
			});
			//
			wb.write(baos);
			//
			instance.setResource(new ByteArrayResource(baos.toByteArray()));
			//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertEquals(Collections.emptyMap(), instance.getObject());
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = WorkbookUtil.createSheet(wb);
			//
			IntStream.range(0, 2).forEach(x -> {
				//
				final Row row = sheet != null ? sheet.createRow(sheet.getPhysicalNumberOfRows()) : null;
				//
				IntStream.range(0, 1).forEach(y -> RowUtil.createCell(row, y));
				//
			});
			//
			wb.write(baos);
			//
			instance.setResource(new ByteArrayResource(baos.toByteArray()));
			//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertEquals(Collections.emptyMap(), instance.getObject());
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = WorkbookUtil.createSheet(wb);
			//
			IntStream.range(0, 2).forEach(x -> {
				//
				final Row row = sheet != null ? sheet.createRow(sheet.getPhysicalNumberOfRows()) : null;
				//
				IntStream.range(0, 2).forEach(y -> RowUtil.createCell(row, y));
				//
			});
			//
			wb.write(baos);
			//
			instance.setResource(new ByteArrayResource(baos.toByteArray()));
			//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			final Map<String, String> map = instance.getObject();
			//
			Assertions.assertEquals(Collections.singletonMap("", ""), map);
			//
			Assertions.assertSame(map, instance.getObject());
			//
		} // try
			//
		final String sheetName = " ";
		//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			instance.setSheetName(sheetName);
			//
			wb.write(baos);
			//
			instance.setResource(new ByteArrayResource(baos.toByteArray()));
			//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertThrows(IllegalArgumentException.class, () -> instance.getObject());
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			WorkbookUtil.createSheet(wb, sheetName);
			//
			wb.write(baos);
			//
			instance.setResource(new ByteArrayResource(baos.toByteArray()));
			//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertNull(instance.getObject());
			//
		} // try
			//
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b, c) -> true, null, null, null, null));
		//
	}

	private static <A, B, C> void testAndAccept(final TriPredicate<A, B, C> predicate, final A a, final B b, final C c,
			final TriConsumer<A, B, C> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, predicate, a, b, c, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPhysicalNumberOfCells() throws Throwable {
		//
		final int zero = 0;
		//
		Assertions.assertEquals(zero, getPhysicalNumberOfCells(null, zero));
		//
	}

	private static int getPhysicalNumberOfCells(final Row instance, final int defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_GET_PHYSICAL_NUMBER_OF_CELLS.invoke(null, instance, defaultValue);
			if (obj instanceof Number) {
				return ((Number) obj).intValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMap() throws Throwable {
		//
		Assertions.assertNull(createMap(null, null, null));
		//
		Assertions.assertNull(createMap(Reflection.newProxy(Sheet.class, ih), null, null));
		//
	}

	private static IValue0<Map<String, String>> createMap(final Sheet sheet, final IValue0<String> keyColumnName,
			final IValue0<String> valueColumnName) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP.invoke(null, sheet, keyColumnName, valueColumnName);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValueCell() throws Throwable {
		//
		Assertions.assertNull(getValueCell(null, null, Unit.with(null)));
		//
		final Object objectIntMap = Reflection.newProxy(CLASS_OBJECT_INT_MAP, ih);
		//
		if (ih != null) {
			//
			ih.containsKey = Boolean.TRUE;
			//
			ih.integer = Integer.valueOf(0);
			//
		} // if
			//
		Assertions.assertNull(getValueCell(null, objectIntMap, Unit.with(null)));
		//
	}

	private static Cell getValueCell(final Row row, final Object objectIntMap, final IValue0<String> columnName)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_VALUE_CELL.invoke(null, row, objectIntMap, columnName);
			if (obj == null) {
				return null;
			} else if (obj instanceof Cell) {
				return (Cell) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testObjectIntMap()
			throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		//
		final Method containsKey = CLASS_OBJECT_INT_MAP != null
				? CLASS_OBJECT_INT_MAP.getDeclaredMethod("containsKey", CLASS_OBJECT_INT_MAP, Object.class)
				: null;
		//
		Assertions.assertEquals(ih != null ? ih.containsKey = Boolean.FALSE : null,
				containsKey != null ? containsKey.invoke(null, Reflection.newProxy(CLASS_OBJECT_INT_MAP, ih), null)
						: null);
		//
	}

}