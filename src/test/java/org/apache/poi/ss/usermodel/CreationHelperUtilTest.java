package org.apache.poi.ss.usermodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreationHelperUtilTest {

	@Test
	void testCreateFormulaEvaluator() {
		//
		Assertions.assertNull(CreationHelperUtil.createFormulaEvaluator(null));
		//
	}

}