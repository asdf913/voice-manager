package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

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

	private static Method METHOD_ENTRY_SET, METHOD_ITERATOR, METHOD_GET_KEY, METHOD_GET_VALUE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = IpaMultimapFactoryBean.class;
		//
		(METHOD_ENTRY_SET = clz.getDeclaredMethod("entrySet", Map.class)).setAccessible(true);
		//
		(METHOD_ITERATOR = clz.getDeclaredMethod("iterator", Iterable.class)).setAccessible(true);
		//
		(METHOD_GET_KEY = clz.getDeclaredMethod("getKey", Entry.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Entry.class)).setAccessible(true);
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
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return null;
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

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new IpaMultimapFactoryBean();
		//
		ih = new IH();
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
		if (instance != null) {
			//
			instance.setResource(Reflection.newProxy(Resource.class, ih));
			//
		} // if
			//
		if (ih != null) {
			//
			ih.exists = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (ih != null) {
			//
			ih.exists = Boolean.TRUE;
			//
		} // if
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

			if (ih != null) {
				//
				ih.inputStream = is;
				//
			} // if
				//
			Assertions.assertEquals(Util.toString(ImmutableMultimap.of(key, value)),
					Util.toString(getObject(instance)));
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

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testEntrySet() throws Throwable {
		//
		Assertions.assertNull(entrySet(null));
		//
		Assertions.assertNull(entrySet(Reflection.newProxy(Map.class, ih)));
		//
	}

	private static <K, V> Set<Entry<K, V>> entrySet(final Map<K, V> instance) throws Throwable {
		try {
			final Object obj = METHOD_ENTRY_SET.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Set) {
				return (Set) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIterator() throws Throwable {
		//
		Assertions.assertNull(iterator(null));
		//
	}

	private static <T> Iterator<T> iterator(final Iterable<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_ITERATOR.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Iterator) {
				return (Iterator) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetKey() throws Throwable {
		//
		Assertions.assertNull(getKey(null));
		//
	}

	private static <K> K getKey(final Entry<K, ?> instance) throws Throwable {
		try {
			return (K) METHOD_GET_KEY.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		Assertions.assertNull(getValue(null));
		//
	}

	private static <V> V getValue(final Entry<?, V> instance) throws Throwable {
		try {
			return (V) METHOD_GET_VALUE.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}