package org.springframework.context.support;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.swing.JList;
import javax.swing.JTextField;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.jena.ext.com.google.common.base.Predicates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class MainTest {

	private static Method METHOD_FOR_NAME, METHOD_TO_STRING, METHOD_GET_INSTANCE,
			METHOD_SHOW_MESSAGE_DIALOG_OR_PRINT_LN, METHOD_CAST, METHOD_GET_BEAN_NAMES_FOR_TYPE,
			METHOD_GET_BEAN_CLASS_NAME, METHOD_PACK, METHOD_SET_VISIBLE, METHOD_TEST_AND_APPLY,
			METHOD_GET_SELECTED_VALUE, METHOD_GET_CLASS, METHOD_GET_NAME = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = Main.class;
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_GET_INSTANCE = clz.getDeclaredMethod("getInstance", ListableBeanFactory.class, Class.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_SHOW_MESSAGE_DIALOG_OR_PRINT_LN = clz.getDeclaredMethod("showMessageDialogOrPrintln", PrintStream.class,
				Object.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
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
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Class.class)).setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private Map<Object, Object> beansOfType = null;

		private String[] beanNamesForType = null;

		private String beanClassName = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
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
		Assertions.assertSame(Object.class, forName("java.lang.Object"));
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
	void testToString() throws Throwable {
		//
		Assertions.assertNull(toString(null));
		//
		final String empty = "";
		//
		Assertions.assertSame(empty, toString(empty));
		//
	}

	private static String toString(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_STRING.invoke(null, instance);
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
	void testShowMessageDialogOrPrintln() {
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> showMessageDialogOrPrintln(null, null));
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
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
		final Class<?> clz = Object.class;
		//
		Assertions.assertNull(cast(clz, null));
		//
		final Object object = new Object();
		//
		Assertions.assertSame(object, cast(clz, object));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, instance);
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
			throw new Throwable(toString(getClass(obj)));
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
			throw new Throwable(toString(getClass(obj)));
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
				GraphicsEnvironment.isHeadless() ? cast(Window.class, Narcissus.allocateInstance(Window.class))
						: new Window(null)));
		//
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
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
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

	private static String getName(final Class<?> instance) throws Throwable {
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

}