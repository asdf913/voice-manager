package org.springframework.beans.factory;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JlptLevelListFactoryBeanTest {

	private JlptLevelListFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new JlptLevelListFactoryBean();
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
		Assertions.assertEquals(List.class, instance != null ? instance.getObjectType() : null);
		//
	}

}