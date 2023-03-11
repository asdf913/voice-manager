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

}