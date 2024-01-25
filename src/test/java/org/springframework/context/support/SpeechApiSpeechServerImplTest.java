package org.springframework.context.support;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

class SpeechApiSpeechServerImplTest {

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
	void testSpeak() throws IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(Error.class, "", () -> instance.speak(null, null, 0, 0));
		//
		Assertions.assertDoesNotThrow(() -> instance.speak("1", null, 0, 0));
		//
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	void testWriteVoiceToFile() throws IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(Error.class,
				"{localizedMessage=Invalid memory access, message=Invalid memory access}",
				() -> instance.writeVoiceToFile(null, null, 0, 0, null));
		//
		final File file = new File(".");
		//
		AssertionsUtil.assertThrowsAndEquals(Error.class,
				"{localizedMessage=Invalid memory access, message=Invalid memory access}",
				() -> instance.writeVoiceToFile(null, null, 0, 0, file));
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
	void testGet() throws Throwable {
		//
		if (instance != null) {
			//
			AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}",
					() -> instance.get("volume", "min"));
			//
			instance.afterPropertiesSet();
			//
			Assertions.assertDoesNotThrow(() -> instance.get("volume", "min"));
			//
		} // if
			//
	}

	@Test
	void testContains() throws Throwable {
		//
		if (instance != null) {
			//
			Assertions.assertFalse(instance.contains("volume", "min"));
			//
			instance.afterPropertiesSet();
			//
			Assertions.assertTrue(instance.contains("volume", "min"));
			//
		} // if
			//
	}

}