package org.springframework.beans.factory;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JoYoKanJiListFactoryBeanTest {

	private JoYoKanJiListFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new JoYoKanJiListFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(List.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertEquals(null, instance != null ? instance.getObject() : null);
		//
	}

}