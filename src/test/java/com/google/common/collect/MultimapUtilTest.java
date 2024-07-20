package com.google.common.collect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MultimapUtilTest {

	@Test
	void testSize() {
		//
		Assertions.assertEquals(0, MultimapUtil.size(null));
		//
	}

	@Test
	void testvalues() {
		//
		Assertions.assertNull(MultimapUtil.values(null));
		//
	}

	@Test
	void testPut() {
		//
		Assertions.assertDoesNotThrow(() -> MultimapUtil.put(null, null, null));
		//
	}

	@Test
	void testPutAll() {
		//
		Assertions.assertDoesNotThrow(() -> MultimapUtil.putAll(null, null));
		//
		final Multimap<?, ?> multimap = LinkedHashMultimap.create();
		//
		Assertions.assertDoesNotThrow(() -> MultimapUtil.putAll(multimap, null));
		//
		Assertions.assertDoesNotThrow(() -> MultimapUtil.putAll(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> MultimapUtil.putAll(multimap, null, null));
		//
	}

	@Test
	void testIsEmpty() {
		//
		Assertions.assertTrue(MultimapUtil.isEmpty(null));
		//
	}
}