package org.springframework.beans.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;

class KyushuJapanRailwayKanjiHiraganaMapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_CREATE_ENTRY, METHOD_PUT, METHOD_SET_LEFT = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = KyushuJapanRailwayKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_CREATE_ENTRY = clz.getDeclaredMethod("createEntry", String.class)).setAccessible(true);
		//
		(METHOD_PUT = clz.getDeclaredMethod("put", Map.class, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_SET_LEFT = clz.getDeclaredMethod("setLeft", MutablePair.class, Object.class)).setAccessible(true);
		//
	}

	private KyushuJapanRailwayKanjiHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new KyushuJapanRailwayKanjiHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, instance != null ? instance.getObjectType() : null);
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
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
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
	void testCreateEntry() throws Throwable {
		//
		Assertions.assertNull(createEntry(""));
		//
		Assertions.assertNull(createEntry(" "));
		//
	}

	private static Entry<String, String> createEntry(final String url) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_ENTRY.invoke(null, url);
			if (obj == null) {
				return null;
			} else if (obj instanceof Entry) {
				return (Entry) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPut() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> put(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> put(new LinkedHashMap<>(), null, null));
		//
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) throws Throwable {
		try {
			METHOD_PUT.invoke(null, instance, key, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetLeft() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> setLeft(null, null));
		//
		Assertions.assertDoesNotThrow(() -> setLeft(MutablePair.of(null, null), null));
		//
	}

	private static <L> void setLeft(final MutablePair<L, ?> instance, final L left) throws Throwable {
		try {
			METHOD_SET_LEFT.invoke(null, instance, left);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}