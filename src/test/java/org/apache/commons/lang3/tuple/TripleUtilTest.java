package org.apache.commons.lang3.tuple;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class TripleUtilTest {

	private static final String STRING = "STRING";

	@Test
	void testNull() {
		//
		final Method[] ms = TripleUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
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
	void testGetLeft() {
		//
		Assertions.assertSame(STRING, TripleUtil.getLeft(Triple.of(STRING, null, null)));
		//
	}

	@Test
	void testGetMiddle() {
		//
		Assertions.assertSame(STRING, TripleUtil.getMiddle(Triple.of(null, STRING, null)));
		//
	}

	@Test
	void testGetRight() {
		//
		Assertions.assertSame(STRING, TripleUtil.getRight(Triple.of(null, null, STRING)));
		//
	}

}