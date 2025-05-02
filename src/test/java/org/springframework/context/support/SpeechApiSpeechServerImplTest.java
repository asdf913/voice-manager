package org.springframework.context.support;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

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
		return SpeechApi.isInstalled(instance);
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	@EnabledIf("isInstalled")
	void testSpeak() throws IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(Error.class, "", () -> instance.speak(null, null, 0, 0, null));
		//
		Assertions.assertDoesNotThrow(() -> instance.speak("1", null, 0, 0, null));
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
		final File file = Path.of(".").toFile();
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
		Assertions.assertNull(SpeechApi.getVoiceAttribute(instance, null, null));
		//
		final String[] voiceIds = SpeechApi.getVoiceIds(instance);
		//
		String voiceId = null;
		//
		for (int i = 0; voiceIds != null && i < voiceIds.length; i++) {
			//
			Assertions.assertEquals("", SpeechApi.getVoiceAttribute(instance, voiceId = voiceIds[i], null));
			//
			Assertions.assertEquals("", SpeechApi.getVoiceAttribute(instance, voiceId, ""));
			//
			Assertions.assertNotNull(SpeechApi.getVoiceAttribute(instance, voiceId, "Language"));
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
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}",
				() -> Lookup.get(instance, "volume", "min"));
		//
		instance.afterPropertiesSet();
		//
		Assertions.assertDoesNotThrow(() -> Lookup.get(instance, "volume", "min"));
		//
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(Lookup.contains(instance, "volume", "min"));
		//
		instance.afterPropertiesSet();
		//
		Assertions.assertTrue(Lookup.contains(instance, "volume", "min"));
		//
	}

}