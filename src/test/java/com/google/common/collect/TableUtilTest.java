package com.google.common.collect;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TableUtilTest {

	@Test
	void testPut() {
		//
		Assertions.assertDoesNotThrow(() -> TableUtil.put(null, null, null, null));
		//
	}

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

	@Test
	void testCellSet() {
		//
		Assertions.assertEquals(Set.of(), TableUtil.cellSet(ImmutableTable.of()));
		//
	}

}