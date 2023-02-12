package org.springframework.beans.config;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomBeanPostProcessorTest {

	@Test
	void testPostProcessBeforeInitialization() throws Exception {
		//
		final CustomBeanPostProcessor instance = new CustomBeanPostProcessor();
		//
		Assertions.assertNull(instance.postProcessBeforeInitialization(null, null));
		//
		final JFrame jFrame = new JFrame();
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
		instance.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
	}

}