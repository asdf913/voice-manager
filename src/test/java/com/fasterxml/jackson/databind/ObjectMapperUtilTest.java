package com.fasterxml.jackson.databind;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class ObjectMapperUtilTest {

	private ObjectMapper objectMapper = null;

	@BeforeEach
	void beforeEach() {
		//
		objectMapper = new ObjectMapper();
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean test = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = getName(method);
			//
			if (Boolean.logicalAnd(proxy instanceof Member, Objects.equals(name, "getName"))) {
				//
				return null;
				//
			} else if (Boolean.logicalAnd(proxy instanceof Collection, Objects.equals(name, "stream"))) {
				//
				return null;
				//
			} else if (proxy instanceof Stream && contains(Arrays.asList("filter", "collect"), name)) {
				//
				return null;
				//
			} else if (Boolean.logicalAnd(proxy instanceof FailableFunction, Objects.equals(name, "apply"))) {
				//
				return null;
				//
			} else if (Boolean.logicalAnd(proxy instanceof Predicate, Objects.equals(name, "test"))) {
				//
				return test;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

		private static boolean contains(final Collection<?> items, final Object item) {
			return items != null && items.contains(item);
		}

	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	@Test
	void testNull() throws Throwable {
		//
		final Method[] ms = ObjectMapperUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertNull(
					Narcissus.invokeStaticMethod(m, toArray(Collections.nCopies(m.getParameterCount(), null))),
					Objects.toString(m));
			//
			//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testNonNull() throws Throwable {
		//
		final Method[] ms = ObjectMapperUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		final IH ih = new IH();
		//
		ih.test = Boolean.FALSE;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()
					|| (parameterTypes = m.getParameterTypes()) == null
					|| Boolean.logicalAnd(Objects.equals(getName(m), "readValue"), Arrays.equals(parameterTypes,
							new Class<?>[] { ObjectMapper.class, byte[].class, Class.class }))) {
				//
				continue;
				//
			} // if
				//
			clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			for (int j = 0; j < parameterTypes.length; j++) {
				//
				if (isInterface(parameterType = ArrayUtils.get(parameterTypes, j))) {
					//
					add(collection, Reflection.newProxy(parameterType, ih));
					//
				} else if (isArray(parameterType)) {
					//
					add(collection, Array.newInstance(parameterType, 0));
					//
				} else if (Objects.equals(parameterType, InputStream.class)) {
					//
					add(collection, new ByteArrayInputStream(new byte[] {}));
					//
				} else if (Objects.equals(parameterType, Class.class)) {
					//
					add(collection, Object.class);
					//
				} else {
					//
					add(collection, Narcissus.allocateInstance(parameterType));
					//
				} // if
					//
			} // for
				//
			os = toArray(collection);
			//
			toString = Objects.toString(m);
			//
			if (Boolean.logicalAnd(Objects.equals(getName(m), "getClass"),
					Arrays.equals(parameterTypes, new Class<?>[] { Object.class }))) {
				//
				Assertions.assertNotNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} // if
				//
		} // for
			//
	}

	private static boolean isArray(final Class<?> instance) {
		return instance != null && instance.isArray();
	}

	private static boolean isInterface(final Class<?> instance) {
		return instance != null && instance.isInterface();
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static void clear(final Collection<?> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	@Test
	void testReadValue() throws IOException {
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, (String) null, null));
		//
		final String empty = "";
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, empty, null));
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, " ", null));
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, "1", null));
		//
		final int one = 1;
		//
		Assertions.assertEquals(Integer.valueOf(one),
				ObjectMapperUtil.readValue(objectMapper, Integer.toString(one), Object.class));
		//
		Assertions.assertEquals(Integer.valueOf(one),
				ObjectMapperUtil.readValue(objectMapper, getBytes(Integer.toString(one)), Object.class));
		//
		Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, (InputStream) null, null));
		//
		final byte[] bs = getBytes(empty);
		//
		try (final InputStream is = new ByteArrayInputStream(bs)) {
			//
			Assertions.assertNull(ObjectMapperUtil.readValue(objectMapper, is, null));
			//
		} // try
			//
		try (final InputStream is = new ByteArrayInputStream(getBytes(Integer.toString(one)))) {
			//
			Assertions.assertEquals(Integer.valueOf(one), ObjectMapperUtil.readValue(objectMapper, is, Object.class));
			//
		} // try
			//
	}

	private static byte[] getBytes(final String instance) {
		return instance != null ? instance.getBytes() : null;
	}

	@Test
	void testWriteValueAsString() throws JsonProcessingException {
		//
		Assertions.assertEquals("null", ObjectMapperUtil.writeValueAsString(objectMapper, null));
		//
	}

	@Test
	void testWriteValueAsBytes() throws JsonProcessingException {
		//
		Assertions.assertArrayEquals("null".getBytes(), ObjectMapperUtil.writeValueAsBytes(objectMapper, null));
		//
	}

}