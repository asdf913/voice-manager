package org.springframework.context.support;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerPdfPanelTest {

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerPdfPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		Object invoke = null;
		//
		String name, toString = null;
		//
		Object[] os = null;
		//
		VoiceManagerPdfPanel instance = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()
					|| Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "main"),
							Arrays.equals(m.getParameterTypes(), new Class<?>[] { String[].class }))) {
				//
				continue;
				//
			} // if
				//
			if ((collection = ObjectUtils.getIfNull(collection, ArrayList::new)) != null) {
				//
				collection.clear();
				//
			} // if
				//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = parameterTypes[j], Float.TYPE)) {
					//
					Util.add(collection, Float.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // if
				//
			os = toArray(collection);
			//
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (Util.contains(Arrays.asList(Float.TYPE, Boolean.TYPE, Integer.TYPE), m.getReturnType())
						|| Boolean.logicalAnd(Objects.equals(name, "pdf"),
								Arrays.equals(parameterTypes, new Class<?>[] { Path.class }))) {
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
				invoke = Narcissus.invokeMethod(instance = ObjectUtils.getIfNull(instance, VoiceManagerPdfPanel::new),
						m, os);
				//
				if (Boolean.logicalAnd(Objects.equals(name, "getTitle"), m.getParameterCount() == 0)) {
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

	@Test
	void testIH() {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.VoiceManagerPdfPanel$IH");
		//
		final Method[] ms = Util.getDeclaredMethods(clz);
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		Object invoke, instance = null;
		//
		String name, toString = null;
		//
		Object[] os = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			if ((collection = ObjectUtils.getIfNull(collection, ArrayList::new)) != null) {
				//
				collection.clear();
				//
			} // if
				//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = parameterTypes[j], Float.TYPE)) {
					//
					Util.add(collection, Float.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // if
				//
			toString = Util.toString(m);
			//
			os = toArray(collection);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (Util.contains(Arrays.asList(Float.TYPE, Boolean.TYPE, Integer.TYPE), m.getReturnType())) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} // if
					//
			} else {
				//
				if (instance == null && clz != null) {
					//
					instance = Narcissus.allocateInstance(clz);
					//
				} // if
					//
				if (Objects.equals(name = Util.getName(m), "invoke") && Arrays.equals(parameterTypes,
						new Class<?>[] { Object.class, Method.class, Object[].class })) {
					//
					final Object instance_ = instance;
					//
					final Method m_ = m;
					//
					final Object[] os_ = os;
					//
					Assertions.assertThrows(Throwable.class, () -> Narcissus.invokeMethod(instance_, m_, os_));
					//
					continue;
					//
				} // if
					//
				if (Boolean.logicalAnd(Objects.equals(name, "getObjects"), m.getParameterCount() == 0)) {
					//
					Assertions.assertNotNull(Narcissus.invokeMethod(instance, m, os), toString);
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