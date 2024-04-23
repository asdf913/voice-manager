package io.github.classgraph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassGraphUtilTest {

	@Test
	void testScan() {
		//
		Assertions.assertNull(ClassGraphUtil.scan(null));
		//
	}

}