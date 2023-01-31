package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;

class IpaMultimapFactoryBeanTest {

	private static Method METHOD_TO_STRING, METHOD_GET_CLASS = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = IpaMultimapFactoryBean.class;
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean exists = null;

		private InputStream inputStream = null;

		private IOException ioException = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof InputStreamSource) {
				//
				if (Objects.equals(methodName, "getInputStream")) {
					//
					if (ioException != null) {
						//
						throw ioException;
						//
					} // if
						//
					return inputStream;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof Resource) {
				//
				if (Objects.equals(methodName, "exists")) {
					//
					return exists;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private IpaMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new IpaMultimapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(getObject(instance));
		//
		final IH ih = new IH();
		//
		if (instance != null) {
			//
			instance.setResource(Reflection.newProxy(Resource.class, ih));
			//
		} // if
			//
		ih.exists = Boolean.FALSE;
		//
		Assertions.assertNull(getObject(instance));
		//
		ih.exists = Boolean.TRUE;
		//
		Assertions.assertNull(getObject(instance));
		//
		final String key = "KEY";
		//
		final String value = "VALUE";
		//
		try (final InputStream is = new ByteArrayInputStream(
				String.format("{\"%1$s\":\"%2$s\"}", key, value).getBytes())) {
			//
			ih.inputStream = is;
			//
			Assertions.assertEquals(toString(ImmutableMultimap.of(key, value)), toString(getObject(instance)));
			//
		} // try
			//
		if (instance != null) {
			//
			instance.setUrl("A");
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> getObject(instance));
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
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null));
		//
		Assertions.assertEquals(String.class, getClass(""));
		//
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

}