package org.springframework.beans.factory;

import java.io.File;
import java.io.OutputStream;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.text.TextStringBuilder;
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
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_GET_STRINGS, METHOD_TO_URL, METHOD_TEST_AND_APPLY, METHOD_TO_MULTI_MAP_ITERABLE,
			METHOD_TO_MULTI_MAP_STRING_STRING, METHOD_TO_MULTI_MAP2, METHOD_GET_OBJECTS, METHOD_APPEND = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_GET_STRINGS = clz.getDeclaredMethod("getStrings", String.class, UnicodeBlock.class,
				UnicodeBlock[].class)).setAccessible(true);
		//
		(METHOD_TO_URL = clz.getDeclaredMethod("toURL", URI.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_ITERABLE = clz.getDeclaredMethod("toMultimap", Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_STRING_STRING = clz.getDeclaredMethod("toMultimap", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP2 = clz.getDeclaredMethod("toMultimap", String.class, Iterable.class)).setAccessible(true);
		//
		(METHOD_GET_OBJECTS = clz.getDeclaredMethod("getObjects", Iterable.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_APPEND = clz.getDeclaredMethod("append", TextStringBuilder.class, Character.TYPE)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Link
					&& Util.contains(Arrays.asList("getText", "getUrl", "getDescription"), methodName)) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

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
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setText(null);
			//
			instance.setLinks(Collections.singleton(null));
			//
		} // if
			//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		final Link link = Reflection.newProxy(Link.class, new IH());
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(link));
			//
		} // if
			//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.nCopies(2, link));
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setText(EMPTY);
			//
			instance.setDescription(null);
			//
			instance.setLinks(Collections.singleton(link));
			//
		} // if
			//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.nCopies(2, link));
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> FactoryBeanUtil.getObject(instance));
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setLinks(null);
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.url")));
				//
			} // if
				//
			final Multimap<String, String> multimap = FactoryBeanUtil.getObject(instance);
			//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			final File file = Path
					.of("OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.xlsx").toFile();
			//
			try (final Workbook wb = WorkbookFactory.create(true);
					final OutputStream os = Files.newOutputStream(file != null ? file.toPath() : null)) {
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
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
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
		final Strings strings = Strings.CS;
		//
		Assertions.assertEquals(Arrays.asList(string),
				getStrings(appendIfMissing(strings, string, "(", ")"), UnicodeBlock.HIRAGANA));
		//
		Assertions.assertEquals(Arrays.asList(string),
				getStrings(appendIfMissing(strings, string, "("), UnicodeBlock.HIRAGANA));
		//
	}

	private static String appendIfMissing(final Strings instance, final String str, final CharSequence suffix,
			final CharSequence... suffixes) {
		return instance != null ? instance.appendIfMissing(str, suffix, suffixes) : null;
	}

	private static List<String> getStrings(final String string, final UnicodeBlock ub, final UnicodeBlock... ubs)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_STRINGS.invoke(null, string, ub, ubs);
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
		Assertions.assertDoesNotThrow(() -> {
			//
			FailableStreamUtil.forEach(new FailableStream<>(Util.filter(
					Arrays.stream(OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.class
							.getDeclaredMethods()),
					m -> m != null && Objects.equals("clear", Util.getName(m)) && m.getParameterCount() == 1
							&& Modifier.isStatic(m.getModifiers()))),
					m -> {
						//
						if (m != null) {
							//
							m.setAccessible(true);
							//
							m.invoke(null, (Object) null);
							//
						} // if
							//
					});
			//
		});
		//
	}

	@Test
	void testToURL() throws Throwable {
		//
		Assertions.assertNull(toURL(null));
		//
		Assertions.assertNull(toURL(new URI(EMPTY)));
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
	void testToMultimap() throws Throwable {
		//
		Assertions.assertNull(toMultimap(null, (String) null));
		//
		Assertions.assertNull(toMultimap(null, EMPTY));
		//
		final String space = " ";
		//
		Assertions.assertNull(toMultimap(null, space));
		//
		Assertions.assertNull(toMultimap(null, "A"));
		//
		Assertions.assertEquals("{半端=[はんぱ]}", Util.toString(toMultimap(null, "関連語：半端（はんぱ）")));
		//
		Assertions.assertEquals("{九十九湾=[つくもわん], 九十九折れ=[つづらおれ]}",
				Util.toString(toMultimap(null, "関連語：九十九湾（つくもわん）九十九折れ（つづらおれ）")));
		//
		Assertions.assertEquals("{R-加群=[あーるかぐん], 左加群=[さかぐん]}",
				Util.toString(toMultimap(null, "R-加群（あーるかぐん）左加群（さかぐん）")));
		//
		Assertions.assertEquals("{差し金=[さしがね], 長手=[ながて], 短手=[みじかて, みじかで], 妻手=[つまて]}",
				Util.toString(toMultimap(null, "差し金（さしがね）とも言う 長いほうを長手（ながて） 短いほうを短手（みじかて・みじかで）または妻手（つまて）")));
		//
		Assertions.assertEquals("{霰=[あられ], 雹=[ひょう]}", Util.toString(toMultimap(null, "霰（あられ）、雹（ひょう）")));
		//
		Assertions.assertEquals("{共軛=[きょうやく]}", Util.toString(toMultimap(null, "は共軛（きょうやく）とも")));
		//
		Assertions.assertEquals("{ISO=[あいそ]}", Util.toString(toMultimap("ISO", "（あいそ）")));
		//
		Assertions.assertEquals("{立米=[りゅうべい]}", Util.toString(toMultimap(null, "（立米・りゅうべい）")));
		//
		Assertions.assertEquals("{正多角形=[せいたかっけい]}", Util.toString(toMultimap("正多角形", "（せいたかっけい）とも")));
		//
		Assertions.assertEquals("{十進法=[じゅっしんほう]}", Util.toString(toMultimap("十進法", "（じゅっしんほう）")));
		//
		Assertions.assertEquals("{零行列=[ぜろぎょうれつ]}", Util.toString(toMultimap("零行列", "（ぜろぎょうれつ）")));
		//
		Assertions.assertEquals("{六十進法=[ろくじゅっしんほう]}", Util.toString(toMultimap("六十進法", "（ろくじゅっしんほう）")));
		//
		Assertions.assertEquals("{平均余命=[へいきんよみょう]}", Util.toString(toMultimap("平均余命", "（へいきんよみょう）")));
		//
		Assertions.assertEquals("{2階導関数=[にかいどうかんすう]}",
				Util.toString(toMultimap("第2次導関数", "2階導関数（にかいどうかんすう）とも言われるとのこと")));
		//
		final Multimap<String, String> multimap = ImmutableMultimap.of();
		//
		Assertions.assertEquals(multimap, toMultimap(Collections.singleton(null)));
		//
		Assertions.assertEquals(multimap,
				toMultimap(Collections.singleton(Util.cast(Element.class, Narcissus.allocateInstance(Element.class)))));
		//
		Assertions.assertNull(toMultimap(null, (Iterable) null));
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

	private static Multimap<String, String> toMultimap(final String s1, final String s3) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_STRING_STRING.invoke(null, s1, s3);
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
	void testGetObjects() throws Throwable {
		//
		Assertions.assertNull(getObjects(null, null, null));
		//
	}

	private static Collection<Object> getObjects(final Iterable<Class<?>> iterable, final Object a, final Object b)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_OBJECTS.invoke(null, iterable, a, b);
			if (obj == null) {
				return null;
			} else if (obj instanceof Collection) {
				return (Collection) obj;
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
		Assertions.assertDoesNotThrow(() -> append(
				Util.cast(TextStringBuilder.class, Narcissus.allocateInstance(TextStringBuilder.class)), ' '));
		//
	}

	private static void append(final TextStringBuilder instance, final char c) throws Throwable {
		try {
			METHOD_APPEND.invoke(null, instance, c);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}