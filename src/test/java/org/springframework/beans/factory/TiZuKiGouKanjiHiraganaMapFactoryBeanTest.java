package org.springframework.beans.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

import io.github.toolfactory.narcissus.Narcissus;

class TiZuKiGouKanjiHiraganaMapFactoryBeanTest {

	private static Method METHOD_TEXT, METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = TiZuKiGouKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_TEXT = clz.getDeclaredMethod("text", Elements.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
	}

	private TiZuKiGouKanjiHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new TiZuKiGouKanjiHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.TiZuKiGouKanjiHiraganaMapFactoryBean.url")) {
			//
			instance.setUrl(Util.toString(Util.get(properties,
					"org.springframework.beans.factory.TiZuKiGouKanjiHiraganaMapFactoryBean.url")));
			//
			System.out.println(ObjectMapperUtil.writeValueAsString(new ObjectMapper(), getObject(instance)));
			//
		} // if
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testText() throws Throwable {
		//
		Assertions.assertNull(text(null));
		//
		Assertions.assertEquals("", text(Util.cast(Elements.class, Narcissus.allocateInstance(Elements.class))));
		//
	}

	private static String text(final Elements instance) throws Throwable {
		try {
			final Object obj = METHOD_TEXT.invoke(null, instance);
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