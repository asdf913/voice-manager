package org.springframework.beans.factory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;

class CentalJapanRailwayStationKanjiHiraganaMapFactoryBeanTest {

	private static Method METHOD_TO_STRING, METHOD_GET, METHOD_CAST = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = CentalJapanRailwayStationKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_GET = clz.getDeclaredMethod("get", Map.class, Object.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
	}

	private CentalJapanRailwayStationKanjiHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new CentalJapanRailwayStationKanjiHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Throwable {
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
		if (instance != null) {
			//
			instance.setUrl(toString(new File("pom.xml").toURI().toURL()));
			//
		} // if
			//
		Assertions.assertThrows(JsonParseException.class, () -> getObject(instance));
		//
	}

	@Test
	void testToString() throws Throwable {
		//
		Assertions.assertNull(toString(null));
		//
	}

	private static String toString(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_STRING.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> T getObject(final FactoryBean<T> instnace) throws Exception {
		return instnace != null ? instnace.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get(Collections.emptyMap(), null));
		//
	}

	private static <V> V get(final Map<?, V> instance, final Object key) throws Throwable {
		try {
			return (V) METHOD_GET.invoke(null, instance, key);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
		final Object object = new Object();
		//
		Assertions.assertSame(object, cast(Object.class, object));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
}