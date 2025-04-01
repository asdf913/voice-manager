package org.springframework.beans;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import javax.annotation.Nullable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class PropertyValuesUtilTest {

	private static class IH implements InvocationHandler {

		private Boolean contains = null;

		@Override
		public Object invoke(final Object proxy, @Nullable final Method method, @Nullable final Object[] args)
				throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof PropertyValues) {
				//
				if (Objects.equals(methodName, "contains")) {
					//
					return contains;
					//
				} else if (Objects.equals(methodName, "getPropertyValue")) {
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

	private PropertyValues propertyValues = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		propertyValues = Reflection.newProxy(PropertyValues.class, ih = new IH());
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = PropertyValuesUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		Object invokeStaticMethod = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			os = toArray(Collections.nCopies(m.getParameterCount(), null));
			//
			toString = Objects.toString(m);
			//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, os);
			//
			if (Objects.equals(m.getReturnType(), Boolean.TYPE)) {
				//
				Assertions.assertNotNull(invokeStaticMethod, toString);
				//
			} else {
				//
				Assertions.assertNull(invokeStaticMethod, toString);
				//
			} // if
				//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testContains() {
		//
		if (ih != null) {
			//
			ih.contains = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertFalse(PropertyValuesUtil.contains(propertyValues, null));
		//
		if (ih != null) {
			//
			ih.contains = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertTrue(PropertyValuesUtil.contains(propertyValues, null));
		//
	}

	@Test
	void testGetPropertyValue() {
		//
		Assertions.assertNull(PropertyValuesUtil.getPropertyValue(propertyValues, null));
		//
	}

}