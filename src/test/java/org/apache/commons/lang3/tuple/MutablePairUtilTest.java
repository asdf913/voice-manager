package org.apache.commons.lang3.tuple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MutablePairUtilTest {

	@Test
	void testSetLeft() {
		//
		Assertions.assertDoesNotThrow(() -> MutablePairUtil.setLeft(null, null));
		//
		Assertions.assertDoesNotThrow(() -> MutablePairUtil.setLeft(new MutablePair<>(), null));
		//
	}

	@Test
	void testSetRight() {
		//
		Assertions.assertDoesNotThrow(() -> MutablePairUtil.setRight(null, null));
		//
		Assertions.assertDoesNotThrow(() -> MutablePairUtil.setRight(new MutablePair<>(), null));
		//
	}
}