package org.springframework.context.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.util.IntList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.AttributeAccessor;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.base.Functions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class MapReportGuiTest {

	private static final String EMPTY = "";

	private static Method METHOD_IS_ALL_ATTRIBUTES_MATCHED, METHOD_REMOVE_ROW, METHOD_ADD_ROW,
			METHOD_GET_PREFERRED_WIDTH, METHOD_DOUBLE_VALUE, METHOD_AS_MAP, METHOD_GET_VALUES, METHOD_OR_ELSE,
			METHOD_MAX, METHOD_MAP_TO_INT, METHOD_CREATE_MULTI_MAP, METHOD_GET_SYSTEM_CLIP_BOARD, METHOD_SET_CONTENTS,
			METHOD_ADD_ACTION_LISTENER, METHOD_LENGTH, METHOD_TEST_AND_APPLY, METHOD_CREATE_MULTIMAP, METHOD_CLEAR,
			METHOD_TEST_AND_ACCEPT3, METHOD_TEST_AND_ACCEPT4, METHOD_WRITER_WITH_DEFAULT_PRETTY_PRINTER, METHOD_WRITER,
			METHOD_WRITE_VALUE_AS_STRING = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = MapReportGui.class;
		//
		(METHOD_IS_ALL_ATTRIBUTES_MATCHED = clz.getDeclaredMethod("isAllAttributesMatched", Map.class,
				AttributeAccessor.class)).setAccessible(true);
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
		(METHOD_GET_SYSTEM_CLIP_BOARD = clz.getDeclaredMethod("getSystemClipboard", Toolkit.class)).setAccessible(true);
		//
		(METHOD_SET_CONTENTS = clz.getDeclaredMethod("setContents", Clipboard.class, Transferable.class,
				ClipboardOwner.class)).setAccessible(true);
		//
		(METHOD_ADD_ACTION_LISTENER = clz.getDeclaredMethod("addActionListener", ActionListener.class,
				AbstractButton[].class)).setAccessible(true);
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", Object[].class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class, Function.class,
				Function.class)).setAccessible(true);
		//
		(METHOD_CREATE_MULTIMAP = clz.getDeclaredMethod("createMultimap", Multimap.class, BiPredicate.class))
				.setAccessible(true);
		//
		(METHOD_CLEAR = clz.getDeclaredMethod("clear", IntList.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3 = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_WRITER_WITH_DEFAULT_PRETTY_PRINTER = clz.getDeclaredMethod("writerWithDefaultPrettyPrinter",
				ObjectMapper.class)).setAccessible(true);
		//
		(METHOD_WRITER = clz.getDeclaredMethod("writer", ObjectMapper.class)).setAccessible(true);
		//
		(METHOD_WRITE_VALUE_AS_STRING = clz.getDeclaredMethod("writeValueAsString", ObjectWriter.class, Object.class))
				.setAccessible(true);
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

		private Map<Object, Object> prorperties = null;

		private Map<Object, Object> getProrperties() {
			if (prorperties == null) {
				prorperties = new LinkedHashMap<>();
			}
			return prorperties;
		}

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
				} // if
					//
			} else if (proxy instanceof PropertyResolver) {
				//
				if (Objects.equals(methodName, "getProperty") && args != null && args.length > 0) {
					//
					return MapUtils.getObject(getProrperties(), args[0]);
					//
				} else if (Objects.equals(methodName, "containsProperty") && args != null && args.length > 0) {
					//
					final Map<?, ?> map = getProrperties();
					//
					return map != null && map.containsKey(args[0]);
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

		private Dimension preferredSize = null;

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
			} else if (self instanceof Component) {
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

	private MapReportGui instance = null;

	private IH ih = null;

	private MH mh = null;

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
			instance = Util.cast(MapReportGui.class, Narcissus.allocateInstance(MapReportGui.class));
			//
		} // if
			//
		stream = Reflection.newProxy(Stream.class, ih = new IH());
		//
		mh = new MH();
		//
	}

	@Test
	void testAfterPropertiesSet() {
		//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
		if (instance != null) {
			//
			instance.setEnvironment(Reflection.newProxy(Environment.class, ih));
			//
		} // if
			//
		final Map<Object, Object> properties = ih != null ? ih.getProrperties() : null;
		//
		if (properties != null) {
			//
			properties.put("org.springframework.context.support.MapReportGui.prettyJson", null);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> afterPropertiesSet(instance));
		//
		if (properties != null) {
			//
			properties.put("org.springframework.context.support.MapReportGui.prettyJson", "true");
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
		FieldUtils.writeDeclaredField(instance, "dtm", null, true);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnCopy, 0, null)));
		//
		final AbstractButton cbPrettyJson = new JCheckBox();
		//
		FieldUtils.writeDeclaredField(instance, "cbPrettyJson", cbPrettyJson, true);
		//
		Assertions.assertDoesNotThrow(() -> actionPerformed(instance, new ActionEvent(btnCopy, 0, null)));
		//
		cbPrettyJson.setSelected(true);
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
			throw new Throwable(obj != null ? Util.toString(obj.getClass()) : null);
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
		if (GraphicsEnvironment.isHeadless()) {
			//
			final Component component = ProxyUtil.createProxy(Component.class, mh);
			//
			Assertions.assertNull(getPreferredWidth(component));
			//
			final double width = 1.0;
			//
			if (mh != null) {
				//
				mh.preferredSize = new Dimension((int) width, 2);
				//
			} // if
				//
			Assertions.assertEquals(width, getPreferredWidth(component));
			//
		} // if
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
			throw new Throwable(Util.toString(obj.getClass()));
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
		if (GraphicsEnvironment.isHeadless()) {
			//
			final double d1 = 2.3;
			//
			Assertions.assertEquals(d1, doubleValue(Double.valueOf(d1), d));
			//
		} // if
			//
	}

	private static double doubleValue(final Number instance, final double defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_DOUBLE_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Double) {
				return ((Double) obj).doubleValue();
			}
			throw new Throwable(obj != null ? Util.toString(obj.getClass()) : null);
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
			throw new Throwable(Util.toString(obj.getClass()));
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
			throw new Throwable(Util.toString(obj.getClass()));
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
			throw new Throwable(Util.toString(obj != null ? obj.getClass() : null));
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
			throw new Throwable(Util.toString(obj.getClass()));
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
			throw new Throwable(Util.toString(obj.getClass()));
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
				Util.toString(createMultimap(Collections.singleton(Collections.singletonMap(null, null)))));
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
			throw new Throwable(Util.toString(obj.getClass()));
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
			Assertions.assertNull(getSystemClipboard(ProxyUtil.createProxy(Toolkit.class, mh)));
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
			throw new Throwable(Util.toString(obj.getClass()));
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

	@Test
	void testLength() throws Throwable {
		//
		Assertions.assertEquals(0, length(null));
		//
	}

	private static int length(final Object[] instance) throws Throwable {
		try {
			final Object obj = METHOD_LENGTH.invoke(null, (Object) instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(obj != null ? obj.getClass() : null));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, Functions.identity(), null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysFalse(), null, null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final Function<T, R> functionTrue, final Function<T, R> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMultimap() throws Throwable {
		//
		final Multimap<?, ?> multimap = ImmutableMultimap.of(EMPTY, EMPTY);
		//
		Assertions.assertNull(createMultimap(multimap, (a, b) -> false));
		//
		Assertions.assertEquals("{=[]}", Util.toString(createMultimap(multimap, null)));
		//
		Assertions.assertEquals("{=[]}", Util.toString(createMultimap(multimap, (a, b) -> true)));
		//
	}

	private static Multimap<?, ?> createMultimap(final Multimap<?, ?> mm,
			final BiPredicate<Multimap<?, ?>, Object> biPredicate) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTIMAP.invoke(null, mm, biPredicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(Util.toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testClear() {
		//
		Assertions.assertDoesNotThrow(() -> clear(null));
		//
		Assertions.assertDoesNotThrow(() -> clear(new IntList()));
		//
	}

	private static void clear(final IntList instance) throws Throwable {
		try {
			METHOD_CLEAR.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null));
		//
		final Predicate<?> alwaysTrue = Predicates.alwaysTrue();
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(alwaysTrue, null, null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept(alwaysTrue, null, x -> {
			}));
			//
		} else {
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysFalse(), null, null));
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, null, null));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> false, null, null, null));
			//
			Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, null, (a, b) -> {
			}));
			//
		} // if
			//
	}

	private static <T> void testAndAccept(final Predicate<T> predicate, final T value, final Consumer<T> consumer)
			throws Throwable {
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
	void testWriterWithDefaultPrettyPrinter() throws Throwable {
		//
		Assertions.assertNull(writerWithDefaultPrettyPrinter(null));
		//
	}

	private static ObjectWriter writerWithDefaultPrettyPrinter(final ObjectMapper instance) throws Throwable {
		try {
			final Object obj = METHOD_WRITER_WITH_DEFAULT_PRETTY_PRINTER.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof ObjectWriter) {
				return (ObjectWriter) obj;
			}
			throw new Throwable(Util.toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testWriter() throws Throwable {
		//
		Assertions.assertNull(writer(null));
		//
	}

	private static ObjectWriter writer(final ObjectMapper instance) throws Throwable {
		try {
			final Object obj = METHOD_WRITER.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof ObjectWriter) {
				return (ObjectWriter) obj;
			}
			throw new Throwable(Util.toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testWriteValueAsString() throws Throwable {
		//
		Assertions.assertNull(writeValueAsString(null, null));
		//
	}

	private static String writeValueAsString(final ObjectWriter instance, final Object value) throws Throwable {
		try {
			final Object obj = METHOD_WRITE_VALUE_AS_STRING.invoke(null, instance, value);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(obj.getClass()));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}