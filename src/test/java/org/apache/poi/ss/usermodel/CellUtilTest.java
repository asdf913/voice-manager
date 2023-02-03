package org.apache.poi.ss.usermodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CellUtilTest {

	@Test
	void testSetCellValue() {
		//
		Assertions.assertDoesNotThrow(() -> CellUtil.setCellValue(null, null));
		//
	}

}