package org.springframework.beans.factory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.javatuples.Pair;
import org.javatuples.valueintf.IValue0;
import org.jsoup.helper.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.Iterators;
import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;

class AccentDictionaryForJapaneseEducationMultimapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_CREATE_MULTI_MAP_STRING, METHOD_GET_MIME_TYPE, METHOD_CREATE_MULTI_MAP_BY_URL,
			METHOD_CREATE_MULTI_MAP_WORK_BOOK, METHOD_CREATE_MULTI_MAP_SHEET, METHOD_GET_AND_SET,
			METHOD_GET_PAIR = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = AccentDictionaryForJapaneseEducationMultimapFactoryBean.class;
		//
		(METHOD_CREATE_MULTI_MAP_STRING = clz.getDeclaredMethod("createMultimap", String.class, String[].class,
				String.class)).setAccessible(true);
		//
		(METHOD_GET_MIME_TYPE = clz.getDeclaredMethod("getMimeType", ContentInfo.class)).setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP_BY_URL = clz.getDeclaredMethod("createMultimapByUrl", String.class, String[].class,
				String.class)).setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP_WORK_BOOK = clz.getDeclaredMethod("createMultimap", Workbook.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP_SHEET = clz.getDeclaredMethod("createMultimap", Sheet.class)).setAccessible(true);
		//
		(METHOD_GET_AND_SET = clz.getDeclaredMethod("getAndSet", AtomicBoolean.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_PAIR = clz.getDeclaredMethod("getPair", String.class, String.class, AtomicReference.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Iterator<Sheet> iteratorSheet = null;

		private Iterator<Row> iteratorRow = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Workbook) {
				//
				if (Objects.equals(methodName, "iterator")) {
					//
					return iteratorSheet;
					//
				} // if
					//
			} else if (proxy instanceof Sheet) {
				//
				if (Objects.equals(methodName, "iterator")) {
					//
					return iteratorRow;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private AccentDictionaryForJapaneseEducationMultimapFactoryBean instance = null;

//	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new AccentDictionaryForJapaneseEducationMultimapFactoryBean();
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
			instance.setUrl(new File("pom.xml").toURI().toURL().toString());
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ClassPathResource("AccentDictionaryForJapaneseEducationHiragana.xlsx"));
			//
		} // if
			//
		Assertions.assertNotNull(getObject(instance));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instnace) throws Exception {
		return instnace != null ? instnace.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testCreateMultimap() throws Throwable {
		//
		Assertions.assertNull(createMultimap((Workbook) null));
		//
		final IH ih = new IH();
		//
		// createMultimap(org.apache.poi.ss.usermodel.Sheet)
		//
		final Workbook wb = Reflection.newProxy(Workbook.class, ih);
		//
		Assertions.assertNull(createMultimap(wb));
		//
		if (ih != null) {
			//
			ih.iteratorSheet = Iterators.singletonIterator(null);
			//
		} // if
			//
		Assertions.assertNull(createMultimap(wb));
		//
		// createMultimap(org.apache.poi.ss.usermodel.Sheet)
		//
		Assertions.assertNull(createMultimap((Sheet) null));
		//
		final Sheet sheet = Reflection.newProxy(Sheet.class, ih);
		//
		Assertions.assertNull(createMultimap(sheet));
		//
		if (ih != null) {
			//
			ih.iteratorRow = Iterators.singletonIterator(null);
			//
		} // if
			//
		Assertions.assertNull(createMultimap(sheet));
		//
		// createMultimap(java.lang.String,java.lang.String[])
		//
		Assertions.assertNull(createMultimap(null, null, null));
		//
		Assertions.assertNull(createMultimap(EMPTY, null, null));
		//
		Assertions.assertNull(createMultimap(" ", null, null));
		//
		final String url = new File("pom.xml").toURI().toURL().toString();
		//
		AssertionsUtil.assertThrowsAndEquals(ValidationException.class,
				"{localizedMessage=java.net.URISyntaxException: Expected authority at index 7: file://, message=java.net.URISyntaxException: Expected authority at index 7: file://}",
				() -> createMultimap(url, null, null));
		//
		AssertionsUtil.assertThrowsAndEquals(ValidationException.class,
				"{localizedMessage=java.net.URISyntaxException: Expected authority at index 7: file://, message=java.net.URISyntaxException: Expected authority at index 7: file://}",
				() -> createMultimap(url, new String[] {}, null));
		//
		Assertions.assertNull(createMultimap(url, new String[] { "http" }, null));
		//
	}

	private static Multimap<String, String> createMultimap(final String url, final String[] allowProtocols,
			final String unicodeBlock) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP_STRING.invoke(null, url, allowProtocols, unicodeBlock);
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

	private static Multimap<String, String> createMultimap(final Sheet sheet) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP_SHEET.invoke(null, sheet);
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
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMultimapByUrl() throws IOException {
		//
		final String url = new File("pom.xml").toURI().toURL().toString();
		//
		AssertionsUtil.assertThrowsAndEquals(ValidationException.class,
				"{localizedMessage=java.net.URISyntaxException: Expected authority at index 7: file://, message=java.net.URISyntaxException: Expected authority at index 7: file://}",
				() -> createMultimapByUrl(url, null, null));
		//
		AssertionsUtil.assertThrowsAndEquals(ValidationException.class,
				"{localizedMessage=java.net.URISyntaxException: Expected authority at index 7: file://, message=java.net.URISyntaxException: Expected authority at index 7: file://}",
				() -> createMultimapByUrl(url, new String[] {}, null));
		//
	}

	private static Multimap<String, String> createMultimapByUrl(final String url, final String[] allowProtocols,
			final String unicodeBlock) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP_BY_URL.invoke(null, url, allowProtocols, unicodeBlock);
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

	@Test
	void testGetAndSet() throws Throwable {
		//
		Assertions.assertNull(getAndSet(null, false));
		//
	}

	private static Boolean getAndSet(final AtomicBoolean instance, final boolean newValue) throws Throwable {
		try {
			final Object obj = METHOD_GET_AND_SET.invoke(null, instance, newValue);
			if (obj == null) {
				return null;
			} else if (obj instanceof Boolean) {
				return (Boolean) obj;
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPair() throws Throwable {
		//
		Assertions.assertNull(getPair(null, null, null));
		//
		Assertions.assertNotNull(getPair("あう (会う / 合う / 遭う)", null, null));
		//
		Assertions.assertNotNull(getPair("あう (会う / 合う / 遭う", null, null));
		//
		Assertions.assertNull(getPair(null, "Hiragana", null));
		//
		final AtomicReference<Pattern> arPattern = new AtomicReference<>();
		//
		Assertions.assertNull(getPair(null, "Hiragana", arPattern));
		//
		Assertions.assertNotNull(getPair("あう (会う / 合う / 遭う)", "Hiragana", arPattern));
		//
	}

	private static Pair<String[], String> getPair(final String text, final String unicodeBlock,
			final AtomicReference<Pattern> arPattern) throws Throwable {
		try {
			final Object obj = METHOD_GET_PAIR.invoke(null, text, unicodeBlock, arPattern);
			if (obj == null) {
				return null;
			} else if (obj instanceof Pair) {
				return (Pair) obj;
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}