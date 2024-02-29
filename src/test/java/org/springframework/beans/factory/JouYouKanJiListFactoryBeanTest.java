package org.springframework.beans.factory;

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

	private static Method METHOD_GET_COLUMN_INDEX = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		(METHOD_GET_COLUMN_INDEX = JouYouKanJiListFactoryBean.class.getDeclaredMethod("getColumnIndex", List.class))
				.setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private Boolean exists = null;

		private InputStream inputStream = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof InputStreamSource) {
				//
				if (Objects.equals(methodName, "getInputStream")) {
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
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ClassPathResource("JouYouKanJi.txt"));
			//
		} // if
			//
		Assertions.assertNotNull(getObject(instance));
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
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource("".getBytes()));
			//
		} // if
			//
		Assertions.assertEquals(Collections.emptyList(), getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource("null".getBytes()));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final int one = 1;
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(Integer.toString(one).getBytes()));
			//
		} // if
			//
		Assertions.assertEquals(Collections.singletonList(Integer.toString(one)), getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource("[]".getBytes()));
			//
		} // if
			//
		Assertions.assertEquals(Collections.emptyList(), getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource("{}".getBytes()));
			//
		} // if
			//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class, "{}",
				() -> getObject(instance));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetColumnIndex() throws Throwable {
		//
		Assertions.assertNull(getColumnIndex(null));
		//
		Assertions.assertNull(getColumnIndex(Arrays.asList(null, new Element("a"))));
		//
	}

	private static Integer getColumnIndex(final List<Element> elements) throws Throwable {
		try {
			final Object obj = METHOD_GET_COLUMN_INDEX.invoke(null, elements);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}