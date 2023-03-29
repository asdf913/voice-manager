package org.apache.poi.ss.usermodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CellValueUtilTest {

	@Test
	void testGetStringValue() {
		//
		Assertions.assertNull(CellValueUtil.getStringValue(null));
		//
	}

}