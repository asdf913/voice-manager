package org.springframework.beans.factory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.valueintf.IValue0;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import com.google.common.collect.Multimap;

import io.github.toolfactory.narcissus.Narcissus;

class JapaneseNameMultimapFactoryBeanTest {

	private static Method METHOD_CREATE_MULTI_MAP_ELEMENT, METHOD_CREATE_MULTI_MAP_WORK_BOOK,
			METHOD_CREATE_MULTI_MAP_BY_URL = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = JapaneseNameMultimapFactoryBean.class;
		//
		(METHOD_CREATE_MULTI_MAP_ELEMENT = clz.getDeclaredMethod("createMultimap", Element.class, Pattern.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP_WORK_BOOK = clz.getDeclaredMethod("createMultimap", Workbook.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP_BY_URL = clz.getDeclaredMethod("createMultimapByUrl", String.class, String[].class))
				.setAccessible(true);
		//
	}

	private JapaneseNameMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new JapaneseNameMultimapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(getObject(instance));
		//
		instance.setUrl(new File("pom.xml").toURI().toURL().toString());
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
		setResource(instance, new ByteArrayResource(bs));
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
		setResource(instance, new ByteArrayResource(bs));
		//
		Assertions.assertNull(getObject(instance));
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
		setResource(instance, new ByteArrayResource(bs));
		//
		Assertions.assertEquals("{=[]}", Util.toString(getObject(instance)));
		//
		// xls
		//
		try (final Workbook wb = new HSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			wb.write(baos);
			//
			bs = baos.toByteArray();
			//
		} // try
			//
		setResource(instance, new ByteArrayResource(bs));
		//
		Assertions.assertNull(getObject(instance));
		//
	}

	private static void setResource(final JapaneseNameMultimapFactoryBean instance, final Resource resource) {
		if (instance != null) {
			instance.setResource(resource);
		}
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testCreateMultimap() throws Throwable {
		//
		Assertions.assertNull(createMultimap(null));
		//
		Assertions.assertNull(createMultimap(null, null));
		//
		Assertions.assertNull(createMultimap(cast(Element.class, Narcissus.allocateInstance(Element.class)), null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static Multimap<String, String> createMultimap(final Element input, final Pattern pattern)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP_ELEMENT.invoke(null, input, pattern);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static IValue0<Multimap<String, String>> createMultimap(final Workbook wb) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP_WORK_BOOK.invoke(null, wb);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMultimapByUrl() throws Throwable {
		//
		final String url = Util.toString(new File("pom.xml").toURI().toURL());
		//
		AssertionsUtil.assertThrowsAndEquals(ValidationException.class,
				"{localizedMessage=java.net.URISyntaxException: Expected authority at index 7: file://, message=java.net.URISyntaxException: Expected authority at index 7: file://}",
				() -> createMultimapByUrl(url, null));
		//
		AssertionsUtil.assertThrowsAndEquals(ValidationException.class,
				"{localizedMessage=java.net.URISyntaxException: Expected authority at index 7: file://, message=java.net.URISyntaxException: Expected authority at index 7: file://}",
				() -> createMultimapByUrl(url, new String[] {}));
		//
	}

	private static Multimap<String, String> createMultimapByUrl(final String url, final String[] allowProtocols)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP_BY_URL.invoke(null, url, allowProtocols);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(obj != null ? Util.toString(obj.getClass()) : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}