package org.springframework.beans.config;

import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class CustomBeanPostProcessorTest {

	@Test
	void testPostProcessBeforeInitialization() throws Exception {
		//
		final CustomBeanPostProcessor instance = new CustomBeanPostProcessor();
		//
		Assertions.assertNull(instance.postProcessBeforeInitialization(null, null));
		//
		JFrame jFrame = null;
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			jFrame = new JFrame();
			//
		} else {
			//
			final Object object = Narcissus.allocateInstance(JFrame.class);
			//
			if (object instanceof JFrame) {
				//
				jFrame = (JFrame) object;
				//
			} // if
				//
		} // if
			//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
		instance.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
	}

}