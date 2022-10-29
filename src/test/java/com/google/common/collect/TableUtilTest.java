package com.google.common.collect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TableUtilTest {

	@Test
	void testContains() {
		//
		Assertions.assertFalse(TableUtil.contains(null, null, null));
		//
	}

	@Test
	void testGet() {
		//
		Assertions.assertNull(TableUtil.get(null, null, null));
		//
	}

}