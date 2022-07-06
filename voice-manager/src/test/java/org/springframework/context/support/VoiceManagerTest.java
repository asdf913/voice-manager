package org.springframework.context.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.miginfocom.swing.MigLayout;

class VoiceManagerTest {

	private static Method METHOD_INIT = null;

	@BeforeAll
	private static void beforeAll() throws NoSuchMethodException {
		//
		(METHOD_INIT = VoiceManager.class.getDeclaredMethod("init")).setAccessible(true);
		//
	}

	private VoiceManager instance = null;

	@BeforeEach
	private void beforeEach() throws ReflectiveOperationException {
		//
		final Constructor<VoiceManager> constructor = VoiceManager.class.getDeclaredConstructor();
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		instance = constructor != null ? constructor.newInstance() : null;
		//
	}

	@Test
	void testInit() {
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> init());
		//
		instance.setLayout(new MigLayout());
		//
		Assertions.assertDoesNotThrow(() -> init());
		//
	}

	private void init() throws Throwable {
		try {
			METHOD_INIT.invoke(instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}