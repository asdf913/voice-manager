package org.meeuw.functional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TriPredicateUtilTest {

	@Test
	void testTest() {
		//
		Assertions.assertFalse(TriPredicateUtil.test(null, null, null, null));
		//
		Assertions.assertFalse(TriPredicateUtil.test((a, b, c) -> false, null, null, null));
		//
		Assertions.assertTrue(TriPredicateUtil.test((a, b, c) -> true, null, null, null));
		//
	}

}