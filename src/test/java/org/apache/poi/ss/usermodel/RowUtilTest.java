package org.apache.poi.ss.usermodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RowUtilTest {

	@Test
	void testCreateCell() {
		//
		Assertions.assertNull(RowUtil.createCell(null, 0));
		//
	}

}