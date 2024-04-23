package io.github.classgraph;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class ClassGraphUtilTest {

	@Test
	void testScan() throws IllegalAccessException {
		//
		Assertions.assertNull(ClassGraphUtil.scan(null));
		//
		ClassGraph instance = cast(ClassGraph.class, Narcissus.allocateInstance(ClassGraph.class));
		//
		Assertions.assertNull(ClassGraphUtil.scan(instance));
		//
		if ((instance = new ClassGraph().enableClassInfo()) != null) {
			//
			FieldUtils.writeDeclaredField(instance, "reflectionUtils", null, true);
			//
			Assertions.assertNull(ClassGraphUtil.scan(instance));
			//
		} // if
			//
		if ((instance = new ClassGraph().enableClassInfo()) != null) {
			//
			FieldUtils.writeDeclaredField(instance, "topLevelLog", null, true);
			//
			Assertions.assertNotNull(ClassGraphUtil.scan(instance));
			//
		} // if
			//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}