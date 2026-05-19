package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.AbstractButton;
import javax.swing.CellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.CellEditorListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ANEWARRAY;
import org.apache.bcel.generic.ARETURN;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.ILOAD;
import org.apache.bcel.generic.INVOKEDYNAMIC;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstructionUtil;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.LDCUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.NEW;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.state.PDGraphicsState;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriPredicate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Suppliers;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.JSHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyUtil;
import javazoom.jl.player.Player;

class VoiceManagerOjadAccentPanelTest {

	private static final String EMPTY = "";

	private static Class<?> CLASS_TEXT_AND_IMAGE, CLASS_IMAGE_DIMENSION_POSITION = null;

	private static Method METHOD_GET_FILE_EXTENSIONS, METHOD_FIND_MATCH, METHOD_PACK, METHOD_GET_KANJI,
			METHOD_GET_HEIGHT, METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_LENGTH_OBJECT_ARRAY, METHOD_LENGTH_DOUBLE_ARRAY,
			METHOD_TO_TEXT_AND_IMAGES, METHOD_TO_TEXT_AND_IMAGES1, METHOD_TO_TEXT_AND_IMAGES2, METHOD_TEST_AND_APPLY,
			METHOD_TO_BYTE_ARRAY, METHOD_GET_IF_NULL, METHOD_ATTRIBUTE, METHOD_CREATE_TEXT_AND_IMAGE_LIST_CELL_RENDERER,
			METHOD_SAVE_IMAGE, METHOD_TEST_AND_RUN_THROWS, METHOD_GET_PART_OF_SPEECH, METHOD_PREVIOUS_ELEMENT_SIBLINGS,
			METHOD_GET_PROPERTY, METHOD_GET_VOICE_URL_IMAGES, METHOD_MATCHES, METHOD_CREATE_TEXT_AND_IMAGE_CONSUMER,
			METHOD_TEST_AND_ACCEPT_INT_PREDICATE, METHOD_TEST_AND_ACCEPT_PREDICATE3, METHOD_TEST_AND_ACCEPT_PREDICATE5,
			METHOD_GET_MOST_OCCURENCE_COLOR, METHOD_SET_RGB, METHOD_SET_PART_OF_SPEECH, METHOD_ADJUST_IMAGE_COLOR,
			METHOD_CLOSE, METHOD_GET_TEXT_AND_IMAGES, METHOD_COMMON_PREFIX, METHOD_GET_CONJUGATION, METHOD_PROCESS_PAGE,
			METHOD_SET_HANDLER, METHOD_ADD_ANNOTATIONS, METHOD_MAP_TO_DOUBLE, METHOD_GET,
			METHOD_CREATE_PD_EMBEDDED_FILE, METHOD_GET_MIME_TYPE, METHOD_GET_VOICE_URL_BY_X,
			METHOD_GET_TEXT_AND_IMAGE_BY_X_Y, METHOD_GET_SIZE, METHOD_GET_TRANSLATE_XS, METHOD_FLAT_MAP,
			METHOD_CREATE_IMAGE_DIMENSION_POSITION_PREDICATE, METHOD_CREATE_FUNCTION, METHOD_CREATE_LIST_CELL_RENDERER1,
			METHOD_CREATE_LIST_CELL_RENDERER2, METHOD_GET_ACCENT_IMAGE_WIDTH, METHOD_GET_CURVE_IMAGE_WIDTH,
			METHOD_FOR_EACH_ORDERED, METHOD_SET, METHOD_CREATE_DEFAULT_TABLE_MODEL,
			METHOD_FIND_ENTRY_WITH_LONGEST_VALUE, METHOD_CREATE_FONT, METHOD_CREATE_TO_INT_FUNCTION, METHOD_ADD_2,
			METHOD_ADD_3 = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		CLASS_IMAGE_DIMENSION_POSITION = Util
				.forName("org.springframework.context.support.VoiceManagerOjadAccentPanel$ImageDimensionPosition");
		//
		final Class<?> clz = VoiceManagerOjadAccentPanel.class;
		//
		(METHOD_GET_FILE_EXTENSIONS = clz.getDeclaredMethod("getFileExtensions", ContentInfo.class))
				.setAccessible(true);
		//
		(METHOD_FIND_MATCH = clz.getDeclaredMethod("findMatch", ContentInfoUtil.class, byte[].class))
				.setAccessible(true);
		//
		(METHOD_PACK = clz.getDeclaredMethod("pack", Window.class)).setAccessible(true);
		//
		(METHOD_GET_KANJI = clz.getDeclaredMethod("getKanji",
				CLASS_TEXT_AND_IMAGE = Util
						.forName("org.springframework.context.support.VoiceManagerOjadAccentPanel$TextAndImage")))
				.setAccessible(true);
		//
		(METHOD_GET_HEIGHT = clz.getDeclaredMethod("getHeight", Dimension2D.class)).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_LENGTH_OBJECT_ARRAY = clz.getDeclaredMethod("length", Object[].class)).setAccessible(true);
		//
		(METHOD_LENGTH_DOUBLE_ARRAY = clz.getDeclaredMethod("length", double[].class)).setAccessible(true);
		//
		(METHOD_TO_TEXT_AND_IMAGES = clz.getDeclaredMethod("toTextAndImages", Iterable.class, Iterable.class,
				Page.class)).setAccessible(true);
		//
		(METHOD_TO_TEXT_AND_IMAGES1 = clz.getDeclaredMethod("toTextAndImages1", Iterable.class, String.class,
				Iterable.class, Page.class)).setAccessible(true);
		//
		(METHOD_TO_TEXT_AND_IMAGES2 = clz.getDeclaredMethod("toTextAndImages2", Iterable.class, String.class,
				Iterable.class, Iterable.class, Page.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_BYTE_ARRAY = clz.getDeclaredMethod("toByteArray", RenderedImage.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_IF_NULL = clz.getDeclaredMethod("getIfNull", Object.class, Iterable.class)).setAccessible(true);
		//
		(METHOD_ATTRIBUTE = clz.getDeclaredMethod("attribute", Element.class, String.class)).setAccessible(true);
		//
		(METHOD_CREATE_TEXT_AND_IMAGE_LIST_CELL_RENDERER = clz.getDeclaredMethod("createTextAndImageListCellRenderer",
				Component.class)).setAccessible(true);
		//
		(METHOD_SAVE_IMAGE = clz.getDeclaredMethod("saveImage", RenderedImage.class, Supplier.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_RUN_THROWS = clz.getDeclaredMethod("testAndRunThrows", Boolean.TYPE, ThrowingRunnable.class))
				.setAccessible(true);
		//
		(METHOD_GET_PART_OF_SPEECH = clz.getDeclaredMethod("getPartOfSpeech", Element.class, String.class))
				.setAccessible(true);
		//
		(METHOD_PREVIOUS_ELEMENT_SIBLINGS = clz.getDeclaredMethod("previousElementSiblings", Element.class))
				.setAccessible(true);
		//
		(METHOD_GET_PROPERTY = clz.getDeclaredMethod("getProperty", JSHandle.class, String.class)).setAccessible(true);
		//
		(METHOD_GET_VOICE_URL_IMAGES = clz.getDeclaredMethod("getVoiceUrlImages", Iterable.class, Page.class,
				String.class)).setAccessible(true);
		//
		(METHOD_MATCHES = clz.getDeclaredMethod("matches", String.class, String.class)).setAccessible(true);
		//
		(METHOD_CREATE_TEXT_AND_IMAGE_CONSUMER = clz.getDeclaredMethod("createTextAndImageConsumer"))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT_INT_PREDICATE = clz.getDeclaredMethod("testAndAccept", IntPredicate.class, Integer.TYPE,
				IntConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT_PREDICATE3 = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT_PREDICATE5 = clz.getDeclaredMethod("testAndAccept", TriPredicate.class, Object.class,
				Object.class, Object.class, TriConsumer.class)).setAccessible(true);
		//
		(METHOD_GET_MOST_OCCURENCE_COLOR = clz.getDeclaredMethod("getMostOccurenceColor", BufferedImage.class))
				.setAccessible(true);
		//
		(METHOD_SET_RGB = clz.getDeclaredMethod("setRGB", BufferedImage.class, Integer.class, Integer.class))
				.setAccessible(true);
		//
		(METHOD_SET_PART_OF_SPEECH = clz.getDeclaredMethod("setPartOfSpeech", CLASS_TEXT_AND_IMAGE, String.class))
				.setAccessible(true);
		//
		(METHOD_ADJUST_IMAGE_COLOR = clz.getDeclaredMethod("adjustImageColor", Iterable.class)).setAccessible(true);
		//
		(METHOD_CLOSE = clz.getDeclaredMethod("close", Player.class)).setAccessible(true);
		//
		(METHOD_GET_TEXT_AND_IMAGES = clz.getDeclaredMethod("getTextAndImages", clz, CLASS_TEXT_AND_IMAGE))
				.setAccessible(true);
		//
		(METHOD_COMMON_PREFIX = clz.getDeclaredMethod("commonPrefix", Iterable.class)).setAccessible(true);
		//
		(METHOD_GET_CONJUGATION = clz.getDeclaredMethod("getConjugation", Iterable.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_PROCESS_PAGE = clz.getDeclaredMethod("processPage", PDFGraphicsStreamEngine.class, PDPage.class))
				.setAccessible(true);
		//
		(METHOD_SET_HANDLER = clz.getDeclaredMethod("setHandler", Proxy.class, MethodHandler.class))
				.setAccessible(true);
		//
		(METHOD_ADD_ANNOTATIONS = clz.getDeclaredMethod("addAnnotations", PDDocument.class, PDPage.class,
				Collection.class, Collection.class, Boolean.TYPE)).setAccessible(true);
		//
		(METHOD_MAP_TO_DOUBLE = clz.getDeclaredMethod("mapToDouble", Stream.class, ToDoubleFunction.class))
				.setAccessible(true);
		//
		(METHOD_GET = clz.getDeclaredMethod("get", int[].class, Integer.TYPE, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_CREATE_PD_EMBEDDED_FILE = clz.getDeclaredMethod("createPDEmbeddedFile", PDDocument.class,
				InputStream.class, ContentInfoUtil.class, byte[].class)).setAccessible(true);
		//
		(METHOD_GET_MIME_TYPE = clz.getDeclaredMethod("getMimeType", ContentInfo.class)).setAccessible(true);
		//
		(METHOD_GET_VOICE_URL_BY_X = clz.getDeclaredMethod("getVoiceUrlByX", Pattern.class, Iterable.class,
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_TEXT_AND_IMAGE_BY_X_Y = clz.getDeclaredMethod("getTextAndImageByXY", Pattern.class, Iterable.class,
				Integer.TYPE, String.class)).setAccessible(true);
		//
		(METHOD_GET_SIZE = clz.getDeclaredMethod("getSize", Collection.class, Predicate.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_TRANSLATE_XS = clz.getDeclaredMethod("getTranslateXs", Collection.class, Double.TYPE))
				.setAccessible(true);
		//
		(METHOD_FLAT_MAP = clz.getDeclaredMethod("flatMap", Stream.class, Function.class)).setAccessible(true);
		//
		(METHOD_CREATE_IMAGE_DIMENSION_POSITION_PREDICATE = clz
				.getDeclaredMethod("createImageDimensionPositionPredicate", double[].class)).setAccessible(true);
		//
		(METHOD_CREATE_FUNCTION = clz.getDeclaredMethod("createFunction", Pattern.class)).setAccessible(true);
		//
		(METHOD_CREATE_LIST_CELL_RENDERER1 = clz.getDeclaredMethod("createListCellRenderer", ListCellRenderer.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_LIST_CELL_RENDERER2 = clz.getDeclaredMethod("createListCellRenderer", ListCellRenderer.class,
				Dimension.class)).setAccessible(true);
		//
		(METHOD_GET_ACCENT_IMAGE_WIDTH = clz.getDeclaredMethod("getAccentImageWidth", CLASS_TEXT_AND_IMAGE))
				.setAccessible(true);
		//
		(METHOD_GET_CURVE_IMAGE_WIDTH = clz.getDeclaredMethod("getCurveImageWidth", CLASS_TEXT_AND_IMAGE))
				.setAccessible(true);
		//
		(METHOD_FOR_EACH_ORDERED = clz.getDeclaredMethod("forEachOrdered", IntStream.class, IntConsumer.class))
				.setAccessible(true);
		//
		(METHOD_SET = clz.getDeclaredMethod("set", Iterable.class, Integer.TYPE, CLASS_TEXT_AND_IMAGE, Integer.TYPE,
				List.class, List.class, List.class)).setAccessible(true);
		//
		(METHOD_CREATE_DEFAULT_TABLE_MODEL = clz.getDeclaredMethod("createDefaultTableModel", Object[].class,
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_FIND_ENTRY_WITH_LONGEST_VALUE = clz.getDeclaredMethod("findEntryWithLongestValue", ListModel.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_FONT = clz.getDeclaredMethod("createFont", Font.class, ToIntFunction.class)).setAccessible(true);
		//
		(METHOD_CREATE_TO_INT_FUNCTION = clz.getDeclaredMethod("createToIntFunction", Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_ADD_2 = clz.getDeclaredMethod("add", Container.class, Component.class)).setAccessible(true);
		//
		(METHOD_ADD_3 = clz.getDeclaredMethod("add", Container.class, Component.class, Object.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Map<Object, Object> evaluate = null;

		private Integer width, height, size = null;

		private Object getElementAt = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final Class<?> returnType = Util.getReturnType(method);
			//
			if (Objects.equals(returnType, Void.TYPE)) {
				//
				return null;
				//
			} else if (method != null && method.getParameterCount() == 0
					&& Objects.equals(returnType, method.getDeclaringClass())) {
				//
				return proxy;
				//
			} // if
				//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof ElementHandle) {
				//
				if (Util.contains(Arrays.asList("screenshot", "textContent", "querySelector", "getAttribute"),
						methodName)) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Playwright) {
				//
				if (Objects.equals(methodName, "chromium")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Page) {
				//
				if (Util.contains(Arrays.asList("querySelectorAll", "pdf"), methodName)) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "evaluate") && args != null && args.length > 0) {
					//
					final Object arg = args[0];
					//
					if (!Util.containsKey(evaluate = ObjectUtils.getIfNull(evaluate, LinkedHashMap::new), arg)) {
						//
						throw new IllegalStateException(Util.toString(arg));
						//
					} // if
						//
					return Util.get(evaluate, arg);
					//
				} // if
					//
			} else if (proxy instanceof Iterable && Objects.equals(methodName, "iterator")) {
				//
				return null;
				//
			} else if (proxy instanceof JSHandle
					&& Util.contains(Arrays.asList("getProperty", "jsonValue"), methodName)) {
				//
				return null;
				//

			} else if (proxy instanceof PDImage) {
				//
				if (Objects.equals(methodName, "getWidth")) {
					//
					return width;
					//
				} else if (Objects.equals(methodName, "getHeight")) {
					//
					return height;
					//
				} // if
					//
			} else if (proxy instanceof DoubleStream) {
				//
				if (Objects.equals(methodName, "toArray")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof IntStream) {
				//
				if (Objects.equals(methodName, "toArray")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Stream && Util.contains(Arrays.asList("mapToDouble", "flatMap"), methodName)) {
				//
				return null;
				//
			} else if (proxy instanceof List && Objects.equals(methodName, "set")) {
				//
				return null;
				//
			} else if (proxy instanceof Type && Objects.equals(methodName, "getTypeName")) {
				//
				return null;
				//
			} else if (proxy instanceof ListModel) {
				//
				if (Objects.equals(methodName, "getSize")) {
					//
					return size;
					//
				} else if (Objects.equals(methodName, "getElementAt")) {
					//
					return getElementAt;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static class MH implements MethodHandler {

		private Double height = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof Dimension2D && Objects.equals(methodName, "getHeight")) {
				//
				return height;
				//
			} else if (self instanceof Toolkit && Objects.equals(methodName, "getSystemClipboard")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private IH ih = null;

	private ElementHandle elementHandle = null;

	private Page page = null;

	private Stream<?> stream = null;

	private VoiceManagerOjadAccentPanel instance = null;

	private Object textAndImage, imageDimensionPosition = null;

	private MH mh = null;

	private ObjectMapper objectMapper = null;

	private PDFGraphicsStreamEngine pdfGraphicsStreamEngine = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		elementHandle = Reflection.newProxy(ElementHandle.class, ih = new IH());
		//
		page = Reflection.newProxy(Page.class, ih);
		//
		stream = Reflection.newProxy(Stream.class, ih);
		//
		instance = new VoiceManagerOjadAccentPanel();
		//
		textAndImage = Narcissus.allocateInstance(CLASS_TEXT_AND_IMAGE);
		//
		imageDimensionPosition = Narcissus.allocateInstance(CLASS_IMAGE_DIMENSION_POSITION);
		//
		mh = new MH();
		//
		objectMapper = new ObjectMapper();
		//
		pdfGraphicsStreamEngine = ProxyUtil.createProxy(PDFGraphicsStreamEngine.class,
				Util.cast(MethodHandler.class,
						Narcissus.allocateInstance(
								Util.forName("org.springframework.context.support.VoiceManagerOjadAccentPanel$MH"))),
				c -> {
					//
					final Constructor<?> constructor = c != null ? c.getDeclaredConstructor(PDPage.class) : null;
					//
					if (constructor != null) {
						//
						constructor.setAccessible(true);
						//
					} // if
						//
					return constructor != null ? constructor.newInstance((Object) null) : null;
					//
				});
		//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Method[] ms = VoiceManagerOjadAccentPanel.class.getDeclaredMethods();
		//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Method m = null;
		//
		Object[] os = null;
		//
		Object invoke = null;
		//
		String toString, name = null;
		//
		JavaClass javaClass = null;
		//
		org.apache.bcel.classfile.Method bcelMethod = null;
		//
		Instruction[] ins = null;
		//
		int length = 0;
		//
		boolean invokeDynamic, invokeSpecial;
		//
		Instruction in = null;
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
			parameterTypes = Util.getParameterTypes(m);
			//
			for (int j = 0; j < length(parameterTypes); j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Float.TYPE)) {
					//
					Util.add(collection, Float.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Double.TYPE)) {
					//
					Util.add(collection, Double.valueOf(0));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // for
				//
			os = Util.toArray(collection);
			//
			invoke = Util.isStatic(m) ? Narcissus.invokeStaticMethod(m, os) : Narcissus.invokeMethod(instance, m, os);
			//
			toString = Util.toString(m);
			//
			if (javaClass == null) {
				//
				final Class<?> clz = VoiceManagerOjadAccentPanel.class;
				//
				try (final InputStream is = Util.getResourceAsStream(clz, StringUtils.join("/",
						StringsUtil.replace(Strings.CS, Util.getName(clz), ".", "/"), ".class"))) {
					//
					javaClass = ClassParserUtil.parse(new ClassParser(is, null));
					//
				} // try
					//
			} // if
				//
			invokeDynamic = invokeSpecial = false;
			//
			if ((bcelMethod = JavaClassUtil.getMethod(javaClass, m)) != null) {
				//
				final ConstantPoolGen cpg = new ConstantPoolGen(javaClass.getConstantPool());
				//
				if ((length = length(ins = InstructionListUtil.getInstructions(MethodGenUtil.getInstructionList(
						testAndApply(Objects::nonNull, bcelMethod, x -> new MethodGen(x, null, cpg), null))))) == 2) {
					//
					invokeDynamic = Boolean.logicalAnd(ArrayUtils.get(ins, 0) instanceof INVOKEDYNAMIC,
							ArrayUtils.get(ins, 1) instanceof ARETURN);
					//
				} else if (length == 3) {
					//
					invokeDynamic = Util.and(
							Boolean.logicalOr((in = ArrayUtils.get(ins, 0)) instanceof ALOAD, in instanceof ILOAD),
							ArrayUtils.get(ins, 1) instanceof INVOKEDYNAMIC, ArrayUtils.get(ins, 2) instanceof ARETURN);
					//
				} else if (length == 4) {
					//
					invokeDynamic = Util.and(ArrayUtils.get(ins, 0) instanceof ALOAD,
							ArrayUtils.get(ins, 1) instanceof ALOAD, ArrayUtils.get(ins, 2) instanceof INVOKEDYNAMIC,
							ArrayUtils.get(ins, 3) instanceof ARETURN);
					//
				} else if (length == 6) {
					//
					invokeSpecial = Util.and(ArrayUtils.get(ins, 0) instanceof NEW,
							ArrayUtils.get(ins, 1) instanceof DUP, ArrayUtils.get(ins, 2) instanceof ALOAD,
							ArrayUtils.get(ins, 3) instanceof ILOAD, ArrayUtils.get(ins, 4) instanceof INVOKESPECIAL,
							ArrayUtils.get(ins, 5) instanceof ARETURN);
					//
				} // if
					//
			} // if
				//
			if (or(Util.contains(
					Arrays.asList(Double.TYPE, Boolean.TYPE, Integer.TYPE, Float.TYPE), Util.getReturnType(m)),
					invokeDynamic, invokeSpecial,
					Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "getClipboard"),
							Arrays.equals(parameterTypes, new Class<?>[] {})),
					Boolean.logicalAnd(Objects.equals(name, "createUrl"),
							Arrays.equals(parameterTypes, new Class<?>[] { String.class, Map.class })),
					Boolean.logicalAnd(Objects.equals(Util.getName(m), "getMapEntryGetKeyMethod"),
							Arrays.equals(parameterTypes, new Class<?>[] {})),
					Boolean.logicalAnd(Objects.equals(Util.getName(m), "getMapEntrySetValueMethod"),
							Arrays.equals(parameterTypes, new Class<?>[] {})))) {
				//
				Assertions.assertNotNull(invoke, toString);
				//
			} else {
				//
				Assertions.assertNull(invoke, toString);
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
	void testMethodWithOneInterfaceParameter() {
		//
		final Method[] ms = VoiceManagerOjadAccentPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Object invokeStaticMethod = null;
		//
		String toString = null;
		//
		if (ih != null) {
			//
			ih.size = Integer.valueOf(0);
			//
		} // if
			//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || (parameterTypes = Util.getParameterTypes(m)) == null
					|| parameterTypes.length != 1 || (parameterType = ArrayUtils.get(parameterTypes, 0)) == null
					|| !Modifier.isInterface(parameterType.getModifiers())
					|| StringsUtil.startsWith(Strings.CS, Util.getName(m), "lambda$")) {
				//
				continue;
				//
			} // if
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m,
					new Object[] { Reflection.newProxy(parameterType, ih) });
			//
			toString = Util.toString(m);
			//
			if (or(Objects.equals(Util.getReturnType(m), Boolean.TYPE),
					Objects.equals(Util.getReturnType(m), parameterType),
					Boolean.logicalAnd(Objects.equals(Util.getName(m), "createComparatorByOrder"),
							Arrays.equals(parameterTypes, new Class<?>[] { List.class })))) {
				//
				Assertions.assertNotNull(invokeStaticMethod, toString);
				//
			} else {
				//
				Assertions.assertNull(invokeStaticMethod, toString);
				//
			} // if
				//
		} // for
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
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(null));
		//
		FieldUtils.writeDeclaredField(instance, "tfTextInput", new JTextField(" "), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(null));
		//
		final ActionEvent actionEvent = new ActionEvent(EMPTY, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		instance.afterPropertiesSet();
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		// jcbTextAndImage
		//
		final JComboBox<?> jcbTextAndImage = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbTextAndImage", jcbTextAndImage, true);
		//
		final ActionEvent actionEventJcbTextAndImage = new ActionEvent(jcbTextAndImage, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventJcbTextAndImage));
		//
		Util.cast(MutableComboBoxModel.class, jcbTextAndImage.getModel()).addElement(textAndImage);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventJcbTextAndImage));
		//
		// jcbLanguage
		//
		final JComboBox<?> jcbLanguage = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbLanguage", jcbLanguage, true);
		//
		final ActionEvent actionEventJcbLanguage = new ActionEvent(jcbLanguage, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventJcbLanguage));
		//
		FieldUtils.writeDeclaredField(instance, "btnExecute", null, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventJcbLanguage));
		//
		// btnCopyAccentImage
		//
		final AbstractButton btnCopyAccentImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyAccentImage", btnCopyAccentImage, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyAccentImage, 0, null)));
		//
		// btnCopyCurveImage
		//
		final AbstractButton btnCopyCurveImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyCurveImage", btnCopyCurveImage, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyCurveImage, 0, null)));
		//
		// btnCopyPartOfSpeech
		//
		final AbstractButton btnCopyPartOfSpeech = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyPartOfSpeech", btnCopyPartOfSpeech, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyPartOfSpeech, 0, null)));
		//
		// btnCopyKanji
		//
		final AbstractButton btnCopyKanji = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyKanji", btnCopyKanji, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyKanji, 0, null)));
		//
		// btnCopyHiragana
		//
		final AbstractButton btnCopyHiragana = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyHiragana", btnCopyHiragana, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopyHiragana, 0, null)));
		//
		// btnSaveAccentImage
		//
		final AbstractButton btnSaveAccentImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSaveAccentImage", btnSaveAccentImage, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnSaveAccentImage, 0, null)));
		//
		// btnSaveCurveImage
		//
		final AbstractButton btnSaveCurveImage = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSaveCurveImage", btnSaveCurveImage, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnSaveCurveImage, 0, null)));
		//
		// Copy
		//
		final Class<?> clz = VoiceManagerOjadAccentPanel.class;
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(EMPTY, 0,
				StringUtils.join(FieldUtils.readDeclaredStaticField(clz, "COPY", true), ','))));
		//
		// Download
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(EMPTY, 0,
				StringUtils.join(FieldUtils.readDeclaredStaticField(clz, "DOWNLOAD", true), ','))));
		//
		// Play
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(EMPTY, 0,
				StringUtils.join(FieldUtils.readDeclaredStaticField(clz, "PLAY", true), ','))));
		//
		// btnPdf
		//
		final AbstractButton btnPdf = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnPdf", btnPdf, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnPdf, 0, null)));
		//
	}

	@Test
	void testGetFileExtensions() throws Throwable {
		//
		Assertions.assertNull(invoke(METHOD_GET_FILE_EXTENSIONS, null,
				Util.cast(ContentInfo.class, Narcissus.allocateInstance(ContentInfo.class))));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null && method.getDeclaringClass() != null ? method.invoke(instance, args) : null;
	}

	@Test
	void testFindMatch() throws Throwable {
		//
		final ContentInfoUtil cic = new ContentInfoUtil();
		//
		Assertions.assertNull(invoke(METHOD_FIND_MATCH, null, cic, null));
		//
		Assertions.assertSame(ContentInfo.EMPTY_INFO, invoke(METHOD_FIND_MATCH, null, cic, new byte[] {}));
		//
	}

	@Test
	void testPack() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_PACK, null, Narcissus.allocateInstance(Window.class)));
		//
	}

	@Test
	void testGetKanji() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_KANJI, null, textAndImage));
		//
	}

	@Test
	void testGetHeight() throws Throwable {
		//
		final double height = 0;
		//
		mh.height = Double.valueOf(height);
		//
		Assertions.assertEquals(Double.valueOf(height),
				invoke(METHOD_GET_HEIGHT, null, ProxyUtil.createProxy(Dimension2D.class, mh)));
		//
	}

	@Test
	void testIH1() throws Throwable {
		//
		final InvocationHandler invocationHandler = Util.cast(InvocationHandler.class, Narcissus
				.allocateInstance(Util.forName("org.springframework.context.support.VoiceManagerOjadAccentPanel$IH")));
		//
		if (invocationHandler == null) {
			//
			return;
			//
		} // if
			//
			// java.lang.Object.equals(java.lang.Object)
			//
		Assertions.assertEquals(Boolean.FALSE,
				invocationHandler.invoke(null, Narcissus.findMethod(Object.class, "equals", Object.class), null));
		//
		final Transferable transferable = Reflection.newProxy(Transferable.class, invocationHandler);
		//
		Assertions.assertThrows(Throwable.class, () -> invocationHandler.invoke(transferable, null, null));
		//
		// java.awt.datatransfer.Transferable.getTransferDataFlavors()
		//
		Assertions.assertEquals(
				ObjectMapperUtil.writeValueAsString(objectMapper, new DataFlavor[] { DataFlavor.imageFlavor }),
				ObjectMapperUtil.writeValueAsString(objectMapper, invocationHandler.invoke(transferable,
						Narcissus.findMethod(Transferable.class, "getTransferDataFlavors"), null)));
		//
		// java.awt.datatransfer.Transferable.getTransferData(java.awt.datatransfer.DataFlavor)
		//
		Assertions.assertNull(invocationHandler.invoke(transferable,
				Narcissus.findMethod(Transferable.class, "getTransferData", DataFlavor.class), null));
		//
		final CellEditor cellEditor = Reflection.newProxy(CellEditor.class, ih);
		//
		Assertions.assertThrows(Throwable.class, () -> invocationHandler.invoke(cellEditor, null, null));
		//
		// javax.swing.CellEditor.isCellEditable(java.util.EventObject)
		//
		Assertions.assertEquals(Boolean.TRUE, invocationHandler.invoke(cellEditor,
				Narcissus.findMethod(CellEditor.class, "isCellEditable", EventObject.class), null));
		//
		// javax.swing.CellEditor.stopCellEditing()
		//
		Assertions.assertEquals(Boolean.TRUE, invocationHandler.invoke(cellEditor,
				Narcissus.findMethod(CellEditor.class, "stopCellEditing"), new Object[] {}));
		//
		// javax.swing.CellEditor.shouldSelectCell(java.util.EventObject)
		//
		Assertions.assertEquals(Boolean.TRUE, invocationHandler.invoke(cellEditor,
				Narcissus.findMethod(CellEditor.class, "shouldSelectCell", EventObject.class), null));
		//
		// javax.swing.CellEditor.shouldSelectCell(javax.swing.event.CellEditorListener)
		//
		Assertions.assertEquals(Boolean.TRUE, invocationHandler.invoke(cellEditor,
				Narcissus.findMethod(CellEditor.class, "addCellEditorListener", CellEditorListener.class), null));
		//
		final TableCellRenderer tableCellRenderer = Reflection.newProxy(TableCellRenderer.class, ih);
		//
		Assertions.assertThrows(Throwable.class, () -> invocationHandler.invoke(tableCellRenderer, null, null));
		//
		// javax.swing.table.TableCellRenderer.getTableCellRendererComponent(javax.swing.JTable,java.lang.Object,boolean,boolean,int,int)
		//
		final Method getTableCellRendererComponent = Narcissus.findMethod(TableCellRenderer.class,
				"getTableCellRendererComponent", JTable.class, Object.class, Boolean.TYPE, Boolean.TYPE, Integer.TYPE,
				Integer.TYPE);
		//
		Assertions.assertThrows(Throwable.class,
				() -> invocationHandler.invoke(tableCellRenderer, getTableCellRendererComponent, null));
		//
		Assertions.assertThrows(Throwable.class,
				() -> invocationHandler.invoke(tableCellRenderer, getTableCellRendererComponent, new Object[] {}));
		//
		Object[] os = Util.toArray(Collections.nCopies(6, null));
		//
		Assertions.assertNull(invocationHandler.invoke(tableCellRenderer, getTableCellRendererComponent, os));
		//
		if (length(os) > 1) {
			//
			os[1] = new byte[] {};
			//
		} // if
			//
		Assertions.assertNotNull(invocationHandler.invoke(tableCellRenderer, getTableCellRendererComponent, os));
		//
		if (length(os) > 1) {
			//
			os[0] = new JTable();
			//
			os[1] = null;
			//
		} // if
			//
		Assertions.assertNull(invocationHandler.invoke(tableCellRenderer, getTableCellRendererComponent, os));
		//
		final Object copy = FieldUtils.readDeclaredStaticField(VoiceManagerOjadAccentPanel.class, "COPY", true);
		//
		if (length(os) > 5) {
			//
			os[0] = new JTable(new DefaultTableModel(new Object[] { null }, 0));
			//
			os[5] = Integer.valueOf(0);
			//
			Assertions.assertNull(invocationHandler.invoke(tableCellRenderer, getTableCellRendererComponent, os));
			//
			os[0] = new JTable(new DefaultTableModel(new Object[] { copy }, 0));
			//
			Assertions.assertNotNull(invocationHandler.invoke(tableCellRenderer, getTableCellRendererComponent, os));
			//
		} // if
			//
		final TableCellEditor tableCellEditor = Reflection.newProxy(TableCellEditor.class, ih);
		//
		Assertions.assertThrows(Throwable.class, () -> invocationHandler.invoke(tableCellEditor, null, null));
		//
		final Method getTableCellEditorComponent = Narcissus.findMethod(TableCellEditor.class,
				"getTableCellEditorComponent", JTable.class, Object.class, Boolean.TYPE, Integer.TYPE, Integer.TYPE);
		//
		Assertions.assertThrows(Throwable.class,
				() -> invocationHandler.invoke(tableCellEditor, getTableCellEditorComponent, null));
		//
		Assertions.assertThrows(Throwable.class,
				() -> invocationHandler.invoke(tableCellEditor, getTableCellEditorComponent, new Object[] {}));
		//
		Assertions.assertNull(invocationHandler.invoke(tableCellEditor, getTableCellEditorComponent,
				os = Util.toArray(Collections.nCopies(5, null))));
		//
		if (length(os) > 1) {
			//
			os[0] = new JTable();
			//
		} // if
			//
		Assertions.assertNull(invocationHandler.invoke(tableCellEditor, getTableCellEditorComponent, os));
		//
		if (length(os) > 4) {
			//
			os[4] = Integer.valueOf(0);
			//
			os[0] = new JTable(new DefaultTableModel(new Object[] { null }, 0));
			//
			Assertions.assertNull(invocationHandler.invoke(tableCellEditor, getTableCellEditorComponent, os));
			//
			final DefaultTableModel dtm = new DefaultTableModel(new Object[] { copy }, 0);
			//
			os[0] = new JTable(dtm);
			//
			Assertions.assertNotNull(invocationHandler.invoke(tableCellEditor, getTableCellEditorComponent, os));
			//
			os[3] = Integer.valueOf(0);
			//
			dtm.addRow(new Object[] { null });
			//
			Assertions.assertNotNull(invocationHandler.invoke(tableCellEditor, getTableCellEditorComponent, os));
			//
		} // if
			//
	}

	@Test
	void testIH2() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.VoiceManagerOjadAccentPanel$IH");
		//
		final InvocationHandler invocationHandler = Util.cast(InvocationHandler.class, Narcissus.allocateInstance(clz));
		//
		if (invocationHandler == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertThrows(Throwable.class, () -> invocationHandler.invoke(null, null, null));
		//
		// org.springframework.context.support.VoiceManagerOjadAccentPanel$IH.getValueAt(javax.swing.JTable,int,int)
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(
				Util.getDeclaredMethod(clz, "getValueAt", JTable.class, Integer.TYPE, Integer.TYPE), null, 0, 0));
		//
	}

	@Test
	void testMH() throws Throwable {
		//
		final Class<?> mhClass = Util.forName("org.springframework.context.support.VoiceManagerOjadAccentPanel$MH");
		//
		final MethodHandler methodHandler = Util.cast(MethodHandler.class, Narcissus.allocateInstance(mhClass));
		//
		if (methodHandler == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertThrows(Throwable.class, () -> methodHandler.invoke(null, null, null, null));
		//
		Assertions.assertThrows(Throwable.class, () -> methodHandler.invoke(pdfGraphicsStreamEngine, null, null, null));
		//
		Util.forEach(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredMethods(PDFGraphicsStreamEngine.class), Arrays::stream,
						null),
				m -> m != null && !m.isSynthetic() && m.getParameterCount() == 0
						&& Modifier.isAbstract(m.getModifiers())),
				m -> {
					//
					Assertions.assertDoesNotThrow(() -> methodHandler.invoke(pdfGraphicsStreamEngine, m, null, null));
					//
				});
		//
		// org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine.drawImage(org.apache.pdfbox.pdmodel.graphics.image.PDImage)
		//
		final Method drawImage = Narcissus.findMethod(PDFGraphicsStreamEngine.class, "drawImage", PDImage.class);
		//
		Assertions.assertDoesNotThrow(() -> methodHandler.invoke(pdfGraphicsStreamEngine, drawImage, null, null));
		//
		Assertions.assertDoesNotThrow(
				() -> methodHandler.invoke(pdfGraphicsStreamEngine, drawImage, null, new Object[] {}));
		//
		Assertions.assertDoesNotThrow(
				() -> methodHandler.invoke(pdfGraphicsStreamEngine, drawImage, null, new Object[] { null }));
		//
		if (ih != null) {
			//
			ih.width = ih.height = Integer.valueOf(0);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> methodHandler.invoke(pdfGraphicsStreamEngine, drawImage, null,
				new Object[] { Reflection.newProxy(PDImage.class, ih) }));
		//
		// org.springframework.context.support.VoiceManagerOjadAccentPanel$MH.getCurrentTransformationMatrix(org.apache.pdfbox.pdmodel.graphics.state.PDGraphicsState)
		//
		final Method getCurrentTransformationMatrix = Util.getDeclaredMethod(mhClass, "getCurrentTransformationMatrix",
				PDGraphicsState.class);
		//
		if (getCurrentTransformationMatrix != null) {
			//
			getCurrentTransformationMatrix.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(getCurrentTransformationMatrix != null
				? getCurrentTransformationMatrix.invoke(null, Narcissus.allocateInstance(PDGraphicsState.class))
				: null);
		//
		// org.springframework.context.support.VoiceManagerOjadAccentPanel$MH.getGraphicsState(org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine)
		//
		final Method getGraphicsState = Util.getDeclaredMethod(mhClass, "getGraphicsState",
				PDFGraphicsStreamEngine.class);
		//
		if (getGraphicsState != null) {
			//
			getGraphicsState.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(getGraphicsState != null ? getGraphicsState.invoke(null, (Object) null) : null);
		//
	}

	@Test
	void testGetSystemClipboard() throws IllegalAccessException, InvocationTargetException, Throwable {
		//
		Assertions.assertNull(invoke(METHOD_GET_SYSTEM_CLIP_BOARD, null, ProxyUtil.createProxy(Toolkit.class, mh)));
		//
	}

	@Test
	void testLength() throws Throwable {
		//
		Assertions.assertEquals(0, length(new Object[] {}));
		//
		Assertions.assertEquals(0, invoke(METHOD_LENGTH_DOUBLE_ARRAY, null, new double[] {}));
		//
	}

	private static int length(final Object[] instance) throws Throwable {
		try {
			final Object obj = invoke(METHOD_LENGTH_OBJECT_ARRAY, null, (Object) instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToTextAndImages() throws Throwable {
		//
		setDefaultPropertyInclusion(setVisibility(objectMapper, PropertyAccessor.ALL, Visibility.ANY),
				Include.NON_NULL);
		//
		Assertions.assertEquals("[{}]", ObjectMapperUtil.writeValueAsString(objectMapper,
				invoke(METHOD_TO_TEXT_AND_IMAGES, null, Collections.singleton(null), null, null)));
		//
		Assertions.assertEquals("[{},{}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_TO_TEXT_AND_IMAGES, null,
						Collections.nCopies(2, null), Collections.nCopies(2, null), null)));
		//
	}

	private static ObjectMapper setDefaultPropertyInclusion(final ObjectMapper instance,
			final JsonInclude.Include incl) {
		return instance != null ? instance.setDefaultPropertyInclusion(incl) : instance;
	}

	private static ObjectMapper setVisibility(final ObjectMapper instance, final PropertyAccessor forMethod,
			final JsonAutoDetect.Visibility visibility) {
		return instance != null ? instance.setVisibility(forMethod, visibility) : instance;
	}

	@Test
	void testToTextAndImages1() throws Throwable {
		//
		setDefaultPropertyInclusion(setVisibility(objectMapper, PropertyAccessor.ALL, Visibility.ANY),
				Include.NON_NULL);
		//
		Assertions.assertEquals("[{}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_TO_TEXT_AND_IMAGES1, null,
						Collections.nCopies(2, null), null, Collections.nCopies(1, null), null)));
		//
	}

	@Test
	void testToTextAndImages2() throws Throwable {
		//
		final Iterable<ElementHandle> words = Collections.nCopies(1, null);
		//
		Assertions.assertNull(invoke(METHOD_TO_TEXT_AND_IMAGES2, null, null, null, words, null, null));
		//
		Assertions.assertNull(invoke(METHOD_TO_TEXT_AND_IMAGES2, null, null, null, words, Set.of(), null));
		//
		final Class<?> clz = Util.getClass(instance);
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				String.format("/%1$s.class", StringsUtil.replace(Strings.CS, Util.getName(clz), ".", "/")))) {
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)),
					METHOD_TO_TEXT_AND_IMAGES2);
			//
			final ConstantPoolGen cpg = testAndApply(Objects::nonNull, FieldOrMethodUtil.getConstantPool(m),
					ConstantPoolGen::new, null);
			//
			final Instruction[] instructions = InstructionListUtil.getInstructions(MethodGenUtil
					.getInstructionList(testAndApply(Objects::nonNull, m, x -> new MethodGen(x, null, cpg), null)));
			//
			for (int i = 0; i < length(instructions); i++) {
				//
				if (ArrayUtils.get(instructions, i) instanceof INVOKESTATIC invokestatic
						&& Objects.equals(InvokeInstructionUtil.getClassName(invokestatic, cpg), "java.util.Arrays")
						&& Objects.equals(InvokeInstructionUtil.getMethodName(invokestatic, cpg), "asList")) {
					//
					for (int j = i - 1; j >= 0; j--) {
						//
						if (ArrayUtils.get(instructions, j) instanceof LDC ldc && ldc != null) {
							//
							Assertions.assertNull(invoke(METHOD_TO_TEXT_AND_IMAGES2, null, null, null, words,
									Collections.singleton(Util.toString(LDCUtil.getValue(ldc, cpg))), null));
							//
						} else if (ArrayUtils.get(instructions, j) instanceof ANEWARRAY) {
							//
							break;
							//
						} // if
							//
					} // for
						//
				} // if
					//
			} // for
				//
		} // try
			//
		Assertions.assertNull(
				invoke(METHOD_TO_TEXT_AND_IMAGES2, null, null, null, Collections.nCopies(2, null), null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) invoke(METHOD_TEST_AND_APPLY, null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Collection<?> toTextAndImages2X(final Iterable<ElementHandle> ehs, final String textInput,
			final Iterable<ElementHandle> words, final Iterable<String> partOfSpeeches, final Page page)
			throws Throwable {
		try {
			final Object obj = invoke(METHOD_TO_TEXT_AND_IMAGES2, null, ehs, textInput, words, partOfSpeeches, page);
			if (obj == null) {
				return null;
			} else if (obj instanceof Collection) {
				return (Collection) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToByteArray() throws Throwable {
		//
		final RenderedImage renderedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		//
		Assertions.assertNull(toByteArray(renderedImage, null));
		//
		Assertions.assertNotNull(toByteArray(renderedImage, "png"));
		//
	}

	private static byte[] toByteArray(final RenderedImage image, final String format) throws Throwable {
		try {
			final Object obj = METHOD_TO_BYTE_ARRAY.invoke(null, image, format);
			if (obj == null) {
				return null;
			} else if (obj instanceof byte[]) {
				return (byte[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetIfNull() throws Throwable {
		//
		Assertions.assertSame(EMPTY, getIfNull(EMPTY, null));
		//
		Assertions.assertSame(EMPTY, getIfNull(null, Collections.singleton(Suppliers.ofInstance(EMPTY))));
		//
	}

	private static <T> T getIfNull(final T object, final Iterable<Supplier<T>> suppliers) throws Throwable {
		try {
			return (T) METHOD_GET_IF_NULL.invoke(null, object, suppliers);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAttribute() throws Throwable {
		//
		Assertions.assertNull(attribute(Util.cast(Element.class, Narcissus.allocateInstance(Element.class)), null));
		//
	}

	private static Attribute attribute(final Element instance, final String key) throws Throwable {
		try {
			final Object obj = METHOD_ATTRIBUTE.invoke(null, instance, key);
			if (obj == null) {
				return null;
			} else if (obj instanceof Attribute) {
				return (Attribute) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateTextAndImageListCellRenderer() throws Throwable {
		//
		Assertions.assertNotNull(Util.getListCellRendererComponent(createTextAndImageListCellRenderer(null), null, null,
				0, false, false));
		//
	}

	private static ListCellRenderer<?> createTextAndImageListCellRenderer(final Component component) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_TEXT_AND_IMAGE_LIST_CELL_RENDERER.invoke(null, component);
			if (obj == null) {
				return null;
			} else if (obj instanceof ListCellRenderer) {
				return (ListCellRenderer) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSaveImage() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> saveImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), null, null));
		//
	}

	private static void saveImage(final RenderedImage image, final Supplier<File> supplier, final String format)
			throws Throwable {
		try {
			METHOD_SAVE_IMAGE.invoke(null, image, supplier, format);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTextAndImage() throws Throwable {
		//
		final Class<?> clz = Util.getClass(textAndImage);
		//
		final List<Field> fs = Util.toList(
				Util.filter(Util.stream(testAndApply(Objects::nonNull, clz, FieldUtils::getAllFieldsList, null)),
						f -> Objects.equals(Util.getName(f), "accentImage")));
		//
		testAndRunThrows(IterableUtils.size(fs) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Field accentImage = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		Narcissus.setField(textAndImage, accentImage, Narcissus.allocateInstance(BufferedImage.class));
		//
		final List<Method> ms = Util
				.toList(Util.filter(testAndApply(Objects::nonNull, Util.getDeclaredMethods(clz), Arrays::stream, null),
						m -> Boolean.logicalAnd(Objects.equals(Util.getName(m), "getAccentImageWidth"),
								Arrays.equals(Util.getParameterTypes(m), new Class<?>[] {}))));
		//
		testAndRunThrows(IterableUtils.size(ms) > 1, () -> {
			//
			throw new IllegalStateException();
			//
		});
		//
		final Method m = testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null);
		//
		Assertions.assertNull(Narcissus.invokeMethod(textAndImage, m));
		//
		final int width = 1;
		//
		Narcissus.setField(textAndImage, accentImage, new BufferedImage(width, 2, BufferedImage.TYPE_INT_RGB));
		//
		Assertions.assertEquals(Integer.valueOf(width), Narcissus.invokeMethod(textAndImage, m));
		//
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean condition,
			final ThrowingRunnable<E> throwingRunnable) throws Throwable {
		try {
			METHOD_TEST_AND_RUN_THROWS.invoke(null, condition, throwingRunnable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPartOfSpeech() throws Throwable {
		//
		Assertions
				.assertNull(getPartOfSpeech(Util.cast(Element.class, Narcissus.allocateInstance(Element.class)), " "));
		//
	}

	private static String getPartOfSpeech(final Element element, final String id) throws Throwable {
		try {
			final Object obj = METHOD_GET_PART_OF_SPEECH.invoke(null, element, id);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPreviousElementSiblings() throws Throwable {
		//
		Assertions.assertNotNull(
				previousElementSiblings(Util.cast(Element.class, Narcissus.allocateInstance(Element.class))));
		//
	}

	static Elements previousElementSiblings(final Element instance) throws Throwable {
		try {
			final Object obj = METHOD_PREVIOUS_ELEMENT_SIBLINGS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Elements) {
				return (Elements) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetProperty() throws Throwable {
		//
		Assertions.assertNull(getProperty(Reflection.newProxy(JSHandle.class, ih), null));
		//
	}

	private static JSHandle getProperty(final JSHandle instance, final String propertyName) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROPERTY.invoke(null, instance, propertyName);
			if (obj == null) {
				return null;
			} else if (obj instanceof JSHandle) {
				return (JSHandle) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetVoiceUrlImages() throws Throwable {
		//
		Assertions.assertNull(getVoiceUrlImages(Collections.singleton(null), page, null));
		//
		if (ih != null) {
			//
			Util.put(ih.evaluate = new LinkedHashMap<>(Map.of("typeof get_pronounce_url", "function")),
					"get_pronounce_url(\"null\",\"mp3\")", null);
			//
		} // if
			//
		final Iterable<ElementHandle> ehs = Collections.singleton(elementHandle);
		//
		final Map<?, ?> map = new LinkedHashMap<>();
		//
		Util.put(map, null, null);
		//
		Assertions.assertEquals(map, getVoiceUrlImages(ehs, page, null));
		//
		if (ih != null) {
			//
			Util.put(ih.evaluate, "typeof get_pronounce_url", null);
			//
		} // if
			//
		Assertions.assertNull(getVoiceUrlImages(ehs, page, null));
		//
	}

	private static Map<String, byte[]> getVoiceUrlImages(final Iterable<ElementHandle> ehs, final Page page,
			final String format) throws Throwable {
		try {
			final Object obj = METHOD_GET_VOICE_URL_IMAGES.invoke(null, ehs, page, format);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMatches() throws Throwable {
		//
		Assertions.assertFalse(matches(EMPTY, null));
		//
	}

	private static boolean matches(final String a, final String b) throws Throwable {
		try {
			final Object obj = METHOD_MATCHES.invoke(null, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateTextAndImageConsumer() throws Throwable {
		//
		final Consumer<?> consumer = createTextAndImageConsumer();
		//
		Util.accept(consumer, null);
		//
		final Method accept = Util.getDeclaredMethod(Consumer.class, "accept", Object.class);
		//
		final Map<String, Object> voiceUrlImages = new LinkedHashMap<>();
		//
		Util.put(voiceUrlImages, EMPTY, null);
		//
		setDefaultPropertyInclusion(setVisibility(objectMapper, PropertyAccessor.ALL, Visibility.ANY),
				Include.NON_NULL);
		//
		Assertions.assertEquals("{}", ObjectMapperUtil.writeValueAsString(objectMapper, textAndImage));
		//
		FieldUtils.writeDeclaredField(textAndImage, "voiceUrlImages", voiceUrlImages, true);
		//
		Assertions.assertEquals("{\"voiceUrlImages\":{}}",
				ObjectMapperUtil.writeValueAsString(objectMapper, textAndImage));
		//
		Assertions.assertNull(Narcissus.invokeMethod(consumer, accept, textAndImage));
		//
		Assertions.assertEquals("{\"voiceUrlImages\":{}}",
				ObjectMapperUtil.writeValueAsString(objectMapper, textAndImage));
		//
		Util.put(voiceUrlImages, "1?2", new byte[] {});
		//
		Assertions.assertEquals("{\"voiceUrlImages\":{\"1?2\":\"\"}}",
				ObjectMapperUtil.writeValueAsString(objectMapper, textAndImage));
		//
		Assertions.assertNull(Narcissus.invokeMethod(consumer, accept, textAndImage));
		//
		Assertions.assertEquals("{\"voiceUrlImages\":{\"1\":\"\"}}",
				ObjectMapperUtil.writeValueAsString(objectMapper, textAndImage));
		//
	}

	private static Consumer<?> createTextAndImageConsumer() throws Throwable {
		try {
			final Object obj = METHOD_CREATE_TEXT_AND_IMAGE_CONSUMER.invoke(null);
			if (obj == null) {
				return null;
			} else if (obj instanceof Consumer) {
				return (Consumer) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testKeyReleased() throws IllegalAccessException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Component component = new JLabel();
		//
		Assertions.assertDoesNotThrow(() -> instance.keyReleased(new KeyEvent(component, 0, 0, 0, KeyEvent.VK_A, ' ')));
		//
		Assertions.assertDoesNotThrow(
				() -> instance.keyReleased(new KeyEvent(component, 0, 0, 0, KeyEvent.VK_ENTER, ' ')));
		//
		// tfIndex
		//
		final JTextComponent tfIndex = new JTextField();
		//
		FieldUtils.writeDeclaredField(instance, "tfIndex", tfIndex, true);
		//
		final KeyEvent keyEventTfIndex = new KeyEvent(tfIndex, 0, 0, 0, KeyEvent.VK_ENTER, ' ');
		//
		Assertions.assertDoesNotThrow(() -> instance.keyReleased(keyEventTfIndex));
		//
		Util.setText(tfIndex, "1");
		//
		Assertions.assertDoesNotThrow(() -> instance.keyReleased(keyEventTfIndex));
		//
		final JComboBox<?> jcbTextAndImage = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbTextAndImage", jcbTextAndImage, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.keyReleased(keyEventTfIndex));
		//
		Util.setText(tfIndex, "-1");
		//
		Assertions.assertDoesNotThrow(() -> instance.keyReleased(keyEventTfIndex));
		//
		// btnExecute
		//
		final Component btnExecute = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnExecute", btnExecute, true);
		//
		final KeyEvent keyEventBtnExecute = new KeyEvent(btnExecute, 0, 0, 0, KeyEvent.VK_SPACE, ' ');
		//
		Assertions.assertDoesNotThrow(() -> instance.keyReleased(keyEventBtnExecute));
		//
		Assertions.assertDoesNotThrow(() -> instance.keyReleased(keyEventBtnExecute));
		//
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(x -> true, 0, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b, c) -> true, null, null, null, null));
		//
	}

	private static void testAndAccept(final IntPredicate predicate, final int value, final IntConsumer consumer)
			throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT_INT_PREDICATE.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> void testAndAccept(final Predicate<T> predicate, final T value, final Consumer<T> consumer)
			throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT_PREDICATE3.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, R> void testAndAccept(final TriPredicate<T, U, R> predicate, final T t, final U u, final R r,
			final TriConsumer<T, U, R> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT_PREDICATE5.invoke(null, predicate, t, u, r, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMostOccurenceColor() throws Throwable {
		//
		final BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		//
		Assertions.assertEquals(image.getRGB(0, 0), invoke(METHOD_GET_MOST_OCCURENCE_COLOR, null, image));
		//
	}

	@Test
	void testSetRGB() throws IllegalAccessException, InvocationTargetException {
		//
		final BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		//
		Assertions.assertNull(invoke(METHOD_SET_RGB, null, image, null, null));
		//
		Assertions.assertNull(invoke(METHOD_SET_RGB, null, image, 0, null));
		//
		final int rgb = image.getRGB(0, 0);
		//
		Assertions.assertNull(invoke(METHOD_SET_RGB, null, image, rgb, rgb));
		//
	}

	@Test
	void testSetPartOfSpeech() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_PART_OF_SPEECH, null, textAndImage, null));
		//
	}

	@Test
	void testAdjustImageColor() throws IllegalAccessException, InvocationTargetException {
		//
		final Object tai = Narcissus.allocateInstance(CLASS_TEXT_AND_IMAGE);
		//
		Assertions.assertNull(invoke(METHOD_ADJUST_IMAGE_COLOR, null, Arrays.asList(null, tai)));
		//
		FieldUtils.writeDeclaredField(tai, "accentImage", Narcissus.allocateInstance(BufferedImage.class), true);
		//
		Assertions.assertNull(invoke(METHOD_ADJUST_IMAGE_COLOR, null, Arrays.asList(null, tai)));
		//
		FieldUtils.writeDeclaredField(tai, "accentImage", new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), true);
		//
		Assertions.assertNull(invoke(METHOD_ADJUST_IMAGE_COLOR, null, Arrays.asList(null, tai, tai)));
		//
	}

	@Test
	void testClose() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(
				invoke(METHOD_CLOSE, null, Util.cast(Player.class, Narcissus.allocateInstance(Player.class))));
		//
	}

	@Test
	void testGetTextAndImages() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_TEXT_AND_IMAGES, null, null, textAndImage));
		//
		Assertions.assertNull(invoke(METHOD_GET_TEXT_AND_IMAGES, null, instance, textAndImage));
		//
		FieldUtils.writeDeclaredField(instance, "cbmCurve",
				new DefaultComboBoxModel<>(new Entry<?, ?>[] { Pair.of(null, null) }), true);
		//
		Assertions.assertNull(invoke(METHOD_GET_TEXT_AND_IMAGES, null, instance, textAndImage));
		//
		Assertions.assertNull(invoke(METHOD_GET_TEXT_AND_IMAGES, null, instance, null));
		//
	}

	@Test
	void testCommonPrefix() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_COMMON_PREFIX, null, Collections.singleton(null)));
		//
		Assertions.assertNull(invoke(METHOD_COMMON_PREFIX, null, Collections.nCopies(2, null)));
		//
		Assertions.assertNull(invoke(METHOD_COMMON_PREFIX, null, Arrays.asList(null, EMPTY)));
		//
		Assertions.assertEquals(EMPTY, invoke(METHOD_COMMON_PREFIX, null, Collections.nCopies(3, EMPTY)));
		//
	}

	@Test
	void testGetConjugation() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(EMPTY, invoke(METHOD_GET_CONJUGATION, null, Collections.singleton(EMPTY), 0));
		//
	}

	@Test
	void testProcessPage() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_PROCESS_PAGE, null, pdfGraphicsStreamEngine, null));
		//
		Assertions.assertNull(invoke(METHOD_PROCESS_PAGE, null, pdfGraphicsStreamEngine, new PDPage()));
		//
	}

	@Test
	void testSetHandler() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_HANDLER, null, Reflection.newProxy(Proxy.class, ih), null));
		//
	}

	@Test
	void testAddAnnotations() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_ADD_ANNOTATIONS, null, new PDDocument(), null, null, null, true));
		//
		Assertions.assertNull(invoke(METHOD_ADD_ANNOTATIONS, null, new PDDocument(), new PDPage(), null, null, true));
		//
		Assertions
				.assertNull(invoke(METHOD_ADD_ANNOTATIONS, null, null, null, Collections.singleton(null), null, true));
		//
	}

	@Test
	void testMapToDouble() throws Throwable {
		//
		Assertions.assertNull(invoke(METHOD_MAP_TO_DOUBLE, null, Stream.empty(), null));
		//
		Assertions.assertNull(invoke(METHOD_MAP_TO_DOUBLE, null, stream, null));
		//
	}

	@Test
	void testGet() throws Throwable {
		//
		final int zero = 0;
		//
		Assertions.assertEquals(zero, invoke(METHOD_GET, null, new int[] { zero }, zero, zero));
		//
	}

	@Test
	void testCreatePDEmbeddedFile() throws Throwable {
		//
		try (final PDDocument pdDocument = new PDDocument();
				final InputStream is = new ByteArrayInputStream(new byte[] {})) {
			//
			Assertions.assertNull(invoke(METHOD_CREATE_PD_EMBEDDED_FILE, null, pdDocument, null, null, null));
			//
			Assertions.assertNotNull(invoke(METHOD_CREATE_PD_EMBEDDED_FILE, null, pdDocument, is, null, null));
			//
			Assertions.assertNotNull(invoke(METHOD_CREATE_PD_EMBEDDED_FILE, null, pdDocument, is, null, new byte[] {}));
			//
		} // try
			//
	}

	@Test
	void testGetMimeType() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_MIME_TYPE, null,
				Util.cast(ContentInfo.class, Narcissus.allocateInstance(ContentInfo.class))));
		//
	}

	@Test
	void testGetVoiceUrlByX() throws Throwable {
		//
		Assertions.assertNull(invoke(METHOD_GET_VOICE_URL_BY_X, null, null, Collections.singleton(null), 0));
		//
		final Pattern patern = Pattern.compile("^\\d+_(\\d+)_\\d+_(\\w+)\\.\\w+$");
		//
		String url = "https://www.gavo.t.u-tokyo.ac.jp/ojad/sound4/mp3/female/011/1184_1_1_female.mp3";
		//
		Assertions.assertEquals(Unit.with(url),
				invoke(METHOD_GET_VOICE_URL_BY_X, null, patern, Collections.singleton(url), 0));
		//
		Assertions.assertEquals(
				Unit.with(url = "https://www.gavo.t.u-tokyo.ac.jp/ojad/sound4/mp3/male/011/1184_1_1_male.mp3"),
				invoke(METHOD_GET_VOICE_URL_BY_X, null, patern, Collections.singleton(url), 1));
		//
		Throwable throwable = null;
		//
		try {
			//
			invoke(METHOD_GET_VOICE_URL_BY_X, null, patern, Collections.nCopies(2,
					"https://www.gavo.t.u-tokyo.ac.jp/ojad/sound4/mp3/male/011/1184_1_1_male.mp3"), 1);
			//
		} catch (final InvocationTargetException e) {
			//
			throwable = e != null ? e.getTargetException() : e;
			//
		} // try
			//
		Assertions.assertTrue(throwable instanceof IllegalStateException);
		//
	}

	@Test
	void testGetTextAndImageByXY() throws Throwable {
		//
		Assertions
				.assertNull(invoke(METHOD_GET_TEXT_AND_IMAGE_BY_X_Y, null, null, Collections.singleton(null), 0, null));
		//
		final Object tai = Narcissus.allocateInstance(CLASS_TEXT_AND_IMAGE);
		//
		final Iterable<?> iterable = Collections.singleton(tai);
		//
		Assertions.assertNull(invoke(METHOD_GET_TEXT_AND_IMAGE_BY_X_Y, null, null, iterable, 0, null));
		//
		final Map<String, ?> map = new LinkedHashMap<>();
		//
		Util.put(map, "https://www.gavo.t.u-tokyo.ac.jp/ojad/sound4/mp3/female/011/1184_1_1_female.mp3", null);
		//
		FieldUtils.writeDeclaredField(tai, "voiceUrlImages", map, true);
		//
		final Pattern patern = Pattern.compile("^\\d+_(\\d+)_\\d+_(\\w+)\\.\\w+$");
		//
		Assertions.assertEquals("{\"voiceUrlImages\":{}}",
				ObjectMapperUtil.writeValueAsString(
						setDefaultPropertyInclusion(setVisibility(objectMapper, PropertyAccessor.ALL, Visibility.ANY),
								Include.NON_NULL),
						invoke(METHOD_GET_TEXT_AND_IMAGE_BY_X_Y, null, patern, iterable, 0, Integer.toString(1))));
		//
		Util.clear(map);
		//
		Util.put(map, "https://www.gavo.t.u-tokyo.ac.jp/ojad/sound4/mp3/male/011/1184_1_1_male.mp3", null);
		//
		FieldUtils.writeDeclaredField(tai, "voiceUrlImages", map, true);
		//
		Assertions.assertEquals("{\"voiceUrlImages\":{}}", ObjectMapperUtil.writeValueAsString(objectMapper,
				invoke(METHOD_GET_TEXT_AND_IMAGE_BY_X_Y, null, patern, iterable, 1, Integer.toString(1))));
		//
		Assertions.assertNull(invoke(METHOD_GET_TEXT_AND_IMAGE_BY_X_Y, null, patern, iterable, 0, null));
		//
	}

	@Test
	void testGetSize() throws Throwable {
		//
		final int defaultValue = 10;
		//
		Assertions.assertEquals(defaultValue,
				invoke(METHOD_GET_SIZE, null, Collections.singleton(null), Predicates.alwaysTrue(), defaultValue));
		//
		Assertions.assertEquals(defaultValue, invoke(METHOD_GET_SIZE, null,
				Collections.singleton(imageDimensionPosition), Predicates.alwaysTrue(), defaultValue));
		//
	}

	@Test
	void testGetTranslateXs() throws Throwable {
		//
		final double defaultValue = 10;
		//
		Assertions.assertTrue(Objects.deepEquals(new double[] { defaultValue },
				invoke(METHOD_GET_TRANSLATE_XS, null, Collections.singleton(imageDimensionPosition), defaultValue)));
		//
	}

	@Test
	void testFlatMap() throws Throwable {
		//
		Assertions.assertNull(flatMap(Stream.empty(), null));
		//
		Assertions.assertNull(flatMap(stream, null));
		//
		Assertions.assertNotNull(flatMap(Stream.of(Set.of()), Collection::stream));
		//
	}

	private static <T, R> Stream<R> flatMap(final Stream<T> instance,
			final Function<? super T, ? extends Stream<? extends R>> mapper) throws Throwable {
		try {
			final Object obj = invoke(METHOD_FLAT_MAP, null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateImageDimensionPositionPredicate()
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		//
		final Method test = Util.getDeclaredMethod(Predicate.class, "test", Object.class);
		//
		Assertions.assertEquals(Boolean.FALSE, Narcissus.invokeMethod(
				invoke(METHOD_CREATE_IMAGE_DIMENSION_POSITION_PREDICATE, null, (Object) null), test, (Object) null));
		//
		Assertions.assertEquals(Boolean.FALSE,
				Narcissus.invokeMethod(invoke(METHOD_CREATE_IMAGE_DIMENSION_POSITION_PREDICATE, null, (Object) null),
						test, imageDimensionPosition));
		//
		Assertions.assertEquals(Boolean.FALSE,
				Narcissus.invokeMethod(invoke(METHOD_CREATE_IMAGE_DIMENSION_POSITION_PREDICATE, null, new double[] {}),
						test, imageDimensionPosition));
		//
		FieldUtils.writeDeclaredField(imageDimensionPosition, "translateX", Float.valueOf(0), true);
		//
		Assertions.assertEquals(Boolean.TRUE,
				Narcissus.invokeMethod(
						invoke(METHOD_CREATE_IMAGE_DIMENSION_POSITION_PREDICATE, null, new double[] { 0d }), test,
						imageDimensionPosition));
		//
		Assertions.assertEquals(Boolean.FALSE,
				Narcissus.invokeMethod(
						invoke(METHOD_CREATE_IMAGE_DIMENSION_POSITION_PREDICATE, null, new double[] { 1d }), test,
						imageDimensionPosition));
		//
		Assertions.assertEquals(Boolean.FALSE,
				Narcissus.invokeMethod(invoke(METHOD_CREATE_IMAGE_DIMENSION_POSITION_PREDICATE, null, (Object) null),
						test, imageDimensionPosition));
		//
	}

	@Test
	void testCreateFunction() throws Throwable {
		//
		final Method apply = Util.getDeclaredMethod(Function.class, "apply", Object.class);
		//
		Assertions.assertNull(
				Narcissus.invokeMethod(invoke(METHOD_CREATE_FUNCTION, null, (Object) null), apply, (Object) null));
		//
		Assertions.assertEquals("1",
				Narcissus.invokeMethod(
						invoke(METHOD_CREATE_FUNCTION, null, Pattern.compile("^\\d+_(\\d+)_\\d+_(\\w+)\\.\\w+$")),
						apply, "https://www.gavo.t.u-tokyo.ac.jp/ojad/sound4/mp3/female/012/1216_1_1_female.mp3"));
		//
	}

	@Test
	void testCreateListCellRenderer() throws Throwable {
		//
		Assertions.assertNull(
				Util.getListCellRendererComponent(createListCellRenderer(null), null, null, 0, false, false));
		//
		Assertions.assertNull(
				Util.getListCellRendererComponent(createListCellRenderer(null, null), null, null, 0, false, false));
		//
		Assertions.assertNull(Util.getListCellRendererComponent(
				createListCellRenderer(null, Util.cast(Dimension.class, Narcissus.allocateInstance(Dimension.class))),
				null, null, 0, false, false));
		//
	}

	private static ListCellRenderer<? super Entry<String, String>> createListCellRenderer(
			final ListCellRenderer<? super Entry<String, String>> lcr) throws Throwable {
		try {
			final Object obj = invoke(METHOD_CREATE_LIST_CELL_RENDERER1, null, lcr);
			if (obj == null) {
				return null;
			} else if (obj instanceof ListCellRenderer) {
				return (ListCellRenderer) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static ListCellRenderer<? super Entry<String, String>> createListCellRenderer(
			final ListCellRenderer<? super Entry<String, ? extends Image>> lcr, final Dimension preferredSize)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_LIST_CELL_RENDERER2.invoke(null, lcr, preferredSize);
			if (obj == null) {
				return null;
			} else if (obj instanceof ListCellRenderer) {
				return (ListCellRenderer) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSet() throws IllegalAccessException {
		//
		Assertions
				.assertDoesNotThrow(
						() -> Narcissus.invokeStaticMethod(
								Util.getDeclaredMethod(VoiceManagerOjadAccentPanel.class, "set", List.class,
										Integer.TYPE, Object.class),
								Reflection.newProxy(List.class, ih), Integer.valueOf(0), null));
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			set(Collections.singleton(null), 0, null, 0, null, null, null);
			//
		});
		//
		FieldUtils.writeDeclaredField(textAndImage, "conjugation", StringUtils.repeat(' ', 8), true);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			set(Collections.singleton(textAndImage), 0, null, 310, null, null, null);
			//
		});
		//
		FieldUtils.writeDeclaredField(textAndImage, "kanji", StringUtils.repeat(' ', 9), true);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			set(Collections.singleton(textAndImage), 0, null, 310, null, null, null);
			//
		});
		//
		FieldUtils.writeDeclaredField(textAndImage, "hiragana", StringUtils.repeat(' ', 11), true);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			set(Collections.singleton(textAndImage), 0, null, 310, null, null, null);
			//
		});
		//
		FieldUtils.writeDeclaredField(textAndImage, "conjugation", StringUtils.repeat(' ', 3), true);
		//
		final Object textAndImage2 = Narcissus.allocateInstance(CLASS_TEXT_AND_IMAGE);
		//
		FieldUtils.writeDeclaredField(textAndImage2, "conjugation", StringUtils.repeat(' ', 6), true);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			set(Collections.singleton(textAndImage2), 0, textAndImage, 310, null, null, null);
			//
		});
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			set(Collections.singleton(textAndImage2), 0, textAndImage, 338, null, null, null);
			//
		});
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			set(null, 0, null, 422, null, null, null);
			//
		});
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			set(null, 0, null, 394, null, null, null);
			//
		});
		//
		FieldUtils.writeDeclaredField(textAndImage2, "conjugation", StringUtils.repeat(' ', 6), true);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			set(Collections.singleton(textAndImage2), 0, null, 394, null, null, null);
			//
		});
		//
		FieldUtils.writeDeclaredField(textAndImage2, "conjugation", StringUtils.repeat(' ', 3), true);
		//
		FieldUtils.writeDeclaredField(textAndImage2, "kanji", StringUtils.repeat(' ', 7), true);
		//
		FieldUtils.writeDeclaredField(textAndImage2, "hiragana", StringUtils.repeat(' ', 11), true);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			set(Collections.singleton(textAndImage2), 0, null, 394, null, null, null);
			//
		});
		//
	}

	private static void set(final Iterable<?> textAndImages, final int i, final Object textAndImage,
			final int imageTotalWidth, final List<Integer> maxConjugationLength, final List<Integer> maxKanjiLength,
			final List<Integer> maxHiraganaLength) throws Throwable {
		try {
			METHOD_SET.invoke(null, textAndImages, i, textAndImage, imageTotalWidth, maxConjugationLength,
					maxKanjiLength, maxHiraganaLength);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAccentImageWidth() {
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(METHOD_GET_ACCENT_IMAGE_WIDTH, textAndImage));
		//
	}

	@Test
	void testGetCurveImageWidth() {
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(METHOD_GET_CURVE_IMAGE_WIDTH, textAndImage));
		//
	}

	@Test
	void testForEachOrdered() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_FOR_EACH_ORDERED, null, IntStream.empty(), null));
		//
		Assertions.assertNull(invoke(METHOD_FOR_EACH_ORDERED, null, Reflection.newProxy(IntStream.class, ih), null));
		//
	}

	@Test
	void testCreateDefaultTableModel() throws Throwable {
		//
		Assertions.assertEquals(Object.class, getColumnClass(createDefaultTableModel(null, 0), 0));
		//
		final Class<?> clz = VoiceManagerOjadAccentPanel.class;
		//
		final Object gender = FieldUtils.readDeclaredStaticField(clz, "GENDER", true);
		//
		Assertions.assertEquals(byte[].class, getColumnClass(createDefaultTableModel(new Object[] { gender }, 0), 0));
		//
		final Object copy = FieldUtils.readDeclaredStaticField(clz, "COPY", true);
		//
		Assertions.assertEquals(String.class, getColumnClass(createDefaultTableModel(new Object[] { copy }, 0), 0));
		//
	}

	private static Class<?> getColumnClass(final AbstractTableModel instance, final int index) {
		return instance != null ? instance.getColumnClass(index) : null;
	}

	private static DefaultTableModel createDefaultTableModel(final Object[] columnNames, final int rowCount)
			throws Throwable {
		try {
			final Object obj = invoke(METHOD_CREATE_DEFAULT_TABLE_MODEL, null, columnNames, rowCount);
			if (obj == null) {
				return null;
			} else if (obj instanceof DefaultTableModel) {
				return (DefaultTableModel) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFindEntryWithLongestValue() throws IllegalAccessException, InvocationTargetException {
		//
		if (ih != null) {
			//
			ih.size = 1;
			//
		} // if
			//
		Assertions.assertNull(
				invoke(METHOD_FIND_ENTRY_WITH_LONGEST_VALUE, null, Reflection.newProxy(ListModel.class, ih)));
		//
	}

	@Test
	void testCreateFont() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNotNull(
				invoke(METHOD_CREATE_FONT, null, Util.cast(Font.class, Narcissus.allocateInstance(Font.class)), null));
		//
	}

	@Test
	void testCreateToIntFunction() throws Throwable {
		//
		final ToIntFunction<Font> toIntFunction = createToIntFunction(0);
		//
		Assertions.assertEquals(0, toIntFunction != null ? toIntFunction.applyAsInt(null) : null);
		//
	}

	private static ToIntFunction<Font> createToIntFunction(final int difference) throws Throwable {
		try {
			final Object obj = invoke(METHOD_CREATE_TO_INT_FUNCTION, null, difference);
			if (obj == null) {
				return null;
			} else if (obj instanceof ToIntFunction) {
				return (ToIntFunction) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAdd() throws Throwable {
		//
		final Container container = Util.cast(Container.class, Narcissus.allocateInstance(Container.class));
		//
		Assertions.assertSame(container, invoke(METHOD_ADD_2, null, container, null));
		//
		Assertions.assertDoesNotThrow(() -> add(container, null, null));
		//
		final JPanel jPanel = new JPanel();
		//
		Assertions.assertSame(jPanel, invoke(METHOD_ADD_2, null, jPanel, null));
		//
		Assertions.assertDoesNotThrow(() -> add(jPanel, null, null));
		//
	}

	private static void add(final Container container, final Component component, final Object constraints)
			throws Throwable {
		try {
			METHOD_ADD_3.invoke(null, container, component, constraints);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}