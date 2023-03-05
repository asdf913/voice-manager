package org.springframework.beans.factory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.Multimap;

class AccentDictionaryForJapaneseEducationMultimapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_CREATE_MULTI_MAP, METHOD_MATCHER = null;

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
	}

	private AccentDictionaryForJapaneseEducationMultimapFactoryBean instance = null;

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
		Assertions.assertThrows(MalformedURLException.class,
				() -> createMultimap(new File("pom.xml").toURI().toURL().toString(), null));
		//
		Assertions.assertThrows(MalformedURLException.class,
				() -> createMultimap(new File("pom.xml").toURI().toURL().toString(), new String[] {}));
		//
		Assertions.assertNull(createMultimap(new File("pom.xml").toURI().toURL().toString(), new String[] { "http" }));
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
		final Pattern pattern = Pattern.compile("\\d+");
		//
		Assertions.assertNull(matcher(pattern, null));
		//
		Assertions.assertNotNull(matcher(pattern, EMPTY));
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

}