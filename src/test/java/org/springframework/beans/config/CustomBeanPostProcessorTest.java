package org.springframework.beans.config;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class CustomBeanPostProcessorTest {

	@Test
	void testPostProcessBeforeInitialization() throws NoSuchFieldException, IllegalAccessException {
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
		// javax.swing.WindowConstants.EXIT_ON_CLOSE
		//
		instance.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//
		final Field defaultCloseOperation = CustomBeanPostProcessor.class.getDeclaredField("defaultCloseOperation");
		//
		if (defaultCloseOperation != null) {
			//
			defaultCloseOperation.setAccessible(true);
			//
		} // if
			//
		Assertions.assertEquals(WindowConstants.EXIT_ON_CLOSE, get(defaultCloseOperation, instance));
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
		// null
		//
		instance.setDefaultCloseOperation(null);
		//
		Assertions.assertNull(get(defaultCloseOperation, instance));
		//
		// java.lang.String
		//
		instance.setDefaultCloseOperation(Integer.toString(0));
		//
		Assertions.assertEquals(WindowConstants.DO_NOTHING_ON_CLOSE, get(defaultCloseOperation, instance));
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> instance.setDefaultCloseOperation(StringUtils.wrap(Integer.toString(0), "\"")));
		//
		// EXIT_ON_CLOSE
		//
		instance.setDefaultCloseOperation("EXIT_ON_CLOSE");
		//
		Assertions.assertEquals(WindowConstants.EXIT_ON_CLOSE, get(defaultCloseOperation, instance));
		//
		Assertions.assertSame(jFrame, instance.postProcessBeforeInitialization(jFrame, null));
		//
		// java.lang.Boolean
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance.setDefaultCloseOperation(Boolean.TRUE));
		//
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

}