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
			METHOD_REMOVE, METHOD_TO_MULTI_MAP_MAP, METHOD_TO_MULTI_MAP_ITERABLE = null;

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
		Assertions.assertThrows(IllegalArgumentException.class, () -> getObject(instance));
		//
		final Decoder decoder = Base64.getDecoder();
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(DeflateUtils.inflate(decode(decoder,
					"eJztWF1sFFUUPnd2t8xuS7strT9F6tDoUujWUHmwxmAXCiIJ2FpqFEOi02Xarmy7ZFmiJhqXIg8mkmgw8YXEkPTFaFRiog+a2L75oMGYkCBPxTeiD4PBGBLp+p0zM3S6ZXW2Gn9wz+TMvf3m3jln7znnu3f6zbnm+TNn2y9RmTxEIVooRanOhymo7v0RJ9JcbKFUKnlwqSb/KbnuthzDMOIXgXLMV7lxjbptTW5NGaYcrgIZtJOm0ObpxXIq+F25DRnjf1+QOQsBxwWVmv2V22f+Zh4PQcvrn7me6z8GrYc2QFdDG6FNJFsANUNboGugrdA24pwguh16h2ur3W1Z16Hf4f5tUI1b/mmJ6ohiXYQ+W/0Vh1xifwkZ8VF4TvLie+gBOsy5MWBm08bfJdvFB1OxD7NI0q3oKToNtJE+FPRzuW+V7IOkyOhzM/qqSonvJ+XeKfdG4vmfypyLgvTiyZdcPa+84RZBVO2no2DCXcKIE5ShNEa1+0dxhahtYMkMmZT9U087wg00w3W2y5qy8mZ2Xupnhn6WuvBk1mCcpwt+NRiuVYnT/w6fwdoZtCjeenY74+1yfGMFPFkB31QBjy7DT2morSKVuI0XQ9I2F+ukbSmGpV1TXCVtazFSelZy+AQyej8TNip234RlFXpfR40+jmtO6VCikZOKfqCnmantYbFVZzMjcyl7J3e/bqF6m2vpC/C+TscUmF+fRSWRnqKOK8z14H4de4AObtd/Af2n9TZK3/ggcLifReMNwyGW+BJiaYDhg3CIS7FZnIjD1K/vXvl27+hQ/zOCFxc/MeheTX6hOoYnc+FNMqNb7tMytpXuofVEiYFE50hm0jpiPGY9bwznJs2p5LA1fjRr5jsTvfcntrUhfIk/HDdkjltGYui4GHhV7gk4s0Xkcv8GX78L/eM9F6Z7Ltj9G339M+CUKH6ikmuakiqpvDleq+gJvFunl2U/JfVafZO7bI20VH6kIV4s+1FnoWzdjRRHsIlC9mbiNmx3CR6147RcNIrJPJ7De/vDWgt9Ih91Kd+odfw+p+syFY0jzOO6/0WK9wvuxnwzFXbwUMw5g8R1TbBZ59Eja3Hbm0nnc0dyYwVj5wtpK2s8+EDPiDlqZbOWZMT2zNhY303cDizKSeYVy0KJ37H8FbwM8yfe+ena4ET8vTd16t7w8Xe83i+Rk8r8nB3n81GKnDXZQ8456SlyzkoHyTkvHSbHycvXnbMP9zfT0ggs67+1/uy5PReVH6/Gf0a081+fP33f2vipt+F/8toHO4BFyrAD5JzTPBaI+3ythNfk1pG/8vtP8qSMA242h5km5VLLk7Cep0M0Kn4cqtr/FmQlW+SNwuOnINLn/mNL0QDsTqJGB+HDcyuyH6LFb7Agc+6E7ibP/iDOglbVdj2Jwb7/PB9kDvNyq9uP0D6ceSdxmRL73ciCMYkJIwWcVXNAKkuXa5/3lqD274K+f8P+DlhIiw+WZGB1/vSt4PffDS0EGRhQqrX/b/r+/w3g9SeV"))));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
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
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
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

}