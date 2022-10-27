package org.springframework.context.support;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

class SpeechApiSpeechServerImplTest {

	private static Method METHOD_CAST = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = Class.forName("org.springframework.context.support.SpeechApiSpeechServerImpl$Jna");
		//
		if ((METHOD_CAST = clz != null ? clz.getDeclaredMethod("cast", Class.class, Object.class) : null) != null) {
			//
			METHOD_CAST.setAccessible(true);
			//
		} // if
			//
	}

	private SpeechApiSpeechServerImpl instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new SpeechApiSpeechServerImpl();
		//
	}

	private boolean isInstalled() {
		//
		return instance != null && instance.isInstalled();
		//
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	@EnabledIf("isInstalled")
	void testSpeak() {
		//
		Assertions.assertThrows(Error.class, () -> instance.speak(null, null, 0, 0));
		//
		Assertions.assertDoesNotThrow(() -> instance.speak("1", null, 0, 0));
		//
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	void testWriteVoiceToFile() {
		//
		Assertions.assertThrows(Error.class, () -> instance.writeVoiceToFile(null, null, 0, 0, null));
		//
		Assertions.assertThrows(Error.class, () -> instance.writeVoiceToFile(null, null, 0, 0, new File(".")));
		//
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	@EnabledIf("isInstalled")
	void testGetVoiceAttribute() {
		//
		Assertions.assertNull(instance.getVoiceAttribute(null, null));
		//
		final String[] voiceIds = instance != null ? instance.getVoiceIds() : null;
		//
		String voiceId = null;
		//
		for (int i = 0; voiceIds != null && i < voiceIds.length; i++) {
			//
			Assertions.assertEquals("", instance.getVoiceAttribute(voiceId = voiceIds[i], null));
			//
			Assertions.assertEquals("", instance.getVoiceAttribute(voiceId, ""));
			//
			Assertions.assertNotNull(instance.getVoiceAttribute(voiceId, "Language"));
			//
		} // for
			//
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	@EnabledIf("isInstalled")
	void testGetProviderName() {
		//
		Assertions.assertNotNull(instance.getProviderName());
		//
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	@EnabledIf("isInstalled")
	void testGetProviderVersion() {
		//
		Assertions.assertNotNull(instance.getProviderVersion());
		//
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
		Assertions.assertNull(cast(Object.class, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}