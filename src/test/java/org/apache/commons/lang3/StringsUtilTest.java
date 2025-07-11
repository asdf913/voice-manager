package org.apache.commons.lang3;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class StringsUtilTest {

	private static final String EMPTY = "";

	@Test
	void testNull() {
		//
		final Method[] ms = StringsUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Object invokeStaticMethod = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
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

	private Strings strings = null;

	@BeforeEach
	void beforeEach() {
		//
		strings = Strings.CS;
		//
	}

	@Test
	void testStartsWith() {
		//
		Assertions.assertTrue(StringsUtil.startsWith(strings, null, null));
		//
		Assertions.assertFalse(StringsUtil.startsWith(strings, EMPTY, null));
		//
	}

	@Test
	void testEndsWith() {
		//
		Assertions.assertFalse(StringsUtil.endsWith(strings, EMPTY, null));
		//
		Assertions.assertTrue(StringsUtil.endsWith(strings, null, null));
		//
	}

	@Test
	void testReplace() {
		//
		Assertions.assertNull(StringsUtil.replace(strings, null, null, null));
		//
	}

	@Test
	void testEquals() {
		//
		Assertions.assertFalse(StringsUtil.equals(strings, EMPTY, null));
		//
		Assertions.assertTrue(StringsUtil.equals(strings, null, null));
		//
	}

	@Test
	void testContains() {
		//
		Assertions.assertFalse(StringsUtil.contains(strings, null, null));
		//
		Assertions.assertTrue(StringsUtil.contains(strings, EMPTY, EMPTY));
		//
	}

}