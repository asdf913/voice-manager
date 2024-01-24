package org.apache.poi.ss.usermodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FormulaErrorUtilTest {

	@Test
	void testGetString() {
		//
		Assertions.assertNull(FormulaErrorUtil.getString(null));
		//
	}

}