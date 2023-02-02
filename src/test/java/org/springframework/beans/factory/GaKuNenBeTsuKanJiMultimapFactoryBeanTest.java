package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.javatuples.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;

class GaKuNenBeTsuKanJiMultimapFactoryBeanTest {

	private static Method METHOD_GET_CLASS, METHOD_TO_STRING, METHOD_CREATE_MULIT_MAP_UNIT, METHOD_IS_XLSX,
			METHOD_GET_STRING_VALUE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = GaKuNenBeTsuKanJiMultimapFactoryBean.class;
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_CREATE_MULIT_MAP_UNIT = clz.getDeclaredMethod("createMulitmapUnit", Workbook.class))
				.setAccessible(true);
		//
		(METHOD_IS_XLSX = clz.getDeclaredMethod("isXlsx", Resource.class)).setAccessible(true);
		//
		(METHOD_GET_STRING_VALUE = clz.getDeclaredMethod("getStringValue", org.odftoolkit.simple.table.Cell.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean exists, hasNext = null;

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
			} else if (proxy instanceof Iterator) {
				//
				if (Objects.equals(methodName, "hasNext")) {
					//
					return hasNext;
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
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl("A");
			//
		} // if
			//
		Assertions.assertThrows(MalformedURLException.class, () -> getObject(instance));
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
		Assertions.assertThrows(MalformedURLException.class, () -> getObject(instance));
		//
		if (ih != null) {
			//
			ih.exists = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertThrows(MalformedURLException.class, () -> getObject(instance));
		//
		try (final InputStream is = new ByteArrayInputStream("".getBytes())) {
			//
			if (ih != null) {
				//
				ih.inputStream = is;
				//
			} // if
				//
			Assertions.assertThrows(MalformedURLException.class, () -> getObject(instance));
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
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null));
		//
		Assertions.assertEquals(String.class, getClass(""));
		//
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testCreateMulitmapUnit() throws Throwable {
		//
		Assertions.assertNull(createMulitmapUnit(null));
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
		Assertions.assertEquals("[{null=[null]}]", toString(createMulitmapUnit(wb)));
		//
	}

	private static Unit<Multimap<String, String>> createMulitmapUnit(final Workbook wb) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULIT_MAP_UNIT.invoke(null, wb);
			if (obj == null) {
				return null;
			} else if (obj instanceof Unit) {
				return (Unit) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsXlsx() throws Throwable {
		//
		Assertions.assertFalse(isXlsx(null));
		//
		final byte[] bs = "".getBytes();
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				final ZipOutputStream zos = new ZipOutputStream(baos)) {
			//
			final ZipEntry entry = new ZipEntry("");
			//
			zos.putNextEntry(entry);
			//
			zos.write(bs);
			//
			zos.closeEntry();
			//
			zos.close();
			//
			Assertions.assertFalse(isXlsx(new ByteArrayResource(baos.toByteArray())));
			//
		} // try
			//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				final ZipOutputStream zos = new ZipOutputStream(baos)) {
			//
			final ZipEntry entry = new ZipEntry("[Content_Types].xml");
			//
			zos.putNextEntry(entry);
			//
			zos.write(bs);
			//
			zos.closeEntry();
			//
			zos.close();
			//
			Assertions.assertFalse(isXlsx(new ByteArrayResource(baos.toByteArray())));
			//
		} // try
			//
	}

	private static boolean isXlsx(final Resource resource) throws Throwable {
		try {
			final Object obj = METHOD_IS_XLSX.invoke(null, resource);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetStringValue() throws Throwable {
		//
		Assertions.assertNull(getStringValue(null));
		//
	}

	private static String getStringValue(final org.odftoolkit.simple.table.Cell instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_STRING_VALUE.invoke(null, instance);
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