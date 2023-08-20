package org.springframework.beans.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;

import io.github.toolfactory.narcissus.Narcissus;

class OdakyuKanjiHiraganaMapFactoryBeanTest {

	private static Method METHOD_GET_OBJECT, METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = OdakyuKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_GET_OBJECT = clz.getDeclaredMethod("getObject", List.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
	}

	private OdakyuKanjiHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OdakyuKanjiHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(getObject(instance));
		//
		Assertions.assertNull(getObject(Collections.singletonList(null)));
		//
		Assertions.assertNull(getObject(
				Collections.singletonList(Util.cast(Element.class, Narcissus.allocateInstance(Element.class)))));
		//
		final Element element = new Element("a");
		//
		final List<Element> elements = Collections.singletonList(element);
		//
		Assertions.assertNull(getObject(elements));
		//
		element.appendChild(new Element("b"));
		//
		element.appendChild(new Element("b"));
		//
		Assertions.assertEquals(Collections.singletonMap("", ""), getObject(elements));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instnace) throws Exception {
		return instnace != null ? instnace.getObject() : null;
	}

	private static Map<String, String> getObject(final List<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_GET_OBJECT.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, instance != null ? instance.getObjectType() : null);
		//
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

}