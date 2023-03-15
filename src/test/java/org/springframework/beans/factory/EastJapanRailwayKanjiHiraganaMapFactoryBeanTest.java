package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Pair;
import org.javatuples.valueintf.IValue0;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;

class EastJapanRailwayKanjiHiraganaMapFactoryBeanTest {

	private static Method METHOD_CREATE_MAP_INPUT_STREAM, METHOD_CREATE_MAP_SHEET, METHOD_CREATE_PAIR_STRING,
			METHOD_CREATE_PAIR_ELEMENT, METHOD_GET_MIME_TYPE, METHOD_GET_MESSAGE, METHOD_MERGE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = EastJapanRailwayKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_CREATE_MAP_INPUT_STREAM = clz.getDeclaredMethod("createMap", InputStream.class, UrlValidator.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MAP_SHEET = clz.getDeclaredMethod("createMap", Sheet.class, String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_PAIR_STRING = clz.getDeclaredMethod("createPair", String.class)).setAccessible(true);
		//
		(METHOD_CREATE_PAIR_ELEMENT = clz.getDeclaredMethod("createPair", Element.class)).setAccessible(true);
		//
		(METHOD_GET_MIME_TYPE = clz.getDeclaredMethod("getMimeType", ContentInfo.class)).setAccessible(true);
		//
		(METHOD_GET_MESSAGE = clz.getDeclaredMethod("getMessage", ContentInfo.class)).setAccessible(true);
		//
		(METHOD_MERGE = clz.getDeclaredMethod("merge", Map.class, Map.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Set<Entry<?, ?>> entrySet = null;

		private Iterator<?> iterator = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Iterable) {
				//
				if (Objects.equals(methodName, "iterator")) {
					//
					return iterator;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return entrySet;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private EastJapanRailwayKanjiHiraganaMapFactoryBean instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new EastJapanRailwayKanjiHiraganaMapFactoryBean();
		//
		ih = new IH();
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
			// org.apache.poi.xssf.usermodel.XSSFWorkbook
			//
		try (final Workbook wb = new XSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = WorkbookUtil.createSheet(wb);
			//
			Row row = null;
			//
			for (int i = 0; sheet != null && i < 1; i++) {
				//
				row = SheetUtil.createRow(sheet, sheet.getPhysicalNumberOfRows());
				//
				for (int j = 0; row != null && j < 2; j++) {
					//
					CellUtil.setCellValue(RowUtil.createCell(row, row.getPhysicalNumberOfCells()), Integer.toString(i));
					//
				} // for
					//
			} // for
				//
			row = sheet != null ? SheetUtil.createRow(sheet, sheet.getPhysicalNumberOfRows()) : null;
			//
			for (int i = 0; row != null && i < 2; i++) {
				//
				CellUtil.setCellValue(RowUtil.createCell(row, row.getPhysicalNumberOfCells()), Integer.toString(i));
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
			Assertions.assertEquals(Collections.emptyMap(), getObject(instance));
			//
		} // try
			//
			// org.apache.poi.xssf.usermodel.HSSFWorkbook
			//
		try (final Workbook wb = new HSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = WorkbookUtil.createSheet(wb);
			//
			Row row = null;
			//
			for (int i = 0; sheet != null && i < 1; i++) {
				//
				row = SheetUtil.createRow(sheet, sheet.getPhysicalNumberOfRows());
				//
				for (int j = 0; row != null && j < 2; j++) {
					//
					CellUtil.setCellValue(RowUtil.createCell(row, row.getPhysicalNumberOfCells()), Integer.toString(i));
					//
				} // for
					//
			} // for
				//
			row = sheet != null ? SheetUtil.createRow(sheet, sheet.getPhysicalNumberOfRows()) : null;
			//
			for (int i = 0; row != null && i < 2; i++) {
				//
				CellUtil.setCellValue(RowUtil.createCell(row, row.getPhysicalNumberOfCells()), Integer.toString(i));
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
			Assertions.assertEquals(Collections.emptyMap(), getObject(instance));
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
		// createMap(java.io.InputStream,org.apache.commons.validator.routines.UrlValidator)
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
			// createMap(org.apache.poi.ss.usermodel.Sheet)
			//
		final Sheet sheet = Reflection.newProxy(Sheet.class, ih);
		//
		Assertions.assertNull(createMap(sheet, null, null));
		//
		if (ih != null) {
			//
			ih.iterator = Collections.singleton(null).iterator();
			//
		} // if
			//
		Assertions.assertNull(createMap(sheet, null, null));
		//
	}

	private static Map<String, String> createMap(final InputStream is, final UrlValidator urlValidator)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP_INPUT_STREAM.invoke(null, is, urlValidator);
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

	private static IValue0<Map<String, String>> createMap(final Sheet sheet, final String kanjiColumnName,
			final String hiraganaColumnName) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP_SHEET.invoke(null, sheet, kanjiColumnName, hiraganaColumnName);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
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

	@Test
	void testGetMimeType() throws Throwable {
		//
		Assertions.assertNull(getMimeType(null));
		//
	}

	private static String getMimeType(final ContentInfo instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_MIME_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMessage() throws Throwable {
		//
		Assertions.assertNull(getMessage(null));
		//
	}

	private static String getMessage(final ContentInfo instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_MESSAGE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMerge() {
		//
		Assertions.assertDoesNotThrow(() -> merge(null, null));
		//
		final Map<String, String> singletonMap = Collections.singletonMap(null, null);
		//
		Assertions.assertDoesNotThrow(() -> merge(null, singletonMap));
		//
		final Map<?, ?> map = Reflection.newProxy(Map.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> merge(null, map));
		//
		if (ih != null) {
			//
			ih.entrySet = Reflection.newProxy(Set.class, ih);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> merge(null, map));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> merge(null, map));
		//
		Assertions.assertDoesNotThrow(() -> merge(new LinkedHashMap<>(singletonMap), singletonMap));
		//
		final Map<String, String> a = new LinkedHashMap<>(singletonMap);

		final Map<String, String> b = Collections.singletonMap(null, "");
		//
		Assertions.assertThrows(IllegalStateException.class, () -> merge(a, b));
		//
	}

	private static <K, V> void merge(final Map<K, V> a, final Map<K, V> b) throws Throwable {
		try {
			METHOD_MERGE.invoke(null, a, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIH() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
			InvocationTargetException {
		//
		Class<?> clz = Class
				.forName("org.springframework.beans.factory.EastJapanRailwayKanjiHiraganaMapFactoryBean$IH");
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Object object = constructor != null ? constructor.newInstance() : null;
		//
		final InvocationHandler ih = object instanceof InvocationHandler ? (InvocationHandler) object : null;
		//
		if (ih != null) {
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(null, null, null));
			//
			// org.springframework.beans.factory.EastJapanRailwayKanjiHiraganaMapFactoryBean$ObjectIntMap
			//
			final Object objectIntMap = Reflection.newProxy(clz = Class.forName(
					"org.springframework.beans.factory.EastJapanRailwayKanjiHiraganaMapFactoryBean$ObjectIntMap"), ih);
			//
			// org.springframework.beans.factory.EastJapanRailwayKanjiHiraganaMapFactoryBean$ObjectIntMap.put(java.lang.Object,int)
			//
			final Method put = clz != null ? clz.getDeclaredMethod("put", Object.class, Integer.TYPE) : null;
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectIntMap, put, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectIntMap, put, new Object[] {}));
			//
			// org.springframework.beans.factory.EastJapanRailwayKanjiHiraganaMapFactoryBean$ObjectIntMap.get(java.lang.Object)
			//
			final Method get = clz != null ? clz.getDeclaredMethod("get", Object.class) : null;
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectIntMap, get, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectIntMap, get, new Object[] {}));
			//
			Assertions.assertThrows(IllegalStateException.class,
					() -> ih.invoke(objectIntMap, get, new Object[] { null }));
			//
			// org.springframework.beans.factory.EastJapanRailwayKanjiHiraganaMapFactoryBean$ObjectIntMap.containsKey(java.lang.Object)
			//
			final Method containsKey = clz != null ? clz.getDeclaredMethod("containsKey", Object.class) : null;
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectIntMap, containsKey, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(objectIntMap, containsKey, new Object[] {}));
			//
		} // if
			//
	}

}