package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.Duration;
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
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.javatuples.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;
import com.helger.css.ECSSUnit;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.Playwright;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ConfigurationUtil;
import freemarker.template.Template;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoUtil;
import io.github.classgraph.HasNameUtil;
import io.github.toolfactory.narcissus.Narcissus;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import j2html.rendering.HtmlBuilder;

class VoiceManagerPdfPanelTest {

	private static Class<?> CLASS_SHLWAPI = null;

	private static Method METHOD_SET_FONT_SIZE_AND_UNIT, METHOD_GET_SELECTED_ITEM, METHOD_TO_HTML,
			METHOD_GET_TEXT_ALIGNS, METHOD_CHOP, METHOD_GENERATE_PDF_HTML, METHOD_LENGTH,
			METHOD_GET_MINIMUM_AND_MAXIMUM_Y, METHOD_TEST_AND_APPLY, METHOD_GET_TEXT_WIDTH, METHOD_OR,
			METHOD_TO_AUDIO_RESOURCE, METHOD_LIST_FILES, METHOD_IS_DIRECTORY, METHOD_GET_TRANSFER_DATA,
			METHOD_FIND_MATCH, METHOD_TO_MILLIS, METHOD_TEST_AND_ACCEPT, METHOD_IIF, METHOD_PATH_FILE_EXISTS_W = null;

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
		(METHOD_GET_MINIMUM_AND_MAXIMUM_Y = clz.getDeclaredMethod("getMinimumAndMaximumY", BufferedImage.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_TEXT_WIDTH = clz.getDeclaredMethod("getTextWidth", String.class, PDFont.class, Float.TYPE))
				.setAccessible(true);
		//
		(METHOD_OR = clz.getDeclaredMethod("or", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_TO_AUDIO_RESOURCE = clz.getDeclaredMethod("toAudioResource", ContentInfoUtil.class, File[].class))
				.setAccessible(true);
		//
		(METHOD_LIST_FILES = clz.getDeclaredMethod("listFiles", File.class)).setAccessible(true);
		//
		(METHOD_IS_DIRECTORY = clz.getDeclaredMethod("isDirectory", File.class)).setAccessible(true);
		//
		(METHOD_GET_TRANSFER_DATA = clz.getDeclaredMethod("getTransferData", Transferable.class, DataFlavor.class))
				.setAccessible(true);
		//
		(METHOD_FIND_MATCH = clz.getDeclaredMethod("findMatch", ContentInfoUtil.class, byte[].class))
				.setAccessible(true);
		//
		(METHOD_TO_MILLIS = clz.getDeclaredMethod("toMillis", Duration.class, Long.TYPE)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				FailableConsumer.class, FailableConsumer.class)).setAccessible(true);
		//
		(METHOD_IIF = clz.getDeclaredMethod("iif", Boolean.TYPE, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_PATH_FILE_EXISTS_W = clz.getDeclaredMethod("PathFileExistsW",
				CLASS_SHLWAPI = Class.forName("org.springframework.context.support.VoiceManagerPdfPanel$Shlwapi"),
				String.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Object transferData = null;

		private Boolean PathFileExistsW = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Transferable && Objects.equals(methodName, "getTransferData")) {
				//
				return transferData;
				//
			} else if (Util.isAssignableFrom(CLASS_SHLWAPI, Util.getClass(proxy))
					&& Objects.equals(methodName, "PathFileExistsW")) {
				//
				return PathFileExistsW;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManagerPdfPanel instance = null;

	private Decoder decoder = null;

	private ContentInfoUtil contentInfoUtil = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerPdfPanel();
		//
		decoder = Base64.getDecoder();
		//
		contentInfoUtil = new ContentInfoUtil();
		//
		ih = new IH();
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
		FieldUtils.writeDeclaredField(instance, "taHtml", taHtml, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnGenerateRubyHtml));
		//
		Util.setText(taHtml, "A");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnGenerateRubyHtml));
		//
		Assertions.assertEquals(Util.getText(taHtml), Util.getText(taHtml));
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnGenerateRubyHtml));
		//
		// btnGenerateRubyHtml
		//
		final AbstractButton btnSetOriginalAudio = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSetOriginalAudio", btnSetOriginalAudio, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnSetOriginalAudio, 0, null)));
		//
		// btnAudioFile
		//
		final AbstractButton btnAudioFile = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnAudioFile", btnAudioFile, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnAudioFile, 0, null)));
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
		try (final InputStream is = new ByteArrayInputStream(decode(decoder,
				"iVBORw0KGgoAAAANSUhEUgAAAAoAAAAFCAIAAADzBuo/AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAFBhaW50Lk5FVCA1LjEuM4y7MyAAAAC2ZVhJZklJKgAIAAAABQAaAQUAAQAAAEoAAAAbAQUAAQAAAFIAAAAoAQMAAQAAAAIAAAAxAQIAEAAAAFoAAABphwQAAQAAAGoAAAAAAAAAYAAAAAEAAABgAAAAAQAAAFBhaW50Lk5FVCA1LjEuMwADAACQBwAEAAAAMDIzMAGgAwABAAAAAQAAAAWgBAABAAAAlAAAAAAAAAACAAEAAgAEAAAAUjk4AAIABwAEAAAAMDEwMAAAAAAOmhdjvMuD8gAAACRJREFUGFdj/P//PwNuAJVmZGSE8OEAIs4E4eACBKTx2s3AAAC16Qv//9Y5UgAAAABJRU5ErkJggg=="))) {
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

	private static byte[] decode(final Decoder instance, final String src) {
		return instance != null ? instance.decode(src) : null;
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
	void testGetMinimumAndMaximumY() throws Throwable {
		//
		try (final InputStream is = new ByteArrayInputStream(decode(decoder,
				"iVBORw0KGgoAAAANSUhEUgAAAAEAAAAFCAIAAAAL5hHIAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAFBhaW50Lk5FVCA1LjEuNBLfpoMAAAC2ZVhJZklJKgAIAAAABQAaAQUAAQAAAEoAAAAbAQUAAQAAAFIAAAAoAQMAAQAAAAIAAAAxAQIAEAAAAFoAAABphwQAAQAAAGoAAAAAAAAAYAAAAAEAAABgAAAAAQAAAFBhaW50Lk5FVCA1LjEuNAADAACQBwAEAAAAMDIzMAGgAwABAAAAAQAAAAWgBAABAAAAlAAAAAAAAAACAAEAAgAEAAAAUjk4AAIABwAEAAAAMDEwMAAAAADX5rshveZftAAAABVJREFUGFdj+P//PwMIAGkmMIOBAQBT0AX96VNS9AAAAABJRU5ErkJggg=="))) {
			//
			Assertions.assertEquals(IntIntPair.of(1, 4), getMinimumAndMaximumY(ImageIO.read(is)));
			//
		} // try
			//
	}

	private static IntIntPair getMinimumAndMaximumY(final BufferedImage bi) throws Throwable {
		try {
			final Object obj = METHOD_GET_MINIMUM_AND_MAXIMUM_Y.invoke(null, bi);
			if (obj == null) {
				return null;
			} else if (obj instanceof IntIntPair) {
				return (IntIntPair) obj;
			} // if
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetBrowserType() throws Throwable {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		instance.setBrowserType(null);
		//
		final Class<?> clz = Util.getClass(instance);
		//
		final List<Field> fs = Util.toList(
				Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredFields(clz), Arrays::stream, null), f -> {
					//
					final Type[] actualTypeArguments = getActualTypeArguments(
							Util.cast(ParameterizedType.class, f != null ? f.getGenericType() : null));
					//
					return Objects.equals(Util.getType(f), FailableFunction.class) && actualTypeArguments != null
							&& actualTypeArguments.length > 0
							&& Objects.equals(ArrayUtils.get(actualTypeArguments, 0), Playwright.class);
					//
				}));
		//
		final int size = IterableUtils.size(fs);
		//
		if (size > 1) {
			//
			throw new IllegalArgumentException();
			//
		} // if
			//
		final Field field = size == 1 ? IterableUtils.get(fs, 0) : null;
		//
		Assertions.assertNull(FailableFunctionUtil
				.apply(Util.cast(FailableFunction.class, FieldUtils.readField(field, instance, true)), null));
		//
		instance.setBrowserType("c");
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			Assertions.assertNotNull(FailableFunctionUtil
					.apply(Util.cast(FailableFunction.class, FieldUtils.readField(field, instance, true)), playwright));
			//
		} // try
			//
		instance.setBrowserType("");
		//
		try (final Playwright playwright = Playwright.create()) {
			//
			Assertions.assertThrows(Exception.class, () -> FailableFunctionUtil
					.apply(Util.cast(FailableFunction.class, FieldUtils.readField(field, instance, true)), playwright));
			//
		} // try
			//
	}

	private static Type[] getActualTypeArguments(final ParameterizedType instance) {
		return instance != null ? instance.getActualTypeArguments() : null;
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
	void testGetTextWidth() throws Throwable {
		//
		final Iterable<ClassInfo> classInfos = ClassInfoUtil.getClassInfos();
		//
		if (classInfos == null || classInfos.iterator() == null) {
			//
			return;
			//
		} // if
			//
		Class<?> clz = null;
		//
		for (final ClassInfo classInfo : classInfos) {
			//
			if (classInfo == null
					|| !Util.isAssignableFrom(PDFont.class, clz = Util.forName(HasNameUtil.getName(classInfo)))
					|| (clz != null && Modifier.isAbstract(clz.getModifiers()))) {
				//
				continue;
				//
			} // if
				//
			System.out.println(clz);
			//
			Assertions.assertEquals(0, getTextWidth(" ", Util.cast(PDFont.class, Narcissus.allocateInstance(clz)), 0),
					Util.getName(clz));
			//
		} // for
			//
	}

	private static float getTextWidth(final String text, final PDFont font, final float fontSize) throws Throwable {
		try {
			final Object obj = METHOD_GET_TEXT_WIDTH.invoke(null, text, font, fontSize);
			if (obj instanceof Float) {
				return ((Float) obj).floatValue();
			} // if
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToAudioResource() throws Throwable {
		//
		Assertions.assertNull(toAudioResource(contentInfoUtil, new File[] { null, Util.toFile(Path.of(".")) }));
		//
		final File file = File.createTempFile(nextAlphanumeric(RandomStringUtils.secure(), 3), null);
		//
		if (file != null) {
			//
			file.deleteOnExit();
			//
		} // if
			//
		Assertions.assertNull(toAudioResource(contentInfoUtil, new File[] { file }));
		//
		if (Util.exists(file)) {
			//
			FileUtils.deleteQuietly(file);
			//
		} // if
			//
	}

	private static String nextAlphanumeric(final RandomStringUtils instance, final int count) {
		return instance != null ? instance.nextAlphanumeric(count) : null;
	}

	private static Resource toAudioResource(final ContentInfoUtil ciu, final File[] fs) throws Throwable {
		try {
			final Object obj = METHOD_TO_AUDIO_RESOURCE.invoke(null, ciu, fs);
			if (obj == null) {
				return null;
			} else if (obj instanceof Resource) {
				return (Resource) obj;
			} // if
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testListFiles() throws Throwable {
		//
		Assertions.assertNotNull(listFiles(Util.toFile(Path.of("."))));
		//
	}

	private static File[] listFiles(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_LIST_FILES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof File[]) {
				return (File[]) obj;
			} // if
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsDirectory() throws Throwable {
		//
		Assertions.assertFalse(isDirectory(Util.toFile(Path.of("pom.xml"))));
		//
		Assertions.assertTrue(isDirectory(Util.toFile(Path.of("."))));
		//
	}

	private static boolean isDirectory(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_DIRECTORY.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			} // if
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetTransferData() throws Throwable {
		//
		Assertions.assertNull(getTransferData(Reflection.newProxy(Transferable.class, ih), null));
		//
	}

	private static Object getTransferData(final Transferable instance, final DataFlavor flavor) throws Throwable {
		try {
			return METHOD_GET_TRANSFER_DATA.invoke(null, instance, flavor);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFindMatch() throws Throwable {
		//
		Assertions.assertNull(findMatch(contentInfoUtil, null));
		//
		Assertions.assertNotNull(findMatch(contentInfoUtil, new byte[] {}));
		//
	}

	private static ContentInfo findMatch(final ContentInfoUtil instance, final byte[] bs) throws Throwable {
		try {
			final Object obj = METHOD_FIND_MATCH.invoke(null, instance, bs);
			if (obj == null) {
				return null;
			} else if (obj instanceof ContentInfo) {
				return (ContentInfo) obj;
			} // if
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMillis() throws Throwable {
		//
		final long one = 1;
		//
		Assertions.assertEquals(one, toMillis(Duration.ofMillis(one), 0));
		//
	}

	private static long toMillis(final Duration instance, final long defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_TO_MILLIS.invoke(null, instance, defaultValue);
			if (obj instanceof Long) {
				return ((Long) obj).longValue();
			} // if
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null, null));
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumerTrue, final FailableConsumer<T, E> consumerFalse) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, predicate, value, consumerTrue, consumerFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIif() throws Throwable {
		//
		Assertions.assertNull(iif(true, null, null));
		//
	}

	private static <T> T iif(final boolean b, final T valueTrue, final T valueFalse) throws Throwable {
		try {
			return (T) METHOD_IIF.invoke(null, b, valueTrue, valueFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPathFileExistsW() throws Throwable {
		//
		final Object shlwapi = Reflection.newProxy(CLASS_SHLWAPI, ih);
		//
		if (ih != null) {
			//
			ih.PathFileExistsW = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertFalse(PathFileExistsW(shlwapi, null));
		//
		if (ih != null) {
			//
			ih.PathFileExistsW = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertTrue(PathFileExistsW(shlwapi, null));
		//
	}

	private static boolean PathFileExistsW(final Object shlwapi, final String pszPath) throws Throwable {
		try {
			final Object obj = METHOD_PATH_FILE_EXISTS_W.invoke(null, shlwapi, pszPath);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testNull() throws Throwable {
		//
		final Class<?> clz = VoiceManagerPdfPanel.class;
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
					|| Boolean.logicalOr(
							Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "main"),
									Arrays.equals(parameterTypes = m.getParameterTypes(),
											new Class<?>[] { String[].class })),
							Boolean.logicalAnd(Util.contains(Arrays.asList("and", "or"), name), Arrays.equals(
									parameterTypes, new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class })))) {
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
				if (Util.contains(Arrays.asList(Float.TYPE, Boolean.TYPE, Integer.TYPE, Long.TYPE), m.getReturnType())
						|| or(Boolean.logicalAnd(Objects.equals(name, "pdf"),
								Arrays.equals(parameterTypes, new Class<?>[] { Path.class })),
								Boolean.logicalAnd(Objects.equals(name, "getNumberAndUnit"),
										Arrays.equals(parameterTypes, new Class<?>[] { String.class })),
								Boolean.logicalAnd(Objects.equals(name, "getNumber"),
										Arrays.equals(parameterTypes, new Class<?>[] { Object.class })),
								Boolean.logicalAnd(Objects.equals(name, "getDefaultSpeechSpeedMap"),
										(parameterCount = m.getParameterCount()) == 0),
								Boolean.logicalAnd(Objects.equals(name,
										"getPDImageXObjectCreateFromByteArrayDetectFileTypeMethodAndAllowedFileTypes"),
										parameterCount == 0),
								Boolean.logicalAnd(Objects.equals(name, "createImageFormatComparator"),
										Arrays.equals(parameterTypes, new Class<?>[] { List.class })),
								Boolean.logicalAnd(Objects.equals(name, "createStyleMap"),
										Arrays.equals(parameterTypes,
												new Class<?>[] { Map.class, BigDecimal.class, ECSSUnit.class })),
								Boolean.logicalAnd(Objects.equals(name, "generatePdfHtml"),
										Arrays.equals(parameterTypes,
												new Class<?>[] { Configuration.class, Map.class })),
								Boolean.logicalAnd(Objects.equals(name, "screenshot"),
										Arrays.equals(parameterTypes, new Class<?>[] { Path.class })),
								Boolean.logicalAnd(Objects.equals(name, "createImagePanel"),
										Arrays.equals(parameterTypes,
												new Class<?>[] { VoiceManagerPdfPanel.class, LayoutManager.class })),
								Boolean.logicalAnd(Objects.equals(name, "createAudioPanel"),
										Arrays.equals(parameterTypes,
												new Class<?>[] { VoiceManagerPdfPanel.class, LayoutManager.class })))) {
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
				forEach(testAndApply(Objects::nonNull,
						Util.getDeclaredFields(
								Util.getClass(instance = ObjectUtils.getIfNull(instance, VoiceManagerPdfPanel::new))),
						Arrays::stream, null), f -> {
							if (f != null && !Modifier.isStatic(f.getModifiers())) {
								Narcissus.setField(instance, f, null);
							}
						});
				//
				invoke = Narcissus.invokeMethod(instance = ObjectUtils.getIfNull(instance, VoiceManagerPdfPanel::new),
						m, os);
				//
				if (Objects.equals(Boolean.TYPE, m.getReturnType()) || Boolean.logicalAnd(m.getParameterCount() == 0,
						Util.contains(Arrays.asList("getTitle", "getFontSizeAndUnitMap", "getObjectMapper",
								"createStyleMap", "getPlaywrightBrowserTypeFunction"), name))) {
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

	private static <T> void forEach(final Stream<T> instance, final Consumer<T> action) {
		if (instance != null && action != null) {
			instance.forEach(action);
		}
	}

	@Test
	void testOr() throws Throwable {
		//
		Assertions.assertTrue(or(true, false));
		//
		Assertions.assertFalse(or(false, false, null));
		//
	}

	private static boolean or(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_OR.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			} // if
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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