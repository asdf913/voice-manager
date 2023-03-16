package org.springframework.beans.factory;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriPredicate;
import org.springframework.core.io.ByteArrayResource;

class StringMapFromResourceFactoryBeanTest {

	private static Method METHOD_TEST_AND_ACCEPT = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		(METHOD_TEST_AND_ACCEPT = StringMapFromResourceFactoryBean.class.getDeclaredMethod("testAndAccept",
				TriPredicate.class, Object.class, Object.class, Object.class, TriConsumer.class)).setAccessible(true);
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

}