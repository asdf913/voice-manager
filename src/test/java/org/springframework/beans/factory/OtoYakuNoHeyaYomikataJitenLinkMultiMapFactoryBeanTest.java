package org.springframework.beans.factory;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_GET_MULTI_MAP, METHOD_TRIM, METHOD_TEST_AND_APPLY, METHOD_PUT_HREF, METHOD_GET_STRING,
			METHOD_NEW_DOCUMENT_BUILDER, METHOD_PARSE, METHOD_NEW_XPATH, METHOD_EVALUATE, METHOD_GET = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBean.class;
		//
		(METHOD_GET_MULTI_MAP = clz.getDeclaredMethod("getMultimap", Element.class)).setAccessible(true);
		//
		(METHOD_TRIM = clz.getDeclaredMethod("trim", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_PUT_HREF = clz.getDeclaredMethod("putHref", Multimap.class, Element.class)).setAccessible(true);
		//
		(METHOD_GET_STRING = clz.getDeclaredMethod("getString", Cell.class)).setAccessible(true);
		//
		(METHOD_NEW_DOCUMENT_BUILDER = clz.getDeclaredMethod("newDocumentBuilder", DocumentBuilderFactory.class))
				.setAccessible(true);
		//
		(METHOD_PARSE = clz.getDeclaredMethod("parse", DocumentBuilder.class, InputSource.class)).setAccessible(true);
		//
		(METHOD_NEW_XPATH = clz.getDeclaredMethod("newXPath", XPathFactory.class)).setAccessible(true);
		//
		(METHOD_EVALUATE = clz.getDeclaredMethod("evaluate", XPath.class, String.class, Object.class, QName.class))
				.setAccessible(true);
		//
		(METHOD_GET = clz.getDeclaredMethod("get", Field.class, Object.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private byte[] contentAsByteArray = null;

		private InputStream inputStream = null;

		private CellType cellType = null;

		private String stringCellValue = null;

		private Double numericCellValue = null;

		private Object evaluate = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Resource) {
				//
				if (Objects.equals(methodName, "getContentAsByteArray")) {
					//
					return contentAsByteArray;
					//
				} else if (Objects.equals(methodName, "getInputStream")) {
					//
					return inputStream;
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
					//
				} // if
					//
			} else if (proxy instanceof XPath) {
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

	private OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBean instance = null;

	private IH ih = null;

	@BeforeEach
	public void before() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBean();
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
			instance.setUrl(EMPTY);
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl(" ");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(Reflection.newProxy(Resource.class, ih));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final Map<?, ?> systemProperties = System.getProperties();
		//
		if (instance != null && isUrlSet(systemProperties)) {
			//
			instance.setUrl(Util.toString(Util.get(systemProperties,
					"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url")));
			//
			Assertions.assertNotNull(getObject(instance));
			//
		} //
			//
	}

	private static boolean isUrlSet(final Map<?, ?> map) {
		return Util.containsKey(map,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url");
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
	void testGetMultimap() throws Throwable {
		//
		Assertions.assertNull(getMultimap(Util.cast(Element.class, Narcissus.allocateInstance(Element.class))));
		//
	}

	private static Multimap<String, String> getMultimap(final Element element) throws Throwable {
		try {
			final Object obj = METHOD_GET_MULTI_MAP.invoke(null, element);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTrim() throws Throwable {
		//
		Assertions.assertNull(trim(null));
		//
		Assertions.assertEquals(EMPTY, trim(EMPTY));
		//
		final String a = "a";
		//
		Assertions.assertEquals(a, trim(StringUtils.wrap(a, " ")));
		//
	}

	private static String trim(final String string) throws Throwable {
		try {
			final Object obj = METHOD_TRIM.invoke(null, string);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPutHref() throws Throwable {
		//
		if (isUrlSystemPropertiesSet()) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> putHref(null, null));
		//
	}

	private static boolean isUrlSystemPropertiesSet() {
		return isUrlSet(System.getProperties());
	}

	private static void putHref(final Multimap<String, String> m, final Element v) throws Throwable {
		try {
			METHOD_PUT_HREF.invoke(null, m, v);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetString() throws Throwable {
		//
		Assertions.assertNull(getString(null));
		//
		Cell cell = Reflection.newProxy(Cell.class, ih);
		//
		final Cell c = cell;
		//
		Assertions.assertThrows(IllegalStateException.class, () -> getString(c));
		//
		if (ih != null) {
			//
			ih.cellType = CellType.STRING;
			//
		} // if
			//
		Assertions.assertNull(getString(cell));
		//
		if (ih != null) {
			//
			ih.cellType = CellType.NUMERIC;
			//
		} // if
			//
		Assertions.assertEquals(Util.toString(ih != null ? ih.numericCellValue = Double.valueOf(0) : null),
				getString(cell));
		//
		try (final Workbook wb = new XSSFWorkbook()) {
			//
			final Sheet sheet = wb.createSheet();
			//
			final Row row = SheetUtil.createRow(sheet, (sheet != null ? sheet.getPhysicalNumberOfRows() : 0) + 1);
			//
			if ((cell = RowUtil.createCell(row, (row != null ? row.getPhysicalNumberOfCells() : 0) + 1)) != null) {
				//
				cell.setCellValue(true);
				//
			} // if
				//
			Assertions.assertEquals(Boolean.toString(true), getString(cell));
			//
			if ((cell = RowUtil.createCell(row, (row != null ? row.getPhysicalNumberOfCells() : 0) + 1)) != null) {
				//
				cell.setBlank();
				//
			} // if
				//
			Assertions.assertEquals(EMPTY, getString(cell));
			//
		} // try
			//
	}

	private static String getString(final Cell cell) throws Throwable {
		try {
			final Object obj = METHOD_GET_STRING.invoke(null, cell);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testNewDocumentBuilder() throws Throwable {
		//
		Assertions.assertNull(newDocumentBuilder(null));
		//
	}

	private static DocumentBuilder newDocumentBuilder(final DocumentBuilderFactory instance) throws Throwable {
		try {
			final Object obj = METHOD_NEW_DOCUMENT_BUILDER.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof DocumentBuilder) {
				return (DocumentBuilder) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testParse() throws Throwable {
		//
		Assertions.assertNull(parse(null, null));
		//
		final DocumentBuilder documentBuilder = newDocumentBuilder(DocumentBuilderFactory.newDefaultInstance());
		//
		Assertions.assertNull(parse(documentBuilder, null));
		//
		try (final Reader r = new StringReader(EMPTY)) {
			//
			Assertions.assertThrows(Throwable.class, () -> parse(documentBuilder, new InputSource(r)));
			//
		} // try
			//
		try (final Reader r = new StringReader("<a/>")) {
			//
			Assertions.assertNotNull(parse(documentBuilder, new InputSource(r)));
			//
		} // try
			//
	}

	private static Document parse(final DocumentBuilder instance, final InputSource is) throws Throwable {
		try {
			final Object obj = METHOD_PARSE.invoke(null, instance, is);
			if (obj == null) {
				return null;
			} else if (obj instanceof Document) {
				return (Document) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testNewXPath() throws Throwable {
		//
		Assertions.assertNull(newXPath(null));
		//
	}

	private static XPath newXPath(final XPathFactory instance) throws Throwable {
		try {
			final Object obj = METHOD_NEW_XPATH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof XPath) {
				return (XPath) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testEvaluate() throws Throwable {
		//
		Assertions.assertNull(evaluate(null, null, null, null));
		//
		Assertions.assertNull(evaluate(Reflection.newProxy(XPath.class, ih), EMPTY, EMPTY, null));
		//
		final XPath xp = newXPath(XPathFactory.newDefaultInstance());
		//
		Assertions.assertNull(evaluate(xp, null, EMPTY, null));
		//
		Assertions.assertNull(evaluate(xp, EMPTY, EMPTY, null));
		//
	}

	private static Object evaluate(final XPath instance, final String expression, final Object item,
			final QName returnType) throws Throwable {
		try {
			return METHOD_EVALUATE.invoke(null, instance, expression, item, returnType);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGet() throws Throwable {
		//
		Assertions.assertSame(Boolean.TRUE, get(Boolean.class.getDeclaredField("TRUE"), null));
		//
	}

	private static Object get(final Field field, final Object instance) throws Throwable {
		try {
			return METHOD_GET.invoke(null, field, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}