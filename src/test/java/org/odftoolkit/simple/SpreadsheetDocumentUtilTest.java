package org.odftoolkit.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SpreadsheetDocumentUtilTest {

	@Test
	void testGetSheetByIndex() {
		//
		Assertions.assertNull(SpreadsheetDocumentUtil.getSheetByIndex(null, 0));
		//
	}

}