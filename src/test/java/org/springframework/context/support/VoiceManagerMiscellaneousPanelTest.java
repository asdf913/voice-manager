package org.springframework.context.support;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerMiscellaneousPanelTest {

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerMiscellaneousPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		String name, toString;
		//
		Object invoke = null;
		//
		VoiceManagerMiscellaneousPanel instance = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null
					// ||!Modifier.isStatic(m.getModifiers())
					|| m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			parameterTypes = m.getParameterTypes();
			//
			if ((collection = ObjectUtils.getIfNull(collection, ArrayList::new)) != null) {
				//
				collection.clear();
				//
			} // if
				//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = parameterTypes[j], Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Character.TYPE)) {
					//
					Util.add(collection, Character.valueOf(' '));
					//
				} else if (Objects.equals(parameterType, Long.TYPE)) {
					//
					Util.add(collection, Long.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // for
				//
			os = toArray(collection);
			//
			name = Util.getName(m);
			//
			toString = Objects.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (or(Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE, Long.TYPE), m.getReturnType()),
						Boolean.logicalAnd(Objects.equals(name, "getAccessibleObjectIsAccessibleMethod"),
								m.getParameterCount() == 0),
						Boolean.logicalAnd(Objects.equals(name, "getObjectsByGroupAnnotation"),
								Arrays.equals(parameterTypes, new Class<?>[] { Object.class, String.class })
										|| Arrays.equals(parameterTypes,
												new Class<?>[] { Object.class, String.class, Class.class })),
						Boolean.logicalAnd(Objects.equals(name, "getPageTitle"),
								Arrays.equals(parameterTypes, new Class<?>[] { String.class, Duration.class })))) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
					//
			} else {
				//
				invoke = Narcissus.invokeMethod(
						instance = ObjectUtils.getIfNull(instance, VoiceManagerMiscellaneousPanel::new), m, os);
				//
				if (Boolean.logicalAnd(
						Util.contains(Arrays.asList("getTitle",
								"getMicrosoftSpeechPlatformRuntimeLanguagesDownloadPageTitle"), name),
						m.getParameterCount() == 0)) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
					//
			} // if
				//
		} // for
			//
	}

	private static boolean or(final boolean a, final boolean b, final boolean... bs) {
		//
		if (a || b) {
			//
			return true;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (bs[i]) {
				//
				return true;
				//
			} // if
				//
		} // for
			//
		return false;
		//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

}