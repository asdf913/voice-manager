package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
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

	private static class IH implements InvocationHandler {

		private Boolean exists = null;

		private boolean reset = true;

		private InputStream inputStream = null;

		private Iterator<?> iterator = null;

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
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private JlptVocabularyListFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new JlptVocabularyListFactoryBean();
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
			instance.setUrls(Arrays.asList(null, "", " "));
			//
		} // if
			//
		final IH ih = new IH();
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
		ih.exists = Boolean.TRUE;
		//
		Assertions.assertNull(getObject(instance));
		//
		try (final InputStream is = new ByteArrayInputStream("".getBytes())) {
			//
			ih.inputStream = is;
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
			ih.inputStream = is;
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
			CellUtil.setCellValue(RowUtil.createCell(row, 1), "");
			//
			// Second Row
			//
			CellUtil.setCellValue(RowUtil.createCell(row = SheetUtil.createRow(sheet, 1), 0), level);
			//
			CellUtil.setCellValue(RowUtil.createCell(row, 1), "");
			//
			workbook.write(baos);
			//
			bs = baos.toByteArray();
			//
		} // try
			//
		try (final InputStream is = new ByteArrayInputStream(bs)) {
			//
			ih.inputStream = is;
			//
			ih.reset = true;
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
			ih.inputStream = is;
			//
			ih.reset = false;
			//
			Assertions.assertThrows(EmptyFileException.class, () -> getObject(instance));
			//
		} // try
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

}