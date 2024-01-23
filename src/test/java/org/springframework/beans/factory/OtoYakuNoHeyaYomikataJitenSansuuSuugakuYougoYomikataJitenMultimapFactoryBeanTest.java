package org.springframework.beans.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_GET_STRINGS, METHOD_CLEAR, METHOD_TO_URL, METHOD_TEST_AND_APPLY, METHOD_LENGTH,
			METHOD_TO_MULTI_MAP_ITERABLE, METHOD_TO_MULTI_MAP_STRING, METHOD_TO_MULTI_MAP2, METHOD_IS_EMPTY = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_GET_STRINGS = clz.getDeclaredMethod("getStrings", String.class, UnicodeBlock.class))
				.setAccessible(true);
		//
		(METHOD_CLEAR = clz.getDeclaredMethod("clear", StringBuilder.class)).setAccessible(true);
		//
		(METHOD_TO_URL = clz.getDeclaredMethod("toURL", URI.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", Object[].class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_ITERABLE = clz.getDeclaredMethod("toMultimap", Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_STRING = clz.getDeclaredMethod("toMultimap", String.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP2 = clz.getDeclaredMethod("toMultimap", String.class, Iterable.class)).setAccessible(true);
		//
		(METHOD_IS_EMPTY = clz.getDeclaredMethod("isEmpty", Multimap.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.url")));
				//
			} // if
				//
				//
			final Multimap<String, String> multimap = instance != null ? instance.getObject() : null;
			//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			final File file = new File(
					"OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.xlsx");
			//
			try (final Workbook wb = WorkbookFactory.create(true); final OutputStream os = new FileOutputStream(file)) {
				//
				final Sheet sheet = WorkbookUtil.createSheet(wb);
				//
				if (Util.iterator(entries) != null) {
					//
					Row row = null;
					//
					for (final Entry<String, String> entry : entries) {
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

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetStrings() throws Throwable {
		//
		Assertions.assertNull(getStrings(null, null));
		//
		Assertions.assertNull(getStrings(" ", null));
		//
		final String string = "あ";
		//
		Assertions.assertEquals(Arrays.asList(string), getStrings(string, UnicodeBlock.HIRAGANA));
		//
		Assertions.assertEquals(Arrays.asList(string),
				getStrings(StringUtils.appendIfMissing(string, "(", ")"), UnicodeBlock.HIRAGANA));
		//
		Assertions.assertEquals(Arrays.asList(string),
				getStrings(StringUtils.appendIfMissing(string, "("), UnicodeBlock.HIRAGANA));
		//
	}

	private static List<String> getStrings(final String string, final UnicodeBlock unicodeBlock) throws Throwable {
		try {
			final Object obj = METHOD_GET_STRINGS.invoke(null, string, unicodeBlock);
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
	void testClear() {
		//
		Assertions.assertDoesNotThrow(() -> clear(null));
		//
	}

	private static void clear(final StringBuilder instance) throws Throwable {
		try {
			METHOD_CLEAR.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToURL() throws Throwable {
		//
		Assertions.assertNull(toURL(null));
		//
		Assertions.assertNull(toURL(new URI("")));
		//
		final URI uri = new URI("http://www.z.cn");
		//
		Assertions.assertEquals(uri.toURL(), toURL(uri));
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
	void testLength() throws Throwable {
		//
		Assertions.assertEquals(0, length(null));
		//
		Assertions.assertEquals(0, length(new Object[] {}));
		//
	}

	private static int length(final Object[] instance) throws Throwable {
		try {
			final Object obj = METHOD_LENGTH.invoke(null, (Object) instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimap() throws Throwable {
		//
		Assertions.assertNull(toMultimap((String) null));
		//
		Assertions.assertNull(toMultimap(""));
		//
		final String space = " ";
		//
		Assertions.assertNull(toMultimap(space));
		//
		Assertions.assertNull(toMultimap("A"));
		//
		Assertions.assertEquals("{半端=[はんぱ]}", Util.toString(toMultimap("関連語：半端（はんぱ）")));
		//
		Assertions.assertEquals("{九十九湾=[つくもわん], 九十九折れ=[つづらおれ]}",
				Util.toString(toMultimap("関連語：九十九湾（つくもわん）九十九折れ（つづらおれ）")));
		//
		Assertions.assertEquals("{R-加群=[あーるかぐん], 左加群=[さかぐん]}", Util.toString(toMultimap("R-加群（あーるかぐん）左加群（さかぐん）")));
		//
		Assertions.assertEquals("{差し金=[さしがね], 長手=[ながて], 短手=[みじかて, みじかで], 妻手=[つまて]}",
				Util.toString(toMultimap("差し金（さしがね）とも言う 長いほうを長手（ながて） 短いほうを短手（みじかて・みじかで）または妻手（つまて）")));
		//
		final Multimap<String, String> multimap = ImmutableMultimap.of();
		//
		Assertions.assertEquals(multimap, toMultimap(Collections.singleton(null)));
		//
		Assertions.assertEquals(multimap,
				toMultimap(Collections.singleton(Util.cast(Element.class, Narcissus.allocateInstance(Element.class)))));
		//
		Assertions.assertNull(toMultimap(null, null));
		//
		Assertions.assertEquals(String.format("{%1$s=[%1$s]}", space),
				Util.toString(toMultimap(space, Collections.singleton(space))));
		//
		Assertions.assertEquals(String.format("{%1$s=[%1$s]}", space),
				Util.toString(toMultimap(space, Collections.nCopies(2, space))));
		//
		Assertions.assertEquals(String.format("{%1$s=[%1$s]}", space),
				Util.toString(toMultimap(String.join("・", space, space), Collections.singleton(space))));
		//
	}

	private static Multimap<String, String> toMultimap(final Iterable<Element> tbodies) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_ITERABLE.invoke(null, tbodies);
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

	private static Multimap<String, String> toMultimap(final String s) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_STRING.invoke(null, s);
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

	private static Multimap<String, String> toMultimap(final String s1, final Iterable<String> ss2) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP2.invoke(null, s1, ss2);
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
	void testIsEmpty() throws Throwable {
		//
		Assertions.assertTrue(isEmpty(ImmutableMultimap.of()));
		//
	}

	private static boolean isEmpty(final Multimap<?, ?> instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_EMPTY.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}