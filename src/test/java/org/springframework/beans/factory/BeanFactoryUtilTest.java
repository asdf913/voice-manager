package org.springframework.beans.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

class BeanFactoryUtilTest {

	private static class IH implements InvocationHandler {

		private Object bean = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof BeanFactory) {
				//
				if (Objects.equals(methodName, "getBean")) {
					//
					return bean;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Test
	void testGetBean() {
		//
		Assertions.assertNull(BeanFactoryUtil.getBean(null, null));
		//
		Assertions.assertNull(BeanFactoryUtil.getBean(Reflection.newProxy(BeanFactory.class, new IH()), null));
		//
	}

}