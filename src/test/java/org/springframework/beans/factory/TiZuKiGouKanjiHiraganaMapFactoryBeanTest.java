package org.springframework.beans.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class TiZuKiGouKanjiHiraganaMapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_TEXT, METHOD_TEST_AND_APPLY, METHOD_TO_MAP_ITERABLE, METHOD_TO_MAP_STRING,
			METHOD_TO_MAP2, METHOD_TO_MAP3, METHOD_SPLIT, METHOD_ALL_MATCH, METHOD_GET_STRING_BY_UNICODE_BLOCK,
			METHOD_TO_URL = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = TiZuKiGouKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_TEXT = clz.getDeclaredMethod("text", Elements.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_MAP_ITERABLE = clz.getDeclaredMethod("toMap", Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_MAP_STRING = clz.getDeclaredMethod("toMap", String.class)).setAccessible(true);
		//
		(METHOD_TO_MAP2 = clz.getDeclaredMethod("toMap", Element.class, String.class)).setAccessible(true);
		//
		(METHOD_TO_MAP3 = clz.getDeclaredMethod("toMap", Iterable.class, Iterable.class, UnaryOperator.class))
				.setAccessible(true);
		//
		(METHOD_SPLIT = clz.getDeclaredMethod("split", String.class, String.class)).setAccessible(true);
		//
		(METHOD_ALL_MATCH = clz.getDeclaredMethod("allMatch", String.class, UnicodeBlock.class)).setAccessible(true);
		//
		(METHOD_GET_STRING_BY_UNICODE_BLOCK = clz.getDeclaredMethod("getStringByUnicodeBlock", String.class,
				UnicodeBlock.class)).setAccessible(true);
		//
		(METHOD_TO_URL = clz.getDeclaredMethod("toURL", URI.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Link) {
				//
				if (Objects.equals(methodName, "getText")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "getUrl")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "getDescription")) {
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

	private TiZuKiGouKanjiHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new TiZuKiGouKanjiHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(null));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final Link link = Reflection.newProxy(Link.class, new IH());
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.nCopies(2, link));
			//
			instance.setText(null);
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(link));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setText("");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setDescription(null);
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setDescription("");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setDescription(null);
			//
			instance.setLinks(Collections.nCopies(2, link));
			//
			FieldUtils.writeDeclaredField(instance, "text", null, true);
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.TiZuKiGouKanjiHiraganaMapFactoryBean.url")) {
			//
			instance.setUrl(Util.toString(Util.get(properties,
					"org.springframework.beans.factory.TiZuKiGouKanjiHiraganaMapFactoryBean.url")));
			//
			final File file = new File("TiZuKiGouKanjiHiraganaMapFactoryBean.xlsx");
			//
			try (final Workbook wb = WorkbookFactory.create(true); final OutputStream os = new FileOutputStream(file)) {
				//
				final Sheet sheet = WorkbookUtil.createSheet(wb);
				//
				final Iterable<Entry<String, String>> entrySet = Util.entrySet(getObject(instance));
				//
				if (Util.iterator(entrySet) != null) {
					//
					Row row = null;
					//
					for (final Entry<String, String> entry : entrySet) {
						//
						if (entry == null) {
							//
							continue;
						} // if
							//
						if (sheet != null && sheet.getPhysicalNumberOfRows() == 0) {
							//
							CellUtil.setCellValue(createCell(row = createRow(sheet)), "kanji");
							//
							CellUtil.setCellValue(createCell(row), "hiragana");
							//
						} // if
							//
						CellUtil.setCellValue(createCell(row = createRow(sheet)), Util.getKey(entry));
						//
						CellUtil.setCellValue(createCell(row), Util.getValue(entry));
						//
					} // for
						//
				} // if
					//
				WorkbookUtil.write(wb, os);
				//
			} // try
				//
			System.out.println(file.getAbsolutePath());
			//
		} // if
			//
	}

	private static Row createRow(final Sheet instance) {
		return instance != null ? instance.createRow(instance.getPhysicalNumberOfRows()) : null;
	}

	private static Cell createCell(final Row instance) {
		return instance != null ? instance.createCell(instance.getPhysicalNumberOfCells()) : null;
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testText() throws Throwable {
		//
		Assertions.assertNull(text(null));
		//
		Assertions.assertEquals(EMPTY, text(Util.cast(Elements.class, Narcissus.allocateInstance(Elements.class))));
		//
	}

	private static String text(final Elements instance) throws Throwable {
		try {
			final Object obj = METHOD_TEXT.invoke(null, instance);
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
	void testToMap() throws Throwable {
		//
		Assertions.assertNull(toMap(Collections.singleton(null)));
		//
		Assertions.assertNull(toMap(""));
		//
		Assertions.assertNull(toMap(" "));
		//
		Assertions.assertNull(toMap(null, "a"));
		//
		Assertions.assertEquals(Collections.singletonMap(EMPTY, null), toMap(null, "（"));
		//
		Assertions.assertEquals(Collections.singletonMap(EMPTY, null), toMap(null, "・"));
		//
		String string = "中";
		//
		Assertions.assertEquals(Collections.singletonMap(string, null), toMap(null, string));
		//
		final String one = "1";
		//
		Assertions.assertEquals(Collections.singletonMap(string = "車線", null),
				toMap(null, StringUtils.joinWith("", one, string)));
		//
		Assertions.assertEquals(Collections.singletonMap(string = "以外", null),
				toMap(null, StringUtils.joinWith("", one, string)));
		//
		Assertions.assertEquals(Collections.singletonMap(string = "支庁界", null),
				toMap(null, StringUtils.joinWith("", one, string)));
		//
		Assertions.assertEquals(Collections.singletonMap(StringUtils.joinWith("", one, string = "科樹林"), "null"),
				toMap(null, StringUtils.joinWith("", one, string)));
		//
		Assertions.assertNull(
				toMap(Collections.singleton(Util.cast(Element.class, Narcissus.allocateInstance(Element.class)))));
		//
		Assertions.assertNull(toMap(Collections.singleton(null), null, null));
		//
		Assertions.assertEquals(Collections.singletonMap(null, null),
				toMap(Collections.singleton(null), Collections.singleton(null), null));
		//
	}

	private static Map<String, String> toMap(final Iterable<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_TO_MAP_ITERABLE.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Map<String, String> toMap(final String url) throws Throwable {
		try {
			final Object obj = METHOD_TO_MAP_STRING.invoke(null, url);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Map<String, String> toMap(final Element e, final String kanji) throws Throwable {
		try {
			final Object obj = METHOD_TO_MAP2.invoke(null, e, kanji);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException ex) {
			throw ex.getTargetException();
		}
	}

	private static Map<String, String> toMap(final Iterable<String> ss1, final Iterable<String> ss2,
			final UnaryOperator<String> function) throws Throwable {
		try {
			final Object obj = METHOD_TO_MAP3.invoke(null, ss1, ss2, function);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSplit() throws Throwable {
		//
		Assertions.assertNull(split(EMPTY, null));
		//
		Assertions.assertArrayEquals(new String[] { EMPTY }, split(EMPTY, EMPTY));
		//
	}

	private static String[] split(final String a, final String b) throws Throwable {
		try {
			final Object obj = METHOD_SPLIT.invoke(null, a, b);
			if (obj == null) {
				return null;
			} else if (obj instanceof String[]) {
				return (String[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAllMatch() throws Throwable {
		//
		Assertions.assertTrue(allMatch("a", null));
		//
		Assertions.assertFalse(allMatch("a", UnicodeBlock.HIRAGANA));
		//
		Assertions.assertTrue(allMatch("a", UnicodeBlock.BASIC_LATIN));
		//
	}

	private static boolean allMatch(final String string, final UnicodeBlock unicodeBlock) throws Throwable {
		try {
			final Object obj = METHOD_ALL_MATCH.invoke(null, string, unicodeBlock);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetStringByUnicodeBlock() throws Throwable {
		//
		Assertions.assertEquals("a", getStringByUnicodeBlock("a", null));
		//
		Assertions.assertEquals("a", getStringByUnicodeBlock("a", UnicodeBlock.BASIC_LATIN));
		//
	}

	private static String getStringByUnicodeBlock(final String string, final UnicodeBlock unicodeBlock)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_STRING_BY_UNICODE_BLOCK.invoke(null, string, unicodeBlock);
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
	void testToURL() throws Throwable {
		//
		Assertions.assertNull(toURL(null));
		//
		Assertions.assertNull(toURL(Util.cast(URI.class, Narcissus.allocateInstance(URI.class))));
		//
	}

	private static URL toURL(final URI instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_URL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URL) {
				return (URL) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}