package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ListableBeanFactory;

import com.google.common.reflect.Reflection;

class MainTest {

	private static Method METHOD_FOR_NAME, METHOD_GET_INSTANCE, METHOD_SHOW_MESSAGE_DIALOG_OR_PRINT_LN, METHOD_CAST,
			METHOD_GET_BEAN_DEFINITION_NAMES = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = Main.class;
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
		//
		(METHOD_GET_INSTANCE = clz.getDeclaredMethod("getInstance", ListableBeanFactory.class, Class.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_SHOW_MESSAGE_DIALOG_OR_PRINT_LN = clz.getDeclaredMethod("showMessageDialogOrPrintln", PrintStream.class,
				Object.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_BEAN_DEFINITION_NAMES = clz.getDeclaredMethod("getBeanDefinitionNames", ListableBeanFactory.class))
				.setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private Map<Object, Object> beansOfType = null;

		private String[] beanDefinitionNames = null;

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
				} else if (Objects.equals(methodName, "getBeanDefinitionNames")) {
					//
					return beanDefinitionNames;
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

	@BeforeEach
	void beforeEach() {
		//
		ih = new IH();
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
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
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
		final ListableBeanFactory beanFactory = Reflection.newProxy(ListableBeanFactory.class, ih);
		//
		Assertions.assertNull(getInstance(beanFactory, Object.class, null));
		//
		if (ih != null) {
			//
			ih.beansOfType = Collections.emptyMap();
			//
		} // if
			//
		Assertions.assertNull(getInstance(beanFactory, Object.class, null));
		//
		//
		if (ih != null) {
			//
			ih.beansOfType = new LinkedHashMap<>(Collections.singletonMap(null, null));
			//
		} // if
			//
		Assertions.assertNull(getInstance(beanFactory, Object.class, null));
		//
		if (ih != null) {
			//
			ih.beansOfType.put("", "");
			//
		} // if
			//
		Assertions.assertNull(getInstance(beanFactory, Object.class, null));
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
	void testGetBeanDefinitionNames() throws Throwable {
		//
		Assertions.assertNull(getBeanDefinitionNames(null));
		//
		Assertions.assertNull(getBeanDefinitionNames(Reflection.newProxy(ListableBeanFactory.class, ih)));
		//
	}

	private static String[] getBeanDefinitionNames(final ListableBeanFactory instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_BEAN_DEFINITION_NAMES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String[]) {
				return (String[]) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}