package org.springframework.context.support;

import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListModel;
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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.Consumers;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.htmlunit.SgmlPage;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlOption;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSelect;
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
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.PropertyResolver;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class VoiceManagerOnlineTtsPanelTest {

	private static Method METHOD_GET_LAYOUT_MANAGER, METHOD_TEST_AND_APPLY4, METHOD_TEST_AND_APPLY5,
			METHOD_QUERY_SELECTOR, METHOD_GET_ELEMENTS_BY_TAG_NAME, METHOD_TEST_AND_ACCEPT, METHOD_GET_ATTRIBUTE,
			METHOD_PREVIOUS_ELEMENT_SIBLING, METHOD_GET_ELEMENTS_BY_NAME, METHOD_GET_VALUE_ATTRIBUTE,
			METHOD_GET_OPTIONS, METHOD_TEST_AND_RUN_THROWS, METHOD_SET_VALUES, METHOD_SELECT_STREAM,
			METHOD_SET_SELECTED_INDEX, METHOD_SET_EDITABLE, METHOD_GET_NEXT_ELEMENT_SIBLING, METHOD_SET_CONTENTS,
			METHOD_GET_SYSTEM_CLIPBOARD, METHOD_GET_ENVIRONMENT, METHOD_IIF, METHOD_GET_VOICE = null;

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
		(METHOD_QUERY_SELECTOR = clz.getDeclaredMethod("querySelector", DomNode.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_ELEMENTS_BY_TAG_NAME = clz.getDeclaredMethod("getElementsByTagName", Document.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class, Consumer.class))
				.setAccessible(true);
		//
		(METHOD_GET_ATTRIBUTE = clz.getDeclaredMethod("getAttribute", NodeList.class, String.class, Predicate.class))
				.setAccessible(true);
		//
		(METHOD_PREVIOUS_ELEMENT_SIBLING = clz.getDeclaredMethod("previousElementSibling", Element.class))
				.setAccessible(true);
		//
		(METHOD_GET_ELEMENTS_BY_NAME = clz.getDeclaredMethod("getElementsByName", HtmlPage.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_VALUE_ATTRIBUTE = clz.getDeclaredMethod("getValueAttribute", HtmlOption.class)).setAccessible(true);
		//
		(METHOD_GET_OPTIONS = clz.getDeclaredMethod("getOptions", HtmlSelect.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_RUN_THROWS = clz.getDeclaredMethod("testAndRunThrows", Boolean.TYPE, ThrowingRunnable.class))
				.setAccessible(true);
		//
		(METHOD_SET_VALUES = clz.getDeclaredMethod("setValues", HtmlPage.class, Map.class, String.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_SELECT_STREAM = clz.getDeclaredMethod("selectStream", Element.class, String.class)).setAccessible(true);
		//
		(METHOD_SET_SELECTED_INDEX = clz.getDeclaredMethod("setSelectedIndex", HtmlSelect.class, HtmlOption.class))
				.setAccessible(true);
		//
		(METHOD_SET_EDITABLE = clz.getDeclaredMethod("setEditable", JTextComponent.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_GET_NEXT_ELEMENT_SIBLING = clz.getDeclaredMethod("getNextElementSibling", DomNode.class))
				.setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIPBOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_GET_ENVIRONMENT = clz.getDeclaredMethod("getEnvironment", EnvironmentCapable.class))
				.setAccessible(true);
		//
		(METHOD_IIF = clz.getDeclaredMethod("iif", Boolean.TYPE, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_VOICE = clz.getDeclaredMethod("getVoice", PropertyResolver.class, String.class, ListModel.class,
				Map.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Collection<Node> nodes = null;

		private NamedNodeMap attributes = null;

		private Map<Object, Node> namedItems = null;

		private String nodeValue = null;

		private Map<Object, Object> properties = null;

		private Object[] elements = null;

		@Override
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Document && Objects.equals(methodName, "getElementsByTagName")) {
				//
				return null;
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
			} else if (Objects.equals(Util.getName(method.getDeclaringClass()),
					"org.springframework.context.support.VoiceManagerOnlineTtsPanel$Name")
					&& Objects.equals(methodName, "value")) {
				//
				return null;
				//
			} else if (proxy instanceof ComboBoxModel && Objects.equals(methodName, "getSelectedItem")) {
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

	private NodeList nodeList = null;

	private Element element = null;

	private HtmlOption htmlOption = null;

	private DomNode domNode = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		instance = new VoiceManagerOnlineTtsPanel();
		//
		nodeList = Reflection.newProxy(NodeList.class, ih = new IH());
		//
		element = Util.cast(Element.class, Narcissus.allocateInstance(Element.class));
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
		final AbstractButton btnExecute = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnExecute", btnExecute, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnExecute, 0, null)));
		//
		final AbstractButton btnCopy = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopy", btnCopy, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopy, 0, null)));
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
			if ((m = ms[i]) == null || m.isSynthetic()) {
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
				Assertions.assertNull(invoke, toString);
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
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, Consumers.nop()));
		//
	}

	private static <T> void testAndAccept(final Predicate<T> instance, final T value, final Consumer<T> consumer)
			throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, instance, value, consumer);
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
			(ih.namedItems = new LinkedHashMap<>()).put(null, node);
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
	void testGetElementsByName() throws Throwable {
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.emptySet(),
				getElementsByName(Util.cast(HtmlPage.class, Narcissus.allocateInstance(HtmlPage.class)), null)));
		//
	}

	private static List<DomElement> getElementsByName(final HtmlPage instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_ELEMENTS_BY_NAME.invoke(null, instance, name);
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
	void testSetValues() {
		//
		Assertions.assertDoesNotThrow(() -> setValues(null, null, null, new JTextField()));
		//
		Assertions.assertDoesNotThrow(() -> setValues(null, null, null, Reflection.newProxy(ComboBoxModel.class, ih)));
		//
	}

	private static void setValues(final HtmlPage htmlPage, final Map<String, String> voices, final String a,
			final Object b) throws Throwable {
		try {
			METHOD_SET_VALUES.invoke(null, htmlPage, voices, a, b);
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
	void testGetEnvironment() throws Throwable {
		//
		Assertions.assertNull(getEnvironment(Reflection.newProxy(EnvironmentCapable.class, ih)));
		//
	}

	private static Environment getEnvironment(final EnvironmentCapable instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ENVIRONMENT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Environment) {
				return (Environment) obj;
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

}