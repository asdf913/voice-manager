package org.springframework.context.support;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Window;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.swing.JList;
import javax.swing.JTextField;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.PropertyResolver;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class MainTest {

	private static Method METHOD_GET_INSTANCE, METHOD_SHOW_MESSAGE_DIALOG_OR_PRINT_LN, METHOD_GET_BEAN_NAMES_FOR_TYPE,
			METHOD_PACK, METHOD_SET_VISIBLE, METHOD_TEST_AND_APPLY, METHOD_GET_SELECTED_VALUE, METHOD_GET_CLASS3,
			METHOD_IS_RAISE_THROWABLE_ONLY, METHOD_ERROR_OR_PRINT_STACK_TRACE, METHOD_GET_CLASS_NAME,
			METHOD_GET_METHOD = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = Main.class;
		//
		(METHOD_GET_INSTANCE = clz.getDeclaredMethod("getInstance", ListableBeanFactory.class, Class.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_SHOW_MESSAGE_DIALOG_OR_PRINT_LN = clz.getDeclaredMethod("showMessageDialogOrPrintln", PrintStream.class,
				Object.class)).setAccessible(true);
		//
		(METHOD_GET_BEAN_NAMES_FOR_TYPE = clz.getDeclaredMethod("getBeanNamesForType", ListableBeanFactory.class,
				Class.class)).setAccessible(true);
		//
		(METHOD_PACK = clz.getDeclaredMethod("pack", Window.class)).setAccessible(true);
		//
		(METHOD_SET_VISIBLE = clz.getDeclaredMethod("setVisible", Component.class, Boolean.TYPE)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_SELECTED_VALUE = clz.getDeclaredMethod("getSelectedValue", JList.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS3 = clz.getDeclaredMethod("getClass", ConfigurableListableBeanFactory.class,
				PropertyResolver.class, String.class)).setAccessible(true);
		//
		(METHOD_IS_RAISE_THROWABLE_ONLY = clz.getDeclaredMethod("isRaiseThrowableOnly", Class.class, Method.class))
				.setAccessible(true);
		//
		(METHOD_ERROR_OR_PRINT_STACK_TRACE = clz.getDeclaredMethod("errorOrPrintStackTrace", Logger.class,
				Throwable.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS_NAME = clz.getDeclaredMethod("getClassName", InvokeInstruction.class, ConstantPoolGen.class))
				.setAccessible(true);
		//
		(METHOD_GET_METHOD = clz.getDeclaredMethod("getMethod", Class.class, String.class, Class[].class))
				.setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private Map<Object, Object> beansOfType = null;

		private String[] beanNamesForType = null;

		private String beanClassName = null;

		private Map<String, BeanDefinition> beanDefinitions = null;

		private Map<String, String> properties = null;

		private Map<String, BeanDefinition> getBeanDefinitions() {
			if (beanDefinitions == null) {
				beanDefinitions = new LinkedHashMap<>();
			}
			return beanDefinitions;
		}

		private Map<String, String> getProperties() {
			if (properties == null) {
				properties = new LinkedHashMap<>();
			}
			return properties;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(method != null ? method.getReturnType() : null, Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof ListableBeanFactory) {
				//
				if (Objects.equals(methodName, "getBeansOfType") && args != null && args.length > 0) {
					//
					return beansOfType;
					//
				} else if (Objects.equals(methodName, "getBeanNamesForType") && args != null && args.length > 0) {
					//
					return beanNamesForType;
					//
				} else if (Objects.equals(methodName, "getBeanDefinitionNames")) {
					//
					final Map<String, ?> map = getBeanDefinitions();
					//
					final Set<String> keySet = map != null ? map.keySet() : null;
					//
					return keySet != null ? keySet.toArray(new String[] {}) : null;
					//
				} // if
					//
			} else if (proxy instanceof BeanDefinition) {
				//
				if (Objects.equals(methodName, "getBeanClassName")) {
					//
					return beanClassName;
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
			} // if
				//
			if (proxy instanceof ConfigurableListableBeanFactory) {
				//
				if (Objects.equals(methodName, "getBeanDefinition") && args != null && args.length > 0) {
					//
					return MapUtils.getObject(getBeanDefinitions(), args[0]);
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private IH ih = null;

	private ListableBeanFactory listableBeanFactory = null;

	@BeforeEach
	void beforeEach() {
		//
		listableBeanFactory = Reflection.newProxy(ListableBeanFactory.class, ih = new IH());
		//
	}

	@Test
	void testMain() {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			return;
			//
		} // if
			//
		Assertions.assertThrows(HeadlessException.class, () -> {
			//
			try {
				//
				Main.main(null);
				//
			} catch (final Exception e) {
				//
				throw ExceptionUtils.getRootCause(e);
				//
			} // try
				//
		});
		//
	}

	@Test
	void testGetInstance() throws Throwable {
		//
		Assertions.assertNull(getInstance(null, null, null));
		//
		Assertions.assertNull(getInstance(null, null, x -> {
		}));
		//
		Assertions.assertNull(getInstance(null, Object.class, null));
		//
		Assertions.assertNull(getInstance(listableBeanFactory, Object.class, null));
		//
		if (ih != null) {
			//
			ih.beansOfType = Collections.emptyMap();
			//
		} // if
			//
		Assertions.assertNull(getInstance(listableBeanFactory, Object.class, null));
		//
		//
		if (ih != null) {
			//
			ih.beansOfType = new LinkedHashMap<>(Collections.singletonMap(null, null));
			//
		} // if
			//
		Assertions.assertNull(getInstance(listableBeanFactory, Object.class, null));
		//
		if (ih != null) {
			//
			ih.beansOfType.put("", "");
			//
		} // if
			//
		Assertions.assertNull(getInstance(listableBeanFactory, Object.class, null));
		//
	}

	private static Object getInstance(final ListableBeanFactory beanFactory, final Class<?> clz,
			final Consumer<String> consumer) throws Throwable {
		try {
			return METHOD_GET_INSTANCE.invoke(null, beanFactory, clz, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testShowMessageDialogOrPrintln() throws IOException {
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> showMessageDialogOrPrintln(null, null));
			//
			try (final OutputStream os = new ByteArrayOutputStream(); final PrintStream ps = new PrintStream(os)) {
				//
				Assertions.assertDoesNotThrow(() -> showMessageDialogOrPrintln(ps, null));
				//
			} // try
				//
		} // if
			//
	}

	private static void showMessageDialogOrPrintln(final PrintStream ps, final Object object) throws Throwable {
		try {
			METHOD_SHOW_MESSAGE_DIALOG_OR_PRINT_LN.invoke(null, ps, object);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetBeanNamesForType() throws Throwable {
		//
		Assertions.assertNull(getBeanNamesForType(null, null));
		//
		Assertions.assertNull(getBeanNamesForType(listableBeanFactory, null));
		//
	}

	private static String[] getBeanNamesForType(final ListableBeanFactory instance, final Class<?> type)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_BEAN_NAMES_FOR_TYPE.invoke(null, instance, type);
			if (obj == null) {
				return null;
			} else if (obj instanceof String[]) {
				return (String[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPack() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> pack(null));
		//
		Assertions.assertDoesNotThrow(() -> pack(
				GraphicsEnvironment.isHeadless() ? Util.cast(Window.class, Narcissus.allocateInstance(Window.class))
						: new Window(null)));
		//
	}

	@Test
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null, null, null));
		//
		final ConfigurableListableBeanFactory clbf = Reflection.newProxy(ConfigurableListableBeanFactory.class, ih);
		//
		if (ih != null) {
			//
			ih.getBeanDefinitions().put(null, null);
			//
		} // if
			//
		Assertions.assertNull(getClass(clbf, null, null));
		//
		if (ih != null) {
			//
			ih.getBeanDefinitions().put(null, Reflection.newProxy(BeanDefinition.class, ih));
			//
		} // if
			//
		Assertions.assertNull(getClass(clbf, null, null));
		//
		if (ih != null) {
			//
			ih.beanClassName = "";
			//
		} // if
			//
		Assertions.assertNull(getClass(clbf, null, null));
		//
		if (ih != null) {
			//
			ih.beanClassName = "1.2";
			//
		} // if
			//
		Assertions.assertNull(getClass(clbf, null, null));
		//
		final PropertyResolver propertyResolver = Reflection.newProxy(PropertyResolver.class, ih);
		//
		Assertions.assertNull(getClass(clbf, propertyResolver, null));
		//
		if (ih != null) {
			//
			ih.getProperties().put(null, "");
			//
		} // if
			//
		Assertions.assertNull(getClass(clbf, propertyResolver, null));
		//
		if (ih != null) {
			//
			ih.getProperties().put(null, " ");
			//
		} // if
			//
		Assertions.assertNull(getClass(clbf, propertyResolver, null));
		//
		if (ih != null) {
			//
			ih.getProperties().put(null, "A");
			//
		} // if
			//
		Assertions.assertNull(getClass(clbf, propertyResolver, null));
		//
		final Class<?> clz = String.class;
		//
		if (ih != null) {
			//
			ih.getProperties().put(null, Util.getName(clz));
			//
		} // if
			//
		Assertions.assertSame(clz, getClass(clbf, propertyResolver, null));
		//
	}

	private static Class<?> getClass(final ConfigurableListableBeanFactory clbf,
			final PropertyResolver propertyResolver, final String key) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS3.invoke(null, clbf, propertyResolver, key);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void pack(final Window instance) throws Throwable {
		try {
			METHOD_PACK.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetVisible() {
		//
		Assertions.assertDoesNotThrow(() -> setVisible(null, false));
		//
		Assertions.assertDoesNotThrow(() -> setVisible(new JTextField(), false));
		//
	}

	private static void setVisible(final Component instance, boolean b) throws Throwable {
		try {
			METHOD_SET_VISIBLE.invoke(null, instance, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		final FailableFunction<?, ?, ?> function = x -> x;
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, function, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysFalse(), null, function, null));
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

	@Test
	void testGetSelectedValue() throws Throwable {
		//
		Assertions.assertNull(getSelectedValue(null));
		//
		Assertions.assertNull(getSelectedValue(new JList<>()));
		//
	}

	private static <E> E getSelectedValue(final JList<E> instance) throws Throwable {
		try {
			return (E) METHOD_GET_SELECTED_VALUE.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsRaiseHeadlessExceptionOnly() throws Throwable {
		//
		Assertions.assertFalse(isRaiseThrowableOnly(null, null));
		//
		final Stream<Method> stream = testAndApply(Objects::nonNull, MainTest.class.getDeclaredMethods(),
				Arrays::stream, null);
		//
		if (!GraphicsEnvironment.isHeadless() && stream != null
				&& stream.anyMatch(x -> x != null && Objects.equals(x.getName(), "createWindow")
						&& Arrays.equals(x.getParameterTypes(), new Class<?>[] { Window.class }))) {
			//
			Assertions.assertTrue(isRaiseThrowableOnly(MainTest.class,
					MainTest.class.getDeclaredMethod("createWindow", Window.class)));
			//
		} // if
			//
	}

	private static boolean isRaiseThrowableOnly(final Class<?> clz, final Method method) throws Throwable {
		try {
			final Object obj = METHOD_IS_RAISE_THROWABLE_ONLY.invoke(null, clz, method);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Object createWindow(final Window target) throws HeadlessException {
		throw new HeadlessException();
	}

	@Test
	void testErrorOrPrintStackTrace() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTrace(null, null));
		//
		final Throwable throwable = Util.cast(Throwable.class, Narcissus.allocateInstance(Throwable.class));
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTrace(null, throwable));
		//
		final Logger logger = Reflection.newProxy(Logger.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTrace(logger, throwable));
		//
	}

	private static void errorOrPrintStackTrace(final Logger logger, final Throwable throwable) throws Throwable {
		try {
			METHOD_ERROR_OR_PRINT_STACK_TRACE.invoke(null, logger, throwable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetClassName() throws Throwable {
		//
		Assertions.assertNull(getClassName(null, null));
		//
	}

	private static String getClassName(final InvokeInstruction instance, final ConstantPoolGen cpg) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS_NAME.invoke(null, instance, cpg);
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
	void testGetMethod() throws Throwable {
		//
		Assertions.assertNull(getMethod(null, null));
		//
	}

	private static Method getMethod(final Class<?> instance, final String name, final Class<?>... parameterTypes)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_METHOD.invoke(null, instance, name, parameterTypes);
			if (obj == null) {
				return null;
			} else if (obj instanceof Method) {
				return (Method) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMain1() throws Throwable {
		//
		Class<?> clz = Main.class;
		//
		String className = null;
		//
		Object argument = null;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(String.format("/%1$s.class", StringUtils.replace(clz.getName(), ".", "/")))
				: null) {
			//
			final org.apache.bcel.classfile.Method[] methods = JavaClassUtil.getMethods(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)));
			//
			org.apache.bcel.classfile.Method method = null;
			//
			Instruction[] ins = null;
			//
			LDC ldc = null;
			//
			INVOKESTATIC invokestatic = null;
			//
			INVOKESPECIAL invokespecial = null;
			//
			String name = null;
			//
			for (int i = 0; methods != null && i < methods.length; i++) {
				//
				if ((method = methods[i]) == null || (ins = InstructionListUtil
						.getInstructions(MethodGenUtil.getInstructionList(testAndApply(Objects::nonNull, method,
								x -> new MethodGen(x, null, new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(x))),
								null)))) == null) {
					//
					continue;
					//
				} // if
					//
				if (Objects.equals(name = FieldOrMethodUtil.getName(method), "main")) {
					//
					for (int j = 0; j < ins.length; j++) {
						//
						if ((ldc = Util.cast(LDC.class, ins[j])) != null && j < ins.length - 1
								&& (invokestatic = Util.cast(INVOKESTATIC.class, ins[j + 1])) != null) {
							//
							final ConstantPoolGen cpg = new ConstantPoolGen(method.getConstantPool());
							//
							if (!Objects.equals(invokestatic.getMethodName(cpg),
									"createConfigurableApplicationContext")) {
								//
								continue;
								//
							} // if
								//
							argument = ldc.getValue(cpg);
							//
							break;
							//
						} // if
							//
					} // for
						//
				} else if (Objects.equals(name, "createConfigurableApplicationContext")) {
					//
					for (int j = 0; j < ins.length; j++) {
						//
						if ((invokespecial = Util.cast(INVOKESPECIAL.class, ins[j])) != null) {
							//
							final ConstantPoolGen cpg = new ConstantPoolGen(method.getConstantPool());
							//
							className = invokespecial.getClassName(cpg);
							//
							if (!Objects.equals(invokespecial.getMethodName(cpg), "<init>")) {
								//
								continue;
								//
							} // if
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
		final Method[] ms = Util.getDeclaredMethods(clz = Util.forName(className));
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			final Method m = ms[i];
			//
			if (m == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			m.setAccessible(true);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertDoesNotThrow(() -> m.invoke(null, new Object[m.getParameterCount()]));
				//
			} else {
				//
				if (Objects.equals("org.springframework.context.support.Main$1", Util.getName(clz))
						&& GraphicsEnvironment.isHeadless()) {
					//
					continue;
					//
				} // if
					//
				final Class<?> c = clz;
				//
				final Object a = argument;
				//
				Assertions.assertDoesNotThrow(() -> m.invoke(newInstance(getDeclaredConstructor(c, String.class), a),
						new Object[m.getParameterCount()]));
				//
			} // if
				//
		} // for
			//
	}

	private static <T> Constructor<T> getDeclaredConstructor(final Class<T> instance, final Class<?>... parameterTypes)
			throws NoSuchMethodException {
		return instance != null ? instance.getDeclaredConstructor(parameterTypes) : null;
	}

	private static <T> T newInstance(final Constructor<T> instance, final Object... initargs)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		return instance != null ? instance.newInstance(initargs) : null;
	}

	@Test
	void testCustomConfigurableApplicationContext() throws Throwable {
		//
		Class<?> clz = Main.class;
		//
		String className = null;
		//
		Object argument = null;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(String.format("/%1$s.class", StringUtils.replace(clz.getName(), ".", "/")))
				: null) {
			//
			final org.apache.bcel.classfile.Method[] methods = JavaClassUtil.getMethods(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)));
			//
			org.apache.bcel.classfile.Method method = null;
			//
			Instruction[] ins = null;
			//
			LDC ldc = null;
			//
			INVOKESPECIAL invokespecial = null;
			//
			for (int i = 0; methods != null && i < methods.length; i++) {
				//
				if ((method = methods[i]) == null || (ins = InstructionListUtil
						.getInstructions(MethodGenUtil.getInstructionList(testAndApply(Objects::nonNull, method,
								x -> new MethodGen(x, null, new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(x))),
								null)))) == null
						|| !Objects.equals(FieldOrMethodUtil.getName(method), "main")) {
					//
					continue;
					//
				} // if
					//
				for (int j = 0; j < ins.length; j++) {
					//
					if ((ldc = Util.cast(LDC.class, ins[j])) != null && j < ins.length - 1
							&& (invokespecial = Util.cast(INVOKESPECIAL.class, ins[j + 1])) != null) {
						//
						final ConstantPoolGen cpg = new ConstantPoolGen(method.getConstantPool());
						//
						if (!Objects.equals(invokespecial.getMethodName(cpg), "<init>")) {
							//
							continue;
							//
						} // if
							//
						className = invokespecial.getClassName(cpg);
						//
						argument = ldc.getValue(cpg);
						//
						break;
						//
					} // if
						//
				} // for
					//
			} // for
				//
		} // try
			//
		Assertions.assertNotNull(Narcissus.allocateInstance(clz));
		//
		final Constructor<?>[] cs = (clz = Util.forName(className)).getDeclaredConstructors();
		//
		Constructor<?> c = null;
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if ((c = cs[i]) == null) {
				//
				continue;
				//
			} // if
				//
			c.setAccessible(true);
			//
			if (c.getParameterCount() == 1 && !GraphicsEnvironment.isHeadless()) {
				//
				Assertions.assertNotNull(c.newInstance(argument));
				//
			} // if
				//
		} // if
			//
		final Method[] ms = Util.getDeclaredMethods(clz);
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			final Method m = ms[i];
			//
			if (m == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			m.setAccessible(true);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertDoesNotThrow(() -> m.invoke(null, new Object[m.getParameterCount()]));
				//
			} // if
				//
		} // for
			//
			// org.springframework.context.support.Main$CustomClassPathXmlApplicationContext.isProxyClass(java.lang.Class)
			//
		final Method m = clz != null ? clz.getDeclaredMethod("isProxyClass", Class.class) : null;
		//
		if (m != null) {
			//
			m.setAccessible(true);
			//
		} // if
			//
		Assertions.assertEquals(Boolean.FALSE, m != null ? m.invoke(null, Class.class) : null);
		//
	}

}