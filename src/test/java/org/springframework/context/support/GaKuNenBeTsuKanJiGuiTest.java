package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class GaKuNenBeTsuKanJiGuiTest {

	private static Method METHOD_CREATE_WORK_BOOK, METHOD_SET_SELECTED_ITEM_BY_ITERABLE, METHOD_INVOKE,
			METHOD_GET_PARAMETER_TYPES, METHOD_EXISTS, METHOD_AND, METHOD_IIF, METHOD_TEST_AND_ACCEPT3,
			METHOD_TEST_AND_ACCEPT4, METHOD_ADD_ACTION_LISTENER, METHOD_TO_ARRAY, METHOD_TO_LIST,
			METHOD_GET_DECLARED_METHODS, METHOD_GET_ABSOLUTE_PATH, METHOD_IS_FILE, METHOD_LENGTH, METHOD_LONG_VALUE,
			METHOD_CONTAINS, METHOD_SET_SELECTED_ITEM, METHOD_SET_PREFERRED_WIDTH, METHOD_GET_PREFERRED_SIZE,
			METHOD_MAX, METHOD_CREATE_DIMENSION_COMPARATOR = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = GaKuNenBeTsuKanJiGui.class;
		//
		(METHOD_CREATE_WORK_BOOK = clz.getDeclaredMethod("createWorkbook", Pair.class, Multimap.class))
				.setAccessible(true);
		//
		(METHOD_SET_SELECTED_ITEM_BY_ITERABLE = clz.getDeclaredMethod("setSelectedItemByIterable", ComboBoxModel.class,
				Iterable.class)).setAccessible(true);
		//
		(METHOD_INVOKE = clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_GET_PARAMETER_TYPES = clz.getDeclaredMethod("getParameterTypes", Executable.class)).setAccessible(true);
		//
		(METHOD_EXISTS = clz.getDeclaredMethod("exists", File.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_IIF = clz.getDeclaredMethod("iif", Boolean.TYPE, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3 = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				FailableConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_ADD_ACTION_LISTENER = clz.getDeclaredMethod("addActionListener", ActionListener.class,
				AbstractButton[].class)).setAccessible(true);
		//
		(METHOD_TO_ARRAY = clz.getDeclaredMethod("toArray", Collection.class, Object[].class)).setAccessible(true);
		//
		(METHOD_TO_LIST = clz.getDeclaredMethod("toList", Stream.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_METHODS = clz.getDeclaredMethod("getDeclaredMethods", Class.class)).setAccessible(true);
		//
		(METHOD_GET_ABSOLUTE_PATH = clz.getDeclaredMethod("getAbsolutePath", File.class)).setAccessible(true);
		//
		(METHOD_IS_FILE = clz.getDeclaredMethod("isFile", File.class)).setAccessible(true);
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", File.class)).setAccessible(true);
		//
		(METHOD_LONG_VALUE = clz.getDeclaredMethod("longValue", Number.class, Long.TYPE)).setAccessible(true);
		//
		(METHOD_CONTAINS = clz.getDeclaredMethod("contains", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_SET_SELECTED_ITEM = clz.getDeclaredMethod("setSelectedItem", ComboBoxModel.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_SET_PREFERRED_WIDTH = clz.getDeclaredMethod("setPreferredWidth", Integer.TYPE, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_GET_PREFERRED_SIZE = clz.getDeclaredMethod("getPreferredSize", Component.class)).setAccessible(true);
		//
		(METHOD_MAX = clz.getDeclaredMethod("max", Stream.class, Comparator.class)).setAccessible(true);
		//
		(METHOD_CREATE_DIMENSION_COMPARATOR = clz.getDeclaredMethod("createDimensionComparator")).setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private Collection<Entry<?, ?>> entries = null;

		private Iterator<?> iterator = null;

		private Integer size = null;

		private Object[] array = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
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
				} else if (Objects.equals(methodName, "toArray")) {
					//
					return array;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "max")) {
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

	private static class MH implements MethodHandler {

		private Dimension preferredSize = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			if (Objects.equals(thisMethod != null ? thisMethod.getReturnType() : null, Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof Component) {
				//
				if (Objects.equals(methodName, "getPreferredSize")) {
					//
					return preferredSize;
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

	private IH ih = null;

	private Stream<?> stream = null;

	@BeforeEach
	void beforeEach() throws Throwable {
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
		} else {
			//
			instance = Util.cast(GaKuNenBeTsuKanJiGui.class, Narcissus.allocateInstance(GaKuNenBeTsuKanJiGui.class));
			//
		} // if
			//
		stream = Reflection.newProxy(Stream.class, ih = new IH());
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
		if (ih != null) {
			//
			ih.entries = Reflection.newProxy(Collection.class, ih);
			//
		} // if
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
		if (ih != null) {
			//
			ih.size = Integer.valueOf(0);
			//
			ih.iterator = Collections.emptyIterator();
			//
		} // if
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetSelectedItemByIterable() throws IOException {
		//
		final Collection<?> collection = Collections.nCopies(2, null);
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class, "{}",
				() -> setSelectedItemByIterable(null, collection));
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
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

	private static <T> T iif(final boolean condition, final T trueValue, final T falseValue) throws Throwable {
		try {
			return (T) METHOD_IIF.invoke(null, condition, trueValue, falseValue);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null));
		//
		final BiPredicate<?, ?> predicate = (a, b) -> true;
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(predicate, null, null, null));
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysFalse(), null, null));
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> false, null, null, null));
			//
		} else {
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept(predicate, null, null, (a, b) -> {
			}));
			//
		} // if
			//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT3.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiConsumer<T, U> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT4.invoke(null, predicate, t, u, consumer);
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

	@Test
	void testToArray() throws Throwable {
		//
		Assertions.assertNull(toArray(Reflection.newProxy(Collection.class, ih), null));
		//
		final Collection<Object> collection = Collections.emptySet();
		//
		Assertions.assertNull(toArray(collection, null));
		//
		final Object[] array = new Object[] {};
		//
		Assertions.assertArrayEquals(array, toArray(collection, array));
		//
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) throws Throwable {
		try {
			return (T[]) METHOD_TO_ARRAY.invoke(null, instance, array);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToList() throws Throwable {
		//
		Assertions.assertNull(toList(null));
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_LIST.invoke(null, instance);
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
	void testGetDeclaredMethods() throws Throwable {
		//
		Assertions.assertNull(getDeclaredMethods(null));
		//
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_METHODS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Method[]) {
				return (Method[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetAbsolutePath() throws Throwable {
		//
		Assertions.assertNull(getAbsolutePath(null));
		//
	}

	private static String getAbsolutePath(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ABSOLUTE_PATH.invoke(null, instance);
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
	void testIsFile() throws Throwable {
		//
		Assertions.assertFalse(isFile(null));
		//
		Assertions.assertFalse(isFile(new File(".")));
		//
	}

	private static boolean isFile(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_FILE.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testLength() throws Throwable {
		//
		Assertions.assertNull(length(null));
		//
	}

	private static Long length(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_LENGTH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Long) {
				return (Long) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testLongValue() throws Throwable {
		//
		final long l = 0;
		//
		Assertions.assertEquals(l, longValue(null, l));
		//
	}

	private static long longValue(final Number instance, final long defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_LONG_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Long) {
				return ((Long) obj).longValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(contains(null, null));
		//
		Assertions.assertTrue(contains(Collections.singleton(null), null));
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS.invoke(null, items, item);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetSelectedItem() {
		//
		Assertions.assertDoesNotThrow(() -> setSelectedItem(new DefaultComboBoxModel<>(), null));
		//
	}

	private static void setSelectedItem(final ComboBoxModel<?> instance, final Object selectedItem) throws Throwable {
		try {
			METHOD_SET_SELECTED_ITEM.invoke(null, instance, selectedItem);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetPreferredWidth() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, null));
		//
		Assertions.assertDoesNotThrow(() -> setPreferredWidth(0, Collections.singleton(null)));
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(Component.class);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		final Object instance = constructor != null ? constructor.newInstance() : null;
		//
		final MH mh = new MH();
		//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(mh);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(
				() -> setPreferredWidth(0, Collections.singleton(Util.cast(Component.class, instance))));
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMax() throws Throwable {
		//
		Assertions.assertNull(max(Stream.empty(), null));
		//
		Assertions.assertNull(max(stream, null));
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCcreateDimensionComparator() throws Throwable {
		//
		final Comparator<Dimension> comparator = createDimensionComparator();
		//
		if (comparator != null) {
			//
			Assertions.assertEquals(0, comparator.compare(null, null));
			//
			Assertions.assertEquals(0,
					comparator.compare(Util.cast(Dimension.class, Narcissus.allocateInstance(Dimension.class)), null));
			//
		} // if
			//
	}

	private static Comparator<Dimension> createDimensionComparator() throws Throwable {
		try {
			final Object obj = METHOD_CREATE_DIMENSION_COMPARATOR.invoke(null);
			if (obj == null) {
				return null;
			} else if (obj instanceof Comparator) {
				return (Comparator) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}