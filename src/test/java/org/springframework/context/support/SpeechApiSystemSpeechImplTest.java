package org.springframework.context.support;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import com.google.common.base.Predicates;

class SpeechApiSystemSpeechImplTest {

	private static Method METHOD_CAST, METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = Class.forName("org.springframework.context.support.SpeechApiSystemSpeechImpl$Jna");
		//
		if ((METHOD_CAST = clz != null ? clz.getDeclaredMethod("cast", Class.class, Object.class) : null) != null) {
			//
			METHOD_CAST.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_TEST_AND_APPLY = SpeechApiSystemSpeechImpl.class.getDeclaredMethod("testAndApply", Predicate.class,
				Object.class, FailableFunction.class, FailableFunction.class)) != null) {
			//
			METHOD_TEST_AND_APPLY.setAccessible(true);
			//
		} // if
			//
	}

	private SpeechApiSystemSpeechImpl instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new SpeechApiSystemSpeechImpl();
		//
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	void testSpeak() {
		//
		Assertions.assertDoesNotThrow(() -> instance.speak(null, null, 0, 0));
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
			Assertions.assertNull(instance.getVoiceAttribute(voiceId = voiceIds[i], null));
			//
			Assertions.assertNull(instance.getVoiceAttribute(voiceId, ""));
			//
			Assertions.assertNotNull(instance.getVoiceAttribute(voiceId, "Language"));
			//
		} // for
			//
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	void testGetProviderName() {
		//
		Assertions.assertNotNull(instance.getProviderName());
		//
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
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

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysFalse(), null, null, x -> x));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}