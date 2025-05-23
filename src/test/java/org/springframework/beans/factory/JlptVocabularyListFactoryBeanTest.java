package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.reflect.Reflection;

class JlptVocabularyListFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_ANNOTATION_TYPE, METHOD_GET_FIELDS_BY_NAME, METHOD_GET_INTEGER_VALUE,
			METHOD_GET_STRING_VALUE_CELL, METHOD_INVOKE, METHOD_GET_DECLARED_ANNOTATIONS, METHOD_GET_DECLARED_METHODS,
			METHOD_SET_ACCESSIBLE, METHOD_GET_NUMBER_VALUE, METHOD_GET_PHYSICAL_NUMBER_OF_CELLS = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = JlptVocabularyListFactoryBean.class;
		//
		(METHOD_ANNOTATION_TYPE = clz.getDeclaredMethod("annotationType", Annotation.class)).setAccessible(true);
		//
		(METHOD_GET_FIELDS_BY_NAME = clz.getDeclaredMethod("getFieldsByName", Field[].class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_INTEGER_VALUE = clz.getDeclaredMethod("getIntegerValue", Cell.class, FormulaEvaluator.class))
				.setAccessible(true);
		//
		(METHOD_GET_STRING_VALUE_CELL = clz.getDeclaredMethod("getStringValue", Cell.class, FormulaEvaluator.class))
				.setAccessible(true);
		//
		(METHOD_INVOKE = clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_GET_DECLARED_ANNOTATIONS = clz.getDeclaredMethod("getDeclaredAnnotations", AnnotatedElement.class))
				.setAccessible(true);
		//
		(METHOD_GET_DECLARED_METHODS = clz.getDeclaredMethod("getDeclaredMethods", Class.class)).setAccessible(true);
		//
		(METHOD_SET_ACCESSIBLE = clz.getDeclaredMethod("setAccessible", AccessibleObject.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_NUMBER_VALUE = clz.getDeclaredMethod("getNumberValue", CellValue.class)).setAccessible(true);
		//
		(METHOD_GET_PHYSICAL_NUMBER_OF_CELLS = clz.getDeclaredMethod("getPhysicalNumberOfCells", Row.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean exists = null;

		private boolean reset = true;

		private InputStream inputStream = null;

		private Object[] toArray = null;

		private CellType cellType = null;

		private String stringCellValue = null;

		private Double numericCellValue = null;

		private CellValue evaluate = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof InputStreamSource) {
				//
				if (Objects.equals(methodName, "getInputStream")) {
					//
					if (inputStream != null && reset) {
						//
						inputStream.reset();
						//
					} // if
						//
					return inputStream;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof Resource) {
				//
				if (Objects.equals(methodName, "exists")) {
					//
					return exists;
					//
				} // if
					//
			} else if (proxy instanceof Collection) {
				//
				if (Objects.equals(methodName, "toArray")) {
					//
					return toArray;
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
				} // if
					//
			} else if (proxy instanceof FormulaEvaluator) {
				//
				if (Objects.equals(methodName, "evaluate")) {
					//
					return evaluate;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private JlptVocabularyListFactoryBean instance = null;

	private IH ih = null;

	private Cell cell = null;

	private FormulaEvaluator formulaEvaluator = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new JlptVocabularyListFactoryBean();
		//
		cell = Reflection.newProxy(Cell.class, ih = new IH());
		//
		formulaEvaluator = Reflection.newProxy(FormulaEvaluator.class, ih);
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertSame(List.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		if (instance != null) {
			//
			instance.setUrls(Arrays.asList(null, EMPTY, " "));
			//
		} // if
			//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrls(Reflection.newProxy(List.class, ih));
			//
		} // if
			//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		// java.io.File.File
		//
		if (instance != null) {
			//
			instance.setUrls(Arrays.asList(Path.of(".").toFile().toURI().toURL().toString()));
			//
		} // if
			//
		Assertions.assertNotNull(FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrls(Arrays.asList(Path.of("pom.xml").toFile().toURI().toURL().toString()));
			//
		} // if
			//
		Assertions.assertNotNull(FactoryBeanUtil.getObject(instance));
		//
		// org.springframework.core.io.Resource
		//
		if (instance != null) {
			//
			instance.setResource(Reflection.newProxy(Resource.class, ih));
			//
			instance.setUrls(null);
			//
		} // if
			//
		if (ih != null) {
			//
			ih.exists = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		try (final InputStream is = new ByteArrayInputStream(EMPTY.getBytes())) {
			//
			if (ih != null) {
				//
				ih.inputStream = is;
				//
			} // if
				//
			Assertions.assertNull(FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
			// org.apache.poi.ss.usermodel.Workbook
			//
		byte[] bs = null;
		//
		try (final Workbook workbook = new XSSFWorkbook();
				final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			workbook.write(baos);
			//
			bs = baos.toByteArray();
			//
		} // try
			//
		try (final InputStream is = new ByteArrayInputStream(bs)) {
			//
			if (ih != null) {
				//
				ih.inputStream = is;
				//
			} // if
				//
			Assertions.assertNull(FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
		final String level = "n1";
		//
		try (final Workbook workbook = new XSSFWorkbook();
				final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = WorkbookUtil.createSheet(workbook);
			//
			// First Row
			//
			Row row = SheetUtil.createRow(sheet, 0);
			//
			CellUtil.setCellValue(RowUtil.createCell(row, 0), "level");
			//
			CellUtil.setCellValue(RowUtil.createCell(row, 1), EMPTY);
			//
			// Second Row
			//
			CellUtil.setCellValue(RowUtil.createCell(row = SheetUtil.createRow(sheet, 1), 0), level);
			//
			CellUtil.setCellValue(RowUtil.createCell(row, 1), EMPTY);
			//
			workbook.write(baos);
			//
			bs = baos.toByteArray();
			//
		} // try
			//
		try (final InputStream is = new ByteArrayInputStream(bs)) {
			//
			if (ih != null) {
				//
				ih.inputStream = is;
				//
				ih.reset = true;
				//
			} // if
				//
			Assertions
					.assertEquals(String.format("[{\"level\":\"%1$s\"}]", level),
							ObjectMapperUtil.writeValueAsString(
									new ObjectMapper().setVisibility(PropertyAccessor.ALL, Visibility.ANY)
											.setSerializationInclusion(Include.NON_NULL),
									FactoryBeanUtil.getObject(instance)));
			//
		} // try
			//
		try (final InputStream is = new ByteArrayInputStream(bs)) {
			//
			if (ih != null) {
				//
				ih.inputStream = is;
				//
				ih.reset = false;
				//
			} // if
				//
			AssertionsUtil.assertThrowsAndEquals(EmptyFileException.class,
					"{localizedMessage=The supplied file was empty (zero bytes long), message=The supplied file was empty (zero bytes long)}",
					() -> FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
	}

	@Test
	void testAnnotationType() throws Throwable {
		//
		Assertions.assertNull(annotationType(null));
		//
	}

	private static Class<? extends Annotation> annotationType(final Annotation instance) throws Throwable {
		try {
			final Object obj = METHOD_ANNOTATION_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFieldsByName() throws Throwable {
		//
		Assertions.assertNull(getFieldsByName(null, null));
		//
		Assertions.assertEquals("[null]", Util.toString(getFieldsByName(new Field[] { null }, null)));
		//
	}

	private static List<Field> getFieldsByName(final Field[] fs, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_FIELDS_BY_NAME.invoke(null, fs, name);
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
	void testGetIntegerValue() throws Throwable {
		//
		Assertions.assertNull(getIntegerValue(null, null));
		//
		Assertions.assertNull(getIntegerValue(cell, null));
		//
		// org.apache.poi.ss.usermodel.CellType.STRING
		//
		if (ih != null) {
			//
			ih.cellType = CellType.STRING;
			//
		} // if
			//
		Assertions.assertEquals(Unit.with(null), getIntegerValue(cell, null));
		//
		// org.apache.poi.ss.usermodel.CellType.NUMERIC
		//
		final Double one = Double.valueOf(1);
		//
		if (ih != null) {
			//
			ih.cellType = CellType.NUMERIC;
			//
			ih.numericCellValue = one;
			//
		} // if
			//
		Assertions.assertEquals(String.format("[%1$s]", one != null ? one.intValue() : null),
				Util.toString(getIntegerValue(cell, null)));
		//
		// org.apache.poi.ss.usermodel.CellType.NUMERIC
		//
		if (ih != null) {
			//
			ih.cellType = CellType.FORMULA;
			//
		} // if
			//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class,
				"{localizedMessage=cellType=FORMULA,cellValueType=null, message=cellType=FORMULA,cellValueType=null}",
				() -> getIntegerValue(cell, null));
		//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class,
				"{localizedMessage=cellType=FORMULA,cellValueType=null, message=cellType=FORMULA,cellValueType=null}",
				() -> getIntegerValue(cell, formulaEvaluator));
		//
		final int zero = 0;
		//
		if (ih != null) {
			//
			ih.evaluate = new CellValue(zero);
			//
		} // if
			//
		Assertions.assertEquals(Unit.with(Integer.valueOf(zero)), getIntegerValue(cell, formulaEvaluator));
		//
		if (ih != null) {
			//
			ih.evaluate = new CellValue(Integer.toString(zero));
			//
		} // if
			//
		Assertions.assertEquals(Unit.with(Integer.valueOf(zero)), getIntegerValue(cell, formulaEvaluator));
		//
	}

	private static IValue0<Integer> getIntegerValue(final Cell cell, final FormulaEvaluator formulaEvaluator)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_INTEGER_VALUE.invoke(null, cell, formulaEvaluator);
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
	void testGetStringValue() throws Throwable {
		//
		Assertions.assertNull(getStringValue(null, null));
		//
		final double zero = 0;
		//
		if (ih != null) {
			//
			ih.cellType = CellType.NUMERIC;
			//
			ih.numericCellValue = Double.valueOf(zero);
			//
		} // if
			//
		Assertions.assertEquals(Unit.with(Double.toString(zero)), getStringValue(cell, null));
		//
		if (ih != null) {
			//
			ih.cellType = CellType.FORMULA;
			//
		} // if
			//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class,
				"{localizedMessage=cellType=FORMULA,cellValueType=null, message=cellType=FORMULA,cellValueType=null}",
				() -> getStringValue(cell, null));
		//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class,
				"{localizedMessage=cellType=FORMULA,cellValueType=null, message=cellType=FORMULA,cellValueType=null}",
				() -> getStringValue(cell, formulaEvaluator));
		//
		final boolean b = true;
		//
		if (ih != null) {
			//
			ih.evaluate = CellValue.valueOf(b);
			//
		} // if
			//
		Assertions.assertEquals(Unit.with(Boolean.toString(b)), getStringValue(cell, formulaEvaluator));
		//
		if (ih != null) {
			//
			ih.evaluate = new CellValue(zero);
			//
		} // if
			//
		Assertions.assertEquals(Unit.with(Double.toString(zero)), getStringValue(cell, formulaEvaluator));
		//
		if (ih != null) {
			//
			ih.evaluate = new CellValue(EMPTY);
			//
		} // if
			//
		Assertions.assertEquals(Unit.with(EMPTY), getStringValue(cell, formulaEvaluator));
		//
	}

	private static IValue0<String> getStringValue(final Cell cell, final FormulaEvaluator formulaEvaluator)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_STRING_VALUE_CELL.invoke(null, cell, formulaEvaluator);
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
	void testInvoke() throws Throwable {
		//
		Assertions.assertNull(invoke(null, null));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args) throws Throwable {
		try {
			return METHOD_INVOKE.invoke(null, method, instance, args);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredAnnotations() throws Throwable {
		//
		Assertions.assertNull(getDeclaredAnnotations(null));
		//
	}

	private static Annotation[] getDeclaredAnnotations(final AnnotatedElement instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_ANNOTATIONS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Annotation[]) {
				return (Annotation[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredMethods() throws Throwable {
		//
		Assertions.assertNull(getDeclaredMethods(null));
		//
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_METHODS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Method[]) {
				return (Method[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetAccessible() {
		//
		Assertions.assertDoesNotThrow(() -> setAccessible(null, false));
		//
	}

	private static void setAccessible(final AccessibleObject instance, final boolean flag) throws Throwable {
		try {
			METHOD_SET_ACCESSIBLE.invoke(null, instance, flag);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetNumberValue() throws Throwable {
		//
		Assertions.assertNull(getNumberValue(null));
		//
	}

	private static Double getNumberValue(final CellValue instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NUMBER_VALUE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Double) {
				return (Double) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPhysicalNumberOfCells() throws Throwable {
		//
		Assertions.assertNull(getPhysicalNumberOfCells(null));
		//
	}

	private static Integer getPhysicalNumberOfCells(final Row instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PHYSICAL_NUMBER_OF_CELLS.invoke(null, instance);
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

}