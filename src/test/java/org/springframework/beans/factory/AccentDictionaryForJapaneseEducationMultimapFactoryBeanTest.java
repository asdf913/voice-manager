package org.springframework.beans.factory;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.javatuples.Pair;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
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

	private static Method METHOD_CREATE_MULTI_MAP_STRING, METHOD_MATCHER, METHOD_MATCHES, METHOD_GROUP_COUNT,
			METHOD_GROUP, METHOD_GET_MIME_TYPE, METHOD_CREATE_MULTI_MAP_BY_URL, METHOD_CREATE_MULTI_MAP_WORK_BOOK,
			METHOD_CREATE_MULTI_MAP_SHEET, METHOD_PUT_ALL, METHOD_GET_AND_SET, METHOD_GET_PAIR = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = AccentDictionaryForJapaneseEducationMultimapFactoryBean.class;
		//
		(METHOD_CREATE_MULTI_MAP_STRING = clz.getDeclaredMethod("createMultimap", String.class, String[].class,
				String.class)).setAccessible(true);
		//
		(METHOD_MATCHER = clz.getDeclaredMethod("matcher", Pattern.class, CharSequence.class)).setAccessible(true);
		//
		(METHOD_MATCHES = clz.getDeclaredMethod("matches", Matcher.class)).setAccessible(true);
		//
		(METHOD_GROUP_COUNT = clz.getDeclaredMethod("groupCount", MatchResult.class)).setAccessible(true);
		//
		(METHOD_GROUP = clz.getDeclaredMethod("group", MatchResult.class, Integer.TYPE)).setAccessible(true);
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
		(METHOD_PUT_ALL = clz.getDeclaredMethod("putAll", Multimap.class, Multimap.class)).setAccessible(true);
		//
		(METHOD_GET_AND_SET = clz.getDeclaredMethod("getAndSet", AtomicBoolean.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_PAIR = clz.getDeclaredMethod("getPair", String.class, String.class, AtomicReference.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Integer groupCount = null;

		private String group = null;

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
			if (proxy instanceof MatchResult) {
				//
				if (Objects.equals(methodName, "groupCount")) {
					//
					return groupCount;
					//
				} else if (Objects.equals(methodName, "group")) {
					//
					return group;
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

	private MatchResult matchResult = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new AccentDictionaryForJapaneseEducationMultimapFactoryBean();
		//
		matchResult = Reflection.newProxy(MatchResult.class, ih = new IH());
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
			instance.setResource(new ClassPathResource("AccentDictionaryForJapaneseEducation.xlsx"));
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
		Assertions.assertThrows(MalformedURLException.class, () -> createMultimap(url, null, null));
		//
		Assertions.assertThrows(MalformedURLException.class, () -> createMultimap(url, new String[] {}, null));
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
	void testMatcher() throws Throwable {
		//
		Assertions.assertNull(matcher(null, null));
		//
		Assertions.assertNull(matcher(Pattern.compile("\\d+"), null));
		//
	}

	private static Matcher matcher(final Pattern instance, final CharSequence input) throws Throwable {
		try {
			final Object obj = METHOD_MATCHER.invoke(null, instance, input);
			if (obj == null) {
				return null;
			} else if (obj instanceof Matcher) {
				return (Matcher) obj;
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMatches() throws Throwable {
		//
		Assertions.assertFalse(matches(null));
		//
		final Pattern pattern = Pattern.compile("\\d+");
		//
		Assertions.assertFalse(matches(matcher(pattern, EMPTY)));
		//
		Assertions.assertTrue(matches(matcher(pattern, "1")));
		//
	}

	private static boolean matches(final Matcher instance) throws Throwable {
		try {
			final Object obj = METHOD_MATCHES.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGroupCount() throws Throwable {
		//
		Assertions.assertEquals(0, groupCount(null));
		//
		if (ih != null) {
			//
			ih.groupCount = Integer.valueOf(0);
			//
		} // if
			//
		Assertions.assertEquals(0, groupCount(matchResult));
		//
	}

	private static int groupCount(final MatchResult instance) throws Throwable {
		try {
			final Object obj = METHOD_GROUP_COUNT.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGroup() throws Throwable {
		//
		Assertions.assertNull(group(null, 0));
		//
		Assertions.assertNull(group(matchResult, 0));
		//
	}

	private static String group(final MatchResult instance, final int group) throws Throwable {
		try {
			final Object obj = METHOD_GROUP.invoke(null, instance, group);
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
	void testCreateMultimapByUrl() throws MalformedURLException {
		//
		final String url = new File("pom.xml").toURI().toURL().toString();
		//
		Assertions.assertThrows(MalformedURLException.class, () -> createMultimapByUrl(url, null, null));
		//
		Assertions.assertThrows(MalformedURLException.class, () -> createMultimapByUrl(url, new String[] {}, null));
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
	void testPutAll() {
		//
		Assertions.assertDoesNotThrow(() -> putAll(null, null));
		//
	}

	private static <K, V> void putAll(final Multimap<K, V> a, final Multimap<? extends K, ? extends V> b)
			throws Throwable {
		try {
			METHOD_PUT_ALL.invoke(null, a, b);
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