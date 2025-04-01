package org.springframework.beans;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

import javax.annotation.Nullable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

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
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Test
	void testContains() {
		//
		Assertions.assertFalse(PropertyValuesUtil.contains(null, null));
		//
		final IH ih = new IH();
		//
		final PropertyValues propertyValues = Reflection.newProxy(PropertyValues.class, ih);
		//
		ih.contains = Boolean.FALSE;
		//
		Assertions.assertFalse(PropertyValuesUtil.contains(propertyValues, null));
		//
		ih.contains = Boolean.TRUE;
		//
		Assertions.assertTrue(PropertyValuesUtil.contains(propertyValues, null));
		//
	}

}