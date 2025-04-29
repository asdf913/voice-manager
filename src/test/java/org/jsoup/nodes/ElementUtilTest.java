package org.jsoup.nodes;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class ElementUtilTest {

	private static Method METHOD_GET_CLASS, METHOD_COLLECT, METHOD_FILTER = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = ElementUtil.class;
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_COLLECT = clz.getDeclaredMethod("collect", Stream.class, Collector.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Stream && ArrayUtils.contains(new Object[] { "collect", "filter" }, methodName)) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private Element element = null;

	private Stream<?> stream = null;

	@BeforeEach
	void beforeEach() {
		//
		element = cast(Element.class, Narcissus.allocateInstance(Element.class));
		//
		stream = Reflection.newProxy(Stream.class, new IH());
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Test
	void testText() {
		//
		Assertions.assertNull(ElementUtil.text(element));
		//
	}

	@Test
	void testGetElementsByTag() {
		//
		Assertions.assertNull(ElementUtil.getElementsByTag(element, null));
		//
		Assertions.assertNull(ElementUtil.getElementsByTag(element, ""));
		//
		Assertions.assertNull(ElementUtil.getElementsByTag(element, " "));
		//
	}

	@Test
	void testSelectXpath() {
		//
		Assertions.assertNull(ElementUtil.selectXpath(element, null));
		//
	}

	@Test
	void testSelect() {
		//
		Assertions.assertNull(ElementUtil.select(element, ".1"));
		//
		Assertions.assertNotNull(ElementUtil.select(new Element("a"), ".1"));
		//
	}

	@Test
	void testChildren() {
		//
		Assertions.assertNull(ElementUtil.children(null));
		//
		Assertions.assertNull(ElementUtil.children(element));
		//
		Assertions.assertNotNull(ElementUtil.children(new Element("a")));
		//
	}

	@Test
	void testNextElementSibling() {
		//
		Assertions.assertNull(ElementUtil.nextElementSibling(element));
		//
		Assertions.assertNull(ElementUtil.nextElementSibling(new Element("a")));
		//
	}

	@Test
	void testTagName() {
		//
		Assertions.assertNull(ElementUtil.tagName(element));
		//
	}

	@Test
	void testChild() {
		//
		Assertions.assertNull(ElementUtil.child(null, 0));
		//
		Assertions.assertNull(ElementUtil.child(element, 0));
		//
	}

	@Test
	void testChildrenSize() throws ReflectiveOperationException {
		//
		Assertions.assertEquals(0, ElementUtil.childrenSize(null));
		//
		Assertions.assertEquals(0, ElementUtil.childrenSize(element));
		//
		FieldUtils.writeDeclaredField(element, "childNodes",
				Narcissus.allocateInstance(Class.forName("org.jsoup.nodes.Element$NodeList")), true);
		//
		Assertions.assertEquals(0, ElementUtil.childrenSize(element));
		//
	}

	@Test
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null));
		//
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testParents() {
		//
		Assertions.assertNotNull(ElementUtil.parents(element));
		//
	}

	@Test
	void testParent() {
		//
		Assertions.assertNull(ElementUtil.parent(element));
		//
	}

	@Test
	void testNextElementSiblings() {
		//
		Assertions.assertEquals(new Elements(), ElementUtil.nextElementSiblings(element));
		//
	}

	@Test
	void testHtml() {
		//
		Assertions.assertNull(ElementUtil.html(element));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = ElementUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Collection<Object> list = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Object invokeStaticMethod = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			clear(list = ObjectUtils.getIfNull(list, ArrayList::new));
			//
			if ((parameterTypes = m.getParameterTypes()) != null) {
				//
				for (final Class<?> clz : parameterTypes) {
					//
					if (Objects.equals(Integer.TYPE, clz)) {
						//
						list.add(Integer.valueOf(0));
						//
					} else {
						//
						list.add(null);
						//
					} // if
						//
				} // for
					//
			} // if
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(list));
			//
			toString = Objects.toString(m);
			//
			if (ArrayUtils.contains(new Object[] { Integer.TYPE, Boolean.TYPE }, m.getReturnType())) {
				//
				Assertions.assertNotNull(invokeStaticMethod, toString);
				//
			} else {
				//
				Assertions.assertNull(invokeStaticMethod, toString);
				//
			} // if
				//
		} // for
			//
	}

	private void clear(final Collection<Object> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testCollect() throws Throwable {
		//
		Assertions.assertNull(collect(stream, null));
		//
		Assertions.assertNull(collect(Stream.empty(), null));
		//
	}

	private static <T, R, A> R collect(final Stream<T> instance, final Collector<? super T, A, R> collector)
			throws Throwable {
		try {
			return (R) METHOD_COLLECT.invoke(null, instance, collector);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(stream, null));
		//
		final Stream<?> empty = Stream.empty();
		//
		Assertions.assertSame(empty, filter(empty, null));
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_FILTER.invoke(null, instance, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}