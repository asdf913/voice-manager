package org.springframework.beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class PropertyValueUtilTest {

	@Test
	void testGetValue() {
		//
		Assertions.assertNull(PropertyValueUtil.getValue(null));
		//
		Assertions.assertNull(
				PropertyValueUtil.getValue(cast(PropertyValue.class, Narcissus.allocateInstance(PropertyValue.class))));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}