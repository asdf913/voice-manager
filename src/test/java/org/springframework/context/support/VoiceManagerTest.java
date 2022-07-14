package org.springframework.context.support;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Stream;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Range;
import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;

import domain.Voice;
import domain.Voice.Yomi;
import mapper.VoiceMapper;
import net.miginfocom.swing.MigLayout;

class VoiceManagerTest {

	private static Class<?> CLASS_OBJECT_MAP, CLASS_IH = null;

	private static Method METHOD_INIT, METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_SET_CONTENTS, METHOD_GET_FILE_EXTENSION,
			METHOD_DIGEST, METHOD_GET_MAPPER, METHOD_INSERT_OR_UPDATE, METHOD_SET_ENABLED, METHOD_TEST_AND_APPLY4,
			METHOD_TEST_AND_APPLY5, METHOD_CAST, METHOD_INT_VALUE, METHOD_GET_PROPERTY, METHOD_SET_VARIABLE,
			METHOD_PARSE_EXPRESSION, METHOD_GET_VALUE, METHOD_GET_TEXT, METHOD_GET_SOURCE, METHOD_EXPORT, METHOD_MAP,
			METHOD_MAX, METHOD_OR_ELSE, METHOD_FOR_EACH, METHOD_CREATE_WORK_BOOK, METHOD_CREATE_VOICE,
			METHOD_GET_MESSAGE, METHOD_INVOKE, METHOD_ANNOTATION_TYPE, METHOD_GET_NAME, METHOD_FIND_FIRST,
			METHOD_GET_DECLARED_METHODS, METHOD_FOR_NAME, METHOD_FILTER, METHOD_SET_TEXT, METHOD_GET_PREFERRED_WIDTH,
			METHOD_IMPORT_VOICE3, METHOD_IMPORT_VOICE4, METHOD_ERROR_OR_PRINT_LN, METHOD_ADD,
			METHOD_CREATE_IMPORT_FILE_TEMPLATE_BYTE_ARRAY, METHOD_GET_DECLARED_ANNOTATIONS, METHOD_CREATE_CELL,
			METHOD_SET_CELL_VALUE, METHOD_ANY_MATCH, METHOD_COLLECT, METHOD_NAME, METHOD_GET_SELECTED_ITEM,
			METHOD_WRITE, METHOD_MATCHER, METHOD_SET_VALUE, METHOD_SET_STRING, METHOD_SET_TOOL_TIP_TEXT, METHOD_FORMAT,
			METHOD_CONTAINS_KEY, METHOD_VALUE_OF, METHOD_GET_CLASS, METHOD_CREATE_RANGE, METHOD_GET_PROVIDER_NAME,
			METHOD_WRITE_VOICE_TO_FILE = null;

	@BeforeAll
	private static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = VoiceManager.class;
		//
		(METHOD_INIT = clz.getDeclaredMethod("init")).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
		//
		(METHOD_GET_FILE_EXTENSION = clz.getDeclaredMethod("getFileExtension", ContentInfo.class)).setAccessible(true);
		//
		(METHOD_DIGEST = clz.getDeclaredMethod("digest", MessageDigest.class, byte[].class)).setAccessible(true);
		//
		(METHOD_GET_MAPPER = clz.getDeclaredMethod("getMapper", Configuration.class, Class.class, SqlSession.class))
				.setAccessible(true);
		//
		(METHOD_INSERT_OR_UPDATE = clz.getDeclaredMethod("insertOrUpdate", VoiceMapper.class, Voice.class))
				.setAccessible(true);
		//
		(METHOD_SET_ENABLED = clz.getDeclaredMethod("setEnabled", Component.class, Boolean.TYPE)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				BiFunction.class, BiFunction.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_INT_VALUE = clz.getDeclaredMethod("intValue", Number.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_PROPERTY = clz.getDeclaredMethod("getProperty", PropertyResolver.class, String.class))
				.setAccessible(true);
		//
		(METHOD_SET_VARIABLE = clz.getDeclaredMethod("setVariable", EvaluationContext.class, String.class,
				Object.class)).setAccessible(true);
		//
		(METHOD_PARSE_EXPRESSION = clz.getDeclaredMethod("parseExpression", ExpressionParser.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Expression.class, EvaluationContext.class))
				.setAccessible(true);
		//
		(METHOD_GET_TEXT = clz.getDeclaredMethod("getText", JTextComponent.class)).setAccessible(true);
		//
		(METHOD_GET_SOURCE = clz.getDeclaredMethod("getSource", EventObject.class)).setAccessible(true);
		//
		(METHOD_EXPORT = clz.getDeclaredMethod("export", List.class, Map.class, String.class, String.class,
				JProgressBar.class)).setAccessible(true);
		//
		(METHOD_MAP = clz.getDeclaredMethod("map", Stream.class, Function.class)).setAccessible(true);
		//
		(METHOD_MAX = clz.getDeclaredMethod("max", Stream.class, Comparator.class)).setAccessible(true);
		//
		(METHOD_OR_ELSE = clz.getDeclaredMethod("orElse", Optional.class, Object.class)).setAccessible(true);
		//
		(METHOD_FOR_EACH = clz.getDeclaredMethod("forEach", Stream.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_CREATE_WORK_BOOK = clz.getDeclaredMethod("createWorkbook", List.class)).setAccessible(true);
		//
		(METHOD_CREATE_VOICE = clz.getDeclaredMethod("createVoice", VoiceManager.class)).setAccessible(true);
		//
		(METHOD_GET_MESSAGE = clz.getDeclaredMethod("getMessage", Throwable.class)).setAccessible(true);
		//
		(METHOD_INVOKE = clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_ANNOTATION_TYPE = clz.getDeclaredMethod("annotationType", Annotation.class)).setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_FIND_FIRST = clz.getDeclaredMethod("findFirst", Stream.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_METHODS = clz.getDeclaredMethod("getDeclaredMethods", Class.class)).setAccessible(true);
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_SET_TEXT = clz.getDeclaredMethod("setText", JTextComponent.class, String.class)).setAccessible(true);
		//
		(METHOD_GET_PREFERRED_WIDTH = clz.getDeclaredMethod("getPreferredWidth", Component.class)).setAccessible(true);
		//
		(METHOD_IMPORT_VOICE3 = clz.getDeclaredMethod("importVoice",
				CLASS_OBJECT_MAP = Class.forName("org.springframework.context.support.VoiceManager$ObjectMap"),
				Consumer.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_IMPORT_VOICE4 = clz.getDeclaredMethod("importVoice", Sheet.class, CLASS_OBJECT_MAP, Consumer.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_ERROR_OR_PRINT_LN = clz.getDeclaredMethod("errorOrPrintln", Logger.class, PrintStream.class,
				String.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_CREATE_IMPORT_FILE_TEMPLATE_BYTE_ARRAY = clz.getDeclaredMethod("createImportFileTemplateByteArray"))
				.setAccessible(true);
		//
		(METHOD_GET_DECLARED_ANNOTATIONS = clz.getDeclaredMethod("getDeclaredAnnotations", AnnotatedElement.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_CELL = clz.getDeclaredMethod("createCell", Row.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_SET_CELL_VALUE = clz.getDeclaredMethod("setCellValue", Cell.class, String.class)).setAccessible(true);
		//
		(METHOD_ANY_MATCH = clz.getDeclaredMethod("anyMatch", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_COLLECT = clz.getDeclaredMethod("collect", Stream.class, Collector.class)).setAccessible(true);
		//
		(METHOD_NAME = clz.getDeclaredMethod("name", Enum.class)).setAccessible(true);
		//
		(METHOD_GET_SELECTED_ITEM = clz.getDeclaredMethod("getSelectedItem", ComboBoxModel.class)).setAccessible(true);
		//
		(METHOD_WRITE = clz.getDeclaredMethod("write", Workbook.class, OutputStream.class)).setAccessible(true);
		//
		(METHOD_MATCHER = clz.getDeclaredMethod("matcher", Pattern.class, CharSequence.class)).setAccessible(true);
		//
		(METHOD_SET_VALUE = clz.getDeclaredMethod("setValue", JProgressBar.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_SET_STRING = clz.getDeclaredMethod("setString", JProgressBar.class, String.class)).setAccessible(true);
		//
		(METHOD_SET_TOOL_TIP_TEXT = clz.getDeclaredMethod("setToolTipText", JComponent.class, String.class))
				.setAccessible(true);
		//
		(METHOD_FORMAT = clz.getDeclaredMethod("format", NumberFormat.class, Double.TYPE)).setAccessible(true);
		//
		(METHOD_CONTAINS_KEY = clz.getDeclaredMethod("containsKey", Map.class, Object.class)).setAccessible(true);
		//
		(METHOD_VALUE_OF = clz.getDeclaredMethod("valueOf", String.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_CREATE_RANGE = clz.getDeclaredMethod("createRange", Integer.class, Integer.class)).setAccessible(true);
		//
		(METHOD_GET_PROVIDER_NAME = clz.getDeclaredMethod("getProviderName", Provider.class)).setAccessible(true);
		//
		(METHOD_WRITE_VOICE_TO_FILE = clz.getDeclaredMethod("writeVoiceToFile", File.class)).setAccessible(true);
		//
		CLASS_IH = Class.forName("org.springframework.context.support.VoiceManager$IH");
		//
	}

	private class IH implements InvocationHandler {

		private Voice voice = null;

		private Set<Entry<?, ?>> entrySet = null;

		private String toString, stringCellValue, providerName = null;

		private Configuration configuration = null;

		private SqlSession sqlSession = null;

		private Expression expression = null;

		private Object value, max, selectedItem = null;

		private Iterator<Row> rows = null;

		private Iterator<Cell> cells = null;

		private Boolean anyMatch = null;

		private String[] voiceIds = null;

		private Map<Object, String> properties = null;

		public Map<Object, String> getProperties() {
			if (properties == null) {
				properties = new LinkedHashMap<>();
			}
			return properties;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (ReflectionUtils.isToStringMethod(method)) {
				//
				return toString;
				//
			} // if
				//
			final Class<?> returnType = method != null ? method.getReturnType() : null;
			//
			if (Objects.equals(Void.TYPE, returnType)) {
				//
				return null;
				//
			} // if
				//
			if (proxy instanceof Iterable) {
				//
				if (proxy instanceof Sheet) {
					//
					return rows;
					//
				} else if (proxy instanceof Row) {
					//
					return cells;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof VoiceMapper) {
				//
				if (Objects.equals(methodName, "searchByTextAndRomaji")) {
					//
					return voice;
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return entrySet;
					//
				} // if
					//
			} else if (proxy instanceof PropertyResolver) {
				//
				if (Objects.equals(methodName, "getProperty") && args != null && args.length > 0) {
					//
					return MapUtils.getObject(getProperties(), args[0]);
					//
				} // if
					//
			} else if (proxy instanceof SqlSessionFactory) {
				//
				if (Objects.equals(methodName, "getConfiguration")) {
					//
					return configuration;
					//
				} else if (Objects.equals(methodName, "openSession")) {
					//
					return sqlSession;
					//
				} // if
					//
			} else if (proxy instanceof ExpressionParser) {
				//
				if (Objects.equals(methodName, "parseExpression")) {
					//
					return expression;
					//
				} // if
					//
			} else if (proxy instanceof Expression) {
				//
				if (Objects.equals(methodName, "getValue")) {
					//
					return value;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(Stream.class, returnType)) {
					//
					return proxy;
					//
				} else if (Objects.equals(methodName, "max")) {
					//
					return max;
					//
				} else if (Objects.equals(methodName, "collect")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "anyMatch")) {
					//
					return anyMatch;
					//
				} // if
					//
			} else if (proxy instanceof Cell) {
				//
				if (Objects.equals(methodName, "getStringCellValue")) {
					//
					return stringCellValue;
					//
				} // if
					//
			} else if (proxy instanceof ComboBoxModel) {
				//
				if (Objects.equals(methodName, "getSelectedItem")) {
					//
					return selectedItem;
					//
				} // if
					//
			} else if (proxy instanceof SpeechApi) {
				//
				if (Objects.equals(methodName, "getVoiceIds")) {
					//
					return voiceIds;
					//
				} // if
					//
			} else if (proxy instanceof Provider) {
				//
				if (Objects.equals(methodName, "getProviderName")) {
					//
					return providerName;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManager instance = null;

	private IH ih = null;

	private SqlSessionFactory sqlSessionFactory = null;

	private Stream<?> stream = null;

	private SpeechApi speechApi = null;

	@BeforeEach
	private void beforeEach() throws ReflectiveOperationException {
		//
		final Constructor<VoiceManager> constructor = VoiceManager.class.getDeclaredConstructor();
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		instance = constructor != null && !GraphicsEnvironment.isHeadless() ? constructor.newInstance() : null;
		//
		sqlSessionFactory = Reflection.newProxy(SqlSessionFactory.class, ih = new IH());
		//
		stream = Reflection.newProxy(Stream.class, ih);
		//
		speechApi = Reflection.newProxy(SpeechApi.class, ih);
		//
	}

	@Test
	void testSetOutputFolderFileNameExpressions() throws NoSuchFieldException, IllegalAccessException {
		//
		final Field outputFolderFileNameExpressions = VoiceManager.class
				.getDeclaredField("outputFolderFileNameExpressions");
		//
		if (outputFolderFileNameExpressions != null) {
			outputFolderFileNameExpressions.setAccessible(true);
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setOutputFolderFileNameExpressions(null));
		//
		Assertions.assertNull(get(outputFolderFileNameExpressions, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setOutputFolderFileNameExpressions(""));
		//
		Assertions.assertNull(get(outputFolderFileNameExpressions, instance));
		//
		final Map<?, ?> emptyMap = Collections.emptyMap();
		//
		final Map<?, ?> map = Reflection.newProxy(Map.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> instance.setOutputFolderFileNameExpressions(map));
		//
		Assertions.assertNull(get(outputFolderFileNameExpressions, instance));
		//
		ih.entrySet = Collections.singleton(null);
		//
		Assertions.assertDoesNotThrow(() -> instance.setOutputFolderFileNameExpressions(map));
		//
		Assertions.assertEquals(emptyMap, get(outputFolderFileNameExpressions, instance));
		//
		set(outputFolderFileNameExpressions, instance, null);
		//
		Assertions.assertDoesNotThrow(
				() -> instance.setOutputFolderFileNameExpressions(Collections.singletonMap(null, null)));
		//
		Assertions.assertEquals(Collections.singletonMap(null, null), get(outputFolderFileNameExpressions, instance));
		//
		set(outputFolderFileNameExpressions, instance, null);
		//
		Assertions.assertDoesNotThrow(
				() -> instance.setOutputFolderFileNameExpressions(Collections.singletonMap(null, null)));
		//
		Assertions.assertEquals(Collections.singletonMap(null, null), get(outputFolderFileNameExpressions, instance));
		//
		set(outputFolderFileNameExpressions, instance, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.setOutputFolderFileNameExpressions("{}"));
		//
		Assertions.assertEquals(emptyMap, get(outputFolderFileNameExpressions, instance));
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance.setOutputFolderFileNameExpressions("1"));
		//
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void set(final Field field, final Object instance, final Object value)
			throws IllegalAccessException {
		if (field != null) {
			field.set(instance, value);
		}
	}

	@Test
	void testActionPerformed() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent("", 0, null)));
		//
		final AbstractButton btnConvertToRomaji = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnConvertToRomaji", btnConvertToRomaji, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnConvertToRomaji, 0, null)));
		//
		final AbstractButton btnConvertToKatakana = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnConvertToKatakana", btnConvertToKatakana, true);
			//
		} // if
			//
		ActionEvent actionEventBtnConvertToKatakana = new ActionEvent(btnConvertToKatakana, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnConvertToKatakana));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfHiragana", new JTextField(), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnConvertToKatakana));
		//
		final AbstractButton btnCopyRomaji = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCopyRomaji", btnCopyRomaji, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnCopyRomaji, 0, null)));
		//
		final AbstractButton btnExport = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnExport", btnExport, true);
			//
		} // if
			//
		final ActionEvent actionEventBtnExport = new ActionEvent(btnExport, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExport));
		//
		instance.setSqlSessionFactory(sqlSessionFactory);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnExport));
		//
		final AbstractButton btnSpeak = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnSpeak", btnSpeak, true);
			//
		} // if
			//
		final ActionEvent actionEventBtnSpeak = new ActionEvent(btnSpeak, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeak));
		//
		if (instance != null) {
			//
			instance.setSpeechApi(speechApi);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeak));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jsSpeechVolume", new JSlider(), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeak));
		//
		final JTextComponent tfSpeechRate = new JTextField();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfSpeechRate", tfSpeechRate, true);
			//
		} // if
			//
		tfSpeechRate.setText("MAX_VALUE");
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnSpeak));
		//
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent actionEvent) {
		if (instance != null) {
			instance.actionPerformed(actionEvent);
		} // if
	}

	@Test
	void testInit() {
		//
		if (instance != null) {
			//
			Assertions.assertThrows(IllegalArgumentException.class, () -> init());
			//
			instance.setLayout(new MigLayout());
			//
			Assertions.assertDoesNotThrow(() -> init());
			//
			instance.setSpeechApi(speechApi);
			//
			ih.voiceIds = new String[] {};
			//
			Assertions.assertDoesNotThrow(() -> init());
			//
			ih.voiceIds = new String[] { null };
			//
			Assertions.assertDoesNotThrow(() -> init());
			//
			ih.voiceIds = new String[] { null, null };
			//
			Assertions.assertThrows(IllegalStateException.class, () -> init());
			//
			ih.voiceIds = new String[] { null };
			//
			instance.setEnvironment(Reflection.newProxy(Environment.class, ih));
			//
			ih.getProperties().put("org.springframework.context.support.VoiceManager.speechVolume", "100");
			//
			Assertions.assertDoesNotThrow(() -> init());
			//
		} // if
			//
	}

	private void init() throws Throwable {
		try {
			METHOD_INIT.invoke(instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSystemClipboard() throws Throwable {
		//
		Assertions.assertNull(getSystemClipboard(null));
		//
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SYSTEM_CLIP_BOARD.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Clipboard) {
				return (Clipboard) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetContents() {
		//
		Assertions.assertDoesNotThrow(() -> setContents(null, null, null));
		//
	}

	private static void setContents(final Clipboard instance, final Transferable contents, final ClipboardOwner owner)
			throws Throwable {
		try {
			METHOD_SET_CONTENTS.invoke(null, instance, contents, owner);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFileExtension() throws Throwable {
		//
		Assertions.assertNull(getFileExtension(null));
		//
		Assertions.assertNull(getFileExtension(new ContentInfo(null, null, "", false)));
		//
		Assertions.assertEquals("mp3", getFileExtension(new ContentInfo(null, null, "MPEG ADTS, layer III.", false)));
		//
		final String name = "wav";
		//
		Assertions.assertEquals(name, getFileExtension(new ContentInfo(name, null, null, false)));
		//
		Assertions.assertEquals("aac", getFileExtension(new ContentInfo(null, "audio/x-hx-aac-adts", null, false)));
		//
	}

	private static String getFileExtension(final ContentInfo ci) throws Throwable {
		try {
			final Object obj = METHOD_GET_FILE_EXTENSION.invoke(null, ci);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testDigest() throws Throwable {
		//
		Assertions.assertNull(digest(null, null));
		//
		final MessageDigest md = MessageDigest.getInstance("SHA-512");
		//
		Assertions.assertNull(digest(md, null));
		//
		Assertions.assertEquals(
				"cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e",
				Hex.encodeHexString(digest(md, new byte[] {})));
		//
	}

	private static byte[] digest(final MessageDigest instance, final byte[] input) throws Throwable {
		try {
			final Object obj = METHOD_DIGEST.invoke(null, instance, input);
			if (obj == null) {
				return null;
			} else if (obj instanceof byte[]) {
				return (byte[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMapper() throws Throwable {
		//
		Assertions.assertNull(getMapper(null, null, null));
		//
		Assertions.assertThrows(BindingException.class, () -> getMapper(new Configuration(), null, null));
		//
	}

	private static <T> T getMapper(final Configuration instance, final Class<T> type, final SqlSession sqlSession)
			throws Throwable {
		try {
			return (T) METHOD_GET_MAPPER.invoke(null, instance, type, sqlSession);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testInsertOrUpdate() {
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(null, null));
		//
		final VoiceMapper voiceMapper = Reflection.newProxy(VoiceMapper.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(voiceMapper, null));
		//
		final Voice voice = new Voice();
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(voiceMapper, voice));
		//
		ih.voice = new Voice();
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(voiceMapper, null));
		//
		Assertions.assertDoesNotThrow(() -> insertOrUpdate(voiceMapper, voice));
		//
	}

	private static void insertOrUpdate(final VoiceMapper instance, final Voice voice) throws Throwable {
		try {
			METHOD_INSERT_OR_UPDATE.invoke(null, instance, voice);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetEnabled() {
		//
		Assertions.assertDoesNotThrow((() -> setEnabled(null, false)));
		//
	}

	private static void setEnabled(final Component instance, final boolean b) throws Throwable {
		try {
			METHOD_SET_ENABLED.invoke(null, instance, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, x -> x));
		//
		Assertions.assertNull(testAndApply(null, null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, x -> x));
		//
		Assertions.assertNull(testAndApply((a, b) -> false, null, null, null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY4.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY5.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIntValue() throws Throwable {
		//
		final int zero = 0;
		//
		Assertions.assertEquals(zero, intValue(null, zero));
		//
	}

	private static int intValue(final Number instance, final int defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_INT_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetProperty() throws Throwable {
		//
		Assertions.assertNull(getProperty(Reflection.newProxy(PropertyResolver.class, ih), null));
		//
	}

	private static String getProperty(final PropertyResolver instance, final String key) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROPERTY.invoke(null, instance, key);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetVariable() {
		//
		Assertions.assertDoesNotThrow(() -> setVariable(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> setVariable(Reflection.newProxy(EvaluationContext.class, ih), null, null));
		//
	}

	private static void setVariable(final EvaluationContext instance, final String name, final Object value)
			throws Throwable {
		try {
			METHOD_SET_VARIABLE.invoke(null, instance, name, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testParseExpression() throws Throwable {
		//
		Assertions.assertNull(parseExpression(null, null));
		//
		Assertions.assertNull(parseExpression(Reflection.newProxy(ExpressionParser.class, ih), null));
		//
	}

	private static Expression parseExpression(final ExpressionParser instance, final String expressionString)
			throws Throwable {
		try {
			final Object obj = METHOD_PARSE_EXPRESSION.invoke(null, instance, expressionString);
			if (obj == null) {
				return null;
			} else if (obj instanceof Expression) {
				return (Expression) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		Assertions.assertNull(getValue(null, null));
		//
		Assertions.assertNull(getValue(Reflection.newProxy(Expression.class, ih), null));
		//
	}

	private static Object getValue(final Expression instance, final EvaluationContext evaluationContext)
			throws Throwable {
		try {
			return METHOD_GET_VALUE.invoke(null, instance, evaluationContext);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetText() throws Throwable {
		//
		Assertions.assertEquals("", getText(new JTextField()));
		//
	}

	private static String getText(final JTextComponent instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TEXT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSource() throws Throwable {
		//
		Assertions.assertNull(getSource(null));
		//
	}

	private static Object getSource(final EventObject instance) throws Throwable {
		try {
			return METHOD_GET_SOURCE.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExport() {
		//
		final JProgressBar progressBar = new JProgressBar();
		//
		Assertions.assertDoesNotThrow(() -> export(null, null, null, null, progressBar));
		//
		//
		Assertions.assertDoesNotThrow(() -> export(Collections.singletonList(null), null, null, null, progressBar));
		//
		final Voice voice = new Voice();
		//
		final List<Voice> voices = Collections.singletonList(voice);
		//
		Assertions.assertDoesNotThrow(() -> export(voices, null, null, null, null));
		//
		voice.setFilePath("");
		//
		Assertions.assertDoesNotThrow(() -> export(voices, null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.emptyMap(), null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Reflection.newProxy(Map.class, ih), null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.singletonMap(null, null), null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.singletonMap("", null), null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.singletonMap("", ""), null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.singletonMap("", " "), null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> export(voices, Collections.singletonMap("", "true"), null, null, null));
		//
	}

	private static void export(final List<Voice> voices, final Map<String, String> outputFolderFileNameExpressions,
			final String voiceFolder, final String outputFolder, final JProgressBar progressBar) throws Throwable {
		try {
			METHOD_EXPORT.invoke(null, voices, outputFolderFileNameExpressions, voiceFolder, outputFolder, progressBar);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMap() throws Throwable {
		//
		Assertions.assertNull(map(null, null));
		//
		Assertions.assertSame(stream, map(stream, null));
		//
		Assertions.assertNull(map(Stream.empty(), null));
		//
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMax() throws Throwable {
		//
		Assertions.assertNull(max(null, null));
		//
		Assertions.assertNull(max(stream, null));
		//
		Assertions.assertNull(max(Stream.empty(), null));
		//
	}

	private static <T> Optional<T> max(final Stream<T> instance, final Comparator<? super T> comparator)
			throws Throwable {
		try {
			final Object obj = METHOD_MAX.invoke(null, instance, comparator);
			if (obj == null) {
				return null;
			} else if (obj instanceof Optional) {
				return (Optional) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOrElse() throws Throwable {
		//
		Assertions.assertNull(orElse(null, null));
		//
	}

	private static <T> T orElse(final Optional<T> instance, final T other) throws Throwable {
		try {
			return (T) METHOD_OR_ELSE.invoke(null, instance, other);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForEach() {
		//
		Assertions.assertDoesNotThrow(() -> forEach(null, null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Stream.empty(), null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Stream.empty(), x -> {
		}));
		//
		Assertions.assertDoesNotThrow(() -> forEach(stream, null));
		//
	}

	private static <T> void forEach(final Stream<T> instance, final Consumer<? super T> action) throws Throwable {
		try {
			METHOD_FOR_EACH.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateWorkbook() throws Throwable {
		//
		Assertions.assertNull(createWorkbook(Collections.singletonList(null)));
		//
		final Voice voice = new Voice();
		//
		voice.setFileLength(Long.valueOf(0));
		//
		voice.setCreateTs(new Date());
		//
		Assertions.assertNotNull(createWorkbook(Collections.nCopies(2, voice)));
		//
	}

	private static Workbook createWorkbook(final List<Voice> voices) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_WORK_BOOK.invoke(null, voices);
			if (obj == null) {
				return null;
			} else if (obj instanceof Workbook) {
				return (Workbook) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateVoice() throws Throwable {
		//
		Assertions.assertNull(createVoice(null));
		//
		Assertions.assertNotNull(createVoice(instance));
		//
	}

	private static Voice createVoice(final VoiceManager instance) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_VOICE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Voice) {
				return (Voice) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMessage() throws Throwable {
		//
		Assertions.assertNull(getMessage(null));
		//
		Assertions.assertNull(getMessage(new Throwable()));
		//
	}

	private static String getMessage(final Throwable instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_MESSAGE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testInvoke() throws Throwable {
		//
		Assertions.assertNull(invoke(null, null));
		//
	}

	private static Object invoke(final Method method, final Object instance, Object... args) throws Throwable {
		try {
			return METHOD_INVOKE.invoke(null, method, instance, args);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnnotationType() throws Throwable {
		//
		Assertions.assertNull(annotationType(null));
		//
	}

	private static Class<? extends Annotation> annotationType(final Annotation instance) throws Throwable {
		try {
			final Object obj = METHOD_ANNOTATION_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName(null));
		//
	}

	private static String getName(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFindFirst() throws Throwable {
		//
		Assertions.assertNull(findFirst(null));
		//
	}

	private static <T> Optional<T> findFirst(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_FIND_FIRST.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Optional) {
				return (Optional) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredMethods() throws Throwable {
		//
		Assertions.assertNull(getDeclaredMethods(null));
		//
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_METHODS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Method[]) {
				return (Method[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForName() throws Throwable {
		//
		Assertions.assertNull(forName(null));
		//
		Assertions.assertNull(forName(""));
		//
		Assertions.assertNull(forName(" "));
		//
		Assertions.assertNull(forName("A"));
		//
	}

	private static Class<?> forName(final String className) throws Throwable {
		try {
			final Object obj = METHOD_FOR_NAME.invoke(null, className);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(null, null));
		//
		Assertions.assertNull(filter(Stream.empty(), null));
		//
		Assertions.assertSame(stream, filter(stream, null));
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_FILTER.invoke(null, instance, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetText() {
		//
		Assertions.assertDoesNotThrow(() -> setText(new JTextField(), null));
		//
	}

	private static void setText(final JTextComponent instance, final String text) throws Throwable {
		try {
			METHOD_SET_TEXT.invoke(null, instance, text);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPreferredWidth() throws Throwable {
		//
		Assertions.assertNull(getPreferredWidth(null));
		//
	}

	private static Double getPreferredWidth(final Component c) throws Throwable {
		try {
			final Object obj = METHOD_GET_PREFERRED_WIDTH.invoke(null, c);
			if (obj == null) {
				return null;
			} else if (obj instanceof Double) {
				return (Double) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testImportVoice() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> importVoice(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(null, null, null, null));
		//
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor(VoiceManager.class)
				: null;
		//
		if (constructor != null) {
			constructor.setAccessible(true);
		} // if
			//
		final InvocationHandler ih = cast(InvocationHandler.class,
				constructor != null ? constructor.newInstance(instance) : null);
		//
		final Object objectMap = Reflection.newProxy(CLASS_OBJECT_MAP, ih);
		//
		Assertions.assertThrows(IllegalStateException.class, () -> importVoice(objectMap, null, null));
		//
		final Map<?, ?> objects = cast(Map.class, FieldUtils.readDeclaredField(ih, "objects", true));
		//
		if (objects != null) {
			//
			((Map) objects).put(File.class, new File("."));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, null, null));
		//
		if (objects != null) {
			//
			((Map) objects).put(File.class, new File("NON_EXISTS"));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, null, null));
		//
		if (objects != null) {
			//
			((Map) objects).put(File.class, new File("pom.xml"));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, x -> {
		}, null));
		//
		final File file = File.createTempFile(RandomStringUtils.randomAlphabetic(3), null);
		//
		if (file != null) {
			//
			file.deleteOnExit();
			//
		} // if
			//
		if (objects != null) {
			//
			((Map) objects).put(File.class, file);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> importVoice(objectMap, null, null));
		//
		final Sheet sheet = Reflection.newProxy(Sheet.class, this.ih);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(sheet, null, null, null));
		//
		final Row row = Reflection.newProxy(Row.class, this.ih);
		//
		this.ih.rows = Iterators.forArray(null, row);
		//
		Assertions.assertDoesNotThrow(() -> importVoice(sheet, null, null, null));
		//
		this.ih.rows = Iterators.forArray(null, row);
		//
		this.ih.cells = Iterators.forArray(null, Reflection.newProxy(Cell.class, this.ih));
		//
		Assertions.assertDoesNotThrow(() -> importVoice(sheet, null, null, null));
		//
	}

	private static void importVoice(final Object objectMap, final Consumer<String> errorMessageConsumer,
			final Consumer<Throwable> throwableConsumer) throws Throwable {
		try {
			METHOD_IMPORT_VOICE3.invoke(null, objectMap, errorMessageConsumer, throwableConsumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void importVoice(final Sheet sheet, final Object objectMap,
			final Consumer<String> errorMessageConsumer, final Consumer<Throwable> throwableConsumer) throws Throwable {
		try {
			METHOD_IMPORT_VOICE4.invoke(null, sheet, objectMap, errorMessageConsumer, throwableConsumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testErrorOrPrintln() throws IOException {
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintln(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintln(Reflection.newProxy(Logger.class, ih), null, null));
		//
		try (final OutputStream os = new ByteArrayOutputStream(); final PrintStream ps = new PrintStream(os)) {
			//
			Assertions.assertDoesNotThrow(() -> errorOrPrintln(null, ps, null));
			//
		} // try
			//
	}

	private static void errorOrPrintln(final Logger logger, final PrintStream ps, final String message)
			throws Throwable {
		try {
			METHOD_ERROR_OR_PRINT_LN.invoke(null, logger, ps, message);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAdd() {
		//
		Assertions.assertDoesNotThrow(() -> add(null, null));
		//
	}

	private static <E> void add(final Collection<E> items, final E item) throws Throwable {
		try {
			METHOD_ADD.invoke(null, items, item);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateImportFileTemplateByteArray() throws Throwable {
		//
		Assertions.assertNotNull(createImportFileTemplateByteArray());
		//
	}

	private static byte[] createImportFileTemplateByteArray() throws Throwable {
		try {
			final Object obj = METHOD_CREATE_IMPORT_FILE_TEMPLATE_BYTE_ARRAY.invoke(null);
			if (obj == null) {
				return null;
			} else if (obj instanceof byte[]) {
				return (byte[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredAnnotations() throws Throwable {
		//
		Assertions.assertNull(getDeclaredAnnotations(null));
		//
	}

	private static Annotation[] getDeclaredAnnotations(final AnnotatedElement instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_ANNOTATIONS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Annotation[]) {
				return (Annotation[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateCell() throws Throwable {
		//
		Assertions.assertNull(createCell(null, 0));
		//
	}

	private static Cell createCell(final Row instance, final int column) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_CELL.invoke(null, instance, column);
			if (obj == null) {
				return null;
			} else if (obj instanceof Cell) {
				return (Cell) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetCellValue() {
		//
		Assertions.assertDoesNotThrow(() -> setCellValue(null, null));
		//
	}

	private static void setCellValue(final Cell instance, final String value) throws Throwable {
		try {
			METHOD_SET_CELL_VALUE.invoke(null, instance, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnyMatch() throws Throwable {
		//
		Assertions.assertFalse(anyMatch(null, null));
		//
		Assertions.assertFalse(anyMatch(Stream.empty(), null));
		//
		Assertions.assertSame(ih.anyMatch = Boolean.FALSE, Boolean.valueOf(anyMatch(stream, null)));
		//
	}

	private static <T> boolean anyMatch(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_ANY_MATCH.invoke(null, instance, predicate);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCollect() throws Throwable {
		//
		Assertions.assertNull(collect(null, null));
		//
		Assertions.assertNull(collect(Stream.empty(), null));
		//
		Assertions.assertNull(collect(stream, null));
		//
	}

	private static <T, R, A> R collect(final Stream<T> instance, final Collector<? super T, A, R> collector)
			throws Throwable {
		try {
			return (R) METHOD_COLLECT.invoke(null, instance, collector);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testName() throws Throwable {
		//
		Assertions.assertNull(name(null));
		//
		Assertions.assertNotNull(name(Yomi.KUN_YOMI));
		//
	}

	private static String name(final Enum<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSelectedItem() throws Throwable {
		//
		Assertions.assertNull(getSelectedItem(Reflection.newProxy(ComboBoxModel.class, ih)));
		//
	}

	private static Object getSelectedItem(final ComboBoxModel<?> instance) throws Throwable {
		try {
			return METHOD_GET_SELECTED_ITEM.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testWrite() throws IOException {
		//
		Assertions.assertDoesNotThrow(() -> write(Reflection.newProxy(Workbook.class, ih), null));
		//
		try (final Workbook workbook = new XSSFWorkbook()) {
			//
			Assertions.assertDoesNotThrow(() -> write(workbook, null));
			//
		} // try
			//
	}

	private static void write(final Workbook instance, final OutputStream stream) throws Throwable {
		try {
			METHOD_WRITE.invoke(null, instance, stream);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMatcher() throws Throwable {
		//
		Assertions.assertNull(matcher(null, null));
		//
	}

	private static Matcher matcher(final Pattern pattern, final CharSequence input) throws Throwable {
		try {
			final Object obj = METHOD_MATCHER.invoke(null, pattern, input);
			if (obj == null) {
				return null;
			} else if (obj instanceof Matcher) {
				return (Matcher) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetValue() {
		//
		Assertions.assertDoesNotThrow(() -> setValue(new JProgressBar(), 0));
		//
	}

	private static void setValue(final JProgressBar instance, final int n) throws Throwable {
		try {
			METHOD_SET_VALUE.invoke(null, instance, n);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetString() {
		//
		Assertions.assertDoesNotThrow(() -> setString(new JProgressBar(), null));
		//
	}

	private static void setString(final JProgressBar instance, final String string) throws Throwable {
		try {
			METHOD_SET_STRING.invoke(null, instance, string);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetToolTipText() {
		//
		Assertions.assertDoesNotThrow(() -> setToolTipText(new JTextField(), null));
		//
	}

	private static void setToolTipText(final JComponent instance, final String toolTipText) throws Throwable {
		try {
			METHOD_SET_TOOL_TIP_TEXT.invoke(null, instance, toolTipText);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFormat() throws Throwable {
		//
		Assertions.assertEquals(null, format(null, 0d));
		//
	}

	private static String format(final NumberFormat instance, final double number) throws Throwable {
		try {
			final Object obj = METHOD_FORMAT.invoke(null, instance, number);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContainsKey() throws Throwable {
		//
		Assertions.assertFalse(containsKey(null, null));
		//
		Assertions.assertFalse(containsKey(Collections.emptyMap(), null));
		//
		Assertions.assertTrue(containsKey(Collections.singletonMap(null, null), null));
		//
	}

	private static boolean containsKey(final Map<?, ?> instance, final Object key) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS_KEY.invoke(null, instance, key);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testValueOf() throws Throwable {
		//
		Assertions.assertNull(valueOf(""));
		//
		Assertions.assertNull(valueOf(" "));
		//
	}

	private static Integer valueOf(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_VALUE_OF.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null));
		//
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testCreateRange() throws Throwable {
		//
		final Integer zero = Integer.valueOf(0);
		//
		Assertions.assertEquals(String.format("[%1$s..+∞)", zero), toString(createRange(zero, null)));
		//
		Assertions.assertEquals(String.format("(-∞..%1$s]", zero), toString(createRange(null, zero)));
		//
		final Integer one = Integer.valueOf(1);
		//
		Assertions.assertEquals(String.format("(%1$s..%2$s)", zero, one), toString(createRange(zero, one)));
		//
	}

	private static Range<Integer> createRange(final Integer minValue, final Integer maxValue) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_RANGE.invoke(null, minValue, maxValue);
			if (obj == null) {
				return null;
			} else if (obj instanceof Range) {
				return (Range) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetProviderName() throws Throwable {
		//
		Assertions.assertNull(getProviderName(null));
		//
		Assertions.assertNull(getProviderName(Reflection.newProxy(Provider.class, ih)));
		//
	}

	private static String getProviderName(final Provider instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROVIDER_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testWriteVoiceToFile() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> writeVoiceToFile(null));
		//
		if (instance != null) {
			//
			instance.setSpeechApi(speechApi);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> writeVoiceToFile(null));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jsSpeechVolume", new JSlider(), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> writeVoiceToFile(null));
		//
	}

	private void writeVoiceToFile(final File file) throws Throwable {
		try {
			METHOD_WRITE_VOICE_TO_FILE.invoke(instance, file);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIh() throws Throwable {
		//
		final Constructor<?> constructor = CLASS_IH != null ? CLASS_IH.getDeclaredConstructor(VoiceManager.class)
				: null;
		//
		if (constructor != null) {
			constructor.setAccessible(true);
		} // if
			//
		final InvocationHandler ih = cast(InvocationHandler.class,
				constructor != null ? constructor.newInstance(instance) : null);
		//
		if (ih != null) {
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(null, null, null));
			//
			// getObject
			//
			final Method methodGetObject = CLASS_OBJECT_MAP != null
					? CLASS_OBJECT_MAP.getDeclaredMethod("getObject", Class.class)
					: null;
			//
			final Object stringMap = Reflection.newProxy(CLASS_OBJECT_MAP, ih);
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(stringMap, methodGetObject, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(stringMap, methodGetObject, new Object[] {}));
			//
			// setObject
			//
			final Method methodSetObject = CLASS_OBJECT_MAP != null
					? CLASS_OBJECT_MAP.getDeclaredMethod("setObject", Class.class, Object.class)
					: null;
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(stringMap, methodSetObject, null));
			//
			Assertions.assertThrows(Throwable.class, () -> ih.invoke(stringMap, methodSetObject, new Object[] {}));
			//
			Assertions.assertNull(ih.invoke(stringMap, methodSetObject, new Object[] { null, null }));
			//
		} // if
			//
	}

	@Test
	void testImportTask() throws Throwable {
		//
		final Class<?> clz = forName("org.springframework.context.support.VoiceManager$ImportTask");
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final Runnable runnable = cast(Runnable.class, constructor != null ? constructor.newInstance() : null);
		//
		Assertions.assertDoesNotThrow(() -> run(runnable));
		//
		FieldUtils.writeDeclaredField(runnable, "counter", Integer.valueOf(0), true);
		//
		Assertions.assertDoesNotThrow(() -> run(runnable));
		//
	}

	private static void run(final Runnable instance) {
		if (instance != null) {
			instance.run();
		}
	}

}