package org.springframework.beans.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AllowedRomajiCharacterArrayFactoryBeanTest {

	private AllowedRomajiCharacterArrayFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new AllowedRomajiCharacterArrayFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(char[].class, instance != null ? instance.getObjectType() : null);
		//
	}

}