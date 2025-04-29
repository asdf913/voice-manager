package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerTtsPanelTest {

	private VoiceManagerTtsPanel instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerTtsPanel();
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerTtsPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		List<Object> list = null;
		//
		Object[] os = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType;
		//
		String name;
		//
		Object invoke;
		//
		String toString;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			if ((list = ObjectUtils.getIfNull(list, ArrayList::new)) != null) {
				//
				list.clear();
				//
			} // if
				//
			for (int j = 0; (parameterTypes = m.getParameterTypes()) != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = parameterTypes[j], Integer.TYPE)) {
					//
					Util.add(list, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
					//
					Util.add(list, Boolean.FALSE);
					//
				} else {
					//
					Util.add(list, null);
					//
				} // if
					//
			} // for
				//
			toString = Objects.toString(m);
			//
			name = Util.getName(m);
			//
			os = toArray(list);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (ArrayUtils.contains(new Class<?>[] { Boolean.TYPE, Integer.TYPE }, m.getReturnType())
						|| Boolean.logicalAnd(Objects.equals(name, "createProviderVersionJTextComponent"),
								Arrays.equals(parameterTypes, new Class[] { Boolean.TYPE, Provider.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "createProviderPlatformJTextComponent"),
								Arrays.equals(parameterTypes, new Class[] { Boolean.TYPE, Provider.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "createVolumeRange"),
								Arrays.equals(parameterTypes, new Class[] { Object.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "createRange"),
								Arrays.equals(parameterTypes, new Class[] { Integer.class, Integer.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "getObjectsByGroupAnnotation"),
								Arrays.equals(parameterTypes, new Class[] { Object.class, String.class, Class.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "getObjectsByGroupAnnotation"),
								Arrays.equals(parameterTypes, new Class[] { Object.class, String.class }))) {
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
				if (Boolean.logicalAnd(Objects.equals(name, "actionPerformedForWriteVoice"),
						Arrays.equals(parameterTypes, new Class[] { Boolean.TYPE }))
						&& !GraphicsEnvironment.isHeadless()) {
					//
					continue;
					//
				} // if
					//
				invoke = Narcissus.invokeMethod(instance, m, os);
				//
				if (ArrayUtils.contains(new Class<?>[] { Void.TYPE }, m.getReturnType()) || Boolean
						.logicalAnd(Objects.equals(name, "getRate"), Arrays.equals(parameterTypes, new Class[] {}))) {
					//
					Assertions.assertNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} // if
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