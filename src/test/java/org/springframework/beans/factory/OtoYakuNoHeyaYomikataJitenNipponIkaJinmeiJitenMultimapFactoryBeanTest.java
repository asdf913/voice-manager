package org.springframework.beans.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.nutch.util.DeflateUtils;
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
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_CREATE_MULTI_MAP, METHOD_CREATE_MULTI_MAP2, METHOD_TO_URL, METHOD_TEST_AND_APPLY,
			METHOD_REMOVE, METHOD_TO_MULTI_MAP_MAP, METHOD_TO_MULTI_MAP_ITERABLE, METHOD_TO_MULTI_MAP_TABLE,
			METHOD_GET_SHEET_BY_NAME = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.class;
		//
		(METHOD_CREATE_MULTI_MAP = clz.getDeclaredMethod("createMultimap", Iterable.class, Multimap.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP2 = clz.getDeclaredMethod("createMultimap2", Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_URL = clz.getDeclaredMethod("toURL", URI.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_REMOVE = clz.getDeclaredMethod("remove", Multimap.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_MAP = clz.getDeclaredMethod("toMultimap", Map.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_ITERABLE = clz.getDeclaredMethod("toMultimap", Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_TABLE = clz.getDeclaredMethod("toMultimap", Table.class)).setAccessible(true);
		//
		(METHOD_GET_SHEET_BY_NAME = clz.getDeclaredMethod("getSheetByName", SpreadsheetDocument.class, String.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean remove = null;

		private Set<Entry<?, ?>> entrySet = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Multimap) {
				//
				if (Objects.equals(methodName, "remove")) {
					//
					return remove;
					//
				} // if
					//
			} else if (proxy instanceof Link) {
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
			} else if (proxy instanceof Map) {
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

	private OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean();
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
			instance.setText(null);
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(null));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final Link link = Reflection.newProxy(Link.class, ih);
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
			instance.setLinks(Collections.nCopies(2, link));
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
		FieldUtils.writeDeclaredField(instance, "text", null, true);
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
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setLinks(null);
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.url")));
				//
				final Properties p = createProperties(
						OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBeanTest.class,
						"/configuration.properties");
				//
				if (p != null && Util.containsKey(p,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.toBeRemoved")) {
					//
					instance.setToBeRemoved(p.getProperty(
							"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.toBeRemoved"));
					//
				} else {
					//
					instance.setToBeRemoved(
							"[{\"後藤艮山\":\"もあり\"},{\"菅原ミネ嗣\":[\"はくさ\",\"に\"]},{\"本間ソウ軒\":[\"は\",\"を\",\"つ\",\"ねた\",\"です\"]}]");
					//
				} // if
					//
			} // if
				//
			final Multimap<String, String> multimap = getObject(instance);
			//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			final File file = new File("OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimap.xlsx");
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
		if (instance != null) {
			//
			instance.setResource(new ClassPathResource("/OtoYakuNoHeyaYomikataJiten.xlsx"));
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		if (instance != null) {
			//
			instance.setSheetName(null);
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final Decoder decoder = Base64.getDecoder();
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(DeflateUtils.inflate(decode(decoder,
					"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"))));
			//
		} // if
			//
		Assertions.assertEquals("{a=[b]}", Util.toString(getObject(instance)));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(DeflateUtils.inflate(decode(decoder,
					"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"))));
			//
		} // if
			//
		Assertions.assertEquals("{a=[b]}", Util.toString(getObject(instance)));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(DeflateUtils.inflate(decode(decoder,
					"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"))));
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(DeflateUtils.inflate(decode(decoder,
					"eJztWF1sFFUUPnd2t8xuS7strT9F6tDoUujWUHmwxmAXCiIJ2FpqFEOi02Xarmy7ZFmiJhqXIg8mkmgw8YXEkPTFaFRiog+a2L75oMGYkCBPxTeiD4PBGBLp+p0zM3S6ZXW2Gn9wz+TMvf3m3jln7znnu3f6zbnm+TNn2y9RmTxEIVooRanOhymo7v0RJ9JcbKFUKnlwqSb/KbnuthzDMOIXgXLMV7lxjbptTW5NGaYcrgIZtJOm0ObpxXIq+F25DRnjf1+QOQsBxwWVmv2V22f+Zh4PQcvrn7me6z8GrYc2QFdDG6FNJFsANUNboGugrdA24pwguh16h2ur3W1Z16Hf4f5tUI1b/mmJ6ohiXYQ+W/0Vh1xifwkZ8VF4TvLie+gBOsy5MWBm08bfJdvFB1OxD7NI0q3oKToNtJE+FPRzuW+V7IOkyOhzM/qqSonvJ+XeKfdG4vmfypyLgvTiyZdcPa+84RZBVO2no2DCXcKIE5ShNEa1+0dxhahtYMkMmZT9U087wg00w3W2y5qy8mZ2Xupnhn6WuvBk1mCcpwt+NRiuVYnT/w6fwdoZtCjeenY74+1yfGMFPFkB31QBjy7DT2morSKVuI0XQ9I2F+ukbSmGpV1TXCVtazFSelZy+AQyej8TNip234RlFXpfR40+jmtO6VCikZOKfqCnmantYbFVZzMjcyl7J3e/bqF6m2vpC/C+TscUmF+fRSWRnqKOK8z14H4de4AObtd/Af2n9TZK3/ggcLifReMNwyGW+BJiaYDhg3CIS7FZnIjD1K/vXvl27+hQ/zOCFxc/MeheTX6hOoYnc+FNMqNb7tMytpXuofVEiYFE50hm0jpiPGY9bwznJs2p5LA1fjRr5jsTvfcntrUhfIk/HDdkjltGYui4GHhV7gk4s0Xkcv8GX78L/eM9F6Z7Ltj9G339M+CUKH6ikmuakiqpvDleq+gJvFunl2U/JfVafZO7bI20VH6kIV4s+1FnoWzdjRRHsIlC9mbiNmx3CR6147RcNIrJPJ7De/vDWgt9Ih91Kd+odfw+p+syFY0jzOO6/0WK9wvuxnwzFXbwUMw5g8R1TbBZ59Eja3Hbm0nnc0dyYwVj5wtpK2s8+EDPiDlqZbOWZMT2zNhY303cDizKSeYVy0KJ37H8FbwM8yfe+ena4ET8vTd16t7w8Xe83i+Rk8r8nB3n81GKnDXZQ8456SlyzkoHyTkvHSbHycvXnbMP9zfT0ggs67+1/uy5PReVH6/Gf0a081+fP33f2vipt+F/8toHO4BFyrAD5JzTPBaI+3ythNfk1pG/8vtP8qSMA242h5km5VLLk7Cep0M0Kn4cqtr/FmQlW+SNwuOnINLn/mNL0QDsTqJGB+HDcyuyH6LFb7Agc+6E7ibP/iDOglbVdj2Jwb7/PB9kDvNyq9uP0D6ceSdxmRL73ciCMYkJIwWcVXNAKkuXa5/3lqD274K+f8P+DlhIiw+WZGB1/vSt4PffDS0EGRhQqrX/b/r+/w3g9SeV"))));
			//
		} // if
			//
		Assertions.assertNull(Util.toString(getObject(instance)));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(DeflateUtils.inflate(decode(decoder,
					"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"))));
			//
		} // if
			//
		Assertions.assertEquals("{1=[2]}", Util.toString(getObject(instance)));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(DeflateUtils.inflate(decode(decoder,
					"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"))));
			//
			instance.setSheetName("Sheet1");
			//
		} // if
			//
		Assertions.assertEquals("{1=[2]}", Util.toString(getObject(instance)));
		//
		FieldUtils.writeDeclaredField(instance, "sheetName", null, true);
		//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.sheetName")) {
			//
			if (instance != null) {
				//
				instance.setSheetName(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.sheetName")));
				//
			} // if
				//
			Assertions.assertNull(getObject(instance));
			//
		} // if
			//
	}

	private static byte[] decode(final Decoder instance, final String src) {
		return instance != null ? instance.decode(src) : null;
	}

	private static Row createRow(final Sheet instance) {
		return instance != null ? instance.createRow(instance.getPhysicalNumberOfRows()) : null;
	}

	private static Cell createCell(final Row instance) {
		return instance != null ? instance.createCell(instance.getPhysicalNumberOfCells()) : null;
	}

	private static Properties createProperties(final Class<?> clz, final String url) throws IOException {
		//
		Properties p = null;
		//
		try (final InputStream is = clz != null ? clz.getResourceAsStream(url) : null) {
			//
			if ((p = is != null ? new Properties() : null) != null) {
				//
				p.load(is);
				//
			} // if
				//
		} // try
			//
		return p;
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testSetToBeRemoved() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance == null) {
				//
				return;
				//
			} // if
				//
			instance.setToBeRemoved(null);
			//
			instance.setToBeRemoved(EMPTY);
			//
			instance.setToBeRemoved(" ");
			//
		});
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			if (instance != null) {
				//
				instance.setToBeRemoved("[{\"\":{}}]");
				//
			} // if
				//
		});
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			if (instance != null) {
				//
				instance.setToBeRemoved("[1]");
				//
			} // if
				//
		});
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			if (instance != null) {
				//
				instance.setToBeRemoved("[{\"\":[{}]}]");
				//
			} // if
				//
		});
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			if (instance != null) {
				//
				instance.setToBeRemoved("1");
				//
			} // if
				//
		});
	}

	@Test
	void testCreateMultimap1() throws Throwable {
		//
		Assertions.assertNull(createMultimap(
				Arrays.asList(null, Util.cast(Element.class, Narcissus.allocateInstance(Element.class))), null));
		//
	}

	private static Multimap<String, String> createMultimap(final Iterable<Element> es,
			final Multimap<String, String> toBeRemoved) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP.invoke(null, es, toBeRemoved);
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
	void testCreateMultimap2() throws Throwable {
		//
		Assertions.assertNull(createMultimap2(Collections.singleton(null)));
		//
	}

	private static Multimap<String, String> createMultimap2(final Iterable<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP2.invoke(null, es);
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
	void testToURL() throws Throwable {
		//
		Assertions.assertNull(toURL(null));
		//
		final URI uri = Util.cast(URI.class, Narcissus.allocateInstance(URI.class));
		//
		Assertions.assertNull(toURL(uri));
		//
		Narcissus.setField(uri, URI.class.getDeclaredField("scheme"), EMPTY);
		//
		Assertions.assertThrows(MalformedURLException.class, () -> toURL(uri));
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
	void testRemove() {
		//
		Assertions.assertDoesNotThrow(() -> remove(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> remove(LinkedHashMultimap.create(), null, null));
		//
		final IH ih = new IH();
		//
		ih.remove = Boolean.TRUE;
		//
		Assertions.assertDoesNotThrow(() -> remove(Reflection.newProxy(Multimap.class, ih), null, null));
		//
	}

	private static void remove(final Multimap<?, ?> instance, final Object key, final Object value) throws Throwable {
		try {
			METHOD_REMOVE.invoke(null, instance, key, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimap() throws Throwable {
		//
		Assertions.assertNull(toMultimap((Map<?, ?>) null));
		//
		Assertions.assertNull(toMultimap((Iterable<?>) null));
		//
		Assertions.assertNull(toMultimap((Table) null));
		//
		Assertions.assertEquals("{=[]}", Util.toString(toMultimap(Collections.singletonMap(EMPTY, EMPTY))));
		//
		Assertions.assertEquals("{=[]}",
				Util.toString(toMultimap(Collections.singletonMap(EMPTY, Collections.singleton(EMPTY)))));
		//
		Assertions.assertThrows(IllegalStateException.class,
				() -> toMultimap(Collections.singletonMap(EMPTY, Collections.singleton(Collections.emptyMap()))));
		//
		Assertions.assertThrows(IllegalStateException.class,
				() -> toMultimap(Collections.singletonMap(EMPTY, Collections.emptyMap())));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertNull(toMultimap(Reflection.newProxy(Map.class, ih)));
		//
		Assertions.assertEquals("{=[]}",
				Util.toString(toMultimap(Collections.singleton(Collections.singletonMap(EMPTY, EMPTY)))));
		//
		Assertions.assertEquals("{=[]}", Util.toString(
				toMultimap(Collections.singleton(Collections.singletonMap(EMPTY, Collections.singleton(EMPTY))))));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertEquals(ImmutableMultimap.of(),
				toMultimap(Collections.singleton(Reflection.newProxy(Map.class, ih))));
		//
		Assertions.assertNull(toMultimap(Util.cast(Table.class, Narcissus.allocateInstance(Table.class))));
		//
	}

	private static Multimap<String, String> toMultimap(final Iterable<?> iterable) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_ITERABLE.invoke(null, iterable);
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

	private static Multimap<String, String> toMultimap(final Map<?, ?> m) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_MAP.invoke(null, m);
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

	private static Multimap<String, String> toMultimap(final Table table) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_TABLE.invoke(null, table);
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
	void testGetSheetByName() throws Throwable {
		//
		Assertions.assertNull(getSheetByName(null, null));
		//
	}

	private static Table getSheetByName(final SpreadsheetDocument instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_SHEET_BY_NAME.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Table) {
				return (Table) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}