package org.springframework.beans.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.Link;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenLinkMapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Class<?> CLASS_IH = null;

	private static Method METHOD_GET_LINKS, METHOD_VALUE_OF, METHOD_OR_ELSE, METHOD_FIND_FIRST, METHOD_TRIM,
			METHOD_APPEND, METHOD_TEST_AND_APPLY, METHOD_IS_ABSOLUTE, METHOD_APPLY,
			METHOD_SET_DESCRIPTION_AND_TEXT_AND_URL, METHOD_ADD_LINKS, METHOD_HAS_ATTR = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.class;
		//
		(METHOD_GET_LINKS = clz.getDeclaredMethod("getLinks", List.class)).setAccessible(true);
		//
		(METHOD_VALUE_OF = clz.getDeclaredMethod("valueOf", String.class)).setAccessible(true);
		//
		(METHOD_OR_ELSE = clz.getDeclaredMethod("orElse", Optional.class, Object.class)).setAccessible(true);
		//
		(METHOD_FIND_FIRST = clz.getDeclaredMethod("findFirst", Stream.class)).setAccessible(true);
		//
		(METHOD_TRIM = clz.getDeclaredMethod("trim", String.class)).setAccessible(true);
		//
		(METHOD_APPEND = clz.getDeclaredMethod("append", StringBuilder.class, Character.TYPE)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_IS_ABSOLUTE = clz.getDeclaredMethod("isAbsolute", URI.class)).setAccessible(true);
		//
		(METHOD_APPLY = clz.getDeclaredMethod("apply", FailableFunction.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_SET_DESCRIPTION_AND_TEXT_AND_URL = clz.getDeclaredMethod("setDescriptionAndTextAndUrl", Element.class,
				CLASS_IH = Class
						.forName("org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$IH"),
				Element.class)).setAccessible(true);
		//
		(METHOD_ADD_LINKS = clz.getDeclaredMethod("addLinks", Collection.class, Element.class, Collection.class,
				Integer.TYPE, Integer.TYPE, Element.class,
				Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$ObjectMap"),
				String.class)).setAccessible(true);
		//
		(METHOD_HAS_ATTR = clz.getDeclaredMethod("hasAttr", Element.class, String.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "findFirst")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean instance = null;

	private Element element = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean();
		//
		element = Util.cast(Element.class, Narcissus.allocateInstance(Element.class));
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		if (instance != null) {
			//
			instance.setTitle("音訳の部屋読み方辞典");
			//
		} // if
			//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (properties != null && properties
				.containsKey("org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url")));
				//
			} // if
				//
			new FailableStream<>(
					ObjectUtils.getIfNull(Util.stream(instance != null ? instance.getObject() : null), Stream::empty))
					.forEach(x -> {
						//
						System.out
								.println(
										ObjectMapperUtil.writeValueAsString(
												new ObjectMapper().setDefaultPropertyInclusion(Include.NON_NULL)
														.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true),
												x));
						//
					});
		} else {
			//
			Assertions.assertNull(instance != null ? instance.getObject() : null);
			//
		} // if
			//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertSame(List.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetLinks() throws Throwable {
		//
		Assertions.assertNull(getLinks(Collections.singletonList(null)));
		//
		Assertions.assertEquals(Collections.emptyList(), getLinks(Collections.singletonList(element)));
		//
	}

	private static List<Link> getLinks(final List<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_GET_LINKS.invoke(null, es);
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
	void testValueOf() throws Throwable {
		//
		Assertions.assertNull(valueOf(null));
		//
		Assertions.assertNull(valueOf(EMPTY));
		//
		Assertions.assertNull(valueOf(" "));
		//
		Assertions.assertNull(valueOf("A"));
		//
		final int one = 1;
		//
		Assertions.assertEquals(Integer.valueOf(one), valueOf(Integer.toString(one)));
		//
	}

	private static Integer valueOf(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_VALUE_OF.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOrElse() throws Throwable {
		//
		Assertions.assertNull(orElse(Util.cast(Optional.class, Narcissus.allocateInstance(Optional.class)), null));
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
		Assertions.assertNull(findFirst(Reflection.newProxy(Stream.class, new IH())));
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
	void testTrim() throws Throwable {
		//
		Assertions.assertNull(trim(null));
		//
		Assertions.assertEquals("A", trim(" A "));
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
	void testAppend() {
		//
		Assertions.assertDoesNotThrow(() -> append(null, ' '));
		//
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
	void testIsAbsolute() throws Throwable {
		//
		Assertions.assertFalse(isAbsolute(null));
		//
		final URI uri = Util.cast(URI.class, Narcissus.allocateInstance(URI.class));
		//
		Assertions.assertFalse(isAbsolute(uri));
		//
		Narcissus.setField(uri, URI.class.getDeclaredField("scheme"), EMPTY);
		//
		Assertions.assertTrue(isAbsolute(uri));
		//
	}

	private static boolean isAbsolute(final URI instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_ABSOLUTE.invoke(null, instance);
			if (obj instanceof Boolean) {
				return (Boolean) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testApply() throws Throwable {
		//
		Assertions.assertNull(apply(null, null, null));
		//
		Assertions.assertNull(apply(a -> null, null, null));
		//
		Assertions.assertNull(apply(a -> {
			throw new RuntimeException();
		}, null, null));
		//
	}

	private static <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> function, final T value,
			final R defaultValue) throws Throwable {
		try {
			return (R) METHOD_APPLY.invoke(null, function, value, defaultValue);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetDescriptionAndTextAndUrl() {
		//
		Assertions.assertDoesNotThrow(() -> setDescriptionAndTextAndUrl(null, null, null));
		//
	}

	private static void setDescriptionAndTextAndUrl(final Element a1, final Object ih, final Element a2)
			throws Throwable {
		try {
			METHOD_SET_DESCRIPTION_AND_TEXT_AND_URL.invoke(null, a1, ih, a2);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddLinks() {
		//
		Assertions.assertDoesNotThrow(() -> addLinks(null, null, null, 0, 0, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> addLinks(null, null, Collections.singleton(null), 0, 0, null, null, null));
		//
		Assertions
				.assertDoesNotThrow(() -> addLinks(null, null, Collections.singleton(element), 0, 0, null, null, null));
		//
	}

	private static void addLinks(final Collection<Link> links, final Element a1, final Collection<Element> as2,
			final int childrenSize, final int offset, final Element e, final Object objectMap, final String imgSrc)
			throws Throwable {
		try {
			METHOD_ADD_LINKS.invoke(null, links, a1, as2, childrenSize, offset, e, objectMap, imgSrc);
		} catch (final InvocationTargetException ex) {
			throw ex.getTargetException();
		}
	}

	@Test
	void testHasAttr() throws Throwable {
		//
		Assertions.assertFalse(hasAttr(null, null));
		//
		Assertions.assertFalse(hasAttr(element, null));
		//
		Assertions.assertFalse(hasAttr(element, EMPTY));
		//
		if (element != null) {
			//
			element.attributes().put(EMPTY, EMPTY);
			//
		} // if
			//
		Assertions.assertTrue(hasAttr(element, EMPTY));
		//
	}

	private static boolean hasAttr(final Element instance, final String attributeKey) throws Throwable {
		try {
			final Object obj = METHOD_HAS_ATTR.invoke(null, instance, attributeKey);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIH() throws ClassNotFoundException {
		//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, Narcissus.allocateInstance(CLASS_IH));
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, null, null, null));
		//
		// org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$Link
		//
		Class<?> clz = Class
				.forName("org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$Link");
		//
		final Object link = Reflection.newProxy(clz, ih);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, link, null, null));
		//
		new FailableStream<>(Arrays.stream(ObjectUtils.getIfNull(getDeclaredMethods(clz), () -> new Method[] {})))
				.forEach(m -> Assertions.assertNull(invoke(ih, link, m, null)));
		//
		// org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$ObjectMap
		//
		final Object objectMap = Reflection.newProxy(
				clz = Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean$ObjectMap"),
				ih);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, objectMap, null, null));
		//
		new FailableStream<>(Arrays.stream(ObjectUtils.getIfNull(getDeclaredMethods(clz), () -> new Method[] {})))
				.forEach(m -> {
					//
					Assertions.assertThrows(Throwable.class, () -> invoke(ih, objectMap, m, null));
					//
					if (Objects.equals(Util.getName(m), "getObject")) {
						//
						Assertions.assertThrows(Throwable.class, () -> invoke(ih, objectMap, m, new Object[] {}));
						//
						Assertions.assertThrows(IllegalStateException.class,
								() -> invoke(ih, objectMap, m, new Object[] { null }));
						//
					} // if
						//
				});
		//
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	private static Object invoke(final InvocationHandler instance, final Object proxy, final Method method,
			final Object[] args) throws Throwable {
		return instance != null ? instance.invoke(proxy, method, args) : null;
	}

}