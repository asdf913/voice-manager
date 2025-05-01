package org.oxbow.swingbits.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.util.ClassUtil;

import io.github.toolfactory.narcissus.Narcissus;

class OperatingSystemUtilTest {

	@Test
	void testNull() {
		//
		final Method[] ms = OperatingSystemUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Object invokeStaticMethod = null;
		//
		String toString = null;
		//
		Class<?> returnType = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m,
					toArray(Collections.nCopies(m.getParameterCount(), null)));
			//
			toString = Objects.toString(m);
			//
			if (Boolean.logicalOr(Objects.equals(returnType = m.getReturnType(), Integer.TYPE),
					ClassUtil.isEnumType(returnType))) {
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

}