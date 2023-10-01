package org.apache.bcel.classfile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class JavaClassUtilTest {

	@Test
	void testGetMethod() {
		//
		Assertions.assertNull(
				JavaClassUtil.getMethod(cast(JavaClass.class, Narcissus.allocateInstance(JavaClass.class)), null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}