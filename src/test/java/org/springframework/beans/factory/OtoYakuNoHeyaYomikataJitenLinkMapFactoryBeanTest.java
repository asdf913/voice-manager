package org.springframework.beans.factory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.CreationHelperUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.RowUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Consumers;
import org.meeuw.functional.Predicates;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.Link;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenLinkMapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Class<?> CLASS_IH = null;

	private static Method METHOD_GET_LINKS, METHOD_VALUE_OF, METHOD_OR_ELSE, METHOD_FIND_FIRST, METHOD_TRIM,
			METHOD_APPEND, METHOD_TEST_AND_APPLY, METHOD_IS_ABSOLUTE, METHOD_APPLY,
			METHOD_SET_DESCRIPTION_AND_TEXT_AND_URL, METHOD_ADD_LINKS, METHOD_HAS_ATTR, METHOD_IIF, METHOD_GET_IMG,
			METHOD_FOR_EACH, METHOD_PUT_ALL, METHOD_GET_STRING_CELL_VALUE, METHOD_GET_SHEET = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.class;
		//
		(METHOD_GET_LINKS = clz.getDeclaredMethod("getLinks", List.class)).setAccessible(true);
		//
		(METHOD_VALUE_OF = clz.getDeclaredMethod("valueOf", String.class)).setAccessible(true);
		//
		(METHOD_OR_ELSE = clz.getDeclaredMethod("orElse", Optional.class, Object.class)).setAccessible(true);
		//
		(METHOD_FIND_FIRST = clz.getDeclaredMethod("findFirst", Stream.class)).setAccessible(true);
		//
		(METHOD_TRIM = clz.getDeclaredMethod("trim", String.class)).setAccessible(true);
		//
		(METHOD_APPEND = clz.getDeclaredMethod("append", StringBuilder.class, Character.TYPE)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_IS_ABSOLUTE = clz.getDeclaredMethod("isAbsolute", URI.class)).setAccessible(true);
		//
		(METHOD_APPLY = clz.getDeclaredMethod("apply", FailableFunction.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_SET_DESCRIPTION_AND_TEXT_AND_URL = clz.getDeclaredMethod("setDescriptionAndTextAndUrl", Element.class,
				CLASS_IH = Class
						.forName("org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$IH"),
				Element.class)).setAccessible(true);
		//
		(METHOD_ADD_LINKS = clz.getDeclaredMethod("addLinks", Collection.class, Element.class, Collection.class,
				Element.class,
				Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$ObjectMap"),
				String.class)).setAccessible(true);
		//
		(METHOD_HAS_ATTR = clz.getDeclaredMethod("hasAttr", Element.class, String.class)).setAccessible(true);
		//
		(METHOD_IIF = clz.getDeclaredMethod("iif", Boolean.TYPE, Integer.TYPE, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_IMG = clz.getDeclaredMethod("getImg", Element.class)).setAccessible(true);
		//
		(METHOD_FOR_EACH = clz.getDeclaredMethod("forEach", Iterable.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_PUT_ALL = clz.getDeclaredMethod("putAll", Map.class, Map.class)).setAccessible(true);
		//
		(METHOD_GET_STRING_CELL_VALUE = clz.getDeclaredMethod("getStringCellValue", Cell.class, FormulaEvaluator.class))
				.setAccessible(true);
		//
		(METHOD_GET_SHEET = clz.getDeclaredMethod("getSheet", Workbook.class, String.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "findFirst")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Iterable) {
				//
				if (Objects.equals(methodName, "forEach")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "putAll")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof RichTextString) {
				//
				if (Objects.equals(methodName, "getString")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Cell) {
				//
				if (Objects.equals(methodName, "getStringCellValue")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean instance = null;

	private Element element = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean();
		//
		element = Util.cast(Element.class, Narcissus.allocateInstance(Element.class));
		//
		ih = new IH();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		if (instance != null) {
			//
			instance.setTitle("音訳の部屋読み方辞典");
			//
			instance.setUrlMap(Collections.singletonMap(
					"http://www.gsi.go.jp/KIDS/map-sign-tizukigou-h14kigou-itiran.htm",
					"https://web.archive.org/web/20211126172558/http://www.gsi.go.jp/KIDS/map-sign-tizukigou-h14kigou-itiran.htm"));
			//
		} // if
			//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url")));
				//
			} // if
				//
			final List<Link> links = instance != null ? instance.getObject() : null;
			//
			new FailableStream<>(ObjectUtils.getIfNull(Util.stream(links), Stream::empty)).forEach(x -> {
				//
				System.out.println(ObjectMapperUtil
						.writeValueAsString(new ObjectMapper().setDefaultPropertyInclusion(Include.NON_NULL)
								.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true), x));
				//
			});
			//
			final Workbook wb = createWorkbook(links);
			//
			final File file = new File(String.format("links_%1$tY%1$tm%1$td.xlsx", new Date()));
			//
			System.out.println(file.getAbsolutePath());
			//
			try (final OutputStream os = new FileOutputStream(file)) {
				//
				wb.write(os);
				//
			} // try
				//
		} else {
			//
			Assertions.assertNull(instance != null ? instance.getObject() : null);
			//
		} // if
			//
	}

	private static Workbook createWorkbook(final Iterable<Link> links)
			throws IOException, IllegalAccessException, InvocationTargetException {
		//
		final Workbook wb = WorkbookFactory.create(true);
		//
		final Sheet sheet = WorkbookUtil.createSheet(wb);
		//
		final List<Method> ms = Util.toList(Util.filter(Arrays.stream(getDeclaredMethods(Link.class)),
				x -> !(Objects.equals(Void.TYPE, x.getReturnType()) || Modifier.isStatic(x.getModifiers())
						|| x.getParameterCount() != 0 || ReflectionUtils.isHashCodeMethod(x)
						|| ReflectionUtils.isToStringMethod(x))));
		//
		Row row = null;
		//
		Cell cell = null;
		//
		Method m = null;
		//
		final int physicalNumberOfRows = sheet != null ? sheet.getPhysicalNumberOfRows() : null;
		//
		if (physicalNumberOfRows == 0 && (row = SheetUtil.createRow(sheet, physicalNumberOfRows)) != null) {
			//
			Pattern pattern = null;
			//
			Matcher matcher = null;
			//
			String name = null;
			//
			for (int i = 0; ms != null && i < ms.size(); i++) {
				//
				if ((m = ms.get(i)) == null || (cell = RowUtil.createCell(row, i)) == null) {
					//
					continue;
					//
				} // if
					//
				name = Util.getName(m);
				//
				if (Util.matches(matcher = Util.matcher(
						pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("^get(\\w+)$")),
						Util.getName(m))) && matcher.groupCount() > 0) {
					//
					name = matcher.group(1);
					//
				} // if
					//
				CellUtil.setCellValue(cell, name);
				//
			} // for
				//
		} // if
			//
		if (links != null && links.iterator() != null) {
			//
			for (final Link link : links) {
				//
				if (sheet == null) {
					//
					continue;
					//
				} // if
					//
				row = SheetUtil.createRow(sheet, sheet.getPhysicalNumberOfRows());
				//
				for (int i = 0; ms != null && i < ms.size(); i++) {
					//
					if ((m = ms.get(i)) == null || (cell = RowUtil.createCell(row, i)) == null) {
						//
						continue;
						//
					} // if
						//
					CellUtil.setCellValue(cell, Util.toString(m.invoke(link)));
					//
				} // for
					//
			} // for
				//
		} // if
			//
		if (sheet != null) {
			//
			sheet.setAutoFilter(new CellRangeAddress(sheet.getFirstRowNum(), sheet.getLastRowNum() - 1,
					row.getFirstCellNum(), row.getLastCellNum() - 1));
			//
		} // if
			//
		return wb;
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertSame(List.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testAfterPropertiesSet() throws IOException {
		//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
		Workbook wb = WorkbookFactory.create(true);
		//
		instance.setResource(new ByteArrayResource(toByteArray(wb)));
		//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
		instance.setResource(new ByteArrayResource(toByteArray(wb = WorkbookFactory.create(false))));
		//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
	}

	private static byte[] toByteArray(final Workbook instance) throws IOException {
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			if (instance != null) {
				//
				instance.write(baos);
				//
			} // if
				//
			return baos.toByteArray();
			//
		} // try
			//
	}

	private static void setCellValue(final Cell instance, final Object value) {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		if (value instanceof Byte) {
			//
			instance.setCellValue(((Byte) value).byteValue());
			//
		} else if (value instanceof Calendar) {
			//
			instance.setCellValue((Calendar) value);
			//
		} else if (value instanceof Date) {
			//
			instance.setCellValue((Date) value);
			//
		} else if (value instanceof Double) {
			//
			instance.setCellValue(((Double) value).doubleValue());
			//
		} else if (value instanceof LocalDate) {
			//
			instance.setCellValue((LocalDate) value);
			//
		} else if (value instanceof RichTextString) {
			//
			instance.setCellValue((RichTextString) value);
			//
		} else if (value instanceof String) {
			//
			instance.setCellValue((String) value);
			//
		} //
			//
	}

	private static void afterPropertiesSet(final InitializingBean instance) throws Exception {
		if (instance != null) {
			instance.afterPropertiesSet();
		}
	}

	@Test
	void testGetLinks() throws Throwable {
		//
		Assertions.assertNull(getLinks(Collections.singletonList(null)));
		//
		Assertions.assertEquals(Collections.emptyList(), getLinks(Collections.singletonList(element)));
		//
	}

	private static List<Link> getLinks(final List<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_GET_LINKS.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testValueOf() throws Throwable {
		//
		Assertions.assertNull(valueOf(null));
		//
		Assertions.assertNull(valueOf(EMPTY));
		//
		Assertions.assertNull(valueOf(" "));
		//
		Assertions.assertNull(valueOf("A"));
		//
		final int one = 1;
		//
		Assertions.assertEquals(Integer.valueOf(one), valueOf(Integer.toString(one)));
		//
	}

	private static Integer valueOf(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_VALUE_OF.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOrElse() throws Throwable {
		//
		Assertions.assertNull(orElse(Util.cast(Optional.class, Narcissus.allocateInstance(Optional.class)), null));
		//
	}

	private static <T> T orElse(final Optional<T> instance, final T value) throws Throwable {
		try {
			return (T) METHOD_OR_ELSE.invoke(null, instance, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFindFirst() throws Throwable {
		//
		Assertions.assertNull(findFirst(Reflection.newProxy(Stream.class, ih)));
		//
	}

	private static <T> Optional<T> findFirst(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_FIND_FIRST.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Optional) {
				return (Optional) obj;
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
		Assertions.assertEquals("A", trim(" A "));
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
	void testAppend() {
		//
		Assertions.assertDoesNotThrow(() -> append(null, ' '));
		//
	}

	private static void append(final StringBuilder instance, final char c) throws Throwable {
		try {
			METHOD_APPEND.invoke(null, instance, c);
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
		//
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
	void testIsAbsolute() throws Throwable {
		//
		Assertions.assertFalse(isAbsolute(null));
		//
		final URI uri = Util.cast(URI.class, Narcissus.allocateInstance(URI.class));
		//
		Assertions.assertFalse(isAbsolute(uri));
		//
		Narcissus.setField(uri, URI.class.getDeclaredField("scheme"), EMPTY);
		//
		Assertions.assertTrue(isAbsolute(uri));
		//
	}

	private static boolean isAbsolute(final URI instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_ABSOLUTE.invoke(null, instance);
			if (obj instanceof Boolean) {
				return (Boolean) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testApply() throws Throwable {
		//
		Assertions.assertNull(apply(null, null, null));
		//
		Assertions.assertNull(apply(a -> null, null, null));
		//
		Assertions.assertNull(apply(a -> {
			throw new RuntimeException();
		}, null, null));
		//
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> function, final T value,
			final R defaultValue) throws Throwable {
		try {
			return (R) METHOD_APPLY.invoke(null, function, value, defaultValue);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetDescriptionAndTextAndUrl() {
		//
		Assertions.assertDoesNotThrow(() -> setDescriptionAndTextAndUrl(null, null, null));
		//
	}

	private static void setDescriptionAndTextAndUrl(final Element a1, final Object ih, final Element a2)
			throws Throwable {
		try {
			METHOD_SET_DESCRIPTION_AND_TEXT_AND_URL.invoke(null, a1, ih, a2);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddLinks() {
		//
		Assertions.assertDoesNotThrow(() -> addLinks(null, null, null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> addLinks(null, null, Collections.singleton(null), null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> addLinks(null, null, Collections.singleton(element), null, null, null));
		//
	}

	private static void addLinks(final Collection<Link> links, final Element a1, final Collection<Element> as2,
			// final int childrenSize, final int offset,
			final Element e, final Object objectMap, final String imgSrc) throws Throwable {
		try {
			METHOD_ADD_LINKS.invoke(null, links, a1, as2, e, objectMap, imgSrc);
		} catch (final InvocationTargetException ex) {
			throw ex.getTargetException();
		}
	}

	@Test
	void testHasAttr() throws Throwable {
		//
		Assertions.assertFalse(hasAttr(null, null));
		//
		Assertions.assertFalse(hasAttr(element, null));
		//
		Assertions.assertFalse(hasAttr(element, EMPTY));
		//
		if (element != null) {
			//
			element.attributes().put(EMPTY, EMPTY);
			//
		} // if
			//
		Assertions.assertTrue(hasAttr(element, EMPTY));
		//
	}

	private static boolean hasAttr(final Element instance, final String attributeKey) throws Throwable {
		try {
			final Object obj = METHOD_HAS_ATTR.invoke(null, instance, attributeKey);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void tsetIif() throws Throwable {
		//
		Assertions.assertEquals(0, iif(true, 0, 1));
		//
	}

	private static int iif(final boolean condition, final int trueValue, final int falseValue) throws Throwable {
		try {
			final Object obj = METHOD_IIF.invoke(null, condition, trueValue, falseValue);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetImg() throws Throwable {
		//
		Assertions.assertNull(getImg(null));
		//
	}

	private static Element getImg(final Element instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_IMG.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Element) {
				return (Element) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForEach() {
		//
		final Iterable<Object> iterable = Collections.emptyList();
		//
		Assertions.assertDoesNotThrow(() -> forEach(iterable, null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(iterable, Consumers.nop()));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Reflection.newProxy(Iterable.class, ih), null));
		//
	}

	private static <T> void forEach(final Iterable<T> instance, final Consumer<? super T> action) throws Throwable {
		try {
			METHOD_FOR_EACH.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPutAll() {
		//
		Assertions.assertDoesNotThrow(() -> putAll(null, null));
		//
		final Map<Object, Object> map = Collections.emptyMap();
		//
		Assertions.assertDoesNotThrow(() -> putAll(map, map));
		//
		Assertions.assertDoesNotThrow(() -> putAll(Reflection.newProxy(Map.class, ih), null));
		//
	}

	private static <K, V> void putAll(final Map<K, V> a, final Map<? extends K, ? extends V> b) throws Throwable {
		try {
			METHOD_PUT_ALL.invoke(null, a, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetStringCellValue() throws Throwable {
		//
		Assertions.assertNull(getStringCellValue(null, null));
		//
		Assertions.assertNull(getStringCellValue(Reflection.newProxy(Cell.class, ih), null));
		//
		// org.apache.poi.hssf.usermodel.HSSFCell
		//
		Cell cell = Util.cast(Cell.class,
				Narcissus.allocateInstance(Class.forName("org.apache.poi.hssf.usermodel.HSSFCell")));
		//
		Assertions.assertNull(getStringCellValue(cell, null));
		//
		Workbook wb = WorkbookFactory.create(false);
		//
		Assertions.assertEquals(EMPTY, getStringCellValue(
				cell = RowUtil.createCell(SheetUtil.createRow(WorkbookUtil.createSheet(wb), 0), 0), null));
		//
		CellUtil.setCellValue(cell, EMPTY);
		//
		Assertions.assertEquals(EMPTY, getStringCellValue(cell, null));
		//
		final boolean b = true;
		//
		if (cell != null) {
			//
			cell.setCellValue(b);
			//
		} // if
			//
		Assertions.assertEquals(Boolean.toString(b), getStringCellValue(cell, null));
		//
		final Date date = new Date(0);
		//
		if (cell != null) {
			//
			cell.setCellValue(date);
			//
		} // if
			//
		Assertions.assertTrue(Util.contains(Arrays.asList("25569.375", "25569.0"), getStringCellValue(cell, null)));
		//
		final Calendar calendar = Calendar.getInstance();
		//
		if (calendar != null) {
			//
			calendar.setTime(date);
			//
		} // if
			//
		if (cell != null) {
			//
			cell.setCellValue(calendar);
			//
		} // if
			//
		Assertions.assertTrue(Util.contains(Arrays.asList("25569.375", "25569.0"), getStringCellValue(cell, null)));
		//
		if (cell != null) {
			//
			cell.setCellValue(LocalDate.of(2001, 1, 2));
			//
		} // if
			//
		Assertions.assertEquals("36893.0", getStringCellValue(cell, null));
		//
		if (cell != null) {
			//
			cell.setCellValue(Reflection.newProxy(RichTextString.class, ih));
			//
		} // if
			//
		Assertions.assertEquals(EMPTY, getStringCellValue(cell, null));
		//
		setCellFormula(cell, "1/0");
		//
		Assertions.assertEquals(cell.getCellFormula(), getStringCellValue(cell, null));
		//
		FormulaEvaluator formulaEvaluator = CreationHelperUtil
				.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb));
		//
		Assertions.assertEquals("7", getStringCellValue(cell, formulaEvaluator));
		//
		setCellFormula(cell = RowUtil.createCell(SheetUtil.createRow(WorkbookUtil.createSheet(wb), 0), 0), "true");
		//
		Assertions.assertEquals(Boolean.toString(true), getStringCellValue(cell, formulaEvaluator));
		//
		setCellFormula(cell = RowUtil.createCell(SheetUtil.createRow(WorkbookUtil.createSheet(wb), 0), 0), "1+2");
		//
		Assertions.assertEquals("3.0", getStringCellValue(cell, formulaEvaluator));
		//
		setCellFormula(cell = RowUtil.createCell(SheetUtil.createRow(WorkbookUtil.createSheet(wb), 0), 0),
				"concat(\"\")");
		//
		Assertions.assertEquals(EMPTY, getStringCellValue(cell, formulaEvaluator));
		//
		// org.apache.poi.xssf.usermodel.XSSFCell
		//
		Assertions.assertEquals(EMPTY,
				getStringCellValue(
						cell = RowUtil.createCell(
								SheetUtil.createRow(WorkbookUtil.createSheet(wb = WorkbookFactory.create(true)), 0), 0),
						null));
		//
		if (cell != null) {
			//
			cell.setCellValue(b);
			//
		} // if
			//
		Assertions.assertEquals(Boolean.toString(b), getStringCellValue(cell, null));
		//
		if (cell != null) {
			//
			cell.setCellValue(date);
			//
		} // if
			//
		Assertions.assertEquals("25569.375", getStringCellValue(cell, null));
		//
		Assertions.assertNull(getStringCellValue(Util.cast(Cell.class,
				Narcissus.allocateInstance(Class.forName("org.apache.poi.xssf.usermodel.XSSFCell"))), null));
		//
		setCellFormula(cell, "1/0");
		//
		Assertions.assertEquals(cell.getCellFormula(), getStringCellValue(cell, null));
		//
		Assertions.assertEquals("7", getStringCellValue(cell,
				formulaEvaluator = CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb))));
		//
		setCellFormula(cell = RowUtil.createCell(SheetUtil.createRow(WorkbookUtil.createSheet(wb), 0), 0), "true");
		//
		Assertions.assertEquals(Boolean.toString(true), getStringCellValue(cell, formulaEvaluator));
		//
		setCellFormula(cell = RowUtil.createCell(SheetUtil.createRow(WorkbookUtil.createSheet(wb), 0), 0), "1+2");
		//
		Assertions.assertEquals("3.0", getStringCellValue(cell, formulaEvaluator));
		//
		setCellFormula(cell = RowUtil.createCell(SheetUtil.createRow(WorkbookUtil.createSheet(wb), 0), 0),
				"concat(\"\")");
		//
		Assertions.assertEquals(EMPTY, getStringCellValue(cell, formulaEvaluator));
		//
		// org.apache.poi.xssf.streaming.SXSSFCell
		//
		Assertions.assertEquals(EMPTY,
				getStringCellValue(cell = RowUtil
						.createCell(SheetUtil.createRow(WorkbookUtil.createSheet(wb = new SXSSFWorkbook()), 0), 0),
						null));
		//
		if (cell != null) {
			//
			cell.setCellValue(b);
			//
		} // if
			//
		Assertions.assertEquals(Boolean.toString(b), getStringCellValue(cell, null));
		//
		if (cell != null) {
			//
			cell.setCellValue(date);
			//
		} // if
			//
		Assertions.assertEquals("25569.375", getStringCellValue(cell, null));
		//
		Assertions.assertNull(getStringCellValue(Util.cast(Cell.class,
				Narcissus.allocateInstance(Class.forName("org.apache.poi.xssf.usermodel.XSSFCell"))), null));
		//
		setCellFormula(cell, "1/0");
		//
		Assertions.assertEquals(cell.getCellFormula(), getStringCellValue(cell, null));
		//
		Assertions.assertEquals("7", getStringCellValue(cell,
				formulaEvaluator = CreationHelperUtil.createFormulaEvaluator(WorkbookUtil.getCreationHelper(wb))));
		//
		setCellFormula(cell = RowUtil.createCell(SheetUtil.createRow(WorkbookUtil.createSheet(wb), 0), 0), "true");
		//
		Assertions.assertEquals(Boolean.toString(true), getStringCellValue(cell, formulaEvaluator));
		//
		setCellFormula(cell = RowUtil.createCell(SheetUtil.createRow(WorkbookUtil.createSheet(wb), 0), 0), "1+2");
		//
		Assertions.assertEquals("3.0", getStringCellValue(cell, formulaEvaluator));
		//
		setCellFormula(cell = RowUtil.createCell(SheetUtil.createRow(WorkbookUtil.createSheet(wb), 0), 0),
				"concat(\"\")");
		//
		Assertions.assertEquals(EMPTY, getStringCellValue(cell, formulaEvaluator));
		//
		Assertions.assertNull(getStringCellValue(Util.cast(Cell.class,
				Narcissus.allocateInstance(Class.forName("org.apache.poi.xssf.streaming.SXSSFCell"))), null));
		//
	}

	private static void setCellFormula(final Cell instance, final String formula)
			throws FormulaParseException, IllegalStateException {
		if (instance != null) {
			instance.setCellFormula(formula);
		}
	}

	private static String getStringCellValue(final Cell instance, final FormulaEvaluator formulaEvaluator)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_STRING_CELL_VALUE.invoke(null, instance, formulaEvaluator);
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
	void testGetSheet() throws Throwable {
		//
		Assertions.assertNull(getSheet(null, null));
		//
		Assertions.assertNull(getSheet(WorkbookFactory.create(true), null));
		//
		Assertions.assertNull(getSheet(WorkbookFactory.create(false), null));
		//
		Assertions.assertNull(getSheet(new SXSSFWorkbook(), null));
		//
	}

	private static Sheet getSheet(final Workbook instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_SHEET.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Sheet) {
				return (Sheet) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIH() throws ClassNotFoundException {
		//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, Narcissus.allocateInstance(CLASS_IH));
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, null, null, null));
		//
		// org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$Link
		//
		Class<?> clz = Class
				.forName("org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$Link");
		//
		final Object link = Reflection.newProxy(clz, ih);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, link, null, null));
		//
		new FailableStream<>(Arrays.stream(ObjectUtils.getIfNull(getDeclaredMethods(clz), () -> new Method[] {})))
				.forEach(m -> {
					//
					if (!Objects.equals(Void.TYPE, m != null ? m.getReturnType() : null)) {
						//
						Assertions.assertNull(invoke(ih, link, m, null));
						//
					} // if
						//
				});
		//
		// org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$ObjectMap
		//
		final Object objectMap = Reflection.newProxy(
				clz = Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$ObjectMap"),
				ih);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, objectMap, null, null));
		//
		new FailableStream<>(Arrays.stream(ObjectUtils.getIfNull(getDeclaredMethods(clz), () -> new Method[] {})))
				.forEach(m -> {
					//
					Assertions.assertThrows(Throwable.class, () -> invoke(ih, objectMap, m, null));
					//
					if (Objects.equals(Util.getName(m), "getObject")) {
						//
						Assertions.assertThrows(Throwable.class, () -> invoke(ih, objectMap, m, new Object[] {}));
						//
						Assertions.assertThrows(IllegalStateException.class,
								() -> invoke(ih, objectMap, m, new Object[] { null }));
						//
					} // if
						//
				});
		//
		// org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$IntMap
		//
		final Object intMap = Reflection.newProxy(clz = Class
				.forName("org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$IntMap"), ih);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, objectMap, null, null));
		//
		new FailableStream<>(Arrays.stream(ObjectUtils.getIfNull(getDeclaredMethods(clz), () -> new Method[] {})))
				.forEach(m -> {
					//
					Assertions.assertThrows(Throwable.class, () -> invoke(ih, intMap, m, null));
					//
					if (Objects.equals(Util.getName(m), "getInt")) {
						//
						Assertions.assertThrows(Throwable.class, () -> invoke(ih, intMap, m, new Object[] {}));
						//
						Assertions.assertThrows(Throwable.class, () -> invoke(ih, intMap, m, new Object[] { null }));
						//
						Assertions.assertThrows(IllegalStateException.class,
								() -> invoke(ih, intMap, m, new Object[] { null, null }));
						//
					} // if
						//
				});
		//
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	private static Object invoke(final InvocationHandler instance, final Object proxy, final Method method,
			final Object[] args) throws Throwable {
		return instance != null ? instance.invoke(proxy, method, args) : null;
	}

}