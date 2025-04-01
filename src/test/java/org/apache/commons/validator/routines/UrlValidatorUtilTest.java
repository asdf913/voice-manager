package org.apache.commons.validator.routines;

import java.lang.reflect.Method;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class UrlValidatorUtilTest {

	private static class MH implements MethodHandler {

		private Boolean isValid = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof UrlValidator && Objects.equals(methodName, "isValid")) {
				//
				return isValid;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Test
	void testIsValid() throws Throwable {
		//
		Assertions.assertFalse(UrlValidatorUtil.isValid(null, null));
		//
		Assertions.assertFalse(UrlValidatorUtil
				.isValid(cast(UrlValidator.class, Narcissus.allocateInstance(UrlValidator.class)), null));
		//
		final MH mh = new MH();
		//
		mh.isValid = Boolean.TRUE;
		//
		Assertions.assertTrue(UrlValidatorUtil.isValid(ProxyUtil.createProxy(UrlValidator.class, mh), null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}