package org.springframework.context.support;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import com.google.common.base.Predicates;
import com.kichik.pecoff4j.PE;
import com.kichik.pecoff4j.ResourceEntry;

class SpeechApiSystemSpeechImplTest {

	private static Class<?> CLASS_JNA = null;

	private static Method METHOD_CAST, METHOD_TEST_AND_APPLY, METHOD_GET_VERSION_INFO_MAP0,
			METHOD_GET_VERSION_INFO_MAP_PE, METHOD_GET_VERSION_INFO_MAP2 = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		if ((METHOD_TEST_AND_APPLY = SpeechApiSystemSpeechImpl.class.getDeclaredMethod("testAndApply", Predicate.class,
				Object.class, FailableFunction.class, FailableFunction.class)) != null) {
			//
			METHOD_TEST_AND_APPLY.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_GET_VERSION_INFO_MAP0 = SpeechApiSystemSpeechImpl.class
				.getDeclaredMethod("getVersionInfoMap")) != null) {
			//
			METHOD_GET_VERSION_INFO_MAP0.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_GET_VERSION_INFO_MAP_PE = SpeechApiSystemSpeechImpl.class.getDeclaredMethod("getVersionInfoMap",
				PE.class)) != null) {
			//
			METHOD_GET_VERSION_INFO_MAP_PE.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_GET_VERSION_INFO_MAP2 = SpeechApiSystemSpeechImpl.class.getDeclaredMethod("getVersionInfoMap2",
				ResourceEntry[].class)) != null) {
			//
			METHOD_GET_VERSION_INFO_MAP2.setAccessible(true);
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
	void testSpeakSsml() {
		//
		Assertions.assertDoesNotThrow(() -> instance.speakSsml(null, null, 0, 0));
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
			Assertions.assertNull(SpeechApi.getVoiceAttribute(instance, voiceId = voiceIds[i], null));
			//
			Assertions.assertNull(SpeechApi.getVoiceAttribute(instance, voiceId, ""));
			//
			Assertions.assertNotNull(SpeechApi.getVoiceAttribute(instance, voiceId, "Language"));
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
		if (METHOD_CAST != null) {
			//
			Assertions.assertNull(Util.cast(null, null));
			//
			Assertions.assertNull(Util.cast(Object.class, null));
			//
		} // if
			//
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

	@Test
	void testGetVersionInfoMap() throws Throwable {
		//
		if (CLASS_JNA != null) {
			//
			Assertions.assertSame(getVersionInfoMap(), getVersionInfoMap());
			//
		} // if
			//
		Assertions.assertNull(getVersionInfoMap((PE) null));
		//
		Assertions.assertNull(getVersionInfoMap((ResourceEntry[]) null));
		//
		final ResourceEntry re = new ResourceEntry();
		//
		final ResourceEntry[] res = new ResourceEntry[] { null, new ResourceEntry() };
		//
		Assertions.assertNull(getVersionInfoMap(res));
		//
		re.setData(new byte[] {});
		//
		Assertions.assertNull(getVersionInfoMap(res));
		//
	}

	private Map<String, String> getVersionInfoMap() throws Throwable {
		try {
			final Object obj = METHOD_GET_VERSION_INFO_MAP0.invoke(instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static Map<String, String> getVersionInfoMap(final PE pe) throws Throwable {
		try {
			final Object obj = METHOD_GET_VERSION_INFO_MAP_PE.invoke(null, pe);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Map<String, String> getVersionInfoMap(final ResourceEntry[] res) throws Throwable {
		try {
			final Object obj = METHOD_GET_VERSION_INFO_MAP2.invoke(null, (Object) res);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
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