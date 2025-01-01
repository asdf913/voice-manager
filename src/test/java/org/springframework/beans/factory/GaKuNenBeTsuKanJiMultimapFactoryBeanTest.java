package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.IterableUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.javatuples.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class GaKuNenBeTsuKanJiMultimapFactoryBeanTest {

	private static Method METHOD_CREATE_MULIT_MAP_UNIT_WORK_BOOK, METHOD_CREATE_MULIT_MAP_UNIT_SPREAD_SHEET_DOCUMENT,
			METHOD_GET_ROW_COUNT, METHOD_GET_SHEET_COUNT = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = GaKuNenBeTsuKanJiMultimapFactoryBean.class;
		//
		(METHOD_CREATE_MULIT_MAP_UNIT_WORK_BOOK = clz.getDeclaredMethod("createMulitmapUnit", Workbook.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULIT_MAP_UNIT_SPREAD_SHEET_DOCUMENT = clz.getDeclaredMethod("createMulitmapUnit",
				SpreadsheetDocument.class)).setAccessible(true);
		//
		(METHOD_GET_ROW_COUNT = clz.getDeclaredMethod("getRowCount", Table.class)).setAccessible(true);
		//
		(METHOD_GET_SHEET_COUNT = clz.getDeclaredMethod("getSheetCount", SpreadsheetDocument.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean exists = null;

		private InputStream inputStream = null;

		private List<Sheet> sheets = null;

		private List<Cell> cells = null;

		private Iterator<?> iterator = null;

		private String toString = null;

		private List<Sheet> getSheets() {
			if (sheets == null) {
				sheets = new ArrayList<>();
			}
			return sheets;
		}

		private List<Cell> getCells() {
			if (cells == null) {
				cells = new ArrayList<>();
			}
			return cells;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (ReflectionUtils.isToStringMethod(method)) {
				//
				return toString;
				//
			} // if
				//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof InputStreamSource) {
				//
				if (Objects.equals(methodName, "getInputStream")) {
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
			} else if (proxy instanceof Workbook) {
				//
				if (Objects.equals(methodName, "getNumberOfSheets")) {
					//
					return IterableUtils.size(getSheets());
					//
				} else if (Objects.equals(methodName, "getSheetAt") && args != null && args.length > 0
						&& args[0] instanceof Number) {
					//
					return IterableUtils.get(getSheets(), ((Number) args[0]).intValue());
					//
				} // if
					//
			} else if (proxy instanceof Row) {
				//
				if (Objects.equals(methodName, "getCell") && args != null && args.length > 0
						&& args[0] instanceof Number) {
					//
					return IterableUtils.get(getCells(), ((Number) args[0]).intValue());
					//
				} else if (Objects.equals(methodName, "getLastCellNum")) {
					//
					return Short.valueOf((short) IterableUtils.size(getCells()));
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private GaKuNenBeTsuKanJiMultimapFactoryBean instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new GaKuNenBeTsuKanJiMultimapFactoryBean();
		//
		ih = new IH();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl("A");
			//
		} // if
			//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
				"{localizedMessage=URI is not absolute, message=URI is not absolute}",
				() -> FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(Reflection.newProxy(Resource.class, ih));
			//
		} // if
			//
		if (ih != null) {
			//
			ih.exists = Boolean.FALSE;
			//
		} // if
			//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
				"{localizedMessage=URI is not absolute, message=URI is not absolute}",
				() -> FactoryBeanUtil.getObject(instance));
		//
		if (ih != null) {
			//
			ih.exists = Boolean.TRUE;
			//
		} // if
			//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
				"{localizedMessage=URI is not absolute, message=URI is not absolute}",
				() -> FactoryBeanUtil.getObject(instance));
		//
		try (final InputStream is = new ByteArrayInputStream("".getBytes())) {
			//
			if (ih != null) {
				//
				ih.inputStream = is;
				//
			} // if
				//
			AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
					"{localizedMessage=URI is not absolute, message=URI is not absolute}",
					() -> FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
	}

	@Test
	void testSetTimeout() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field timeout = GaKuNenBeTsuKanJiMultimapFactoryBean.class.getDeclaredField("timeout");
		//
		if (timeout != null) {
			//
			timeout.setAccessible(true);
			//
		} // if
			//
			// null
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(null);
				//
			} // if
				//
		});
		//
		Assertions.assertNull(get(timeout, instance));
		//
		// java.time.Duration
		//
		final Duration duration = Duration.ZERO;
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(duration);
				//
			} // if
				//
		});
		//
		Assertions.assertSame(duration, get(timeout, instance));
		//
		// java.lang.Number
		//
		final long l = 1;
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(Long.valueOf(l));
				//
			} // if
				//
		});
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(Long.toString(l));
				//
			} // if
				//
		});
		//
		Assertions.assertEquals(Duration.ofMillis(l), get(timeout, instance));
		//
		// java.lang.String
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout("");
				//
			} // if
				//
		});
		//
		Assertions.assertEquals(Duration.ofMillis(l), get(timeout, instance));
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(" ");
				//
			} // if
				//
		});
		//
		Assertions.assertEquals(Duration.ofMillis(l), get(timeout, instance));
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.setTimeout(String.format("PT%1$sS", l));
				//
			} // if
				//
		});
		//
		Assertions.assertEquals(Duration.ofMillis(l * 1000), get(timeout, instance));
		//
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Test
	void testCreateMulitmapUnit() throws Throwable {
		//
		Assertions.assertNull(createMulitmapUnit((Workbook) null));
		//
		Assertions.assertNull(createMulitmapUnit((SpreadsheetDocument) null));
		//
		final Workbook wb = Reflection.newProxy(Workbook.class, ih);
		//
		Assertions.assertNull(createMulitmapUnit(wb));
		//
		if (ih != null) {
			//
			ih.getSheets().add(null);
			//
		} // if
			//
		Assertions.assertNull(createMulitmapUnit(wb));
		//
		if (ih != null) {
			//
			ih.getSheets().set(0, Reflection.newProxy(Sheet.class, ih));
			//
		} // if
			//
		Assertions.assertNull(createMulitmapUnit(wb));
		//
		if (ih != null) {
			//
			ih.iterator = Collections.singleton(null).iterator();
			//
		} // if
			//
		Assertions.assertNull(createMulitmapUnit(wb));
		//
		final Row row = Reflection.newProxy(Row.class, ih);
		//
		if (ih != null) {
			//
			ih.iterator = Collections.singleton(row).iterator();
			//
		} // if
			//
		Assertions.assertNull(createMulitmapUnit(wb));
		//
		if (ih != null) {
			//
			ih.iterator = Collections.nCopies(2, row).iterator();
			//
		} // if
			//
		Assertions.assertNull(createMulitmapUnit(wb));
		//
		if (ih != null) {
			//
			ih.iterator = Collections.nCopies(2, row).iterator();
			//
			ih.cells = Collections.nCopies(2, Reflection.newProxy(Cell.class, ih));
			//
		} // if
			//
		Assertions.assertEquals("[{null=[null]}]", Util.toString(createMulitmapUnit(wb)));
		//
		// org.odftoolkit.simple.SpreadsheetDocument
		//
		Assertions.assertNull(
				createMulitmapUnit((SpreadsheetDocument) Narcissus.allocateInstance(SpreadsheetDocument.class)));
		//
	}

	private static Unit<Multimap<String, String>> createMulitmapUnit(final Workbook wb) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULIT_MAP_UNIT_WORK_BOOK.invoke(null, wb);
			if (obj == null) {
				return null;
			} else if (obj instanceof Unit) {
				return (Unit) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Unit<Multimap<String, String>> createMulitmapUnit(final SpreadsheetDocument ssd) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULIT_MAP_UNIT_SPREAD_SHEET_DOCUMENT.invoke(null, ssd);
			if (obj == null) {
				return null;
			} else if (obj instanceof Unit) {
				return (Unit) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetRowCount() throws Throwable {
		//
		Assertions.assertEquals(0, getRowCount(null));
		//
	}

	private static int getRowCount(final Table instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ROW_COUNT.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSheetCount() throws Throwable {
		//
		Assertions.assertEquals(0, getSheetCount(null));
		//
	}

	private static int getSheetCount(final SpreadsheetDocument instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SHEET_COUNT.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}