package org.springframework.context.support;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.helger.css.ECSSUnit;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerPdfPanelTest {

	private static Method METHOD_SET_FONT_SIZE_AND_UNIT, METHOD_GET_SELECTED_ITEM, METHOD_IS_SELECTED = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = VoiceManagerPdfPanel.class;
		//
		(METHOD_SET_FONT_SIZE_AND_UNIT = clz.getDeclaredMethod("setFontSizeAndUnit", Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_SELECTED_ITEM = clz.getDeclaredMethod("getSelectedItem", ComboBoxModel.class)).setAccessible(true);
		//
		(METHOD_IS_SELECTED = clz.getDeclaredMethod("isSelected", AbstractButton.class)).setAccessible(true);
		//
	}

	private VoiceManagerPdfPanel instance = null;

	@BeforeEach
	private void beforeEach() {
		//
		instance = new VoiceManagerPdfPanel();
		//
	}

	@Test
	void testSetPdAnnotationRectangleSize() throws NoSuchFieldException {
		//
		final Field pdAnnotationRectangleSize = VoiceManagerPdfPanel.class
				.getDeclaredField("pdAnnotationRectangleSize");
		//
		if (instance != null) {
			//
			final Number number = Integer.valueOf(0);
			//
			instance.setPdAnnotationRectangleSize(number);
			//
			Assertions.assertEquals(number, Narcissus.getField(instance, pdAnnotationRectangleSize));
			//
			instance.setPdAnnotationRectangleSize(Util.toString(number));
			//
			Assertions.assertEquals(number, Narcissus.getField(instance, pdAnnotationRectangleSize));
			//
			Assertions.assertThrows(IllegalArgumentException.class, () -> instance.setPdAnnotationRectangleSize(""));
			//
		} // if
			//
	}

	@Test
	void testSetSpeechSpeedMap() throws NoSuchFieldException, JsonProcessingException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		instance.setSpeechSpeedMap("{\"1\":2}");
		//
		Assertions.assertEquals(Collections.singletonMap(Integer.valueOf(1), "2"),
				Narcissus.getField(instance, VoiceManagerPdfPanel.class.getDeclaredField("speechSpeedMap")));
		//
	}

	@Test
	void testSetFontSizeAndUnit() throws Throwable {
		//
		final JTextComponent tfFontSize1 = new JTextField();
		//
		Narcissus.setField(instance, VoiceManagerPdfPanel.class.getDeclaredField("tfFontSize1"), tfFontSize1);
		//
		final ComboBoxModel<?> cbmFontSize1 = new DefaultComboBoxModel<>();
		//
		Narcissus.setField(instance, VoiceManagerPdfPanel.class.getDeclaredField("cbmFontSize1"), cbmFontSize1);
		//
		setFontSizeAndUnit(19);
		//
		Assertions.assertEquals("42", Util.getText(tfFontSize1));
		//
		Assertions.assertEquals(ECSSUnit.PX, getSelectedItem(cbmFontSize1));
		//
		setFontSizeAndUnit(18);
		//
		Assertions.assertEquals("43", Util.getText(tfFontSize1));
		//
		Assertions.assertEquals(ECSSUnit.PX, getSelectedItem(cbmFontSize1));
		//
		setFontSizeAndUnit(16);
		//
		Assertions.assertEquals("50", Util.getText(tfFontSize1));
		//
		Assertions.assertEquals(ECSSUnit.PX, getSelectedItem(cbmFontSize1));
		//
		setFontSizeAndUnit(14);
		//
		Assertions.assertEquals("56", Util.getText(tfFontSize1));
		//
		Assertions.assertEquals(ECSSUnit.PX, getSelectedItem(cbmFontSize1));
		//
		setFontSizeAndUnit(12);
		//
		Assertions.assertEquals("66", Util.getText(tfFontSize1));
		//
		Assertions.assertEquals(ECSSUnit.PX, getSelectedItem(cbmFontSize1));
		//
		setFontSizeAndUnit(10);
		//
		Assertions.assertEquals("80", Util.getText(tfFontSize1));
		//
		Assertions.assertEquals(ECSSUnit.PX, getSelectedItem(cbmFontSize1));
		//
	}

	private static Object getSelectedItem(final ComboBoxModel<?> instance) throws Throwable {
		try {
			return METHOD_GET_SELECTED_ITEM.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private void setFontSizeAndUnit(final int length) throws Throwable {
		try {
			METHOD_SET_FONT_SIZE_AND_UNIT.invoke(instance, length);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsSelected() throws Throwable {
		//
		final AbstractButton ab = new JButton();
		//
		Assertions.assertFalse(isSelected(ab));
		//
		ab.setSelected(true);
		//
		Assertions.assertTrue(isSelected(ab));
		//
	}

	private static boolean isSelected(final AbstractButton instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_SELECTED.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

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
				if (Util.contains(Arrays.asList(Float.TYPE, Boolean.TYPE, Integer.TYPE), m.getReturnType()) || or(
						Boolean.logicalAnd(Objects.equals(name, "pdf"),
								Arrays.equals(parameterTypes, new Class<?>[] { Path.class })),
						Boolean.logicalAnd(Objects.equals(name, "getNumberAndUnit"),
								Arrays.equals(parameterTypes, new Class<?>[] { String.class })),
						Boolean.logicalAnd(Objects.equals(name, "getNumber"),
								Arrays.equals(parameterTypes, new Class<?>[] { Object.class })),
						Boolean.logicalAnd(Objects.equals(name, "getDefaultSpeechSpeedMap"),
								m.getParameterCount() == 0),
						Boolean.logicalAnd(
								Objects.equals(name,
										"getPDImageXObjectCreateFromByteArrayDetectFileTypeMethodAndAllowedFileTypes"),
								m.getParameterCount() == 0))) {
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

	private static boolean or(final boolean a, final boolean b, final boolean... bs) {
		//
		boolean result = a || b;
		//
		if (result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (result |= bs[i]) {
				//
				return result;
				//
			} // if
				//
		} // for
			//
		return result;
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
		Object invoke, object = null;
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
				if (object == null && clz != null) {
					//
					object = Narcissus.allocateInstance(clz);
					//
				} // if
					//
				if (Objects.equals(name = Util.getName(m), "invoke") && Arrays.equals(parameterTypes,
						new Class<?>[] { Object.class, Method.class, Object[].class })) {
					//
					final Object instance_ = object;
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
					Assertions.assertNotNull(Narcissus.invokeMethod(object, m, os), toString);
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