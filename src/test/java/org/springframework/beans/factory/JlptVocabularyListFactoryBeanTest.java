package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

class JlptVocabularyListFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_FILTER, METHOD_GET_CLASS, METHOD_TO_STRING, METHOD_TO_LIST, METHOD_ANNOTATION_TYPE,
			METHOD_GET_NAME, METHOD_TEST, METHOD_OR, METHOD_GET_STRING_CELL_VALUE, METHOD_ADD, METHOD_ADD_ALL,
			METHOD_PUT, METHOD_GET_FIELDS_BY_NAME, METHOD_GET_INTEGER_VALUE, METHOD_GET_STRING_VALUE, METHOD_INVOKE,
			METHOD_GET_DECLARED_ANNOTATIONS, METHOD_GET_DECLARED_METHODS, METHOD_IS_ASSIGNABLE_FROM = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = JlptVocabularyListFactoryBean.class;
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_TO_LIST = clz.getDeclaredMethod("toList", Stream.class)).setAccessible(true);
		//
		(METHOD_ANNOTATION_TYPE = clz.getDeclaredMethod("annotationType", Annotation.class)).setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Class.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_OR = clz.getDeclaredMethod("or", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_GET_STRING_CELL_VALUE = clz.getDeclaredMethod("getStringCellValue", Cell.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_ADD_ALL = clz.getDeclaredMethod("addAll", Collection.class, Collection.class)).setAccessible(true);
		//
		(METHOD_PUT = clz.getDeclaredMethod("put", Map.class, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_FIELDS_BY_NAME = clz.getDeclaredMethod("getFieldsByName", Field[].class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_INTEGER_VALUE = clz.getDeclaredMethod("getIntegerValue", Cell.class, FormulaEvaluator.class))
				.setAccessible(true);
		//
		(METHOD_GET_STRING_VALUE = clz.getDeclaredMethod("getStringValue", Cell.class, FormulaEvaluator.class))
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
		(METHOD_IS_ASSIGNABLE_FROM = clz.getDeclaredMethod("isAssignableFrom", Class.class, Class.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean exists, addAll = null;

		private boolean reset = true;

		private InputStream inputStream = null;

		private Iterator<?> iterator = null;

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
			} else if (proxy instanceof Iterable) {
				//
				if (Objects.equals(methodName, "iterator")) {
					//
					return iterator;
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
				} else if (Objects.equals(methodName, "addAll")) {
					//
					return addAll;
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
		Assertions.assertSame(List.class, instance != null ? instance.getObjectType() : null);
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
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrls(Reflection.newProxy(List.class, ih));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		// java.io.File.File
		//
		if (instance != null) {
			//
			instance.setUrls(Arrays.asList(new File(".").toURI().toURL().toString()));
			//
		} // if
			//
		Assertions.assertNotNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrls(Arrays.asList(new File("pom.xml").toURI().toURL().toString()));
			//
		} // if
			//
		Assertions.assertNotNull(getObject(instance));
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
		Assertions.assertNull(getObject(instance));
		//
		try (final InputStream is = new ByteArrayInputStream(EMPTY.getBytes())) {
			//
			if (ih != null) {
				//
				ih.inputStream = is;
				//
			} // if
				//
			Assertions.assertNull(getObject(instance));
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
			Assertions.assertNull(getObject(instance));
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
			Assertions.assertEquals(String.format("[{\"level\":\"%1$s\"}]", level),
					ObjectMapperUtil
							.writeValueAsString(new ObjectMapper().setVisibility(PropertyAccessor.ALL, Visibility.ANY)
									.setSerializationInclusion(Include.NON_NULL), getObject(instance)));
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
			Assertions.assertThrows(EmptyFileException.class, () -> getObject(instance));
			//
		} // try
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(null, null));
		//
		final Stream<?> empty = Stream.empty();
		//
		Assertions.assertNull(filter(empty, null));
		//
		Assertions.assertNotNull(filter(empty, Predicates.alwaysTrue()));
		//
		final Stream<?> steram = Reflection.newProxy(Stream.class, new IH());
		//
		Assertions.assertSame(steram, filter(steram, null));
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_FILTER.invoke(null, instance, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
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
			} else if (obj instanceof Class) {
				return (Class) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToString() throws Throwable {
		//
		Assertions.assertNull(toString(null));
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
	void testToList() throws Throwable {
		//
		Assertions.assertNull(toList(null));
		//
		Assertions.assertEquals(Collections.emptyList(), toList(Stream.empty()));
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_LIST.invoke(null, instance);
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName(null));
		//
	}

	private static String getName(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME.invoke(null, instance);
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
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
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
	void testOr() throws Throwable {
		//
		Assertions.assertTrue(or(false, true));
		//
		Assertions.assertFalse(or(false, false, null));
		//
		Assertions.assertTrue(or(false, false, true));
		//
	}

	private static boolean or(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_OR.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetStringCellValue() throws Throwable {
		//
		Assertions.assertNull(getStringCellValue(null));
		//
	}

	private static String getStringCellValue(final Cell instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_STRING_CELL_VALUE.invoke(null, instance);
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
	void testAdd() {
		//
		Assertions.assertDoesNotThrow(() -> add(null, null));
		//
	}

	private static <E> void add(final Collection<E> items, final E item) throws Throwable {
		try {
			METHOD_ADD.invoke(null, items, item);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddAll() {
		//
		Assertions.assertDoesNotThrow(() -> addAll(null, null));
		//
		Assertions.assertDoesNotThrow(() -> addAll(Collections.emptyList(), null));
		//
		if (ih != null) {
			//
			ih.addAll = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> addAll(Reflection.newProxy(Collection.class, ih), null));
		//
	}

	private static <E> void addAll(final Collection<E> a, final Collection<? extends E> b) throws Throwable {
		try {
			METHOD_ADD_ALL.invoke(null, a, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPut() {
		//
		Assertions.assertDoesNotThrow(() -> put(null, null, null));
		//
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) throws Throwable {
		try {
			METHOD_PUT.invoke(null, instance, key, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFieldsByName() throws Throwable {
		//
		Assertions.assertNull(getFieldsByName(null, null));
		//
		Assertions.assertEquals("[null]", toString(getFieldsByName(new Field[] { null }, null)));
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
			throw new Throwable(toString(getClass(obj)));
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
				toString(getIntegerValue(cell, null)));
		//
		// org.apache.poi.ss.usermodel.CellType.NUMERIC
		//
		if (ih != null) {
			//
			ih.cellType = CellType.FORMULA;
			//
		} // if
			//
		Assertions.assertThrows(UnsupportedOperationException.class, () -> getIntegerValue(cell, null));
		//
		Assertions.assertThrows(UnsupportedOperationException.class, () -> getIntegerValue(cell, formulaEvaluator));
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
			throw new Throwable(toString(getClass(obj)));
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
		Assertions.assertThrows(UnsupportedOperationException.class, () -> getStringValue(cell, null));
		//
		Assertions.assertThrows(UnsupportedOperationException.class, () -> getStringValue(cell, formulaEvaluator));
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
			final Object obj = METHOD_GET_STRING_VALUE.invoke(null, cell, formulaEvaluator);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAssignableFrom() throws Throwable {
		//
		Assertions.assertFalse(isAssignableFrom(null, null));
		//
		Assertions.assertFalse(isAssignableFrom(Object.class, null));
		//
		Assertions.assertFalse(isAssignableFrom(Iterable.class, CharSequence.class));
		//
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) throws Throwable {
		try {
			final Object obj = METHOD_IS_ASSIGNABLE_FROM.invoke(null, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}