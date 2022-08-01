package org.springframework.context;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomBeanFactoryPostProcessorTest {

	private CustomBeanFactoryPostProcessor instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new CustomBeanFactoryPostProcessor();
		//
	}

	@Test
	void testSetEnvironment() {
		//
		Assertions.assertDoesNotThrow(() -> instance.setEnvironment(null));
		//
	}

	@Test
	void testPostProcessBeanFactory() {
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(null));
		//
	}

}