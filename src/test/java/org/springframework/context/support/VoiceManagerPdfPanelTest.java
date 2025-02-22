package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstructionUtil;
import org.apache.bcel.generic.LDC_W;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.javatuples.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.helger.css.ECSSUnit;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ConfigurationUtil;
import freemarker.template.Template;
import io.github.toolfactory.narcissus.Narcissus;
import j2html.rendering.HtmlBuilder;

class VoiceManagerPdfPanelTest {

	private static Method METHOD_SET_FONT_SIZE_AND_UNIT, METHOD_GET_SELECTED_ITEM, METHOD_TO_HTML,
			METHOD_GET_TEXT_ALIGNS, METHOD_CHOP, METHOD_GENERATE_PDF_HTML, METHOD_LENGTH = null;

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
		(METHOD_GET_TEXT_ALIGNS = clz.getDeclaredMethod("getTextAligns")).setAccessible(true);
		//
		(METHOD_CHOP = clz.getDeclaredMethod("chop", BufferedImage.class)).setAccessible(true);
		//
		(METHOD_GENERATE_PDF_HTML = clz.getDeclaredMethod("generatePdfHtml", Configuration.class, Map.class))
				.setAccessible(true);
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", Object[].class)).setAccessible(true);
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
	void testSetTextAligns()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		//
		if (instance == null) {
			//
			return;
		} // if
			//
		final Field textAligns = VoiceManagerPdfPanel.class.getDeclaredField("textAligns");
		//
		if (textAligns != null) {
			//
			textAligns.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(FieldUtils.readField(textAligns, instance));
		//
		instance.setTextAligns(null);
		//
		Assertions.assertEquals(Unit.with(null), FieldUtils.readField(textAligns, instance));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> instance.setTextAligns("{}"));
		//
		final boolean b = true;
		//
		instance.setTextAligns(Boolean.toString(b));
		//
		Assertions.assertEquals(Unit.with(Collections.singletonList(Boolean.toString(b))),
				FieldUtils.readField(textAligns, instance));
		//
		final int one = 1;
		//
		instance.setTextAligns(Integer.toString(one));
		//
		Assertions.assertEquals(Unit.with(Collections.singletonList(Integer.toString(one))),
				FieldUtils.readField(textAligns, instance));
		//
		instance.setTextAligns(String.format("[%1$s]", one));
		//
		Assertions.assertEquals(Unit.with(Collections.singletonList(Integer.toString(one))),
				FieldUtils.readField(textAligns, instance));
		//
		String string = "";
		//
		instance.setTextAligns(string);
		//
		Assertions.assertEquals(Unit.with(Collections.singletonList(string)),
				FieldUtils.readField(textAligns, instance));
		//
		instance.setTextAligns(StringUtils.join("\"", string, "\""));
		//
		Assertions.assertEquals(Unit.with(Collections.singletonList(string)),
				FieldUtils.readField(textAligns, instance));
		//
		instance.setTextAligns(string = " ");
		//
		Assertions.assertEquals(Unit.with(Collections.singletonList(string)),
				FieldUtils.readField(textAligns, instance));
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
			// btnGenerateRubyHtml
			//
		final AbstractButton btnGenerateRubyHtml = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnGenerateRubyHtml", btnGenerateRubyHtml, true);
		//
		final ActionEvent actionEventBtnGenerateRubyHtml = new ActionEvent(btnGenerateRubyHtml, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnGenerateRubyHtml));
		//
		final JTextComponent taHtml = new JTextArea();
		//
		Util.setText(taHtml, "それは大変ですね。すぐ交番に行かないと。");
		//
		FieldUtils.writeDeclaredField(instance, "taHtml", taHtml, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnGenerateRubyHtml));
		//
		Assertions.assertEquals(
				"それは<ruby><rb>大変</rb><rp>(</rp><rt>たいへん</rt><rp>)</rp></ruby>ですね。すぐ<ruby><rb>交番</rb><rp>(</rp><rt>こうばん</rt><rp>)</rp></ruby>に<ruby><rb>行</rb><rp>(</rp><rt>い</rt><rp>)</rp></ruby>かないと。",
				Util.getText(taHtml));
		//
		Util.setText(taHtml, "A");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnGenerateRubyHtml));
		//
		Assertions.assertEquals(Util.getText(taHtml), Util.getText(taHtml));
		//
		Util.setText(taHtml, "テーブルに花瓶が置いてあります。");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnGenerateRubyHtml));
		//
		Assertions.assertEquals(
				"テーブルに<ruby><rb>花瓶</rb><rp>(</rp><rt>かびん</rt><rp>)</rp></ruby>が<ruby><rb>置</rb><rp>(</rp><rt>お</rt><rp>)</rp></ruby>いてあります。",
				Util.getText(taHtml));
		//
		Util.setText(taHtml, "ごみ箱は台所の隅に置いてあります。");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnGenerateRubyHtml));
		//
		Assertions.assertEquals(
				"ごみ<ruby><rb>箱</rb><rp>(</rp><rt>ばこ</rt><rp>)</rp></ruby>は<ruby><rb>台所</rb><rp>(</rp><rt>だいどころ</rt><rp>)</rp></ruby>の<ruby><rb>隅</rb><rp>(</rp><rt>すみ</rt><rp>)</rp></ruby>に<ruby><rb>置</rb><rp>(</rp><rt>お</rt><rp>)</rp></ruby>いてあります。",
				Util.getText(taHtml));
		//
		Util.setText(taHtml, "ハサミは引き出しに入れてあります。");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnGenerateRubyHtml));
		//
		Assertions.assertEquals(
				"ハサミは<ruby><rb>引</rb><rp>(</rp><rt>ひ</rt><rp>)</rp></ruby>き<ruby><rb>出</rb><rp>(</rp><rt>だ</rt><rp>)</rp></ruby>しに<ruby><rb>入</rb><rp>(</rp><rt>い</rt><rp>)</rp></ruby>れてあります。",
				Util.getText(taHtml));
		//
		Util.setText(taHtml, "7時の新幹線に乗る予定です。");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnGenerateRubyHtml));
		//
		Assertions.assertEquals(
				"7<ruby><rb>時</rb><rp>(</rp><rt>じ</rt><rp>)</rp></ruby>の<ruby><rb>新幹線</rb><rp>(</rp><rt>しんかんせん</rt><rp>)</rp></ruby>に<ruby><rb>乗</rb><rp>(</rp><rt>の</rt><rp>)</rp></ruby>る<ruby><rb>予定</rb><rp>(</rp><rt>よてい</rt><rp>)</rp></ruby>です。",
				Util.getText(taHtml));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			// btnPreviewRubyPdf
			//
			final AbstractButton btnPreviewRubyPdf = new JButton();
			//
			FieldUtils.writeDeclaredField(instance, "btnPreviewRubyPdf", btnPreviewRubyPdf, true);
			//
			final ActionEvent actionEventBtnPreviewRubyPdf = new ActionEvent(btnPreviewRubyPdf, 0, null);
			//
			Util.setText(taHtml, null);
			//
			Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnPreviewRubyPdf));
			//
		} // if
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
	void testGetTextAligns() throws Throwable {
		//
		Assertions.assertNull(getTextAligns());
		//
		if (instance != null) {
			//
			instance.setCssSpecificationUrl(null);
			//
		} // if
			//
		Assertions.assertNull(getTextAligns());
		//
	}

	private List<String> getTextAligns() throws Throwable {
		try {
			final Object obj = METHOD_GET_TEXT_ALIGNS.invoke(instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			} // if
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testChop() throws Throwable {
		//
		BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		//
		Assertions.assertSame(bi, chop(bi));
		//
		Assertions.assertSame(bi = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB), chop(bi));
		//
		final Decoder decoder = Base64.getDecoder();
		//
		try (final InputStream is = new ByteArrayInputStream(decoder != null ? decoder.decode(
				"iVBORw0KGgoAAAANSUhEUgAAAAoAAAAFCAIAAADzBuo/AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAFBhaW50Lk5FVCA1LjEuM4y7MyAAAAC2ZVhJZklJKgAIAAAABQAaAQUAAQAAAEoAAAAbAQUAAQAAAFIAAAAoAQMAAQAAAAIAAAAxAQIAEAAAAFoAAABphwQAAQAAAGoAAAAAAAAAYAAAAAEAAABgAAAAAQAAAFBhaW50Lk5FVCA1LjEuMwADAACQBwAEAAAAMDIzMAGgAwABAAAAAQAAAAWgBAABAAAAlAAAAAAAAAACAAEAAgAEAAAAUjk4AAIABwAEAAAAMDEwMAAAAAAOmhdjvMuD8gAAACRJREFUGFdj/P//PwNuAJVmZGSE8OEAIs4E4eACBKTx2s3AAAC16Qv//9Y5UgAAAABJRU5ErkJggg==")
				: null)) {
			//
			final BufferedImage result = chop(ImageIO.read(is));
			//
			Assertions.assertNotNull(result);
			//
			if (result != null) {
				//
				Assertions.assertEquals(6, result.getWidth());
				//
				Assertions.assertEquals(3, result.getHeight());
				//
			} // if
				//
		} // try
			//
	}

	private static BufferedImage chop(final BufferedImage bi) throws Throwable {
		try {
			final Object obj = METHOD_CHOP.invoke(null, bi);
			if (obj == null) {
				return null;
			} else if (obj instanceof BufferedImage) {
				return (BufferedImage) obj;
			} // if
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGeneratePdfHtml() throws Throwable {
		//
		Assertions.assertEquals("", generatePdfHtml(null, null));
		//
		final Configuration configuration = new Configuration(Configuration.getVersion());
		//
		Assertions.assertEquals("", generatePdfHtml(configuration, null));
		//
		final Class<?> clz = VoiceManagerPdfPanel.class;
		//
		final TemplateLoader tl = new ClassTemplateLoader(clz, "/");
		//
		Object ldcwGetValue = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))) {
			//
			final org.apache.bcel.classfile.Method method = JavaClassUtil.getMethod(
					ClassParserUtil.parse(new ClassParser(is, null)),
					Util.getDeclaredMethod(clz, "generatePdfHtml", Configuration.class, Map.class));
			//
			final ConstantPoolGen cpg = new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(method));
			//
			final Instruction[] ins = InstructionListUtil
					.getInstructions(MethodGenUtil.getInstructionList(new MethodGen(method, null, cpg)));
			//
			Instruction in = null;
			//
			for (int i = 0; i < length(ins); i++) {
				//
				if ((in = ArrayUtils.get(ins, i)) instanceof LDC_W ldcw) {
					//
					ldcwGetValue = ldcw.getValue(cpg);
					//
				} else if (in instanceof INVOKESTATIC invokestatic
						&& Objects.equals(invokestatic.getClassName(cpg), "freemarker.template.ConfigurationUtil")
						&& Objects.equals(InvokeInstructionUtil.getMethodName(invokestatic, cpg), "getTemplate")
						&& Objects.equals(Arrays.toString(InvokeInstructionUtil.getArgumentTypes(invokestatic, cpg)),
								"[freemarker.template.Configuration, java.lang.String]")) {
					//
					break;
					//
				} // if
					//
			} // for
				//
		} // try
			//
		final String templateName = String.format("%1$s", ObjectUtils.defaultIfNull(ldcwGetValue, "pdf.html.ftl"));
		//
		tl.findTemplateSource(templateName);
		//
		configuration.setTemplateLoader(tl);
		//
		final Template template = ConfigurationUtil.getTemplate(configuration, templateName);
		//
		final List<String> names = Util.toList(Util.filter(Util.map(FailableStreamUtil.stream(FailableStreamUtil.map(
				new FailableStream<>(Arrays.stream(Util.cast(Object[].class, FieldUtils
						.readField(FieldUtils.readDeclaredField(template, "rootElement", true), "childBuffer", true)))),
				x -> {
					//
					final String className = Util.getName(Util.getClass(x));
					//
					if (Objects.equals(className, "freemarker.core.ConditionalBlock")) {
						//
						return FieldUtils.readDeclaredField(FieldUtils.readDeclaredField(x, "condition", true), "exp",
								true);
						//
					} // if
						//
					return null;
					//
				})), Objects::toString), x -> !Objects.equals("null", x)));
		//
		Map<Object, Object> map = new LinkedHashMap<>(Map.of("k1", "v1", "k2", "v2"));
		//
		System.out.println(generatePdfHtml(configuration, Map.of("captionStyle", map)));
		//
		System.out.println(generatePdfHtml(configuration, Map.of("descriptionStyle", map)));
		//
		final List<Object> values = Arrays.asList(null, "", Boolean.TRUE, Integer.valueOf(0),
				Collections.singleton(null), Collections.singletonMap(null, null), new Date(0));
		//
		String name = null;
		//
		Object value = null;
		//
		for (int i = 0; i < IterableUtils.size(names); i++) {
			//
			name = IterableUtils.get(names, i);
			//
			for (int j = 0; j < IterableUtils.size(values); j++) {
				//
				System.out.println(Util.getClass(value = IterableUtils.get(values, j)));
				//
				if ((map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) != null) {
					//
					map.clear();
					//
				} // if
					//
				Util.put(map, name, value);
				//
				System.out.println(generatePdfHtml(configuration, map));
				//
			} // for
				//
		} // if
			//
	}

	private static int length(final Object[] instance) throws Throwable {
		try {
			final Object obj = METHOD_LENGTH.invoke(null, (Object) instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			} // if
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String generatePdfHtml(final Configuration configuration, final Map<?, ?> map) throws Throwable {
		try {
			final Object obj = METHOD_GENERATE_PDF_HTML.invoke(null, configuration, map);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			} // if
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
								Arrays.equals(parameterTypes, new Class<?>[] { List.class })),
						Boolean.logicalAnd(Objects.equals(name, "createStyleMap"),
								Arrays.equals(parameterTypes,
										new Class<?>[] { Map.class, BigDecimal.class, ECSSUnit.class })),
						Boolean.logicalAnd(Objects.equals(name, "generatePdfHtml"),
								Arrays.equals(parameterTypes, new Class<?>[] { Configuration.class, Map.class })))) {
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
						Boolean.logicalAnd(Objects.equals(name, "getObjectMapper"), parameterCount == 0),
						Boolean.logicalAnd(Objects.equals(name, "createStyleMap"), parameterCount == 0))) {
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