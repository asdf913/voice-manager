package org.springframework.beans.factory;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Pair;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriPredicate;
import org.springframework.core.io.ByteArrayResource;

import com.google.common.reflect.Reflection;

class StringMapFromResourceFactoryBeanTest {

	private static Method METHOD_TEST_AND_ACCEPT, METHOD_GET_PHYSICAL_NUMBER_OF_CELLS, METHOD_CREATE_MAP,
			METHOD_GET_VALUE_CELL_AND_FORMULA_EVALUATOR, METHOD_GET_VALUE_CELL = null;

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
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", Sheet.class, FormulaEvaluator.class, IValue0.class,
				Pair.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE_CELL_AND_FORMULA_EVALUATOR = clz.getDeclaredMethod("getString", Cell.class,
				FormulaEvaluator.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE_CELL = clz.getDeclaredMethod("getValueCell", Row.class,
				CLASS_OBJECT_INT_MAP = Class
						.forName("org.springframework.beans.factory.StringMapFromResourceFactoryBean$ObjectIntMap"),
				Pair.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean containsKey = null;

		private Iterator<?> iterator = null;

		private Integer integer = null;

		private CellType cellType = null;

		private String stringCellValue = null;

		private Double numericCellValue = null;

		private Boolean booleanCellValue = null;

		private CellValue cellValue = null;

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
			} else if (proxy instanceof Cell) {
				//
				if (Objects.equals(methodName, "getCellType")) {
					//
					return cellType;
					//
				} else if (Objects.equals(methodName, "getStringCellValue")) {
					//
					return stringCellValue;
					//
				} else if (Objects.equals(methodName, "getNumericCellValue")) {
					//
					return numericCellValue;
					//
				} else if (Objects.equals(methodName, "getBooleanCellValue")) {
					//
					return booleanCellValue;
					//
				} // if
					//
			} else if (proxy instanceof FormulaEvaluator) {
				//
				if (Objects.equals(methodName, "evaluate")) {
					//
					return cellValue;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private StringMapFromResourceFactoryBean instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new StringMapFromResourceFactoryBean();
		//
		ih = new IH();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource("".getBytes()));
			//
		} // if
			//
		FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
		//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		// org.springframework.beans.factory.HSSFWorkbook
		//
		try (final Workbook wb = new HSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			wb.write(baos);
			//
			if (instance != null) {
				//
				instance.setResource(new ByteArrayResource(baos.toByteArray()));
				//
			} // if
				//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
					"{localizedMessage=There is no sheet in the workbook, message=There is no sheet in the workbook}",
					() -> FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
			// org.springframework.beans.factory.XSSFWorkbook
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			wb.write(baos);
			//
			if (instance != null) {
				//
				instance.setResource(new ByteArrayResource(baos.toByteArray()));
				//
			} // if
				//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
					"{localizedMessage=There is no sheet in the workbook, message=There is no sheet in the workbook}",
					() -> FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			Util.forEach(IntStream.range(0, 2), x -> WorkbookUtil.createSheet(wb));
			//
			wb.write(baos);
			//
			if (instance != null) {
				//
				instance.setResource(new ByteArrayResource(baos.toByteArray()));
				//
			} // if
				//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
					"{localizedMessage=There are more than one sheet in the workbook, message=There are more than one sheet in the workbook}",
					() -> FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			WorkbookUtil.createSheet(wb);
			//
			wb.write(baos);
			//
			if (instance != null) {
				//
				instance.setResource(new ByteArrayResource(baos.toByteArray()));
				//
			} // if
				//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertNull(FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = WorkbookUtil.createSheet(wb);
			//
			Util.forEach(IntStream.range(0, 2), x -> {
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
			if (instance != null) {
				//
				instance.setResource(new ByteArrayResource(baos.toByteArray()));
				//
			} // if
				//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertEquals(Collections.emptyMap(), FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = WorkbookUtil.createSheet(wb);
			//
			Util.forEach(IntStream.range(0, 2), x -> {
				//
				final Row row = sheet != null ? sheet.createRow(sheet.getPhysicalNumberOfRows()) : null;
				//
				Util.forEach(IntStream.range(0, 1), y -> RowUtil.createCell(row, y));
				//
			});
			//
			wb.write(baos);
			//
			if (instance != null) {
				//
				instance.setResource(new ByteArrayResource(baos.toByteArray()));
				//
			} // if
				//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertEquals(Collections.emptyMap(), FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = WorkbookUtil.createSheet(wb);
			//
			Util.forEach(IntStream.range(0, 2), x -> {
				//
				final Row row = sheet != null ? sheet.createRow(sheet.getPhysicalNumberOfRows()) : null;
				//
				Util.forEach(IntStream.range(0, 2), y -> RowUtil.createCell(row, y));
				//
			});
			//
			wb.write(baos);
			//
			if (instance != null) {
				//
				instance.setResource(new ByteArrayResource(baos.toByteArray()));
				//
			} // if
				//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			final Map<String, String> map = FactoryBeanUtil.getObject(instance);
			//
			Assertions.assertEquals(Collections.emptyMap(), map);
			//
			Assertions.assertSame(map, FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
		final String sheetName = " ";
		//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			if (instance != null) {
				//
				instance.setSheetName(sheetName);
				//
			} // if
				//
			wb.write(baos);
			//
			if (instance != null) {
				//
				instance.setResource(new ByteArrayResource(baos.toByteArray()));
				//
			}
			//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
					"{localizedMessage=Sheet [ ] not found, message=Sheet [ ] not found}",
					() -> FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			WorkbookUtil.createSheet(wb, sheetName);
			//
			wb.write(baos);
			//
			if (instance != null) {
				//
				instance.setResource(new ByteArrayResource(baos.toByteArray()));
				//
			} // if
				//
			FieldUtils.writeDeclaredField(instance, "iValue0", null, true);
			//
			Assertions.assertNull(FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
	}

	@Test
	void testSetValueColumnName() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field valueColumnNameAndIndex = StringMapFromResourceFactoryBean.class
				.getDeclaredField("valueColumnNameAndIndex");
		//
		if (valueColumnNameAndIndex != null) {
			//
			valueColumnNameAndIndex.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setValueColumnName(null);
				//
			} // if
				//
		});
		//
		final Object before = valueColumnNameAndIndex != null ? valueColumnNameAndIndex.get(instance) : null;
		//
		Assertions.assertEquals(Pair.with(null, null), before);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setValueColumnName(null);
				//
			} // if
				//
		});
		//
		final Object after = valueColumnNameAndIndex != null ? valueColumnNameAndIndex.get(instance) : null;
		//
		Assertions.assertEquals(before, after);
		//
		Assertions.assertNotSame(before, after);
		//
	}

	@Test
	void testSetValueColumnIndex() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field valueColumnNameAndIndex = StringMapFromResourceFactoryBean.class
				.getDeclaredField("valueColumnNameAndIndex");
		//
		if (valueColumnNameAndIndex != null) {
			//
			valueColumnNameAndIndex.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setValueColumnIndex(null);
				//
			} // if
				//
		});
		//
		final Object before = valueColumnNameAndIndex != null ? valueColumnNameAndIndex.get(instance) : null;
		//
		Assertions.assertEquals(Pair.with(null, null), before);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setValueColumnIndex(null);
				//
			} // if
				//
		});
		//
		final Object after = valueColumnNameAndIndex != null ? valueColumnNameAndIndex.get(instance) : null;
		//
		Assertions.assertEquals(before, after);
		//
		Assertions.assertNotSame(before, after);
		//
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null, null));
		//
		final TriPredicate<?, ?, ?> triPredicate = (a, b, c) -> true;
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(triPredicate, null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(triPredicate, null, null, null, (a, b, c) -> {
		}));
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
			throw new Throwable(obj != null ? Util.toString(obj.getClass()) : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMap() throws Throwable {
		//
		Assertions.assertNull(createMap(null, null, null, null));
		//
		Assertions.assertNull(createMap(Reflection.newProxy(Sheet.class, ih), null, null, null));
		//
	}

	private static IValue0<Map<String, String>> createMap(final Sheet sheet, final FormulaEvaluator formulaEvaluator,
			final IValue0<String> keyColumnName, final IValue0<String> valueColumnName) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP.invoke(null, sheet, formulaEvaluator, keyColumnName, valueColumnName);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(Util.toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValueCell() throws Throwable {
		//
		Assertions.assertNull(getValueCell(null, null, Pair.with(null, null)));
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
		Assertions.assertNull(getValueCell(null, objectIntMap, Pair.with(null, null)));
		//
		Assertions.assertNull(getValueCell(null, objectIntMap, Pair.with(null, Integer.valueOf(0))));
		//
	}

	private static Cell getValueCell(final Row row, final Object objectIntMap,
			final Pair<String, Integer> valueColumnNameAndIndex) throws Throwable {
		try {
			final Object obj = METHOD_GET_VALUE_CELL.invoke(null, row, objectIntMap, valueColumnNameAndIndex);
			if (obj == null) {
				return null;
			} else if (obj instanceof Cell) {
				return (Cell) obj;
			}
			throw new Throwable(Util.toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetString() throws Throwable {
		//
		Assertions.assertNull(getString(null, null));
		//
		final Cell cell = Reflection.newProxy(Cell.class, ih);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}", () -> getString(cell, null));
		//
		if (ih != null) {
			//
			ih.cellType = CellType.STRING;
			//
		} // if
			//
		Assertions.assertNull(getString(cell, null));
		//
		if (ih != null) {
			//
			ih.cellType = CellType.BLANK;
			//
		} // if
			//
		Assertions.assertNull(getString(cell, null));
		//
		final Double d = Double.valueOf(1.2);
		//
		if (ih != null) {
			//
			ih.cellType = CellType.NUMERIC;
			//
			ih.numericCellValue = d;
			//
		} // if
			//
		Assertions.assertEquals(d != null ? d.toString() : null, getString(cell, null));
		//
		final Boolean b = Boolean.TRUE;
		//
		if (ih != null) {
			//
			ih.cellType = CellType.BOOLEAN;
			//
			ih.booleanCellValue = b;
			//
		} // if
			//
		Assertions.assertEquals(b != null ? b.toString() : null, getString(cell, null));
		//
		if (ih != null) {
			//
			ih.cellType = CellType.FORMULA;
			//
		} // if
			//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{localizedMessage=FORMULA, message=FORMULA}",
				() -> getString(cell, null));
		//
		final FormulaEvaluator formulaEvaluator = Reflection.newProxy(FormulaEvaluator.class, ih);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{localizedMessage=FORMULA, message=FORMULA}",
				() -> getString(cell, formulaEvaluator));
		//
		if (ih != null) {
			//
			ih.cellValue = new CellValue(null);
			//
		} // if
			//
		Assertions.assertNull(getString(cell, formulaEvaluator));
		//
		if (ih != null) {
			//
			ih.cellValue = CellValue.getError(0);
			//
		} // if
			//
		Assertions.assertEquals("#NULL!", getString(cell, formulaEvaluator));
		//
		if (ih != null) {
			//
			ih.cellValue = CellValue.getError(Integer.MAX_VALUE);
			//
		} // if
			//
		Assertions.assertEquals("(no error)", getString(cell, formulaEvaluator));
		//
	}

	private static String getString(final Cell cell, final FormulaEvaluator formulaEvaluator) throws Throwable {
		try {
			final Object obj = METHOD_GET_VALUE_CELL_AND_FORMULA_EVALUATOR.invoke(null, cell, formulaEvaluator);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testObjectIntMap() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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