package org.springframework.beans.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.google.common.collect.Multimap;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBeanTest {

	private static Method METHOD_GET_MULTI_MAP, METHOD_OR_ELSE, METHOD_FIND_FIRST, METHOD_NODE_NAME, METHOD_PARENTS,
			METHOD_TRIM, METHOD_APPEND, METHOD_TEST_AND_APPLY = null;

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
		(METHOD_NODE_NAME = clz.getDeclaredMethod("nodeName", Node.class)).setAccessible(true);
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
	}

	private static class MH implements MethodHandler {

		private String nodeName, attr = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof Node) {
				//
				if (Objects.equals(methodName, "nodeName")) {
					//
					return nodeName;
					//
				} else if (Objects.equals(methodName, "attr")) {
					//
					return attr;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBean instance = null;

	private MH mh = null;

	@BeforeEach
	public void before() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBean();
		//
		mh = new MH();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setUrl("");
			//
		} // if
			//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setUrl(" ");
			//
		} // if
			//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		final Map<?, ?> systemProperties = System.getProperties();
		//
		if (instance != null && Util.containsKey(systemProperties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url")) {
			//
			instance.setUrl(Util.toString(Util.get(systemProperties,
					"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url")));
			//
			Assertions.assertNotNull(instance.getObject());
			//
		} // if
			//
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
	void testNodeName() throws Throwable {
		//
		Assertions.assertNull(nodeName(null));
		//
		Assertions.assertNull(nodeName(Util.cast(Node.class, createProxy(Node.class, mh, null))));
		//
	}

	private static <T> T createProxy(final Class<T> clz, final MethodHandler mh,
			final FailableFunction<Class<?>, Object, Throwable> function) throws Throwable {

		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(clz);
		//
		final Class<?> c = proxyFactory.createClass();
		//
		Object instance = function != null ? function.apply(c) : null;
		//
		if (instance == null) {
			//
			final Constructor<?> constructor = c != null ? c.getDeclaredConstructor() : null;
			//
			instance = constructor != null ? constructor.newInstance() : null;
			//
		} // if
			//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(mh);
			//
		} // if
			//
		return Util.cast(clz, instance);
		//
	}

	private static String nodeName(final Node instance) throws Throwable {
		try {
			final Object obj = METHOD_NODE_NAME.invoke(null, instance);
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

}