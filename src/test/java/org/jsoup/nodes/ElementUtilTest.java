package org.jsoup.nodes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class ElementUtilTest {

	private static Method METHOD_GET_CLASS = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		(METHOD_GET_CLASS = ElementUtil.class.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
	}

	private Element element = null;

	@BeforeEach
	void beforeEach() {
		//
		element = cast(Element.class, Narcissus.allocateInstance(Element.class));
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
	void testChildrenSize() throws IllegalAccessException {
		//
		Assertions.assertEquals(0, ElementUtil.childrenSize(null));
		//
		Assertions.assertEquals(0, ElementUtil.childrenSize(element));
		//
		FieldUtils.writeDeclaredField(element, "childNodes", Collections.emptyList(), true);
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
	void testNull() {
		//
		final Method[] ms = ElementUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Object[] os = null;
		//
		Collection<Object> list = null;
		//
		Class<?>[] parameterTypes = null;
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
			os = toArray(list);
			//
			if (Objects.equals(Integer.TYPE, m.getReturnType())) {
				//
				Assertions.assertEquals(0, Narcissus.invokeStaticIntMethod(m, os));
				//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), Objects.toString(m));
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

}