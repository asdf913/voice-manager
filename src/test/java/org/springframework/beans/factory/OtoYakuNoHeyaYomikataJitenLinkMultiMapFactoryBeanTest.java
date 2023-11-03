package org.springframework.beans.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.google.common.collect.Multimap;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBeanTest {

	private static Method METHOD_GET_MULTI_MAP, METHOD_OR_ELSE, METHOD_FIND_FIRST, METHOD_PARENTS, METHOD_TRIM,
			METHOD_APPEND, METHOD_TEST_AND_APPLY, METHOD_PUT_HREF = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBean.class;
		//
		(METHOD_GET_MULTI_MAP = clz.getDeclaredMethod("getMultimap", Element.class)).setAccessible(true);
		//
		(METHOD_OR_ELSE = clz.getDeclaredMethod("orElse", Optional.class, Object.class)).setAccessible(true);
		//
		(METHOD_FIND_FIRST = clz.getDeclaredMethod("findFirst", Stream.class)).setAccessible(true);
		//
		(METHOD_PARENTS = clz.getDeclaredMethod("parents", Element.class)).setAccessible(true);
		//
		(METHOD_TRIM = clz.getDeclaredMethod("trim", String.class)).setAccessible(true);
		//
		(METHOD_APPEND = clz.getDeclaredMethod("append", StringBuilder.class, Character.TYPE)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_PUT_HREF = clz.getDeclaredMethod("putHref", Multimap.class, Element.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBean instance = null;;

	@BeforeEach
	public void before() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl("");
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
		final Map<?, ?> systemProperties = System.getProperties();
		//
		if (instance != null && isUrlSet(systemProperties)) {
			//
			instance.setUrl(Util.toString(Util.get(systemProperties,
					"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url")));
			//
			Assertions.assertNotNull(getObject(instance));
			//
		} //
			//
	}

	private static boolean isUrlSet(final Map<?, ?> map) {
		return Util.containsKey(map,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url");
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
	void testGetMultimap() throws Throwable {
		//
		Assertions.assertNull(getMultimap(Util.cast(Element.class, Narcissus.allocateInstance(Element.class))));
		//
	}

	private static Multimap<String, String> getMultimap(final Element element) throws Throwable {
		try {
			final Object obj = METHOD_GET_MULTI_MAP.invoke(null, element);
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
	void testOrElse() throws Throwable {
		//
		Assertions.assertNull(orElse(Optional.empty(), null));
		//
	}

	private static <T> T orElse(final Optional<T> instance, final T value) throws Throwable {
		try {
			return (T) METHOD_OR_ELSE.invoke(null, instance, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFindFirst() throws Throwable {
		//
		Assertions.assertEquals(Optional.empty(), findFirst(Stream.empty()));
		//
	}

	private static <T> Optional<T> findFirst(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_FIND_FIRST.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Optional) {
				return (Optional) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testParents() throws Throwable {
		//
		Assertions.assertNull(parents(null));
		//
		Assertions.assertNotNull(
				parents(Util.cast(Element.class, Util.cast(Element.class, Narcissus.allocateInstance(Element.class)))));
		//
	}

	private static List<Element> parents(final Element instance) throws Throwable {
		try {
			final Object obj = METHOD_PARENTS.invoke(null, instance);
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
	void testTrim() throws Throwable {
		//
		Assertions.assertNull(trim(null));
		//
		final String empty = "";
		//
		Assertions.assertEquals(empty, trim(empty));
		//
		final String a = "a";
		//
		Assertions.assertEquals(a, trim(StringUtils.wrap(a, " ")));
		//
	}

	private static String trim(final String string) throws Throwable {
		try {
			final Object obj = METHOD_TRIM.invoke(null, string);
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
	void testAppend() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> append(null, ' '));
	}

	private static void append(final StringBuilder instance, final char c) throws Throwable {
		try {
			METHOD_APPEND.invoke(null, instance, c);
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
	void testPutHref() throws Throwable {
		//
		if (isUrlSystemPropertiesSet()) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> putHref(null, null));
		//
	}

	private static boolean isUrlSystemPropertiesSet() {
		return isUrlSet(System.getProperties());
	}

	private static void putHref(final Multimap<String, String> m, final Element v) throws Throwable {
		try {
			METHOD_PUT_HREF.invoke(null, m, v);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}