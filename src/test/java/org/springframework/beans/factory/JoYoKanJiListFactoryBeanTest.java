package org.springframework.beans.factory;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import com.google.common.reflect.Reflection;

class JoYoKanJiListFactoryBeanTest {

	private static Method METHOD_TAG_NAME = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		(METHOD_TAG_NAME = JoYoKanJiListFactoryBean.class.getDeclaredMethod("tagName", Element.class))
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

	private JoYoKanJiListFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new JoYoKanJiListFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(List.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ClassPathResource("JoYoKanJi.txt"));
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
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testTagName() throws Throwable {
		//
		Assertions.assertNull(tagName(null));
		//
		final String tagName = "a";
		//
		Assertions.assertSame(tagName, tagName(new Element(tagName)));
		//
	}

	private static String tagName(final Element instance) throws Throwable {
		try {
			final Object obj = METHOD_TAG_NAME.invoke(null, instance);
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

}