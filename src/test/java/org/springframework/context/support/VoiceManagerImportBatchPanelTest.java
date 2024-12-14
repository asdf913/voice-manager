package org.springframework.context.support;

import java.awt.Component;
import java.io.Console;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.swing.JFileChooser;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerImportBatchPanelTest {

	private VoiceManagerImportBatchPanel instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerImportBatchPanel();
		//
	}

	@Test
	void testNull() throws ClassNotFoundException {
		//
		final Method[] ms = VoiceManagerImportBatchPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		List<Object> list = null;
		//
		Object[] os = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
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
				if (Objects.equals(parameterType = parameterTypes[j], Boolean.TYPE)) {
					//
					Util.add(list, Boolean.FALSE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(list, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Long.TYPE)) {
					//
					Util.add(list, Long.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Double.TYPE)) {
					//
					Util.add(list, Double.valueOf(0));
					//
				} else {
					//
					Util.add(list, null);
					//
				} // if
					//
			} // for
				//
			name = Util.getName(m);
			//
			toString = Objects.toString(m);
			//
			os = toArray(list);
			//
			if (Boolean.logicalAnd(Objects.equals(name, "getPassword"),
					Arrays.equals(parameterTypes, new Class<?>[] { Console.class }))
					|| Boolean.logicalAnd(Objects.equals(name, "createTempFile"),
							Arrays.equals(parameterTypes, new Class<?>[] { String.class, String.class }))
					|| Boolean.logicalAnd(Objects.equals(name, "showOpenDialog"),
							Arrays.equals(parameterTypes, new Class<?>[] { JFileChooser.class, Component.class }))
					|| Boolean.logicalAnd(Objects.equals(name, "createImportFileTemplateByteArray"),
							Arrays.equals(parameterTypes,
									new Class<?>[] { Boolean.TYPE, Collection.class, Collection.class }))
					|| Boolean.logicalAnd(Objects.equals(name, "actionPerformedForImport"),
							Arrays.equals(parameterTypes, new Class<?>[] { Object.class, Boolean.TYPE }))
					|| Boolean.logicalAnd(Objects.equals(name, "actionPerformedForBtnImport"),
							Arrays.equals(parameterTypes, new Class<?>[] { Boolean.TYPE }))
					|| Boolean.logicalAnd(Objects.equals(name, "createRange"),
							Arrays.equals(parameterTypes, new Class<?>[] { Integer.class, Integer.class }))
					|| Boolean.logicalAnd(Objects.equals(name, "createVolumeRange"),
							Arrays.equals(parameterTypes, new Class<?>[] { Object.class }))) {
				//
				continue;
				//
			} // if
				//
			System.out.println("method=" + m);// TODO
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (ArrayUtils.contains(new Class<?>[] { Integer.TYPE, Boolean.TYPE, Long.TYPE, Double.TYPE },
						m.getReturnType())
						|| Boolean.logicalAnd(Objects.equals(name, "getTempFileMinimumPrefixLength"),
								Arrays.equals(parameterTypes, new Class<?>[] {}))
						|| Boolean.logicalAnd(Objects.equals(name, "randomAlphabetic"),
								Arrays.equals(parameterTypes, new Class<?>[] { Integer.TYPE }))
						|| Boolean.logicalAnd(Objects.equals(name, "checkFileExtension"),
								Arrays.equals(parameterTypes, new Class<?>[] { String.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "getBooleanValues"),
								Arrays.equals(parameterTypes, new Class<?>[] {}))
						|| Boolean.logicalAnd(Objects.equals(name, "getIntegerValueFromCell"),
								Arrays.equals(parameterTypes, new Class<?>[] { Class.forName(
										"org.springframework.context.support.VoiceManagerImportBatchPanel$ObjectMap") }))
						|| Boolean.logicalAnd(Objects.equals(name, "getObjectsByGroupAnnotation"),
								Arrays.equals(parameterTypes, new Class<?>[] { Object.class, String.class }))) {
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
				invoke = Narcissus.invokeMethod(instance, m, os);
				//
				if (ArrayUtils.contains(new Class<?>[] { Void.TYPE }, m.getReturnType())
						|| Boolean.logicalAnd(Objects.equals(name, "getRate"),
								Arrays.equals(parameterTypes, new Class<?>[] {}))
						|| Boolean.logicalAnd(Objects.equals(name, "getVoiceIdForExecute"),
								Arrays.equals(parameterTypes, new Class<?>[] { Boolean.TYPE }))) {
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