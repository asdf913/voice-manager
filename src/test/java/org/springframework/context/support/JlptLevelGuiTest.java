package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.reflect.Reflection;

class JlptLevelGuiTest {

	private static Method METHOD_TO_ARRAY, METHOD_GET_CLASS, METHOD_TEST, METHOD_GET_PREFERRED_SIZE,
			METHOD_SET_PREFERRED_WIDTH, METHOD_FOR_NAME, METHOD_GET_TEXT, METHOD_GET_SYSTEM_CLIP_BOARD,
			METHOD_ADD_ACTION_LISTENER = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = JlptLevelGui.class;
		//
		(METHOD_TO_ARRAY = clz.getDeclaredMethod("toArray", Collection.class, Object[].class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_PREFERRED_SIZE = clz.getDeclaredMethod("getPreferredSize", Component.class)).setAccessible(true);
		//
		(METHOD_SET_PREFERRED_WIDTH = clz.getDeclaredMethod("setPreferredWidth", Integer.TYPE, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
		//
		(METHOD_GET_TEXT = clz.getDeclaredMethod("getText", JTextComponent.class)).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_ADD_ACTION_LISTENER = clz.getDeclaredMethod("addActionListener", ActionListener.class,
				AbstractButton[].class)).setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private Object[] array = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Collection) {
				//
				if (Objects.equals(methodName, "toArray")) {
					//
					return array;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(method != null ? method.getName() : null);
			//
		}

	}

	private JlptLevelGui instance = null;

	@BeforeEach
	void beforeEach() throws ReflectiveOperationException {
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
		} // if
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
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent("", 0, null)));
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfJson", new JTextField(), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		// btnCopy
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
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent e) {
		if (instance != null) {
			instance.actionPerformed(e);
		}
	}

	@Test
	void testToArray() throws Throwable {
		//
		Assertions.assertNull(toArray(Reflection.newProxy(Collection.class, new IH()), null));
		//
		Assertions.assertNull(toArray(Collections.emptyList(), null));
		//
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) throws Throwable {
		try {
			final Object obj = METHOD_TO_ARRAY.invoke(null, instance, array);
			if (obj == null) {
				return null;
			}
			return (T[]) obj;
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPreferredSize() throws Throwable {
		//
		Assertions.assertNull(getPreferredSize(null));
		//
	}

	private static Dimension getPreferredSize(final Component instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PREFERRED_SIZE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Dimension) {
				return (Dimension) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetPreferredWidth() {
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, null));
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, Collections.singleton(null)));
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
	void testForName() throws Throwable {
		//
		Assertions.assertNull(forName(null));
		//
		Assertions.assertNull(forName("a"));
		//
	}

	private static Class<?> forName(final String className) throws Throwable {
		try {
			final Object obj = METHOD_FOR_NAME.invoke(null, className);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetText() throws Throwable {
		//
		Assertions.assertNull(getText(null));
		//
		Assertions.assertEquals("", getText(new JTextField()));
		//
	}

	private static String getText(final JTextComponent instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TEXT.invoke(null, instance);
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
	void testGetSystemClipboard() throws Throwable {
		//
		Assertions.assertNull(getSystemClipboard(null));
		//
	}

	private static Clipboard getSystemClipboard(final Toolkit instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_SYSTEM_CLIP_BOARD.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Clipboard) {
				return (Clipboard) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddActionListener() {
		//
		Assertions.assertDoesNotThrow(() -> addActionListener(null, (AbstractButton[]) null));
		//
		Assertions.assertDoesNotThrow(() -> addActionListener(null, (AbstractButton) null));
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

}