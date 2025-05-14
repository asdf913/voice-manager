package org.springframework.context.support;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstructionUtil;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.htmlunit.SgmlPage;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlOption;
import org.htmlunit.html.HtmlSelect;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;
import org.meeuw.functional.ThrowingRunnable;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class SpeechApiOnlineImplTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_TEST_AND_ACCEPT, METHOD_WRITE, METHOD_AND, METHOD_GET_FORMAT,
			METHOD_GET_AUDIO_INPUT_STREAM, METHOD_GET_VALUE_ATTRIBUTE, METHOD_GET_OPTIONS, METHOD_SET_SELECTED_INDEX,
			METHOD_GET_NEXT_ELEMENT_SIBLING, METHOD_TEST_AND_RUN_THROWS, METHOD_GET_ATTRIBUTE,
			METHOD_GET_ELEMENTS_BY_TAG_NAME, METHOD_QUERY_SELECTOR, METHOD_EXECUTE = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = SpeechApiOnlineImpl.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				FailableBiConsumer.class)).setAccessible(true);
		//
		(METHOD_WRITE = clz.getDeclaredMethod("write", SourceDataLine.class, byte[].class, Integer.TYPE, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Object.class, Predicate.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_GET_FORMAT = clz.getDeclaredMethod("getFormat", AudioInputStream.class)).setAccessible(true);
		//
		(METHOD_GET_AUDIO_INPUT_STREAM = clz.getDeclaredMethod("getAudioInputStream", InputStream.class))
				.setAccessible(true);
		//
		(METHOD_GET_VALUE_ATTRIBUTE = clz.getDeclaredMethod("getValueAttribute", HtmlOption.class)).setAccessible(true);
		//
		(METHOD_GET_OPTIONS = clz.getDeclaredMethod("getOptions", HtmlSelect.class)).setAccessible(true);
		//
		(METHOD_SET_SELECTED_INDEX = clz.getDeclaredMethod("setSelectedIndex", HtmlSelect.class, HtmlOption.class))
				.setAccessible(true);
		//
		(METHOD_GET_NEXT_ELEMENT_SIBLING = clz.getDeclaredMethod("getNextElementSibling", DomNode.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_RUN_THROWS = clz.getDeclaredMethod("testAndRunThrows", Boolean.TYPE, ThrowingRunnable.class))
				.setAccessible(true);
		//
		(METHOD_GET_ATTRIBUTE = clz.getDeclaredMethod("getAttribute", NodeList.class, String.class, Predicate.class))
				.setAccessible(true);
		//
		(METHOD_GET_ELEMENTS_BY_TAG_NAME = clz.getDeclaredMethod("getElementsByTagName", Document.class, String.class))
				.setAccessible(true);
		//
		(METHOD_QUERY_SELECTOR = clz.getDeclaredMethod("querySelector", DomNode.class, String.class))
				.setAccessible(true);
		//
		(METHOD_EXECUTE = clz.getDeclaredMethod("execute", String.class, String.class, Map.class, String.class,
				Integer.TYPE, Map.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Collection<Node> nodes = null;

		private NamedNodeMap attributes = null;

		private Map<Object, Node> namedItems = null;

		private String nodeValue = null;

		private Integer write = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Document && Objects.equals(methodName, "getElementsByTagName")) {
				//
				return null;
				//
			} else if (proxy instanceof SourceDataLine && Objects.equals(methodName, "write")) {
				//
				return write;
				//
			} else if (proxy instanceof NodeList) {
				//
				if (Objects.equals(methodName, "item") && args != null && args.length > 0
						&& args[0] instanceof Integer) {
					//
					return nodes != null ? IterableUtils.get(nodes, (Integer) args[0]) : null;
					//
				} else if (Objects.equals(methodName, "getLength")) {
					//
					return IterableUtils.size(nodes);
					//
				} // if
					//
			} else if (proxy instanceof Node) {
				//
				if (Objects.equals(methodName, "getAttributes")) {
					//
					return attributes;
					//
				} else if (Objects.equals(methodName, "getNodeValue")) {
					//
					return nodeValue;
					//
				} // if
					//
			} else if (proxy instanceof NamedNodeMap && Objects.equals(methodName, "getNamedItem") && args != null
					&& args.length > 0) {
				//
				return MapUtils.getObject(namedItems, args[0]);
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
			if (Objects.equals(Util.getReturnType(thisMethod), Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof DomNode
					&& Util.contains(Arrays.asList("querySelector", "getNextElementSibling"), methodName)) {
				//
				return null;
				//
			} else if (self instanceof InputStream && Objects.equals(methodName, "read")) {
				//
				return -1;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private SpeechApiOnlineImpl instance = null;

	private IH ih = null;

	private MH mh = null;

	private HtmlOption htmlOption = null;

	private DomNode domNode = null;

	private NodeList nodeList = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		instance = new SpeechApiOnlineImpl();
		//
		htmlOption = Util.cast(HtmlOption.class, Narcissus.allocateInstance(HtmlOption.class));
		//
		domNode = ProxyUtil.createProxy(DomNode.class, mh = new MH(), clz -> {
			final Constructor<?> constructor = clz != null ? clz.getConstructor(SgmlPage.class) : null;
			if (constructor != null) {
				constructor.setAccessible(true);
			}
			return constructor != null ? constructor.newInstance((Object) null) : null;
		});
		//
		nodeList = Reflection.newProxy(NodeList.class, ih = new IH());
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = SpeechApiOnlineImpl.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		Object invoke = null;
		//
		String toString = null;
		//
		Class<?> returnType = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				///
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Integer.TYPE)) {
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
			os = Util.toArray(collection);
			//
			toString = Objects.toString(m);
			//
			returnType = Util.getReturnType(m);
			//
			if (Util.isStatic(m)) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE), returnType)) {
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
				invoke = Narcissus.invokeMethod(instance = ObjectUtils.getIfNull(instance, SpeechApiOnlineImpl::new), m,
						os);
				//
				if (Objects.equals(returnType, Boolean.TYPE)) {
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

	@Test
	void testGetVoiceAttribute() throws Throwable {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final Map<Object, Object> properties = System.getProperties();
		//
		testAndAccept(Util::containsKey, properties, "url", (a, b) -> {
			//
			FieldUtils.writeDeclaredField(instance, "url", MapUtils.getObject(a, b), true);
			//
		});
		//
		final Class<?> clz = Util.getClass(instance);
		//
		Collection<String> attributes = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))) {
			//
			final org.apache.bcel.classfile.Method method = JavaClassUtil.getMethod(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)),
					Util.getDeclaredMethod(clz, "getVoiceAttribute", String.class, String.class));
			//
			final ConstantPoolGen cpg = testAndApply(Objects::nonNull, FieldOrMethodUtil.getConstantPool(method),
					x -> new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(method)), null);
			//
			final Instruction[] instructions = InstructionListUtil.getInstructions(MethodGenUtil.getInstructionList(
					testAndApply(Objects::nonNull, method, x -> new MethodGen(x, null, cpg), null)));
			//
			for (int i = 0; instructions != null && i < instructions.length; i++) {
				//
				if (ArrayUtils.get(instructions, i) instanceof INVOKESTATIC invokestatic
						&& Objects.equals(InvokeInstructionUtil.getClassName(invokestatic, cpg), "java.util.Objects")
						&& Objects.equals(InvokeInstructionUtil.getMethodName(invokestatic, cpg), "equals") && i > 0
						&& ArrayUtils.get(instructions, i - 1) instanceof LDC ldc) {
					//
					Util.add(attributes = ObjectUtils.getIfNull(attributes, ArrayList::new),
							Util.toString(ldc.getValue(cpg)));
					//
				} // for
					//
			} // for
				//
			final int maxLength = Util.stream(attributes).mapToInt(StringUtils::length).max().orElse(0);
			//
			for (int i = 0; i < IterableUtils.size(attributes); i++) {
				//
				final String attribute = Util.toString(IterableUtils.get(attributes, i));
				//
				Assertions.assertDoesNotThrow(() -> {
					//
					System.out
							.println(
									String.join(" ", StringUtils.rightPad(attribute, maxLength),
											instance.getVoiceAttribute(
													Util.toString(testAndApply(x -> Util.containsKey(properties, x),
															"voiceId", x -> Util.get(properties, x), null)),
													attribute)));
					//
				});
				//
			} // for
				//
		} // try
			//
		final String key = "1";
		//
		final Field voices = clz != null ? clz.getDeclaredField("voices") : null;
		//
		Narcissus.setField(instance, voices, Unit.with(Map.of(key, "女性：Mei (Normal)")));
		//
		final int maxLength = Util.stream(attributes).mapToInt(StringUtils::length).max().orElse(0);
		//
		for (int i = 0; i < IterableUtils.size(attributes); i++) {
			//
			final String attribute = Util.toString(IterableUtils.get(attributes, i));
			//
			Assertions.assertDoesNotThrow(() -> {
				//
				System.out.println(String.join(" ", StringUtils.rightPad(attribute, maxLength),
						instance.getVoiceAttribute(key, attribute)));
				//
			});
			//
		} // for
			//
		Narcissus.setField(instance, voices, null);
		//
	}

	@Test
	void testTestAndAccept() {
		//
		final BiPredicate<?, ?> biPredicate = Predicates.biAlwaysTrue();
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(biPredicate, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(biPredicate, null, null, FailableBiConsumer.nop()));
		//
	}

	private static <T, U, E extends Throwable> void testAndAccept(final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiConsumer<T, U, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, predicate, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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
	void testIH() {
		//
		final InvocationHandler invocationHandler = Util.cast(InvocationHandler.class,
				Narcissus.allocateInstance(Util.forName("org.springframework.context.support.SpeechApiOnlineImpl$IH")));
		//
		if (invocationHandler == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertThrows(Throwable.class, () -> invocationHandler.invoke(null, null, null));
		//
	}

	@Test
	void testWrite() throws Throwable {
		//
		final int zero = 0;
		//
		if (ih != null) {
			//
			ih.write = Integer.valueOf(zero);
			//
		} // if
			//
		Assertions.assertEquals(zero, write(Reflection.newProxy(SourceDataLine.class, ih), null, zero, zero));
		//
	}

	private static int write(final SourceDataLine instance, final byte[] b, final int off, final int len)
			throws Throwable {
		try {
			final Object obj = METHOD_WRITE.invoke(null, instance, b, off, len);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnd() throws Throwable {
		//
		final Predicate<Object> alwaysTrue = Predicates.alwaysTrue();
		//
		Assertions.assertTrue(and(null, alwaysTrue, alwaysTrue));
		//
	}

	private static <T> boolean and(final T value, final Predicate<T> a, final Predicate<T> b) throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, value, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFormat() throws Throwable {
		//
		Assertions.assertNull(
				getFormat(Util.cast(AudioInputStream.class, Narcissus.allocateInstance(AudioInputStream.class))));
		//
	}

	private static AudioFormat getFormat(final AudioInputStream instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_FORMAT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof AudioFormat) {
				return (AudioFormat) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAudioInputStream() {
		//
		Assertions.assertThrows(UnsupportedAudioFileException.class,
				() -> getAudioInputStream(ProxyUtil.createProxy(InputStream.class, mh)));
		//
	}

	private static AudioInputStream getAudioInputStream(final InputStream instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_AUDIO_INPUT_STREAM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof AudioInputStream) {
				return (AudioInputStream) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValueAttribute() throws Throwable {
		//
		Assertions.assertNull(getValueAttribute(htmlOption));
		//
	}

	private static final String getValueAttribute(final HtmlOption instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_VALUE_ATTRIBUTE.invoke(null, instance);
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
	void testGetOptions() throws Throwable {
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.emptySet(),
				getOptions(Util.cast(HtmlSelect.class, Narcissus.allocateInstance(HtmlSelect.class)))));
		//
	}

	private static List<HtmlOption> getOptions(final HtmlSelect instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_OPTIONS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetSelectedIndex() {
		//
		final HtmlSelect htmlSelect = Util.cast(HtmlSelect.class, Narcissus.allocateInstance(HtmlSelect.class));
		//
		Assertions.assertDoesNotThrow(() -> setSelectedIndex(htmlSelect, null));
		//
		Assertions.assertDoesNotThrow(() -> setSelectedIndex(htmlSelect, htmlOption));
		//
	}

	private static void setSelectedIndex(final HtmlSelect htmlSelect, final HtmlOption htmlOption) throws Throwable {
		try {
			METHOD_SET_SELECTED_INDEX.invoke(null, htmlSelect, htmlOption);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetNextElementSibling() throws Throwable {
		//
		Assertions.assertNull(getNextElementSibling(domNode));
		//
	}

	private static DomElement getNextElementSibling(final DomNode instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NEXT_ELEMENT_SIBLING.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof DomElement) {
				return (DomElement) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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
	void testGetAttribute() throws Throwable {
		//
		final Node node = Reflection.newProxy(Node.class, ih);
		//
		if (ih != null) {
			//
			(ih.nodes = new ArrayList<>()).add(node);
			//
		} // if
			//
		Assertions.assertNull(getAttribute(nodeList, null, null));
		//
		if (ih != null) {
			//
			ih.attributes = Reflection.newProxy(NamedNodeMap.class, ih);
			//
		} // if
			//
		Assertions.assertNull(getAttribute(nodeList, null, null));
		//
		if (ih != null) {
			//
			Util.put(ih.namedItems = new LinkedHashMap<>(), null, node);
			//
		} // if
			//
		Assertions.assertNull(getAttribute(nodeList, null, null));
		//
		Assertions.assertEquals(Unit.with(null), getAttribute(nodeList, null, Predicates.alwaysTrue()));
		//
	}

	private static IValue0<String> getAttribute(final NodeList nodeList, final String attrbiuteName,
			final Predicate<String> predicate) throws Throwable {
		try {
			final Object obj = METHOD_GET_ATTRIBUTE.invoke(null, nodeList, attrbiuteName, predicate);
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
	void testGetElementsByTagName() throws Throwable {
		//
		Assertions.assertNull(getElementsByTagName(Reflection.newProxy(Document.class, ih), null));
		//
	}

	private static NodeList getElementsByTagName(final Document instance, final String tagname) throws Throwable {
		try {
			final Object obj = METHOD_GET_ELEMENTS_BY_TAG_NAME.invoke(null, instance, tagname);
			if (obj == null) {
				return null;
			} else if (obj instanceof NodeList) {
				return (NodeList) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testQuerySelector() throws Throwable {
		//
		Assertions.assertNull(querySelector(domNode, null));
		//
	}

	private static <N extends DomNode> N querySelector(final DomNode instance, final String selectors)
			throws Throwable {
		try {
			final Object obj = METHOD_QUERY_SELECTOR.invoke(null, instance, selectors);
			if (obj == null) {
				return null;
			} else if (obj instanceof DomNode) {
				return (N) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExecute() throws Throwable {
		//
		final Class<?> clz = SpeechApiOnlineImpl.class;
		//
		Map<String, Object> map = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))) {
			//
			final org.apache.bcel.classfile.Method method = JavaClassUtil.getMethod(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)),
					Util.getDeclaredMethod(clz, "execute", String.class, String.class, Map.class, String.class,
							Integer.TYPE, Map.class));
			//
			final ConstantPoolGen cpg = testAndApply(Objects::nonNull, FieldOrMethodUtil.getConstantPool(method),
					x -> new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(method)), null);
			//
			final Instruction[] instructions = InstructionListUtil.getInstructions(MethodGenUtil.getInstructionList(
					testAndApply(Objects::nonNull, method, x -> new MethodGen(x, null, cpg), null)));
			//
			final int length = instructions != null ? instructions.length : 0;
			//
			for (int i = 0; i < length; i++) {
				//
				if (ArrayUtils.get(instructions, i) instanceof LDC ldc && Boolean.logicalOr(
						// SYNALPHA
						i < length - 4 && ArrayUtils.get(instructions, i + 1) instanceof ASTORE
								&& ArrayUtils.get(instructions, i + 2) instanceof ALOAD
								&& ArrayUtils.get(instructions, i + 3) instanceof ALOAD
								&& ArrayUtils.get(instructions, i + 4) instanceof INVOKESTATIC invokeStatic
								&& Objects.equals(InvokeInstructionUtil.getClassName(invokeStatic, cpg),
										"org.springframework.context.support.Util")
								&& Objects.equals(InvokeInstructionUtil.getMethodName(invokeStatic, cpg),
										"containsKey"),
						// F0SHIFT
						i < length - 3 && ArrayUtils.get(instructions, i + 1) instanceof DUP
								&& ArrayUtils.get(instructions, i + 2) instanceof ASTORE
								&& ArrayUtils.get(instructions, i + 3) instanceof INVOKESTATIC invokeStatic
								&& Objects.equals(InvokeInstructionUtil.getClassName(invokeStatic, cpg),
										"org.springframework.context.support.Util")
								&& Objects.equals(InvokeInstructionUtil.getMethodName(invokeStatic, cpg),
										"containsKey"))) {
					//
					Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), Util.toString(ldc.getValue(cpg)),
							null);
					//
				} // if
					//
			} // for
				//
		} // try
			//
		Assertions.assertNull(execute(null, null, null, null, 0, map));
		//
	}

	private static URL execute(final String url, final String text, final Map<String, String> voices,
			final String voiceId, final int rate, final Map<String, Object> map) throws Throwable {
		try {
			final Object obj = METHOD_EXECUTE.invoke(null, url, text, voices, voiceId, rate, map);
			if (obj == null) {
				return null;
			} else if (obj instanceof URL) {
				return (URL) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}