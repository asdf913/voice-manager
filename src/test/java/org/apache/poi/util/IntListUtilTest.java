package org.apache.poi.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IntListUtilTest {

	@Test
	void testAdd() {
		//
		Assertions.assertDoesNotThrow(() -> IntListUtil.add(null, 0));
		//
	}

}