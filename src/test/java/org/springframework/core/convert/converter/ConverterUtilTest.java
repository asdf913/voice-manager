package org.springframework.core.convert.converter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class ConverterUtilTest {

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = method != null ? method.getName() : null;
			//
			if (proxy instanceof Converter) {
				//
				if (Objects.equals(name, "convert")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(name);
			//
		}
	}

	@Test
	void testConvert() {
		//
		Assertions.assertNull(ConverterUtil.convert(null, null));
		//
		final Method[] ms = ConverterUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || !(Boolean.logicalAnd(Objects.equals(m.getName(), "convert"),
					Arrays.equals(m.getParameterTypes(), new Class<?>[] { Converter.class, Object.class })))) {
				//
				continue;
				//
			} // if
				//
			Assertions
					.assertNull(Narcissus.invokeStaticMethod(m, Reflection.newProxy(Converter.class, new IH()), null));
			//
		} // for
			//
	}
}