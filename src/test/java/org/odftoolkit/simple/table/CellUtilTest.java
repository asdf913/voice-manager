package org.odftoolkit.simple.table;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class CellUtilTest {

	@Test
	void testGetStringValue() {
		//
		Assertions.assertNull(CellUtil.getStringValue(null));
		//
		Assertions.assertNull(CellUtil.getStringValue(cast(Cell.class, Narcissus.allocateInstance(Cell.class))));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

}