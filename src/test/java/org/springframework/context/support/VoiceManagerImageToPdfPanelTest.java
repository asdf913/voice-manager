package org.springframework.context.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerImageToPdfPanelTest {

	private VoiceManagerImageToPdfPanel instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerImageToPdfPanel();
		//
	}

	@Test
	void testActionPerformed() throws Exception {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		instance.actionPerformed(null);
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerImageToPdfPanel.class.getDeclaredMethods();
		//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Method m = null;
		//
		Object invokeStaticMethod = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			for (int j = 0; (parameterTypes = Util.getParameterTypes(m)) != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.TRUE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Float.TYPE)) {
					//
					Util.add(collection, Float.valueOf(0));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // for
				//
			if (Util.isStatic(m)) {
				//
				invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(collection));
				//
				toString = Objects.toString(m);
				//
				if (Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE, Float.TYPE), Util.getReturnType(m))
						|| Boolean.logicalAnd(Objects.equals(Util.getName(m),
								"getPDImageXObjectCreateFromFileByContentDetectFileTypeMethodAndAllowedFileTypes"),
								Arrays.equals(Util.getParameterTypes(m), new Class<?>[] {}))) {
					//
					Assertions.assertNotNull(invokeStaticMethod, toString);
					//
				} else {
					//
					Assertions.assertNull(invokeStaticMethod, toString);
					//
				} // if
					//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(
						instance = ObjectUtils.getIfNull(instance, VoiceManagerImageToPdfPanel::new), m,
						toArray(collection)), toString);
				//
			} // if
				//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

}