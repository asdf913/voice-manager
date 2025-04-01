package org.springframework.beans.factory.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class BeanDefinitionUtilTest {

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof BeanDefinition
					&& contains(Arrays.asList("getBeanClassName", "getPropertyValues"), methodName)) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		private static boolean contains(final Collection<?> items, final Object item) {
			return items != null && items.contains(item);
		}

	}

	private BeanDefinition beanDefinition = null;

	@BeforeEach
	void beforeEach() {
		//
		beanDefinition = Reflection.newProxy(BeanDefinition.class, new IH());
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = BeanDefinitionUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertNull(
					Narcissus.invokeStaticMethod(m, toArray(Collections.nCopies(m.getParameterCount(), null))),
					Objects.toString(m));
			//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testGetBeanClassName() {
		//
		Assertions.assertNull(BeanDefinitionUtil.getBeanClassName(beanDefinition));
		//
	}

	@Test
	void testGetPropertyValues() {
		//
		Assertions.assertNull(BeanDefinitionUtil.getPropertyValues(beanDefinition));
		//
	}

}