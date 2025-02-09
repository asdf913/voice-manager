package org.springframework.context.support;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.helger.css.ECSSUnit;

import io.github.toolfactory.narcissus.Narcissus;
import j2html.rendering.HtmlBuilder;

class VoiceManagerPdfPanelTest {

	private static Method METHOD_SET_FONT_SIZE_AND_UNIT, METHOD_GET_SELECTED_ITEM, METHOD_TO_HTML = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = VoiceManagerPdfPanel.class;
		//
		(METHOD_SET_FONT_SIZE_AND_UNIT = clz.getDeclaredMethod("setFontSizeAndUnit", Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_SELECTED_ITEM = clz.getDeclaredMethod("getSelectedItem", ComboBoxModel.class)).setAccessible(true);
		//
		(METHOD_TO_HTML = clz.getDeclaredMethod("toHtml", HtmlBuilder.class, String.class, String.class))
				.setAccessible(true);
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
				Narcissus.getField(instance, Util.getDeclaredField(VoiceManagerPdfPanel.class, "speechSpeedMap")));
		//
	}

	@Test
	void testSetFontSizeAndUnit() throws Throwable {
		//
		final JTextComponent tfFontSize1 = new JTextField();
		//
		Narcissus.setField(instance, Util.getDeclaredField(VoiceManagerPdfPanel.class, "tfFontSize1"), tfFontSize1);
		//
		final ComboBoxModel<?> cbmFontSize1 = new DefaultComboBoxModel<>();
		//
		Narcissus.setField(instance, Util.getDeclaredField(VoiceManagerPdfPanel.class, "cbmFontSize1"), cbmFontSize1);
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
	void testSetImageFormatOrders() throws NoSuchFieldException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		instance.setImageFormatOrders(Collections.singleton(null));
		//
		final Field field = Util.getDeclaredField(VoiceManagerPdfPanel.class, "imageFormatOrders");
		//
		if (field != null) {
			//
			field.setAccessible(true);
			//
		} // if
			//
		final Collection<?> singletonList = Collections.singletonList(null);
		//
		Assertions.assertEquals(singletonList, Narcissus.getField(instance, field));
		//
		instance.setImageFormatOrders(singletonList);
		//
		Assertions.assertEquals(singletonList, Narcissus.getField(instance, field));
		//
		instance.setImageFormatOrders("[null]".toCharArray());
		//
		Assertions.assertEquals(singletonList, Narcissus.getField(instance, field));
		//
		instance.setImageFormatOrders(new String[] { null });
		//
		Assertions.assertEquals(singletonList, Narcissus.getField(instance, field));
		//
		Object object = Boolean.TRUE;
		//
		instance.setImageFormatOrders(object);
		//
		Assertions.assertEquals(Collections.singletonList(Util.toString(object)), Narcissus.getField(instance, field));
		//
		instance.setImageFormatOrders(object = Integer.valueOf(0));
		//
		Assertions.assertEquals(Collections.singletonList(Util.toString(object)), Narcissus.getField(instance, field));
		//
	}

	@Test
	void testSetFontSizeAndUnitMap() throws NoSuchFieldException, JsonProcessingException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final ObjectMapper objectMapper = Util.cast(ObjectMapper.class,
				MethodUtils.invokeMethod(instance, true, "getObjectMapper"));
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance
				.setFontSizeAndUnitMap(ObjectMapperUtil.writeValueAsString(objectMapper, Integer.valueOf(1))));
		//
		instance.setFontSizeAndUnitMap(
				ObjectMapperUtil.writeValueAsString(objectMapper, Collections.singletonMap("1", "1px")));
		//
		final Field field = Util.getDeclaredField(VoiceManagerPdfPanel.class, "fontSizeAndUnitMap");
		//
		if (field != null) {
			//
			field.setAccessible(true);
			//
		} // if
			//
		Assertions.assertEquals(Collections.singletonMap(Integer.valueOf(1), "1px"),
				Narcissus.getField(instance, field));
		//
	}

	@Test
	void testActionPerformed() throws IllegalAccessException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final AbstractButton btnGenerateRubyHtml = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnGenerateRubyHtml", btnGenerateRubyHtml, true);
		//
		final ActionEvent actionEvent = new ActionEvent(btnGenerateRubyHtml, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		final JTextComponent taHtml = new JTextArea();
		//
		Util.setText(taHtml, "それは大変ですね。すぐ交番に行かないと。");
		//
		FieldUtils.writeDeclaredField(instance, "taHtml", taHtml, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		Assertions.assertEquals(
				"それは<ruby><rb>大変</rb><rp>(</rp><rt>たいへん</rt><rp>)</rp></ruby>ですね。すぐ<ruby><rb>交番</rb><rp>(</rp><rt>こうばん</rt><rp>)</rp></ruby>に<ruby><rb>行</rb><rp>(</rp><rt>い</rt><rp>)</rp></ruby>かないと。",
				Util.getText(taHtml));
		//
		Util.setText(taHtml, "A");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		Assertions.assertEquals(Util.getText(taHtml), Util.getText(taHtml));
		//
		Util.setText(taHtml, "テーブルに花瓶が置いてあります。");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		Assertions.assertEquals(
				"テーブルに<ruby><rb>花瓶</rb><rp>(</rp><rt>かびん</rt><rp>)</rp></ruby>が<ruby><rb>置</rb><rp>(</rp><rt>お</rt><rp>)</rp></ruby>いてあります。",
				Util.getText(taHtml));
		//
		Util.setText(taHtml, "ごみ箱は台所の隅に置いてあります。");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		Assertions.assertEquals(
				"ごみ<ruby><rb>箱</rb><rp>(</rp><rt>ばこ</rt><rp>)</rp></ruby>は<ruby><rb>台所</rb><rp>(</rp><rt>だいどころ</rt><rp>)</rp></ruby>の<ruby><rb>隅</rb><rp>(</rp><rt>すみ</rt><rp>)</rp></ruby>に<ruby><rb>置</rb><rp>(</rp><rt>お</rt><rp>)</rp></ruby>いてあります。",
				Util.getText(taHtml));
		//
		Util.setText(taHtml, "ハサミは引き出しに入れてあります。");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		Assertions.assertEquals(
				"ハサミは<ruby><rb>引</rb><rp>(</rp><rt>ひ</rt><rp>)</rp></ruby>き<ruby><rb>出</rb><rp>(</rp><rt>だ</rt><rp>)</rp></ruby>しに<ruby><rb>入</rb><rp>(</rp><rt>い</rt><rp>)</rp></ruby>れてあります。",
				Util.getText(taHtml));
		//
	}

	@Test
	void testToHtml() {
		//
		Assertions.assertDoesNotThrow(() -> toHtml(null, "", null));
		//
	}

	private static void toHtml(final HtmlBuilder<StringBuilder> htmlBuilder, final String text, final String ruby)
			throws Throwable {
		try {
			METHOD_TO_HTML.invoke(null, htmlBuilder, text, ruby);
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
		int parameterCount = 0;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()
					|| Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "main"),
							Arrays.equals(parameterTypes = m.getParameterTypes(), new Class<?>[] { String[].class }))
					|| Boolean.logicalAnd(Objects.equals(name, "and"), Arrays.equals(parameterTypes,
							new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class }))) {
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
								(parameterCount = m.getParameterCount()) == 0),
						Boolean.logicalAnd(
								Objects.equals(name,
										"getPDImageXObjectCreateFromByteArrayDetectFileTypeMethodAndAllowedFileTypes"),
								parameterCount == 0),
						Boolean.logicalAnd(Objects.equals(name, "createImageFormatComparator"),
								Arrays.equals(parameterTypes, new Class<?>[] { List.class })))) {
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
				if (Objects.equals(Boolean.TYPE, m.getReturnType()) || or(
						Boolean.logicalAnd(Objects.equals(name, "getTitle"),
								(parameterCount = m.getParameterCount()) == 0),
						Boolean.logicalAnd(Objects.equals(name, "getFontSizeAndUnitMap"), parameterCount == 0),
						Boolean.logicalAnd(Objects.equals(name, "getObjectMapper"), parameterCount == 0))) {
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