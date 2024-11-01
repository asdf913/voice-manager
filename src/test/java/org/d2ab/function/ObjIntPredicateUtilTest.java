package org.d2ab.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ObjIntPredicateUtilTest {

	@Test
	void testTest() {
		//
		Assertions.assertFalse(ObjIntPredicateUtil.test(null, null, 0));
		//
		Assertions.assertFalse(ObjIntPredicateUtil.test((a, b) -> false, null, 0));
		//
		Assertions.assertTrue(ObjIntPredicateUtil.test((a, b) -> true, null, 0));
		//
	}

}