package org.springframework.context.support;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Window;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
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

import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.commons.collections.MapUtils;
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
			METHOD_GET_BEAN_CLASS_NAME, METHOD_PACK, METHOD_SET_VISIBLE, METHOD_TEST_AND_APPLY,
			METHOD_GET_SELECTED_VALUE, METHOD_GET_CLASS3, METHOD_IS_RAISE_THROWABLE_ONLY,
			METHOD_ERROR_OR_PRINT_STACK_TRACE, METHOD_GET_CLASS_NAME, METHOD_GET_METHOD, METHOD_GET_DECLARING_CLASS,
			METHOD_IS_ASSIGNABLE_FROM = null;

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
		(METHOD_GET_BEAN_CLASS_NAME = clz.getDeclaredMethod("getBeanClassName", BeanDefinition.class))
				.setAccessible(true);
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
		(METHOD_GET_DECLARING_CLASS = clz.getDeclaredMethod("getDeclaringClass", Member.class)).setAccessible(true);
		//
		(METHOD_IS_ASSIGNABLE_FROM = clz.getDeclaredMethod("isAssignableFrom", Class.class, Class.class))
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
	void testGetBeanClassName() throws Throwable {
		//
		Assertions.assertNull(getBeanClassName(null));
		//
		Assertions.assertNull(getBeanClassName(Reflection.newProxy(BeanDefinition.class, ih)));
		//
	}

	private static String getBeanClassName(final BeanDefinition instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_BEAN_CLASS_NAME.invoke(null, instance);
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
	void testGetDeclaringClass() throws Throwable {
		//
		Assertions.assertNull(getDeclaringClass(null));
		//
	}

	private static Class<?> getDeclaringClass(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARING_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAssignableFrom() throws Throwable {
		//
		Assertions.assertFalse(isAssignableFrom(null, null));
		//
		Assertions.assertFalse(isAssignableFrom(Object.class, null));
		//
		Assertions.assertFalse(isAssignableFrom(CharSequence.class, Object.class));
		//
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) throws Throwable {
		try {
			final Object obj = METHOD_IS_ASSIGNABLE_FROM.invoke(null, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}