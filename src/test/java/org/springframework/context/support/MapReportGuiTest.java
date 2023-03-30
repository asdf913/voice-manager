package org.springframework.context.support;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.AttributeAccessor;

import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class MapReportGuiTest {

	private static final String EMPTY = "";

	private static Method METHOD_CAST, METHOD_IS_ALL_ATTRIBUTES_MATCHED, METHOD_GET_CLASS, METHOD_TO_STRING,
			METHOD_REMOVE_ROW, METHOD_ADD_ROW, METHOD_GET_PREFERRED_WIDTH, METHOD_DOUBLE_VALUE, METHOD_AS_MAP,
			METHOD_GET_VALUES, METHOD_OR_ELSE, METHOD_MAX, METHOD_MAP_TO_INT, METHOD_CREATE_MULTI_MAP, METHOD_ADD,
			METHOD_IS_ASSIGNABLE_FROM, METHOD_GET_KEY, METHOD_GET_VALUE, METHOD_FOR_NAME, METHOD_FILTER, METHOD_TO_LIST,
			METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_SET_CONTENTS, METHOD_ADD_ACTION_LISTENER = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = MapReportGui.class;
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_IS_ALL_ATTRIBUTES_MATCHED = clz.getDeclaredMethod("isAllAttributesMatched", Map.class,
				AttributeAccessor.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_REMOVE_ROW = clz.getDeclaredMethod("removeRow", DefaultTableModel.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_ADD_ROW = clz.getDeclaredMethod("addRow", DefaultTableModel.class, Object[].class)).setAccessible(true);
		//
		(METHOD_GET_PREFERRED_WIDTH = clz.getDeclaredMethod("getPreferredWidth", Component.class)).setAccessible(true);
		//
		(METHOD_DOUBLE_VALUE = clz.getDeclaredMethod("doubleValue", Number.class, Double.TYPE)).setAccessible(true);
		//
		(METHOD_AS_MAP = clz.getDeclaredMethod("asMap", Multimap.class)).setAccessible(true);
		//
		(METHOD_GET_VALUES = clz.getDeclaredMethod("getValues", BeanFactory.class, Class.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_OR_ELSE = clz.getDeclaredMethod("orElse", OptionalInt.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_MAX = clz.getDeclaredMethod("max", IntStream.class)).setAccessible(true);
		//
		(METHOD_MAP_TO_INT = clz.getDeclaredMethod("mapToInt", Stream.class, ToIntFunction.class)).setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP = clz.getDeclaredMethod("createMultimap", Iterable.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_IS_ASSIGNABLE_FROM = clz.getDeclaredMethod("isAssignableFrom", Class.class, Class.class))
				.setAccessible(true);
		//
		(METHOD_GET_KEY = clz.getDeclaredMethod("getKey", Entry.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Entry.class)).setAccessible(true);
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_TO_LIST = clz.getDeclaredMethod("toList", Stream.class)).setAccessible(true);
		//
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
		//
		(METHOD_ADD_ACTION_LISTENER = clz.getDeclaredMethod("addActionListener", ActionListener.class,
				AbstractButton[].class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private String[] beanDefinitionNames = null;

		private BeanDefinition beanDefinition;

		private String beanClassName = null;

		private Set<Entry<?, ?>> entrySet = null;

		private Iterator<?> iterator = null;

		private Boolean hasAttribute = null;

		private Object attribute, bean = null;

		private Map<?, ?> asMap = null;

		private IntStream intStream = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof BeanFactory) {
				//
				if (Objects.equals(methodName, "getBean")) {
					//
					return bean;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof ListableBeanFactory) {
				//
				if (Objects.equals(methodName, "getBeanDefinitionNames")) {
					//
					return beanDefinitionNames;
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return entrySet;
					//
				} // if
					//
			} else if (proxy instanceof Iterable) {
				//
				if (Objects.equals(methodName, "iterator")) {
					//
					return iterator;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof ConfigurableListableBeanFactory) {
				//
				if (Objects.equals(methodName, "getBeanDefinition")) {
					//
					return beanDefinition;
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
			} else if (proxy instanceof AttributeAccessor) {
				//
				if (Objects.equals(methodName, "hasAttribute")) {
					//
					return hasAttribute;
					//
				} else if (Objects.equals(methodName, "getAttribute")) {
					//
					return attribute;
					//
				} // if
					//
			} else if (proxy instanceof Multimap) {
				//
				if (Objects.equals(methodName, "asMap")) {
					//
					return asMap;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "mapToInt")) {
					//
					return intStream;
					//
				} else if (Objects.equals(methodName, "filter")) {
					//
					return proxy;
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

		private Clipboard clipboard = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof Toolkit) {
				//
				if (Objects.equals(methodName, "getSystemClipboard")) {
					//
					return clipboard;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private MapReportGui instance = null;

	private IH ih = null;

	private Stream<?> stream = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			final Constructor<MapReportGui> constructor = MapReportGui.class.getDeclaredConstructor();
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
			instance = cast(MapReportGui.class, Narcissus.allocateInstance(MapReportGui.class));
			//
		} // if
			//
		stream = Reflection.newProxy(Stream.class, ih = new IH());
		//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance != null) {
				//
				instance.afterPropertiesSet();
				//
			} //
				//
		});
		//
	}

	@Test
	void testActionPerformed() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(EMPTY, 0, null)));
		//
		final JTextComponent tfAttributeJson = new JTextField();
		//
		FieldUtils.writeDeclaredField(instance, "tfAttributeJson", tfAttributeJson, true);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		tfAttributeJson.setText("{}");
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		final DefaultTableModel dtm = new DefaultTableModel();
		//
		dtm.addRow(new Object[] {});
		//
		FieldUtils.writeDeclaredField(instance, "dtm", dtm, true);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		if (instance != null) {
			//
			instance.postProcessBeanFactory(Reflection.newProxy(ConfigurableListableBeanFactory.class, ih));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		if (ih != null) {
			//
			ih.beanDefinitionNames = new String[] { null };
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		if (ih != null) {
			//
			ih.beanDefinition = Reflection.newProxy(BeanDefinition.class, ih);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		FieldUtils.writeDeclaredField(instance, "jTable", new JTable(), true);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, null));
		//
		final AbstractButton btnCopy = new JButton();
		//
		FieldUtils.writeDeclaredField(instance, "btnCopy", btnCopy, true);
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
	void testTest() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAllAttributesMatched() throws Throwable {
		//
		Assertions.assertTrue(isAllAttributesMatched(null, null));
		//
		Assertions.assertTrue(isAllAttributesMatched(Collections.singletonMap(null, null), null));
		//
		final Map<?, ?> map = Reflection.newProxy(Map.class, ih);
		//
		Assertions.assertTrue(isAllAttributesMatched(map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Reflection.newProxy(Set.class, ih);
			//
		} // if
			//
		Assertions.assertTrue(isAllAttributesMatched(map, null));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertTrue(isAllAttributesMatched(map, null));
		//
		final AttributeAccessor aa = Reflection.newProxy(AttributeAccessor.class, ih);
		//
		if (ih != null) {
			//
			ih.hasAttribute = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertFalse(isAllAttributesMatched(Collections.singletonMap(null, null), aa));
		//
		if (ih != null) {
			//
			ih.hasAttribute = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertTrue(isAllAttributesMatched(Collections.singletonMap(null, null), aa));
		//
		Assertions.assertFalse(isAllAttributesMatched(Collections.singletonMap(null, EMPTY), aa));
		//
	}

	private static boolean isAllAttributesMatched(final Map<?, ?> attributes, final AttributeAccessor aa)
			throws Throwable {
		try {
			final Object obj = METHOD_IS_ALL_ATTRIBUTES_MATCHED.invoke(null, attributes, aa);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(obj != null ? toString(obj.getClass()) : null);
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
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToString() throws Throwable {
		//
		Assertions.assertSame(EMPTY, toString(EMPTY));
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
			throw new Throwable(toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testRemoveRow() {
		//
		Assertions.assertDoesNotThrow(() -> removeRow(null, 0));
		//
	}

	private static void removeRow(final DefaultTableModel instance, final int row) throws Throwable {
		try {
			METHOD_REMOVE_ROW.invoke(null, instance, row);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddRow() {
		//
		Assertions.assertDoesNotThrow(() -> addRow(null, null));
		//
		Assertions.assertDoesNotThrow(() -> addRow(new DefaultTableModel(), null));
		//
	}

	private static void addRow(final DefaultTableModel instance, final Object[] rowData) throws Throwable {
		try {
			METHOD_ADD_ROW.invoke(null, instance, rowData);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetPreferredWidth() throws Throwable {
		//
		Assertions.assertNull(getPreferredWidth(null));
		//
	}

	private static Double getPreferredWidth(final Component instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PREFERRED_WIDTH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Double) {
				return (Double) obj;
			}
			throw new Throwable(toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testDoubleValue() throws Throwable {
		//
		final double d = 1.2;
		//
		Assertions.assertEquals(d, doubleValue(null, d));
		//
	}

	private static double doubleValue(final Number instance, final double defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_DOUBLE_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Double) {
				return ((Double) obj).doubleValue();
			}
			throw new Throwable(obj != null ? toString(obj.getClass()) : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAsMap() throws Throwable {
		//
		Assertions.assertNull(asMap(null));
		//
		Assertions.assertNull(asMap(Reflection.newProxy(Multimap.class, ih)));
		//
	}

	private static <K, V> Map<K, Collection<V>> asMap(final Multimap<K, V> instance) throws Throwable {
		try {
			final Object obj = METHOD_AS_MAP.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValues() throws Throwable {
		//
		final BeanFactory beanFactory = Reflection.newProxy(BeanFactory.class, ih);
		//
		final Iterable<String> strings = Collections.singleton(null);
		//
		Assertions.assertNull(getValues(beanFactory, null, strings));
		//
		Assertions.assertEquals(Collections.singletonList(ih != null ? ih.bean = Collections.emptyMap() : null),
				getValues(beanFactory, Map.class, strings));
		//
	}

	private static <V> Collection<V> getValues(final BeanFactory beanFactory, final Class<V> clz,
			final Iterable<String> beanNames) throws Throwable {
		try {
			final Object obj = METHOD_GET_VALUES.invoke(null, beanFactory, clz, beanNames);
			if (obj == null) {
				return null;
			} else if (obj instanceof Collection) {
				return (Collection) obj;
			}
			throw new Throwable(toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOrElse() throws Throwable {
		//
		final int zero = 0;
		//
		Assertions.assertEquals(zero, orElse(OptionalInt.of(zero), 1));
		//
	}

	private static int orElse(final OptionalInt instance, final int other) throws Throwable {
		try {
			final Object obj = METHOD_OR_ELSE.invoke(null, instance, other);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(obj != null ? obj.getClass() : null));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMax() throws Throwable {
		//
		Assertions.assertEquals(OptionalInt.empty(), max(IntStream.empty()));
		//
	}

	private static OptionalInt max(final IntStream instance) throws Throwable {
		try {
			final Object obj = METHOD_MAX.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof OptionalInt) {
				return (OptionalInt) obj;
			}
			throw new Throwable(toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMapToInt() throws Throwable {
		//
		Assertions.assertNull(mapToInt(Stream.empty(), null));
		//
		Assertions.assertNull(mapToInt(stream, null));
		//
		Assertions.assertNotNull(mapToInt(Stream.empty(), x -> 0));
		//
	}

	private static <T> IntStream mapToInt(final Stream<T> instance, final ToIntFunction<? super T> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP_TO_INT.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof IntStream) {
				return (IntStream) obj;
			}
			throw new Throwable(toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testcreateMultimap() throws Throwable {
		//
		Assertions.assertNull(createMultimap(Reflection.newProxy(Iterable.class, ih)));
		//
		Assertions.assertNull(createMultimap(Collections.singleton(null)));
		//
		Assertions.assertNull(createMultimap(Collections.singleton(Reflection.newProxy(Map.class, ih))));
		//
		Assertions.assertEquals("{null=[null]}",
				toString(createMultimap(Collections.singleton(Collections.singletonMap(null, null)))));
		//
	}

	private static Multimap<?, ?> createMultimap(final Iterable<Map<?, ?>> maps) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP.invoke(null, maps);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAdd() {
		//
		Assertions.assertDoesNotThrow(() -> add(null, null));
		//
	}

	private static <E> void add(final Collection<E> items, final E item) throws Throwable {
		try {
			METHOD_ADD.invoke(null, items, item);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAssignableFrom() throws Throwable {
		//
		Assertions.assertFalse(isAssignableFrom(null, null));
		//
		Assertions.assertTrue(isAssignableFrom(Object.class, Object.class));
		//
		Assertions.assertFalse(isAssignableFrom(String.class, Object.class));
		//
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) throws Throwable {
		try {
			final Object obj = METHOD_IS_ASSIGNABLE_FROM.invoke(null, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(obj != null ? obj.getClass() : null));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetKey() throws Throwable {
		//
		Assertions.assertNull(getKey(null));
		//
	}

	private static <K> K getKey(final Entry<K, ?> instance) throws Throwable {
		try {
			return (K) METHOD_GET_KEY.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		Assertions.assertNull(getValue(null));
		//
	}

	private static <V> V getValue(final Entry<?, V> instance) throws Throwable {
		try {
			return (V) METHOD_GET_VALUE.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForName() throws Throwable {
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
				return (Class) obj;
			}
			throw new Throwable(toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(null, null));
		//
		Assertions.assertNull(filter(Stream.empty(), null));
		//
		Assertions.assertSame(stream, filter(stream, null));
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_FILTER.invoke(null, instance, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(obj.getClass()));
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
			throw new Throwable(toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSystemClipboard() throws Throwable {
		//
		Assertions.assertNull(getSystemClipboard(null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			final ProxyFactory proxyFactory = new ProxyFactory();
			//
			proxyFactory.setSuperclass(Toolkit.class);
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
			Assertions.assertNull(getSystemClipboard(cast(Toolkit.class, instance)));
			//
		} // if
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
			throw new Throwable(toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetContents() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> setContents(null, null, null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> setContents(new Clipboard(null), null, null));
			//
		} // if
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
	void testAddActionListener() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> addActionListener(null, (AbstractButton[]) null));
		//
		Assertions.assertDoesNotThrow(() -> addActionListener(null, null, new JButton()));
		//
	}

	private static void addActionListener(final ActionListener actionListener, final AbstractButton... abs)
			throws Throwable {
		try {
			METHOD_ADD_ACTION_LISTENER.invoke(null, actionListener, abs);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}