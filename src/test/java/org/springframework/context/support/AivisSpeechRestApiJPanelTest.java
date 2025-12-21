package org.springframework.context.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.ItemSelectable;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Vector;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.classfile.MethodUtil;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ATHROW;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldInstruction;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.IFNONNULL;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstructionUtil;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.LDCUtil;
import org.apache.bcel.generic.NEW;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.TypeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.htmlunit.http.HttpStatus;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Predicates;
import com.google.common.net.HostAndPort;
import com.google.common.reflect.Reflection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.j256.simplemagic.ContentType;
import com.sun.jna.platform.win32.Guid.GUID;
import com.sun.jna.platform.win32.KnownFolders;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.win32.StdCallLibrary;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;
import net.miginfocom.swing.MigLayout;

class AivisSpeechRestApiJPanelTest {

	private static final String EMPTY = "";

	private static Class<?> CLASS_STYLE, CLASS_STYLE_INFO, CLASS_SPEAKER = null;

	private static Method METHOD_ADD_ACTION_LISTENER, METHOD_CREATE_HOST_AND_PORT, METHOD_WRITE, METHOD_GET_BYTES,
			METHOD_REMOVE_ALL_ELEMENTS, METHOD_GET_SCREEN_SIZE, METHOD_GET_HOST, METHOD_TEST_AND_ACCEPT3,
			METHOD_TEST_AND_ACCEPT4, METHOD_SET_VISIBLE, METHOD_PACK, METHOD_ADD, METHOD_SET_DEFAULT_CLOSE_OPERATION,
			METHOD_SPEAKERS_HOST_AND_PORT, METHOD_SPEAKERS_ITERABLE, METHOD_AUDIO_QUERY, METHOD_SYNTHESIS,
			METHOD_LENGTH_BYTE_ARRAY, METHOD_LENGTH_OBJECT_ARRAY, METHOD_TEST_AND_RUN, METHOD_ADD_ITEM_LISTENER,
			METHOD_SPEAKER_INFO_HOST_AND_PORT, METHOD_SPEAKER_INFO_MAP, METHOD_DECODE, METHOD_GET_STYLE_INFO_BY_ID,
			METHOD_SET_STYLE_INFO, METHOD_LINES, METHOD_TO_JSON, METHOD_FROM_JSON, METHOD_CREATE, METHOD_EXEC,
			METHOD_GET_CODE_METHOD, METHOD_GET_CODE_CODE, METHOD_TEST_AND_APPLY, METHOD_PLAY,
			METHOD_GET_FILE_EXTENSION_BYTE_ARRAY, METHOD_GET_FILE_EXTENSION_CONTENT_INFO,
			METHOD_GET_CONTENT_TYPE_CONTENT_INFO, METHOD_GET_CONTENT_TYPE_FILE, METHOD_IS_SUPPORTED_AUDIO_FORMAT,
			METHOD_TEST_AND_TEST, METHOD_SH_GET_KNOWN_FOLDER_PATH, METHOD_LIST_FILES, METHOD_NEXT_ALPHA_NUMERIC,
			METHOD_GET_MESSAGE, METHOD_SET, METHOD_IS_CLIENT_ERROR, METHOD_VERSION, METHOD_GET_MODEL,
			METHOD_CORE_VERSIONS, METHOD_TO_ITERABLE = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = AivisSpeechRestApiJPanel.class;
		//
		(METHOD_ADD_ACTION_LISTENER = clz.getDeclaredMethod("addActionListener", ActionListener.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_HOST_AND_PORT = clz.getDeclaredMethod("createHostAndPort", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_WRITE = clz.getDeclaredMethod("write", OutputStream.class, byte[].class)).setAccessible(true);
		//
		(METHOD_GET_BYTES = clz.getDeclaredMethod("getBytes", String.class)).setAccessible(true);
		//
		(METHOD_REMOVE_ALL_ELEMENTS = clz.getDeclaredMethod("removeAllElements", DefaultComboBoxModel.class))
				.setAccessible(true);
		//
		(METHOD_GET_SCREEN_SIZE = clz.getDeclaredMethod("getScreenSize", Toolkit.class)).setAccessible(true);
		//
		(METHOD_GET_HOST = clz.getDeclaredMethod("getHost", HostAndPort.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3 = clz.getDeclaredMethod("testAndAccept", IntPredicate.class, Integer.TYPE,
				IntConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				FailableBiConsumer.class)).setAccessible(true);
		//
		(METHOD_SET_VISIBLE = clz.getDeclaredMethod("setVisible", Component.class, Boolean.TYPE)).setAccessible(true);
		//
		(METHOD_PACK = clz.getDeclaredMethod("pack", Window.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Container.class, Component.class)).setAccessible(true);
		//
		(METHOD_SET_DEFAULT_CLOSE_OPERATION = clz.getDeclaredMethod("setDefaultCloseOperation", JFrame.class,
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_SPEAKERS_HOST_AND_PORT = clz.getDeclaredMethod("speakers", HostAndPort.class)).setAccessible(true);
		//
		(METHOD_SPEAKERS_ITERABLE = clz.getDeclaredMethod("speakers", Iterable.class)).setAccessible(true);
		//
		(METHOD_AUDIO_QUERY = clz.getDeclaredMethod("audioQuery", HostAndPort.class,
				CLASS_STYLE = Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$Style"),
				String.class)).setAccessible(true);
		//
		(METHOD_SYNTHESIS = clz.getDeclaredMethod("synthesis", HostAndPort.class, CLASS_STYLE, String.class))
				.setAccessible(true);
		//
		(METHOD_LENGTH_BYTE_ARRAY = clz.getDeclaredMethod("length", byte[].class)).setAccessible(true);
		//
		(METHOD_LENGTH_OBJECT_ARRAY = clz.getDeclaredMethod("length", Object[].class)).setAccessible(true);
		//
		(METHOD_TEST_AND_RUN = clz.getDeclaredMethod("testAndRun", Boolean.TYPE, Runnable.class)).setAccessible(true);
		//
		(METHOD_ADD_ITEM_LISTENER = clz.getDeclaredMethod("addItemListener", ItemListener.class,
				ItemSelectable[].class)).setAccessible(true);
		//
		(METHOD_SPEAKER_INFO_HOST_AND_PORT = clz.getDeclaredMethod("speakerInfo", HostAndPort.class, String.class))
				.setAccessible(true);
		//
		(METHOD_SPEAKER_INFO_MAP = clz.getDeclaredMethod("speakerInfo", Map.class)).setAccessible(true);
		//
		(METHOD_DECODE = clz.getDeclaredMethod("decode", Decoder.class, String.class)).setAccessible(true);
		//
		(METHOD_GET_STYLE_INFO_BY_ID = clz.getDeclaredMethod("getStyleInfoById", Iterable.class, String.class))
				.setAccessible(true);
		//
		(METHOD_SET_STYLE_INFO = clz.getDeclaredMethod("setStyleInfo",
				CLASS_SPEAKER = Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$Speaker"),
				HostAndPort.class, DefaultComboBoxModel.class)).setAccessible(true);
		//
		(METHOD_LINES = clz.getDeclaredMethod("lines", String.class)).setAccessible(true);
		//
		(METHOD_TO_JSON = clz.getDeclaredMethod("toJson", Gson.class, Object.class)).setAccessible(true);
		//
		(METHOD_FROM_JSON = clz.getDeclaredMethod("fromJson", Gson.class, String.class, Class.class))
				.setAccessible(true);
		//
		(METHOD_CREATE = clz.getDeclaredMethod("create", GsonBuilder.class)).setAccessible(true);
		//
		(METHOD_EXEC = clz.getDeclaredMethod("exec", Runtime.class, String.class)).setAccessible(true);
		//
		(METHOD_GET_CODE_METHOD = clz.getDeclaredMethod("getCode", org.apache.bcel.classfile.Method.class))
				.setAccessible(true);
		//
		(METHOD_GET_CODE_CODE = clz.getDeclaredMethod("getCode", Code.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_PLAY = clz.getDeclaredMethod("play",
				CLASS_STYLE_INFO = Util
						.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$StyleInfo"),
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_FILE_EXTENSION_BYTE_ARRAY = clz.getDeclaredMethod("getFileExtension", byte[].class))
				.setAccessible(true);
		//
		(METHOD_GET_FILE_EXTENSION_CONTENT_INFO = clz.getDeclaredMethod("getFileExtension", ContentInfo.class))
				.setAccessible(true);
		//
		(METHOD_GET_CONTENT_TYPE_CONTENT_INFO = clz.getDeclaredMethod("getContentType", ContentInfo.class))
				.setAccessible(true);
		//
		(METHOD_GET_CONTENT_TYPE_FILE = clz.getDeclaredMethod("getContentType", File.class)).setAccessible(true);
		//
		(METHOD_IS_SUPPORTED_AUDIO_FORMAT = clz.getDeclaredMethod("isSupportedAudioFormat", byte[].class))
				.setAccessible(true);
		//
		(METHOD_IS_SUPPORTED_AUDIO_FORMAT = clz.getDeclaredMethod("isSupportedAudioFormat", byte[].class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_TEST = clz.getDeclaredMethod("testAndTest", Predicate.class, Object.class, Predicate.class))
				.setAccessible(true);
		//
		(METHOD_SH_GET_KNOWN_FOLDER_PATH = clz.getDeclaredMethod("SHGetKnownFolderPath", GUID.class))
				.setAccessible(true);
		//
		(METHOD_LIST_FILES = clz.getDeclaredMethod("listFiles", File.class)).setAccessible(true);
		//
		(METHOD_NEXT_ALPHA_NUMERIC = clz.getDeclaredMethod("nextAlphanumeric", RandomStringUtils.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_MESSAGE = clz.getDeclaredMethod("getMessage", Throwable.class)).setAccessible(true);
		//
		(METHOD_SET = clz.getDeclaredMethod("set", Object.class, Map.class)).setAccessible(true);
		//
		(METHOD_IS_CLIENT_ERROR = clz.getDeclaredMethod("isClientError", HttpURLConnection.class)).setAccessible(true);
		//
		(METHOD_VERSION = clz.getDeclaredMethod("version", HostAndPort.class)).setAccessible(true);
		//
		(METHOD_GET_MODEL = clz.getDeclaredMethod("getModel", JComboBox.class)).setAccessible(true);
		//
		(METHOD_CORE_VERSIONS = clz.getDeclaredMethod("coreVersions", HostAndPort.class)).setAccessible(true);
		//
		(METHOD_TO_ITERABLE = clz.getDeclaredMethod("toIterable", Object.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean test, equals = null;

		private Long count = null;

		private Integer write = null;

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
			if (Boolean.logicalOr(
					proxy instanceof AutoCloseable && Objects.equals(name, "close") && method != null
							&& method.getParameterCount() == 0,
					proxy instanceof DataLine && IterableUtils.contains(Arrays.asList("drain", "start"), name))) {
				//
				return null;
				//
			} else if (proxy instanceof ListModel && Objects.equals(name, "getSize")) {
				//
				return Integer.valueOf(0);
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
			} else if (proxy instanceof BiFunction) {
				//
				if (Objects.equals(name, "apply")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof ListCellRenderer) {
				//
				if (Objects.equals(name, "getListCellRendererComponent")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof FailableFunction) {
				//
				if (Objects.equals(name, "apply")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Iterable) {
				//
				if (IterableUtils.contains(Arrays.asList("iterator", "spliterator"), name)) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Map && Objects.equals(name, "get")) {
				//
				return null;
				//
			} else if (proxy instanceof Stream && Objects.equals(name, "count")) {
				//
				return count;
				//
			} else if (proxy instanceof SourceDataLine) {
				//
				if (Objects.equals(name, "open")) {
					//
					return null;
					//
				} else if (Objects.equals(name, "write")) {
					//
					return write;
					//
				} // if
					//
			} else if (proxy instanceof IntPredicate) {
				//
				if (Objects.equals(name, "test")) {
					//
					return test;
					//
				} // if
					//
			} else if (proxy instanceof IntConsumer && Objects.equals(name, "accept")) {
				//
				return null;
				//
			} else if (proxy instanceof Ole32 && Objects.equals(name, "CoTaskMemFree")) {
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

		private Integer responseCode;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			if (Objects.equals(getReturnType(thisMethod), Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String name = Util.getName(thisMethod);
			//
			if (self instanceof Toolkit && Objects.equals(name, "getScreenSize")) {
				//
				return null;
				//
			} else if (self instanceof HttpURLConnection && Objects.equals(name, "getResponseCode")) {
				//
				return responseCode;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private AivisSpeechRestApiJPanel instance = null;

	private IH ih = null;

	private MH mh = null;

	private Object style, styleInfo, speaker = null;

	private ObjectMapper objectMapper = null;

	private Boolean exec = null;

	private org.apache.bcel.classfile.Method method = null;

	private Decoder decoder = null;

	private OperatingSystem operatingSystem = null;

	private String playMethodThrowExceptionClassName = null;

	private Entry<org.apache.bcel.classfile.Method, Class<?>> methodThrowExceptionIfOnlyOneParameterIsNull = null;

	private JComboBox<?> jComboBox = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		instance = Util.cast(AivisSpeechRestApiJPanel.class,
				Narcissus.allocateInstance(AivisSpeechRestApiJPanel.class));
		//
		ih = new IH();
		//
		mh = new MH();
		//
		style = Narcissus.allocateInstance(CLASS_STYLE);
		//
		styleInfo = Narcissus.allocateInstance(CLASS_STYLE_INFO);
		//
		speaker = Narcissus.allocateInstance(CLASS_SPEAKER);
		//
		objectMapper = new ObjectMapper();
		//
		decoder = Base64.getDecoder();
		//
		final Class<?> clz = AivisSpeechRestApiJPanel.class;
		//
		playMethodThrowExceptionClassName = getPlayMethodThrowExceptionClassName(clz);
		//
		if (IterableUtils.contains(Arrays.asList(OperatingSystem.LINUX, OperatingSystem.WINDOWS),
				operatingSystem = OperatingSystemUtil.getOperatingSystem())) {
			//
			try {
				//
				final Entry<String, org.apache.bcel.classfile.Method> entry = getCommandAndMethod(clz);
				//
				method = Util.getValue(entry);
				//
				exec(Runtime.getRuntime(), Util.getKey(entry));
				//
				exec = Boolean.TRUE;
				//
			} catch (final Throwable e) {
				//
				e.printStackTrace();
				//
				exec = Boolean.FALSE;
				//
			} // try
				//
		} // if
			//
		methodThrowExceptionIfOnlyOneParameterIsNull = getMethodThrowExceptionIfOnlyOneParameterIsNull(clz);
		//
		jComboBox = Util.cast(JComboBox.class, Narcissus.allocateInstance(JComboBox.class));
		//
	}

	@Test
	void testExec() throws Throwable {
		//
		final Runtime runtime = Runtime.getRuntime();
		//
		Assertions.assertNull(exec(runtime, null));
		//
		Assertions.assertNull(exec(runtime, EMPTY));
		//
		if (Objects.equals(OperatingSystem.LINUX, operatingSystem)) {
			//
			Assertions.assertNotNull(exec(runtime, "uname"));
			//
		} // if
			//
	}

	private static Process exec(final Runtime instance, final String command) throws Throwable {
		try {
			final Object obj = invoke(METHOD_EXEC, null, instance, command);
			if (obj == null) {
				return null;
			} else if (obj instanceof Process) {
				return (Process) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getReturnType(final Method instance) {
		return instance != null ? instance.getReturnType() : null;
	}

	@Test
	void testNull() throws Throwable {
		//
		final Method[] ms = AivisSpeechRestApiJPanel.class.getDeclaredMethods();
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
		Object[] arguments = null;
		//
		Throwable throwable = null;
		//
		boolean isStatic, isWindows;
		//
		org.apache.bcel.classfile.Method clMethod = null;
		//
		Type[] argumentTypes = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()
					|| Boolean.logicalAnd(Objects.equals(name = Util.getName(m), "play"),
							Boolean.logicalOr(
									Arrays.equals(parameterTypes = m.getParameterTypes(),
											new Class<?>[] { CLASS_STYLE_INFO, Integer.TYPE }),
									Arrays.equals(parameterTypes, new Class<?>[] { byte[].class })))) {
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
			} // if
				//
			arguments = toArray(collection);
			//
			toString = Util.toString(m);
			//
			if (Util.and(Objects.equals(name, FieldOrMethodUtil.getName(method)),
					Arrays.equals(parameterTypes, new Class<?>[] { CLASS_STYLE_INFO, Integer.TYPE }),
					isStatic = Modifier.isStatic(m.getModifiers()),
					Boolean.logicalOr(isWindows = Objects.equals(OperatingSystem.WINDOWS, operatingSystem),
							Boolean.logicalAnd(Objects.equals(OperatingSystem.LINUX, operatingSystem),
									Objects.equals(Boolean.FALSE, exec))))) {
				//
				throwable = null;
				//
				try {
					//
					Narcissus.invokeStaticMethod(m, arguments);
					//
				} catch (final Throwable e) {
					//
					throwable = e;
					//
				} // if
					//
				if (isWindows) {
					//
					Assertions.assertEquals(playMethodThrowExceptionClassName, Util.getName(Util.getClass(throwable)));
					//
				} else {
					//
					Assertions.assertEquals("java.io.IOException", Util.getName(Util.getClass(throwable)));
					//
				} // if
					//
				continue;
				//
			} // if
				//
			if (Objects
					.equals(FieldOrMethodUtil.getName(
							clMethod = Util.getKey(methodThrowExceptionIfOnlyOneParameterIsNull)), Util.getName(m))
					&& length(argumentTypes = clMethod.getArgumentTypes()) == m.getParameterCount()
					&& length(argumentTypes) == 1 && Objects.equals(Util.toString(ArrayUtils.get(argumentTypes, 0)),
							Util.getName(ArrayUtils.get(Util.getParameterTypes(m), 0)))) {
				//
				throwable = null;
				//
				try {
					//
					if (isStatic) {
						//
						Narcissus.invokeStaticMethod(m, arguments);
						//
					} // if
						//
				} catch (final Throwable e) {
					//
					throwable = e;
					//
				} // try
					//
				Assertions.assertEquals(Util.getValue(methodThrowExceptionIfOnlyOneParameterIsNull),
						Util.getClass(throwable));
				//
				continue;
				//
			} // if
				//
			invoke = isStatic ? Narcissus.invokeStaticMethod(m, arguments)
					: Narcissus.invokeMethod(instance, m, arguments);
			//
			if (IterableUtils.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE, Long.TYPE), getReturnType(m))) {
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

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testMethodWithInterfaceParameter() throws Throwable {
		//
		final Method[] ms = AivisSpeechRestApiJPanel.class.getDeclaredMethods();
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
		Object[] arguments = null;
		//
		String name, toString = null;
		//
		Throwable throwable = null;
		//
		boolean isStatic, isWindows;
		//
		org.apache.bcel.classfile.Method clMethod = null;
		//
		Type[] argumentTypes = null;
		//
		if (ih != null) {
			//
			ih.test = Boolean.FALSE;
			//
			ih.count = Long.valueOf(0);
			//
			ih.write = Integer.valueOf(0);
			//
		} // if
			//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()
					|| Boolean
							.logicalAnd(Objects.equals(name = Util.getName(m), "main"),
									Arrays.equals(parameterTypes = m.getParameterTypes(),
											new Class<?>[] { String[].class }))
					|| Boolean.logicalAnd(Objects.equals(name, "play"),
							Boolean.logicalOr(
									Arrays.equals(parameterTypes, new Class<?>[] { CLASS_STYLE_INFO, Integer.TYPE }),
									Arrays.equals(parameterTypes, new Class<?>[] { byte[].class })))) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (isInterface(parameterType = ArrayUtils.get(parameterTypes, j))) {
					//
					Util.add(collection,
							Boolean.logicalAnd(Util.isAssignableFrom(StdCallLibrary.class, parameterType),
									!Objects.equals(OperatingSystem.WINDOWS, operatingSystem)) ? null
											: Reflection.newProxy(parameterType, ih));
					//
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
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
			} // if
				//
			arguments = toArray(collection);
			//
			toString = Util.toString(m);
			//
			if (Util.and(Objects.equals(name, FieldOrMethodUtil.getName(method)),
					Arrays.equals(parameterTypes, new Class<?>[] { CLASS_STYLE_INFO, Integer.TYPE }),
					isStatic = Modifier.isStatic(m.getModifiers()),
					Boolean.logicalOr(isWindows = Objects.equals(OperatingSystem.WINDOWS, operatingSystem),
							Boolean.logicalAnd(Objects.equals(OperatingSystem.LINUX, operatingSystem),
									Objects.equals(Boolean.FALSE, exec))))) {
				//
				throwable = null;
				//
				try {
					//
					Narcissus.invokeStaticMethod(m, arguments);
					//
				} catch (final Throwable e) {
					//
					throwable = e;
					//
				} // if
					//
				if (isWindows) {
					//
					Assertions.assertEquals(playMethodThrowExceptionClassName, Util.getName(Util.getClass(throwable)));
					//
				} else {
					//
					Assertions.assertEquals("java.io.IOException", Util.getName(Util.getClass(throwable)));
					//
				} // if
					//
				continue;
				//
			} // if
				//

			if (Objects
					.equals(FieldOrMethodUtil.getName(
							clMethod = Util.getKey(methodThrowExceptionIfOnlyOneParameterIsNull)), Util.getName(m))
					&& length(argumentTypes = clMethod.getArgumentTypes()) == m.getParameterCount()
					&& length(argumentTypes) == 1 && Objects.equals(Util.toString(ArrayUtils.get(argumentTypes, 0)),
							Util.getName(ArrayUtils.get(Util.getParameterTypes(m), 0)))) {
				//
				throwable = null;
				//
				try {
					//
					if (isStatic) {
						//
						Narcissus.invokeStaticMethod(m, arguments);
						//
					} // if
						//
				} catch (final Throwable e) {
					//
					throwable = e;
					//
				} // try
					//
				Assertions.assertEquals(Util.getValue(methodThrowExceptionIfOnlyOneParameterIsNull),
						Util.getClass(throwable));
				//
				continue;
				//
			} // if
				//
			invoke = isStatic ? Narcissus.invokeStaticMethod(m, arguments)
					: Narcissus.invokeMethod(instance, m, arguments);
			//
			if (Boolean.logicalOr(
					IterableUtils.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE, Long.TYPE), getReturnType(m)),
					Boolean.logicalAnd(Objects.equals(Util.getName(m), "speakerInfo"),
							Arrays.equals(parameterTypes, new Class<?>[] { Map.class })))) {
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

	private static boolean isInterface(final Class<?> instance) {
		return instance != null && instance.isInterface();
	}

	@Test
	void testActionPerformed1() throws Exception {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
			// btnAudioQuery
			//
		final Object btnAudioQuery = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnAudioQuery", btnAudioQuery, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnAudioQuery, 0, null)));
		//
		// btnViewAudioQuery
		//
		final Object btnViewAudioQuery = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnViewAudioQuery", btnViewAudioQuery, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnViewAudioQuery, 0, null)));
		//
		// btnSynthesis
		//
		final Object btnSynthesis = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSynthesis", btnSynthesis, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnSynthesis, 0, null)));
		//
		// btnViewPortrait
		//
		final Object btnViewPortrait = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnViewPortrait", btnViewPortrait, true);
		//
		final ActionEvent actionEventBtnViewPortrait = new ActionEvent(btnViewPortrait, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewPortrait));
		//
		final JComboBox<?> jcbSpeaker = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbSpeaker", jcbSpeaker, true);
		//
		ComboBoxModel<?> cbm = jcbSpeaker.getModel();
		//
		invoke(Util.getDeclaredMethod(Util.getClass(cbm), "addElement", Object.class), cbm, speaker);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewPortrait));
		//
		final Object speakerInfo = Narcissus.allocateInstance(
				Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$SpeakerInfo"));
		//
		FieldUtils.writeDeclaredField(speaker, "speakerInfo", speakerInfo, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewPortrait));
		//
		// btnCopyPortrait
		//
		final Object btnCopyPortrait = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyPortrait", btnCopyPortrait, true);
		//
		final ActionEvent actionEventBtnCopyPortrait = new ActionEvent(btnCopyPortrait, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopyPortrait));
		//
		FieldUtils.writeDeclaredField(speaker, "speakerInfo", null, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopyPortrait));
		//
		invoke(Util.getDeclaredMethod(Util.getClass(cbm), "removeElementAt", Integer.TYPE), cbm, 0);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopyPortrait));
		//
		// btnSavePortrait
		//
		final Object btnSavePortrait = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSavePortrait", btnSavePortrait, true);
		//
		final ActionEvent actionEventBtnSavePortrait = new ActionEvent(btnSavePortrait, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSavePortrait));
		//
		invoke(Util.getDeclaredMethod(Util.getClass(cbm), "addElement", Object.class), cbm, speaker);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSavePortrait));
		//
		FieldUtils.writeDeclaredField(speaker, "speakerInfo", speakerInfo, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSavePortrait));
		//
		final BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR);
		//
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			ImageIO.write(bi, "png", baos);
			//
			FieldUtils.writeDeclaredField(speakerInfo, "portrait", baos.toByteArray(), true);
			//
		} // try
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSavePortrait));
		//
		// btnViewIcon
		//
		final Object btnViewIcon = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnViewIcon", btnViewIcon, true);
		//
		final ActionEvent actionEventBtnViewIcon = new ActionEvent(btnViewIcon, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewIcon));
		//
		final JComboBox<?> jcbStyle = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbStyle", jcbStyle, true);
		//
		invoke(Util.getDeclaredMethod(Util.getClass(cbm = jcbStyle.getModel()), "addElement", Object.class), cbm,
				style);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewIcon));
		//
		FieldUtils.writeDeclaredField(style, "styleInfo", styleInfo, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnViewIcon));
		//
		// btnCopyIcon
		//
		final Object btnCopyIcon = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyIcon", btnCopyIcon, true);
		//
		final ActionEvent actionEventBtnCopyIcon = new ActionEvent(btnCopyIcon, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopyIcon));
		//
		FieldUtils.writeDeclaredField(style, "styleInfo", null, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopyIcon));
		//
		invoke(Util.getDeclaredMethod(Util.getClass(jcbStyle.getModel()), "removeElementAt", Integer.TYPE),
				jcbStyle.getModel(), 0);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnCopyIcon));
		//
		// btnSaveIcon
		//
		final Object btnSaveIcon = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnSaveIcon", btnSaveIcon, true);
		//
		final ActionEvent actionEventBtnSaveIcon = new ActionEvent(btnSaveIcon, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSaveIcon));
		//
		invoke(Util.getDeclaredMethod(Util.getClass(cbm = jcbStyle.getModel()), "addElement", Object.class), cbm,
				style);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSaveIcon));
		//
		FieldUtils.writeDeclaredField(style, "styleInfo", styleInfo, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSaveIcon));
		//
		FieldUtils.writeDeclaredField(style, "name", "NAME", true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEventBtnSaveIcon));
		//
		// btnCopyVoiceSampleTranscriptToText
		//
		final Object btnCopyVoiceSampleTranscriptToText = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopyVoiceSampleTranscriptToText",
				btnCopyVoiceSampleTranscriptToText, true);
		//
		Assertions.assertDoesNotThrow(
				() -> instance.actionPerformed(new ActionEvent(btnCopyVoiceSampleTranscriptToText, 0, null)));
		//
		// btnPlayVoiceSampleTranscript
		//
		final Object btnPlayVoiceSampleTranscript = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnPlayVoiceSampleTranscript", btnPlayVoiceSampleTranscript, true);
		//
		Assertions.assertDoesNotThrow(
				() -> instance.actionPerformed(new ActionEvent(btnPlayVoiceSampleTranscript, 0, null)));
		//
	}

	@Test
	void testActionPerformed2() throws Exception {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
			// btnPlay
			//
		if (Objects.equals(exec, Boolean.TRUE)) {
			//
			final Object btnPlay = new JButton();
			//
			FieldUtils.writeDeclaredField(instance, "btnPlay", btnPlay, true);
			//
			if (Objects.equals(OperatingSystem.WINDOWS, operatingSystem)) {
				//
				Throwable throwable = null;
				//
				try {
					//
					instance.actionPerformed(new ActionEvent(btnPlay, 0, null));
					//
				} catch (final Throwable e) {
					//
					throwable = e;
					//
				} // try
					//
				Assertions.assertEquals(playMethodThrowExceptionClassName, Util.getName(Util.getClass(throwable)));
				//
			} else {
				//
				Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnPlay, 0, null)));
				//
			} // if
				//
		} else if (!Objects.equals(exec, Boolean.FALSE)) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
	}

	@Test
	void testItemStateChanged() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		if (ih != null) {
			//
			ih.equals = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance
				.itemStateChanged(new ItemEvent(Reflection.newProxy(ItemSelectable.class, ih), 0, null, 0)));
		//
		// jcbSpeaker
		//
		final JComboBox<?> jcbSpeaker = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbSpeaker", jcbSpeaker, true);
		//
		ComboBoxModel<?> cbm = jcbSpeaker.getModel();
		//
		invoke(Util.getDeclaredMethod(Util.getClass(cbm), "addElement", Object.class), cbm, speaker);
		//
		final ItemEvent itemEventJcbSpeaker = new ItemEvent(jcbSpeaker, 0, null, 0);
		//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEventJcbSpeaker));
		//
		FieldUtils.writeDeclaredField(speaker, "speakerInfo",
				Narcissus.allocateInstance(
						Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$SpeakerInfo")),
				true);
		//
		FieldUtils.writeDeclaredField(instance, "jLabelPortrait", new JLabel(), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEventJcbSpeaker));
		//
		// jcbStyle
		//
		final JComboBox<?> jcbStyle = new JComboBox<>();
		//
		FieldUtils.writeDeclaredField(instance, "jcbStyle", jcbStyle, true);
		//
		final ItemEvent itemEventJcbStyle = new ItemEvent(jcbStyle, 0, null, 0);
		//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEventJcbStyle));
		//
		invoke(Util.getDeclaredMethod(Util.getClass(cbm = jcbStyle.getModel()), "addElement", Object.class), cbm,
				style);
		//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEventJcbStyle));
		//
		FieldUtils.writeDeclaredField(style, "styleInfo", Narcissus.allocateInstance(
				Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$StyleInfo")), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEventJcbStyle));
		//
	}

	@Test
	void testAddActionListener() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_ADD_ACTION_LISTENER, null, null, null));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Test
	void testCreateHostAndPort() throws Throwable {
		//
		final int port = 12345;
		//
		Assertions.assertNull(createHostAndPort(null, Integer.toString(port)));
		//
		Assertions.assertEquals(HostAndPort.fromParts(EMPTY, port), createHostAndPort(EMPTY, Integer.toString(port)));
		//
	}

	private static HostAndPort createHostAndPort(final String host, final String port) throws Throwable {
		try {
			final Object obj = invoke(METHOD_CREATE_HOST_AND_PORT, null, host, port);
			if (obj == null) {
				return null;
			} else if (obj instanceof HostAndPort) {
				return (HostAndPort) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testWrite() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> write(ProxyUtil.createProxy(OutputStream.class, mh), null));
		//
		try (final OutputStream os = new ByteArrayOutputStream()) {
			//
			Assertions.assertDoesNotThrow(() -> write(os, null));
			//
			Assertions.assertDoesNotThrow(() -> write(os, new byte[] {}));
			//
		} // try
			//
	}

	private static void write(final OutputStream instance, final byte[] bs) throws Throwable {
		try {
			invoke(METHOD_WRITE, null, instance, bs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetBytes() throws Throwable {
		//
		Assertions.assertArrayEquals(new byte[] {}, getBytes(EMPTY));
		//
	}

	private static byte[] getBytes(final String instance) throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_BYTES, null, instance);
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
	void testRemoveAllElements() throws IllegalAccessException, InvocationTargetException, NoSuchFieldException,
			InstantiationException, NoSuchMethodException {
		//
		final Object dcbm = Narcissus.allocateInstance(DefaultComboBoxModel.class);
		//
		Assertions.assertNull(invoke(METHOD_REMOVE_ALL_ELEMENTS, null, dcbm));
		//
		Narcissus.setField(dcbm, Narcissus.findField(Util.getClass(dcbm), "objects"),
				Util.newInstance(Vector.class.getDeclaredConstructor()));
		//
		Assertions.assertNull(invoke(METHOD_REMOVE_ALL_ELEMENTS, null, dcbm));
		//
	}

	@Test
	void testGetScreenSize() throws Throwable {
		//
		Assertions.assertNull(invoke(METHOD_GET_SCREEN_SIZE, null, ProxyUtil.createProxy(Toolkit.class, mh)));
		//
	}

	@Test
	void testGetHost() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_HOST, null, Narcissus.allocateInstance(HostAndPort.class)));
		//
	}

	@Test
	void testTestAndAccept() throws IllegalAccessException, InvocationTargetException {
		//
		if (ih != null) {
			//
			ih.test = Boolean.TRUE;
			//
		} // if
			//
		Assertions
				.assertDoesNotThrow(() -> testAndAccept(Reflection.newProxy(BiPredicate.class, ih), null, null, null));
		//
		final IntPredicate intPredicate = Reflection.newProxy(IntPredicate.class, ih);
		//
		final Integer zero = Integer.valueOf(0);
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_ACCEPT3, null, intPredicate, zero, null));
		//
		Assertions.assertNull(
				invoke(METHOD_TEST_AND_ACCEPT3, null, intPredicate, zero, Reflection.newProxy(IntConsumer.class, ih)));
		//
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			@Nullable final U u, final FailableBiConsumer<T, U, E> consumer) throws Throwable {
		try {
			invoke(METHOD_TEST_AND_ACCEPT4, null, instance, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetVisible() throws Throwable {
		//
		Assertions
				.assertNull(invoke(METHOD_SET_VISIBLE, null, ProxyUtil.createProxy(Component.class, mh), Boolean.TRUE));
		//
	}

	@Test
	void testPack() throws IllegalAccessException, InvocationTargetException, NoSuchFieldException {
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			return;
			//
		} // if
			//
		final Object window = Narcissus.allocateInstance(Window.class);
		//
		Assertions.assertNull(invoke(METHOD_PACK, null, window));
		//
		Narcissus.setField(window, Narcissus.findField(Util.getClass(instance), "objectLock"), new Object());
		//
		Assertions.assertNull(invoke(METHOD_PACK, null, window));
		//
		Narcissus.setField(window, Narcissus.findField(Util.getClass(instance), "objectLock"), new Object());
		//
		Assertions.assertNull(invoke(METHOD_PACK, null, window));
		//
	}

	@Test
	void testAdd() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_ADD, null, Narcissus.allocateInstance(Container.class), null));
		//
	}

	@Test
	void testSetDefaultCloseOperation() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_DEFAULT_CLOSE_OPERATION, null, Narcissus.allocateInstance(JFrame.class),
				Integer.valueOf(0)));
		//
	}

	@Test
	void testSpeakers() throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
		//
		Assertions.assertNull(invoke(METHOD_SPEAKERS_HOST_AND_PORT, null, HostAndPort.fromHost(EMPTY)));
		//
		Assertions.assertNull(invoke(METHOD_SPEAKERS_HOST_AND_PORT, null, HostAndPort.fromParts(EMPTY, 1)));
		//
		Assertions.assertNull(invoke(METHOD_SPEAKERS_ITERABLE, null, Collections.singleton(null)));
		//
		if (objectMapper != null) {
			//
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			//
		} // if
			//
		Assertions.assertEquals(
				"[{\"name\":null,\"speakerUuid\":null,\"styles\":[{\"id\":null,\"name\":null,\"styleInfo\":null}],\"speakerInfo\":null}]",
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_SPEAKERS_ITERABLE, null,
						ObjectMapperUtil.readValue(objectMapper, "[{\"styles\":[null,{}]}]", Object.class))));
		//
	}

	@Test
	void testAudioQuery() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_AUDIO_QUERY, null, HostAndPort.fromHost(EMPTY), null, null));
		//
		final HostAndPort hostAndPort = HostAndPort.fromParts(EMPTY, 1);
		//
		Assertions.assertNull(invoke(METHOD_AUDIO_QUERY, null, hostAndPort, null, null));
		//
		Assertions.assertNull(invoke(METHOD_AUDIO_QUERY, null, hostAndPort, style, null));
		//
	}

	@Test
	void testSynthesis() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SYNTHESIS, null, HostAndPort.fromHost(EMPTY), null, null));
		//
		final HostAndPort hostAndPort = HostAndPort.fromParts(EMPTY, 1);
		//
		Assertions.assertNull(invoke(METHOD_SYNTHESIS, null, hostAndPort, null, null));
		//
		Assertions.assertNull(invoke(METHOD_SYNTHESIS, null, hostAndPort, style, null));
		//
	}

	@Test
	void testLength() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(Integer.valueOf(0), invoke(METHOD_LENGTH_BYTE_ARRAY, null, new byte[] {}));
		//
	}

	@Test
	void testTestAndRun() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_RUN, null, Boolean.TRUE, null));
		//
	}

	@Test
	void testAddItemListener() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_ADD_ITEM_LISTENER, null, null, (Object) null));
		//
	}

	@Test
	void testSpeakInfo() throws Throwable {
		//
		Assertions.assertNull(invoke(METHOD_SPEAKER_INFO_HOST_AND_PORT, null, HostAndPort.fromHost(EMPTY), null));
		//
		Assertions.assertNull(invoke(METHOD_SPEAKER_INFO_HOST_AND_PORT, null, HostAndPort.fromParts(EMPTY, 1), null));
		//
		if (objectMapper != null) {
			//
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			//
			objectMapper.setDefaultPropertyInclusion(Include.NON_NULL);
			//
		} // if
			//
		Assertions.assertEquals("{\"portrait\":\"\"}", ObjectMapperUtil.writeValueAsString(objectMapper,
				invoke(METHOD_SPEAKER_INFO_MAP, null, Collections.singletonMap("portrait", EMPTY))));
		//
		Assertions.assertEquals("{\"styleInfos\":[{}]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_SPEAKER_INFO_MAP, null,
						ObjectMapperUtil.readValue(objectMapper, "{\"style_infos\":[null,{}]}", Object.class))));
		//
		Assertions.assertEquals("{\"styleInfos\":[{\"icon\":\"\",\"voiceSamples\":[\"\"]}]}",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						invoke(METHOD_SPEAKER_INFO_MAP, null, ObjectMapperUtil.readValue(objectMapper,
								"{\"style_infos\":[{\"icon\":\"\",\"voice_samples\":[\"\"]}]}", Object.class))));
		//
	}

	@Test
	void testDecode() throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
		//
		Assertions.assertNull(invoke(METHOD_DECODE, null, decoder, (Object) null));
		//
		Assertions.assertEquals(StringUtils.repeat('"', 2),
				ObjectMapperUtil.writeValueAsString(objectMapper, invoke(METHOD_DECODE, null, decoder, EMPTY)));
		//
	}

	@Test
	void testGetStyleInfobyId() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_STYLE_INFO_BY_ID, null, Collections.singleton(null), null));
		//
		Assertions.assertSame(styleInfo,
				invoke(METHOD_GET_STYLE_INFO_BY_ID, null, Collections.singleton(styleInfo), null));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			try {
				//
				invoke(METHOD_GET_STYLE_INFO_BY_ID, null, Collections.nCopies(2, styleInfo), null);
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
	void testSetStyleInfo() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_STYLE_INFO, null, speaker, null, null));
		//
	}

	@Test
	void testLines() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_LINES, null, Narcissus.allocateInstance(String.class)));
		//
	}

	@Test
	void testToJson() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TO_JSON, null, Narcissus.allocateInstance(Gson.class), null));
		//
		Assertions.assertEquals(Objects.toString(null), invoke(METHOD_TO_JSON, null, new Gson(), null));
		//
	}

	@Test
	void testFromJson() throws IllegalAccessException, InvocationTargetException {
		//
		final Object gson = Narcissus.allocateInstance(Gson.class);
		//
		Assertions.assertNull(invoke(METHOD_FROM_JSON, null, gson, null, null));
		//
		Assertions.assertNull(invoke(METHOD_FROM_JSON, null, gson, null, Object.class));
		//
		Assertions.assertNull(invoke(METHOD_FROM_JSON, null, gson, "", Object.class));
		//
		Assertions.assertNull(invoke(METHOD_FROM_JSON, null, gson, "{}", Object.class));
		//
	}

	@Test
	void testCreate() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_CREATE, null, Narcissus.allocateInstance(GsonBuilder.class)));
		//
	}

	private static Entry<org.apache.bcel.classfile.Method, Class<?>> getMethodThrowExceptionIfOnlyOneParameterIsNull(
			final Class<?> clz) throws Throwable {
		//
		Entry<org.apache.bcel.classfile.Method, Class<?>> entry = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				StringUtils.join("/", StringsUtil.replace(Strings.CS, Util.getName(clz), ".", "/"), ".class"))) {
			//
			final JavaClass javaClass = ClassParserUtil
					.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null));
			//
			final Iterable<org.apache.bcel.classfile.Method> ms = Util.collect(
					testAndApply(Objects::nonNull, JavaClassUtil.getMethods(javaClass), Arrays::stream, null),
					Collectors.toList());
			//
			ConstantPoolGen cpg = null;
			//
			org.apache.bcel.classfile.Method m = null;
			//
			Class<?> c = null;
			//
			for (int i = 0; i < IterableUtils.size(ms); i++) {
				//
				if ((c = getMethodThrowExceptionIfOnlyOneParameterIsNull(
						cpg = ObjectUtils.getIfNull(cpg, () -> new ConstantPoolGen(javaClass.getConstantPool())),
						m = IterableUtils.get(ms, i))) == null) {
					//
					continue;
					//
				} // if
					//
				if (entry != null) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				entry = Pair.of(m, c);
				//
			} // for
				//
		} // try
			//
		return entry;
		//
	}

	private static Class<?> getMethodThrowExceptionIfOnlyOneParameterIsNull(final ConstantPoolGen cpg,
			final org.apache.bcel.classfile.Method method) throws Throwable {
		//
		final Instruction[] instructions = InstructionListUtil
				.getInstructions(testAndApply(Objects::nonNull, getCode(getCode(method)), InstructionList::new, null));
		//
		for (int i = 0; i <= length(instructions); i++) {
			//
			if (ArrayUtils.get(instructions, i) instanceof ATHROW && i > 6
					&& ArrayUtils.get(instructions, i - 4) instanceof NEW NEW
					&& ArrayUtils.get(instructions, i - 5) instanceof IFNONNULL
					&& ArrayUtils.get(instructions, i - 6) instanceof ALOAD) {
				//
				return Util.forName(TypeUtil.getClassName(NEW.getType(cpg)));
				//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	private static String getPlayMethodThrowExceptionClassName(final Class<?> clz) throws Throwable {
		//
		IValue0<String> ivalue0 = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				StringUtils.join("/", StringsUtil.replace(Strings.CS, Util.getName(clz), ".", "/"), ".class"))) {
			//
			final JavaClass javaClass = ClassParserUtil
					.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null));
			//
			final Iterable<org.apache.bcel.classfile.Method> ms = Util
					.collect(
							Util.filter(
									testAndApply(Objects::nonNull, JavaClassUtil.getMethods(javaClass), Arrays::stream,
											null),
									m -> m != null
											&& Objects.equals(FieldOrMethodUtil.getName(m),
													"play")
											&& CollectionUtils.isEqualCollection(
													Util.collect(Util.map(Arrays.stream(MethodUtil.getArgumentTypes(m)),
															TypeUtil::getClassName), Collectors.toList()),
													Collections.singleton("[B"))),
							Collectors.toList());
			//
			if (IterableUtils.size(ms) > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			final org.apache.bcel.classfile.Method method = testAndApply(x -> IterableUtils.size(x) == 1, ms,
					x -> IterableUtils.get(x, 0), null);
			//
			final Instruction[] instructions = InstructionListUtil.getInstructions(
					testAndApply(Objects::nonNull, getCode(getCode(method)), InstructionList::new, null));
			//
			ConstantPoolGen cpg = null;
			//
			final int length = length(instructions);
			//
			if (length > 1 && ArrayUtils.get(instructions, length - 1) instanceof ATHROW
					&& ArrayUtils.get(instructions, length - 2) instanceof INVOKESPECIAL invokespecial) {
				//
				ivalue0 = Unit.with(invokespecial.getClassName(
						ObjectUtils.getIfNull(cpg, () -> new ConstantPoolGen(javaClass.getConstantPool()))));
				//
			} // if
				//
		} // try
			//
		return IValue0Util.getValue0(ivalue0);
		//
	}

	private static Entry<String, org.apache.bcel.classfile.Method> getCommandAndMethod(final Class<?> clz)
			throws Throwable {
		//
		Collection<IValue0<Entry<String, org.apache.bcel.classfile.Method>>> collection = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				StringUtils.join("/", StringsUtil.replace(Strings.CS, Util.getName(clz), ".", "/"), ".class"))) {
			//
			final JavaClass javaClass = ClassParserUtil
					.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null));
			//
			final Iterable<org.apache.bcel.classfile.Method> ms = Util.collect(
					testAndApply(Objects::nonNull, JavaClassUtil.getMethods(javaClass), Arrays::stream, null),
					Collectors.toList());
			//
			for (int i = 0; i < IterableUtils.size(ms); i++) {
				//
				testAndAccept((a, b) -> b != null, collection = ObjectUtils.getIfNull(collection, ArrayList::new),
						getCommandAndMethod(clz, IterableUtils.get(ms, i)), Util::add);
				//
			} // for
				//
		} // try
			//
		if (IterableUtils.size(collection) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return IValue0Util.getValue0(
				testAndApply(x -> IterableUtils.size(x) == 1, collection, x -> IterableUtils.get(x, 0), null));
		//
	}

	private static IValue0<Entry<String, org.apache.bcel.classfile.Method>> getCommandAndMethod(final Class<?> clz,
			final org.apache.bcel.classfile.Method method) throws Throwable {
		//
		IValue0<Entry<String, org.apache.bcel.classfile.Method>> ivalue0 = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				StringUtils.join("/", StringsUtil.replace(Strings.CS, Util.getName(clz), ".", "/"), ".class"))) {
			//
			final JavaClass javaClass = ClassParserUtil
					.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null));
			//
			final Instruction[] instructions = InstructionListUtil.getInstructions(
					testAndApply(Objects::nonNull, getCode(getCode(method)), InstructionList::new, null));
			//
			Instruction instruction = null;
			//
			ConstantPoolGen cpg = null;
			//
			OperatingSystem operatingSystem = null;
			//
			boolean found = false;
			//
			String key = null;
			//
			for (int i = 0; instructions != null && i < instructions.length; i++) {
				//
				if ((instruction = ArrayUtils.get(instructions, i)) instanceof GETSTATIC gc && Objects.equals(
						TypeUtil.getClassName(getReferenceType(gc,
								cpg = ObjectUtils.getIfNull(cpg,
										() -> new ConstantPoolGen(javaClass.getConstantPool())))),
						"org.oxbow.swingbits.util.OperatingSystem")) {
					//
					if (Objects.equals(Util.name(operatingSystem = ObjectUtils.getIfNull(operatingSystem,
							OperatingSystemUtil::getOperatingSystem)), getFieldName(gc, cpg))) {
						//
						found = true;
						//
					} else if (found) {
						//
						break;
						//
					} // if
						//
				} // if
					//
				if (!found) {
					//
					continue;
					//
				} // if
					//
				if (instruction instanceof LDC ldc && i > 0
						&& ArrayUtils.get(instructions, i - 1) instanceof INVOKESTATIC invokeStatic
						&& Objects.equals("java.lang.Runtime",
								InvokeInstructionUtil.getClassName(invokeStatic, cpg = ObjectUtils.getIfNull(cpg,
										() -> new ConstantPoolGen(javaClass.getConstantPool()))))) {
					//
					if (Objects.equals(key = testAndApply(x -> x != null && x.length > 0,
							StringUtils.split(Util.toString(LDCUtil.getValue(ldc, cpg)), ' '),
							x -> ArrayUtils.get(x, 0), null), "%1$s")) {
						//
						continue;
						//
					} // if
						//
					if (ivalue0 != null) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					ivalue0 = Unit.with(Pair.of(key, method));
					//
				} // if
					//
			} // for
				//
		} // try
			//
		return ivalue0;
		//
	}

	private static String getFieldName(final FieldInstruction instance, final ConstantPoolGen cpg) {
		return instance != null ? instance.getFieldName(cpg) : null;
	}

	private static ReferenceType getReferenceType(final GETSTATIC instance, final ConstantPoolGen cpg) {
		return instance != null ? instance.getReferenceType(cpg) : null;
	}

	private static byte[] getCode(final Code instance) throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_CODE_CODE, null, instance);
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

	private static Code getCode(final org.apache.bcel.classfile.Method instance) throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_CODE_METHOD, null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Code) {
				return (Code) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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

	@Test
	void testPlay() throws Throwable {
		//
		final Method play = AivisSpeechRestApiJPanel.class.getDeclaredMethod("play", byte[].class);
		//
		if (play != null) {
			//
			play.setAccessible(true);
			//
		} // if
			//
			// wav.wav
			//
		if (length(AudioSystem.getSourceLineInfo(new Line.Info(SourceDataLine.class))) > 0) {
			//
			Assertions.assertNull(invoke(play, null,
					decode(decoder, "UklGRiQAAABXQVZFZm10IBAAAAABAAEARKwAAIhYAQACABAAZGF0YQAAAAA=")));
			//
			// Windows Navigation Start.wma
			//
			Assertions.assertNull(invoke(play, null, decode(decoder,
					"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AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")));
			//
		} // if
			//
		if (Objects.equals(OperatingSystem.WINDOWS, operatingSystem)) {
			//
			final Iterable<File> iterable = Util
					.toList(Util.filter(
							testAndApply(Objects::nonNull,
									testAndApply(x -> x != null && x.exists() && x.isDirectory(),
											Util.toFile(Util.cast(Path.class,
													invoke(METHOD_SH_GET_KNOWN_FOLDER_PATH, null,
															KnownFolders.FOLDERID_Windows))),
											AivisSpeechRestApiJPanelTest::listFiles, null),
									Arrays::stream, null),
							x -> Objects.equals(Util.getName(x), "Media")));
			//
			if (IterableUtils.size(iterable) > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			final File[] fs = listFiles(
					testAndApply(x -> IterableUtils.size(x) == 1, iterable, x -> IterableUtils.get(x, 0), null));
			//
			if (fs != null) {
				//
				Arrays.sort(fs, (a, b) -> {
					//
					if (a != null && b != null) {
						//
						return Long.compare(a.length(), b.length());
						//
					} else if (a != null) {
						//
						return 1;
						//
					} // if
						//
					return -1;
					//
				});
				//
			} // if
				//
			File f = null;
			//
			ContentInfoUtil ciu = null;
			//
			for (int i = 0; fs != null && i < fs.length; i++) {
				//
				if ((f = ArrayUtils.get(fs, i)) == null || !f.isFile() || !Objects.equals(ContentType.WAV,
						getContentType(findMatch(ciu = ObjectUtils.getIfNull(ciu, ContentInfoUtil::new), f)))) {
					//
					continue;
					//
				} // if
					//

				Assertions.assertNull(invoke(play, null, Files.readAllBytes(Util.toPath(f))));
				//
				break;
				//
			} // for
				//
		} // if
			//
		if (Objects.equals(exec, Boolean.FALSE)) {
			//
			Throwable targetException = null;
			//
			try {
				//
				invoke(METHOD_PLAY, null, styleInfo, 0);
				//
			} catch (final InvocationTargetException e) {
				//
				targetException = e.getTargetException();
				//
			} // try
				//
			final String className = Util.getName(Util.getClass(targetException));
			//
			if (Objects.equals(OperatingSystem.LINUX, operatingSystem)) {
				//
				Assertions.assertEquals("java.io.IOException", className);
				//
			} else if (Objects.equals(OperatingSystem.WINDOWS, operatingSystem)) {
				//
				Assertions.assertEquals("java.lang.UnsupportedOperationException", className);
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} //
				//
		} else if (Objects.equals(exec, Boolean.TRUE)) {
			//
			if (Objects.equals(OperatingSystem.WINDOWS, operatingSystem)) {
				//
				Throwable throwable = null;
				//
				try {
					//
					invoke(METHOD_PLAY, null, styleInfo, 0);
					//
				} catch (final InvocationTargetException e) {
					//
					throwable = e != null ? e.getTargetException() : null;
					//
				} // try
					//
				Assertions.assertEquals(playMethodThrowExceptionClassName, Util.getName(Util.getClass(throwable)));
				//
			} else {
				//
				Assertions.assertNull(invoke(METHOD_PLAY, null, styleInfo, 0));
				//
				FieldUtils.writeDeclaredField(styleInfo, "voiceSamples", Collections.singletonList(null), true);
				//
				Assertions.assertNull(invoke(METHOD_PLAY, null, styleInfo, 0));
				//
			} // if
				//
		} // if
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

	private static ContentInfo findMatch(final ContentInfoUtil instnace, final File file) throws IOException {
		return instnace != null ? instnace.findMatch(file) : null;
	}

	@Test
	void testGetContentType() throws Throwable {
		//
		Assert.assertThrows(IllegalArgumentException.class, () -> getContentType(Util.toFile(Path.of("."))));
		//
		Assert.assertThrows(IllegalArgumentException.class, () -> getContentType(Util.toFile(Path.of("pom.xml"))));
		//
		// Windows Navigation Start.wma
		//
		final File file = File.createTempFile(nextAlphanumeric(RandomStringUtils.secureStrong(), 3), null);
		//
		try {
			//
			testAndAccept((a, b) -> b != null, file, decode(decoder,
					"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AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
					FileUtils::writeByteArrayToFile);
			//
			Assert.assertEquals(ContentType.WMA, Narcissus.invokeStaticMethod(METHOD_GET_CONTENT_TYPE_FILE, file));
			//
		} finally {
			//
			FileUtils.deleteQuietly(file);
			//
		} // try
			//
	}

	private static ContentType getContentType(final File file) throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_CONTENT_TYPE_FILE, null, file);
			if (obj == null) {
				return null;
			} else if (obj instanceof ContentType) {
				return (ContentType) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String nextAlphanumeric(final RandomStringUtils instance, final int count) throws Throwable {
		try {
			final Object obj = invoke(METHOD_NEXT_ALPHA_NUMERIC, null, instance, count);
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

	private static ContentType getContentType(final ContentInfo instance) throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_CONTENT_TYPE_CONTENT_INFO, null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof ContentType) {
				return (ContentType) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static File[] listFiles(final File instance) throws Throwable {
		try {
			final Object obj = invoke(METHOD_LIST_FILES, null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof File[]) {
				return (File[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFileExtension() throws Throwable {
		//
		// wav.wav
		//
		Assertions.assertEquals("wav",
				getFileExtension(decode(decoder, "UklGRiQAAABXQVZFZm10IBAAAAABAAEARKwAAIhYAQACABAAZGF0YQAAAAA=")));
		//
		try (final Workbook wb = new HSSFWorkbook(); final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			wb.write(baos);
			//
			Assertions.assertNull(getFileExtension(baos.toByteArray()));
			//
		} // try
			//
		Assertions.assertNull(getFileExtension(new ContentInfo(ContentType.HTML)));
		//
		final ContentInfo contentInfo = new ContentInfo(ContentType.OTHER);
		//
		Assertions.assertNull(getFileExtension(contentInfo));
		//
		FieldUtils.writeDeclaredField(contentInfo, "mimeType", "audio/mp4", true);
		//
		Assertions.assertNull(getFileExtension(contentInfo));
		//
		FieldUtils.writeDeclaredField(contentInfo, "name", "ISO", true);
		//
		Assertions.assertNull(getFileExtension(contentInfo));
		//
		FieldUtils.writeDeclaredField(contentInfo, "message", "ISO Media, MPEG v4 system, iTunes AAC-LC", true);
		//
		Assertions.assertNotNull(getFileExtension(contentInfo));
		//
	}

	private static byte[] decode(final Decoder instance, final String string) {
		return instance != null ? instance.decode(string) : null;
	}

	private static String getFileExtension(final byte[] bs) throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_FILE_EXTENSION_BYTE_ARRAY, null, bs);
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

	private static String getFileExtension(final ContentInfo contentInfo) throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_FILE_EXTENSION_CONTENT_INFO, null, contentInfo);
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
	void testIsSupportedAudioFormat() throws Throwable {
		//
		Assertions.assertEquals(Boolean.FALSE, invoke(METHOD_IS_SUPPORTED_AUDIO_FORMAT, null, getBytes(EMPTY)));
		//
	}

	@Test
	void testTestAndTest() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertEquals(Boolean.FALSE,
				invoke(METHOD_TEST_AND_TEST, null, Predicates.alwaysTrue(), null, Predicates.alwaysFalse()));
		//
	}

	@Test
	void testGetMessage() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_GET_MESSAGE, null, new Throwable()));
		//
	}

	@Test
	void testSet() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(
				invoke(METHOD_SET, null, new MigLayout(), Collections.singletonMap("layoutConstraints", null)));
		//
		Assertions.assertNull(
				invoke(METHOD_SET, null, new MigLayout(), Collections.singletonMap("layoutConstraints", "")));
		//
		Assertions.assertNull(invoke(METHOD_SET, null, new MigLayout(), Collections.singletonMap("constraintMap", "")));
		//
	}

	@Test
	void testIh() throws Throwable {
		//
		final InvocationHandler invocationHandler = Util.cast(InvocationHandler.class, Narcissus
				.allocateInstance(Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$IH")));
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(invocationHandler,
				Reflection.newProxy(Transferable.class, invocationHandler), null, null));
		//
		Assertions.assertThrows(Throwable.class,
				() -> invoke(invocationHandler, Reflection.newProxy(Iterable.class, invocationHandler), null, null));
		//
	}

	private static Object invoke(final InvocationHandler instance, final Object proxy, final Method method,
			final Object[] args) throws Throwable {
		return instance != null ? instance.invoke(proxy, method, args) : null;
	}

	@Test
	void testSpeaker() throws NoSuchMethodException {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.AivisSpeechRestApiJPanel$Speaker");
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(Util.getDeclaredMethod(clz, "getName", clz), (Object) null));
		//
	}

	@Test
	void testIsClientError() throws Throwable {
		//
		if (mh != null) {
			//
			mh.responseCode = HttpStatus.BAD_REQUEST_400;
			//
		} // if
			//
		final HttpURLConnection httpURLConnection = ProxyUtil.createProxy(HttpURLConnection.class, mh, x -> {
			//
			final Constructor<?> constructor = x != null ? x.getConstructor(URL.class) : null;
			//
			return constructor != null ? constructor.newInstance((Object) null) : null;
			//
		});
		//
		Assertions.assertTrue(isClientError(httpURLConnection));
		//
		if (mh != null) {
			//
			mh.responseCode = HttpStatus.OK_200;
			//
		} // if
			//
		Assertions.assertFalse(isClientError(httpURLConnection));
		//
	}

	private static boolean isClientError(final HttpURLConnection instance) throws Throwable {
		try {
			final Object obj = invoke(METHOD_IS_CLIENT_ERROR, null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testVersion() throws Throwable {
		//
		Assertions.assertNull(version(HostAndPort.fromHost("")));
		//
		Assertions.assertNull(version(HostAndPort.fromParts("", 0)));
		//
	}

	private static String version(final HostAndPort hostAndPort) throws Throwable {
		try {
			final Object obj = invoke(METHOD_VERSION, null, hostAndPort);
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
	void testGetModel() throws Throwable {
		//
		Assertions.assertNull(invoke(METHOD_GET_MODEL, null, jComboBox));
		//
	}

	@Test
	void testCoreVersions() throws Throwable {
		//
		Assertions.assertNull(coreVersions(HostAndPort.fromHost("")));
		//
		Assertions.assertNull(coreVersions(HostAndPort.fromParts("", 0)));
		//
	}

	private static Iterable<?> coreVersions(final HostAndPort hostAndPort) throws Throwable {
		try {
			final Object obj = invoke(METHOD_CORE_VERSIONS, null, hostAndPort);
			if (obj == null) {
				return null;
			} else if (obj instanceof Iterable) {
				return (Iterable<?>) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToIterable() throws Throwable {
		//
		Assertions.assertThrows(IllegalStateException.class, () -> toIterable(Collections.emptyMap()));
		// s
		final Iterable<?> iterable = Collections.emptyList();
		//
		Assertions.assertSame(iterable, toIterable(iterable));
		//
		final String string = "a";
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(string), IterableUtils.toList(
				toIterable(ObjectMapperUtil.readValue(objectMapper, StringUtils.wrap(string, '"'), Object.class)))));
		//
		final Number number = Integer.valueOf(1);
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(number), IterableUtils
				.toList(toIterable(ObjectMapperUtil.readValue(objectMapper, Util.toString(number), Object.class)))));
		//
		final Boolean b = Boolean.TRUE;
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(b), IterableUtils
				.toList(toIterable(ObjectMapperUtil.readValue(objectMapper, Util.toString(b), Object.class)))));
		//
	}

	private static Iterable<?> toIterable(final Object instance) throws Throwable {
		try {
			final Object obj = invoke(METHOD_TO_ITERABLE, null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Iterable) {
				return (Iterable<?>) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}