package org.springframework.beans.factory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

import com.google.common.collect.Multimap;

class YojijukugoMultimapFactoryBeanTest {

	private static Method METHOD_TO_STRING, METHOD_TEST = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = YojijukugoMultimapFactoryBean.class;
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
	}

	private YojijukugoMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new YojijukugoMultimapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(getObject(instance));
		//
		instance.setUrl("");
		//
		Assertions.assertNull(getObject(instance));
		//
		instance.setUrl(" ");
		//
		Assertions.assertNull(getObject(instance));
		//
		instance.setUrl(new File("pom.xml").toURI().toURL().toString());
		//
		Assertions.assertNull(getObject(instance));
		//
		// org.springframework.beans.factory.YojijukugoMultimapFactoryBean.resource
		//
		instance.setResource(new ByteArrayResource("".getBytes()));
		//
		Assertions.assertNull(getObject(instance));
		//
		byte[] bs = null;
		//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			wb.write(baos);
			//
			bs = baos.toByteArray();
			//
		} // try
			//
		instance.setResource(new ByteArrayResource(bs));
		//
		Assertions.assertNull(getObject(instance));
		//
		// 1 Sheet and 1 Row
		//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = wb.createSheet();
			//
			if (sheet != null) {
				//
				sheet.createRow(sheet.getPhysicalNumberOfRows());
				//
			} // if
				//
			wb.write(baos);
			//
			bs = baos.toByteArray();
			//
		} // try
			//
			// 2 Row(s) with 2 Cell(s) respectively
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = wb.createSheet();
			//
			// First Row
			//
			Row row = sheet != null ? sheet.createRow(sheet.getPhysicalNumberOfRows()) : null;
			//
			if (row != null) {
				//
				IntStream.range(0, 2).forEach(row::createCell);
				//
			} // if
				//
				// Second Row
				//
			if ((row = sheet != null ? sheet.createRow(sheet.getPhysicalNumberOfRows()) : null) != null) {
				//
				IntStream.range(0, 2).forEach(row::createCell);
				//
			} // if
				//
			wb.write(baos);
			//
			bs = baos.toByteArray();
			//
		} // try
			//
		instance.setResource(new ByteArrayResource(bs));
		//
		Assertions.assertNull(toString(getObject(instance)));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
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
			throw new Throwable(obj != null ? toString(obj.getClass()) : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
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

}