package org.springframework.beans.factory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Multimap;

class YojijukugoMultimapFactoryBeanTest {

	private static Method METHOD_TEST = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		(METHOD_TEST = YojijukugoMultimapFactoryBean.class.getDeclaredMethod("test", Predicate.class, Object.class))
				.setAccessible(true);
		//
	}

	private YojijukugoMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new YojijukugoMultimapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		instance.setUrl("");
		//
		Assertions.assertNull(getObject(instance));
		//
		instance.setUrl(" ");
		//
		Assertions.assertNull(getObject(instance));
		//
		instance.setUrl(new File("pom.xml").toURI().toURL().toString());
		//
		Assertions.assertNull(getObject(instance));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}