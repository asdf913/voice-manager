package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.validator.routines.UrlValidator;
import org.javatuples.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;

import com.google.common.reflect.Reflection;

class EastJapanRailwayKanjiHiraganaMapFactoryBeanTest {

	private static Method METHOD_CREATE_MAP_INPUT_STREAM, METHOD_CREATE_PAIR_STRING, METHOD_CREATE_PAIR_ELEMENT,
			METHOD_MERGE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = EastJapanRailwayKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_CREATE_MAP_INPUT_STREAM = clz.getDeclaredMethod("createMap", InputStream.class, UrlValidator.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_PAIR_STRING = clz.getDeclaredMethod("createPair", String.class)).setAccessible(true);
		//
		(METHOD_CREATE_PAIR_ELEMENT = clz.getDeclaredMethod("createPair", Element.class)).setAccessible(true);
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

	private UrlValidator urlValidator = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new EastJapanRailwayKanjiHiraganaMapFactoryBean();
		//
		ih = new IH();
		//
		urlValidator = UrlValidator.getInstance();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrls(new String[] { null, "", " " });
			//
		} // if
			//
		Assertions.assertEquals(Collections.emptyMap(), FactoryBeanUtil.getObject(instance));
		//
		final File file = Path.of("pom.xml").toFile();
		//
		final String url = file != null ? file.toURI().toURL().toString() : null;
		//
		if (instance != null) {
			//
			instance.setUrls(new String[] { url });
			//
		} // if
			//
		Assertions.assertEquals(Collections.emptyMap(), FactoryBeanUtil.getObject(instance));
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testCreateMap1() throws Throwable {
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
		try (final InputStream is = new ByteArrayInputStream("1,2,3,4,5,6".getBytes())) {
			//
			Assertions.assertNull(createMap(is, urlValidator));
			//
		} // try
			//
	}

	@Test
	void testCreateMap2() throws Throwable {
		//
		try (final InputStream is = new ByteArrayInputStream("1,2,3,4,5,http://127.0.0.1".getBytes())) {
			//
			final Executable executable = () -> createMap(is, urlValidator);
			//
			if (Objects.equals(OperatingSystem.WINDOWS, OperatingSystemUtil.getOperatingSystem())) {
				//
				AssertionsUtil.assertThrowsAndEquals(ConnectException.class, "{}", executable);
				//
			} else {
				//
				AssertionsUtil.assertThrowsAndEquals(ConnectException.class, "{}", executable);
				//
			} // if
				//
		} // try
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
	void testMerge() throws IOException {
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
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{localizedMessage=null=, message=null=}",
				() -> merge(a, b));
		//
	}

	private static <K, V> void merge(final Map<K, V> a, final Map<K, V> b) throws Throwable {
		try {
			METHOD_MERGE.invoke(null, a, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}