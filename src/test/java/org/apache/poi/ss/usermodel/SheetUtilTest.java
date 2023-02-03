package org.apache.poi.ss.usermodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SheetUtilTest {

	@Test
	void testCreateRow() {
		//
		Assertions.assertNull(SheetUtil.createRow(null, 0));
		//
	}

}