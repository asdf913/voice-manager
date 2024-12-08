package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.image.RenderedImage;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.Voice;
import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerImportSinglePanelTest {

	private VoiceManagerImportSinglePanel instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerImportSinglePanel();
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerImportSinglePanel.class.getDeclaredMethods();
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
				} else if (Objects.equals(parameterType, Long.TYPE)) {
					//
					Util.add(list, Long.valueOf(0));
					//
				} else {
					//
					Util.add(list, null);
					//
				} // if
					//
			} // for
				//
			if (Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "getAudioFile"),
					Arrays.equals(parameterTypes,
							new Class[] { Boolean.TYPE, JFileChooser.class, Voice.class, DefaultTableModel.class }))
					|| Boolean.logicalAnd(Objects.equals(name, "getAudioFile"),
							Arrays.equals(parameterTypes,
									new Class[] { Boolean.TYPE, Voice.class, DefaultTableModel.class }))
					|| Boolean.logicalAnd(Objects.equals(name, "actionPerformedForExecute"),
							Arrays.equals(parameterTypes, new Class[] { Boolean.TYPE, Boolean.TYPE }))
					|| Boolean.logicalAnd(Objects.equals(name, "actionPerformed"),
							Arrays.equals(parameterTypes, new Class[] { ActionEvent.class }))
					|| Boolean.logicalAnd(Objects.equals(name, "actionPerformedForPronunciation"),
							Arrays.equals(parameterTypes, new Class[] { Object.class }))
					|| Boolean.logicalAnd(Objects.equals(name, "actionPerformedForConversion"),
							Arrays.equals(parameterTypes, new Class[] { Object.class }))
					|| Boolean.logicalAnd(Objects.equals(name, "actionPerformedForSystemClipboardAnnotated"),
							Arrays.equals(parameterTypes, new Class[] { Boolean.TYPE, Object.class }))) {
				//
				continue;
				//
			} // if
				//
			toString = Objects.toString(m);
			//
			os = toArray(list);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (ArrayUtils.contains(new Class<?>[] { Boolean.TYPE, Long.TYPE, Integer.TYPE }, m.getReturnType())
						|| Boolean.logicalAnd(Objects.equals(name, "reverseRange"),
								Arrays.equals(parameterTypes, new Class[] { Integer.TYPE, Integer.TYPE }))
						|| Boolean.logicalAnd(Objects.equals(name, "createByteArray"),
								Arrays.equals(parameterTypes,
										new Class[] { RenderedImage.class, String.class, Boolean.TYPE }))
						|| Boolean.logicalAnd(Objects.equals(name, "getTempFileMinimumPrefixLength"),
								Arrays.equals(parameterTypes, new Class[] {}))
						|| Boolean.logicalAnd(Objects.equals(name, "getBooleanValues"),
								Arrays.equals(parameterTypes, new Class[] {}))
						|| Boolean.logicalAnd(Objects.equals(name, "getObjectsByGroupAnnotation"),
								Arrays.equals(parameterTypes, new Class[] { Object.class, String.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "createFunctionForBtnConvertToHiraganaOrKatakana"),
								Arrays.equals(parameterTypes, new Class[] { String.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "createYomiNameMap"),
								Arrays.equals(parameterTypes, new Class[] {}))
						|| Boolean.logicalAnd(Objects.equals(name, "createRange"),
								Arrays.equals(parameterTypes, new Class[] { Integer.class, Integer.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "createRange"),
								Arrays.equals(parameterTypes, new Class[] { Object.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "createPronunciationListCellRenderer"),
								Arrays.equals(parameterTypes, new Class[] { ListCellRenderer.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "createVolumeRange"),
								Arrays.equals(parameterTypes, new Class[] { Object.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "playAudio"),
								Arrays.equals(parameterTypes, new Class[] { String.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "getImageFormats"),
								Arrays.equals(parameterTypes, new Class[] {}))
						|| Boolean.logicalAnd(Objects.equals(name, "randomAlphabetic"),
								Arrays.equals(parameterTypes, new Class[] { Integer.TYPE }))) {
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
				if (ArrayUtils.contains(new Class<?>[] { Void.TYPE }, m.getReturnType())
						|| Boolean.logicalAnd(Objects.equals(name, "generateTtsAudioFile"),
								Arrays.equals(parameterTypes, new Class[] { Boolean.TYPE, Boolean.TYPE, Voice.class }))
						|| Boolean.logicalAnd(Objects.equals(name, "getVoiceIdForExecute"),
								Arrays.equals(parameterTypes, new Class[] { Boolean.TYPE }))
						|| Boolean.logicalAnd(Objects.equals(name, "getRate"),
								Arrays.equals(parameterTypes, new Class[] {}))) {
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