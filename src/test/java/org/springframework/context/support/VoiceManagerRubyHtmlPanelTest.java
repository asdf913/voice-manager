package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.ReturnInstruction;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.ThrowingRunnable;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.PropertyOrFieldReference;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class VoiceManagerRubyHtmlPanelTest {

	private static Method METHOD_LENGTH, METHOD_GET_ACTUAL_TYPE_ARGUMENTS, METHOD_GET_RAW_TYPE, METHOD_GET_GENERIC_TYPE,
			METHOD_GET_GENERIC_INTERFACES, METHOD_TEST_AND_APPLY4, METHOD_TEST_AND_APPLY5, METHOD_GET_LAYOUT_MANAGER,
			METHOD_ADD_ACTION_LISTENER, METHOD_GET_SCREEN_SIZE, METHOD_SET_CONTENTS, METHOD_GET_SYSTEM_CLIPBOARD,
			METHOD_GET_LIST_CELL_RENDERER_COMPONENT, METHOD_AND, METHOD_GET_DESCRIPTION, METHOD_GET_SELECTED_ITEM,
			METHOD_TEST_AND_RUN_THROWS, METHOD_CLEAR, METHOD_GET_VALUE, METHOD_CREATE_MAP, METHOD_GET_AST,
			METHOD_GET_CHILD_COUNT, METHOD_GET_CHILD = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = VoiceManagerRubyHtmlPanel.class;
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", Object[].class)).setAccessible(true);
		//
		(METHOD_GET_ACTUAL_TYPE_ARGUMENTS = clz.getDeclaredMethod("getActualTypeArguments", ParameterizedType.class))
				.setAccessible(true);
		//
		(METHOD_GET_RAW_TYPE = clz.getDeclaredMethod("getRawType", ParameterizedType.class)).setAccessible(true);
		//
		(METHOD_GET_GENERIC_TYPE = clz.getDeclaredMethod("getGenericType", Field.class)).setAccessible(true);
		//
		(METHOD_GET_GENERIC_INTERFACES = clz.getDeclaredMethod("getGenericInterfaces", Class.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				FailableBiFunction.class, FailableBiFunction.class)).setAccessible(true);
		//
		(METHOD_GET_LAYOUT_MANAGER = clz.getDeclaredMethod("getLayoutManager", Object.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_ADD_ACTION_LISTENER = clz.getDeclaredMethod("addActionListener", ActionListener.class,
				AbstractButton[].class)).setAccessible(true);
		//
		(METHOD_GET_SCREEN_SIZE = clz.getDeclaredMethod("getScreenSize", Toolkit.class)).setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIPBOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_GET_LIST_CELL_RENDERER_COMPONENT = clz.getDeclaredMethod("getListCellRendererComponent",
				ListCellRenderer.class, JList.class, Object.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Object.class, Predicate.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_GET_DESCRIPTION = clz.getDeclaredMethod("getDescription", String.class)).setAccessible(true);
		//
		(METHOD_GET_SELECTED_ITEM = clz.getDeclaredMethod("getSelectedItem", JComboBox.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_RUN_THROWS = clz.getDeclaredMethod("testAndRunThrows", Boolean.TYPE, ThrowingRunnable.class))
				.setAccessible(true);
		//
		(METHOD_CLEAR = clz.getDeclaredMethod("clear", Map.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Expression.class, EvaluationContext.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", Object.class, PropertyOrFieldReference.class))
				.setAccessible(true);
		//
		(METHOD_GET_AST = clz.getDeclaredMethod("getAST", Object.class)).setAccessible(true);
		//
		(METHOD_GET_CHILD_COUNT = clz.getDeclaredMethod("getChildCount", SpelNode.class)).setAccessible(true);
		//
		(METHOD_GET_CHILD = clz.getDeclaredMethod("getChild", SpelNode.class, Integer.TYPE)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Type[] actualTypeArguments = null;

		private Type rawType = null;

		private Integer childCount = null;

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
			final Class<?>[] parameterTypes = Util.getParameterTypes(method);
			//
			if (proxy instanceof ParameterizedType) {
				//
				if (Objects.equals(methodName, "getActualTypeArguments")
						&& Arrays.equals(parameterTypes, new Class<?>[] {})) {
					//
					return actualTypeArguments;
					//
				} else if (Objects.equals(methodName, "getRawType")
						&& Arrays.equals(parameterTypes, new Class<?>[] {})) {
					//
					return rawType;
					//
				} // if
					//
			} else if (or(
					and(proxy instanceof ListCellRenderer, Objects.equals(methodName, "getListCellRendererComponent"),
							Arrays.equals(parameterTypes,
									new Class<?>[] { JList.class, Object.class, Integer.TYPE, Boolean.TYPE,
											Boolean.TYPE })),
					and(proxy instanceof ComboBoxModel, Objects.equals(methodName, "getSelectedItem"),
							Arrays.equals(parameterTypes, new Class<?>[] {})),
					and(proxy instanceof Entry, Objects.equals(methodName, "setValue"),
							Arrays.equals(parameterTypes, new Class<?>[] { Object.class })),
					and(proxy instanceof Expression, Objects.equals(methodName, "getValue"),
							Arrays.equals(parameterTypes, new Class<?>[] { EvaluationContext.class, Object.class })),
					and(proxy instanceof SpelNode, Objects.equals(methodName, "getChild"),
							Arrays.equals(parameterTypes, new Class<?>[] { Integer.TYPE })))) {
				//
				return null;
				//
			} else if (and(proxy instanceof SpelNode, Objects.equals(methodName, "getChildCount"),
					Arrays.equals(parameterTypes, new Class<?>[] {}))) {
				//
				return childCount;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		private static boolean and(final boolean a, final boolean b, final boolean... bs) {
			//
			boolean result = Boolean.logicalAnd(a, b);
			//
			if (!result) {
				//
				return result;
				//
			} // if
				//
			for (int i = 0; bs != null && i < bs.length; i++) {
				//
				if (!(result &= bs[i])) {
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

		private static boolean or(final boolean a, final boolean b, final boolean... bs) {
			//
			if (a || b) {
				//
				return true;
				//
			} // if
				//
			for (int i = 0; bs != null && i < bs.length; i++) {
				//
				if (bs[i]) {
					//
					return true;
					//
				} // if
					//
			} // for
				//
			return false;
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
			if (self instanceof Toolkit
					&& Util.contains(Arrays.asList("getSystemClipboard", "getScreenSize"), methodName)) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManagerRubyHtmlPanel instance = null;

	private IH ih = null;

	private ParameterizedType parameterizedType = null;

	private SpelNode spelNode = null;

	private Toolkit toolkit = null;

	private MH mh = null;

	private JComboBox<?> jcbImplementation = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerRubyHtmlPanel();
		//
		parameterizedType = Reflection.newProxy(ParameterizedType.class, ih = new IH());
		//
		spelNode = Reflection.newProxy(SpelNode.class, ih);
		//
		toolkit = Util.cast(Toolkit.class, Narcissus.allocateInstance(Util.forName("sun.awt.HeadlessToolkit")));
		//
		mh = new MH();
		//
		jcbImplementation = Util.cast(JComboBox.class, Narcissus.allocateInstance(JComboBox.class));
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
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(null));
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(new Object(), 0, null)));
		//
		final AbstractButton btnCopy = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopy", btnCopy, true);
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(btnCopy, 0, null)));
		//
	}

	@Test
	void testItemStateChanged() throws NoSuchFieldException {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		final ItemEvent itemEvent = Util.cast(ItemEvent.class, Narcissus.allocateInstance(ItemEvent.class));
		//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEvent));
		//
		if (itemEvent != null) {
			//
			Narcissus.setField(itemEvent, Narcissus.findField(ItemEvent.class, "stateChange"),
					Integer.valueOf(ItemEvent.SELECTED));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.itemStateChanged(itemEvent));
		//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Class<?> clz = VoiceManagerRubyHtmlPanel.class;
		//
		final Method[] ms = Util.getDeclaredMethods(clz);
		//
		Method m = null;
		//
		List<Object> list = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		Object invoke = null;
		//
		final Collection<?> methodReturnNonNullObjectList = getMethodReturnNonNullObjectList(clz);
		//
		for (int i = 0; i < length(ms); i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			if ((parameterTypes = m.getParameterTypes()) != null) {
				//
				Util.clear(list = ObjectUtils.getIfNull(list, ArrayList::new));
				//
				for (final Class<?> parameterType : parameterTypes) {
					//
					if (Objects.equals(parameterType, Double.TYPE)) {
						//
						Util.add(list, Double.valueOf(0));
						//
					} else if (Objects.equals(parameterType, Boolean.TYPE)) {
						//
						Util.add(list, Boolean.TRUE);
						//
					} else if (Objects.equals(parameterType, Integer.TYPE)) {
						//
						Util.add(list, Integer.valueOf(0));
						//
					} else {
						//
						Util.add(list, null);
						//
					} // if
						//
				} // for
					//
			} // if
				//
			os = toArray(list);
			//
			toString = Objects.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (Util.contains(Arrays.asList(Integer.TYPE, Double.TYPE, Boolean.TYPE), m.getReturnType())
						|| Objects.equals(Util.getName(m), "createMap")) {
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
				invoke = Narcissus.invokeMethod(instance, m, os);
				//
				if (Boolean.logicalOr(Objects.equals(m.getReturnType(), Double.TYPE),
						Util.contains(methodReturnNonNullObjectList, m))) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
			} // if
				//
		} // for
			//
	}

	private static List<Method> getMethodReturnNonNullObjectList(final Class<?> clz) throws Throwable {
		//
		List<Method> list = null;
		//
		try (final InputStream is = Util.getResourceAsStream(clz,
				String.format("/%1$s.class", StringUtils.replace(Util.getName(clz), ".", "/")))) {
			//
			final JavaClass javaClass = ClassParserUtil.parse(new ClassParser(is, null));
			//
			final Method[] ms = Util.getDeclaredMethods(clz);
			//
			Method m = null;
			//
			org.apache.bcel.classfile.Method method = null;
			//
			Instruction[] ins = null;
			//
			LDC ldc = null;
			//
			ConstantPoolGen cpg = null;
			//
			for (int i = 0; i < length(ms); i++) {
				//
				if ((method = JavaClassUtil.getMethod(javaClass, m = ArrayUtils.get(ms, i))) == null) {
					//
					continue;
					//
				} // if
					//
				if (length(
						ins = InstructionListUtil.getInstructions(MethodGenUtil.getInstructionList(new MethodGen(method,
								null, cpg = new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(method)))))) == 2
						&& (ldc = Util.cast(LDC.class, ArrayUtils.get(ins, 0))) != null && ldc.getValue(cpg) != null
						&& (Util.cast(ReturnInstruction.class, ArrayUtils.get(ins, 1))) != null) {
					//
					Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), m);
					//
				} // if
					//
			} // for
				//
		} // try
			//
		return list;
		//
	}

	private static int length(final Object[] instance) throws Throwable {
		try {
			final Object obj = METHOD_LENGTH.invoke(null, (Object) instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testGetActualTypeArguments() throws Throwable {
		//
		Assertions.assertNull(getActualTypeArguments(parameterizedType));
		//
	}

	private static Type[] getActualTypeArguments(final ParameterizedType instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ACTUAL_TYPE_ARGUMENTS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Type[]) {
				return (Type[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetGenericType() throws Throwable {
		//
		Assertions.assertNull(getGenericType(Util.cast(Field.class, Narcissus.allocateInstance(Field.class))));
		//
	}

	private static Type getGenericType(final Field instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_GENERIC_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Type) {
				return (Type) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetRawType() throws Throwable {
		//
		Assertions.assertNull(getRawType(parameterizedType));
		//
	}

	private static Type getRawType(final ParameterizedType instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_RAW_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Type) {
				return (Type) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetGenericInterfaces() throws Throwable {
		//
		Assertions.assertArrayEquals(new Class<?>[] {}, getGenericInterfaces(Object.class));
		//
	}

	private static Type[] getGenericInterfaces(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_GENERIC_INTERFACES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Type[]) {
				return (Type[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
		//
		Assertions.assertNull(testAndApply((a, b) -> true, null, null, (a, b) -> null, null));
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

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY5.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetLayoutManager() throws Throwable {
		//
		Assertions.assertNull(getLayoutManager(null, Util.entrySet(Collections.singletonMap(null, null))));
		//
		final Iterable<Entry<String, Object>> entrySet = Util
				.entrySet(Collections.singletonMap(null, Reflection.newProxy(LayoutManager.class, ih)));
		//
		Assertions.assertNull(getLayoutManager(null, entrySet));
		//
		Assertions.assertNull(getLayoutManager(Util.cast(DefaultSingletonBeanRegistry.class,
				Narcissus.allocateInstance(DefaultSingletonBeanRegistry.class)), entrySet));
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
	void testAddActionListener() {
		//
		Assertions.assertDoesNotThrow(() -> addActionListener(null, (AbstractButton[]) null));
		//
	}

	private static void addActionListener(final ActionListener actionListener, final AbstractButton... bs)
			throws Throwable {
		try {
			METHOD_ADD_ACTION_LISTENER.invoke(null, actionListener, bs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetScreenSize() throws Throwable {
		//
		Assertions.assertEquals(null, getScreenSize(toolkit));
		//
		Assertions.assertEquals(null, getScreenSize(ProxyUtil.createProxy(Toolkit.class, mh)));
		//
	}

	private static Dimension getScreenSize(final Toolkit instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SCREEN_SIZE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Dimension) {
				return (Dimension) obj;
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
		Assertions.assertEquals(null, getSystemClipboard(toolkit));
		//
		Assertions.assertEquals(null, getSystemClipboard(ProxyUtil.createProxy(Toolkit.class, mh)));
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
	void testGetListCellRendererComponent() throws Throwable {
		//
		Assertions.assertNull(getListCellRendererComponent(Reflection.newProxy(ListCellRenderer.class, ih), null, null,
				0, false, false));
		//
	}

	private static <E> Component getListCellRendererComponent(final ListCellRenderer<E> instance,
			final JList<? extends E> list, final E value, final int index, final boolean isSelected,
			final boolean cellHasFocus) throws Throwable {
		try {
			final Object obj = METHOD_GET_LIST_CELL_RENDERER_COMPONENT.invoke(null, instance, list, value, index,
					isSelected, cellHasFocus);
			if (obj == null) {
				return null;
			} else if (obj instanceof Component) {
				return (Component) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnd() throws Throwable {
		//
		Assertions.assertFalse(and(null, Predicates.alwaysTrue(), null));
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
	void testGetDescription() throws Throwable {
		//
		Assertions
				.assertNotNull(getDescription("org.apache.commons.lang3.function.FuriganaHtmlBuilderFailableFunction"));
		//
	}

	private static IValue0<Object> getDescription(final String beanClassName) throws Throwable {
		try {
			final Object obj = METHOD_GET_DESCRIPTION.invoke(null, beanClassName);
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
	void testGetSelectedItem() throws Throwable {
		//
		Assertions.assertNull(getSelectedItem(jcbImplementation));
		//
		if (jcbImplementation != null) {
			//
			Narcissus.setField(jcbImplementation, Narcissus.findField(JComboBox.class, "dataModel"),
					Reflection.newProxy(ComboBoxModel.class, ih));
			//
		} // if
			//
		Assertions.assertNull(getSelectedItem(jcbImplementation));
		//
	}

	private static Object getSelectedItem(final JComboBox<?> instance) throws Throwable {
		try {
			return METHOD_GET_SELECTED_ITEM.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndRunThrows() {
		//
		Assertions.assertDoesNotThrow(() -> testAndRunThrows(true, () -> {
		}));
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
	void testClear() {
		//
		Assertions.assertDoesNotThrow(() -> clear(Reflection.newProxy(Map.class, ih)));
		//
	}

	private static void clear(final Map<?, ?> instance) throws Throwable {
		try {
			METHOD_CLEAR.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		Assertions.assertNull(getValue(Reflection.newProxy(Expression.class, ih), null, null));
		//
	}

	private static Object getValue(final Expression instance, final EvaluationContext context, final Object rootObject)
			throws Throwable {
		try {
			return METHOD_GET_VALUE.invoke(null, instance, context, rootObject);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMap() throws Throwable {
		//
		final PropertyOrFieldReference pofr = Util.cast(PropertyOrFieldReference.class,
				Narcissus.allocateInstance(PropertyOrFieldReference.class));
		//
		Assertions.assertEquals(Collections.singletonMap(null, null), createMap(null, pofr));
		//
		String name = "LOG";
		//
		FieldUtils.writeDeclaredField(pofr, "name", name, true);
		//
		Assertions.assertEquals(Collections.singletonMap(name, FieldUtils.readDeclaredField(instance, name, true)),
				createMap(instance, pofr));
		//
		FieldUtils.writeDeclaredField(pofr, "name", name = "table", true);
		//
		Assertions.assertEquals(Collections.singletonMap(name, FieldUtils.readDeclaredField(instance, name, true)),
				createMap(instance, pofr));
		//
	}

	private static Map<String, Object> createMap(final Object instance, final PropertyOrFieldReference pofr)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP.invoke(null, instance, pofr);
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
	void testGetAST() throws Throwable {
		//
		Assertions.assertNull(getAST(Narcissus.allocateInstance(SpelExpressionParser.class)));
		//
	}

	private static Object getAST(final Object instance) throws Throwable {
		try {
			return METHOD_GET_AST.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetChildCount() throws Throwable {
		//
		int childCount = 0;
		//
		if (ih != null) {
			//
			ih.childCount = Integer.valueOf(childCount);
			//
		} // ih
			//
		Assertions.assertEquals(childCount, getChildCount(spelNode));
		//
	}

	private static int getChildCount(final SpelNode instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CHILD_COUNT.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetChild() throws Throwable {
		//
		Assertions.assertNull(getChild(spelNode, 0));
		//
	}

	private static SpelNode getChild(final SpelNode instance, final int index) throws Throwable {
		try {
			final Object obj = METHOD_GET_CHILD.invoke(null, instance, index);
			if (obj == null) {
				return null;
			} else if (obj instanceof SpelNode) {
				return (SpelNode) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}