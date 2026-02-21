package org.springframework.context.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableLongConsumer;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
import org.javatuples.Unit;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.ThrowingRunnable;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class JapanDictGuiTest {

	private static final int ZERO = 0;

	private static final int ONE = 1;

	private static Class<?> CLASS_JAPAN_DICT_ENTRY, CLASS_PITCH_ACCENT;

	private static Method METHOD_TEST_AND_GET, METHOD_SET_TEXT, METHOD_STARTS_WITH, METHOD_APPEND,
			METHOD_TEST_AND_ACCEPT3_OBJECT, METHOD_TEST_AND_ACCEPT3_LONG, METHOD_TEST_AND_ACCEPT5,
			METHOD_TEST_AND_ACCEPT4_BI_PREDICATE, METHOD_GET_AUDIO_URL, METHOD_TEST_AND_RUN2, METHOD_TEST_AND_RUN3,
			METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_SET_ENABLED, METHOD_TEST_AND_APPLY4, METHOD_TEST_AND_APPLY5,
			METHOD_TO_ARRAY, METHOD_GET_JLPT_LEVEL_INDICES, METHOD_GET_JLPT_LEVEL, METHOD_SET_JCB_JLPT_LEVEL,
			METHOD_CHOP_IMAGE1, METHOD_CHOP_IMAGE2, METHOD_TO_DURATION, METHOD_TO_BUFFERED_IMAGE,
			METHOD_GET_COLUMN_NAME, METHOD_GET_TABLE_CELL_RENDERER_COMPONENT, METHOD_GET_STROKE_IMAGE, METHOD_AND2,
			METHOD_AND3, METHOD_PREPARE_RENDERER, METHOD_GET_CELL_RENDERER, METHOD_GET_COLUMN_COUNT,
			METHOD_SET_ROW_SELECTION_INTERVAL, METHOD_CREATE_TABLE_CELL_RENDERER,
			METHOD_CREATE_PITCH_ACCENT_LIST_CELL_RENDERER, METHOD_SET_PREFERRED_SIZE, METHOD_FILTER,
			METHOD_ADD_PARAMETERS, METHOD_GET_JWT, METHOD_ADD_ROWS, METHOD_GET_SELECTED_ROW, METHOD_OR,
			METHOD_GET_MIN_MAX, METHOD_THEN_ACCEPT_ASYNC, METHOD_SET_STROKE_IMAGE_AND_STROKE_WITH_NUMBER_IMAGE,
			METHOD_COPY_FIELD, METHOD_GET_LINK_MULTI_MAP_ELEMENT, METHOD_GET_LINK_MULTI_MAP_STRING,
			METHOD_TEST_AND_RUN_THROWS, METHOD_CREATE_STRING_PD_RECTANGLE_ENTRY_LIST_CELL_RENDERER,
			METHOD_SET_CBM_PD_RECTANGLE_SELECTED_ITEM = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = JapanDictGui.class;
		//
		(METHOD_TEST_AND_GET = Util.getDeclaredMethod(clz, "testAndGet", Boolean.TYPE, FailableSupplier.class))
				.setAccessible(true);
		//
		(METHOD_SET_TEXT = Util.getDeclaredMethod(clz, "setText", String.class, JTextComponent[].class))
				.setAccessible(true);
		//
		(METHOD_STARTS_WITH = Util.getDeclaredMethod(clz, "startsWith", Strings.class, String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_APPEND = Util.getDeclaredMethod(clz, "append", StringBuilder.class, Character.TYPE))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3_OBJECT = Util.getDeclaredMethod(clz, "testAndAccept", Predicate.class, Object.class,
				FailableConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3_LONG = Util.getDeclaredMethod(clz, "testAndAccept", LongPredicate.class, Long.TYPE,
				FailableLongConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4_BI_PREDICATE = Util.getDeclaredMethod(clz, "testAndAccept", BiPredicate.class,
				Object.class, Object.class, FailableBiConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT5 = Util.getDeclaredMethod(clz, "testAndAccept", BiPredicate.class, Object.class,
				Object.class, FailableBiConsumer.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_GET_AUDIO_URL = Util.getDeclaredMethod(clz, "getAudioUrl", String.class, Strings.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_RUN2 = Util.getDeclaredMethod(clz, "testAndRun", Boolean.TYPE, Runnable.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_RUN3 = Util.getDeclaredMethod(clz, "testAndRun", Boolean.TYPE, FailableRunnable.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = Util.getDeclaredMethod(clz, "getSystemClipboard", Toolkit.class))
				.setAccessible(true);
		//
		(METHOD_SET_ENABLED = Util.getDeclaredMethod(clz, "setEnabled", Boolean.TYPE, Component.class, Component.class,
				Component[].class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY4 = Util.getDeclaredMethod(clz, "testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = Util.getDeclaredMethod(clz, "testAndApply", BiPredicate.class, Object.class,
				Object.class, FailableBiFunction.class, FailableBiFunction.class)).setAccessible(true);
		//
		(METHOD_TO_ARRAY = Util.getDeclaredMethod(clz, "toArray", Stream.class, IntFunction.class)).setAccessible(true);
		//
		(METHOD_GET_JLPT_LEVEL_INDICES = Util.getDeclaredMethod(clz, "getJlptLevelIndices", ComboBoxModel.class,
				String.class)).setAccessible(true);
		//
		(METHOD_GET_JLPT_LEVEL = Util.getDeclaredMethod(clz, "getJlptLevel", ComboBoxModel.class, String.class))
				.setAccessible(true);
		//
		(METHOD_SET_JCB_JLPT_LEVEL = Util.getDeclaredMethod(clz, "setJcbJlptLevel", int[].class)).setAccessible(true);
		//
		(METHOD_CHOP_IMAGE1 = Util.getDeclaredMethod(clz, "chopImage", BufferedImage.class)).setAccessible(true);
		//
		(METHOD_CHOP_IMAGE2 = Util.getDeclaredMethod(clz, "chopImage", byte[].class, BoundingBox.class))
				.setAccessible(true);
		//
		(METHOD_TO_DURATION = Util.getDeclaredMethod(clz, "toDuration", Object.class)).setAccessible(true);
		//
		(METHOD_TO_BUFFERED_IMAGE = Util.getDeclaredMethod(clz, "toBufferedImage", byte[].class)).setAccessible(true);
		//
		(METHOD_GET_COLUMN_NAME = Util.getDeclaredMethod(clz, "getColumnName", JTable.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_TABLE_CELL_RENDERER_COMPONENT = Util.getDeclaredMethod(clz, "getTableCellRendererComponent",
				TableCellRenderer.class, JTable.class, Object.class, Boolean.TYPE, Boolean.TYPE, Integer.TYPE,
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_STROKE_IMAGE = Util.getDeclaredMethod(clz, "getStrokeImage", clz, Page.class,
				CLASS_JAPAN_DICT_ENTRY = Util
						.forName("org.springframework.context.support.JapanDictGui$JapanDictEntry")))
				.setAccessible(true);
		//
		(METHOD_AND2 = Util.getDeclaredMethod(clz, "and", Boolean.TYPE, BooleanSupplier.class)).setAccessible(true);
		//
		(METHOD_AND3 = Util.getDeclaredMethod(clz, "and", Object.class, Predicate.class, Predicate.class))
				.setAccessible(true);
		//
		(METHOD_PREPARE_RENDERER = Util.getDeclaredMethod(clz, "prepareRenderer", JTable.class, TableCellRenderer.class,
				Integer.TYPE, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_CELL_RENDERER = Util.getDeclaredMethod(clz, "getCellRenderer", JTable.class, Integer.TYPE,
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_COLUMN_COUNT = Util.getDeclaredMethod(clz, "getColumnCount", JTable.class)).setAccessible(true);
		//
		(METHOD_SET_ROW_SELECTION_INTERVAL = Util.getDeclaredMethod(clz, "setRowSelectionInterval", JTable.class,
				Integer.TYPE, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_CREATE_TABLE_CELL_RENDERER = Util.getDeclaredMethod(clz, "createTableCellRenderer",
				TableCellRenderer.class)).setAccessible(true);
		//
		(METHOD_CREATE_PITCH_ACCENT_LIST_CELL_RENDERER = Util.getDeclaredMethod(clz,
				"createPitchAccentListCellRenderer", Component.class, ListCellRenderer.class, Dimension.class))
				.setAccessible(true);
		//
		(METHOD_SET_PREFERRED_SIZE = Util.getDeclaredMethod(clz, "setPreferredSize", Component.class, Dimension.class))
				.setAccessible(true);
		//
		(METHOD_FILTER = Util.getDeclaredMethod(clz, "filter", FailableStream.class, FailablePredicate.class))
				.setAccessible(true);
		//
		(METHOD_ADD_PARAMETERS = Util.getDeclaredMethod(clz, "addParameters", URIBuilder.class, List.class))
				.setAccessible(true);
		//
		(METHOD_GET_JWT = Util.getDeclaredMethod(clz, "getJwt", Iterable.class, String.class)).setAccessible(true);
		//
		(METHOD_ADD_ROWS = Util.getDeclaredMethod(clz, "addRows", JapanDictGui.class, Iterable.class, String.class,
				String.class, Multimap.class)).setAccessible(true);
		//
		(METHOD_GET_SELECTED_ROW = Util.getDeclaredMethod(clz, "getSelectedRow", JTable.class)).setAccessible(true);
		//
		(METHOD_OR = Util.getDeclaredMethod(clz, "or", Boolean.TYPE, Boolean.TYPE, boolean[].class))
				.setAccessible(true);
		//
		(METHOD_GET_MIN_MAX = Util.getDeclaredMethod(clz, "getMinMax", int[].class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_THEN_ACCEPT_ASYNC = Util.getDeclaredMethod(clz, "thenAcceptAsync", CompletableFuture.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_SET_STROKE_IMAGE_AND_STROKE_WITH_NUMBER_IMAGE = Util.getDeclaredMethod(clz,
				"setStrokeImageAndStrokeWithNumberImage", DefaultTableModel.class, CLASS_JAPAN_DICT_ENTRY))
				.setAccessible(true);
		//
		(METHOD_COPY_FIELD = Util.getDeclaredMethod(clz, "copyField", Object.class, Object.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_LINK_MULTI_MAP_ELEMENT = Util.getDeclaredMethod(clz, "getLinkMultimap", Element.class,
				Iterable.class)).setAccessible(true);
		//
		(METHOD_GET_LINK_MULTI_MAP_STRING = Util.getDeclaredMethod(clz, "getLinkMultimap", String.class,
				Iterable.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_RUN_THROWS = Util.getDeclaredMethod(clz, "testAndRunThrows", Boolean.TYPE,
				ThrowingRunnable.class)).setAccessible(true);
		//
		(METHOD_CREATE_STRING_PD_RECTANGLE_ENTRY_LIST_CELL_RENDERER = Util.getDeclaredMethod(clz,
				"createStringPDRectangleEntryListCellRenderer", ListCellRenderer.class)).setAccessible(true);
		//
		(METHOD_SET_CBM_PD_RECTANGLE_SELECTED_ITEM = Util.getDeclaredMethod(clz, "setCbmPDRectangleSelectedItem",
				JapanDictGui.class, Object.class)).setAccessible(true);
		//
		CLASS_PITCH_ACCENT = Util.forName("org.springframework.context.support.JapanDictGui$PitchAccent");
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean test, booleanValue, equals;

		private Integer size, length, columnCount, sum;

		private int[] selectedIndices;

		private Exception exception;

		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (ReflectionUtils.isEqualsMethod(method)) {
				//
				return equals;
				//
			} // if
				//
			final String name = Util.getName(method);
			//
			if (proxy instanceof FailableBiConsumer && Objects.equals(name, "accept") && exception != null) {
				//
				throw exception;
				//
			} // if
				//
			if (Objects.equals(Util.getReturnType(method), Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			if (proxy instanceof Collection && Objects.equals(name, "toArray")) {
				//
				return null;
				//
			} // if
				//
			if (proxy instanceof Predicate) {
				//
				if (Objects.equals(name, "test")) {
					//
					return test;
					//
				} // if
					//
			} else if (proxy instanceof BiPredicate) {
				//
				if (Objects.equals(name, "test")) {
					//
					return test;
					//
				} // if
					//
			} else if (proxy instanceof FailableFunction && Objects.equals(name, "apply")) {
				//
				return null;
				//
			} else if (proxy instanceof FailableBiFunction) {
				//
				if (Objects.equals(name, "apply")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Iterable) {
				//
				if (Util.anyMatch(Stream.of("iterator", "spliterator"), x -> Objects.equals(name, x))) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Util.contains(Arrays.asList("toArray", "mapToDouble"), name)) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof ListModel && Objects.equals(name, "getSize")) {
				//
				return size;
				//
			} else if (proxy instanceof Page) {
				//
				if (Util.anyMatch(Stream.of("evaluate", "locator", "querySelectorAll"), x -> Objects.equals(name, x))) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Locator
					&& Util.anyMatch(Stream.of("screenshot", "boundingBox"), x -> Objects.equals(name, x))) {
				//
				return null;
				//
			} else if (proxy instanceof CharSequence && Objects.equals(name, "length")) {
				//
				return length;
				//
			} else if (proxy instanceof LongPredicate && Objects.equals(name, "test")) {
				//
				return test;
				//
			} else if (proxy instanceof TableModel) {
				//
				if (Objects.equals(name, "getValueAt")) {
					//
					return null;
					//
				} else if (Objects.equals(name, "getColumnCount")) {
					//
					return columnCount;
					//
				} // if
					//
			} else if (proxy instanceof TableCellRenderer && Objects.equals(name, "getTableCellRendererComponent")) {
				//
				return null;
				//
			} else if (proxy instanceof ElementHandle
					&& Util.anyMatch(Stream.of("boundingBox", "querySelectorAll", "screenshot", "getAttribute"),
							x -> Objects.equals(name, x))) {
				//
				return null;
				//
			} else if (proxy instanceof ListSelectionModel && Objects.equals(name, "getSelectedIndices")) {
				//
				return selectedIndices;
				//
			} else if (proxy instanceof NameValuePair && Objects.equals(name, "getValue")) {
				//
				return null;
				//
			} else if (proxy instanceof BooleanSupplier && Objects.equals(name, "getAsBoolean")) {
				//
				return booleanValue;
				//
			} else if (proxy instanceof Map && Objects.equals(name, "get")) {
				//
				return null;
				//
			} else if (proxy instanceof IntStream && Objects.equals(name, "sum")) {
				//
				return sum;
				//
			} else if (proxy instanceof TableColumnModel && Objects.equals(name, "getColumn")) {
				//
				return null;
				//
			} else if (proxy instanceof DoubleStream && Objects.equals(name, "max")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private static class MH implements MethodHandler {

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String name = Util.getName(thisMethod);
			//
			if (self instanceof Toolkit && Objects.equals(name, "getSystemClipboard")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private JapanDictGui instance = null;

	private IH ih = null;

	private ObjectMapper objectMapper;

	private Object japanDictEntry, pitchAccent;

	private OperatingSystem operatingSystem = null;

	private String connectivity = null;

	private final boolean isHeadless = GraphicsEnvironment.isHeadless();

	private boolean nmcliExists = false;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		instance = Util.cast(JapanDictGui.class, Narcissus.allocateInstance(JapanDictGui.class));
		//
		ih = new IH();
		//
		objectMapper = new ObjectMapper();
		//
		japanDictEntry = Narcissus.allocateInstance(CLASS_JAPAN_DICT_ENTRY);
		//
		pitchAccent = Narcissus.allocateInstance(CLASS_PITCH_ACCENT);
		//
		if (Objects.equals(operatingSystem = OperatingSystemUtil.getOperatingSystem(), OperatingSystem.LINUX)) {
			//
			final Charset charset = StandardCharsets.UTF_8;
			//
			try (final InputStream is = getInputStream(start(new ProcessBuilder(new String[] { "which", "nmcli" })))) {
				//
				nmcliExists = Util.exists(testAndApply(Objects::nonNull,
						StringUtils.trim(IOUtils.toString(is, charset)), File::new, null));
				//
			} // try
				//
			try (final InputStream is = getInputStream(
					start(nmcliExists ? new ProcessBuilder(new String[] { "nmcli", "-mode", "multiline", "general" })
							: null))) {
				//
				final Collection<String> collection = testAndApply(Objects::nonNull, is,
						x -> IOUtils.readLines(x, charset), null);
				//
				connectivity = Util
						.toString(testAndApply(
								x -> IterableUtils
										.size(x) == 1,
								Util.toList(Util.map(
										Util.filter(Util.stream(collection),
												x -> StringsUtil.startsWith(Strings.CI, x, "CONNECTIVITY:")),
										x -> StringUtils.trim(StringUtils.substringAfter(x, ':')))),
								x -> IterableUtils.size(x) == 0, null));
				//
			} // try
				//
		} // if
			//
	}

	private static Process start(final ProcessBuilder instance) throws IOException {
		return instance != null ? instance.start() : null;
	}

	private static InputStream getInputStream(final Process instance) {
		return instance != null ? instance.getInputStream() : null;
	}

	@Test
	void testNull() throws Throwable {
		//
		final Method[] ms = JapanDictGui.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType, returnType = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		String toString, name = null;
		//
		Object result = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()
					|| Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "or"),
							Arrays.equals(parameterTypes = Util.getParameterTypes(m),
									new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class }))) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Long.TYPE)) {
					//
					Util.add(collection, Long.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Float.TYPE)) {
					//
					Util.add(collection, Float.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Double.TYPE)) {
					//
					Util.add(collection, Double.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Character.TYPE)) {
					//
					Util.add(collection, Character.valueOf(' '));
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
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				if (isThrowRuntimeException(Util.getClass(instance), m)) {
					//
					final Method m1 = m;
					//
					final Object[] os1 = os;
					//
					Assertions.assertThrows(RuntimeException.class, () -> Narcissus.invokeStaticMethod(m1, os1));
					//
					continue;
					//
				} // if
					//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (or(Boolean.logicalAnd(isPrimitive(returnType = Util.getReturnType(m)),
						!Objects.equals(returnType, Void.TYPE)),
						Boolean.logicalAnd(Objects.equals(name, "getJapanDictEntry"),
								Arrays.equals(parameterTypes,
										new Class<?>[] { Element.class, Pattern.class, Pattern.class,
												ObjectMapper.class, Integer.TYPE, Map.class, Iterable.class })),
						Boolean.logicalAnd(Objects.equals(name, "createTableCellRenderer"),
								Arrays.equals(parameterTypes, new Class<?>[] { TableCellRenderer.class })),
						Boolean.logicalAnd(Objects.equals(name, "createPitchAccentListCellRenderer"),
								Arrays.equals(parameterTypes,
										new Class<?>[] { Component.class, ListCellRenderer.class, Dimension.class })),
						Boolean.logicalAnd(Objects.equals(name, "getMinMax"),
								Arrays.equals(parameterTypes, new Class<?>[] { int[].class, Integer.TYPE })),
						Boolean.logicalAnd(Objects.equals(name, "toPdfByteArray"),
								Arrays.equals(parameterTypes,
										new Class<?>[] { CLASS_JAPAN_DICT_ENTRY, PDFont.class, String.class,
												PDRectangle.class })),
						Boolean.logicalAnd(Objects.equals(name, "getSamplePdfByteArray"),
								Arrays.equals(parameterTypes, new Class<?>[] {})),
						Boolean.logicalAnd(Objects.equals(name, "createStringPDRectangleEntryListCellRenderer"),
								Arrays.equals(parameterTypes, new Class<?>[] { ListCellRenderer.class })))) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} else {
				//
				if (Objects.equals(name, "actionPerformed")
						&& Arrays.equals(parameterTypes, new Class<?>[] { ActionEvent.class })
						&& Objects.equals(operatingSystem, OperatingSystem.LINUX)
						&& !StringsUtil.equals(Strings.CI, connectivity, "full") && isHeadless && nmcliExists) {
					//
					final Method m1 = m;
					//
					final Object[] os1 = os;
					//
					Assertions
							.assertThrows(RuntimeException.class,
									() -> Narcissus
											.invokeMethod(
													instance = ObjectUtils.getIfNull(instance,
															() -> Util.cast(JapanDictGui.class,
																	Narcissus.allocateInstance(JapanDictGui.class))),
													m1, os1));
					//
					continue;
					//
				} // if
					//
				result = Narcissus.invokeMethod(
						instance = ObjectUtils.getIfNull(instance,
								() -> Util.cast(JapanDictGui.class, Narcissus.allocateInstance(JapanDictGui.class))),
						m, os);
				//
				if (Boolean.logicalAnd(Objects.equals(Util.getName(m), "getUserAgent"), m.getParameterCount() == 0)) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} // if
				//
		} // for
			//
	}

	private static boolean isPrimitive(final Class<?> instance) {
		return instance != null && instance.isPrimitive();
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testNonNull() throws Throwable {
		//
		final Method[] ms = JapanDictGui.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType, returnType = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		String name, toString = null;
		//
		Object result = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || or(m.isSynthetic(),
					Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "setText"),
							Arrays.equals(parameterTypes = Util.getParameterTypes(m),
									new Class<?>[] { String.class, JTextComponent[].class })),
					Boolean.logicalAnd(Objects.equals(name, "setEditable"),
							Arrays.equals(parameterTypes, new Class<?>[] { Boolean.TYPE, JTextComponent[].class })),
					Boolean.logicalAnd(Objects.equals(name, "addActionListener"),
							Arrays.equals(parameterTypes,
									new Class<?>[] { ActionListener.class, AbstractButton[].class })),
					Boolean.logicalAnd(Objects.equals(name, "setEnabled"), Arrays.equals(parameterTypes,
							new Class<?>[] { Boolean.TYPE, Component.class, Component.class, Component[].class })),
					Boolean.logicalAnd(Objects.equals(name, "getTableCellRendererComponent"),
							Arrays.equals(parameterTypes,
									new Class<?>[] { TableCellRenderer.class, JTable.class, Object.class, Boolean.TYPE,
											Boolean.TYPE, Integer.TYPE, Integer.TYPE })),
					Boolean.logicalAnd(Objects.equals(name, "getJapanDictEntry"),
							Arrays.equals(parameterTypes,
									new Class<?>[] { Element.class, Pattern.class, Pattern.class, ObjectMapper.class,
											Integer.TYPE, Integer.TYPE, Map.class })),
					Boolean.logicalAnd(Objects.equals(name, "createTableCellRenderer"),
							Arrays.equals(parameterTypes, new Class<?>[] { TableCellRenderer.class })),
					Boolean.logicalAnd(Objects.equals(name, "or"), Arrays.equals(parameterTypes,
							new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class })))) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Long.TYPE)) {
					//
					Util.add(collection, Long.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Float.TYPE)) {
					//
					Util.add(collection, Float.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Double.TYPE)) {
					//
					Util.add(collection, Double.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Character.TYPE)) {
					//
					Util.add(collection, Character.valueOf(' '));
					//
				} else if (isArray(parameterType)) {
					//
					Util.add(collection, Array.newInstance(componentType(parameterType), 0));
					//
				} else if (isInterface(parameterType)) {
					//
					if ((ih = ObjectUtils.getIfNull(ih, IH::new)) != null) {
						//
						ih.test = Boolean.FALSE;
						//
						ih.size = ih.length = ih.columnCount = ih.sum = Integer.valueOf(0);
						//
					} // if
						//
					Util.add(collection, Reflection.newProxy(parameterType, ih));
					//
				} else if (parameterType != null && !Modifier.isAbstract(parameterType.getModifiers())) {
					//
					Util.add(collection, Narcissus.allocateInstance(parameterType));
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
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				if (isThrowRuntimeException(Util.getClass(instance), m)) {
					//
					final Method m1 = m;
					//
					final Object[] os1 = os;
					//
					Assertions.assertThrows(RuntimeException.class, () -> Narcissus.invokeStaticMethod(m1, os1));
					//
					continue;
					//
				} // if
					//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (or(Boolean.logicalAnd(isPrimitive(returnType = Util.getReturnType(m)),
						!Objects.equals(returnType, Void.TYPE)),
						Boolean.logicalAnd(Objects.equals(name, "append"),
								Arrays.equals(parameterTypes, new Class<?>[] { StringBuilder.class, Character.TYPE })),
						Boolean.logicalAnd(Objects.equals(name, "createPitchAccentListCellRenderer"),
								Arrays.equals(parameterTypes,
										new Class<?>[] { Component.class, ListCellRenderer.class, Dimension.class })),
						Boolean.logicalAnd(Objects.equals(name, "filter"),
								Arrays.equals(parameterTypes,
										new Class<?>[] { FailableStream.class, FailablePredicate.class })),
						Boolean.logicalAnd(Objects.equals(name, "addParameters"),
								Arrays.equals(parameterTypes, new Class<?>[] { URIBuilder.class, List.class })),
						Boolean.logicalAnd(Objects.equals(name, "clearParameters"),
								Arrays.equals(parameterTypes, new Class<?>[] { URIBuilder.class })),
						Boolean.logicalAnd(Objects.equals(name, "getQueryParams"),
								Arrays.equals(parameterTypes, new Class<?>[] { URIBuilder.class })),
						Boolean.logicalAnd(Objects.equals(name, "getJapanDictEntry"),
								Arrays.equals(parameterTypes,
										new Class<?>[] { Element.class, Pattern.class, Pattern.class,
												ObjectMapper.class, Integer.TYPE, Map.class, Iterable.class })),
						Boolean.logicalAnd(Objects.equals(name, "getMinMax"),
								Arrays.equals(parameterTypes, new Class<?>[] { int[].class, Integer.TYPE })),
						Boolean.logicalAnd(Objects.equals(name, "chopImage"),
								Arrays.equals(parameterTypes, new Class<?>[] { BufferedImage.class, int[].class })),
						Boolean.logicalAnd(Objects.equals(name, "orElse"),
								Arrays.equals(parameterTypes, new Class<?>[] { Optional.class, Object.class })),
						Boolean.logicalAnd(Objects.equals(name, "toPdfByteArray"),
								Arrays.equals(parameterTypes,
										new Class<?>[] { CLASS_JAPAN_DICT_ENTRY, PDFont.class, String.class,
												PDRectangle.class })),
						Boolean.logicalAnd(Objects.equals(name, "toPDcolor"),
								Arrays.equals(parameterTypes, new Class<?>[] { Color.class, PDColorSpace.class })),
						Boolean.logicalAnd(Objects.equals(name, "getSamplePdfByteArray"),
								Arrays.equals(parameterTypes, new Class<?>[] {})),
						Boolean.logicalAnd(Objects.equals(name, "createStringPDRectangleEntryListCellRenderer"),
								Arrays.equals(parameterTypes, new Class<?>[] { ListCellRenderer.class })))) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} else {
				//
				if (Objects.equals(name, "actionPerformed")
						&& Arrays.equals(parameterTypes, new Class<?>[] { ActionEvent.class })
						&& Objects.equals(operatingSystem, OperatingSystem.LINUX)
						&& !StringsUtil.equals(Strings.CI, connectivity, "full") && isHeadless && nmcliExists) {
					//
					final Method m1 = m;
					//
					final Object[] os1 = os;
					//
					Assertions
							.assertThrows(RuntimeException.class,
									() -> Narcissus
											.invokeMethod(
													instance = ObjectUtils.getIfNull(instance,
															() -> Util.cast(JapanDictGui.class,
																	Narcissus.allocateInstance(JapanDictGui.class))),
													m1, os1));
					//
					continue;
					//
				} // if
					//
				result = Narcissus.invokeMethod(
						instance = ObjectUtils.getIfNull(instance,
								() -> Util.cast(JapanDictGui.class, Narcissus.allocateInstance(JapanDictGui.class))),
						m, os);
				//
				if (Boolean.logicalAnd(Objects.equals(name, "getUserAgent"), m.getParameterCount() == 0)) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} // if
				//
		} // for
			//
	}

	private static boolean isThrowRuntimeException(final Class<?> clz, final Method method) throws Throwable {
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				"/" + StringsUtil.replace(Strings.CS, Util.getName(clz), ".", "/") + ".class")) {
			//
			final Stream<Instruction> stream = testAndApply(Objects::nonNull,
					InstructionListUtil
							.getInstructions(testAndApply(Objects::nonNull,
									getCode(getCode(JavaClassUtil.getMethod(ClassParserUtil.parse(
											testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)),
											method))),
									InstructionList::new, null)),
					Arrays::stream, null);
			//
			return CollectionUtils.isEqualCollection(Util.toList(Util.map(stream, x -> x != null ? x.getName() : null)),
					Arrays.asList("aload_0", "instanceof", "ifeq", "aload_0", "checkcast", "astore_1", "aload_1",
							"goto", "new", "dup", "aload_0", "invokespecial", "athrow"));
			//
		} // try
			//
	}

	private static byte[] getCode(final Code instance) {
		return instance != null ? instance.getCode() : null;
	}

	private static Code getCode(final org.apache.bcel.classfile.Method instance) {
		return instance != null ? instance.getCode() : null;
	}

	@Test
	void testOr() throws Throwable {
		//
		Assertions.assertFalse(or(false, false, null));
		//
	}

	private static boolean or(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = invoke(METHOD_OR, null, a, b, bs);
			if (obj instanceof Boolean bool) {
				return bool.booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> componentType(final Class<?> instance) {
		return instance != null ? instance.componentType() : null;
	}

	private static boolean isInterface(final Class<?> instance) {
		return instance != null && instance.isInterface();
	}

	private static boolean isArray(final Class<?> instance) {
		return instance != null && instance.isArray();
	}

	@Test
	void testActionPerformed() throws Throwable {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent("", 0, null)));
		//
		final Iterable<Field> fs = Util.toList(Util.filter(
				Util.stream(
						testAndApply(Objects::nonNull, Util.getClass(instance), FieldUtils::getAllFieldsList, null)),
				x -> Util.isAssignableFrom(AbstractButton.class, Util.getType(x))));
		//
		Field f = null;
		//
		for (int i = 0; i < IterableUtils.size(fs); i++) {
			//
			if ((f = IterableUtils.get(fs, i)) == null) {
				//
				continue;
				//
			} // if
				//
			final AbstractButton abstractButton = new JButton();
			//
			Narcissus.setField(instance, f, abstractButton);
			//
			if (Objects.equals(Util.getName(f), "btnExecute") && Objects.equals(operatingSystem, OperatingSystem.LINUX)
					&& !StringsUtil.equals(Strings.CI, connectivity, "full") && isHeadless && nmcliExists) {
				//
				Assertions.assertThrows(RuntimeException.class,
						() -> instance.actionPerformed(new ActionEvent(abstractButton, 0, null)));
				//
			} else {
				//
				Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(abstractButton, 0, null)));
				//
			} // if
				//
		} // for
			//
			// btnDownloadAudio
			//
		final AbstractButton btnDownloadAudio = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnDownloadAudio", btnDownloadAudio, true);
		//
		final ActionEvent actionEvent = new ActionEvent(btnDownloadAudio, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		final JTextComponent tfAudioUrl = new JTextField();
		//
		FieldUtils.writeDeclaredField(instance, "tfAudioUrl", tfAudioUrl, true);
		//
		Util.setText(tfAudioUrl, "");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		Util.setText(tfAudioUrl, " ");
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		Util.setText(tfAudioUrl, Integer.toString(ONE));
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
	}

	private static <T, R, E extends Exception> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) invoke(METHOD_TEST_AND_APPLY4, null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Test
	void testTestAndGet() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_GET, null, Boolean.TRUE, null));
		//
	}

	@Test
	void testSetText() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_TEXT, null, null, null));
		//
		Assertions.assertNull(invoke(METHOD_SET_TEXT, null, null, new JTextComponent[] { new JTextField() }));
		//
	}

	@Test
	void testStartsWith() throws IllegalAccessException, InvocationTargetException {
		//
		final Strings strings = Strings.CS;
		//
		Assertions.assertEquals(Boolean.TRUE, invoke(METHOD_STARTS_WITH, null, strings, null, null));
		//
		Assertions.assertEquals(Boolean.FALSE, invoke(METHOD_STARTS_WITH, null, strings, "", null));
		//
	}

	@Test
	void testAppend() throws IllegalAccessException, InvocationTargetException {
		//
		final StringBuilder sb = new StringBuilder();
		//
		final char c = ' ';
		//
		Assertions.assertEquals(String.valueOf(new char[] { c }),
				Util.toString(invoke(METHOD_APPEND, null, sb, Character.valueOf(c))));
		//
	}

	@Test
	void testTestAndAccept() throws IllegalAccessException, InvocationTargetException {
		//
		final LongPredicate longPredicate = Reflection.newProxy(LongPredicate.class, ih);
		//
		if (ih != null) {
			//
			ih.test = Boolean.TRUE;
			//
		} // if
			//
		final Long l = Long.valueOf(ONE);
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT3_LONG, null, longPredicate, l, null));
		//
		final Predicate<?> predicate = Predicates.alwaysTrue();
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT3_OBJECT, null, predicate, null, null));
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT3_OBJECT, null, predicate, null, null));
		//
		final BiPredicate<?, ?> biPredicate = org.meeuw.functional.Predicates.biAlwaysTrue();
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT4_BI_PREDICATE, null, biPredicate, null, null, null));
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT5, null, biPredicate, null, null, null, null));
		//
		if (ih != null) {
			//
			ih.exception = new Exception();
			//
		} // if
			//
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT5, null, biPredicate, null, null,
				Reflection.newProxy(FailableBiConsumer.class, ih), null));
		//
	}

	@Test
	void testGetAudioUrl() throws Throwable {
		//
		Assertions.assertEquals(":///read?outputFormat=mp3&vid=1&jwt=1.2.3&text=%3Ca%2F%3E&",
				getAudioUrl(null, Strings.CS, Arrays.asList("//", Integer.toString(ONE), "1.2.3", "<a/>", null)));
		//
	}

	private static String getAudioUrl(final String scheme, final Strings strings, final Iterable<?> iterable)
			throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_AUDIO_URL, null, scheme, strings, iterable);
			if (obj == null) {
				return null;
			} else if (obj instanceof String s) {
				return s;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndRun() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_RUN2, null, Boolean.TRUE, null));
		//
		final FailableRunnable<?> fr = () -> {
			//
			throw new RuntimeException();
			//
		};
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_RUN3, null, Boolean.TRUE, fr, null));
		//
	}

	@Test
	void testGetSystemClipboard() throws Throwable {
		//
		Assertions
				.assertNull(invoke(METHOD_GET_SYSTEM_CLIP_BOARD, null, ProxyUtil.createProxy(Toolkit.class, new MH())));
		//
	}

	@Test
	void testSetEnabled() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_ENABLED, null, Boolean.TRUE, null, null, null));
		//
	}

	@Test
	void testTestAndApply() throws IllegalAccessException, InvocationTargetException {
		//
		if (ih != null) {
			//
			ih.test = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertNull(invoke(METHOD_TEST_AND_APPLY5, null, Reflection.newProxy(BiPredicate.class, ih), null,
				null, null, null));
		//
	}

	@Test
	void testToArray() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TO_ARRAY, null, Reflection.newProxy(Stream.class, ih), null));
		//
		Assertions.assertNull(invoke(METHOD_TO_ARRAY, null, Stream.empty(), null));
		//
	}

	@Test
	void testGetJlptLevelIndices() throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
		//
		Assertions.assertNull(
				invoke(METHOD_GET_JLPT_LEVEL_INDICES, null, new DefaultComboBoxModel<>(new Object[] { null }), null));
		//
		Assertions.assertEquals(ObjectMapperUtil.writeValueAsString(objectMapper, new int[] { ONE }),
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_GET_JLPT_LEVEL_INDICES, null,
						new DefaultComboBoxModel<>(new Object[] { null, "N1" }), "JLPT N1")));
		//
	}

	@Test
	void testGetJlptLevel() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(
				invoke(METHOD_GET_JLPT_LEVEL, null, new DefaultComboBoxModel<>(new Object[] { null }), null));
		//
		final String n1 = "N1";
		//
		Assertions.assertEquals(n1,
				invoke(METHOD_GET_JLPT_LEVEL, null, new DefaultComboBoxModel<>(new Object[] { null, n1 }), "JLPT N1"));
		//
	}

	@Test
	void testSetJcbJlptLevel() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_JCB_JLPT_LEVEL, instance, new int[] { 0 }));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			try {
				//
				invoke(METHOD_SET_JCB_JLPT_LEVEL, instance, new int[] { ZERO, ONE });
				//
			} catch (final InvocationTargetException e) {
				//
				throw e.getTargetException();
				//
			} // try
				//
		});
		//
		//
	}

	@Test
	void testIH() throws Throwable {
		//
		final InvocationHandler invocationHandler = Util.cast(InvocationHandler.class,
				Narcissus.allocateInstance(Util.forName("org.springframework.context.support.JapanDictGui$IH")));
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(invocationHandler, null, null, null));
		//
		// java.awt.datatransfer.Transferable
		//
		final Transferable transferable = Reflection.newProxy(Transferable.class, invocationHandler);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(invocationHandler, transferable, null, null));
		//
		Assertions.assertNotNull(invoke(invocationHandler, transferable,
				Util.getDeclaredMethod(Transferable.class, "getTransferDataFlavors"), null));
		//
		Assertions.assertNull(invoke(invocationHandler, transferable,
				Util.getDeclaredMethod(Transferable.class, "getTransferData", DataFlavor.class), null));
		//
		// java.util.Map
		//
		final Map<?, ?> map = Reflection.newProxy(Map.class, invocationHandler);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(invocationHandler, map, null, null));
		//
		Assertions.assertNull(invoke(invocationHandler, map, Util.getDeclaredMethod(Map.class, "clear"), null));
		//
		final Method put = Util.getDeclaredMethod(Map.class, "put", Object.class, Object.class);
		//
		Assertions.assertNull(invoke(invocationHandler, map, put, null));
		//
		final Method get = Util.getDeclaredMethod(Map.class, "get", Object.class);
		//
		Assertions.assertThrows(IllegalStateException.class, () -> invoke(invocationHandler, map, get, null));
		//
		final Object k = "k", v = "v";
		//
		Assertions.assertNull(invoke(invocationHandler, map, put, new Object[] { k, v }));
		//
		Assertions.assertSame(v, invoke(invocationHandler, map, get, new Object[] { k }));
		//
	}

	private static Object invoke(final InvocationHandler instance, final Object proxy, final Method method,
			final Object[] args) throws Throwable {
		return instance != null ? instance.invoke(proxy, method, args) : null;
	}

	@Test
	void testChopImage() throws Throwable {
		//
		byte[] bs = new byte[] {};
		//
		Assertions.assertNull(invoke(METHOD_CHOP_IMAGE1, null, invoke(METHOD_TO_BUFFERED_IMAGE, instance, bs)));
		//
		Assertions.assertNull(invoke(METHOD_CHOP_IMAGE2, null, bs, null));
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			ImageIO.write(new BufferedImage(ONE, ONE, BufferedImage.TYPE_INT_RGB), "png", baos);
			//
			Assertions.assertNotNull(invoke(METHOD_CHOP_IMAGE1, null,
					invoke(METHOD_TO_BUFFERED_IMAGE, instance, bs = baos.toByteArray())));
			//
			Assertions.assertNotNull(invoke(METHOD_CHOP_IMAGE2, null, bs, null));
			//
			final Object boundingBox = Narcissus.allocateInstance(BoundingBox.class);
			//
			Assertions.assertNotNull(invoke(METHOD_CHOP_IMAGE2, null, bs, boundingBox));
			//
			FieldUtils.writeField(boundingBox, "width", ONE);
			//
			Assertions.assertNotNull(invoke(METHOD_CHOP_IMAGE2, null, bs, boundingBox));
			//
		} // try
			//
	}

	@Test
	void testToDuration() throws Throwable {
		//
		final Duration duration = toDuration(Integer.valueOf(ONE));
		//
		Assertions.assertNotNull(duration);
		//
		Assertions.assertSame(duration, toDuration(duration));
		//
		Assertions.assertEquals(duration, toDuration(toCharArray(Integer.toString(ONE))));
		//
		Assertions.assertEquals(Duration.ofSeconds(ONE), toDuration("PT1S"));
		//
	}

	private static char[] toCharArray(final String instance) throws NoSuchFieldException {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		if (Narcissus.getField(instance, Util.getDeclaredField(String.class, "value")) == null) {
			//
			return null;
			//
		} // if
			//
		return instance.toCharArray();
		//
	}

	private static Duration toDuration(final Object object) throws Throwable {
		try {
			final Object obj = invoke(METHOD_TO_DURATION, null, object);
			if (obj == null) {
				return null;
			} else if (obj instanceof Duration duration) {
				return duration;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetColumnName() throws IllegalAccessException, InvocationTargetException {
		//
		final String columnName = "";
		//
		Assertions.assertSame(columnName, invoke(METHOD_GET_COLUMN_NAME, null,
				new JTable(new DefaultTableModel(new Object[] { columnName }, 0)), 0));
		//
	}

	@Test
	void testGetTableCellRendererComponent() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_TABLE_CELL_RENDERER_COMPONENT, null,
				Reflection.newProxy(TableCellRenderer.class, ih), null, null, true, true, 0, 0));
		//
	}

	@Test
	void testValueChanged() throws IllegalAccessException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final ListSelectionModel lsm = Reflection.newProxy(ListSelectionModel.class, ih);
		//
		FieldUtils.writeDeclaredField(instance, "lsm", lsm, true);
		//
		final ListSelectionEvent listSelectionEvent = new ListSelectionEvent(lsm, 0, 0, false);
		//
		if (ih != null) {
			//
			ih.equals = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		final DefaultTableModel dtm = new DefaultTableModel(new Object[] { null }, 0);
		//
		FieldUtils.writeDeclaredField(instance, "dtm", dtm, true);
		//
		final JTable jTable = new JTable(dtm);
		//
		FieldUtils.writeDeclaredField(instance, "jTable", jTable, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[] {};
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		if (ih != null) {
			//
			ih.selectedIndices = new int[] { 0 };
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		dtm.addRow(new Object[] { null });
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		dtm.removeRow(0);
		//
		dtm.addRow(new Object[] { japanDictEntry });
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		FieldUtils.writeDeclaredField(japanDictEntry, "pageUrl", "", true);
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		FieldUtils.writeDeclaredField(japanDictEntry, "pageUrl", " ", true);
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
		FieldUtils.writeDeclaredField(japanDictEntry, "pageUrl", Integer.toString(ONE), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.valueChanged(listSelectionEvent));
		//
	}

	@Test
	void testGetStrokeImage() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_STROKE_IMAGE, null, null, null, japanDictEntry));
		//
	}

	@Test
	void testAnd() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(Boolean.FALSE, invoke(METHOD_AND2, null, Boolean.TRUE, null));
		//
		final BooleanSupplier booleanSupplier = Reflection.newProxy(BooleanSupplier.class, ih);
		//
		Assertions.assertEquals(ih.booleanValue = Boolean.FALSE,
				invoke(METHOD_AND2, null, Boolean.TRUE, booleanSupplier));
		//
		Assertions.assertEquals(ih.booleanValue = Boolean.TRUE,
				invoke(METHOD_AND2, null, Boolean.TRUE, booleanSupplier));
		//
		final Predicate<?> alwaysTrue = Predicates.alwaysTrue();
		//
		Assertions.assertEquals(Boolean.FALSE, invoke(METHOD_AND3, null, null, alwaysTrue, null));
		//
		Assertions.assertEquals(Boolean.TRUE, invoke(METHOD_AND3, null, null, alwaysTrue, alwaysTrue));
		//
	}

	@Test
	void testPrepareRenderer() throws IllegalAccessException, InvocationTargetException {
		//
		final JTable jTable = new JTable(new DefaultTableModel(new Object[] { null }, ONE));
		//
		Assertions.assertNull(
				invoke(METHOD_PREPARE_RENDERER, null, jTable, null, Integer.valueOf(ZERO), Integer.valueOf(ZERO)));
		//
		Assertions.assertNull(invoke(METHOD_PREPARE_RENDERER, null, jTable,
				Reflection.newProxy(TableCellRenderer.class, ih), Integer.valueOf(ZERO), Integer.valueOf(ZERO)));
		//
	}

	@Test
	void testGetCellRenderer() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNotNull(
				invoke(METHOD_GET_CELL_RENDERER, null, new JTable(new DefaultTableModel(new Object[] { null }, ONE)),
						Integer.valueOf(ZERO), Integer.valueOf(ZERO)));
		//
	}

	@Test
	void testGetColumnCount() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(Integer.valueOf(ONE),
				invoke(METHOD_GET_COLUMN_COUNT, null, new JTable(new DefaultTableModel(new Object[] { null }, ZERO))));
		//
	}

	@Test
	void testSetRowSelectionInterval() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_ROW_SELECTION_INTERVAL, null,
				new JTable(new DefaultTableModel(new Object[] { null }, ONE)), ZERO, ZERO));
		//
	}

	@Test
	void testCreateTableCellRenderer() throws IllegalAccessException, InvocationTargetException {
		//
		final TableCellRenderer tcr = Util.cast(TableCellRenderer.class,
				invoke(METHOD_CREATE_TABLE_CELL_RENDERER, null, (Object) null));
		//
		if (tcr == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertNull(tcr.getTableCellRendererComponent(null, tcr, false, false, ZERO, ZERO));
		//
		final DefaultTableModel dtm = new DefaultTableModel(new Object[] { "", "Hiragana" }, ZERO);
		//
		dtm.addRow(new Object[] { japanDictEntry });
		//
		Assertions.assertNull(tcr.getTableCellRendererComponent(new JTable(dtm), tcr, false, false, ZERO, ZERO));
		//
	}

	@Test
	void testCreatePitchAccentListCellRenderer()
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//
		final ListCellRenderer<?> lcr = Util.cast(ListCellRenderer.class,
				invoke(METHOD_CREATE_PITCH_ACCENT_LIST_CELL_RENDERER, null, null, null, null));
		//
		if (lcr == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertNull(lcr.getListCellRendererComponent(null, null, ZERO, false, false));
		//
		final Method getListCellRendererComponent = ListCellRenderer.class.getDeclaredMethod(
				"getListCellRendererComponent", JList.class, Object.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
		//
		Assertions.assertNull(
				Narcissus.invokeMethod(lcr, getListCellRendererComponent, null, pitchAccent, ZERO, false, false));
		//
		FieldUtils.writeDeclaredField(pitchAccent, "image", new BufferedImage(ONE, ONE, BufferedImage.TYPE_INT_RGB),
				true);
		//
		Assertions.assertNotNull(
				Narcissus.invokeMethod(lcr, getListCellRendererComponent, null, pitchAccent, ZERO, false, false));
		//
	}

	@Test
	void testSetPreferredSize() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_PREFERRED_SIZE, null, new JLabel(), null));
		//
	}

	@Test
	void testFilter() throws IllegalAccessException, InvocationTargetException {
		//
		final FailableStream<?> fs = new FailableStream<>(Stream.empty());
		//
		Assertions.assertSame(fs, invoke(METHOD_FILTER, null, fs, null));
		//
	}

	@Test
	void testAddParameters() throws IllegalAccessException, InvocationTargetException {
		//
		final Object object = Narcissus.allocateInstance(URIBuilder.class);
		//
		Assertions.assertSame(object, invoke(METHOD_ADD_PARAMETERS, null, object, Collections.emptyList()));
		//
	}

	@Test
	void testGetJwt() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_JWT, null, Collections.singleton(null), null));
		//
		final String string = "a.b.c";
		//
		Assertions.assertEquals(Unit.with(string), invoke(METHOD_GET_JWT, null, Collections.singleton(string), null));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			try {
				//
				invoke(METHOD_GET_JWT, null, Collections.nCopies(2, string), null);
				//
			} catch (final InvocationTargetException e) {
				//
				throw e.getTargetException();
				//
			} // try
				//
		});
		//
	}

	@Test
	void testAddRows() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_ADD_ROWS, null, null, Collections.singleton(null), null, null, null));
		//
		final Iterable<?> es = Collections.singleton(Narcissus.allocateInstance(Element.class));
		//
		Assertions.assertNull(invoke(METHOD_ADD_ROWS, null, null, es, null, null, null));
		//
		Assertions.assertNull(invoke(METHOD_ADD_ROWS, null, instance, es, null, null, null));
		//
	}

	@Test
	void testGetSelectedRow() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(Integer.valueOf(-1), invoke(METHOD_GET_SELECTED_ROW, null, new JTable()));
		//
	}

	@Test
	void testJapanDictEntry() throws Throwable {
		//
		final Method[] ms = Util.getDeclaredMethods(CLASS_JAPAN_DICT_ENTRY);
		//
		Method m = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			os = toArray(Collections.nCopies(m.getParameterCount(), null));
			//
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
				if (Arrays.equals(Util.getParameterTypes(m), new Class<?>[] { CLASS_JAPAN_DICT_ENTRY })) {
					//
					Assertions.assertNull(Narcissus.invokeStaticMethod(m, japanDictEntry), toString);
					//
				} // if
					//
			} else {
				//
				Assertions
						.assertNull(
								Narcissus
										.invokeMethod(
												japanDictEntry = ObjectUtils.getIfNull(japanDictEntry,
														() -> Narcissus.allocateInstance(CLASS_JAPAN_DICT_ENTRY)),
												m, os),
								toString);
				//
			} // if
				//
		} // for
			//
	}

	@Test
	void testGetMinMax() throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
		//
		final int two = 2;
		//
		int[] ints = new int[] { ONE };
		//
		Assertions.assertEquals(String.format("[%1$s,%2$s]", ONE, two), ObjectMapperUtil
				.writeValueAsString(objectMapper, invoke(METHOD_GET_MIN_MAX, null, ints, Integer.valueOf(two))));
		//
		final int three = 3;
		//
		Assertions.assertEquals(String.format("[%1$s,%2$s]", ONE, three), ObjectMapperUtil
				.writeValueAsString(objectMapper, invoke(METHOD_GET_MIN_MAX, null, ints, Integer.valueOf(three))));
		//
		Assertions.assertEquals(String.format("[%1$s]", ZERO), ObjectMapperUtil.writeValueAsString(objectMapper,
				invoke(METHOD_GET_MIN_MAX, null, ints, Integer.valueOf(ZERO))));
		//
		Assertions.assertEquals(String.format("[%1$s,%2$s]", ONE, three), ObjectMapperUtil.writeValueAsString(
				objectMapper, invoke(METHOD_GET_MIN_MAX, null, new int[] { ONE, two }, Integer.valueOf(three))));
		//
		Assertions.assertEquals(String.format("[%1$s,%2$s]", ONE, two), ObjectMapperUtil.writeValueAsString(
				objectMapper, invoke(METHOD_GET_MIN_MAX, null, new int[] { ONE, two }, Integer.valueOf(ONE))));
		//
	}

	@Test
	void testThenAcceptAsync() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(null,
				invoke(METHOD_THEN_ACCEPT_ASYNC, null, Narcissus.allocateInstance(CompletableFuture.class), null));
		//
	}

	@Test
	void testStrokeWithNumberImageSupplier() throws Throwable {
		//
		final Class<?> clz = Util
				.forName("org.springframework.context.support.JapanDictGui$StrokeWithNumberImageSupplier");
		//
		final Method[] ms = Util.getDeclaredMethods(clz);
		//
		Method m = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		Object object = null;
		//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
				// null
				//
			os = toArray(Collections.nCopies(m.getParameterCount(), null));
			//
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				Assertions.assertNull(
						Narcissus.invokeMethod(
								object = ObjectUtils.getIfNull(object, () -> Narcissus.allocateInstance(clz)), m, os),
						toString);
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (isInterface(parameterType = ArrayUtils.get(parameterTypes, j))) {
					//
					Util.add(collection, Reflection.newProxy(parameterType, ih = ObjectUtils.getIfNull(ih, IH::new)));
					//
				} // if
					//
			} // for
				//
			os = toArray(collection);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				Assertions.assertNull(
						Narcissus.invokeMethod(
								object = ObjectUtils.getIfNull(object, () -> Narcissus.allocateInstance(clz)), m, os),
						toString);
				//
			} // if
				//
		} // for
			//
	}

	@Test
	void testSetStrokeImageAndStrokeWithNumberImage() throws IllegalAccessException, InvocationTargetException {
		//
		final DefaultTableModel dtm = new DefaultTableModel(new Object[] { null }, ONE);
		//
		Assertions.assertNull(invoke(METHOD_SET_STROKE_IMAGE_AND_STROKE_WITH_NUMBER_IMAGE, null, dtm, japanDictEntry));
		//
		Util.addRow(dtm, new Object[] { japanDictEntry });
		//
		FieldUtils.writeDeclaredField(japanDictEntry, "id", "", true);
		//
		Assertions.assertNull(invoke(METHOD_SET_STROKE_IMAGE_AND_STROKE_WITH_NUMBER_IMAGE, null, dtm,
				Narcissus.allocateInstance(CLASS_JAPAN_DICT_ENTRY)));
		//
	}

	@Test
	void testJapanDictEntrySupplier() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.JapanDictGui$JapanDictEntrySupplier");
		//
		final Method[] ms = Util.getDeclaredMethods(clz);
		//
		Method m = null;
		//
		final int type = BufferedImage.TYPE_3BYTE_BGR;
		//
		final BufferedImage bi = new BufferedImage(2, 2, type);
		//
		final Color red = Color.RED;
		//
		if (red != null) {
			//
			bi.setRGB(1, 1, red.getRGB());
			//
		} // if
			//
		int[] firstPixelColor = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if (Boolean.logicalAnd(Objects.equals(Util.getName(m = ArrayUtils.get(ms, i)), "getFirstPixelColor"),
					Arrays.equals(Util.getParameterTypes(m),
							new Class<?>[] { BufferedImage.class, Integer.TYPE, byte[].class }))) {
				//
				firstPixelColor = Util.cast(int[].class,
						Narcissus.invokeStaticMethod(m, bi, type,
								Util.cast(byte[].class, Narcissus.invokeStaticMethod(
										JapanDictGui.class.getDeclaredMethod("getData", DataBufferByte.class),
										Narcissus.invokeStaticMethod(
												JapanDictGui.class.getDeclaredMethod("getDataBuffer", Raster.class),
												bi.getRaster())))));
				//
			} // if
				//
		} // if
			//
		Object[] os = null;
		//
		String toString = null;
		//
		Object object = null, result = null;
		//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		int parameterCount = 0;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
				// null
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(ArrayUtils.get(parameterTypes, j), Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
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
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				result = Narcissus.invokeMethod(
						object = ObjectUtils.getIfNull(object, () -> Narcissus.allocateInstance(clz)), m, os);
				//
				if (Boolean.logicalAnd(Objects.equals(Util.getName(m), "get"), parameterCount == 0)) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} // if
				//
				// non-null
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (isInterface(parameterType = ArrayUtils.get(parameterTypes, j))) {
					//
					Util.add(collection, Reflection.newProxy(parameterType, ih = ObjectUtils.getIfNull(ih, IH::new)));
					//
				} else if (Objects.equals(parameterType, int[].class)) {
					//
					Util.add(collection, new int[] {});
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
			} // for
				//
			os = toArray(collection);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				result = Narcissus.invokeMethod(
						object = ObjectUtils.getIfNull(object, () -> Narcissus.allocateInstance(clz)), m, os);
				//
				if (Boolean.logicalAnd(Objects.equals(Util.getName(m), "get"), parameterCount == 0)) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} // if
				//
				// org.springframework.context.support.JapanDictGui.JapanDictEntrySupplier$chopImage(java.awt.image.BufferedImage,int[])
				//
			if (Boolean.logicalAnd(Objects.equals(Util.getName(m), "chopImage"),
					Arrays.equals(parameterTypes, new Class<?>[] { BufferedImage.class, int[].class }))) {
				//
				Assertions.assertSame(bi, Narcissus.invokeStaticMethod(m, bi, firstPixelColor));
				//
			} else if (Boolean.logicalAnd(Objects.equals(Util.getName(m), "getPitchAccents"),
					Arrays.equals(parameterTypes, new Class<?>[] { Iterable.class }))) {// org.springframework.context.support.JapanDictGui.JapanDictEntrySupplier$getPitchAccents(java.lang.Iterable)
				//
				final Builder builder = JsonMapper.builder();
				//
				if (builder != null) {
					//
					builder.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
					//
				} // if
					//
				final ObjectMapper om = builder != null ? builder.build() : null;
				//
				if (om != null) {
					//
					om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
					//
				} // if
					//
				Assertions.assertEquals("[{\"image\":null,\"type\":null}]", ObjectMapperUtil.writeValueAsString(om,
						Narcissus.invokeStaticMethod(m, Collections.singleton(null))));
				//
			} // if
				//
		} // for
			//
	}

	@Test
	void testCopyField() throws Throwable {
		//
		final Field field = testAndApply(x -> !IterableUtils.isEmpty(x),
				testAndApply(Objects::nonNull, Util.getClass(japanDictEntry), FieldUtils::getAllFieldsList, null),
				x -> IterableUtils.get(x, ZERO), null);
		//
		Assertions.assertNull(invoke(METHOD_COPY_FIELD, null, japanDictEntry, japanDictEntry, Util.getName(field)));
		//
	}

	@Test
	void testPitchAccent() throws Throwable {
		//
		final Method[] ms = Util.getDeclaredMethods(CLASS_PITCH_ACCENT);
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
				// null
				//
			os = toArray(Collections.nCopies(m.getParameterCount(), null));
			//
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(pitchAccent = ObjectUtils.getIfNull(pitchAccent,
						() -> Narcissus.allocateInstance(CLASS_PITCH_ACCENT)), m, os), toString);
				//
			} // if
				//
				// non-null
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				Util.add(collection, Narcissus.allocateInstance(ArrayUtils.get(parameterTypes, j)));
				//
			} // for
				//
			os = toArray(collection);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(pitchAccent = ObjectUtils.getIfNull(pitchAccent,
						() -> Narcissus.allocateInstance(CLASS_PITCH_ACCENT)), m, os), toString);
				//
			} // if
				//
		} // for
			//
	}

	@Test
	void testGetLinkMultimap() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(ImmutableMultimap.of(),
				invoke(METHOD_GET_LINK_MULTI_MAP_ELEMENT, null, null, Collections.singleton(null)));
		//
		Assertions.assertNull(invoke(METHOD_GET_LINK_MULTI_MAP_STRING, null, null, Collections.singleton(null)));
		//
	}

	@Test
	void testLink() throws Throwable {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.JapanDictGui$Link");
		//
		final Method[] ms = Util.getDeclaredMethods(clz);
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		Object link = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
				// null
				//
			os = toArray(Collections.nCopies(m.getParameterCount(), null));
			//
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				Assertions.assertNull(
						Narcissus.invokeMethod(
								link = ObjectUtils.getIfNull(link, () -> Narcissus.allocateInstance(clz)), m, os),
						toString);
				//
			} // if
				//
				// non-null
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				Util.add(collection, Narcissus.allocateInstance(ArrayUtils.get(parameterTypes, j)));
				//
			} // for
				//
			os = toArray(collection);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				Assertions.assertNull(
						Narcissus.invokeMethod(
								link = ObjectUtils.getIfNull(link, () -> Narcissus.allocateInstance(clz)), m, os),
						toString);
				//
			} // if
				//
		} // for
			//
	}

	@Test
	void testTestAndRunThrows() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_RUN_THROWS, null, Boolean.TRUE, null));
		//
	}

	@Test
	void testCreateStringPDRectangleEntryListCellRenderer() throws Throwable {
		//
		final Object object = Narcissus.invokeStaticMethod(METHOD_CREATE_STRING_PD_RECTANGLE_ENTRY_LIST_CELL_RENDERER,
				(Object) null);
		//
		final Iterable<Method> ms = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.getDeclaredMethods(Util.getClass(object)), Arrays::stream, null),
				m -> Boolean.logicalAnd(Objects.equals(Util.getName(m), "getListCellRendererComponent"), Arrays.equals(
						Util.getParameterTypes(m),
						new Class<?>[] { JList.class, Object.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE }))));
		//
		final Method method = testAndApply(x -> IterableUtils.size(x) == 1, ms, x -> IterableUtils.get(x, 0), null);
		//
		Assertions.assertNotNull(method);
		//
		Assertions.assertNull(Narcissus.invokeMethod(object, method, null, null, 0, false, false));
		//
		Assertions.assertNotNull(Narcissus.invokeMethod(object, method, null, Pair.of(null, null), 0, false, false));
		//
	}

	@Test
	void testSetCbmPDRectangleSelectedItem() throws IllegalAccessException, InvocationTargetException {
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "cbmPDRectangle",
					new DefaultComboBoxModel<>(new Object[] { null, "" }), true);
			//
		} // if
			//
		Assertions.assertNull(invoke(METHOD_SET_CBM_PD_RECTANGLE_SELECTED_ITEM, null, instance, null));
		//
	}

}