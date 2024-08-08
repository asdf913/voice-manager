package org.apache.commons.lang3.function;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

class TriFunctionUtilTest {

	@Test
	void testApply() {
		//
		Assertions.assertNull(TriFunctionUtil.apply(null, null, null, null));
		//
	}

}