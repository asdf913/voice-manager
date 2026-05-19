package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import com.google.common.reflect.Reflection;

class JouYouKanJiListFactoryBeanTest {

	private static Method METHOD_GET_COLUMN_INDEX, METHOD_READ_ALL_BYTES = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = JouYouKanJiListFactoryBean.class;
		//
		(METHOD_GET_COLUMN_INDEX = clz.getDeclaredMethod("getColumnIndex", List.class)).setAccessible(true);
		//
		(METHOD_READ_ALL_BYTES = clz.getDeclaredMethod("readAllBytes", InputStream.class)).setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private Boolean exists = null;

		private InputStream inputStream = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof InputStreamSource && Objects.equals(methodName, "getInputStream")) {
				//
				return inputStream;
				//
			} // if
				//
			if (proxy instanceof Resource && Objects.equals(methodName, "exists")) {
				//
				return exists;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private JouYouKanJiListFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new JouYouKanJiListFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(List.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ClassPathResource("JouYouKanJi.txt"));
			//
		} // if
			//
		Assertions.assertNotNull(FactoryBeanUtil.getObject(instance));
		//
		final IH ih = new IH();
		//
		if (instance != null) {
			//
			instance.setResource(Reflection.newProxy(Resource.class, ih));
			//
		} // if
			//
		ih.exists = Boolean.TRUE;
		//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource("".getBytes()));
			//
		} // if
			//
		Assertions.assertEquals(List.of(), FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource("null".getBytes()));
			//
		} // if
			//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		final int one = 1;
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(Integer.toString(one).getBytes()));
			//
		} // if
			//
		Assertions.assertEquals(Collections.singletonList(Integer.toString(one)), FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource("[]".getBytes()));
			//
		} // if
			//
		Assertions.assertEquals(List.of(), FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource("{}".getBytes()));
			//
		} // if
			//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}",
				() -> FactoryBeanUtil.getObject(instance));
		//
	}

	@Test
	void testGetColumnIndex() throws Throwable {
		//
		Assertions.assertNull(invoke(METHOD_GET_COLUMN_INDEX, null, (Object) null));
		//
		Assertions.assertNull(invoke(METHOD_GET_COLUMN_INDEX, null, Arrays.asList(null, new Element("a"))));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null && method.getDeclaringClass() != null ? method.invoke(instance, args) : null;
	}

	@Test
	void testReadAllBytes() throws IllegalAccessException, InvocationTargetException, IOException {
		//
		Assertions.assertNull(invoke(METHOD_READ_ALL_BYTES, null, (Object) null));
		//
		final byte[] bs = new byte[] {};
		//
		try (final InputStream is = new ByteArrayInputStream(bs)) {
			//
			Assertions.assertTrue(Objects.deepEquals(bs, invoke(METHOD_READ_ALL_BYTES, null, is)));
			//
		} // try
			//
	}

}