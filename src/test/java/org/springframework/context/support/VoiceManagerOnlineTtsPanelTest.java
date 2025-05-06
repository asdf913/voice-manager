package org.springframework.context.support;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ARETURN;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.TripleUtil;
import org.htmlunit.html.DomNode;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Functions;
import org.meeuw.functional.Predicates;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.TriPredicate;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.InputStreamSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class VoiceManagerOnlineTtsPanelTest {

	private static Method METHOD_GET_LAYOUT_MANAGER, METHOD_TEST_AND_APPLY4, METHOD_TEST_AND_APPLY5,
			METHOD_TEST_AND_APPLY6, METHOD_TEST_AND_ACCEPT3, METHOD_TEST_AND_ACCEPT4, METHOD_PREVIOUS_ELEMENT_SIBLING,
			METHOD_TEST_AND_RUN_THROWS, METHOD_SELECT_STREAM, METHOD_SET_EDITABLE, METHOD_SET_CONTENTS,
			METHOD_GET_SYSTEM_CLIPBOARD, METHOD_IIF, METHOD_GET_VOICE, METHOD_EQUALS, METHOD_SHOW_SAVE_DIALOG,
			METHOD_SET_ENABLED, METHOD_SHA512HEX, METHOD_CREATE_INPUT_STREAM_SOURCE, METHOD_CAN_READ, METHOD_AND,
			METHOD_ADD_LIST_DATA_LISTENER, METHOD_GET_ANNOTATED_ELEMENT_OBJECT_ENTRY,
			METHOD_GET_STRING_OBJECT_ENTRY = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = VoiceManagerOnlineTtsPanel.class;
		//
		(METHOD_GET_LAYOUT_MANAGER = clz.getDeclaredMethod("getLayoutManager", Object.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				BiFunction.class, BiFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY6 = clz.getDeclaredMethod("testAndApply", TriPredicate.class, Object.class, Object.class,
				Object.class, TriFunction.class, TriFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3 = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				FailableConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				FailableBiConsumer.class)).setAccessible(true);
		//
		(METHOD_PREVIOUS_ELEMENT_SIBLING = clz.getDeclaredMethod("previousElementSibling", Element.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_RUN_THROWS = clz.getDeclaredMethod("testAndRunThrows", Boolean.TYPE, ThrowingRunnable.class))
				.setAccessible(true);
		//
		(METHOD_SELECT_STREAM = clz.getDeclaredMethod("selectStream", Element.class, String.class)).setAccessible(true);
		//
		(METHOD_SET_EDITABLE = clz.getDeclaredMethod("setEditable", JTextComponent.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIPBOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_IIF = clz.getDeclaredMethod("iif", Boolean.TYPE, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_VOICE = clz.getDeclaredMethod("getVoice", PropertyResolver.class, String.class, ListModel.class,
				Map.class)).setAccessible(true);
		//
		(METHOD_EQUALS = clz.getDeclaredMethod("equals", Number.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_SHOW_SAVE_DIALOG = clz.getDeclaredMethod("showSaveDialog", JFileChooser.class, Component.class))
				.setAccessible(true);
		//
		(METHOD_SET_ENABLED = clz.getDeclaredMethod("setEnabled", AbstractButton.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_SHA512HEX = clz.getDeclaredMethod("sha512Hex", clz, ObjectMapper.class)).setAccessible(true);
		//
		(METHOD_CREATE_INPUT_STREAM_SOURCE = clz.getDeclaredMethod("createInputStreamSource", File.class))
				.setAccessible(true);
		//
		(METHOD_CAN_READ = clz.getDeclaredMethod("canRead", File.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_ADD_LIST_DATA_LISTENER = clz.getDeclaredMethod("addListDataListener", ListModel.class,
				ListDataListener.class)).setAccessible(true);
		//
		(METHOD_GET_ANNOTATED_ELEMENT_OBJECT_ENTRY = clz.getDeclaredMethod("getAnnotatedElementObjectEntry",
				Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_STRING_OBJECT_ENTRY = clz.getDeclaredMethod("getStringObjectEntry", Entry.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Map<Object, Object> properties = null;

		private Object[] elements = null;

		private Map<String, Object> beansOfType = null;

		@Override
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			if (Objects.equals(Util.getReturnType(method), Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof ListableBeanFactory && Objects.equals(methodName, "getBeansOfType")) {
				//
				return beansOfType;
				//
			} // if
				//
			if (Objects.equals(Util.getName(method.getDeclaringClass()),
					"org.springframework.context.support.VoiceManagerOnlineTtsPanel$Name")
					&& Objects.equals(methodName, "value")) {
				//
				return null;
				//
			} else if (proxy instanceof EnvironmentCapable && Objects.equals(methodName, "getEnvironment")) {
				//
				return null;
				//
			} else if (proxy instanceof ListModel) {
				//
				if (Objects.equals(methodName, "getElementAt") && args != null && args[0] instanceof Integer i) {
					//
					return ArrayUtils.get(elements, i);
					//
				} else if (Objects.equals(methodName, "getSize")) {
					//
					return elements != null ? elements.length : 0;
					//
				} // if
					//
			} else if (proxy instanceof PropertyResolver) {
				//
				if (Objects.equals(methodName, "containsProperty") && args != null && args.length > 0) {
					//
					return Util.containsKey(properties, args[0]);
					//
				} else if (Objects.equals(methodName, "getProperty") && args != null && args.length > 0) {
					//
					return Util.get(properties, args[0]);
					//
				} // if
					//
			} else if (proxy instanceof ApplicationContext
					&& Objects.equals(methodName, "getAutowireCapableBeanFactory")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static class MH implements MethodHandler {

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (Boolean.logicalOr(
					self instanceof DomNode
							&& Util.contains(Arrays.asList("querySelector", "getNextElementSibling"), methodName),
					self instanceof Toolkit && Objects.equals(methodName, "getSystemClipboard"))) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManagerOnlineTtsPanel instance = null;

	private IH ih = null;

	private MH mh = null;

	private Element element = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		instance = new VoiceManagerOnlineTtsPanel();
		//
		ih = new IH();
		//
		element = Util.cast(Element.class, Narcissus.allocateInstance(Element.class));
		//
		mh = new MH();
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
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent("", 0, null)));
		//
		// btnExecute
		//
		final AbstractButton btnExecute = new JButton();
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnExecute, 0, null)));
		//
		// btnCopy
		//
		final AbstractButton btnCopy = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopy", btnCopy, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopy, 0, null)));
		//
		// btnDownload
		//
		final AbstractButton btnDownload = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnDownload", btnDownload, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnDownload, 0, null)));
		//
		// btnPlayAudio
		//
		final AbstractButton btnPlayAudio = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnPlayAudio", btnPlayAudio, true);
		//
		final ActionEvent actionEvent = new ActionEvent(btnPlayAudio, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		FieldUtils.writeDeclaredField(instance, "speechApi", Reflection.newProxy(SpeechApi.class, ih), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
		FieldUtils.writeDeclaredField(instance, "entry", Pair.of(null, Util.toFile(Path.of("."))), true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(actionEvent));
		//
	}

	@Test
	void testIntervalAdded() {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final DefaultListModel<?> dlm = new DefaultListModel<>();
		//
		final ListDataEvent lde = new ListDataEvent(dlm, 0, 0, 0);
		//
		Assertions.assertDoesNotThrow(() -> instance.intervalAdded(lde));
		//
		dlm.addElement(null);
		//
		Assertions.assertDoesNotThrow(() -> instance.intervalAdded(lde));
		//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		if (ih != null) {
			//
			ih.beansOfType = Collections.singletonMap(null, null);
			//
		} // if
			//
		instance.setApplicationContext(Reflection.newProxy(ApplicationContext.class, ih));
		//
		Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
		//
		if (ih != null) {
			//
			ih.beansOfType = Collections.singletonMap(null, Reflection.newProxy(SpeechApi.class, ih));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.afterPropertiesSet());
		//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Class<?> clz = VoiceManagerOnlineTtsPanel.class;
		//
		final Method[] ms = Util.getDeclaredMethods(clz);
		//
		Method m = null;
		//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		Object invoke = null;
		//
		JavaClass javaClass = null;
		//
		ConstantPoolGen cpg = null;
		//
		Instruction[] instructions = null;
		//
		org.apache.bcel.classfile.Method cfMethod = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()
					|| Boolean.logicalAnd(Objects.equals(Util.getName(m), "and"),
							Arrays.equals(Util.getParameterTypes(m),
									new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class }))) {
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
				if (Objects.equals(parameterType = parameterTypes[j], Integer.TYPE)) {
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
			} // for
				//
			os = toArray(collection);
			//
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE), m.getReturnType())) {
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
				invoke = Narcissus.invokeMethod(new VoiceManagerOnlineTtsPanel(), m, os);
				//
				if (javaClass == null) {
					//
					javaClass = getJavaClass(clz);
					//
				} // if
					//
				if ((cfMethod = JavaClassUtil.getMethod(javaClass, m)) != null) {
					//
					if (cpg == null) {
						//
						cpg = new ConstantPoolGen(cfMethod.getConstantPool());
						//
					} // if
						//
					if ((instructions = InstructionListUtil
							.getInstructions(new MethodGen(cfMethod, null, cpg).getInstructionList())) != null
							&& instructions.length == 2 && ArrayUtils.get(instructions, 0) instanceof LDC ldc
							&& ldc.getValue(cpg) != null && ArrayUtils.get(instructions, 1) instanceof ARETURN) {
						//
						Assertions.assertNotNull(invoke, toString);
						//
						continue;
						//
					} // if
						//
				} // if
					//
				if (Objects.equals(m.getReturnType(), Boolean.TYPE)) {
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

	private static JavaClass getJavaClass(final Class<?> clz) throws Throwable {
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(String.format("/%1$s.class", StringUtils.replace(clz.getName(), ".", "/")))
				: null) {
			//
			return ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null));
			//
		} // try
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testGetLayoutManager() throws Throwable {
		//
		Assertions.assertNull(getLayoutManager(null, Collections.singleton(null)));
		//
		final LayoutManager layoutManager = Reflection.newProxy(LayoutManager.class, ih);
		//
		Assertions.assertNull(getLayoutManager(layoutManager, Collections.singleton(Pair.of(null, layoutManager))));
		//
	}

	private static LayoutManager getLayoutManager(final Object acbf, final Iterable<Entry<String, Object>> entrySet)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_LAYOUT_MANAGER.invoke(null, acbf, entrySet);
			if (obj == null) {
				return null;
			} else if (obj instanceof LayoutManager) {
				return (LayoutManager) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.biAlwaysTrue(), null, null, Functions.biAlways(null), null));
		//
		Assertions.assertNull(testAndApply(Predicates.triAlwaysTrue(), null, null, null, null, null));
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

	private static <T, U, V, R> R testAndApply(final TriPredicate<T, U, V> predicate, final T t, final U u, final V v,
			final TriFunction<T, U, V, R> functionTrue, final TriFunction<T, U, V, R> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY6.invoke(null, predicate, t, u, v, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, FailableConsumer.nop()));
		//
		final BiPredicate<?, ?> biAlwaysTrue = Predicates.biAlwaysTrue();
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(biAlwaysTrue, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(biAlwaysTrue, null, null, FailableBiConsumer.nop()));
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> instance, @Nullable final T value,
			final FailableConsumer<T, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT3.invoke(null, instance, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> instance, final T t,
			final U u, final FailableBiConsumer<T, U, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT4.invoke(null, instance, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPreviousElementSibling() throws Throwable {
		//
		Assertions.assertNull(previousElementSibling(element));
		//
	}

	private static Element previousElementSibling(final Element instance) throws Throwable {
		try {
			final Object obj = METHOD_PREVIOUS_ELEMENT_SIBLING.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Element) {
				return (Element) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testValue() throws NoSuchMethodException {
		//
		final Class<?> clz = Util.forName("org.springframework.context.support.VoiceManagerOnlineTtsPanel$Name");
		//
		Assertions.assertNull(Narcissus.invokeStaticMethod(
				VoiceManagerOnlineTtsPanel.class.getDeclaredMethod("value", clz), Reflection.newProxy(clz, ih)));
		//
	}

	@Test
	void testTestAndRunThrows() {
		//
		Assertions.assertDoesNotThrow(() -> testAndRunThrows(true, null));
		//
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean b,
			final ThrowingRunnable<E> throwingRunnable) throws Throwable {
		try {
			METHOD_TEST_AND_RUN_THROWS.invoke(null, b, throwingRunnable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSelectStream() throws Throwable {
		//
		Assertions.assertNull(selectStream(element, null));
		//
		Assertions.assertNull(selectStream(element, ""));
		//
		Assertions.assertNull(selectStream(element, " "));
		//
		Assertions.assertNotNull(selectStream(element, "a"));
		//
	}

	private static Stream<Element> selectStream(final Element instance, final String cssQuery) throws Throwable {
		try {
			final Object obj = METHOD_SELECT_STREAM.invoke(null, instance, cssQuery);
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
	void testSetEditable() {
		//
		Assertions.assertDoesNotThrow(() -> setEditable(new JTextField(), false));
		//
	}

	private static void setEditable(final JTextComponent instance, final boolean b) throws Throwable {
		try {
			METHOD_SET_EDITABLE.invoke(null, instance, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetContents() {
		//
		Assertions.assertDoesNotThrow(
				() -> setContents(Util.cast(Clipboard.class, Narcissus.allocateInstance(Clipboard.class)), null, null));
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
	void testGetSystemClipboard() throws Throwable {
		//
		Assertions.assertNull(getSystemClipboard(ProxyUtil.createProxy(Toolkit.class, mh)));
		//
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SYSTEM_CLIPBOARD.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Clipboard) {
				return (Clipboard) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
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

	private static <T> T iif(final boolean condition, final T valueTrue, final T valueFalse) throws Throwable {
		try {
			return (T) METHOD_IIF.invoke(null, condition, valueTrue, valueFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetVoice() throws Throwable {
		//
		final PropertyResolver propertyResolver = Reflection.newProxy(PropertyResolver.class, ih);
		//
		final ListModel<?> listModel = Reflection.newProxy(ListModel.class, ih);
		//
		Assertions.assertEquals(null, getVoice(propertyResolver, null, listModel, null));
		//
		if (ih != null) {
			//
			Util.put(ih.properties = ObjectUtils.getIfNull(ih.properties, LinkedHashMap::new), null, null);
			//
			ih.elements = new Object[] { null };
			//
		} // if
			//
		Assertions.assertEquals(Unit.with(null), getVoice(propertyResolver, null, listModel, null));
		//
		final String a = "a";
		//
		Assertions.assertNull(getVoice(propertyResolver, null, listModel, Collections.singletonMap(null, a)));
		//
		if (ih != null) {
			//
			Util.put(ih.properties = ObjectUtils.getIfNull(ih.properties, LinkedHashMap::new), null, a);
			//
			ih.elements = new Object[] { a };
			//
		} // if
			//
		Assertions.assertEquals(Unit.with(a), getVoice(propertyResolver, null, listModel, null));
		//
		if (ih != null) {
			//
			ih.elements = new Object[] { a, a };
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getVoice(propertyResolver, null, listModel, null));
		//
	}

	private static IValue0<Object> getVoice(final PropertyResolver propertyResolver, final String propertyKey,
			final ListModel<?> listModel, final Map<?, ?> map) throws Throwable {
		try {
			final Object obj = METHOD_GET_VOICE.invoke(null, propertyResolver, propertyKey, listModel, map);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testEquals() throws Throwable {
		//
		final int one = 1;
		//
		final Number number = Integer.valueOf(one);
		//
		Assertions.assertTrue(equals(number, one));
		//
		Assertions.assertFalse(equals(Integer.valueOf(one + one), one));
		//
	}

	private static boolean equals(final Number a, final int b) throws Throwable {
		try {
			final Object obj = METHOD_EQUALS.invoke(null, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testShowSaveDialog() throws Throwable {
		//
		Assertions.assertNull(
				showSaveDialog(Util.cast(JFileChooser.class, Narcissus.allocateInstance(JFileChooser.class)), null));
		//
	}

	private static Integer showSaveDialog(final JFileChooser instance, final Component parent) throws Throwable {
		try {
			final Object obj = METHOD_SHOW_SAVE_DIALOG.invoke(null, instance, parent);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetEnabled() {
		//
		Assertions.assertDoesNotThrow(
				() -> setEnabled(Util.cast(AbstractButton.class, Narcissus.allocateInstance(JButton.class)), false));
		//
	}

	private static void setEnabled(final AbstractButton instance, final boolean b) throws Throwable {
		try {
			METHOD_SET_ENABLED.invoke(null, instance, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSha512Hex() throws Throwable {
		//
		Assertions.assertEquals(
				"04f8ff2682604862e405bf88de102ed7710ac45c1205957625e4ee3e5f5a2241e453614acc451345b91bafc88f38804019c7492444595674e94e8cf4be53817f",
				sha512Hex(null, new ObjectMapper()));
		//
	}

	private static String sha512Hex(final VoiceManagerOnlineTtsPanel instance, final ObjectMapper objectMapper)
			throws Throwable {
		try {
			final Object obj = METHOD_SHA512HEX.invoke(null, instance, objectMapper);
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
	void testCreateInputStreamSource() throws Throwable {
		//
		Assertions.assertNull(createInputStreamSource(Util.toFile(Path.of("."))));
		//
		final File file = Util.toFile(Path.of("pom.xml"));
		//
		if (Util.exists(file)) {
			//
			Assertions.assertNotNull(createInputStreamSource(file));
			//
		} else {
			//
			Assertions.assertNull(createInputStreamSource(file));
			//
		} // if
			//
	}

	private static InputStreamSource createInputStreamSource(final File file) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_INPUT_STREAM_SOURCE.invoke(null, file);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStreamSource) {
				return (InputStreamSource) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCanRead() throws Throwable {
		//
		Assertions.assertTrue(canRead(Util.toFile(Path.of("."))));
		//
	}

	private static boolean canRead(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_CAN_READ.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnd() throws Throwable {
		//
		Assertions.assertTrue(and(true, true, null));
		//
		Assertions.assertFalse(and(true, true, false));
		//
		Assertions.assertTrue(and(true, true, true));
		//
	}

	private static boolean and(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddListDataListener() {
		//
		Assertions.assertDoesNotThrow(() -> addListDataListener(Reflection.newProxy(ListModel.class, ih), null));
		//
	}

	private static void addListDataListener(final ListModel<?> instance, final ListDataListener listener)
			throws Throwable {
		try {
			METHOD_ADD_LIST_DATA_LISTENER.invoke(null, instance, listener);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAnnotatedElementObjectEntry() throws Throwable {
		//
		final VoiceManagerOnlineTtsPanel vmotp = new VoiceManagerOnlineTtsPanel();
		//
		Assertions.assertThrows(IllegalStateException.class, () -> getAnnotatedElementObjectEntry(vmotp, null));
		//
		final Entry<Field, Object> entry = Util.orElse(Util.map(
				Util.stream(testAndApply(Objects::nonNull, Util.getClass(vmotp), FieldUtils::getAllFieldsList, null)),
				f -> Util.isStatic(f) ? Pair.of(f, Narcissus.getStaticField(f)) : null).findFirst(), null);
		//
		Assertions.assertEquals(entry, getAnnotatedElementObjectEntry(vmotp, Util.getValue(entry)));
		//
	}

	private static Entry<AnnotatedElement, Object> getAnnotatedElementObjectEntry(final Object instance,
			final Object value) throws Throwable {
		try {
			final Object obj = METHOD_GET_ANNOTATED_ELEMENT_OBJECT_ENTRY.invoke(null, instance, value);
			if (obj == null) {
				return null;
			} else if (obj instanceof Entry) {
				return (Entry) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetStringObjectEntry() throws Throwable {
		//
		final VoiceManagerOnlineTtsPanel vmotp = new VoiceManagerOnlineTtsPanel();
		//
		Assertions.assertNull(getStringObjectEntry(null));
		//
		final Collection<MutableTriple<Field, String, Object>> entries = Util.toList(Util.filter(Util.map(
				Util.stream(testAndApply(Objects::nonNull, Util.getClass(vmotp), FieldUtils::getAllFieldsList, null)),
				f -> {
					//
					final Annotation[] as = f != null ? f.getDeclaredAnnotations() : null;
					//
					Annotation a = null;
					//
					Class<?> clz = null;
					//
					List<Map<?, ?>> maps = null;
					//
					Collection<?> values = null;
					//
					for (int i = 0; as != null && i < as.length; i++) {
						//
						if ((a = as[i]) == null) {
							//
							continue;
							//
						} // if
							//
						if (Objects.equals(Util.getName(a.annotationType()),
								"org.springframework.context.support.VoiceManagerOnlineTtsPanel$Name")
								&& Proxy.isProxyClass(Util.getClass(a))) {
							//
							final Object invocationHandler = Proxy.getInvocationHandler(a);
							//
							if (IterableUtils
									.size(values = Util.values(Util.cast(Map.class,
											IterableUtils.size(maps = Util.toList(Util.map(
													Util.filter(
															Util.stream((clz = Util.getClass(invocationHandler)) != null
																	? FieldUtils.getAllFieldsList(clz)
																	: null),
															x -> Util.getType(x) != null
																	&& Util.getType(x).isAssignableFrom(Map.class)),
													x -> {
														//
														return Util.cast(Map.class,
																Util.isStatic(x) ? Narcissus.getStaticField(x)
																		: Narcissus.getField(invocationHandler, x));
														//
													}))) == 1 ? IterableUtils.get(maps, 0) : null))) == 1) {
								//
								return MutableTriple.of(f, Util.toString(IterableUtils.get(values, 0)),
										Util.isStatic(f) ? Narcissus.getStaticField(f) : Narcissus.getField(vmotp, f));
								//
							} // if
								//
						} // if
							//
					} // for
						//
					return null;
					//
				}), Objects::nonNull));
		//
		final MutableTriple<Field, String, Object> triple = Util.orElse(Util.filter(Util.stream(entries), x -> {
			return JTextComponent.class.isAssignableFrom(Util.getType(TripleUtil.getLeft(x)));
		}).findFirst(), null);
		//
		final MutablePair<AnnotatedElement, Object> entry = MutablePair.of(TripleUtil.getLeft(triple),
				TripleUtil.getRight(triple));
		//
		final String string = TripleUtil.getMiddle(triple);
		//
		Assertions.assertEquals(Pair.of(string, null), getStringObjectEntry(entry));
		//
		if (entry != null) {
			//
			entry.setValue(new JTextField(string));
			//
		} // if
			//
		Assertions.assertEquals(Pair.of(string, string), getStringObjectEntry(entry));
		//
	}

	private static Entry<String, Object> getStringObjectEntry(final Entry<AnnotatedElement, Object> entry)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_STRING_OBJECT_ENTRY.invoke(null, entry);
			if (obj == null) {
				return null;
			} else if (obj instanceof Entry) {
				return (Entry) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}