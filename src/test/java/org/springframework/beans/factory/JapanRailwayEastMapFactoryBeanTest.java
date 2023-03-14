package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

class JapanRailwayEastMapFactoryBeanTest {

	private static Method METHOD_CREATE_MAP, METHOD_CREATE_PAIR_STRING, METHOD_CREATE_PAIR_ELEMENT = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = JapanRailwayEastMapFactoryBean.class;
		//
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", InputStream.class, UrlValidator.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_PAIR_STRING = clz.getDeclaredMethod("createPair", String.class)).setAccessible(true);
		//
		(METHOD_CREATE_PAIR_ELEMENT = clz.getDeclaredMethod("createPair", Element.class)).setAccessible(true);
		//
	}

	private JapanRailwayEastMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new JapanRailwayEastMapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrls(new String[] { null, "", " " });
			//
		} // if
			//
		Assertions.assertEquals(Collections.emptyMap(), getObject(instance));
		//
		final File file = new File("pom.xml");
		//
		final String url = file != null ? file.toURI().toURL().toString() : null;
		//
		if (instance != null) {
			//
			instance.setUrls(new String[] { url });
			//
		} // if
			//
		Assertions.assertEquals(Collections.emptyMap(), getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource("".getBytes()));
			//
		} // if
			//
		Assertions.assertNotNull(getObject(instance));
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
			Assertions.assertNotNull(getObject(instance));
			//
		} // try
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = wb.createSheet();
			//
			for (int i = 0; sheet != null && i < 1; i++) {
				//
				sheet.createRow(sheet.getPhysicalNumberOfRows());
				//
			} // for
				//
			final Row row = sheet != null ? sheet.createRow(sheet.getPhysicalNumberOfRows()) : null;
			//
			Cell cell = null;
			//
			for (int i = 0; row != null && i < 2; i++) {
				//
				if ((cell = row.createCell(row.getPhysicalNumberOfCells())) == null) {
					//
					continue;
					//
				} // if
					//
				cell.setCellValue(Integer.toString(i));
				//
			} // for
				//
			wb.write(baos);
			//
			if (instance != null) {
				//
				instance.setResource(new ByteArrayResource(baos.toByteArray()));
				//
			} // if
				//
			Assertions.assertEquals(Collections.singletonMap("0", "1"), getObject(instance));
			//
		} // try
			//
	}

	private static <T> T getObject(final FactoryBean<T> instnace) throws Exception {
		return instnace != null ? instnace.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testCreateMap() throws Throwable {
		//
		try (final InputStream is = new ByteArrayInputStream("1,2,3,4,5".getBytes())) {
			//
			Assertions.assertNull(createMap(is, null));
			//
		} // try
			//
		try (final InputStream is = new ByteArrayInputStream("1,2,3,4,5,6".getBytes())) {
			//
			Assertions.assertNull(createMap(is, null));
			//
		} // try
			//
		final UrlValidator urlValidator = UrlValidator.getInstance();
		//
		try (final InputStream is = new ByteArrayInputStream("1,2,3,4,5,6".getBytes())) {
			//
			Assertions.assertNull(createMap(is, urlValidator));
			//
		} // try
			//
		try (final InputStream is = new ByteArrayInputStream("1,2,3,4,5,http://127.0.0.1".getBytes())) {
			//
			Assertions.assertThrows(ConnectException.class, () -> createMap(is, urlValidator));
			//
		} // try
			//
	}

	private static Map<String, String> createMap(final InputStream is, final UrlValidator urlValidator)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP.invoke(null, is, urlValidator);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreatePair() throws Throwable {
		//
		Assertions.assertNull(createPair((String) null));
		//
		Assertions.assertNull(createPair(""));
		//
		Assertions.assertNull(createPair(" "));
		//
		Assertions.assertNull(createPair((Element) null));
		//
		final String key = "key";
		//
		final String value = "value";
		//
		final Element element = Jsoup.parse(String.format(
				"<span class=\"station_name01\">%1$s</span><span class=\"station_name02\">%2$s</span>", key, value));
		//
		Assertions.assertEquals(Pair.with(key, value), createPair(element));
		//
	}

	private static Pair<String, String> createPair(final String url) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_PAIR_STRING.invoke(null, url);
			if (obj == null) {
				return null;
			} else if (obj instanceof Pair) {
				return (Pair) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Pair<String, String> createPair(final Element document) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_PAIR_ELEMENT.invoke(null, document);
			if (obj == null) {
				return null;
			} else if (obj instanceof Pair) {
				return (Pair) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}