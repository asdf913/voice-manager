package org.springframework.context.support;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;

class GaKuNenBeTsuKanJiGuiTest {

	private static Method METHOD_CREATE_WORK_BOOK, METHOD_GET_CLASS, METHOD_TO_STRING,
			METHOD_SET_SELECTED_ITEM_BY_ITERABLE, METHOD_INVOKE, METHOD_GET_NAME, METHOD_GET_PARAMETER_TYPES,
			METHOD_EXISTS, METHOD_AND, METHOD_IIF = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = GaKuNenBeTsuKanJiGui.class;
		//
		(METHOD_CREATE_WORK_BOOK = clz.getDeclaredMethod("createWorkbook", Pair.class, Multimap.class))
				.setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_SET_SELECTED_ITEM_BY_ITERABLE = clz.getDeclaredMethod("setSelectedItemByIterable", ComboBoxModel.class,
				Iterable.class)).setAccessible(true);
		//
		(METHOD_INVOKE = clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_GET_PARAMETER_TYPES = clz.getDeclaredMethod("getParameterTypes", Executable.class)).setAccessible(true);
		//
		(METHOD_EXISTS = clz.getDeclaredMethod("exists", File.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_IIF = clz.getDeclaredMethod("iif", Boolean.TYPE, Object.class, Object.class)).setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private Collection<Entry<?, ?>> entries = null;

		private Iterator<?> iterator = null;

		private Integer size = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = getName(method);
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
			if (proxy instanceof Multimap) {
				//
				if (Objects.equals(methodName, "entries")) {
					//
					return entries;
					//
				} // if
					//
			} else if (proxy instanceof Collection) {
				//
				if (Objects.equals(methodName, "size")) {
					//
					return size;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private GaKuNenBeTsuKanJiGui instance = null;

	@BeforeEach
	void beforeEach() throws ReflectiveOperationException {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			final Constructor<GaKuNenBeTsuKanJiGui> constructor = GaKuNenBeTsuKanJiGui.class.getDeclaredConstructor();
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
	}

	private static void afterPropertiesSet(final InitializingBean instance) throws Exception {
		if (instance != null) {
			instance.afterPropertiesSet();
		}
	}

	@Test
	void testKeyTyped() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.keyTyped(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testKeyPressed() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.keyPressed(null);
				//
			} // if
				//
		});
		//
	}

	@Test
	void testKeyReleased() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, null);
			//
		});
		//
		if (instance != null) {
			//
			instance.setGaKuNenBeTsuKanJiMultimap(ImmutableMultimap.of());
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, null);
			//
		});
		//
		if (instance != null) {
			//
			instance.setGaKuNenBeTsuKanJiMultimap(ImmutableMultimap.of("", ""));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, null);
			//
		});
		//
		final JTextComponent tfText = new JTextField();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "tfText", tfText, true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			keyReleased(instance, new KeyEvent(tfText, 0, 0, 0, 0, (char) 0));
			//
		});
		//
	}

	private static void keyReleased(final KeyListener instance, final KeyEvent e) {
		if (instance != null) {
			instance.keyReleased(e);
		}
	}

	@Test
	void testActionPerformed() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			actionPerformed(instance, new ActionEvent("", 0, null));
			//
		});
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			actionPerformed(instance, null);
			//
		});
		//
		if (instance != null) {
			//
			instance.setGaKuNenBeTsuKanJiMultimap(ImmutableMultimap.of());
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			actionPerformed(instance, null);
			//
		});
		//
		final IH ih = new IH();
		//
		if (instance != null) {
			//
			instance.setGaKuNenBeTsuKanJiMultimap(Reflection.newProxy(Multimap.class, ih));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			actionPerformed(instance, null);
			//
		});
		//
		ih.entries = Reflection.newProxy(Collection.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			actionPerformed(instance, null);
			//
		});
		//
		final AbstractButton btnCompare = new JButton();
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "btnCompare", btnCompare, true);
			//
		} // if
			//
		ih.size = Integer.valueOf(0);
		//
		ih.iterator = Collections.emptyIterator();
		//
		final ActionEvent actionEvent = new ActionEvent(btnCompare, 0, null);
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			actionPerformed(instance, actionEvent);
			//
		});
		//
		if (instance != null) {
			//
			FieldUtils.writeDeclaredField(instance, "jlCompare", new JLabel(), true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> {
			//
			actionPerformed(instance, actionEvent);
			//
		});
		//
	}

	private static void actionPerformed(final ActionListener instance, final ActionEvent e) {
		if (instance != null) {
			instance.actionPerformed(e);
		}
	}

	@Test
	void testCreateWorkbook() throws Throwable {
		//
		Assertions.assertNotNull(createWorkbook(null, ImmutableMultimap.of("", "")));
		//
	}

	private static Workbook createWorkbook(final Pair<String, String> columnNames, final Multimap<?, ?> multimap)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_WORK_BOOK.invoke(null, columnNames, multimap);
			if (obj == null) {
				return null;
			} else if (obj instanceof Workbook) {
				return (Workbook) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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
	void testSetSelectedItemByIterable() {
		//
		Assertions.assertThrows(IllegalStateException.class,
				() -> setSelectedItemByIterable(null, Collections.nCopies(2, null)));
		//
	}

	private static void setSelectedItemByIterable(final ComboBoxModel<?> cbm, final Iterable<?> iterable)
			throws Throwable {
		try {
			METHOD_SET_SELECTED_ITEM_BY_ITERABLE.invoke(null, cbm, iterable);
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
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName(null));
		//
	}

	private static String getName(final Member instance) throws Throwable {
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

	@Test
	void testGetParameterTypes() throws Throwable {
		//
		Assertions.assertNull(getParameterTypes(null));
		//
	}

	private static Class<?>[] getParameterTypes(final Executable instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PARAMETER_TYPES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>[]) {
				return (Class<?>[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExists() throws Throwable {
		//
		Assertions.assertFalse(exists(null));
		//
		Assertions.assertFalse(exists(new File("non_exists")));
		//
	}

	private static boolean exists(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_EXISTS.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnd() throws Throwable {
		//
		Assertions.assertFalse(and(true, false));
		//
		Assertions.assertTrue(and(true, true, null));
		//
		Assertions.assertFalse(and(true, true, false));
		//
	}

	private static boolean and(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
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

	private static <T> T iif(final boolean condition, final T trueValue, final T falseValue) throws Throwable {
		try {
			return (T) METHOD_IIF.invoke(null, condition, trueValue, falseValue);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}