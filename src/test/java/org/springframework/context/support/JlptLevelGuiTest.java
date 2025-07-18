package org.springframework.context.support;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ATHROW;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.NEW;
import org.apache.bcel.generic.ObjectType;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oxbow.swingbits.util.OperatingSystem;
import org.oxbow.swingbits.util.OperatingSystemUtil;
import org.springframework.beans.factory.InitializingBean;

import com.github.hal4j.uritemplate.URIBuilder;
import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import domain.JlptVocabulary;
import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class JlptLevelGuiTest {

	private static final String EMPTY = "";

	private static Method METHOD_SET_PREFERRED_WIDTH, METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_TEST_AND_APPLY,
			METHOD_SET_CONTENTS, METHOD_INVOKE, METHOD_IIF, METHOD_RUN, METHOD_SET_JLPT_VOCABULARY_AND_LEVEL,
			METHOD_GET_LEVEL, METHOD_FOR_EACH_STREAM, METHOD_TEST_AND_ACCEPT, METHOD_BROWSE,
			METHOD_ADD_DOCUMENT_LISTENER, METHOD_SET_SELECTED_INDICES, METHOD_TO_URI, METHOD_MAX = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = JlptLevelGui.class;
		//
		(METHOD_SET_PREFERRED_WIDTH = clz.getDeclaredMethod("setPreferredWidth", Integer.TYPE, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_INVOKE = clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_IIF = clz.getDeclaredMethod("iif", Boolean.TYPE, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_RUN = clz.getDeclaredMethod("run", Boolean.TYPE, Runnable.class)).setAccessible(true);
		//
		(METHOD_SET_JLPT_VOCABULARY_AND_LEVEL = clz.getDeclaredMethod("setJlptVocabularyAndLevel", clz))
				.setAccessible(true);
		//
		(METHOD_GET_LEVEL = clz.getDeclaredMethod("getLevel", JlptVocabulary.class)).setAccessible(true);
		//
		(METHOD_FOR_EACH_STREAM = clz.getDeclaredMethod("forEach", Iterable.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class, Consumer.class))
				.setAccessible(true);
		//
		(METHOD_BROWSE = clz.getDeclaredMethod("browse", Desktop.class, URI.class)).setAccessible(true);
		//
		(METHOD_ADD_DOCUMENT_LISTENER = clz.getDeclaredMethod("addDocumentListener", Document.class,
				DocumentListener.class)).setAccessible(true);
		//
		(METHOD_SET_SELECTED_INDICES = clz.getDeclaredMethod("setSelectedIndices", JList.class, int[].class))
				.setAccessible(true);
		//
		(METHOD_TO_URI = clz.getDeclaredMethod("toURI", URIBuilder.class)).setAccessible(true);
		//
		(METHOD_MAX = clz.getDeclaredMethod("max", Stream.class, Comparator.class)).setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private Document document = null;

		private Iterator<?> iterator = null;

		private Optional<?> max = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Void.TYPE, method != null ? method.getReturnType() : null)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Iterable) {
				//
				if (Objects.equals(methodName, "iterator")) {
					//
					return iterator;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "max")) {
					//
					return max;
					//
				} // if
					//
			} else if (proxy instanceof DocumentEvent && Objects.equals(methodName, "getDocument")) {
				//
				return document;
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
			if (self instanceof Component) {
				//
				if (Objects.equals(methodName, "getPreferredSize")) {
					//
					return null;
					//
				} // if
					//
			} else if (self instanceof Toolkit) {
				//
				if (Objects.equals(methodName, "getSystemClipboard")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private JlptLevelGui instance = null;

	private IH ih = null;

	private DocumentEvent documentEvent = null;

	private Document document = null;

	private MH mh = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			final Constructor<JlptLevelGui> constructor = JlptLevelGui.class.getDeclaredConstructor();
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			instance = constructor != null ? constructor.newInstance() : null;
			//
		} else {
			//
			instance = Util.cast(JlptLevelGui.class, Narcissus.allocateInstance(JlptLevelGui.class));
			//
		} // if
			//
		documentEvent = Reflection.newProxy(DocumentEvent.class, ih = new IH());
		//
		document = Reflection.newProxy(Document.class, ih = new IH());
		//
		mh = new MH();
		//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
		if (instance != null) {
			//
			instance.setJlptLevels(Collections.emptyList());
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
	}

	private static void afterPropertiesSet(final InitializingBean instance) throws Exception {
		if (instance != null) {
			instance.afterPropertiesSet();
		}
	}

	@Test
	void testActionPerformed() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(EMPTY, 0, null)));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfJson", new JTextField(), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		// org.springframework.context.support.JlptLevelGui.btnCopy
		//
		final AbstractButton btnCopy = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCopy", btnCopy, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnCopy, 0, null)));
		//
		// org.springframework.context.support.JlptLevelGui.btnCompare
		//
		final AbstractButton btnCompare = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCompare", btnCompare, true);
			//
		} // if
			//
		final ActionEvent actionEventBtnCompare = new ActionEvent(btnCompare, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnCompare));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jlCompare", new JLabel(), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnCompare));
		//
		// org.springframework.context.support.JlptLevelGui.btnVisitJMdictDB
		//
		final AbstractButton btnVisitJMdictDB = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnVisitJMdictDB", btnVisitJMdictDB, true);
			//
		} // if
			//
		final ActionEvent actionEventBtnVisitJMdictDB = new ActionEvent(btnVisitJMdictDB, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnVisitJMdictDB));
		//
		// org.springframework.context.support.JlptLevelGui.cbmJlptVocabulary
		//
		final MutableComboBoxModel<JlptVocabulary> cbmJlptVocabulary = new DefaultComboBoxModel<>();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "cbmJlptVocabulary", cbmJlptVocabulary, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnVisitJMdictDB));
		//
		final JlptVocabulary jv = new JlptVocabulary();
		//
		// domain.JlptVocabulary.jmdictSeq
		//
		FieldUtils.writeDeclaredField(jv, "jmdictSeq", Integer.valueOf(1), true);
		//
		cbmJlptVocabulary.addElement(jv);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, actionEventBtnVisitJMdictDB));
		//
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent e) {
		if (instance != null) {
			instance.actionPerformed(e);
		}
	}

	@Test
	void testChangedUpdate() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.changedUpdate(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testInsertUpdate() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> insertUpdate(instance, null));
		//
		if (ih != null) {
			//
			ih.document = document;
			//
		} // if
			//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfTextDocument", document, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> insertUpdate(instance, documentEvent));
		//
	}

	private static void insertUpdate(final DocumentListener instance, final DocumentEvent evt) {
		if (instance != null) {
			instance.insertUpdate(evt);
		}
	}

	@Test
	void testRemoveUpdate() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, null));
		//
		if (ih != null) {
			//
			ih.document = document;
			//
		} // if
			//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfTextDocument", document, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, documentEvent));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfTextDocument", null, true);
			//
		} // if
			//
			// org.springframework.context.support.JlptLevelGui.cbmJlptVocabulary
			//
		final MutableComboBoxModel<JlptVocabulary> cbmJlptVocabulary = new DefaultComboBoxModel<>();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "cbmJlptVocabulary", cbmJlptVocabulary, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, documentEvent));
		//
		final String A = "A";
		//
		// org.springframework.context.support.JlptLevelGui.tfText
		//
		final JTextComponent tfText = new JTextField(A);
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfText", tfText, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, documentEvent));
		//
		// org.springframework.context.support.JlptLevelGui.jlptVocabularyList
		//
		final JlptVocabulary jv = new JlptVocabulary();
		//
		final IValue0<List<JlptVocabulary>> jlptVocabularyList = Unit.with(Arrays.asList(null, jv));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jlptVocabularyList", jlptVocabularyList, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, documentEvent));
		//
		FieldUtils.writeDeclaredField(jv, "kanji", A, true);
		//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, documentEvent));
		//
		// org.springframework.context.support.JlptLevelGui.cbmJlptLevel
		//
		final ComboBoxModel<String> cbmJlptLevel = new DefaultComboBoxModel<>();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "cbmJlptLevel", cbmJlptLevel, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> removeUpdate(instance, documentEvent));
		//
	}

	private static void removeUpdate(final DocumentListener instance, final DocumentEvent evt) {
		if (instance != null) {
			instance.removeUpdate(evt);
		}
	}

	@Test
	void testItemStateChanged() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> itemStateChanged(instance, null));
		//
		final ItemEvent itemEvent = Util.cast(ItemEvent.class, Narcissus.allocateInstance(ItemEvent.class));
		//
		if (itemEvent != null) {
			//
			itemEvent.setSource(EMPTY);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> itemStateChanged(instance, itemEvent));
		//
		// org.springframework.context.support.JlptLevelGui.jcbJlptVocabulary
		//
		final JComboBox<?> jcbJlptVocabulary = new JComboBox<>();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jcbJlptVocabulary", jcbJlptVocabulary, true);
			//
		} // if
			//
		if (itemEvent != null) {
			//
			itemEvent.setSource(jcbJlptVocabulary);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> itemStateChanged(instance, itemEvent));
		//
	}

	private static void itemStateChanged(final ItemListener instance, final ItemEvent evt) {
		if (instance != null) {
			instance.itemStateChanged(evt);
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testSetPreferredWidth() {
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, null));
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, Collections.singleton(null)));
		//
		Assertions.assertDoesNotThrow(
				() -> setPreferredWidth(0, Collections.singleton(ProxyUtil.createProxy(Component.class, mh))));
		//
	}

	private static void setPreferredWidth(final int width, final Iterable<Component> cs) throws Throwable {
		try {
			METHOD_SET_PREFERRED_WIDTH.invoke(null, width, cs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSystemClipboard() throws Throwable {
		//
		Assertions.assertNull(getSystemClipboard(null));
		//
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		//
		final Class<?> clz = Util.getClass(toolkit);
		//
		final Class<? extends Throwable> throwableClassByGetSystemClipboard = getThrowingThrowableClass(clz,
				clz != null ? clz.getDeclaredMethod("getSystemClipboard") : null);
		//
		if (throwableClassByGetSystemClipboard != null) {
			//
			final org.junit.jupiter.api.function.Executable executable = () -> getSystemClipboard(toolkit);
			//
			if (Objects.equals(OperatingSystem.WINDOWS, OperatingSystemUtil.getOperatingSystem())) {
				//
				AssertionsUtil.assertThrowsAndEquals(throwableClassByGetSystemClipboard, "{}", executable);
				//
			} else {
				//
				AssertionsUtil.assertThrowsAndEquals(HeadlessException.class,
						String.format("{localizedMessage=%1$s, message=%1$s}",
								Narcissus.invokeStaticMethod(
										GraphicsEnvironment.class.getDeclaredMethod("getHeadlessMessage"))),
						executable);
				//
			} // if
				//
		} else {
			//
			Assertions.assertNotNull(getSystemClipboard(toolkit));
			//
		} // if
			//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertNull(getSystemClipboard(ProxyUtil.createProxy(Toolkit.class, mh)));
			//
		} // if
			//
	}

	private static Class<? extends Throwable> getThrowingThrowableClass(final Class<?> clz, final Method method)
			throws Throwable {
		//
		try (final InputStream is = clz != null ? clz.getResourceAsStream(String.format("/%1$s.class",
				StringsUtil.replace(Strings.CS, clz != null ? clz.getName() : null, ".", "/"))) : null) {
			//
			final JavaClass javaClass = ClassParserUtil
					.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null));
			//
			final org.apache.bcel.classfile.Method m = javaClass != null
					? testAndApply(Objects::nonNull, method, javaClass::getMethod, null)
					: null;
			//
			final Instruction[] instructions = InstructionListUtil.getInstructions(MethodGenUtil
					.getInstructionList(testAndApply(Objects::nonNull, m, x -> new MethodGen(x, null, null), null)));
			//
			ObjectType objectType = null;
			//
			String className = null;
			//
			if (!Objects.equals(
					FailableStreamUtil.map(
							new FailableStream<>(testAndApply(Objects::nonNull, instructions, Arrays::stream, null)),
							x -> Util.getClass(x)).collect(Collectors.toList()),
					Arrays.asList(NEW.class, DUP.class, INVOKESPECIAL.class, ATHROW.class))) {
				//
				return null;
				//
			} // if
				//
			for (int i = 0; instructions != null && i < instructions.length; i++) {
				//
				if ((instructions[i]) instanceof NEW _new) {
					//
					className = (objectType = _new != null
							? _new.getLoadClassType(new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(m)))
							: null) != null ? objectType.getClassName() : null;
					//
				} // if
					//
			} // for
				//
			final Class<?> classTemp = Util.forName(className);
			//
			return classTemp != null && Throwable.class.isAssignableFrom(classTemp)
					? (Class<? extends Throwable>) classTemp
					: null;
			//
		} // try
			//
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

	private static Clipboard getSystemClipboard(final Toolkit instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SYSTEM_CLIP_BOARD.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Clipboard) {
				return (Clipboard) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void tsetSetContents() {
		//
		Assertions.assertDoesNotThrow(() -> setContents(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> setContents(new Clipboard(EMPTY), null, null));
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
	void testInvoke() throws Throwable {
		//
		Assertions.assertNull(invoke(null, null));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args) throws Throwable {
		try {
			return METHOD_INVOKE.invoke(null, method, instance, args);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIif() throws Throwable {
		//
		Assertions.assertNull(iif(false, null, null));
		//
	}

	private static <T> T iif(final boolean condition, final T trueValue, final T falseValue) throws Throwable {
		try {
			return (T) METHOD_IIF.invoke(null, condition, trueValue, falseValue);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testRun() {
		//
		Assertions.assertDoesNotThrow(() -> run(true, null));
		//
		Assertions.assertDoesNotThrow(() -> run(true, Reflection.newProxy(Runnable.class, ih)));
		//
	}

	private static void run(final boolean b, final Runnable runnable) throws Throwable {
		try {
			METHOD_RUN.invoke(null, b, runnable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetJlptVocabularyAndLevel() {
		//
		Assertions.assertDoesNotThrow(() -> setJlptVocabularyAndLevel(null));
		//
	}

	private static void setJlptVocabularyAndLevel(final VoiceManager instance) throws Throwable {
		try {
			METHOD_SET_JLPT_VOCABULARY_AND_LEVEL.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetLevel() throws Throwable {
		//
		Assertions.assertNull(getLevel(null));
		//
	}

	private static String getLevel(final JlptVocabulary instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_LEVEL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForEach() {
		//
		Assertions.assertDoesNotThrow(() -> forEach(null, null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Collections.emptyList(), null));
		//
		final Iterable<?> iterable = Reflection.newProxy(Iterable.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> forEach(iterable, null));
		//
		if (ih != null) {
			//
			ih.iterator = IteratorUtils.emptyIterator();
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> forEach(iterable, null));
		//
	}

	private static <T> void forEach(final Iterable<T> instance, final Consumer<? super T> action) throws Throwable {
		try {
			METHOD_FOR_EACH_STREAM.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final Consumer<T> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testBrowse() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> browse(null, null));
		//
		final Desktop mock = Util.cast(Desktop.class, Narcissus.allocateInstance(Desktop.class));
		//
		Assertions.assertDoesNotThrow(
				() -> browse(!GraphicsEnvironment.isHeadless() ? Desktop.getDesktop() : mock, null));
		//
		Assertions.assertDoesNotThrow(() -> browse(mock, Util.toURI(Path.of(".").toFile())));
		//
	}

	private static void browse(final Desktop instance, final URI uri) throws Throwable {
		try {
			METHOD_BROWSE.invoke(null, instance, uri);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddDocumentListener() {
		//
		Assertions.assertDoesNotThrow(() -> addDocumentListener(null, null));
		//
	}

	private static void addDocumentListener(final Document instance, final DocumentListener listener) throws Throwable {
		try {
			METHOD_ADD_DOCUMENT_LISTENER.invoke(null, instance, listener);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetSelectedIndices() {
		//
		final JList<?> jList = new JList<>();
		//
		Assertions.assertDoesNotThrow(() -> setSelectedIndices(jList, null));
		//
		Assertions.assertDoesNotThrow(() -> setSelectedIndices(jList, new int[] {}));
		//
	}

	private static void setSelectedIndices(final JList<?> instance, final int[] indices) throws Throwable {
		try {
			METHOD_SET_SELECTED_INDICES.invoke(null, instance, indices);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToURI() throws Throwable {
		//
		Assertions.assertNull(toURI(null));
		//
		Assertions.assertEquals(new URI(EMPTY), toURI(URIBuilder.basedOn(EMPTY)));
		//
	}

	private static URI toURI(final URIBuilder instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_URI.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URI) {
				return (URI) obj;
			}
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMax() throws Throwable {
		//
		Assertions.assertNull(max(null, null));
		//
		Assertions.assertNull(max(Stream.empty(), null));
		//
		Assertions.assertNull(max(Reflection.newProxy(Stream.class, ih), null));
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
			throw new Throwable(toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}