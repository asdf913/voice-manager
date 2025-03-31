package org.springframework.context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;

import com.google.common.reflect.Reflection;

class ConfigurableApplicationContextUtilTest {

	private static class IH implements InvocationHandler {

		private BeanFactory beanFactory = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof ConfigurableApplicationContext && Objects.equals(methodName, "getBeanFactory")) {
				//
				return beanFactory;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Test
	void testGetBeanFactory() {
		//
		Assertions.assertNull(ConfigurableApplicationContextUtil.getBeanFactory(null));
		//
		Assertions.assertNull(ConfigurableApplicationContextUtil
				.getBeanFactory(Reflection.newProxy(ConfigurableApplicationContext.class, new IH())));
		//
	}

}