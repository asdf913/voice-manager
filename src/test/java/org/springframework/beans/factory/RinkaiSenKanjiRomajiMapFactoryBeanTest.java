package org.springframework.beans.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

class RinkaiSenKanjiRomajiMapFactoryBeanTest {

	private static Method METHOD_CREATE_ENTRY_STRING, METHOD_CREATE_ENTRY_LIST, METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = RinkaiSenKanjRomajiMapFactoryBean.class;
		//
		(METHOD_CREATE_ENTRY_STRING = clz.getDeclaredMethod("createEntry", String.class)).setAccessible(true);
		//
		(METHOD_CREATE_ENTRY_LIST = clz.getDeclaredMethod("createEntry", List.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
	}

	private RinkaiSenKanjRomajiMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new RinkaiSenKanjRomajiMapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instnace) throws Exception {
		return instnace != null ? instnace.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testCreateEntry() throws Throwable {
		//
		Assertions.assertNull(createEntry((String) null));
		//
		Assertions.assertEquals(Pair.of(null, null), createEntry(Collections.nCopies(2, null)));
		//
	}

	private static Entry<String, String> createEntry(final String url) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_ENTRY_STRING.invoke(null, url);
			if (obj == null) {
				return null;
			} else if (obj instanceof Entry) {
				return (Entry) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Entry<String, String> createEntry(final List<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_ENTRY_LIST.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof Entry) {
				return (Entry) obj;
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