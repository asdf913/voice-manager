package org.springframework.beans.factory;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;

class AccentDictionaryForJapaneseEducationMultimapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_CREATE_MULTI_MAP, METHOD_MATCHER, METHOD_MATCHES, METHOD_GROUP_COUNT, METHOD_GROUP,
			METHOD_GET_MIME_TYPE, METHOD_CREATE_MULTI_MAP_BY_URL = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = AccentDictionaryForJapaneseEducationMultimapFactoryBean.class;
		//
		(METHOD_CREATE_MULTI_MAP = clz.getDeclaredMethod("createMultimap", String.class, String[].class))
				.setAccessible(true);
		//
		(METHOD_MATCHER = clz.getDeclaredMethod("matcher", Pattern.class, String.class)).setAccessible(true);
		//
		(METHOD_MATCHES = clz.getDeclaredMethod("matches", Matcher.class)).setAccessible(true);
		//
		(METHOD_GROUP_COUNT = clz.getDeclaredMethod("groupCount", MatchResult.class)).setAccessible(true);
		//
		(METHOD_GROUP = clz.getDeclaredMethod("group", MatchResult.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_MIME_TYPE = clz.getDeclaredMethod("getMimeType", ContentInfo.class)).setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP_BY_URL = clz.getDeclaredMethod("createMultimapByUrl", String.class, String[].class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Integer groupCount = null;

		private String group = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
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
		Assertions.assertNull(createMultimap(null, null));
		//
		Assertions.assertNull(createMultimap(EMPTY, null));
		//
		Assertions.assertNull(createMultimap(" ", null));
		//
		final String url = new File("pom.xml").toURI().toURL().toString();
		//
		Assertions.assertThrows(MalformedURLException.class, () -> createMultimap(url, null));
		//
		Assertions.assertThrows(MalformedURLException.class, () -> createMultimap(url, new String[] {}));
		//
		Assertions.assertNull(createMultimap(url, new String[] { "http" }));
		//
	}

	private static Multimap<String, String> createMultimap(final String url, final String[] allowProtocols)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP.invoke(null, url, allowProtocols);
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

	private static Matcher matcher(final Pattern instance, final String input) throws Throwable {
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
		Assertions.assertThrows(MalformedURLException.class, () -> createMultimapByUrl(url, null));
		//
		Assertions.assertThrows(MalformedURLException.class, () -> createMultimapByUrl(url, new String[] {}));
		//
	}

	private static Multimap<String, String> createMultimapByUrl(final String url, final String[] allowProtocols)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP_BY_URL.invoke(null, url, allowProtocols);
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

}