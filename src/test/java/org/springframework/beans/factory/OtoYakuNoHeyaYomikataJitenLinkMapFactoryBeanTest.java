package org.springframework.beans.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
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
import org.apache.commons.lang3.reflect.FieldUtils;
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

	private static Method METHOD_GET_LINKS, METHOD_CHILDREN_SIZE, METHOD_VALUE_OF, METHOD_OR_ELSE, METHOD_FIND_FIRST,
			METHOD_PARENTS, METHOD_TRIM, METHOD_APPEND, METHOD_TEST_AND_APPLY, METHOD_ADD_ALL, METHOD_IS_ABSOLUTE,
			METHOD_APPLY = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.class;
		//
		(METHOD_GET_LINKS = clz.getDeclaredMethod("getLinks", List.class)).setAccessible(true);
		//
		(METHOD_CHILDREN_SIZE = clz.getDeclaredMethod("childrenSize", Element.class)).setAccessible(true);
		//
		(METHOD_VALUE_OF = clz.getDeclaredMethod("valueOf", String.class)).setAccessible(true);
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
		(METHOD_ADD_ALL = clz.getDeclaredMethod("addAll", Collection.class, Collection.class)).setAccessible(true);
		//
		(METHOD_IS_ABSOLUTE = clz.getDeclaredMethod("isAbsolute", URI.class)).setAccessible(true);
		//
		(METHOD_APPLY = clz.getDeclaredMethod("apply", FailableFunction.class, Object.class, Object.class))
				.setAccessible(true);
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

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean();
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
		Assertions.assertEquals(Collections.emptyList(), getLinks(
				Collections.singletonList(Util.cast(Element.class, Narcissus.allocateInstance(Element.class)))));
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
	void testChildrenSize() throws Throwable {
		//
		Assertions.assertEquals(0, childrenSize(null));
		//
		final Element element = Util.cast(Element.class, Narcissus.allocateInstance(Element.class));
		//
		FieldUtils.writeDeclaredField(element, "childNodes", Collections.emptyList(), true);
		//
		Assertions.assertEquals(0, childrenSize(element));
		//
	}

	private static int childrenSize(final Element instance) throws Throwable {
		try {
			final Object obj = METHOD_CHILDREN_SIZE.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
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
		Assertions.assertNull(valueOf(""));
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
	void testParents() throws Throwable {
		//
		Assertions.assertEquals(Collections.emptyList(),
				parents(Util.cast(Element.class, Narcissus.allocateInstance(Element.class))));
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
	void testAddAll() {
		//
		Assertions.assertDoesNotThrow(() -> addAll(null, null));
		//
		final Collection<Object> collection = Collections.emptyList();
		//
		Assertions.assertDoesNotThrow(() -> addAll(collection, collection));
		//
	}

	private static <E> void addAll(final Collection<E> instance, final Collection<? extends E> c) throws Throwable {
		try {
			METHOD_ADD_ALL.invoke(null, instance, c);
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
		Narcissus.setField(uri, URI.class.getDeclaredField("scheme"), "");
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

}