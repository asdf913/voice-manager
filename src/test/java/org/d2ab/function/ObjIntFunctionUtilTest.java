package org.d2ab.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ObjIntFunctionUtilTest {

	@Test
	void testApply() {
		//
		Assertions.assertNull(ObjIntFunctionUtil.apply(null, null, 0));
		//
		Assertions.assertNull(ObjIntFunctionUtil.apply((a, b) -> null, null, 0));
		//
	}

}