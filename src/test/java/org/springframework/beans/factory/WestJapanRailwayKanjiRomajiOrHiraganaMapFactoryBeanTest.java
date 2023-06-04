package org.springframework.beans.factory;

import java.io.File;
import java.io.InputStream;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.reflect.Reflection;

class WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBeanTest {

	private static Method METHOD_TO_STRING, METHOD_CAST, METHOD_TEST_AND_APPLY, METHOD_CREATE_TABLE,
			METHOD_GET_UNICODE_BLOCKS, METHOD_TEST, METHOD_ACCEPT, METHOD_IS_INSTANCE, METHOD_CONTAINS, METHOD_PUT,
			METHOD_ADD, METHOD_IS_ASSIGNABLE_FROM, METHOD_OPEN_STREAM, METHOD_GET_TRIPLES_1, METHOD_GET_TRIPLES_2,
			METHOD_GET_NAME, METHOD_GET_ROW_KEY, METHOD_GET_COLUMN_KEY, METHOD_GET_VALUE, METHOD_GET_MODULE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean.class;
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_CREATE_TABLE = clz.getDeclaredMethod("createTable", Object[].class)).setAccessible(true);
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", BiPredicate.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_ACCEPT = clz.getDeclaredMethod("accept", BiConsumer.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_IS_INSTANCE = clz.getDeclaredMethod("isInstance", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_PUT = clz.getDeclaredMethod("put", Map.class, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_CONTAINS = clz.getDeclaredMethod("contains", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_IS_ASSIGNABLE_FROM = clz.getDeclaredMethod("isAssignableFrom", Class.class, Class.class))
				.setAccessible(true);
		//
		(METHOD_OPEN_STREAM = clz.getDeclaredMethod("openStream", URL.class)).setAccessible(true);
		//
		(METHOD_GET_TRIPLES_1 = clz.getDeclaredMethod("getTriples", Map.class)).setAccessible(true);
		//
		(METHOD_GET_TRIPLES_2 = clz.getDeclaredMethod("getTriples", Field[].class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_GET_ROW_KEY = clz.getDeclaredMethod("getRowKey", Cell.class)).setAccessible(true);
		//
		(METHOD_GET_COLUMN_KEY = clz.getDeclaredMethod("getColumnKey", Cell.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Cell.class)).setAccessible(true);
		//
		(METHOD_GET_MODULE = clz.getDeclaredMethod("getModule", Class.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Object rowKey, columnKey, value = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Cell) {
				//
				if (Objects.equals(methodName, "getRowKey")) {
					//
					return rowKey;
					//
				} else if (Objects.equals(methodName, "getColumnKey")) {
					//
					return columnKey;
					//
				} else if (Objects.equals(methodName, "getValue")) {
					//
					return value;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean instance = null;

	private IH ih = null;

	private Cell<?, ?, ?> cell = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean();
		//
		cell = Reflection.newProxy(Cell.class, ih = new IH());
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setUrl("");
			//
		} // if
			//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setUrl(" ");
			//
		} // if
			//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
	}

	@Test
	void testSetUnicodeBlock() throws Throwable {
		//
		final Field unicodeBlock = WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean.class
				.getDeclaredField("unicodeBlock");
		//
		if (unicodeBlock != null) {
			//
			unicodeBlock.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock(null));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock(""));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("".toCharArray()));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("".getBytes()));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("123"));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("HIRAGANA".toCharArray()));
		//
		final Object hiragana = get(unicodeBlock, instance);
		//
		Assertions.assertEquals("HIRAGANA", toString(hiragana));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock(hiragana));
		//
		Assertions.assertSame(hiragana, get(unicodeBlock, instance));
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance.setUnicodeBlock(Collections.emptyMap()));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> instance.setUnicodeBlock("h"));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> instance.setUnicodeBlock("map"));
		//
	}

	private static Object get(final Field field, final Object instance)
			throws IllegalArgumentException, IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Test
	void testToString() throws Throwable {
		//
		Assertions.assertNull(toString(null));
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

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
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
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
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

	private static class InnerClass {

		private String string1, string2 = null;

	}

	@Test
	void testCreateTable() throws Throwable {
		//
		Assertions.assertNull(createTable(new Object[] { null }));
		//
		final InnerClass cls = new InnerClass();
		//
		Assertions.assertNull(createTable(new Object[] { "", cls }));
		//
		cls.string1 = "";
		//
		Assertions.assertNull(createTable(new Object[] { "", cls }));
		//
		cls.string1 = "一";
		//
		Assertions.assertNull(createTable(new Object[] { "", cls }));
		//
		Assertions.assertEquals(ImmutableTable.of(cls.string1 = "一一", UnicodeBlock.BASIC_LATIN, cls.string2 = " "),
				createTable(new Object[] { "", cls }));
		//
	}

	private static Table<String, UnicodeBlock, String> createTable(final Object[] os) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_TABLE.invoke(null, (Object) os);
			if (obj == null) {
				return null;
			} else if (obj instanceof Table) {
				return (Table) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetUnicodeBlocks() throws Throwable {
		//
		Assertions.assertNull(getUnicodeBlocks(null));
		//
	}

	private static List<UnicodeBlock> getUnicodeBlocks(final String string) throws Throwable {
		try {
			final Object obj = METHOD_GET_UNICODE_BLOCKS.invoke(null, string);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null, null));
		//
	}

	private static <T, U> boolean test(final BiPredicate<T, U> instance, final T t, final U u) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, t, u);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAccept() {
		//
		Assertions.assertDoesNotThrow(() -> accept(null, null, null));
		//
	}

	private static <T, U> void accept(final BiConsumer<T, U> instance, final T t, final U u) throws Throwable {
		try {
			METHOD_ACCEPT.invoke(null, instance, t, u);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsInstance() throws Throwable {
		//
		Assertions.assertFalse(isInstance(null, null));
		//
	}

	private static boolean isInstance(final Class<?> clz, final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_INSTANCE.invoke(null, clz, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPut() {
		//
		Assertions.assertDoesNotThrow(() -> put(null, null, null));
		//
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) throws Throwable {
		try {
			METHOD_PUT.invoke(null, instance, key, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(contains(null, null));
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS.invoke(null, items, item);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
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
		Assertions.assertFalse(isAssignableFrom(String.class, null));
		//
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) throws Throwable {
		try {
			final Object obj = METHOD_IS_ASSIGNABLE_FROM.invoke(null, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOpenStream() throws Throwable {
		//
		Assertions.assertNotNull(openStream(new File("pom.xml").toURI().toURL()));
		//
	}

	private static InputStream openStream(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_OPEN_STREAM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetTriples() throws Throwable {
		//
		Assertions.assertNull(getTriples(null, null));
		//
		Assertions.assertNull(getTriples(new Field[] { null }, null));
		//
		final Map<UnicodeBlock, String> map = new LinkedHashMap<>();
		//
		Assertions.assertNull(getTriples(map));
		//
		map.put(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, null);
		//
		map.put(UnicodeBlock.CJK_STROKES, null);
		//
		Assertions.assertThrows(IllegalStateException.class, () -> getTriples(map));
		//
	}

	private static List<Triple<String, UnicodeBlock, String>> getTriples(final Map<UnicodeBlock, String> map)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_TRIPLES_1.invoke(null, map);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static List<Triple<String, UnicodeBlock, String>> getTriples(final Field[] fs, final Object instance)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_TRIPLES_2.invoke(null, fs, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
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
	void testGetRowKey() throws Throwable {
		//
		Assertions.assertNull(getRowKey(null));
		//
		Assertions.assertNull(getRowKey(cell));
		//
	}

	private static <R> R getRowKey(final Cell<R, ?, ?> instance) throws Throwable {
		try {
			return (R) METHOD_GET_ROW_KEY.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetColumnKey() throws Throwable {
		//
		Assertions.assertNull(getColumnKey(null));
		//
		Assertions.assertNull(getColumnKey(cell));
		//
	}

	private static <C> C getColumnKey(final Cell<?, C, ?> instance) throws Throwable {
		try {
			return (C) METHOD_GET_COLUMN_KEY.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		Assertions.assertNull(getValue(null));
		//
		Assertions.assertNull(getValue(cell));
		//
	}

	private static <V> V getValue(final Cell<?, ?, V> instance) throws Throwable {
		try {
			return (V) METHOD_GET_VALUE.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetModule() throws Throwable {
		//
		Assertions.assertNull(getModule(null));
		//
	}

	private static Module getModule(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_MODULE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Module) {
				return (Module) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}