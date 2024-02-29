package org.springframework.beans.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FactoryBeanUtilTest {

	@Test
	void testGetObjectType() {
		//
		Assertions.assertNull(FactoryBeanUtil.getObjectType(null));
		//
	}

}